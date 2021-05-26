package com.cn.lgf.common.http;

import android.content.Context;

import com.cn.lgf.common.http.base.IRequestConnect;
import com.cn.lgf.common.http.base.RequestConnect;
import com.cn.lgf.common.http.urlconnection.HttpUrlConnectionHelper;

import java.lang.reflect.Constructor;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.http
 * @ClassName: RequestFactory
 * @Description: RequestFactory RequestConnect工厂 传递类必须必须实现IRequestConnect
 * @Author: 作者名
 * @CreateDate: 2021/5/23 2:32 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2021/5/23 2:32 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class RequestFactory {
    public static RequestConnect initConnect() {
        return new RequestConnect(createConnect());
    }

    /**
     * 获取注册的IRequestConnect
     *
     * @return
     */
    private static IRequestConnect createConnect() {
        Class<? extends IRequestConnect> cls = RegisterHttpManager.getConnectLib();
        if (cls == null) {
            return new HttpUrlConnectionHelper();
        }

        IRequestConnect requestConnect = null;
        try {
            Constructor constructor = cls.getDeclaredConstructor(/*Context.class*/);
            requestConnect = (IRequestConnect) constructor.newInstance(new Object[]{});
        } catch (Exception e) {

        }
        if (requestConnect == null) {
            requestConnect = new HttpUrlConnectionHelper();
        }
        return requestConnect;
    }
}
