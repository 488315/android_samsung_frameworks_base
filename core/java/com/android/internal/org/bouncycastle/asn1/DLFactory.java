package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes5.dex */
class DLFactory {
  static final ASN1Sequence EMPTY_SEQUENCE = new DLSequence();
  static final ASN1Set EMPTY_SET = new DLSet();

  DLFactory() {}

  static ASN1Sequence createSequence(ASN1EncodableVector v) {
    if (v.size() < 1) {
      return EMPTY_SEQUENCE;
    }
    return new DLSequence(v);
  }

  static ASN1Set createSet(ASN1EncodableVector v) {
    if (v.size() < 1) {
      return EMPTY_SET;
    }
    return new DLSet(v);
  }
}
