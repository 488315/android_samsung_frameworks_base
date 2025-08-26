package com.samsung.android.sdk.scs.base.tasks;

import com.samsung.android.sdk.scs.base.utils.Log;

/* loaded from: classes4.dex */
public class TaskStreamingImpl extends TaskImpl implements Cloneable {
    public TaskStreamingImpl() {
        super(new TaskStreamingListenersManager());
    }

    public final Object clone() {
        return super.clone();
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskImpl, com.samsung.android.sdk.scs.base.tasks.Task
    public final Object getResult() {
        Object obj;
        synchronized (this.mLock) {
            try {
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

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskImpl, com.samsung.android.sdk.scs.base.tasks.Task
    public final boolean isSuccessful() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mException == null;
        }
        return z;
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskImpl
    public final void setResult(Object obj) {
        synchronized (this.mLock) {
            this.mResult = obj;
        }
        try {
            this.mListenersManager.processCompletion((Task) super.clone());
        } catch (Exception e) {
            Log.i("ScsApi@TaskStreamingImpl", "setResult, e : " + e.getMessage());
            this.mListenersManager.processCompletion(this);
        }
    }
}
