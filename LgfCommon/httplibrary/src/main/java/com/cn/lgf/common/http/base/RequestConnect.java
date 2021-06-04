package com.cn.lgf.common.http.base;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.cn.lgf.common.http.HttpApp;
import com.cn.lgf.common.http.debug.HttpLog;
import com.cn.lgf.common.http.exception.HttpException;
import com.cn.lgf.common.http.interf.Method;
import com.cn.lgf.common.http.priority.HttpPriorityTask;
import com.cn.lgf.common.http.priority.PriorityTaskDispatch;
import com.cn.lgf.common.http.priority.base.PriorityTask;
import com.cn.lgf.common.http.utils.NetworkHelper;
import com.cn.lgf.common.http.utils.UIHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestConnect {
    //标识
    private static final String TAG = "RequestConnect";
    //Http Headers
    private HashMap<String, String> mDefaultHeaders;
    //Http Priority Dispatch
    private PriorityTaskDispatch mPriorityDispatch;
    //Http Connect
    private IRequestConnect mRequestConnect;
    //Http 业务拦截
    private Method<? extends HttpExtraPlugin> mHttpResponsePlugin;
    //Http Status
    private RequestConnectStatus mConnectStatus = RequestConnectStatus.NULL;

    public RequestConnect(@NonNull IRequestConnect requestConnect) {
        this.mRequestConnect = requestConnect;
        mDefaultHeaders = HttpDefaultHeaders.INSTANCE.createHttpDefaultHeaders();
        mPriorityDispatch = new PriorityTaskDispatch();
    }

    /**
     * 异步请求
     *
     * @param request
     * @param handler
     */
    public void connect(Request request, final IRequestHandler handler) {
        try {
            checkRequestValid(request, mRequestConnect);
            if (request.requestType == RequestType.GET || request.requestType == RequestType.DELETE) {
                request = buildUrlWithParams(request);
            } else {
                request = buildRequestParams(request);
            }
            HttpLog.i(TAG, "start Request, Url = " + request.url);
            mRequestConnect.connect(request, new RequestHandler(handler, mHttpResponsePlugin != null ? mHttpResponsePlugin.call() : null));

        } catch (Exception e) {
            if (handler != null) {
                handler.requestError(request, e);
            }
        }

    }

    /**
     * 同步请求
     *
     * @param request
     * @return
     */
    public IResponse syncConnect(Request request) throws Exception {
        checkRequestValid(request, mRequestConnect);
        request = deployRequestByHeaders(request);
        if (request.requestType == RequestType.GET || request.requestType == RequestType.DELETE) {
            request = buildUrlWithParams(request);
        } else {
            request = buildRequestParams(request);
        }
        HttpLog.i(TAG, "start Request, Url = " + request.url);
        return mRequestConnect.syncConnect(request);
    }

    /***
     * 根据标识ID取消
     * @param requestId
     * @return
     */
    public boolean abort(int requestId) {
        List<PriorityTask> runningTasks = mPriorityDispatch.runningPriorityTasks();
        for (PriorityTask priorityTask : runningTasks) {
            if (priorityTask instanceof HttpPriorityTask) {
                HttpPriorityTask httpPriorityTask = (HttpPriorityTask) priorityTask;
                Request taskRequest = httpPriorityTask.getRequest();
                if (taskRequest.requestId == requestId) {
                    httpPriorityTask.cancelTask();
                    break;
                }
            }
        }
        return mRequestConnect != null && mRequestConnect.abort(requestId);
    }

    /***
     * 取消单个Request请求
     * @param request
     * @return
     */
    public boolean abort(Request request) {
        if (mRequestConnect == null) {
            HttpLog.w("connect can't be null, please check");
            return false;
        }
        if (request == null) {
            HttpLog.w("request can't be null, please check");
            return false;
        }
        return abort(request.requestId);
    }

    /***
     * 取消请求队列里面的所有请求
     * @return
     */
    public boolean abortAll() {
        if (mRequestConnect == null) {
            return false;
        }
        if (mPriorityDispatch != null) {
            mPriorityDispatch.cancelAll();
        }
        return mRequestConnect.abortAll();
    }

    public RequestConnectStatus getConnectStatus() {
        return mConnectStatus;
    }

    /**
     * 设置response 判定结果plugin.
     *
     * @param mHttpResponsePlugin
     */
    public void setHttpResponsePlugin(Method<? extends HttpExtraPlugin> mHttpResponsePlugin) {
        this.mHttpResponsePlugin = mHttpResponsePlugin;
    }

    /**
     * 检查参数有效性
     *
     * @param request
     * @param connect
     * @throws Exception
     */
    private void checkRequestValid(Request request, IRequestConnect connect) throws Exception {
        if (connect == null) {
            HttpLog.w("connect can't be null, please check");
            throw new HttpException("connect can't be null, please check");
        }
        if (request == null) {
            HttpLog.w("request can't be null, please check");
            throw new HttpException("request can't be null, please check");
        }
        if (TextUtils.isEmpty(request.url) || TextUtils.isEmpty(request.url.trim())) {
            HttpLog.w("request url can't be null, please check");
            throw new HttpException("request url can't be null, please check");
        }
//        request.url = parseUrl(request.url);
//        if (TextUtils.isEmpty(request.url)) {
//            HttpDebugLog.w("request url is invalid, please check");
//            throw new HttpException("request url is invalid, please check");
//        }
        if (!NetworkHelper.isNetworkAvailable(HttpApp.getContext())) {
            HttpLog.w("network is invalid, please check");
            throw new HttpException("network is invalid, please check");
        }
    }

    private Request deployRequestByHeaders(Request request) {

        if (UIHelper.isOnUIThread()) {
            //如果是主线程的调用，每次都处理下头部信息
            mDefaultHeaders = updateDefaultHeaders(mDefaultHeaders);
        }
        if (mDefaultHeaders != null) {
            Map<String, String> headers = request.headers;
            if (headers != null) {
                headers.putAll(mDefaultHeaders);
            } else {
                headers = mDefaultHeaders;
            }
            request.headers = headers;
        }
        return request;
    }

    /***
     * 更新默认Http Headers
     * 更新可根据环境变化的 Headers（网络状态等等）
     * @param defaultHeaders
     * @return
     */
    private HashMap<String, String> updateDefaultHeaders(HashMap<String, String> defaultHeaders) {
        if (defaultHeaders == null) {
            return HttpDefaultHeaders.INSTANCE.createHttpDefaultHeaders();
        }
        return defaultHeaders;
    }

    /**
     * 参数拼接
     *
     * @param request
     * @return
     */
    private Request buildRequestParams(@NonNull Request request) {
        Map<String, String> params = request.params;
        if (params == null || params.size() <= 0 || !request.isEncrypted) {
            return request;
        }
        StringBuilder paramsBuild = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() != null) {
                paramsBuild.append(entry.getKey());
                paramsBuild.append("=");
                paramsBuild.append(entry.getValue());
                paramsBuild.append("&");
            }
        }
        if (params.size() > 0) {
            paramsBuild.delete(paramsBuild.length() - 1, paramsBuild.length());
        }
        request.params.clear();
        request.params.put("isEncrypted", String.valueOf(true));
        return request;
    }

    /***
     * 参数拼接
     * @param request
     * @return
     */

    private Request buildUrlWithParams(@NonNull Request request) {
        Map<String, String> params = request.params;
        if (params == null || params.size() <= 0) {
            return request;
        }
        String url = request.url;
        StringBuilder sb = new StringBuilder(url);
        if (!url.contains("?")) {
            sb.append("?");
        }
        StringBuilder paramsBuild = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() != null) {
                paramsBuild.append(entry.getKey());
                paramsBuild.append("=");
                paramsBuild.append(entry.getValue());
                paramsBuild.append("&");
            }
        }
        if (params.size() > 0) {
            paramsBuild.delete(paramsBuild.length() - 1, paramsBuild.length());
        }
        if (request.isEncrypted) {

        } else {
            sb.append(paramsBuild.toString());
        }
        request.url = sb.toString();
        request.params = null;
        return request;
    }

    /***
     * RequestHandler Http任务回调
     */
    private class RequestHandler implements IRequestHandler {
        private IRequestHandler mHandler;
        private HttpExtraPlugin mHttpExtraPlugin;

        private RequestHandler(IRequestHandler handler, HttpExtraPlugin httpExtraPlugin) {
            if (handler != null) {
                this.mHandler = handler;
            }
            if (httpExtraPlugin != null) {
                this.mHttpExtraPlugin = httpExtraPlugin;
            }
        }

        @Override
        public void startRequest(Request request) {
            if (mHandler != null) {
                mHandler.startRequest(request);
            }
            mConnectStatus = RequestConnectStatus.ACTIVE;
        }

        @Override
        public void requestProgress(Request request, int progress) {
            if (mHandler != null) {
                mHandler.requestProgress(request, progress);
            }
        }

        @Override
        public void requestFinish(Request request, IResponse response) {
            try {
                if (!response.isSuccess()) {
                    requestError(request, new HttpException("http error, error code is " + response.getHttpCode()));
                    return;
                }
                String result = response.getResult();
                if (TextUtils.isEmpty(result)) {
                    requestError(request, new HttpException("http error, response is null."));
                    return;
                }
                if (mHttpExtraPlugin != null) {
                    mHttpExtraPlugin.setResponseStringResult(result);
                    if (!mHttpExtraPlugin.isSuccess()) {
                        requestError(request, new HttpException("http error, response is invaild data, and response result is " + response.getResult()));
                        return;
                    }
                }
                if (mHandler != null) {
                    mHandler.requestFinish(request, response);
                }
                mConnectStatus = RequestConnectStatus.IDLE;
            } catch (Exception e) {
                requestError(request, e);
            }
        }

        @Override
        public void requestError(final Request request, @Nullable Exception e) {
            startReportErrorLog(request, e);
            try {
                if (request.needRetry && request.retryCount > 1) {
                    request.retryCount = request.retryCount - 1;
                    if (UIHelper.isOnUIThread()) {
                        UIHelper.runOnUIThreadDelay(new Runnable() {
                            @Override
                            public void run() {
                                connect(request, mHandler);
                            }
                        }, 3000 * (Request.RETRY_COUNT - request.retryCount));
                    } else {
                        Thread.sleep(3000 * (Request.RETRY_COUNT - request.retryCount));
                        connect(request, mHandler);
                    }
                    return;
                }
            } catch (Exception ex) {
                // 重试异常忽略掉，直接回调业务标识出错就好。
                HttpLog.e(TAG, ex);
            }
            if (mHandler != null) {
                mHandler.requestError(request, e);
            }
            mConnectStatus = RequestConnectStatus.IDLE;
        }
    }

    /***
     * 错误日志 可作为Http error错误上报入口
     * @param request
     * @param e
     */
    private void startReportErrorLog(final Request request, Exception e) {
        boolean needReport = request.needReport;
        if (!needReport) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        if (request.retryCount > 1) {
            builder.append("[http request failed by retry], retryNum is ").append((Request.RETRY_COUNT - request.retryCount)).append(", url == ").append(request.url);
        } else {
            builder.append("[http request failed], url == ").append(request.url);
        }
        builder.append("\\n ");
        Map<String, String> params = request.params;
        if (params != null && params.size() > 0) {
            builder.append("params is ");
            for (Map.Entry<String, String> entry : request.params.entrySet()) {
                if (entry.getValue() != null) {
                    builder.append(entry.getKey());
                    builder.append("=");
                    builder.append(entry.getValue());
                    builder.append("&");
                }
            }
            builder.append("\\n ");
        }
        if (e != null) {
            if (!TextUtils.isEmpty(e.toString())) {
                builder.append("Cause by: ").append(e.toString());
                builder.append("\\n ");
            }
            StackTraceElement[] element = e.getStackTrace();
            if (element != null) {
                for (StackTraceElement i : element) {
                    builder.append(i.toString());
                    builder.append("\\n ");
                }
            }
        }
    }
}
