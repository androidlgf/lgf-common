package com.cn.lgf.common.image;

import android.content.Context;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * 图片加载Loader基类
 */
public interface IImageLoader {
    /**
     * 加载图片
     *
     * @param imageView
     * @param imageInfo
     * @param result    加载回调
     */
    void loadImage(WeakReference<? extends IImageView> imageView, ImageInfo imageInfo, @Nullable IImageLoaderResult result);

    /**
     * 预加载图片
     *
     * @param context
     * @param imageInfo
     * @param result
     */
    void preloadImage(Context context, ImageInfo imageInfo, @Nullable IImageLoaderResult result);
}
