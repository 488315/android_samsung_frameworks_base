package com.samsung.android.sdk.scs.base.tasks;

import java.util.ArrayDeque;
import java.util.Queue;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class TaskListenersManager {
    public boolean mIsProcessingCompletion;
    public final Object mLock = new Object();
    public Queue mQueue;

    public void processCompletion(Task task) {
        CompleteListenerCompletion completeListenerCompletion;
        synchronized (this.mLock) {
            if (this.mQueue != null && !this.mIsProcessingCompletion) {
                this.mIsProcessingCompletion = true;
                while (true) {
                    synchronized (this.mLock) {
                        completeListenerCompletion = (CompleteListenerCompletion) ((ArrayDeque) this.mQueue).poll();
                        if (completeListenerCompletion == null) {
                            this.mIsProcessingCompletion = false;
                            return;
                        }
                    }
                    synchronized (completeListenerCompletion.mLock) {
                        if (completeListenerCompletion.mListener != null) {
                            completeListenerCompletion.mExecutor.execute(new CompleteListenerRunnable(completeListenerCompletion, task));
                        }
                    }
                }
            }
        }
    }
}
