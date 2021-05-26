package com.cn.lgf.common.http;

import com.cn.lgf.common.http.base.Request;
import com.cn.lgf.common.http.base.RequestCacheType;
import com.cn.lgf.common.http.base.RequestType;

import java.io.InputStream;
import java.util.Map;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.http
 * @ClassName: HttpRequest
 * @Description: HttpRequest 请求HttpRequest
 * @Author: 作者名
 * @CreateDate: 2021/5/23 2:32 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2021/5/23 2:32 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class HttpRequest extends Request {
    private HttpRequest(String url, RequestType method, InputStream input,
                        RequestCacheType defaultCacheType,
                        Map<String, String> headers, Map<String, String> params) {
        super(url, method, input, defaultCacheType, headers, params);
    }

    /***
     * HTTP GET
     * @param url
     * @param headers
     * @param params
     * @param defaultCacheType
     * @return
     */
    public static HttpRequest get(String url, Map<String, String> headers, Map<String, String> params,
                                  RequestCacheType defaultCacheType) {

        return new HttpRequest(url, RequestType.GET, null, defaultCacheType, headers, params);
    }

    /***
     * HTTP GET
     * @param url
     * @param params
     * @param defaultCacheType
     * @return
     */
    public static HttpRequest get(String url, Map<String, String> params, RequestCacheType defaultCacheType) {
        return get(url, null, params, defaultCacheType);
    }

    /***
     * HTTP GET
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public static HttpRequest get(String url, Map<String, String> headers, Map<String, String> params) {
        return get(url, headers, params, RequestCacheType.DEFAULT);
    }

    /***
     * HTTP GET
     * @param url
     * @param params
     * @return
     */
    public static HttpRequest get(String url, Map<String, String> params) {
        return get(url, null, params, RequestCacheType.DEFAULT);
    }

    /***
     * HTTP GET
     * @param url
     * @param defaultCacheType
     * @return
     */
    public static HttpRequest get(String url, RequestCacheType defaultCacheType) {
        return get(url, null, null, defaultCacheType);
    }

    /***
     * HTTP GET
     * @param url
     * @return
     */
    public static HttpRequest get(String url) {
        return get(url, RequestCacheType.DEFAULT);
    }

    /***
     * HTTP POST
     * @param url
     * @param headers
     * @param params
     * @param defaultCacheType
     * @return
     */
    public static HttpRequest post(String url, Map<String, String> headers, Map<String, String> params,
                                   RequestCacheType defaultCacheType) {
        return new HttpRequest(url, RequestType.POST, null, defaultCacheType, headers, params);
    }

    /***
     * HTTP POST
     * @param url
     * @param params
     * @param defaultCacheType
     * @return
     */
    public static HttpRequest post(String url, Map<String, String> params, RequestCacheType defaultCacheType) {
        return post(url, null, params, defaultCacheType);
    }

    /***
     * HTTP POST
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public static HttpRequest post(String url, Map<String, String> headers, Map<String, String> params) {
        return post(url, headers, params, RequestCacheType.DEFAULT);
    }

    /**
     * HTTP POST
     *
     * @param url
     * @param params
     * @return
     */
    public static HttpRequest post(String url, Map<String, String> params) {
        return post(url, null, params, RequestCacheType.DEFAULT);
    }

    /***
     * HTTP PUT
     * @param url
     * @param headers
     * @param params
     * @param defaultCacheType
     * @return
     */
    public static HttpRequest put(String url, Map<String, String> headers, Map<String, String> params, RequestCacheType defaultCacheType) {
        return new HttpRequest(url, RequestType.PUT, null, defaultCacheType, headers, params);
    }

    /***
     * HTTP PUT
     * @param url
     * @param params
     * @param defaultCacheType
     * @return
     */
    public static HttpRequest put(String url, Map<String, String> params, RequestCacheType defaultCacheType) {
        return put(url, null, params, defaultCacheType);
    }

    /***
     * HTTP PUT
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public static HttpRequest put(String url, Map<String, String> headers, Map<String, String> params) {
        return put(url, headers, params, RequestCacheType.DEFAULT);
    }

    /***
     * HTTP PUT
     * @param url
     * @param params
     * @return
     */
    public static HttpRequest put(String url, Map<String, String> params) {
        return put(url, null, params, RequestCacheType.DEFAULT);
    }

    /***
     * HTTP DELETE
     * @param url
     * @param headers
     * @param params
     * @param defaultCacheType
     * @return
     */
    public static HttpRequest delete(String url, Map<String, String> headers, Map<String, String> params,
                                     RequestCacheType defaultCacheType) {
        return new HttpRequest(url, RequestType.DELETE, null, defaultCacheType, headers, params);
    }

    /***
     * HTTP DELETE
     * @param url
     * @param params
     * @param defaultCacheType
     * @return
     */
    public static HttpRequest delete(String url, Map<String, String> params, RequestCacheType defaultCacheType) {
        return delete(url, null, params, defaultCacheType);
    }

    /**
     * HTTP DELETE
     *
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public static HttpRequest delete(String url, Map<String, String> headers, Map<String, String> params) {
        return delete(url, headers, params, RequestCacheType.DEFAULT);
    }

    /***
     * HTTP DELETE
     * @param url
     * @param params
     * @return
     */
    public static HttpRequest delete(String url, Map<String, String> params) {
        return delete(url, null, params, RequestCacheType.DEFAULT);
    }
}
