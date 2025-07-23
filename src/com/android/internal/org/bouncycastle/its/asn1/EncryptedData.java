package com.android.internal.org.bouncycastle.its.asn1;

import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes5.dex */
public class EncryptedData {
    private EncryptedData(ASN1Sequence aSN1Sequence) {
    }

    public static EncryptedData getInstance(Object obj) {
        if (obj instanceof EncryptedData) {
            return (EncryptedData) obj;
        }
        if (obj != null) {
            return new EncryptedData(ASN1Sequence.getInstance(obj));
        }
        return null;
    }
}
