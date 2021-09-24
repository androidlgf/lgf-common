package com.cn.lgf.module.main.http;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.cn.lgf.common.http.download.DownloadTask;
import com.cn.lgf.common.http.download.TaskListener;
import com.cn.lgf.common.http.model.HttpDownModel;

import java.io.File;
import java.util.List;

public class VideoDownloadModel extends HttpDownModel {
    private Context mContext;

    public VideoDownloadModel(Context context) {
        super();
        mContext = context;
    }

    @Override
    public DownloadTask createDownloadTask() {
        DownloadTask downloadTask = new DownloadTask(mContext, "https://bensoncar.oss-cn-shanghai.aliyuncs.com/test/upload/model/video/file_715mswir7.mp4", mContext.getCacheDir().getPath() + File.separator + "本森超跑.mp4");
        return downloadTask;
    }

    @Override
    public List<DownloadTask> createMultiDownloadTasks() {
        return null;
    }

    @Override
    public TaskListener createDownloadListener() {
        return new TaskListener() {
            @Override
            public boolean isFinishing() {
                return false;
            }

            @Override
            public void onTaskStart(Object o) {
                Log.i("VideoDownloadModel", "onTaskStart=" + o.toString());
            }

            @Override
            public void onTaskProgress(Object o, int progress) {
                Log.i("VideoDownloadModel", "onTaskProgress=" + o.toString() + "==progress==" + progress);
            }

            @Override
            public void onTaskFailed(Object o, @Nullable Throwable throwable) {
                Log.i("VideoDownloadModel", "onTaskFailed=" + o.toString());
            }

            @Override
            public void onTaskSuccess(Object o, Object o2) {
                Log.i("VideoDownloadModel", "onTaskSuccess=" + o.toString());
            }

            @Override
            public void onTasksComplete(@Nullable List successfulTasks, @Nullable List failedTasks) {
                if (successfulTasks != null) {
                    Log.i("VideoDownloadModel", "onTasksComplete successfulTasks=" + successfulTasks.size());
                }
                if (failedTasks != null) {
                    Log.i("VideoDownloadModel", "onTasksComplete failedTasks=" + failedTasks.size());

                }
            }
        };
    }
}
