package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

import com.android.internal.org.bouncycastle.asn1.x509.CertificateList;
import com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper;

import java.security.cert.CRLException;

/* loaded from: classes5.dex */
class X509CRLInternal extends X509CRLImpl {
    private final byte[] encoding;

    X509CRLInternal(
            JcaJceHelper bcHelper,
            CertificateList c,
            String sigAlgName,
            byte[] sigAlgParams,
            boolean isIndirect,
            byte[] encoding) {
        super(bcHelper, c, sigAlgName, sigAlgParams, isIndirect);
        this.encoding = encoding;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLImpl,
              // java.security.cert.X509CRL
    public byte[] getEncoded() throws CRLException {
        if (this.encoding == null) {
            throw new CRLException();
        }
        return this.encoding;
    }
}
