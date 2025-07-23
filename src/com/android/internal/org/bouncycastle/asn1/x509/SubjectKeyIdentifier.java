package com.android.internal.org.bouncycastle.asn1.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.util.Arrays;

/* loaded from: classes5.dex */
public class SubjectKeyIdentifier extends ASN1Object {
    private byte[] keyidentifier;

    public static SubjectKeyIdentifier getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1OctetString.getInstance(aSN1TaggedObject, z));
    }

    public static SubjectKeyIdentifier getInstance(Object obj) {
        if (obj instanceof SubjectKeyIdentifier) {
            return (SubjectKeyIdentifier) obj;
        }
        if (obj != null) {
            return new SubjectKeyIdentifier(ASN1OctetString.getInstance(obj));
        }
        return null;
    }

    public static SubjectKeyIdentifier fromExtensions(Extensions extensions) {
        return getInstance(Extensions.getExtensionParsedValue(extensions, Extension.subjectKeyIdentifier));
    }

    public SubjectKeyIdentifier(byte[] bArr) {
        this.keyidentifier = Arrays.clone(bArr);
    }

    protected SubjectKeyIdentifier(ASN1OctetString aSN1OctetString) {
        this(aSN1OctetString.getOctets());
    }

    public byte[] getKeyIdentifier() {
        return Arrays.clone(this.keyidentifier);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DEROctetString(getKeyIdentifier());
    }
}
