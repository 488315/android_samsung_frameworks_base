package com.samsung.android.sdk.scs.base.tasks;

import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CompleteListenerCompletion {
    public final Executor mExecutor;
    public final OnCompleteListener mListener;
    public final Object mLock = new Object();

    public CompleteListenerCompletion(Executor executor, OnCompleteListener onCompleteListener) {
        this.mExecutor = executor;
        this.mListener = onCompleteListener;
    }
}
