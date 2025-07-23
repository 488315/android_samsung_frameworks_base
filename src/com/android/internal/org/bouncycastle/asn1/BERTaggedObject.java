package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes5.dex */
public class BERTaggedObject extends ASN1TaggedObject {
    public BERTaggedObject(int i, ASN1Encodable aSN1Encodable) {
        super(true, i, aSN1Encodable);
    }

    public BERTaggedObject(int i, int i2, ASN1Encodable aSN1Encodable) {
        super(true, i, i2, aSN1Encodable);
    }

    public BERTaggedObject(boolean z, int i, ASN1Encodable aSN1Encodable) {
        super(z, i, aSN1Encodable);
    }

    public BERTaggedObject(boolean z, int i, int i2, ASN1Encodable aSN1Encodable) {
        super(z, i, i2, aSN1Encodable);
    }

    BERTaggedObject(int i, int i2, int i3, ASN1Encodable aSN1Encodable) {
        super(i, i2, i3, aSN1Encodable);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean encodeConstructed() {
        return isExplicit() || this.obj.toASN1Primitive().encodeConstructed();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength(boolean z) throws IOException {
        ASN1Primitive aSN1Primitive = this.obj.toASN1Primitive();
        boolean isExplicit = isExplicit();
        int encodedLength = aSN1Primitive.encodedLength(isExplicit);
        if (isExplicit) {
            encodedLength += 3;
        }
        return encodedLength + (z ? ASN1OutputStream.getLengthOfIdentifier(this.tagNo) : 0);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(ASN1OutputStream aSN1OutputStream, boolean z) throws IOException {
        ASN1Primitive aSN1Primitive = this.obj.toASN1Primitive();
        boolean isExplicit = isExplicit();
        if (z) {
            int i = this.tagClass;
            if (isExplicit || aSN1Primitive.encodeConstructed()) {
                i |= 32;
            }
            aSN1OutputStream.writeIdentifier(true, i, this.tagNo);
        }
        if (isExplicit) {
            aSN1OutputStream.write(128);
            aSN1Primitive.encode(aSN1OutputStream, true);
            aSN1OutputStream.write(0);
            aSN1OutputStream.write(0);
            return;
        }
        aSN1Primitive.encode(aSN1OutputStream, false);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject
    ASN1Sequence rebuildConstructed(ASN1Primitive aSN1Primitive) {
        return new BERSequence(aSN1Primitive);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject
    ASN1TaggedObject replaceTag(int i, int i2) {
        return new BERTaggedObject(this.explicitness, i, i2, this.obj);
    }
}
