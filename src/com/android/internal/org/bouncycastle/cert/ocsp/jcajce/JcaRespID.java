package com.android.internal.org.bouncycastle.cert.ocsp.jcajce;

import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.internal.org.bouncycastle.cert.ocsp.OCSPException;
import com.android.internal.org.bouncycastle.cert.ocsp.RespID;
import com.android.internal.org.bouncycastle.operator.DigestCalculator;
import java.security.PublicKey;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes5.dex */
public class JcaRespID extends RespID {
    public JcaRespID(X500Principal x500Principal) {
        super(X500Name.getInstance(x500Principal.getEncoded()));
    }

    public JcaRespID(PublicKey publicKey, DigestCalculator digestCalculator) throws OCSPException {
        super(SubjectPublicKeyInfo.getInstance(publicKey.getEncoded()), digestCalculator);
    }
}
