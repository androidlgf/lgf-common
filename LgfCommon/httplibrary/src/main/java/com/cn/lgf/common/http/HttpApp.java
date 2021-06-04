package com.cn.lgf.common.http;

import android.content.Context;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.http
 * @ClassName: HttpApp
 * @Description: HttpApp 设置全局的Context Application
 * @Author: 作者名
 * @CreateDate: 2021/5/24 4:50 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2021/5/24 4:50 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class HttpApp {
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
