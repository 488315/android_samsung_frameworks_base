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
public class CertBag extends ASN1Object {
    private ASN1ObjectIdentifier certId;
    private ASN1Encodable certValue;

    private CertBag(ASN1Sequence aSN1Sequence) {
        this.certId = ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        this.certValue = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(1)).getExplicitBaseObject();
    }

    public static CertBag getInstance(Object obj) {
        if (obj instanceof CertBag) {
            return (CertBag) obj;
        }
        if (obj != null) {
            return new CertBag(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CertBag(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.certId = aSN1ObjectIdentifier;
        this.certValue = aSN1Encodable;
    }

    public ASN1ObjectIdentifier getCertId() {
        return this.certId;
    }

    public ASN1Encodable getCertValue() {
        return this.certValue;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.certId);
        aSN1EncodableVector.add(new DERTaggedObject(0, this.certValue));
        return new DERSequence(aSN1EncodableVector);
    }
}
