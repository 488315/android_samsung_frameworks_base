package com.android.internal.org.bouncycastle.asn1.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;

/* loaded from: classes5.dex */
public class AltSignatureAlgorithm extends ASN1Object {
    private final AlgorithmIdentifier algorithm;

    public static AltSignatureAlgorithm getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(AlgorithmIdentifier.getInstance(aSN1TaggedObject, z));
    }

    public static AltSignatureAlgorithm getInstance(Object obj) {
        if (obj instanceof AltSignatureAlgorithm) {
            return (AltSignatureAlgorithm) obj;
        }
        if (obj != null) {
            return new AltSignatureAlgorithm(AlgorithmIdentifier.getInstance(obj));
        }
        return null;
    }

    public static AltSignatureAlgorithm fromExtensions(Extensions extensions) {
        return getInstance(Extensions.getExtensionParsedValue(extensions, Extension.altSignatureAlgorithm));
    }

    public AltSignatureAlgorithm(AlgorithmIdentifier algorithmIdentifier) {
        this.algorithm = algorithmIdentifier;
    }

    public AltSignatureAlgorithm(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this(aSN1ObjectIdentifier, null);
    }

    public AltSignatureAlgorithm(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.algorithm = new AlgorithmIdentifier(aSN1ObjectIdentifier, aSN1Encodable);
    }

    public AlgorithmIdentifier getAlgorithm() {
        return this.algorithm;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return this.algorithm.toASN1Primitive();
    }
}
