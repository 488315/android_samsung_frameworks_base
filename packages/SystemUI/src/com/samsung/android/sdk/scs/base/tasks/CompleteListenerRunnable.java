package com.samsung.android.sdk.scs.base.tasks;

/* loaded from: classes4.dex */
public final class CompleteListenerRunnable implements Runnable {
    public CompleteListenerCompletion mCompletion;
    public final Task mTask;

    public CompleteListenerRunnable(CompleteListenerCompletion completeListenerCompletion, Task task) {
        this.mCompletion = completeListenerCompletion;
        this.mTask = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.mCompletion) {
            try {
                OnCompleteListener onCompleteListener = this.mCompletion.mListener;
                if (onCompleteListener != null) {
                    onCompleteListener.onComplete(this.mTask);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
