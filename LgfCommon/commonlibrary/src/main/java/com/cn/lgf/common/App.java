package com.cn.lgf.common;

import android.content.Context;

/**
 * 设置全局Context
 */
public class App {
    private static Context sAppContext = null;

    public static Context getContext() {
        return sAppContext;
    }

    public static void setContext(Context context) {
        if (sAppContext == null) {
            sAppContext = context.getApplicationContext();
        }
    }
}
