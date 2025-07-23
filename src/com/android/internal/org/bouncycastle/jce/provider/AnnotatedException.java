package com.android.internal.org.bouncycastle.jce.provider;

import com.android.internal.org.bouncycastle.jce.exception.ExtException;

/* loaded from: classes5.dex */
public class AnnotatedException extends Exception implements ExtException {
    private Throwable _underlyingException;

    public AnnotatedException(String str, Throwable th) {
        super(str);
        this._underlyingException = th;
    }

    public AnnotatedException(String str) {
        this(str, null);
    }

    Throwable getUnderlyingException() {
        return this._underlyingException;
    }

    @Override // java.lang.Throwable, com.android.internal.org.bouncycastle.jce.exception.ExtException
    public Throwable getCause() {
        return this._underlyingException;
    }
}
