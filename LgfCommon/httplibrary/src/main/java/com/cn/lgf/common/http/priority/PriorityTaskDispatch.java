package com.cn.lgf.common.http.priority;

import com.cn.lgf.common.http.priority.base.PriorityTask;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PriorityTaskDispatch {
    //Java线程池
    private ExecutorService mExecutorService;
    //队列
    private final Deque<PriorityTask> mRunningAsyncCalls = new ArrayDeque<>();

    public PriorityTaskDispatch() {
        //优先级的无界阻塞队列
        PriorityBlockingQueue<Runnable> priorityBlockingQueue =
                new PriorityBlockingQueue<>
                        (60, new PriorityTaskComparator<Runnable>());
        //线程池
        mExecutorService = new ThreadPoolExecutor(
                1,
                1,
                20,
                TimeUnit.SECONDS,
                priorityBlockingQueue,
                threadFactory("Priority Dispatcher", false));
    }

    public PriorityTaskDispatch(ExecutorService executorService) {
        mExecutorService = executorService;
    }

    //加入Task队列
    public void enqueue(PriorityTask priorityTask) {
        mRunningAsyncCalls.add(priorityTask);
        mExecutorService.execute(priorityTask);
    }

    public static ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread result = new Thread(runnable, name);
                result.setDaemon(daemon);
                return result;
            }
        };
    }

    public List<PriorityTask> runningPriorityTasks() {
        List<PriorityTask> result = new ArrayList<>();
        for (PriorityTask priorityTask : mRunningAsyncCalls) {
            result.add(priorityTask);
        }
        return Collections.unmodifiableList(result);
    }

    //取消Task
    public void cancel(PriorityTask priorityTask) {
        for (PriorityTask task : mRunningAsyncCalls) {
            if (task.getTaskId() == priorityTask.getTaskId()) {
                priorityTask.cancelTask();
                mRunningAsyncCalls.remove(task);
                break;
            }
        }
    }

    public void cancelAll() {
        if (mRunningAsyncCalls.isEmpty()) {
            return;
        }
        for (PriorityTask task : mRunningAsyncCalls) {
            task.cancelTask();
        }
        mRunningAsyncCalls.clear();
        try {
            if (mExecutorService.isShutdown()) {
                mExecutorService.shutdownNow();
            }
        } catch (SecurityException e) {
        }
    }

    public void finished(PriorityTask priorityTask) {
        synchronized (this) {
            if (!mRunningAsyncCalls.remove(priorityTask)) {
                return;
            }
        }
        if (mRunningAsyncCalls.isEmpty()) {
        }
    }
}
