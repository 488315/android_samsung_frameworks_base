package com.android.internal.org.bouncycastle.jce.provider;

import com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker;
import com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters;
import com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;

/* loaded from: classes5.dex */
class ProvCrlRevocationChecker implements PKIXCertRevocationChecker {
    private Date currentDate = null;
    private final JcaJceHelper helper;
    private PKIXCertRevocationCheckerParameters params;

    @Override // com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker
    public void setParameter(String str, Object obj) {
    }

    public ProvCrlRevocationChecker(JcaJceHelper jcaJceHelper) {
        this.helper = jcaJceHelper;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker
    public void initialize(PKIXCertRevocationCheckerParameters pKIXCertRevocationCheckerParameters) {
        this.params = pKIXCertRevocationCheckerParameters;
        this.currentDate = new Date();
    }

    public void init(boolean z) throws CertPathValidatorException {
        if (z) {
            throw new CertPathValidatorException("forward checking not supported");
        }
        this.params = null;
        this.currentDate = new Date();
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker
    public void check(Certificate certificate) throws CertPathValidatorException {
        try {
            PKIXCertRevocationCheckerParameters pKIXCertRevocationCheckerParameters = this.params;
            RFC3280CertPathUtilities.checkCRLs(pKIXCertRevocationCheckerParameters, pKIXCertRevocationCheckerParameters.getParamsPKIX(), this.currentDate, this.params.getValidDate(), (X509Certificate) certificate, this.params.getSigningCert(), this.params.getWorkingPublicKey(), this.params.getCertPath().getCertificates(), this.helper);
        } catch (AnnotatedException e) {
            throw new CertPathValidatorException(e.getMessage(), e.getCause() != null ? e.getCause() : e, this.params.getCertPath(), this.params.getIndex());
        }
    }
}
