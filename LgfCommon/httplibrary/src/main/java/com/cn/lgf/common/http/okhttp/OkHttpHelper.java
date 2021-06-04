package com.cn.lgf.common.http.okhttp;

import android.util.SparseArray;
import com.cn.lgf.common.http.debug.HttpLog;
import com.cn.lgf.common.http.HttpApp;
import com.cn.lgf.common.http.base.IRequestConnect;
import com.cn.lgf.common.http.base.IRequestHandler;
import com.cn.lgf.common.http.base.IResponse;
import com.cn.lgf.common.http.base.Request;
import com.cn.lgf.common.http.base.RequestType;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/***
 * OkHttp 实现的Connect
 */
public class OkHttpHelper implements IRequestConnect {
    private static final String TAG = "OkHttpHelper";
    private OkHttpClient okHttpClient;
    private volatile SparseArray<Call> callSparseArray = new SparseArray<>();

    public OkHttpHelper() {
        okHttpClient = OkHttpClientUtil.getClient(HttpApp.getContext());
    }

    @Override
    public void connect(final Request request, final IRequestHandler handler) {
        okhttp3.Request.Builder builder;
        if (request.requestType == RequestType.GET) {
            builder = createOkHttpGetBuilder(request);
        } else if (request.requestType == RequestType.POST) {
            builder = createOkHttpPostBuilder(request);
        } else if (request.requestType == RequestType.PUT) {
            builder = createOkHttpPutBuilder(request);
        } else {
            builder = createOkHttpDeleteBuilder(request);
        }
        if (handler != null) {
            handler.startRequest(request);
        }
        Call call = okHttpClient.newCall(builder.build());
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                OKHttpResponse okHttpResponse = null;
                try {
                    if (response != null && !response.isSuccessful()) {
                        onFailure(call, new IOException("http error, error code is " + response.code()));
                        return;
                    }
                    okHttpResponse = new OKHttpResponse(response, request);
                    if (okHttpResponse.isParseError()) {
                        onFailure(call, new IOException("http data error, data is " + okHttpResponse.getResult()));
                        return;
                    }
                    if (handler != null) {
                        handler.requestFinish(request, okHttpResponse);
                    }
                    callSparseArray.remove(request.requestId);
                    HttpLog.i(TAG, "request end, url = " + request.url);
                } finally {
                    if (okHttpResponse != null) {
                        okHttpResponse.close();
                    }
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callSparseArray.remove(request.requestId);
                if (call.isCanceled()) {
                    return;
                }
                if (handler != null) {
                    handler.requestError(request, e);
                }
                HttpLog.i(TAG, "request error, url = " + request.url);
            }
        });
        callSparseArray.put(request.requestId, call);
    }

    @Override
    public IResponse syncConnect(Request request) throws Exception {
        okhttp3.Request.Builder builder;
        if (request.requestType == RequestType.GET) {
            builder = createOkHttpGetBuilder(request);
        } else if (request.requestType == RequestType.POST) {
            builder = createOkHttpPostBuilder(request);
        } else if (request.requestType == RequestType.PUT) {
            builder = createOkHttpPutBuilder(request);
        } else {
            builder = createOkHttpDeleteBuilder(request);
        }
        Response response = okHttpClient.newCall(builder.build()).execute();
        return new OKHttpResponse(response, request);
    }

    @Override
    public boolean abort(int requestId) {
        Call call = callSparseArray.get(requestId);
        if (call != null) {
            call.cancel();
            return true;
        }
        return false;
    }

    @Override
    public boolean abortAll() {
        if (okHttpClient != null) {
            okHttpClient.dispatcher().cancelAll();
            return true;
        }
        return false;
    }

    private okhttp3.Request.Builder createOkHttpGetBuilder(final Request request) {
        final okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        return addUrlAndHeaders(builder, request);
    }

    private okhttp3.Request.Builder createOkHttpPostBuilder(final Request request) {
        final okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        addUrlAndHeaders(builder, request);
        builder.post(createJsonBody(request));
        return builder;
    }

    private okhttp3.Request.Builder createOkHttpPutBuilder(final Request request) {
        final okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        addUrlAndHeaders(builder, request);
        builder.put(createFormBody(request));
        return builder;
    }

    private okhttp3.Request.Builder createOkHttpDeleteBuilder(final Request request) {
        final okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        addUrlAndHeaders(builder, request);
        builder.delete(createFormBody(request));
        return builder;
    }

    //根据请求参数构建form表单
    private FormBody createFormBody(Request request) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (request.params != null && request.params.size() > 0) {
            for (Map.Entry<String, String> entry : request.params.entrySet()) {
                if (entry.getValue() != null) {
                    formBuilder.add(entry.getKey(), entry.getValue());
                }
            }
        }
        return formBuilder.build();
    }

    //根据请求参数构建 Json Body
    private RequestBody createJsonBody(Request request) {
        String body = "";
        if (request.params != null && request.params.size() > 0) {
            body = new JSONObject(request.params).toString();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);
        return requestBody;
    }

    //添加http首部
    private okhttp3.Request.Builder addUrlAndHeaders(okhttp3.Request.Builder builder, Request request) {
        builder.url(request.url);
        if (request.headers != null && request.headers.size() > 0) {
            for (Map.Entry<String, String> entry : request.headers.entrySet()) {
                if (entry.getValue() != null) {
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
            }
        }
        return builder;
    }

    private class OKHttpResponse implements IResponse {
        private Response mResponse;
        private String mResult;
        private Exception mThrowable;

        OKHttpResponse(Response response, Request request) throws IOException {
            mResponse = response;
        }

        @Override
        public Map<String, List<String>> getResponseHeaders() {
            if (mResponse == null) {
                return null;
            }
            Headers headers = mResponse.headers();
            return headers == null ? null : headers.toMultimap();
        }

        @Override
        public InputStream getByteStream() {
            if (mResponse == null) {
                return null;
            }
            ResponseBody body = mResponse.body();
            if (body == null) {
                return null;
            }
            return body.byteStream();
        }

        @Override
        public long getContentLength() {
            if (mResponse == null) {
                return -1;
            }
            ResponseBody body = mResponse.body();
            if (body == null) {
                return -1;
            }
            return body.contentLength();
        }

        public boolean isParseError() {
            String string = getResult();
            return mThrowable != null;
        }

        @Override
        public boolean isSuccess() {
            return mResponse.body() != null && mResponse.isSuccessful();
        }

        @Override
        public int getHttpCode() {
            if (mResponse == null) {
                return 0;
            }
            return mResponse.code();
        }

        @Override
        public String getResult() {
            try {
                Object object = mResponse.body();
                if (object == null) {
                    return null;
                }
                if (mResult == null) {
                    ResponseBody body = (ResponseBody) object;
                    mResult = body.string();
                    mResult = mResult == null ? "" : mResult;
                }
                return mResult;
            } catch (Exception e) {
                HttpLog.i(OkHttpHelper.TAG, e);
                mThrowable = e;
            }
            return null;
        }

        public void close() {
            try {
                mResponse.close();
            } catch (Exception e) {
                HttpLog.e(OkHttpHelper.TAG, e);
            }
        }
    }
}
