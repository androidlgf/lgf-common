package com.cn.lgf.common.image;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

public class BitmapInfo {
    private Bitmap mBitmap;
    private Drawable mDrawable;

    public BitmapInfo(@Nullable Bitmap bitmap, @Nullable Drawable drawable) {
        this.mBitmap = bitmap;
        this.mDrawable = drawable;
    }

    public BitmapInfo() {

    }

    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public void setDrawable(Drawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }
}
