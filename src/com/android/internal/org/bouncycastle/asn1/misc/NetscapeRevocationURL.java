package com.android.internal.org.bouncycastle.asn1.misc;

import com.android.internal.org.bouncycastle.asn1.ASN1IA5String;
import com.android.internal.org.bouncycastle.asn1.DERIA5String;

/* loaded from: classes5.dex */
public class NetscapeRevocationURL extends DERIA5String {
    public NetscapeRevocationURL(ASN1IA5String aSN1IA5String) {
        super(aSN1IA5String.getString());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1IA5String
    public String toString() {
        return "NetscapeRevocationURL: " + getString();
    }
}
