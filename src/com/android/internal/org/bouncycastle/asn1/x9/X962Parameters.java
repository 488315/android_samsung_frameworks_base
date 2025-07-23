package com.android.internal.org.bouncycastle.asn1.x9;

import com.android.internal.org.bouncycastle.asn1.ASN1Choice;
import com.android.internal.org.bouncycastle.asn1.ASN1Null;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;

/* loaded from: classes5.dex */
public class X962Parameters extends ASN1Object implements ASN1Choice {
    private ASN1Primitive params;

    public static X962Parameters getInstance(Object obj) {
        if (obj == null || (obj instanceof X962Parameters)) {
            return (X962Parameters) obj;
        }
        if (obj instanceof ASN1Primitive) {
            return new X962Parameters((ASN1Primitive) obj);
        }
        if (obj instanceof byte[]) {
            try {
                return new X962Parameters(ASN1Primitive.fromByteArray((byte[]) obj));
            } catch (Exception e) {
                throw new IllegalArgumentException("unable to parse encoded data: " + e.getMessage());
            }
        }
        throw new IllegalArgumentException("unknown object in getInstance()");
    }

    public static X962Parameters getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (!z) {
            throw new IllegalArgumentException("choice item must be explicitly tagged");
        }
        return getInstance(aSN1TaggedObject.getExplicitBaseObject());
    }

    public X962Parameters(X9ECParameters x9ECParameters) {
        this.params = null;
        this.params = x9ECParameters.toASN1Primitive();
    }

    public X962Parameters(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.params = aSN1ObjectIdentifier;
    }

    public X962Parameters(ASN1Null aSN1Null) {
        this.params = aSN1Null;
    }

    private X962Parameters(ASN1Primitive aSN1Primitive) {
        this.params = aSN1Primitive;
    }

    public boolean isNamedCurve() {
        return this.params instanceof ASN1ObjectIdentifier;
    }

    public boolean isImplicitlyCA() {
        return this.params instanceof ASN1Null;
    }

    public ASN1Primitive getParameters() {
        return this.params;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return this.params;
    }
}
