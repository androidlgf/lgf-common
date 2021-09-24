package com.cn.lgf.common.image;

public class ImageRegisterLibsManager {
    private static Class<? extends IImageLoader> sImageLoaderClass;

    public static Class<? extends IImageLoader> getImageLoaderLib() {
        return sImageLoaderClass;
    }

    public static void registerImageLoaderLib(Class<? extends IImageLoader> sImageLoaderClass) {
        ImageRegisterLibsManager.sImageLoaderClass = sImageLoaderClass;
    }
}
