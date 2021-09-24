package com.cn.lgf.common.image;

import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;

/**
 * 图片信息属性
 */
public class ImageInfo {
    //图片加载Url
    private final String url;
    //加载图
    private final Drawable placeHolderImage;
    // 失败图
    private final Drawable failedImage;
    // 背景图
    private final Drawable backgroundImage;
    // 重试图
    private final Drawable retryImage;
    // 圆角大小
    private final int radius;
    //Draw Color
    @ColorInt
    private final int drawColor;
    //画笔颜色
    private final boolean needPaintColor;
    //宽
    private final int resizeWidth;
    //高
    private final int resizeHeight;
    //false
    private final boolean isLocalMedia;
    //Build构造器
    private Builder builder;

    public ImageInfo(Builder builder) {
        this.url = builder.url;
        this.placeHolderImage = builder.placeHolderImage;
        this.failedImage = builder.failedImage;
        this.backgroundImage = builder.backgroundImage;
        this.retryImage = builder.retryImage;
        this.radius = builder.radius;
        this.drawColor = builder.drawColor;
        this.needPaintColor = builder.needPaintColor;
        this.resizeWidth = builder.resizeWidth;
        this.resizeHeight = builder.resizeHeight;
        this.isLocalMedia = builder.isLocalMedia;
    }

    public static class Builder {
        private String url;
        private Drawable placeHolderImage;
        private Drawable failedImage;
        private Drawable backgroundImage;
        private Drawable retryImage;
        private int radius;
        @ColorInt
        private int drawColor;
        private boolean needPaintColor;
        private boolean isLocalMedia = false;
        private int resizeWidth;
        private int resizeHeight;

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setPlaceHolderImage(Drawable placeHolderImage) {
            this.placeHolderImage = placeHolderImage;
            return this;
        }

        public Builder setFailedImage(Drawable failedImage) {
            this.failedImage = failedImage;
            return this;
        }

        public Builder setBackgroundImage(Drawable backgroundImage) {
            this.backgroundImage = backgroundImage;
            return this;
        }

        public Builder setRetryImage(Drawable retryImage) {
            this.retryImage = retryImage;
            return this;
        }

        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder setDrawColor(int drawColor) {
            this.drawColor = drawColor;
            return this;
        }

        public Builder setNeedPaintColor(boolean needPaintColor) {
            this.needPaintColor = needPaintColor;
            return this;
        }

        public Builder setLocalMedia(boolean localMedia) {
            isLocalMedia = localMedia;
            return this;
        }

        public Builder setResizeWidth(int resizeWidth) {
            this.resizeWidth = resizeWidth;
            return this;
        }

        public Builder setResizeHeight(int resizeHeight) {
            this.resizeHeight = resizeHeight;
            return this;
        }

        public ImageInfo build() {
            return new ImageInfo(this);
        }
    }
}
