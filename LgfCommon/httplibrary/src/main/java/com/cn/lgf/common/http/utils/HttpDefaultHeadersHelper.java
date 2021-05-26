package com.cn.lgf.common.http.utils;

import android.content.Context;
import android.media.audiofx.BassBoost;
import android.provider.Settings;

import androidx.annotation.NonNull;

import java.io.UnsupportedEncodingException;

import static java.net.URLEncoder.encode;

public class HttpDefaultHeadersHelper {
    /**
     * 获取userAgent
     *
     * @param context
     * @return
     */
    public static String getUserAgent(@NonNull Context context) {
        String model = null;
        try {
            model = encode(android.os.Build.MODEL, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return "android/"
                + (model != null ? (model + "/") : "")
                + android.os.Build.VERSION.SDK + "/"
                + android.os.Build.VERSION.RELEASE + "/"
                + (getPackageName(context) == null ? "" : getPackageName(context));
    }

    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(@NonNull Context context) {
        return context.getPackageName();
    }

    /**
     * 获取语言
     *
     * @param context
     * @return
     */
    public static String getLanguage(@NonNull Context context) {
        return context.getResources()
                .getConfiguration().locale.getCountry();
    }

    /**
     * 设备唯一的编号
     * Android ID 在设备首次启动时，系统会随机生成一个64位的数字，并把这个数字以16进制字符串的形式保存下来
     * 恢复出厂设置，重新生成
     * root手机，可以改写
     * @param context
     * @return
     */
    public static String getUdId(@NonNull Context context) {
        return Settings.System.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
