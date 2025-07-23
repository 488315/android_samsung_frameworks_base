package com.android.internal.org.bouncycastle.asn1.ocsp;

import com.android.internal.org.bouncycastle.asn1.ASN1Choice;
import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1Null;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.ASN1Util;
import com.android.internal.org.bouncycastle.asn1.DERNull;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;

/* loaded from: classes5.dex */
public class CertStatus extends ASN1Object implements ASN1Choice {
    private int tagNo;
    private ASN1Encodable value;

    public CertStatus() {
        this.tagNo = 0;
        this.value = DERNull.INSTANCE;
    }

    public CertStatus(RevokedInfo revokedInfo) {
        this.tagNo = 1;
        this.value = revokedInfo;
    }

    public CertStatus(int i, ASN1Encodable aSN1Encodable) {
        this.tagNo = i;
        this.value = aSN1Encodable;
    }

    private CertStatus(ASN1TaggedObject aSN1TaggedObject) {
        int tagNo = aSN1TaggedObject.getTagNo();
        if (tagNo == 0) {
            this.value = ASN1Null.getInstance(aSN1TaggedObject, false);
        } else if (tagNo == 1) {
            this.value = RevokedInfo.getInstance(aSN1TaggedObject, false);
        } else if (tagNo == 2) {
            this.value = ASN1Null.getInstance(aSN1TaggedObject, false);
        } else {
            throw new IllegalArgumentException("Unknown tag encountered: " + ASN1Util.getTagText(aSN1TaggedObject));
        }
        this.tagNo = tagNo;
    }

    public static CertStatus getInstance(Object obj) {
        if (obj == null || (obj instanceof CertStatus)) {
            return (CertStatus) obj;
        }
        if (obj instanceof ASN1TaggedObject) {
            return new CertStatus((ASN1TaggedObject) obj);
        }
        throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public static CertStatus getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(aSN1TaggedObject.getExplicitBaseTagged());
    }

    public int getTagNo() {
        return this.tagNo;
    }

    public ASN1Encodable getStatus() {
        return this.value;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(false, this.tagNo, this.value);
    }
}
