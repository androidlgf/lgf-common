package com.cn.lgf.common.http.base;

import androidx.annotation.Nullable;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.http
 * @ClassName: IRequestHandler
 * @Description: IRequestHandler 请求回调
 * @Author: 作者名
 * @CreateDate: 2021/5/21 10:08 上午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2021/5/21 10:08 上午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface IRequestHandler {
    void requestFinish(Request request, IResponse response);

    void requestError(Request request, @Nullable Exception e);

    void startRequest(Request request);

    void requestProgress(Request request, int progress);
}
