package com.cn.lgf.common.http.download;

import androidx.annotation.MainThread;

import java.util.List;

public abstract class SingleDownloadListener<Task, Result> implements TaskListener<Task, Result> {

    @MainThread
    public void onTasksComplete(List<Task> successfulTasks, List<Task> failedTasks) {

    }
}

