package com.cn.lgf.common.http;

import android.text.TextUtils;

import com.cn.lgf.common.http.base.HttpExtraPlugin;
import com.cn.lgf.common.http.interf.Method;

import org.json.JSONObject;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.http
 * @ClassName: HttpStatusPlugin
 * @Description: HttpStatusPlugin Http code==200情况 业务拦截校验
 * @Author: 作者名
 * @CreateDate: 2021/5/24 5:50 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2021/5/24 5:50 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class HttpStatusPlugin extends HttpExtraPlugin implements Method<HttpExtraPlugin> {
    private static final String HTTP_TAG_STATUS = "status";

    @Override
    public boolean isSuccess() throws Exception {
        String json = getResponseResult();
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        JSONObject objJson = new JSONObject(json);
        if (!objJson.has(HTTP_TAG_STATUS)) {
            return false;
        }
        int status = objJson.optInt(HTTP_TAG_STATUS);
        return status == 0;
    }

    @Override
    public HttpExtraPlugin call() {
        return this;
    }
}
