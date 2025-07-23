package com.android.internal.org.bouncycastle.asn1.pkcs;

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
public class CRLBag extends ASN1Object {
    private ASN1ObjectIdentifier crlId;
    private ASN1Encodable crlValue;

    private CRLBag(ASN1Sequence aSN1Sequence) {
        this.crlId = ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        this.crlValue = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(1)).getExplicitBaseObject();
    }

    public static CRLBag getInstance(Object obj) {
        if (obj instanceof CRLBag) {
            return (CRLBag) obj;
        }
        if (obj != null) {
            return new CRLBag(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CRLBag(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.crlId = aSN1ObjectIdentifier;
        this.crlValue = aSN1Encodable;
    }

    public ASN1ObjectIdentifier getCrlId() {
        return this.crlId;
    }

    public ASN1Encodable getCrlValue() {
        return this.crlValue;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.crlId);
        aSN1EncodableVector.add(new DERTaggedObject(0, this.crlValue));
        return new DERSequence(aSN1EncodableVector);
    }
}
