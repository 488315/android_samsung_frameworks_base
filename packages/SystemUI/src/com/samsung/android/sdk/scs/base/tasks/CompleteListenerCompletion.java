package com.samsung.android.sdk.scs.base.tasks;

import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class CompleteListenerCompletion {
    public final Executor mExecutor;
    public final OnCompleteListener mListener;
    public final Object mLock = new Object();

    public CompleteListenerCompletion(Executor executor, OnCompleteListener onCompleteListener) {
        this.mExecutor = executor;
        this.mListener = onCompleteListener;
    }

    public final void onComplete(Task task) {
        synchronized (this.mLock) {
            try {
                if (this.mListener == null) {
                    return;
                }
                this.mExecutor.execute(new CompleteListenerRunnable(this, task));
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
