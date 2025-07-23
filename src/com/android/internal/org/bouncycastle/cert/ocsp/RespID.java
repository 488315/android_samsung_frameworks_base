package com.android.internal.org.bouncycastle.cert.ocsp;

import com.android.internal.org.bouncycastle.asn1.DERNull;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.ocsp.ResponderID;
import com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.internal.org.bouncycastle.operator.DigestCalculator;
import java.io.OutputStream;

/* loaded from: classes5.dex */
public class RespID {
    public static final AlgorithmIdentifier HASH_SHA1 = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
    ResponderID id;

    public RespID(ResponderID responderID) {
        this.id = responderID;
    }

    public RespID(X500Name x500Name) {
        this.id = new ResponderID(x500Name);
    }

    public RespID(SubjectPublicKeyInfo subjectPublicKeyInfo, DigestCalculator digestCalculator) throws OCSPException {
        try {
            if (!digestCalculator.getAlgorithmIdentifier().equals(HASH_SHA1)) {
                throw new IllegalArgumentException("only SHA-1 can be used with RespID - found: " + digestCalculator.getAlgorithmIdentifier().getAlgorithm());
            }
            OutputStream outputStream = digestCalculator.getOutputStream();
            outputStream.write(subjectPublicKeyInfo.getPublicKeyData().getBytes());
            outputStream.close();
            this.id = new ResponderID(new DEROctetString(digestCalculator.getDigest()));
        } catch (Exception e) {
            throw new OCSPException("problem creating ID: " + e, e);
        }
    }

    public ResponderID toASN1Primitive() {
        return this.id;
    }

    public boolean equals(Object obj) {
        if (obj instanceof RespID) {
            return this.id.equals(((RespID) obj).id);
        }
        return false;
    }

    public int hashCode() {
        return this.id.hashCode();
    }
}
