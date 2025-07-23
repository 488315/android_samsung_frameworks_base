package com.android.internal.org.bouncycastle.asn1.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;

/* loaded from: classes5.dex */
public class OtherName extends ASN1Object {
    private final ASN1ObjectIdentifier typeID;
    private final ASN1Encodable value;

    public static OtherName getInstance(Object obj) {
        if (obj instanceof OtherName) {
            return (OtherName) obj;
        }
        if (obj != null) {
            return new OtherName(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public OtherName(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.typeID = aSN1ObjectIdentifier;
        this.value = aSN1Encodable;
    }

    private OtherName(ASN1Sequence aSN1Sequence) {
        this.typeID = ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        this.value = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(1)).getExplicitBaseObject();
    }

    public ASN1ObjectIdentifier getTypeID() {
        return this.typeID;
    }

    public ASN1Encodable getValue() {
        return this.value;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.typeID);
        aSN1EncodableVector.add(new DERTaggedObject(true, 0, this.value));
        return new DERSequence(aSN1EncodableVector);
    }
}
