package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes5.dex */
public class DLBitString extends ASN1BitString {
  public static ASN1BitString getInstance(Object obj) {
    if (obj == null || (obj instanceof DLBitString)) {
      return (DLBitString) obj;
    }
    if (obj instanceof DERBitString) {
      return (DERBitString) obj;
    }
    if (obj instanceof byte[]) {
      try {
        return (ASN1BitString) fromByteArray((byte[]) obj);
      } catch (Exception e) {
        throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
      }
    }
    throw new IllegalArgumentException(
        "illegal object in getInstance: " + obj.getClass().getName());
  }

  public static ASN1BitString getInstance(ASN1TaggedObject obj, boolean explicit) {
    ASN1Primitive o = obj.getObject();
    if (explicit || (o instanceof DLBitString)) {
      return getInstance(o);
    }
    return fromOctetString(ASN1OctetString.getInstance(o).getOctets());
  }

  protected DLBitString(byte data, int padBits) {
    super(data, padBits);
  }

  public DLBitString(byte[] data, int padBits) {
    super(data, padBits);
  }

  public DLBitString(byte[] data) {
    this(data, 0);
  }

  public DLBitString(int value) {
    super(getBytes(value), getPadBits(value));
  }

  public DLBitString(ASN1Encodable obj) throws IOException {
    super(obj.toASN1Primitive().getEncoded(ASN1Encoding.DER), 0);
  }

  @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
  boolean isConstructed() {
    return false;
  }

  @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
  int encodedLength() {
    return StreamUtil.calculateBodyLength(this.data.length + 1) + 1 + this.data.length + 1;
  }

  @Override // com.android.internal.org.bouncycastle.asn1.ASN1BitString,
            // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
  void encode(ASN1OutputStream out, boolean withTag) throws IOException {
    out.writeEncoded(withTag, 3, (byte) this.padBits, this.data);
  }

  @Override // com.android.internal.org.bouncycastle.asn1.ASN1BitString,
            // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
  ASN1Primitive toDLObject() {
    return this;
  }

  static DLBitString fromOctetString(byte[] bytes) {
    if (bytes.length < 1) {
      throw new IllegalArgumentException("truncated BIT STRING detected");
    }
    int padBits = bytes[0];
    byte[] data = new byte[bytes.length - 1];
    if (data.length != 0) {
      System.arraycopy(bytes, 1, data, 0, bytes.length - 1);
    }
    return new DLBitString(data, padBits);
  }
}
