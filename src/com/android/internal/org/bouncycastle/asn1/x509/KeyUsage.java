package com.android.internal.org.bouncycastle.asn1.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1BitString;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.DERBitString;

/* loaded from: classes5.dex */
public class KeyUsage extends ASN1Object {
    public static final int cRLSign = 2;
    public static final int dataEncipherment = 16;
    public static final int decipherOnly = 32768;
    public static final int digitalSignature = 128;
    public static final int encipherOnly = 1;
    public static final int keyAgreement = 8;
    public static final int keyCertSign = 4;
    public static final int keyEncipherment = 32;
    public static final int nonRepudiation = 64;
    private ASN1BitString bitString;

    public static KeyUsage getInstance(Object obj) {
        if (obj instanceof KeyUsage) {
            return (KeyUsage) obj;
        }
        if (obj != null) {
            return new KeyUsage(ASN1BitString.getInstance(obj));
        }
        return null;
    }

    public static KeyUsage fromExtensions(Extensions extensions) {
        return getInstance(Extensions.getExtensionParsedValue(extensions, Extension.keyUsage));
    }

    public KeyUsage(int i) {
        this.bitString = new DERBitString(i);
    }

    private KeyUsage(ASN1BitString aSN1BitString) {
        this.bitString = aSN1BitString;
    }

    public boolean hasUsages(int i) {
        return (this.bitString.intValue() & i) == i;
    }

    public byte[] getBytes() {
        return this.bitString.getBytes();
    }

    public int getPadBits() {
        return this.bitString.getPadBits();
    }

    public String toString() {
        byte[] bytes = this.bitString.getBytes();
        if (bytes.length == 1) {
            return "KeyUsage: 0x" + Integer.toHexString(bytes[0] & 255);
        }
        return "KeyUsage: 0x" + Integer.toHexString((bytes[0] & 255) | ((bytes[1] & 255) << 8));
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return this.bitString;
    }
}
