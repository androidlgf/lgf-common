package com.cn.lgf.common.http;

import com.cn.lgf.common.http.base.IRequestConnect;

public class RegisterHttpManager {
    private static Class<? extends IRequestConnect> sConnectClass;

    public static Class<? extends IRequestConnect> getConnectLib() {
        return sConnectClass;
    }

    /**
     * 注册IRequestConnect实现类
     *
     * @param sConnectClass
     */
    public static void registerConnectLib(Class<? extends IRequestConnect> sConnectClass) {
        RegisterHttpManager.sConnectClass = sConnectClass;
    }
}
