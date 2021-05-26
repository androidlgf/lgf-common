package com.cn.lgf.common.http.priority;

import com.cn.lgf.common.http.priority.base.PriorityTask;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.http.priority
 * @ClassName: PriorityTaskCallback
 * @Description: PriorityTaskCallback 任务监听
 * @Author: 作者名
 * @CreateDate: 2021/5/23 10:38 上午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2021/5/23 10:38 上午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface PriorityTaskCallback {
    //任务执行
    void execute(PriorityTask task);
}
