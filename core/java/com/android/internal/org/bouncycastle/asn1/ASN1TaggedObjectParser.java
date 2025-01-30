package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes5.dex */
public interface ASN1TaggedObjectParser extends ASN1Encodable, InMemoryRepresentable {
    ASN1Encodable getObjectParser(int i, boolean z) throws IOException;

    int getTagNo();
}
