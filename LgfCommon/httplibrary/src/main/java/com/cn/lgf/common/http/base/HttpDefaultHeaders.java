package com.cn.lgf.common.http.base;
import com.cn.lgf.common.http.HttpApp;
import com.cn.lgf.common.http.utils.ApkVersionCodeHelper;
import com.cn.lgf.common.http.utils.HttpDefaultHeadersHelper;
import com.cn.lgf.common.http.utils.NetworkHelper;

import java.util.HashMap;

public enum HttpDefaultHeaders {
    INSTANCE;

    //操作系统和浏览器的名称和版本
    private static final String USER_AGENT = "user-agent";
    //操作系统语言
    private static final String LANGUAGE = "lang";
    //硬件设备唯一标识
    private static final String UD_ID = "UD_ID";
    //网络标识
    private static final String NETWORK = "NETWORK";
    //ip地址
    private static final String IP = "IP";
    //手机型号
    private static final String PHONE_MODEL = "PHONE_MODEL";
    //手机提供商
    private static final String PHONE_PROVIDER = "PHONE_PROVIDER";
    //APK version name
    private static final String APK_VERSION = "VERSION";
    //APK version code
    private static final String APK_VERSION_CODE = "VERSION_CODE";

    public HashMap<String, String> createHttpDefaultHeaders() {
        HashMap<String, String> defaultHeadersMap = new HashMap<>();
        defaultHeadersMap.put(USER_AGENT, HttpDefaultHeadersHelper.getUserAgent(HttpApp.getContext()));
        defaultHeadersMap.put(LANGUAGE, HttpDefaultHeadersHelper.getLanguage(HttpApp.getContext()));
        defaultHeadersMap.put(PHONE_MODEL, String.valueOf(android.os.Build.MODEL));
        defaultHeadersMap.put(PHONE_PROVIDER, String.valueOf(android.os.Build.BRAND));
        defaultHeadersMap.put(APK_VERSION, ApkVersionCodeHelper.getVersionName(HttpApp.getContext()));
        defaultHeadersMap.put(APK_VERSION_CODE, String.valueOf(ApkVersionCodeHelper.getVersionCode(HttpApp.getContext())));
        //可变
        defaultHeadersMap.put(NETWORK, NetworkHelper.getNetWorkName(HttpApp.getContext()));
        defaultHeadersMap.put(IP, NetworkHelper.getLocalIPAddress());
        return defaultHeadersMap;
    }

}
