package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

/* loaded from: classes5.dex */
class LazyEncodedSequence extends ASN1Sequence {
    private byte[] encoded;

    LazyEncodedSequence(byte[] bArr) throws IOException {
        if (bArr == null) {
            throw new NullPointerException("'encoded' cannot be null");
        }
        this.encoded = bArr;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    public ASN1Encodable getObjectAt(int i) {
        force();
        return super.getObjectAt(i);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    public Enumeration getObjects() {
        byte[] contents = getContents();
        if (contents != null) {
            return new LazyConstructionEnumeration(contents);
        }
        return super.getObjects();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence, com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        force();
        return super.hashCode();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence, com.android.internal.org.bouncycastle.util.Iterable, java.lang.Iterable
    public Iterator<ASN1Encodable> iterator() {
        force();
        return super.iterator();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    public int size() {
        force();
        return super.size();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    public ASN1Encodable[] toArray() {
        force();
        return super.toArray();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    ASN1Encodable[] toArrayInternal() {
        force();
        return super.toArrayInternal();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength(boolean z) throws IOException {
        byte[] contents = getContents();
        if (contents != null) {
            return ASN1OutputStream.getLengthOfEncodingDL(z, contents.length);
        }
        return super.toDLObject().encodedLength(z);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(ASN1OutputStream aSN1OutputStream, boolean z) throws IOException {
        byte[] contents = getContents();
        if (contents != null) {
            aSN1OutputStream.writeEncodingDL(z, 48, contents);
        } else {
            super.toDLObject().encode(aSN1OutputStream, z);
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    ASN1BitString toASN1BitString() {
        return ((ASN1Sequence) toDLObject()).toASN1BitString();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    ASN1External toASN1External() {
        return ((ASN1Sequence) toDLObject()).toASN1External();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    ASN1OctetString toASN1OctetString() {
        return ((ASN1Sequence) toDLObject()).toASN1OctetString();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    ASN1Set toASN1Set() {
        return ((ASN1Sequence) toDLObject()).toASN1Set();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    synchronized ASN1Primitive toDERObject() {
        force();
        return super.toDERObject();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    synchronized ASN1Primitive toDLObject() {
        force();
        return super.toDLObject();
    }

    private synchronized void force() {
        if (this.encoded != null) {
            ASN1InputStream aSN1InputStream = new ASN1InputStream(this.encoded, true);
            try {
                ASN1EncodableVector readVector = aSN1InputStream.readVector();
                aSN1InputStream.close();
                this.elements = readVector.takeElements();
                this.encoded = null;
            } catch (IOException e) {
                throw new ASN1ParsingException("malformed ASN.1: " + e, e);
            }
        }
    }

    private synchronized byte[] getContents() {
        return this.encoded;
    }
}
