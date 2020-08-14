package com.cn.lgf.common.aspect.permission;

import android.text.TextUtils;
import com.cn.lgf.common.aspect.permission.permissiondispatcher.PermissionItem;

import java.io.Serializable;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.aspect.permission
 * @ClassName: CheckPermissionItem
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/27 4:19 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/27 4:19 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class CheckPermissionItem implements Serializable {
    public String classPath;
    public PermissionItem permissionItem;

    public CheckPermissionItem(String classPath, String... permissions) {
        if (TextUtils.isEmpty(classPath)) {
            throw new IllegalArgumentException("classPath must not be null or empty");
        }

        if (permissions == null || permissions.length <= 0) {
            throw new IllegalArgumentException("permissions must have one content at least");
        }
        permissionItem = new PermissionItem(permissions);
        this.classPath = classPath;
    }

    public CheckPermissionItem rationalMessage(String rationalMessage) {
        permissionItem.rationalMessage(rationalMessage);

        return this;
    }

    public CheckPermissionItem rationalButton(String rationalButton) {
        permissionItem.rationalButton(rationalButton);

        return this;
    }

    public CheckPermissionItem deniedMessage(String deniedMessage) {
        permissionItem.deniedMessage(deniedMessage);

        return this;
    }

    public CheckPermissionItem deniedButton(String deniedButton) {
        permissionItem.deniedButton(deniedButton);

        return this;
    }

    public CheckPermissionItem needGotoSetting(boolean needGotoSetting) {
        permissionItem.needGotoSetting(needGotoSetting);

        return this;
    }

    public CheckPermissionItem runIgnorePermission(boolean ignorePermission) {
        permissionItem.runIgnorePermission(ignorePermission);

        return this;
    }

    public CheckPermissionItem settingText(String settingText) {
        permissionItem.settingText(settingText);

        return this;
    }
}
