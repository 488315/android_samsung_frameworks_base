package com.android.internal.org.bouncycastle.its.asn1;

import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.DERSequence;

/* loaded from: classes5.dex */
public class GroupLinkageValue extends ASN1Object {
    private byte[] jValue;
    private byte[] value;

    private GroupLinkageValue(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new IllegalArgumentException("sequence not length 2");
        }
        this.jValue = Utils.octetStringFixed(ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets(), 4);
        this.value = Utils.octetStringFixed(ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets(), 9);
    }

    public static GroupLinkageValue getInstance(Object obj) {
        if (obj instanceof GroupLinkageValue) {
            return (GroupLinkageValue) obj;
        }
        if (obj != null) {
            return new GroupLinkageValue(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public byte[] getJValue() {
        return this.jValue;
    }

    public byte[] getValue() {
        return this.value;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new DEROctetString(this.jValue));
        aSN1EncodableVector.add(new DEROctetString(this.value));
        return new DERSequence(aSN1EncodableVector);
    }
}
