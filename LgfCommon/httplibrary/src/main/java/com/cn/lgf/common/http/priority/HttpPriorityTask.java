package com.cn.lgf.common.http.priority;

import androidx.annotation.NonNull;

import com.cn.lgf.common.http.base.Request;
import com.cn.lgf.common.http.priority.base.PriorityTask;

public class HttpPriorityTask extends PriorityTask {
    //网络请求Request
    private Request mRequest;

    public HttpPriorityTask(@NonNull Request request) {
        super(request.getPriority());
        mRequest = request;
    }

    public HttpPriorityTask() {

    }

    public void setRequest(Request request) {
        mRequest = request;
    }

    public Request getRequest() {
        return mRequest;
    }

    @Override
    public void execute() {
        if(isCanceled()) {
            return;
        }
    }

    @Override
    public int getTaskId() {
        return mRequest.url.hashCode();
    }
}
