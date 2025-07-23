package com.android.internal.org.bouncycastle.jce.provider;

import com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker;
import com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.PKIXCertPathChecker;

/* loaded from: classes5.dex */
class WrappedRevocationChecker implements PKIXCertRevocationChecker {
    private final PKIXCertPathChecker checker;

    @Override // com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker
    public void setParameter(String str, Object obj) {
    }

    public WrappedRevocationChecker(PKIXCertPathChecker pKIXCertPathChecker) {
        this.checker = pKIXCertPathChecker;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker
    public void initialize(PKIXCertRevocationCheckerParameters pKIXCertRevocationCheckerParameters) throws CertPathValidatorException {
        this.checker.init(false);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker
    public void check(Certificate certificate) throws CertPathValidatorException {
        this.checker.check(certificate);
    }
}
