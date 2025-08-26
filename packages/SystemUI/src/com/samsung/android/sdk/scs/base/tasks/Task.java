package com.samsung.android.sdk.scs.base.tasks;

/* loaded from: classes4.dex */
public abstract class Task {
    public Task addOnCompleteListener(OnCompleteListener onCompleteListener) {
        throw new UnsupportedOperationException("addOnCompleteListener is not implemented");
    }

    public abstract Exception getException();

    public abstract Object getResult();

    public abstract boolean isComplete();

    public abstract boolean isSuccessful();
}
