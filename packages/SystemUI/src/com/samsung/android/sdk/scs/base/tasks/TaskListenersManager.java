package com.samsung.android.sdk.scs.base.tasks;

import java.util.ArrayDeque;
import java.util.Queue;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
                        try {
                            completeListenerCompletion = (CompleteListenerCompletion) ((ArrayDeque) this.mQueue).poll();
                            if (completeListenerCompletion == null) {
                                this.mIsProcessingCompletion = false;
                                return;
                            }
                        } finally {
                        }
                    }
                    completeListenerCompletion.onComplete(task);
                }
            }
        }
    }
}
