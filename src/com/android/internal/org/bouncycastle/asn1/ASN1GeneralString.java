package com.android.internal.org.bouncycastle.asn1;

import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.Strings;
import java.io.IOException;

/* loaded from: classes5.dex */
public abstract class ASN1GeneralString extends ASN1Primitive implements ASN1String {
    static final ASN1UniversalType TYPE = new ASN1UniversalType(ASN1GeneralString.class, 27) { // from class: com.android.internal.org.bouncycastle.asn1.ASN1GeneralString.1
        @Override // com.android.internal.org.bouncycastle.asn1.ASN1UniversalType
        ASN1Primitive fromImplicitPrimitive(DEROctetString dEROctetString) {
            return ASN1GeneralString.createPrimitive(dEROctetString.getOctets());
        }
    };
    final byte[] contents;

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    final boolean encodeConstructed() {
        return false;
    }

    public static ASN1GeneralString getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1GeneralString)) {
            return (ASN1GeneralString) obj;
        }
        if (obj instanceof ASN1Encodable) {
            ASN1Primitive aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
            if (aSN1Primitive instanceof ASN1GeneralString) {
                return (ASN1GeneralString) aSN1Primitive;
            }
        }
        if (obj instanceof byte[]) {
            try {
                return (ASN1GeneralString) TYPE.fromByteArray((byte[]) obj);
            } catch (Exception e) {
                throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static ASN1GeneralString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return (ASN1GeneralString) TYPE.getContextInstance(aSN1TaggedObject, z);
    }

    ASN1GeneralString(String str) {
        this.contents = Strings.toByteArray(str);
    }

    ASN1GeneralString(byte[] bArr, boolean z) {
        this.contents = z ? Arrays.clone(bArr) : bArr;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1String
    public final String getString() {
        return Strings.fromByteArray(this.contents);
    }

    public String toString() {
        return getString();
    }

    public final byte[] getOctets() {
        return Arrays.clone(this.contents);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    final int encodedLength(boolean z) {
        return ASN1OutputStream.getLengthOfEncodingDL(z, this.contents.length);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    final void encode(ASN1OutputStream aSN1OutputStream, boolean z) throws IOException {
        aSN1OutputStream.writeEncodingDL(z, 27, this.contents);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    final boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (aSN1Primitive instanceof ASN1GeneralString) {
            return Arrays.areEqual(this.contents, ((ASN1GeneralString) aSN1Primitive).contents);
        }
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public final int hashCode() {
        return Arrays.hashCode(this.contents);
    }

    static ASN1GeneralString createPrimitive(byte[] bArr) {
        return new DERGeneralString(bArr, false);
    }
}
