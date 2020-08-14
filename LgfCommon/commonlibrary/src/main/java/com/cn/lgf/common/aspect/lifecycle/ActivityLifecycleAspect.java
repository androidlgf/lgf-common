package com.cn.lgf.common.aspect.lifecycle;

import android.util.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.aspect.lifecycle
 * @ClassName: ActivityLifecycleAspect
 * @Description: Aop Activity生命周期
 * @Author: 作者名
 * @CreateDate: 2020/7/26 2:37 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/26 2:37 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
@Aspect
public class ActivityLifecycleAspect {
    @After("execution(* android.app.Activity.onCreate(..))")
    public void onCreateCutPoint(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();

        Class<?> cls = signature.getDeclaringType();
        String methodName = signature.getName();
    }

    @After("execution(* android.app.Activity.onResume(..))")
    public void onResumeCutPoint() {
    }

    @After("execution(* android.app.Activity.onPause(..))")
    public void onPauseCutPoint() {

    }

    @After("execution(* android.app.Activity.onStart(..))")
    public void onStartCutPoint() {

    }

    @After("execution(* android.app.Activity.onStop(..))")
    public void onStopCutPoint() {

    }

    @After("execution(* android.app.Activity.onDestroy(..))")
    public void onDestroyCutPoint() {

    }
}
