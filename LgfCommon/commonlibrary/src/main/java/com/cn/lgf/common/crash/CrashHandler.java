package com.cn.lgf.common.crash;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.cn.lgf.common.crash.entity.CrashEntity;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.crash
 * @ClassName: CrashHandler
 * @Description: 拦截异常信息
 * @Author: 作者名
 * @CreateDate: 2020/8/12 3:46 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/8/12 3:46 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class CrashHandler extends SimpleActivityLifecycleCallbacks implements Thread.UncaughtExceptionHandler {
    private static final String TAG = CrashHandler.class.getSimpleName();

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private List<Activity> mActivities = new ArrayList<>();
    private Application mApplication;

    private CrashHandler() {
    }

    private static class CrashHandlerHolder {
        public static CrashHandler instance = new CrashHandler();
    }

    public static CrashHandler getInstance() {
        return CrashHandlerHolder.instance;
    }

    @Override
    public void onActivityCreated(@NonNull @NotNull Activity activity, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(activity, savedInstanceState);
        mActivities.add(activity);
    }

    @Override
    public void onActivityDestroyed(@NonNull @NotNull Activity activity) {
        super.onActivityDestroyed(activity);
        mActivities.remove(activity);
    }

    public void init(Application application) {
        this.mApplication = application;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        application.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void uncaughtException(@NonNull @NotNull Thread t, @NonNull @NotNull Throwable e) {
        if (!handleException(t, e) && null != mDefaultHandler) {
            mDefaultHandler.uncaughtException(t, e);
        } else {
            try {
                Thread.sleep(500);
            } catch (InterruptedException pE) {
                pE.printStackTrace();
            }
            exit();
        }
    }

    private void exit() {
        exitAllActivity();
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    private void exitAllActivity() {
        for (Activity vActivity : mActivities) {
            vActivity.finish();
        }
    }

    private boolean handleException(@NonNull @NotNull Thread t, @NonNull @NotNull Throwable e) {
        if (e == null) {
            return false;
        }
        String crashInfo = collectCrashInfo(t, e);
        String deviceInfo = collectionDeviceInfo();
        CrashEntity entity = new CrashEntity();
        entity.createTime = new Date();
        entity.timestamp = System.currentTimeMillis();
        entity.deviceInfo = deviceInfo;
        entity.crashInfo = crashInfo;
        CrashDataBase.getInstance(this.mApplication).crashDao().insertCrash(entity);
        return true;
    }

    /***
     * 获取设备信息
     * @return 设备信息
     */
    private String collectionDeviceInfo() {
        Field[] vFields = Build.class.getDeclaredFields();
        StringBuilder vBuilder = new StringBuilder();
        for (Field vField : vFields) {
            try {
                vField.setAccessible(true);
                vBuilder.append(vField.getName()).append(" : ").append(vField.get(null)).append("\n");
            } catch (IllegalAccessException pE) {
                pE.printStackTrace();
            }
        }
        vBuilder.append("SDK : ").append(Build.VERSION.SDK_INT).append("\n");
        vBuilder.append("RELEASE : ").append(Build.VERSION.RELEASE).append("\n");
        return vBuilder.toString();
    }

    /***
     * 获取错误类信息
     * @param t
     * @param e
     * @return
     */
    private String collectCrashInfo(Thread t, Throwable e) {
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        int length = stackTraceElements.length;
        StringBuilder builder = new StringBuilder((length + 1) * 2);
        builder.append(e.toString()).append("\n");
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            builder.append(stackTraceElement.toString()).append("\n");
        }
        return builder.toString();
    }
}
