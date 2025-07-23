package com.android.internal.org.bouncycastle.asn1.ocsp;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime;
import com.android.internal.org.bouncycastle.asn1.ASN1IA5String;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERIA5String;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;
import java.util.Enumeration;

/* loaded from: classes5.dex */
public class CrlID extends ASN1Object {
    private ASN1Integer crlNum;
    private ASN1GeneralizedTime crlTime;
    private ASN1IA5String crlUrl;

    private CrlID(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) objects.nextElement();
            int tagNo = aSN1TaggedObject.getTagNo();
            if (tagNo == 0) {
                this.crlUrl = ASN1IA5String.getInstance(aSN1TaggedObject, true);
            } else if (tagNo == 1) {
                this.crlNum = ASN1Integer.getInstance(aSN1TaggedObject, true);
            } else if (tagNo == 2) {
                this.crlTime = ASN1GeneralizedTime.getInstance(aSN1TaggedObject, true);
            } else {
                throw new IllegalArgumentException("unknown tag number: " + aSN1TaggedObject.getTagNo());
            }
        }
    }

    public static CrlID getInstance(Object obj) {
        if (obj instanceof CrlID) {
            return (CrlID) obj;
        }
        if (obj != null) {
            return new CrlID(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public DERIA5String getCrlUrl() {
        ASN1IA5String aSN1IA5String = this.crlUrl;
        if (aSN1IA5String == null || (aSN1IA5String instanceof DERIA5String)) {
            return (DERIA5String) aSN1IA5String;
        }
        return new DERIA5String(this.crlUrl.getString(), false);
    }

    public ASN1IA5String getCrlUrlIA5() {
        return this.crlUrl;
    }

    public ASN1Integer getCrlNum() {
        return this.crlNum;
    }

    public ASN1GeneralizedTime getCrlTime() {
        return this.crlTime;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        if (this.crlUrl != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, (ASN1Encodable) this.crlUrl));
        }
        if (this.crlNum != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 1, (ASN1Encodable) this.crlNum));
        }
        if (this.crlTime != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 2, (ASN1Encodable) this.crlTime));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
