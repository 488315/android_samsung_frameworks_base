package com.android.internal.org.bouncycastle.asn1.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1PrintableString;
import com.android.internal.org.bouncycastle.util.encoders.Hex;
import java.io.IOException;

/* loaded from: classes5.dex */
public abstract class X509NameEntryConverter {
    public abstract ASN1Primitive getConvertedValue(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str);

    protected ASN1Primitive convertHexEncoded(String str, int i) throws IOException {
        return ASN1Primitive.fromByteArray(Hex.decodeStrict(str, i, str.length() - i));
    }

    protected boolean canBePrintable(String str) {
        return ASN1PrintableString.isPrintableString(str);
    }
}
