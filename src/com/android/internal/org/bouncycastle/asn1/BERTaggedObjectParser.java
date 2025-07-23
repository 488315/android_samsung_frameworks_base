package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes5.dex */
class BERTaggedObjectParser implements ASN1TaggedObjectParser {
    final ASN1StreamParser _parser;
    final int _tagClass;
    final int _tagNo;

    BERTaggedObjectParser(int i, int i2, ASN1StreamParser aSN1StreamParser) {
        this._tagClass = i;
        this._tagNo = i2;
        this._parser = aSN1StreamParser;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public int getTagClass() {
        return this._tagClass;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public int getTagNo() {
        return this._tagNo;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public boolean hasContextTag() {
        return this._tagClass == 128;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public boolean hasContextTag(int i) {
        return this._tagClass == 128 && this._tagNo == i;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public boolean hasTag(int i, int i2) {
        return this._tagClass == i && this._tagNo == i2;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public boolean hasTagClass(int i) {
        return this._tagClass == i;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() throws IOException {
        return this._parser.loadTaggedIL(this._tagClass, this._tagNo);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1Encodable parseBaseUniversal(boolean z, int i) throws IOException {
        if (z) {
            return this._parser.parseObject(i);
        }
        return this._parser.parseImplicitConstructedIL(i);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1Encodable parseExplicitBaseObject() throws IOException {
        return this._parser.readObject();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1TaggedObjectParser parseExplicitBaseTagged() throws IOException {
        return this._parser.parseTaggedObject();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1TaggedObjectParser parseImplicitBaseTagged(int i, int i2) throws IOException {
        return new BERTaggedObjectParser(i, i2, this._parser);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e) {
            throw new ASN1ParsingException(e.getMessage());
        }
    }
}
