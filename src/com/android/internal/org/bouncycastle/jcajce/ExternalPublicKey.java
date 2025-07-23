package com.android.internal.org.bouncycastle.jcajce;

import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.bc.BCObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.bc.ExternalValue;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.GeneralName;
import com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.internal.org.bouncycastle.util.Arrays;
import java.io.IOException;
import java.security.PublicKey;

/* loaded from: classes5.dex */
public class ExternalPublicKey implements PublicKey {
    private final byte[] digest;
    private final AlgorithmIdentifier digestAlg;
    private final GeneralName location;

    public ExternalPublicKey(GeneralName generalName, AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        this.location = generalName;
        this.digestAlg = algorithmIdentifier;
        this.digest = Arrays.clone(bArr);
    }

    public ExternalPublicKey(ExternalValue externalValue) {
        this(externalValue.getLocation(), externalValue.getHashAlg(), externalValue.getHashValue());
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return "ExternalKey";
    }

    @Override // java.security.Key
    public String getFormat() {
        return "X.509";
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        try {
            return new SubjectPublicKeyInfo(new AlgorithmIdentifier(BCObjectIdentifiers.external_value), new ExternalValue(this.location, this.digestAlg, this.digest)).getEncoded(ASN1Encoding.DER);
        } catch (IOException e) {
            throw new IllegalStateException("unable to encode composite key: " + e.getMessage());
        }
    }
}
