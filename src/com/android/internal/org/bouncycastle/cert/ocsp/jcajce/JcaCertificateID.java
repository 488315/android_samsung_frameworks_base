package com.android.internal.org.bouncycastle.cert.ocsp.jcajce;

import com.android.internal.org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import com.android.internal.org.bouncycastle.cert.ocsp.CertificateID;
import com.android.internal.org.bouncycastle.cert.ocsp.OCSPException;
import com.android.internal.org.bouncycastle.operator.DigestCalculator;
import java.math.BigInteger;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

/* loaded from: classes5.dex */
public class JcaCertificateID extends CertificateID {
    public JcaCertificateID(DigestCalculator digestCalculator, X509Certificate x509Certificate, BigInteger bigInteger) throws OCSPException, CertificateEncodingException {
        super(digestCalculator, new JcaX509CertificateHolder(x509Certificate), bigInteger);
    }
}
