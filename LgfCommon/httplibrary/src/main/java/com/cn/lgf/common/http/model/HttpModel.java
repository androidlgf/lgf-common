package com.cn.lgf.common.http.model;

import androidx.annotation.NonNull;

import com.cn.lgf.common.http.debug.HttpLog;
import com.cn.lgf.common.http.HttpStatusPlugin;
import com.cn.lgf.common.http.RequestFactory;
import com.cn.lgf.common.http.base.IRequestHandler;
import com.cn.lgf.common.http.base.Request;
import com.cn.lgf.common.http.base.RequestConnect;

public abstract class HttpModel {
    //Http Connect
    private RequestConnect mRequestConnect;
    //业务拦截插件(注Http Code==200情况 拦截业务)
    private static HttpStatusPlugin sPlugin = new HttpStatusPlugin();
    //请求参数
    private Request mCurrentRequest;

    public HttpModel() {
        mRequestConnect = RequestFactory.initConnect();
        if (needCheckResponseValid()) {
            mRequestConnect.setHttpResponsePlugin(sPlugin);
        }
    }

    public HttpModel(@NonNull RequestConnect connect) {
        mRequestConnect = connect;
        if (needCheckResponseValid()) {
            mRequestConnect.setHttpResponsePlugin(sPlugin);
        }
    }

    /**
     * 生成Http 请求Request
     *
     * @return
     */
    public abstract Request createRequest();

    /**
     * 网络业务回调
     *
     * @return
     */
    public abstract IRequestHandler createRequestHandler();

    /**
     * 开启Request请求
     */
    public void startRequest() {
        if (mRequestConnect == null) {
            HttpLog.e(HttpModel.class.getName(), "connect error, connect can't be null");
            return;
        }
        Request request = createRequest();
        if (request == null) {
            HttpLog.e(HttpModel.class.getName(), "http request error, request can't be null");
            return;
        }
        mRequestConnect.connect(request, createRequestHandler());
        mCurrentRequest = request;
    }

    /**
     * 销毁 回收线程
     */
    public void destroy() {
        if (mRequestConnect != null && mCurrentRequest != null) {
            mRequestConnect.abort(mCurrentRequest);
        }
    }

    /**
     * 业务逻辑校验()
     *
     * @return
     */
    public boolean needCheckResponseValid() {
        return false;
    }
}
