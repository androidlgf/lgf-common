package com.cn.lgf.common.mvp;

import androidx.lifecycle.DefaultLifecycleObserver;
import com.cn.lgf.common.BuildConfig;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.mvp
 * @ClassName: BasePresenter
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/8/3 11:14 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/8/3 11:14 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {
    private static final String TAG = "BasePresenter";
    private V mMvpView;

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    @Override
    public void onDetach() {
        mMvpView = null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
