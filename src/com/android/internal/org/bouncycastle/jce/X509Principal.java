package com.android.internal.org.bouncycastle.jce;

import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.ASN1InputStream;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import com.android.internal.org.bouncycastle.asn1.x509.X509Name;
import java.io.IOException;
import java.security.Principal;
import java.util.Hashtable;
import java.util.Vector;

/* loaded from: classes5.dex */
public class X509Principal extends X509Name implements Principal {
    private static ASN1Sequence readSequence(ASN1InputStream aSN1InputStream) throws IOException {
        try {
            return ASN1Sequence.getInstance(aSN1InputStream.readObject());
        } catch (IllegalArgumentException e) {
            throw new IOException("not an ASN.1 Sequence: " + e);
        }
    }

    public X509Principal(byte[] bArr) throws IOException {
        super(readSequence(new ASN1InputStream(bArr)));
    }

    public X509Principal(X509Name x509Name) {
        super((ASN1Sequence) x509Name.toASN1Primitive());
    }

    public X509Principal(X500Name x500Name) {
        super((ASN1Sequence) x500Name.toASN1Primitive());
    }

    public X509Principal(Hashtable hashtable) {
        super(hashtable);
    }

    public X509Principal(Vector vector, Hashtable hashtable) {
        super(vector, hashtable);
    }

    public X509Principal(Vector vector, Vector vector2) {
        super(vector, vector2);
    }

    public X509Principal(String str) {
        super(str);
    }

    public X509Principal(boolean z, String str) {
        super(z, str);
    }

    public X509Principal(boolean z, Hashtable hashtable, String str) {
        super(z, hashtable, str);
    }

    @Override // java.security.Principal
    public String getName() {
        return toString();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.util.Encodable
    public byte[] getEncoded() {
        try {
            return getEncoded(ASN1Encoding.DER);
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }
}
