package com.cn.lgf.common.aspect.permission.permissiondispatcher;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.aspect.permission.permissiondispatcher
 * @ClassName: PermissionListener
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/27 1:32 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/27 1:32 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface PermissionListener {
    public void permissionGranted();
    public void permissionDenied();
}
