package com.cn.lgf.common.http.download;

public class DownloadTask implements QueueTaskRunner.ITask {

    @Override
    public int getTaskId() {
        return 0;
    }

    @Override
    public boolean isComplete() {
        return false;
    }
}
