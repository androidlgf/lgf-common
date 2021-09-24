package com.cn.lgf.module.main.http;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.cn.lgf.common.http.HttpRequest;
import com.cn.lgf.common.http.base.IRequestHandler;
import com.cn.lgf.common.http.base.IResponse;
import com.cn.lgf.common.http.base.Request;
import com.cn.lgf.common.http.model.HttpModel;

public class AdHttpModel extends HttpModel {
    @Override
    public Request createRequest() {
        return HttpRequest.get("http://test-bensen.videojj.com/api/mobile/vehicleDetail/42");
    }

    @Override
    public IRequestHandler createRequestHandler() {
        return new IRequestHandler() {
            @Override
            public void requestFinish(Request request, IResponse response) {
                Log.i("AdHttpModel",response.getResult());
            }

            @Override
            public void requestError(Request request, @Nullable Exception e) {
                Log.i("AdHttpModel",e.getMessage());
            }

            @Override
            public void startRequest(Request request) {

            }

            @Override
            public void requestProgress(Request request, int progress) {

            }
        };
    }
}
