package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

import com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints;
import com.android.internal.org.bouncycastle.asn1.x509.Certificate;
import com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper;
import java.security.cert.CertificateEncodingException;

/* loaded from: classes5.dex */
class X509CertificateInternal extends X509CertificateImpl {
    private final byte[] encoding;
    private final CertificateEncodingException exception;

    X509CertificateInternal(JcaJceHelper jcaJceHelper, Certificate certificate, BasicConstraints basicConstraints, boolean[] zArr, String str, byte[] bArr, byte[] bArr2, CertificateEncodingException certificateEncodingException) {
        super(jcaJceHelper, certificate, basicConstraints, zArr, str, bArr);
        this.encoding = bArr2;
        this.exception = certificateEncodingException;
    }

    @Override // java.security.cert.Certificate
    public byte[] getEncoded() throws CertificateEncodingException {
        CertificateEncodingException certificateEncodingException = this.exception;
        if (certificateEncodingException != null) {
            throw certificateEncodingException;
        }
        byte[] bArr = this.encoding;
        if (bArr != null) {
            return bArr;
        }
        throw new CertificateEncodingException();
    }
}
