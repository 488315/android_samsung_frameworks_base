package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes5.dex */
class DLOutputStream extends ASN1OutputStream {
    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OutputStream
    DLOutputStream getDLSubStream() {
        return this;
    }

    DLOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OutputStream
    void writeElements(ASN1Encodable[] aSN1EncodableArr) throws IOException {
        for (ASN1Encodable aSN1Encodable : aSN1EncodableArr) {
            aSN1Encodable.toASN1Primitive().toDLObject().encode(this, true);
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OutputStream
    void writePrimitive(ASN1Primitive aSN1Primitive, boolean z) throws IOException {
        aSN1Primitive.toDLObject().encode(this, z);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OutputStream
    void writePrimitives(ASN1Primitive[] aSN1PrimitiveArr) throws IOException {
        for (ASN1Primitive aSN1Primitive : aSN1PrimitiveArr) {
            aSN1Primitive.toDLObject().encode(this, true);
        }
    }
}
