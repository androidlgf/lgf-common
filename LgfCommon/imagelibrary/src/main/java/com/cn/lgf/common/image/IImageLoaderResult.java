package com.cn.lgf.common.image;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * 图片加载回到方法
 */
public interface IImageLoaderResult {
    /**
     * 加载成功
     *
     * @param imageView 控件
     * @param url       图片Url
     * @param bitmap
     */
    void loadSuccess(@Nullable WeakReference<? extends IImageView> imageView, String url, @Nullable BitmapInfo bitmap);

    /**
     * 加载失败
     *
     * @param imageView
     * @param url
     * @param e         错误信息
     */
    void loadFailure(@Nullable WeakReference<? extends IImageView> imageView, String url, @Nullable Exception e);
}
