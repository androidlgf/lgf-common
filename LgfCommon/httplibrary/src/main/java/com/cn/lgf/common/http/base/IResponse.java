package com.cn.lgf.common.http.base;

import androidx.annotation.Nullable;

import java.io.InputStream;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.http
 * @ClassName: IResponse
 * @Description: IResponse 请求返回IResponse
 * @Author: 作者名
 * @CreateDate: 2021/5/21 10:08 上午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2021/5/21 10:08 上午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface IResponse {
    /**
     * 返回结果
     *
     * @return
     */
    @Nullable
    String getResult();

    /**
     * 返回结果
     *
     * @return
     */
    @Nullable
    InputStream getByteStream();

    /**
     * 返回长度
     *
     * @return
     */
    long getContentLength();

    /**
     * 请求是否成功
     *
     * @return
     */
    boolean isSuccess();

    /**
     * 请求Http code
     *
     * @return
     */
    int getHttpCode();
}
