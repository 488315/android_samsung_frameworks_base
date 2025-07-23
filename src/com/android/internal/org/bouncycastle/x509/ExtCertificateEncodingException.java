package com.android.internal.org.bouncycastle.x509;

import java.security.cert.CertificateEncodingException;

/* loaded from: classes5.dex */
class ExtCertificateEncodingException extends CertificateEncodingException {
    Throwable cause;

    ExtCertificateEncodingException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }
}
