package com.cn.lgf.common.aspect.permission.permissiondispatcher;

import java.io.Serializable;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.aspect.permission.permissiondispatcher
 * @ClassName: PermissionItem
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/27 1:31 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/27 1:31 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class PermissionItem implements Serializable {
    public String[] permissions;
    public String rationalMessage;
    public String rationalButton;
    public String deniedMessage;
    public String deniedButton;
    public String settingText;
    public boolean needGotoSetting;
    public boolean runIgnorePermission;

    public PermissionItem(String... permissions) {
        if (permissions == null || permissions.length <= 0) {
            throw new IllegalArgumentException("permissions must have one content at least");
        }

        this.permissions = permissions;
    }

    public PermissionItem rationalMessage(String rationalMessage) {
        this.rationalMessage = rationalMessage;

        return this;
    }

    public PermissionItem rationalButton(String rationalButton) {
        this.rationalButton = rationalButton;

        return this;
    }

    public PermissionItem deniedMessage(String deniedMessage) {
        this.deniedMessage = deniedMessage;

        return this;
    }

    public PermissionItem deniedButton(String deniedButton) {
        this.deniedButton = deniedButton;

        return this;
    }

    public PermissionItem needGotoSetting(boolean needGotoSetting) {
        this.needGotoSetting = needGotoSetting;

        return this;
    }

    public PermissionItem runIgnorePermission(boolean ignorePermission) {
        this.runIgnorePermission = ignorePermission;

        return this;
    }

    public PermissionItem settingText(String settingText) {
        this.settingText = settingText;
        return this;
    }
}
