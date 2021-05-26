package com.cn.lgf.common.http.utils;

import android.content.Context;

public class ApkVersionCodeHelper {
    /***
     * 获取本地APK版本CODE
     * @param context 上下文
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /***
     * 获取本地APK版本名
     * @param context 上下文
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "1.0.0";
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
