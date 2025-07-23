package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes5.dex */
public class DERSequence extends ASN1Sequence {
    private int contentsLength;

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDERObject() {
        return this;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDLObject() {
        return this;
    }

    public static DERSequence convert(ASN1Sequence aSN1Sequence) {
        return (DERSequence) aSN1Sequence.toDERObject();
    }

    public DERSequence() {
        this.contentsLength = -1;
    }

    public DERSequence(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable);
        this.contentsLength = -1;
    }

    public DERSequence(ASN1EncodableVector aSN1EncodableVector) {
        super(aSN1EncodableVector);
        this.contentsLength = -1;
    }

    public DERSequence(ASN1Encodable[] aSN1EncodableArr) {
        super(aSN1EncodableArr);
        this.contentsLength = -1;
    }

    DERSequence(ASN1Encodable[] aSN1EncodableArr, boolean z) {
        super(aSN1EncodableArr, z);
        this.contentsLength = -1;
    }

    private int getContentsLength() throws IOException {
        if (this.contentsLength < 0) {
            int length = this.elements.length;
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                i += this.elements[i2].toASN1Primitive().toDERObject().encodedLength(true);
            }
            this.contentsLength = i;
        }
        return this.contentsLength;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength(boolean z) throws IOException {
        return ASN1OutputStream.getLengthOfEncodingDL(z, getContentsLength());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(ASN1OutputStream aSN1OutputStream, boolean z) throws IOException {
        aSN1OutputStream.writeIdentifier(z, 48);
        DEROutputStream dERSubStream = aSN1OutputStream.getDERSubStream();
        int length = this.elements.length;
        int i = 0;
        if (this.contentsLength >= 0 || length > 16) {
            aSN1OutputStream.writeDL(getContentsLength());
            while (i < length) {
                this.elements[i].toASN1Primitive().toDERObject().encode(dERSubStream, true);
                i++;
            }
            return;
        }
        ASN1Primitive[] aSN1PrimitiveArr = new ASN1Primitive[length];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            ASN1Primitive dERObject = this.elements[i3].toASN1Primitive().toDERObject();
            aSN1PrimitiveArr[i3] = dERObject;
            i2 += dERObject.encodedLength(true);
        }
        this.contentsLength = i2;
        aSN1OutputStream.writeDL(i2);
        while (i < length) {
            aSN1PrimitiveArr[i].encode(dERSubStream, true);
            i++;
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    ASN1BitString toASN1BitString() {
        return new DERBitString(BERBitString.flattenBitStrings(getConstructedBitStrings()), false);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    ASN1External toASN1External() {
        return new DERExternal(this);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    ASN1OctetString toASN1OctetString() {
        return new DEROctetString(BEROctetString.flattenOctetStrings(getConstructedOctetStrings()));
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    ASN1Set toASN1Set() {
        return new DLSet(false, toArrayInternal());
    }
}
