package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes5.dex */
public abstract class ASN1Primitive extends ASN1Object {
    abstract boolean asn1Equals(ASN1Primitive aSN1Primitive);

    abstract void encode(ASN1OutputStream aSN1OutputStream, boolean z) throws IOException;

    abstract boolean encodeConstructed();

    abstract int encodedLength(boolean z) throws IOException;

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public abstract int hashCode();

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public final ASN1Primitive toASN1Primitive() {
        return this;
    }

    ASN1Primitive toDERObject() {
        return this;
    }

    ASN1Primitive toDLObject() {
        return this;
    }

    ASN1Primitive() {
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public void encodeTo(OutputStream outputStream) throws IOException {
        ASN1OutputStream aSN1OutputStreamCreate = ASN1OutputStream.create(outputStream);
        aSN1OutputStreamCreate.writePrimitive(this, true);
        aSN1OutputStreamCreate.flushInternal();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public void encodeTo(OutputStream outputStream, String str) throws IOException {
        ASN1OutputStream aSN1OutputStreamCreate = ASN1OutputStream.create(outputStream, str);
        aSN1OutputStreamCreate.writePrimitive(this, true);
        aSN1OutputStreamCreate.flushInternal();
    }

    public static ASN1Primitive fromByteArray(byte[] bArr) throws IOException {
        ASN1InputStream aSN1InputStream = new ASN1InputStream(bArr);
        try {
            ASN1Primitive object = aSN1InputStream.readObject();
            if (aSN1InputStream.available() == 0) {
                return object;
            }
            throw new IOException("Extra data detected in stream");
        } catch (ClassCastException unused) {
            throw new IOException("cannot recognise object in stream");
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ASN1Encodable) && asn1Equals(((ASN1Encodable) obj).toASN1Primitive());
    }

    public final boolean equals(ASN1Encodable aSN1Encodable) {
        if (this != aSN1Encodable) {
            return aSN1Encodable != null && asn1Equals(aSN1Encodable.toASN1Primitive());
        }
        return true;
    }

    public final boolean equals(ASN1Primitive aSN1Primitive) {
        return this == aSN1Primitive || asn1Equals(aSN1Primitive);
    }
}
