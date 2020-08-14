package com.cn.lgf.common.mvp;

import androidx.annotation.StringRes;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.base
 * @ClassName: MvpView
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/8/3 11:35 上午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/8/3 11:35 上午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface MvpView {
    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();
}
