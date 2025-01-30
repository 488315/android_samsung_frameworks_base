package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes5.dex */
public class BERSet extends ASN1Set {
    public BERSet() {
    }

    public BERSet(ASN1Encodable element) {
        super(element);
    }

    public BERSet(ASN1EncodableVector elementVector) {
        super(elementVector, false);
    }

    public BERSet(ASN1Encodable[] elements) {
        super(elements, false);
    }

    BERSet(boolean isSorted, ASN1Encodable[] elements) {
        super(isSorted, elements);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() throws IOException {
        int count = this.elements.length;
        int totalLength = 0;
        for (int i = 0; i < count; i++) {
            ASN1Primitive p = this.elements[i].toASN1Primitive();
            totalLength += p.encodedLength();
        }
        int i2 = totalLength + 2;
        return i2 + 2;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Set, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(ASN1OutputStream out, boolean withTag) throws IOException {
        out.writeEncodedIndef(withTag, 49, this.elements);
    }
}
