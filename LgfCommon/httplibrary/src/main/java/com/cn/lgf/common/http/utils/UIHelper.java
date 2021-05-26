package com.cn.lgf.common.http.utils;

import android.net.VpnService;
import android.os.Handler;
import android.os.Looper;

public class UIHelper {
    //标识
    private static final String TAG = "UIHelper";
    //UI线程Handler
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    /**
     * 返回UI Handler
     *
     * @return
     */
    public static Handler getHandler() {
        return sHandler;
    }

    /**
     * 是否主线程
     *
     * @return
     */
    public static boolean isOnUIThread() {
        return Thread.currentThread() == getUIThread();
    }

    public static Thread getUIThread() {
        return Looper.getMainLooper().getThread();
    }

    /***
     * 保证在UI线程Run
     * @param action
     */
    public static void runOnUIThread(Runnable action) {
        if (!isOnUIThread()) {
            getHandler().post(action);
        } else {
            action.run();
        }
    }

    /***
     * 保证在UI线程Run
     * @param action
     * @param delayMillis
     */
    public static void runOnUIThreadDelay(Runnable action, long delayMillis) {
        getHandler().postDelayed(action, delayMillis);
    }
}
