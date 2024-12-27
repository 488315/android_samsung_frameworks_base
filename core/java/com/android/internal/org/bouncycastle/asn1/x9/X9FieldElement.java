package com.android.internal.org.bouncycastle.asn1.x9;

import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;

import java.math.BigInteger;

public class X9FieldElement extends ASN1Object {
    private static X9IntegerConverter converter = new X9IntegerConverter();
    protected ECFieldElement f;

    public X9FieldElement(ECFieldElement f) {
        this.f = f;
    }

    public X9FieldElement(BigInteger p, ASN1OctetString s) {
        this(new ECFieldElement.Fp(p, new BigInteger(1, s.getOctets())));
    }

    public X9FieldElement(int m, int k1, int k2, int k3, ASN1OctetString s) {
        this(new ECFieldElement.F2m(m, k1, k2, k3, new BigInteger(1, s.getOctets())));
    }

    public ECFieldElement getValue() {
        return this.f;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object,
              // com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        int byteCount = converter.getByteLength(this.f);
        byte[] paddedBigInteger = converter.integerToBytes(this.f.toBigInteger(), byteCount);
        return new DEROctetString(paddedBigInteger);
    }
}
