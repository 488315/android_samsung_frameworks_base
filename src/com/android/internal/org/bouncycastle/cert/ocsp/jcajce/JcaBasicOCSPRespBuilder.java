package com.android.internal.org.bouncycastle.cert.ocsp.jcajce;

import com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.internal.org.bouncycastle.cert.ocsp.BasicOCSPRespBuilder;
import com.android.internal.org.bouncycastle.cert.ocsp.OCSPException;
import com.android.internal.org.bouncycastle.operator.DigestCalculator;
import java.security.PublicKey;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes5.dex */
public class JcaBasicOCSPRespBuilder extends BasicOCSPRespBuilder {
    public JcaBasicOCSPRespBuilder(X500Principal x500Principal) {
        super(new JcaRespID(x500Principal));
    }

    public JcaBasicOCSPRespBuilder(PublicKey publicKey, DigestCalculator digestCalculator) throws OCSPException {
        super(SubjectPublicKeyInfo.getInstance(publicKey.getEncoded()), digestCalculator);
    }
}
