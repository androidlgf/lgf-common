package com.cn.lgf.common.http.okhttp;

import android.content.Context;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.internal.Util;

public class OkHttpClientUtil {
    public static final int TIME_OUT = 20;
    private static OkHttpClient client;

    public static OkHttpClient getClient(Context context) {
        if (client == null) {
            synchronized (OkHttpClientUtil.class) {
                if (client == null) {
                    client = new OkHttpClient.Builder()
                            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                            .dispatcher(new Dispatcher(new ThreadPoolExecutor(0, 100, 60, TimeUnit.SECONDS,
                                    new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp Dispatcher", false))))
                            .build();
                }
            }
        }
        return client;
    }
}
