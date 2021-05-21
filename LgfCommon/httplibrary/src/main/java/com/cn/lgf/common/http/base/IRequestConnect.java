package com.cn.lgf.common.http.base;

import androidx.annotation.WorkerThread;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.http
 * @ClassName: IRequestConnect
 * @Description: IRequestConnect 请求Connect
 * @Author: 作者名
 * @CreateDate: 2021/5/21 10:08 上午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2021/5/21 10:08 上午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface IRequestConnect {
    /**
     * 异步链接请求
     */
    void connect(Request request, IRequestHandler handler);

    /***
     * 同步链接请求,不能在主线程中调用，只能在异步线程中，并且注意同步引起的性能问题
     * @param request
     * @return
     * @throws Exception
     */
    @WorkerThread
    IResponse syncConnect(Request request) throws Exception;

    /**
     * 根据requestID终止取消请求
     *
     * @param requestID
     * @return
     */
    boolean abort(int requestID);

    /**
     * 终止取消全部请求
     *
     * @return
     */
    boolean abortAll();
}
