package com.samsung.android.sdk.scs.base.tasks;

import com.samsung.android.sdk.scs.base.utils.Log;
import java.util.ArrayDeque;
import java.util.Iterator;

/* loaded from: classes4.dex */
public final class TaskStreamingListenersManager extends TaskListenersManager {
    @Override // com.samsung.android.sdk.scs.base.tasks.TaskListenersManager
    public final void processCompletion(Task task) {
        synchronized (this.mLock) {
            if (this.mQueue != null && !this.mIsProcessingCompletion) {
                this.mIsProcessingCompletion = true;
                Log.d("TaskStreamingListenersManager", "processCompletionStreaming: " + ((ArrayDeque) this.mQueue).size());
                Iterator it = ((ArrayDeque) this.mQueue).iterator();
                while (it.hasNext()) {
                    ((CompleteListenerCompletion) it.next()).onComplete(task);
                }
                this.mIsProcessingCompletion = false;
            }
        }
    }
}
