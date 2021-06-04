package com.cn.lgf.common.http.download;

import android.os.Build;
import android.text.TextUtils;

/**
 * 在质量相同的情况下，WebP格式图像的体积要比JPEG格式图像小40%
 * 阿里云、腾讯云、又拍云都提供了图片直接转换webp的相关服务。以阿里云为例，只要在图片后缀加上 ?x-oss-process=image/format,webp
 * https://www.alibabacloud.com/help/zh/doc-detail/44705.htm
 */
public class WebpConvert {
    //阿里云jpeg转webp后缀添加
    private static final String WEBP_SUFFIX_ALI = "?x-oss-process=image/format,webp";

    public static String convertWebp(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return url;
        }
        if (url.contains(".svga")) {
            return url;
        }

        if (url.contains("?")) {
            int index = url.indexOf("?");
            url = url.substring(0, index);
        }
        if (url.contains(".gif")) {
            return url;
        }
        return url;
    }
}
