package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/* loaded from: classes5.dex */
class LazyConstructionEnumeration implements Enumeration {
    private ASN1InputStream aIn;
    private Object nextObj = readObject();

    public LazyConstructionEnumeration(byte[] bArr) {
        this.aIn = new ASN1InputStream(bArr, true);
    }

    @Override // java.util.Enumeration
    public boolean hasMoreElements() {
        return this.nextObj != null;
    }

    @Override // java.util.Enumeration
    public Object nextElement() {
        Object obj = this.nextObj;
        if (obj != null) {
            this.nextObj = readObject();
            return obj;
        }
        throw new NoSuchElementException();
    }

    private Object readObject() {
        try {
            return this.aIn.readObject();
        } catch (IOException e) {
            throw new ASN1ParsingException("malformed ASN.1: " + e, e);
        }
    }
}
