package com.samsung.android.sdk.scs.base.tasks;

/* loaded from: classes4.dex */
public class TaskCompletionSource {
    public final TaskImpl task;

    public TaskCompletionSource() {
        this(new TaskImpl());
    }

    public final void setException(Exception exc) {
        TaskImpl taskImpl = this.task;
        taskImpl.getClass();
        synchronized (taskImpl.mLock) {
            if (taskImpl.mComplete) {
                throw new IllegalStateException("Task is already complete");
            }
            taskImpl.mComplete = true;
            taskImpl.mException = exc;
        }
        taskImpl.mListenersManager.processCompletion(taskImpl);
    }

    public final void setResult(Object obj) {
        this.task.setResult(obj);
    }

    public TaskCompletionSource(TaskImpl taskImpl) {
        this.task = taskImpl;
    }
}
