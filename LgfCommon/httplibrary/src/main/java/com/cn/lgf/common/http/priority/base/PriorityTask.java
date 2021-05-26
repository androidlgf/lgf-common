package com.cn.lgf.common.http.priority.base;

import androidx.annotation.NonNull;

import com.cn.lgf.common.http.priority.Priority;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.http.priority
 * @ClassName: PriorityTask
 * @Description: PriorityTask 自定义Runnable Task
 * @Author: 作者名
 * @CreateDate: 2021/5/23 10:38 上午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2021/5/23 10:38 上午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public abstract class PriorityTask implements Runnable, Comparable<PriorityTask> {
    //Task优先级
    @NonNull
    private Priority mPriority = Priority.NORMAL;
    private boolean isCanceled = false;

    public PriorityTask(Priority priority) {
        setPriority(priority);
    }

    public PriorityTask() {

    }

    /***
     * 设置Task优先级
     * @return Task优先级
     */
    @NonNull
    public Priority getPriority() {
        return mPriority;
    }

    /***
     * 获取Task优先级
     * @return Task优先级
     */
    public void setPriority(@NonNull Priority mPriority) {
        this.mPriority = mPriority;
    }

    /***
     * Task是否取消
     * @return boolean true取消
     */
    public boolean isCanceled() {
        return isCanceled;
    }

    /***
     * 设置是否取消Task
     */
    public void cancel(boolean canceled) {
        isCanceled = canceled;
    }

    /***
     * 取消Task
     */
    public void cancelTask() {
        isCanceled = true;
    }

    @Override
    public void run() {
        if (isCanceled) {
            return;
        }
        execute();
    }

    /***
     * Task执行
     */
    public abstract void execute();

    /***
     * TaskId 网络标识
     * @return
     */
    public abstract int getTaskId();

    /***
     * 自然排序
     * @param priorityTask
     * @return 如果返回值为正数，则表示当前对象(调用该方法的对象)比 obj 对象“大”；反之“小”；如果为零的话，则表示两对象相等
     */
    @Override
    public int compareTo(@NonNull PriorityTask priorityTask) {
        if (mPriority.ordinal() < priorityTask.getPriority().ordinal()) {
            return 1;
        } else if (mPriority.ordinal() > priorityTask.getPriority().ordinal()) {
            return -1;
        } else {
            return 0;
        }
    }
}
