package com.android.internal.org.bouncycastle.asn1.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.util.Arrays;
import java.util.Enumeration;

/* loaded from: classes5.dex */
public class DigestInfo extends ASN1Object {
    private AlgorithmIdentifier algId;
    private byte[] digest;

    public static DigestInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static DigestInfo getInstance(Object obj) {
        if (obj instanceof DigestInfo) {
            return (DigestInfo) obj;
        }
        if (obj != null) {
            return new DigestInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public DigestInfo(AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        this.digest = Arrays.clone(bArr);
        this.algId = algorithmIdentifier;
    }

    public DigestInfo(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.algId = AlgorithmIdentifier.getInstance(objects.nextElement());
        this.digest = ASN1OctetString.getInstance(objects.nextElement()).getOctets();
    }

    public AlgorithmIdentifier getAlgorithmId() {
        return this.algId;
    }

    public byte[] getDigest() {
        return Arrays.clone(this.digest);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.algId);
        aSN1EncodableVector.add(new DEROctetString(this.digest));
        return new DERSequence(aSN1EncodableVector);
    }
}
