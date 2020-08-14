package com.cn.lgf.common.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.view.View;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.utils
 * @ClassName: ContextUtils
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/27 4:40 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/27 4:40 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ContextUtils {
    public static Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Application) {
            return (Application) object;
        } else if (object instanceof Fragment) {
            android.app.Fragment fragment = (android.app.Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;
    }
}
