package com.samsung.android.sdk.scs.base.tasks;

import com.samsung.android.sdk.scs.base.utils.Log;
import java.util.ArrayDeque;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class TaskStreamingListenersManager extends TaskListenersManager {
    @Override // com.samsung.android.sdk.scs.base.tasks.TaskListenersManager
    public final void processCompletion(Task task) {
        synchronized (this.mLock) {
            if (this.mQueue != null && !this.mIsProcessingCompletion) {
                this.mIsProcessingCompletion = true;
                Log.m266d("TaskStreamingListenersManager", "processCompletionStreaming: " + ((ArrayDeque) this.mQueue).size());
                Iterator it = ((ArrayDeque) this.mQueue).iterator();
                while (it.hasNext()) {
                    CompleteListenerCompletion completeListenerCompletion = (CompleteListenerCompletion) it.next();
                    synchronized (completeListenerCompletion.mLock) {
                        if (completeListenerCompletion.mListener != null) {
                            completeListenerCompletion.mExecutor.execute(new CompleteListenerRunnable(completeListenerCompletion, task));
                        }
                    }
                }
                this.mIsProcessingCompletion = false;
            }
        }
    }
}
