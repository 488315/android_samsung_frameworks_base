package com.android.internal.org.bouncycastle.pkcs;

/* loaded from: classes5.dex */
public class PKCSException extends Exception {
    private Throwable cause;

    public PKCSException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }

    public PKCSException(String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }
}
