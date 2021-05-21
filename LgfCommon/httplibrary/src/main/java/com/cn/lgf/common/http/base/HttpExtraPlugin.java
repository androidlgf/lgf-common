package com.cn.lgf.common.http.base;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.http
 * @ClassName: HttpExtraPlugin
 * @Description: HttpExtraPlugin 请求成功code=200 判断业务是否成功插件
 * @Author: 作者名
 * @CreateDate: 2021/5/21 10:08 上午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2021/5/21 10:08 上午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public abstract class HttpExtraPlugin {
    //请求返回实体String
    private String mResult;

    /***
     * 判断业务是否成功 例httpCode=200 缺少参数导致返回不是需要数据
     * @return
     * @throws Exception
     */
    public abstract boolean isSuccess() throws Exception;

    /***
     * setResponse
     * @param result
     */
    public void setResponseStringResult(String result) {
        this.mResult = result;
    }

    /***
     * getResponse
     * @return
     */
    public String getResponseResult() {
        return mResult;
    }
}
