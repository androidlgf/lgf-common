package com.cn.lgf.common.http.priority;

import com.cn.lgf.common.http.priority.base.PriorityTask;

import java.util.Comparator;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.http.priority
 * @ClassName: PriorityTaskComparator
 * @Description: PriorityTaskComparator 优先级的无界阻塞队列自定义比较器
 * @Author: 作者名
 * @CreateDate: 2021/5/23 10:38 上午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2021/5/23 10:38 上午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class PriorityTaskComparator<Task> implements Comparator<Task> {

    @Override
    public int compare(Task task1, Task task2) {
        if ((task1 instanceof PriorityTask)
                && (task2 instanceof PriorityTask)) {
            PriorityTask priorityTask1 = (PriorityTask) task1;
            PriorityTask priorityTask2 = (PriorityTask) task2;
            //数字越大优先级越高
            return priorityTask2.getPriority().ordinal() - priorityTask1.getPriority().ordinal();
        }
        return 0;
    }
}
