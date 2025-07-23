package com.android.internal.org.bouncycastle.asn1.pkcs;

import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.BERSequence;

/* loaded from: classes5.dex */
public class Pfx extends ASN1Object implements PKCSObjectIdentifiers {
    private ContentInfo contentInfo;
    private MacData macData;

    private Pfx(ASN1Sequence aSN1Sequence) {
        this.macData = null;
        if (!ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)).hasValue(3)) {
            throw new IllegalArgumentException("wrong version for PFX PDU");
        }
        this.contentInfo = ContentInfo.getInstance(aSN1Sequence.getObjectAt(1));
        if (aSN1Sequence.size() == 3) {
            this.macData = MacData.getInstance(aSN1Sequence.getObjectAt(2));
        }
    }

    public static Pfx getInstance(Object obj) {
        if (obj instanceof Pfx) {
            return (Pfx) obj;
        }
        if (obj != null) {
            return new Pfx(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public Pfx(ContentInfo contentInfo, MacData macData) {
        this.contentInfo = contentInfo;
        this.macData = macData;
    }

    public ContentInfo getAuthSafe() {
        return this.contentInfo;
    }

    public MacData getMacData() {
        return this.macData;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        aSN1EncodableVector.add(new ASN1Integer(3L));
        aSN1EncodableVector.add(this.contentInfo);
        MacData macData = this.macData;
        if (macData != null) {
            aSN1EncodableVector.add(macData);
        }
        return new BERSequence(aSN1EncodableVector);
    }
}
