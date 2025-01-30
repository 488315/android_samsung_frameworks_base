package com.samsung.android.sdk.scs.base.tasks;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CompleteListenerRunnable implements Runnable {
    public final CompleteListenerCompletion mCompletion;
    public final Task mTask;

    public CompleteListenerRunnable(CompleteListenerCompletion completeListenerCompletion, Task task) {
        this.mCompletion = completeListenerCompletion;
        this.mTask = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.mCompletion) {
            OnCompleteListener onCompleteListener = this.mCompletion.mListener;
            if (onCompleteListener != null) {
                onCompleteListener.onComplete(this.mTask);
            }
        }
    }
}
