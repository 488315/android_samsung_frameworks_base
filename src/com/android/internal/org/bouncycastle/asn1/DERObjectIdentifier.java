package com.android.internal.org.bouncycastle.asn1;

import java.nio.charset.StandardCharsets;

/* loaded from: classes5.dex */
public class DERObjectIdentifier extends ASN1ObjectIdentifier {
    DERObjectIdentifier(byte[] bArr) {
        super(new String(bArr, StandardCharsets.UTF_8));
    }
}
