package com.android.internal.org.bouncycastle.asn1;

import java.io.OutputStream;

class BEROutputStream extends ASN1OutputStream {
    BEROutputStream(OutputStream os) {
        super(os);
    }
}
