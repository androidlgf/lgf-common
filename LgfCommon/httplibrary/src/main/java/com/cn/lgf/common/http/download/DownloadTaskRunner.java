package com.cn.lgf.common.http.download;

import android.text.TextUtils;
import com.cn.lgf.common.http.HttpRequest;
import com.cn.lgf.common.http.RequestFactory;
import com.cn.lgf.common.http.base.IResponse;
import com.cn.lgf.common.http.base.Request;
import com.cn.lgf.common.http.base.RequestConnect;
import com.cn.lgf.common.http.debug.HttpLog;
import com.cn.lgf.common.http.exception.HttpException;
import com.cn.lgf.common.http.utils.IOHelper;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class DownloadTaskRunner extends QueueTaskRunner<DownloadTask, Boolean> {
    private RequestConnect mRequestConnect;

    public DownloadTaskRunner(RequestConnect connect) {
        super();
        mRequestConnect = connect;
    }

    @Deprecated
    public DownloadTaskRunner() {
        super();
        mRequestConnect = RequestFactory.initConnect();
    }

    @Override
    protected Boolean execute(DownloadTask task) throws Throwable {
        String url = task.getDownloadUrl();
        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("download failed,because download url is null");
        }
        RequestConnect connect = mRequestConnect;
        if (connect == null) {
            throw new NullPointerException("download connect can't be null");
        }
        task.startWork();
        Request request = HttpRequest.get(url);
        IResponse response = connect.syncConnect(request);
        InputStream is = null;
        try {
            is = response.getByteStream();
            //获取文件总长度
            long totalLength = response.getContentLength();
            int code = response.getHttpCode();
            if (code != 200) {
                return null;
            }
            //已经下载的文件长度
            long tempLength = 0;

            String filePath = task.getDownloadCachePath();
            int lastIndex = filePath.lastIndexOf("/");
            String fileDirPath = filePath.substring(0, lastIndex + 1);
            String fileName = filePath.substring(lastIndex + 1);
            File dir = new File(fileDirPath);
            if (!dir.exists()) {
                dir.mkdirs();//创建目录
            }
            File file = new File(dir, fileName);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                tempLength = file.length();
            }
            if (totalLength != 0 && tempLength == totalLength) {//下载成功
                HttpLog.i(fileName + " already download,return immediately");
                task.success(tempLength, totalLength);
                return true;
            } else {
                if (tempLength > 0) {
                    file.delete();
                    file.createNewFile();
                }
            }
            setProgress(task, 0);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            byte buf[] = new byte[1024];
            long downloadFileSize = 0;
            int percent = 0;
            int downloadCount = 0;
            do {
                int numRead = is.read(buf);
                if (numRead > 0) {
                    downloadCount = 0;
                    randomAccessFile.write(buf, 0, numRead);
                    downloadFileSize += numRead;
                    int currentPercent = (int) (((double) downloadFileSize / (double) totalLength) * 100.0d);
                    if (currentPercent >= percent + 10) {
                        //每次只有进度是达到10%才回调前端
                        setProgress(task, currentPercent);
                        task.progress(downloadFileSize, totalLength);
                        percent = currentPercent;
                    }
                } else {
                    downloadCount++;
                    if (downloadCount > 20) {
                        task.failed();
                        throw new HttpException("download failed, and retry count is more than max count");
                    }
                }
            } while (downloadFileSize < totalLength);

            if (downloadFileSize == totalLength) {
                HttpLog.i("---download complete----url ==" + url);
                task.success(downloadFileSize, totalLength);
                setProgress(task, 1);
                return true;
            } else {
                task.failed();
                throw new HttpException("download failed, because file totalSize is wrong");
            }
        } finally {
            IOHelper.close(is);
        }
    }
    @Override
    public void destroy() {
        super.destroy();
        if (mRequestConnect != null) {
            mRequestConnect.abortAll();
        }
        mRequestConnect=null;
    }
}
