package com.cn.lgf.common.debug;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.mvp
 * @ClassName: DebugState
 * @Description: DebugState 开发环境状态
 * @Author: 作者名
 * @CreateDate: 2020/8/3 11:14 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/8/3 11:14 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class DebugState {
    /***
     * 开发环境列表
     * RELEASE 线上环境
     * PREVIEW 测试环境
     * DEBUG 开发环境
     */
    public enum EnvironmentState {
        RELEASE(0),
        PREVIEW(1),
        DEBUG(2);
        int environmentValue;

        EnvironmentState(int value) {
            environmentValue = value;
        }
    }

    //当前开发状态
    private static EnvironmentState sCurrentDebugStatus = EnvironmentState.RELEASE;

    /***
     * 修改环境
     * @param state
     */
    public static void changeEnvironmentState(EnvironmentState state) {
        if (sCurrentDebugStatus != state)
            sCurrentDebugStatus = state;
    }

    /***
     * 是否是线上环境
     * @return
     */
    public static boolean isRelease() {
        return sCurrentDebugStatus == EnvironmentState.RELEASE;
    }

    /***
     * 是否是测试环境
     * @return
     */
    public static boolean isPreView() {
        return sCurrentDebugStatus == EnvironmentState.PREVIEW;
    }

    /***
     * 是否是开发环境
     * @return
     */
    public static boolean isDebug() {
        return sCurrentDebugStatus == EnvironmentState.DEBUG;
    }

    /***
     * 获取当前项目环境
     * @return
     */
    public static EnvironmentState getCurrentEnvironmentState() {
        return sCurrentDebugStatus;
    }
}
