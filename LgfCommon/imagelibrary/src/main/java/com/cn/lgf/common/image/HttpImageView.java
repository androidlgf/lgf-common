package com.cn.lgf.common.image;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.cn.lgf.common.image.round.RoundedImageView;

import java.lang.ref.WeakReference;

public class HttpImageView extends RoundedImageView implements IImageLoader {
    //加载图片逻辑
    private IImageLoader mImageLoader;

    public HttpImageView(Context context) {
        this(context, null);
    }

    public HttpImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HttpImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取加载实现类
        mImageLoader = ImageLoaderFactory.getImageLoader();
    }

    @Override
    public void loadImage(WeakReference<? extends IImageView> imageView, ImageInfo imageInfo, @Nullable IImageLoaderResult result) {

    }

    @Override
    public void preloadImage(Context context, ImageInfo imageInfo, @Nullable IImageLoaderResult result) {

    }
}
