package com.cn.lgf.common.pub;

import com.cn.lgf.common.http.RegisterHttpManager;
import com.cn.lgf.common.http.base.IRequestConnect;
import com.cn.lgf.common.image.IImageLoader;
import com.cn.lgf.common.image.ImageRegisterLibsManager;

/**
 * 引用库注册管理类
 */
public class RegisterLibsManager {
    public static Class<? extends IImageLoader> getImageLoaderLib() {
        return ImageRegisterLibsManager.getImageLoaderLib();
    }

    public static void registerImageLoaderLib(Class<? extends IImageLoader> sImageLoaderClass) {
        ImageRegisterLibsManager.registerImageLoaderLib(sImageLoaderClass);
    }

    public static Class<? extends IRequestConnect> getConnectLib() {
        return RegisterHttpManager.getConnectLib();
    }

    public static void registerConnectLib(Class<? extends IRequestConnect> sConnectClass) {
        RegisterHttpManager.registerConnectLib(sConnectClass);
    }
}
