package com.cn.lgf.common.mvp;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.cn.lgf.common.debug.DebugLog;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.mvp
 *
 * @ClassName: BasePresenter
 * @Description: Lifecycle 属于 AppCompatActivity 或 Fragment
 * @Author: 作者名
 * @CreateDate: 2020/8/3 11:14 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/8/3 11:14 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class LifecyclePresenter<V extends MvpView> implements MvpPresenter<V>, LifecycleObserver {
    private static final String TAG = "LifecyclePresenter";
    private V mMvpView;
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        DebugLog.i(TAG, "onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        DebugLog.i(TAG, "onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        DebugLog.i(TAG, "onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        DebugLog.i(TAG, "onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        DebugLog.i(TAG, "onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        DebugLog.i(TAG, "onDestroy");
    }
    /**
     * 绑定View
     *
     * @param mvpView (mvpView一般指向Activity或Fragment等)
     */
    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    /***
     * 判断是否绑定View
     * @return false未绑定 true绑定
     */
    public boolean isViewAttached() {
        return mMvpView != null;
    }

    /***
     * 检查MvpView是否绑定
     */
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
