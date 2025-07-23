package com.android.internal.org.bouncycastle.its.asn1;

import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.DERSequence;

/* loaded from: classes5.dex */
public class AesCcmCiphertext extends ASN1Object {
    private final byte[] nonce;
    private final SequenceOfOctetString opaque;

    private AesCcmCiphertext(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new IllegalArgumentException("sequence not length 2");
        }
        this.nonce = Utils.octetStringFixed(ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets(), 12);
        this.opaque = SequenceOfOctetString.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public static AesCcmCiphertext getInstance(Object obj) {
        if (obj instanceof AesCcmCiphertext) {
            return (AesCcmCiphertext) obj;
        }
        if (obj != null) {
            return new AesCcmCiphertext(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new DEROctetString(this.nonce));
        aSN1EncodableVector.add(this.opaque);
        return new DERSequence(aSN1EncodableVector);
    }
}
