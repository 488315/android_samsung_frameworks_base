package com.samsung.android.sdk.scs.base.tasks;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
