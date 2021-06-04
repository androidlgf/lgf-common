package com.cn.lgf.common.http.download;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.cn.lgf.common.http.debug.HttpLog;
import com.cn.lgf.common.http.utils.UIHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class QueueTaskRunner<Task extends QueueTaskRunner.ITask, Result> {
    /**
     * interface Task
     */
    public interface ITask {
        int getTaskId();

        boolean isComplete();
    }

    //是否已取消
    boolean mCanceled = false;
    //是否正在运行
    boolean mIsWorking = false;
    //成功Task集合
    List<Task> mSuccessTask;
    //失败Task集合
    List<Task> mFailedTask;
    //Task集合
    @NonNull
    private final Queue<Task> mTasks = new LinkedList<>();
    //Task 监听
    private TaskListener<Task, Result> mListener;
    //异步任务
    private AsyncTask<Void, Void, Void> mAsyncTask;

    /**
     * 获取Task集合
     *
     * @return
     */
    public Collection<Task> getAllTask() {
        return mTasks;
    }

    /**
     * 任务执行
     *
     * @param task
     * @return
     * @throws Throwable
     */
    @WorkerThread
    protected abstract Result execute(Task task) throws Throwable;

    /**
     * isWorking
     *
     * @return
     */
    public boolean isWorking() {
        return mIsWorking;
    }

    /**
     * 销毁
     */
    public void destroy() {
        if (!mCanceled && getAllTask().size() > 0) {
            cancel();
        }
    }

    /***
     * 取消
     * @return
     */
    public boolean cancel() {
        if (isWorking() && !mCanceled) {
            mCanceled = true;
            getAllTask().clear();
            if (mSuccessTask != null) {
                mSuccessTask.clear();
            }
            if (mFailedTask != null) {
                mFailedTask.clear();
            }
            if (mAsyncTask != null) {
                mAsyncTask.cancel(true);
            }
            return true;
        }
        return false;
    }

    /**
     * 执行任务集合
     *
     * @param tasks
     * @param listener
     * @return
     */
    public boolean startTasks(@NonNull final List<Task> tasks, final TaskListener<Task, Result> listener) {
        try {
            if (isWorking()) {
                if (UIHelper.isOnUIThread()) {
                    UIHelper.runOnUIThreadDelay(new Runnable() {
                        @Override
                        public void run() {
                            startTasks(tasks, listener);
                        }
                    }, 3000);
                } else {
                    Thread.sleep(3000);
                    startTasks(tasks, listener);
                }
                return false;
            }
            mListener = listener;
            return startInternal(tasks);
        } catch (Exception e) {
            HttpLog.e(QueueTaskRunner.class.getName(), e);
            return false;
        }
    }

    /***
     * 执行任务
     * @param task
     * @param listener
     * @return
     */
    public boolean startTask(@NonNull final Task task, final TaskListener<Task, Result> listener) {
        ArrayList<Task> list = new ArrayList<>();
        list.add(task);
        return startTasks(list, listener);
    }

    /***
     * 执行任务集合实现
     * @param tasks
     * @return
     */
    private boolean startInternal(@NonNull List<Task> tasks) {
        if (tasks.isEmpty()) {
            return false;
        }
        mSuccessTask = new ArrayList<>();
        mFailedTask = new ArrayList<>();
        mCanceled = false;
        mIsWorking = true;
        getAllTask().clear();
        getAllTask().addAll(tasks);
        if (UIHelper.isOnUIThread()) {
            mAsyncTask = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    execNextTask();
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    mAsyncTask = null;
                    mIsWorking = false;
                }
            };
            mAsyncTask.execute();
        } else {
            execNextTask();
            mAsyncTask = null;
            mIsWorking = false;
        }
        return true;
    }

    @WorkerThread
    protected void execNextTask() {
        if (mCanceled) return;
        if (getAllTask().isEmpty()) {
            onTasksComplete(mSuccessTask, mFailedTask);
        } else {
            Task task = getSingleTask();
            if (task != null && !task.isComplete()) {
                if (execTask(task)) {
                    mSuccessTask.add(task);
                } else {
                    mFailedTask.add(task);
                }
            }
            execNextTask();
        }
    }

    protected Task getSingleTask() {
        Collection<Task> collection = getAllTask();
        if (collection != null && collection instanceof Queue) {
            Queue<Task> queue = (Queue) getAllTask();
            return queue.poll();
        }
        return null;
    }

    @WorkerThread
    protected boolean execTask(Task task) {
        if (mCanceled) return false;
        Result result = null;
        Throwable throwable = null;
        onTaskStart(task);
        try {
            result = execute(task);
        } catch (Throwable e) {
            e.printStackTrace();
            throwable = e;
        }
        if (result != null) {
            onSingleTaskSuccess(task, result);
            return true;
        } else {
            onSingleTaskFailed(task, throwable);
            return false;
        }
    }

    void setProgress(final Task task, final int progress) {
        final TaskListener<Task, Result> listener = mListener;
        if (listener != null && !listener.isFinishing()) {
            UIHelper.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    listener.onTaskProgress(task, progress);
                }
            });
        }
    }

    void onTaskStart(final Task task) {
        final TaskListener<Task, Result> listener = mListener;
        if (listener != null && !listener.isFinishing()) {
            UIHelper.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    listener.onTaskStart(task);
                }
            });
        }
    }

    void onSingleTaskFailed(final Task task, final Throwable throwable) {
        final TaskListener<Task, Result> listener = mListener;
        if (listener != null && !listener.isFinishing()) {
            UIHelper.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    listener.onTaskFailed(task, throwable);
                }
            });
        }
    }

    void onSingleTaskSuccess(final Task task, final Result result) {
        final TaskListener<Task, Result> listener = mListener;
        if (listener != null && !listener.isFinishing()) {
            UIHelper.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    listener.onTaskSuccess(task, result);
                }
            });
        }
    }

    void onTasksComplete(final List<Task> successfulTasks, final List<Task> failedTasks) {
        final TaskListener<Task, Result> listener = mListener;
        if (listener != null && !listener.isFinishing()) {
            UIHelper.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    listener.onTasksComplete(successfulTasks, failedTasks);
                }
            });
        }
    }
}
