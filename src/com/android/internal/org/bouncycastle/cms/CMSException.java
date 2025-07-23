package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes5.dex */
public class CMSException extends Exception {
    Exception e;

    public CMSException(String str) {
        super(str);
    }

    public CMSException(String str, Exception exc) {
        super(str);
        this.e = exc;
    }

    public Exception getUnderlyingException() {
        return this.e;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.e;
    }
}
