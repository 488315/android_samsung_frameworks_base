package com.android.internal.org.bouncycastle.jce.provider;

import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;

/* loaded from: classes5.dex */
class RecoverableCertPathValidatorException extends CertPathValidatorException {
    public RecoverableCertPathValidatorException(String str, Throwable th, CertPath certPath, int i) {
        super(str, th, certPath, i);
    }
}
