package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes5.dex */
public class BERSequence extends ASN1Sequence {
    public BERSequence() {
    }

    public BERSequence(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable);
    }

    public BERSequence(ASN1EncodableVector aSN1EncodableVector) {
        super(aSN1EncodableVector);
    }

    public BERSequence(ASN1Encodable[] aSN1EncodableArr) {
        super(aSN1EncodableArr);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength(boolean z) throws IOException {
        int i = z ? 4 : 3;
        int length = this.elements.length;
        for (int i2 = 0; i2 < length; i2++) {
            i += this.elements[i2].toASN1Primitive().encodedLength(true);
        }
        return i;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(ASN1OutputStream aSN1OutputStream, boolean z) throws IOException {
        aSN1OutputStream.writeEncodingIL(z, 48, this.elements);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    ASN1BitString toASN1BitString() {
        return new BERBitString(getConstructedBitStrings());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    ASN1External toASN1External() {
        return ((ASN1Sequence) toDLObject()).toASN1External();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    ASN1OctetString toASN1OctetString() {
        return new BEROctetString(getConstructedOctetStrings());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    ASN1Set toASN1Set() {
        return new BERSet(false, toArrayInternal());
    }
}
