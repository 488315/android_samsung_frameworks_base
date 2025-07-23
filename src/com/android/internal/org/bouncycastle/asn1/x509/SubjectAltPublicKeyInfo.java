package com.android.internal.org.bouncycastle.asn1.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1BitString;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERSequence;

/* loaded from: classes5.dex */
public class SubjectAltPublicKeyInfo extends ASN1Object {
    private AlgorithmIdentifier algorithm;
    private ASN1BitString subjectAltPublicKey;

    public static SubjectAltPublicKeyInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static SubjectAltPublicKeyInfo getInstance(Object obj) {
        if (obj instanceof SubjectAltPublicKeyInfo) {
            return (SubjectAltPublicKeyInfo) obj;
        }
        if (obj != null) {
            return new SubjectAltPublicKeyInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static SubjectAltPublicKeyInfo fromExtensions(Extensions extensions) {
        return getInstance(Extensions.getExtensionParsedValue(extensions, Extension.subjectAltPublicKeyInfo));
    }

    private SubjectAltPublicKeyInfo(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new IllegalArgumentException("extension should contain only 2 elements");
        }
        this.algorithm = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        this.subjectAltPublicKey = ASN1BitString.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public SubjectAltPublicKeyInfo(AlgorithmIdentifier algorithmIdentifier, ASN1BitString aSN1BitString) {
        this.algorithm = algorithmIdentifier;
        this.subjectAltPublicKey = aSN1BitString;
    }

    public SubjectAltPublicKeyInfo(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.algorithm = subjectPublicKeyInfo.getAlgorithm();
        this.subjectAltPublicKey = subjectPublicKeyInfo.getPublicKeyData();
    }

    public AlgorithmIdentifier getAlgorithm() {
        return this.algorithm;
    }

    public ASN1BitString getSubjectAltPublicKey() {
        return this.subjectAltPublicKey;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.algorithm);
        aSN1EncodableVector.add(this.subjectAltPublicKey);
        return new DERSequence(aSN1EncodableVector);
    }
}
