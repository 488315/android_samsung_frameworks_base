package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

class DLOutputStream extends ASN1OutputStream {
    DLOutputStream(OutputStream os) {
        super(os);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OutputStream
    void writePrimitive(ASN1Primitive primitive, boolean withTag) throws IOException {
        primitive.toDLObject().encode(this, withTag);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OutputStream
    ASN1OutputStream getDLSubStream() {
        return this;
    }
}
