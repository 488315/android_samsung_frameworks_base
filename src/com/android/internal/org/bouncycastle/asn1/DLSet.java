package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes5.dex */
public class DLSet extends ASN1Set {
    private int contentsLength;

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Set, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDLObject() {
        return this;
    }

    public DLSet() {
        this.contentsLength = -1;
    }

    public DLSet(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable);
        this.contentsLength = -1;
    }

    public DLSet(ASN1EncodableVector aSN1EncodableVector) {
        super(aSN1EncodableVector, false);
        this.contentsLength = -1;
    }

    public DLSet(ASN1Encodable[] aSN1EncodableArr) {
        super(aSN1EncodableArr, false);
        this.contentsLength = -1;
    }

    DLSet(boolean z, ASN1Encodable[] aSN1EncodableArr) {
        super(z, aSN1EncodableArr);
        this.contentsLength = -1;
    }

    DLSet(ASN1Encodable[] aSN1EncodableArr, ASN1Encodable[] aSN1EncodableArr2) {
        super(aSN1EncodableArr, aSN1EncodableArr2);
        this.contentsLength = -1;
    }

    private int getContentsLength() throws IOException {
        if (this.contentsLength < 0) {
            int length = this.elements.length;
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                i += this.elements[i2].toASN1Primitive().toDLObject().encodedLength(true);
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
        aSN1OutputStream.writeIdentifier(z, 49);
        DLOutputStream dLSubStream = aSN1OutputStream.getDLSubStream();
        int length = this.elements.length;
        int i = 0;
        if (this.contentsLength >= 0 || length > 16) {
            aSN1OutputStream.writeDL(getContentsLength());
            while (i < length) {
                dLSubStream.writePrimitive(this.elements[i].toASN1Primitive(), true);
                i++;
            }
            return;
        }
        ASN1Primitive[] aSN1PrimitiveArr = new ASN1Primitive[length];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            ASN1Primitive dLObject = this.elements[i3].toASN1Primitive().toDLObject();
            aSN1PrimitiveArr[i3] = dLObject;
            i2 += dLObject.encodedLength(true);
        }
        this.contentsLength = i2;
        aSN1OutputStream.writeDL(i2);
        while (i < length) {
            dLSubStream.writePrimitive(aSN1PrimitiveArr[i], true);
            i++;
        }
    }
}
