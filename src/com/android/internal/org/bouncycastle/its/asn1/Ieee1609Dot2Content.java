package com.android.internal.org.bouncycastle.its.asn1;

import com.android.internal.org.bouncycastle.asn1.ASN1Choice;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.DERSequence;

/* loaded from: classes5.dex */
public class Ieee1609Dot2Content extends ASN1Object implements ASN1Choice {
    public static Ieee1609Dot2Content getInstance(Object obj) {
        if (obj instanceof Ieee1609Dot2Content) {
            return (Ieee1609Dot2Content) obj;
        }
        if (obj != null) {
            return getInstance(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(new ASN1EncodableVector());
    }
}
