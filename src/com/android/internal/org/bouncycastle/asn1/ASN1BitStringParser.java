package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public interface ASN1BitStringParser extends ASN1Encodable, InMemoryRepresentable {
    InputStream getBitStream() throws IOException;

    InputStream getOctetStream() throws IOException;

    int getPadBits();
}
