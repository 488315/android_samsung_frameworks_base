package com.android.internal.org.bouncycastle.its.asn1;

import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes5.dex */
public class RectangularRegion extends ASN1Object {
    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return null;
    }

    private RectangularRegion(ASN1Sequence aSN1Sequence) {
    }

    public static RectangularRegion getInstance(Object obj) {
        if (obj instanceof RectangularRegion) {
            return (RectangularRegion) obj;
        }
        if (obj != null) {
            return new RectangularRegion(ASN1Sequence.getInstance(obj));
        }
        return null;
    }
}
