package com.android.internal.org.bouncycastle.its.asn1;

import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes5.dex */
public class ToBeSignedCertificate extends ASN1Object {
    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return null;
    }

    private ToBeSignedCertificate(ASN1Sequence aSN1Sequence) {
    }

    public static ToBeSignedCertificate getInstance(Object obj) {
        if (obj instanceof ToBeSignedCertificate) {
            return (ToBeSignedCertificate) obj;
        }
        if (obj != null) {
            return new ToBeSignedCertificate(ASN1Sequence.getInstance(obj));
        }
        return null;
    }
}
