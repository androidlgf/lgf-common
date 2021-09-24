package com.cn.lgf.common.image;

import com.cn.lgf.common.debug.DebugLog;

import java.lang.ref.WeakReference;

public class ImageLoaderFactory {
    private static WeakReference<IImageLoader> sImageLoaderReference;

    public static IImageLoader getImageLoader() {
        try {
            IImageLoader imageLoader;
            if (sImageLoaderReference == null) {
                imageLoader = ImageRegisterLibsManager.getImageLoaderLib().newInstance();
                sImageLoaderReference = new WeakReference<>(imageLoader);
            } else {
                imageLoader = sImageLoaderReference.get();
                if (imageLoader == null) {
                    if (ImageRegisterLibsManager.getImageLoaderLib() != null) {
                        imageLoader = ImageRegisterLibsManager.getImageLoaderLib().newInstance();
                        sImageLoaderReference = new WeakReference<>(imageLoader);
                    }
                }
            }
            return imageLoader;
        } catch (Exception e) {
            DebugLog.e(ImageLoaderFactory.class.getName(), e);
        }
        return null;
    }
}
