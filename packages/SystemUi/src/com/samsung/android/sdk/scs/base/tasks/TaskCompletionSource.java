package com.samsung.android.sdk.scs.base.tasks;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class TaskCompletionSource {
    public final TaskImpl task;

    public TaskCompletionSource() {
        this(new TaskImpl());
    }

    public final void setException(Exception exc) {
        TaskImpl taskImpl = this.task;
        synchronized (taskImpl.mLock) {
            if (!(!taskImpl.mComplete)) {
                throw new IllegalStateException("Task is already complete");
            }
            taskImpl.mComplete = true;
            taskImpl.mException = exc;
        }
        taskImpl.mListenersManager.processCompletion(taskImpl);
    }

    public TaskCompletionSource(TaskImpl taskImpl) {
        this.task = taskImpl;
    }
}
