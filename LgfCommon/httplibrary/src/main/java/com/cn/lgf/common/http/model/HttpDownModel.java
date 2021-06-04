package com.cn.lgf.common.http.model;
import androidx.annotation.NonNull;

import com.cn.lgf.common.http.debug.HttpLog;
import com.cn.lgf.common.http.base.RequestConnect;
import com.cn.lgf.common.http.download.DownloadTask;
import com.cn.lgf.common.http.download.DownloadTaskRunner;
import com.cn.lgf.common.http.download.TaskListener;

import java.util.List;

public abstract class HttpDownModel {
    private DownloadTaskRunner mDownloadTaskRunner;

    public HttpDownModel() {
        mDownloadTaskRunner = new DownloadTaskRunner();
    }

    public HttpDownModel(@NonNull RequestConnect connect) {
        mDownloadTaskRunner = new DownloadTaskRunner(connect);
    }

    /**
     * 创建DownloadTask
     *
     * @return
     */
    public abstract DownloadTask createDownloadTask();

    /**
     * 创建DownloadTask
     *
     * @return
     */
    public abstract List<DownloadTask> createMultiDownloadTasks();

    /**
     * 创建下载监听
     *
     * @return
     */
    public abstract TaskListener createDownloadListener();

    /**
     * 开启Request请求
     */
    public void startDownload() {
        if (mDownloadTaskRunner == null) {
            HttpLog.e(HttpDownModel.class.getName(), "downloadTaskRunner error, downloadTaskRunner can't be null");
            return;
        }
        DownloadTask downloadTask = createDownloadTask();
        if (downloadTask == null) {
            HttpLog.e(HttpDownModel.class.getName(), "http downloadTask error, downloadTask can't be null");
            return;
        }
        mDownloadTaskRunner.startTask(downloadTask, createDownloadListener());
    }

    public void startMultiDownload() {
        if (mDownloadTaskRunner == null) {
            HttpLog.e(HttpDownModel.class.getName(), "multiDownloadTasks error, multiDownloadTasks can't be null");
            return;
        }
        List<DownloadTask> multiDownloadTasks = createMultiDownloadTasks();
        if (multiDownloadTasks == null || multiDownloadTasks.size() <= 0) {
            HttpLog.e(HttpDownModel.class.getName(), "http multiDownloadTasks error, multiDownloadTasks can't be null");
            return;
        }
        mDownloadTaskRunner.startTasks(multiDownloadTasks, createDownloadListener());
    }

    public void destroy() {
        if (mDownloadTaskRunner != null) {
            mDownloadTaskRunner.destroy();
        }
    }
}
