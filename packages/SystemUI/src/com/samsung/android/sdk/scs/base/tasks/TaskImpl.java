package com.samsung.android.sdk.scs.base.tasks;

import com.samsung.android.sdk.scs.base.tasks.TaskExecutors;
import java.util.ArrayDeque;
import java.util.UUID;

/* loaded from: classes4.dex */
public class TaskImpl extends Task {
    public boolean mComplete;
    public Exception mException;
    public final TaskListenersManager mListenersManager;
    public final Object mLock;
    public Object mResult;

    public TaskImpl() {
        this(new TaskListenersManager());
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.Task
    public final Task addOnCompleteListener(OnCompleteListener onCompleteListener) {
        TaskExecutors.MainExecutor mainExecutor = TaskExecutors.MAIN_THREAD;
        TaskListenersManager taskListenersManager = this.mListenersManager;
        CompleteListenerCompletion completeListenerCompletion = new CompleteListenerCompletion(mainExecutor, onCompleteListener);
        synchronized (taskListenersManager.mLock) {
            try {
                if (taskListenersManager.mQueue == null) {
                    taskListenersManager.mQueue = new ArrayDeque();
                }
                ((ArrayDeque) taskListenersManager.mQueue).add(completeListenerCompletion);
            } finally {
            }
        }
        synchronized (this.mLock) {
            try {
                if (!this.mComplete) {
                    return this;
                }
                this.mListenersManager.processCompletion(this);
                return this;
            } finally {
            }
        }
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.Task
    public final Exception getException() {
        Exception exc;
        synchronized (this.mLock) {
            exc = this.mException;
        }
        return exc;
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.Task
    public Object getResult() {
        Object obj;
        synchronized (this.mLock) {
            try {
                if (!this.mComplete) {
                    throw new IllegalStateException("Task is not yet complete");
                }
                if (this.mException != null) {
                    throw new RuntimeException(this.mException);
                }
                obj = this.mResult;
            } catch (Throwable th) {
                throw th;
            }
        }
        return obj;
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.Task
    public final boolean isComplete() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mComplete;
        }
        return z;
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.Task
    public boolean isSuccessful() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = this.mComplete && this.mException == null;
            } finally {
            }
        }
        return z;
    }

    public void setResult(Object obj) {
        synchronized (this.mLock) {
            if (this.mComplete) {
                throw new IllegalStateException("Task is already complete");
            }
            this.mComplete = true;
            this.mResult = obj;
        }
        this.mListenersManager.processCompletion(this);
    }

    public TaskImpl(TaskListenersManager taskListenersManager) {
        this.mLock = new Object();
        this.mListenersManager = taskListenersManager;
        UUID.randomUUID().toString();
    }
}
