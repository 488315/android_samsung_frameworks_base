package com.android.internal.org.bouncycastle.asn1.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class CRLNumber extends ASN1Object {
    private BigInteger number;

    public CRLNumber(BigInteger bigInteger) {
        if (BigIntegers.ZERO.compareTo(bigInteger) > 0) {
            throw new IllegalArgumentException("Invalid CRL number : not in (0..MAX)");
        }
        this.number = bigInteger;
    }

    public BigInteger getCRLNumber() {
        return this.number;
    }

    public String toString() {
        return "CRLNumber: " + getCRLNumber();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new ASN1Integer(this.number);
    }

    public static CRLNumber getInstance(Object obj) {
        if (obj instanceof CRLNumber) {
            return (CRLNumber) obj;
        }
        if (obj != null) {
            return new CRLNumber(ASN1Integer.getInstance(obj).getValue());
        }
        return null;
    }
}
