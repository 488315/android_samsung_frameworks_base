package com.android.internal.org.bouncycastle.asn1;

import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.Strings;
import com.android.internal.org.bouncycastle.util.encoders.Hex;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public abstract class ASN1OctetString extends ASN1Primitive implements ASN1OctetStringParser {
    byte[] string;
    static final ASN1UniversalType TYPE = new ASN1UniversalType(ASN1OctetString.class, 4) { // from class: com.android.internal.org.bouncycastle.asn1.ASN1OctetString.1
        @Override // com.android.internal.org.bouncycastle.asn1.ASN1UniversalType
        ASN1Primitive fromImplicitPrimitive(DEROctetString dEROctetString) {
            return dEROctetString;
        }

        @Override // com.android.internal.org.bouncycastle.asn1.ASN1UniversalType
        ASN1Primitive fromImplicitConstructed(ASN1Sequence aSN1Sequence) {
            return aSN1Sequence.toASN1OctetString();
        }
    };
    static final byte[] EMPTY_OCTETS = new byte[0];

    public ASN1OctetStringParser parser() {
        return this;
    }

    public static ASN1OctetString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return (ASN1OctetString) TYPE.getContextInstance(aSN1TaggedObject, z);
    }

    public static ASN1OctetString getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1OctetString)) {
            return (ASN1OctetString) obj;
        }
        if (obj instanceof ASN1Encodable) {
            ASN1Primitive aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
            if (aSN1Primitive instanceof ASN1OctetString) {
                return (ASN1OctetString) aSN1Primitive;
            }
        } else if (obj instanceof byte[]) {
            try {
                return (ASN1OctetString) TYPE.fromByteArray((byte[]) obj);
            } catch (IOException e) {
                throw new IllegalArgumentException("failed to construct OCTET STRING from byte[]: " + e.getMessage());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public ASN1OctetString(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("'string' cannot be null");
        }
        this.string = bArr;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OctetStringParser
    public InputStream getOctetStream() {
        return new ByteArrayInputStream(this.string);
    }

    public byte[] getOctets() {
        return this.string;
    }

    public int getOctetsLength() {
        return getOctets().length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return Arrays.hashCode(getOctets());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (aSN1Primitive instanceof ASN1OctetString) {
            return Arrays.areEqual(this.string, ((ASN1OctetString) aSN1Primitive).string);
        }
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() {
        return toASN1Primitive();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDERObject() {
        return new DEROctetString(this.string);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDLObject() {
        return new DEROctetString(this.string);
    }

    public String toString() {
        return "#" + Strings.fromByteArray(Hex.encode(this.string));
    }

    static ASN1OctetString createPrimitive(byte[] bArr) {
        return new DEROctetString(bArr);
    }
}
