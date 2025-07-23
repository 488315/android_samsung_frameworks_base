package com.android.internal.org.bouncycastle.its.asn1;

import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.DERSequence;

/* loaded from: classes5.dex */
public class SequenceOfRectangularRegion extends ASN1Object {
    private final RectangularRegion[] sequence;

    private SequenceOfRectangularRegion(ASN1Sequence aSN1Sequence) {
        this.sequence = new RectangularRegion[aSN1Sequence.size()];
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            this.sequence[i] = RectangularRegion.getInstance(aSN1Sequence.getObjectAt(i));
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(this.sequence);
    }
}
