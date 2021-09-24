package com.cn.lgf.common.pub;

import android.content.Context;

import com.cn.lgf.common.App;
import com.cn.lgf.common.http.RegisterHttpManager;
import com.cn.lgf.common.http.okhttp.OkHttpHelper;

/***
 * pubLibrary入口
 */
public class PubLibrary {
    public static void initializeLibrary(Context context) {
        //设置全局Context
        App.setContext(context);
        //注册OkHttp3网络 rConnectLib
        RegisterHttpManager.registerConnectLib(OkHttpHelper.class);
    }
}
