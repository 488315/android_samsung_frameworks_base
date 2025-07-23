package com.android.internal.org.bouncycastle.asn1.ocsp;

import com.android.internal.org.bouncycastle.asn1.ASN1Choice;
import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;
import com.android.internal.org.bouncycastle.asn1.x500.X500Name;

/* loaded from: classes5.dex */
public class ResponderID extends ASN1Object implements ASN1Choice {
    private ASN1Encodable value;

    public ResponderID(ASN1OctetString aSN1OctetString) {
        this.value = aSN1OctetString;
    }

    public ResponderID(X500Name x500Name) {
        this.value = x500Name;
    }

    public static ResponderID getInstance(Object obj) {
        if (obj instanceof ResponderID) {
            return (ResponderID) obj;
        }
        if (obj instanceof DEROctetString) {
            return new ResponderID((DEROctetString) obj);
        }
        if (obj instanceof ASN1TaggedObject) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) obj;
            if (aSN1TaggedObject.getTagNo() == 1) {
                return new ResponderID(X500Name.getInstance(aSN1TaggedObject, true));
            }
            return new ResponderID(ASN1OctetString.getInstance(aSN1TaggedObject, true));
        }
        return new ResponderID(X500Name.getInstance(obj));
    }

    public static ResponderID getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (!z) {
            throw new IllegalArgumentException("choice item must be explicitly tagged");
        }
        return getInstance(aSN1TaggedObject.getExplicitBaseObject());
    }

    public byte[] getKeyHash() {
        ASN1Encodable aSN1Encodable = this.value;
        if (aSN1Encodable instanceof ASN1OctetString) {
            return ((ASN1OctetString) aSN1Encodable).getOctets();
        }
        return null;
    }

    public X500Name getName() {
        ASN1Encodable aSN1Encodable = this.value;
        if (aSN1Encodable instanceof ASN1OctetString) {
            return null;
        }
        return X500Name.getInstance(aSN1Encodable);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        if (this.value instanceof ASN1OctetString) {
            return new DERTaggedObject(true, 2, this.value);
        }
        return new DERTaggedObject(true, 1, this.value);
    }
}
