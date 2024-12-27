package com.android.internal.org.bouncycastle.util;

/* loaded from: classes5.dex */
public class StoreException extends RuntimeException {
    private Throwable _e;

    public StoreException(String msg, Throwable cause) {
        super(msg);
        this._e = cause;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this._e;
    }
}
