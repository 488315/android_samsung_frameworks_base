package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

class LazyEncodedSequence extends ASN1Sequence {
    private byte[] encoded;

    LazyEncodedSequence(byte[] encoded) throws IOException {
        this.encoded = encoded;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    public synchronized ASN1Encodable getObjectAt(int index) {
        force();
        return super.getObjectAt(index);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    public synchronized Enumeration getObjects() {
        if (this.encoded != null) {
            return new LazyConstructionEnumeration(this.encoded);
        }
        return super.getObjects();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence,
              // com.android.internal.org.bouncycastle.asn1.ASN1Primitive,
              // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public synchronized int hashCode() {
        force();
        return super.hashCode();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence,
              // com.android.internal.org.bouncycastle.util.Iterable, java.lang.Iterable
    public synchronized Iterator<ASN1Encodable> iterator() {
        force();
        return super.iterator();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    public synchronized int size() {
        force();
        return super.size();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    public synchronized ASN1Encodable[] toArray() {
        force();
        return super.toArray();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    ASN1Encodable[] toArrayInternal() {
        force();
        return super.toArrayInternal();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    synchronized int encodedLength() throws IOException {
        if (this.encoded != null) {
            return StreamUtil.calculateBodyLength(this.encoded.length) + 1 + this.encoded.length;
        }
        return super.toDLObject().encodedLength();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence,
              // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    synchronized void encode(ASN1OutputStream out, boolean withTag) throws IOException {
        if (this.encoded != null) {
            out.writeEncoded(withTag, 48, this.encoded);
        } else {
            super.toDLObject().encode(out, withTag);
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence,
              // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    synchronized ASN1Primitive toDERObject() {
        force();
        return super.toDERObject();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence,
              // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    synchronized ASN1Primitive toDLObject() {
        force();
        return super.toDLObject();
    }

    private void force() {
        if (this.encoded != null) {
            ASN1EncodableVector v = new ASN1EncodableVector();
            Enumeration en = new LazyConstructionEnumeration(this.encoded);
            while (en.hasMoreElements()) {
                v.add((ASN1Primitive) en.nextElement());
            }
            this.elements = v.takeElements();
            this.encoded = null;
        }
    }
}
