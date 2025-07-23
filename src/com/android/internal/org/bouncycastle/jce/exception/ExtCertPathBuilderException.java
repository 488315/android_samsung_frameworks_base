package com.android.internal.org.bouncycastle.jce.exception;

import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;

/* loaded from: classes5.dex */
public class ExtCertPathBuilderException extends CertPathBuilderException implements ExtException {
    private Throwable cause;

    public ExtCertPathBuilderException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }

    public ExtCertPathBuilderException(String str, Throwable th, CertPath certPath, int i) {
        super(str, th);
        this.cause = th;
    }

    @Override // java.lang.Throwable, com.android.internal.org.bouncycastle.jce.exception.ExtException
    public Throwable getCause() {
        return this.cause;
    }
}
