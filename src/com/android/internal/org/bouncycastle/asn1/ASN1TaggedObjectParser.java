package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes5.dex */
public interface ASN1TaggedObjectParser extends ASN1Encodable, InMemoryRepresentable {
    int getTagClass();

    int getTagNo();

    boolean hasContextTag();

    boolean hasContextTag(int i);

    boolean hasTag(int i, int i2);

    boolean hasTagClass(int i);

    ASN1Encodable parseBaseUniversal(boolean z, int i) throws IOException;

    ASN1Encodable parseExplicitBaseObject() throws IOException;

    ASN1TaggedObjectParser parseExplicitBaseTagged() throws IOException;

    ASN1TaggedObjectParser parseImplicitBaseTagged(int i, int i2) throws IOException;
}
