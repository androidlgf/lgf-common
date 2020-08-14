package com.cn.lgf.common.crash;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.crash
 * @ClassName: SimpleActivityLifecycleCallbacks
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/8/13 2:33 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/8/13 2:33 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class SimpleActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(@NonNull @NotNull Activity activity, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull @NotNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull @NotNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull @NotNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull @NotNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull @NotNull Activity activity, @NonNull @NotNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull @NotNull Activity activity) {

    }
}
