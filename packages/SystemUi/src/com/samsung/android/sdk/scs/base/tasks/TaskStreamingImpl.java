package com.samsung.android.sdk.scs.base.tasks;

import com.samsung.android.sdk.scs.base.utils.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class TaskStreamingImpl extends TaskImpl implements Cloneable {
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
            if (this.mException != null) {
                throw new RuntimeException(this.mException);
            }
            obj = this.mResult;
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
            Log.m268i("ScsApi@TaskStreamingImpl", "setResult, e : " + e.getMessage());
            this.mListenersManager.processCompletion(this);
        }
    }
}
