package com.cn.lgf.common.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

//Base ImageView
public interface IImageView {
    //设置本地Resource图片
    void setImageResource(@DrawableRes int resourceId);

    //设置Uri
    void setImageURI(@Nullable Uri uri);

    //设置Drawable
    void setImageDrawable(@Nullable Drawable drawable);

    //设置Bitmap
    void setImageBitmap(Bitmap bitmap);

    //设置图片显示方式
    void setScaleType(ImageView.ScaleType scaleType);

    //获取图片显示方式
    ImageView.ScaleType getScaleType();

    //设置LayoutParams大小
    void setLayoutParams(ViewGroup.LayoutParams params);

    //获取上下文
    Context getContext();

    //获取当前Image
    View getImageView();
}
