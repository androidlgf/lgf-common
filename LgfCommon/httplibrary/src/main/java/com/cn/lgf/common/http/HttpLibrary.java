package com.cn.lgf.common.http;

import android.content.Context;

import androidx.annotation.NonNull;

import com.cn.lgf.common.http.base.IRequestConnect;

public class HttpLibrary {
    private static volatile HttpLibrary INSTANCE;

    public static HttpLibrary getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpLibrary.class) {
                //double checking Singleton instance
                if (INSTANCE == null) {
                    INSTANCE = new HttpLibrary();
                }
            }
        }
        return INSTANCE;
    }

    public void init(@NonNull Context context, Class<? extends IRequestConnect> sConnectClass) {
        //设置Http Lib全局Context
        HttpApp.setContext(context.getApplicationContext());
        //设置Http 使用插件库 实现IRequestConnect自定义(传入null 默认实现HttpUrlConnection)
        RegisterHttpManager.registerConnectLib(sConnectClass);
    }
}
