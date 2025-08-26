package com.android.internal.org.bouncycastle.its.asn1;

import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class IValue extends ASN1Object {
    private final BigInteger value;

    private IValue(ASN1Integer aSN1Integer) {
        int iIntValueExact = BigIntegers.intValueExact(aSN1Integer.getValue());
        if (iIntValueExact < 0 || iIntValueExact > 65535) {
            throw new IllegalArgumentException("value out of range");
        }
        this.value = aSN1Integer.getValue();
    }

    public static IValue getInstance(Object obj) {
        if (obj instanceof IValue) {
            return (IValue) obj;
        }
        if (obj != null) {
            return new IValue(ASN1Integer.getInstance(obj));
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new ASN1Integer(this.value);
    }
}
