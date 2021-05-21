package com.cn.lgf.common.http.base;

import com.cn.lgf.common.http.priority.Priority;
import com.cn.lgf.common.http.utils.RequestIDHelper;

import java.util.Map;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.http
 * @ClassName: Request
 * @Description: Request 请求Request
 * @Author: 作者名
 * @CreateDate: 2021/5/21 10:08 上午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2021/5/21 10:08 上午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public abstract class Request {
    //默认重试次数
    public static final int RETRY_COUNT = 1;
    //请求优先级
    private Priority mPriority = Priority.NORMAL;
    //请求ID标识
    public int mRequestId;
    //请求URL
    public String mUrl;
    //请求参数
    public Map<String, String> mParams;
    //请求头
    public Map<String, String> mHeaders;
    //请求方式
    public RequestType mRequestType;
    //请求缓存类型
    public RequestCacheType mCacheType;
    //请求是否需要缓存
    public boolean needCache = false;
    //请求失败是否重试
    public boolean needRetry = true;
    //请求是否上报
    public boolean needReport = false;
    //重试次数
    public int mRetryCount = 0;
    //请求是否加密
    public boolean isEncrypted = false;
    //缓存key
    public String cacheKey = "";

    public Request() {
    }

    public Request(String url, RequestType method, RequestCacheType defaultCacheType, Map<String, String> headers, Map<String, String> params) {
        this.mUrl = url;
        this.mParams = params;
        this.mHeaders = headers;
        this.mRequestType = method;
        this.mCacheType = defaultCacheType;
        this.mRequestId = RequestIDHelper.INSTANCE.createRequestId();
    }

    /***
     * 设置Http Url
     * @param url
     */
    public void setUrl(String url) {
        this.mUrl = url;
    }

    /**
     * 设置Http params
     *
     * @param params
     */
    public void setParams(Map<String, String> params) {
        this.mParams = params;
    }

    /***
     * 设置Http params
     * @param headers
     */
    public void setHeaders(Map<String, String> headers) {
        this.mHeaders = headers;
    }

    /***
     * 设置Http 请求方式
     * @param requestType
     */
    public void setRequestType(RequestType requestType) {
        this.mRequestType = requestType;
    }

    /***
     * 设置Http 缓存类型
     * @param cacheType
     */
    public void setCacheType(RequestCacheType cacheType) {
        this.mCacheType = cacheType;
    }

    /***
     * 设置Http 是否缓存
     * @param needCache
     */
    public void needCache(boolean needCache) {
        this.needCache = needCache;
        if (needCache) {
            this.cacheKey = String.valueOf(System.currentTimeMillis());
        }
    }

    /***
     * 设置Http 是否重试
     * @param needRetry
     */
    public void needRetry(boolean needRetry) {
        this.needRetry = needRetry;
        this.mRetryCount = RETRY_COUNT;
    }

    /***
     * 设置Http 是否上报
     * @param needReport
     */
    public void needReport(boolean needReport) {
        this.needReport = needReport;
    }

    /***
     * 设置Http 是否加密
     * @param isEncrypted
     */
    public void needEncrypted(boolean isEncrypted) {
        this.isEncrypted = isEncrypted;
    }

    public Priority getPriority() {
        return mPriority;
    }

    public void setPriority(Priority priority) {
        this.mPriority = priority;
    }
}
