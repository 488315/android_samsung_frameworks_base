package com.android.internal.org.bouncycastle.asn1.cms;

import com.android.internal.org.bouncycastle.asn1.ASN1Choice;
import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;

/* loaded from: classes5.dex */
public class SignerIdentifier extends ASN1Object implements ASN1Choice {
    private ASN1Encodable id;

    public SignerIdentifier(IssuerAndSerialNumber issuerAndSerialNumber) {
        this.id = issuerAndSerialNumber;
    }

    public SignerIdentifier(ASN1OctetString aSN1OctetString) {
        this.id = new DERTaggedObject(false, 0, (ASN1Encodable) aSN1OctetString);
    }

    public SignerIdentifier(ASN1Primitive aSN1Primitive) {
        this.id = aSN1Primitive;
    }

    public static SignerIdentifier getInstance(Object obj) {
        if (obj == null || (obj instanceof SignerIdentifier)) {
            return (SignerIdentifier) obj;
        }
        if (obj instanceof IssuerAndSerialNumber) {
            return new SignerIdentifier((IssuerAndSerialNumber) obj);
        }
        if (obj instanceof ASN1OctetString) {
            return new SignerIdentifier((ASN1OctetString) obj);
        }
        if (obj instanceof ASN1Primitive) {
            return new SignerIdentifier((ASN1Primitive) obj);
        }
        throw new IllegalArgumentException("Illegal object in SignerIdentifier: " + obj.getClass().getName());
    }

    public boolean isTagged() {
        return this.id instanceof ASN1TaggedObject;
    }

    public ASN1Encodable getId() {
        ASN1Encodable aSN1Encodable = this.id;
        return aSN1Encodable instanceof ASN1TaggedObject ? ASN1OctetString.getInstance((ASN1TaggedObject) aSN1Encodable, false) : aSN1Encodable;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return this.id.toASN1Primitive();
    }
}
