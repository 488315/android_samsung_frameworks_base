package com.android.internal.org.bouncycastle.operator;

/* loaded from: classes5.dex */
public class OperatorException extends Exception {
    private Throwable cause;

    public OperatorException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }

    public OperatorException(String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }
}
