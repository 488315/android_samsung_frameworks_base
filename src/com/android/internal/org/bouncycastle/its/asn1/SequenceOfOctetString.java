package com.android.internal.org.bouncycastle.its.asn1;

import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.util.Arrays;

/* loaded from: classes5.dex */
public class SequenceOfOctetString extends ASN1Object {
    private byte[][] octetStrings;

    private SequenceOfOctetString(ASN1Sequence aSN1Sequence) {
        this.octetStrings = toByteArrays(aSN1Sequence);
    }

    public static SequenceOfOctetString getInstance(Object obj) {
        if (obj instanceof SequenceOfOctetString) {
            return (SequenceOfOctetString) obj;
        }
        if (obj != null) {
            return new SequenceOfOctetString(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public int size() {
        return this.octetStrings.length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (int i = 0; i != this.octetStrings.length; i++) {
            aSN1EncodableVector.add(new DEROctetString(Arrays.clone(this.octetStrings[i])));
        }
        return new DERSequence(aSN1EncodableVector);
    }

    static byte[][] toByteArrays(ASN1Sequence aSN1Sequence) {
        byte[][] bArr = new byte[aSN1Sequence.size()][];
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            bArr[i] = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(i)).getOctets();
        }
        return bArr;
    }
}
