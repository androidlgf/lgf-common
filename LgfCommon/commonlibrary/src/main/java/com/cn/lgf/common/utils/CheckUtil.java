package com.cn.lgf.common.utils;

import androidx.annotation.Nullable;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.utils
 * @ClassName: CheckUtil
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/8/14 3:22 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/8/14 3:22 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class CheckUtil {
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }
}
