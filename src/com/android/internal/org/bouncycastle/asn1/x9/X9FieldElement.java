package com.android.internal.org.bouncycastle.asn1.x9;

import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;

/* loaded from: classes5.dex */
public class X9FieldElement extends ASN1Object {
    private static X9IntegerConverter converter = new X9IntegerConverter();
    protected ECFieldElement f;

    public X9FieldElement(ECFieldElement eCFieldElement) {
        this.f = eCFieldElement;
    }

    public ECFieldElement getValue() {
        return this.f;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DEROctetString(converter.integerToBytes(this.f.toBigInteger(), converter.getByteLength(this.f)));
    }
}
