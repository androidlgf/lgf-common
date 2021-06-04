package com.cn.lgf.common.http.download;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;

/**
 * 文件下载Task
 */
public class DownloadTask implements QueueTaskRunner.ITask {
    //下载文件Url
    private String mUrl;
    //保存文件路径
    private String mCachePath;
    //上下文
    private Context mContext;
    //DB 存储Download状态
    private DownloadDbHelper mHelper;
    //文件描述
    private DownloadDbHelper.DownloadInfo mDownloadInfo;
    //是否强制重新下载
    private boolean isForceDownload;

    public DownloadTask(@NonNull Context context, @NonNull String url, @NonNull String filePath) {
        this(context, url, filePath, false);
    }

    public DownloadTask(@NonNull Context context, @NonNull String url, @NonNull String filePath, boolean forceDownload) {
        mUrl = url;
        mCachePath = filePath;
        mContext = context;
        mHelper = new DownloadDbHelper(mContext);
        mDownloadInfo = new DownloadDbHelper.DownloadInfo();
        mDownloadInfo.status = DownloadStatus.NONE;
        mDownloadInfo.downloadSize = 0;
        mDownloadInfo.totalSize = 0;
        mDownloadInfo.url = url;
        mDownloadInfo.filePath = filePath;
        isForceDownload = forceDownload;
    }

    public void startWork() {
        mDownloadInfo.status = DownloadStatus.DOWNLOADING;
        mHelper.insertDownloadInfoForDB(mDownloadInfo);
    }

    public void progress(long currentPosition, long totalSize) {
        mDownloadInfo.downloadSize = currentPosition;
        mDownloadInfo.totalSize = totalSize;
        mHelper.updateDownloadInfoForDB(mDownloadInfo);
    }

    public void failed() {
        mDownloadInfo.status = DownloadStatus.DOWNLOAD_FAILED;
        mHelper.updateDownloadInfoForDB(mDownloadInfo);
    }

    public void success(long downloadSize, long totalSize) {
        mDownloadInfo.downloadSize = downloadSize;
        mDownloadInfo.totalSize = totalSize;
        mDownloadInfo.status = DownloadStatus.DOWNLOAD_SUCCESS;
        mHelper.updateDownloadInfoForDB(mDownloadInfo);
    }

    public String getDownloadUrl() {
        return mUrl;
    }

    public String getDownloadCachePath() {
        return mCachePath;
    }

    @Override
    public int getTaskId() {
        return mUrl.hashCode();
    }

    @Override
    public boolean isComplete() {
        DownloadDbHelper.DownloadInfo info = mHelper.queryDownloadInfo(mUrl);
        if (isForceDownload) {
            if (info != null) {
                mHelper.delete(info);
            }
            File file = new File(mDownloadInfo.filePath);
            if (file.exists()) {
                file.delete();
            }
            return false;
        }
        if (info != null) {
            //如果文件正在下载或者下载完成会直接返回而不进行下载
            if (info.status == DownloadStatus.DOWNLOAD_SUCCESS || info.status == DownloadStatus.DOWNLOADING) {
                File file = new File(mDownloadInfo.filePath);
                if (!file.exists()) {
                    mHelper.delete(info);
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}
