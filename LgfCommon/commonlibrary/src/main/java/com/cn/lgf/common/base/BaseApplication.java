package com.cn.lgf.common.base;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import org.jetbrains.annotations.NotNull;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.base
 * @ClassName: BaseApplication
 * @Description: Application基类
 * @Author: 作者名
 * @CreateDate: 2020/7/31 10:08 上午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/31 10:08 上午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class BaseApplication extends Application implements ViewModelStoreOwner {

    private ViewModelStore mAppViewModelStore;
    private static BaseApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mAppViewModelStore = new ViewModelStore();
    }

    @NonNull
    @NotNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }
}
