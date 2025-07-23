package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes5.dex */
class DLTaggedObjectParser extends BERTaggedObjectParser {
    private final boolean _constructed;

    DLTaggedObjectParser(int i, int i2, boolean z, ASN1StreamParser aSN1StreamParser) {
        super(i, i2, aSN1StreamParser);
        this._constructed = z;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.BERTaggedObjectParser, com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() throws IOException {
        return this._parser.loadTaggedDL(this._tagClass, this._tagNo, this._constructed);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.BERTaggedObjectParser, com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1Encodable parseBaseUniversal(boolean z, int i) throws IOException {
        if (z) {
            if (!this._constructed) {
                throw new IOException("Explicit tags must be constructed (see X.690 8.14.2)");
            }
            return this._parser.parseObject(i);
        }
        if (this._constructed) {
            return this._parser.parseImplicitConstructedDL(i);
        }
        return this._parser.parseImplicitPrimitive(i);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.BERTaggedObjectParser, com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1Encodable parseExplicitBaseObject() throws IOException {
        if (!this._constructed) {
            throw new IOException("Explicit tags must be constructed (see X.690 8.14.2)");
        }
        return this._parser.readObject();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.BERTaggedObjectParser, com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1TaggedObjectParser parseExplicitBaseTagged() throws IOException {
        if (!this._constructed) {
            throw new IOException("Explicit tags must be constructed (see X.690 8.14.2)");
        }
        return this._parser.parseTaggedObject();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.BERTaggedObjectParser, com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1TaggedObjectParser parseImplicitBaseTagged(int i, int i2) throws IOException {
        return new DLTaggedObjectParser(i, i2, this._constructed, this._parser);
    }
}
