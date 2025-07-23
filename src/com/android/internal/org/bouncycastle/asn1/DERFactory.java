package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes5.dex */
class DERFactory {
    static final DERSequence EMPTY_SEQUENCE = new DERSequence();
    static final DERSet EMPTY_SET = new DERSet();

    DERFactory() {
    }

    static DERSequence createSequence(ASN1EncodableVector aSN1EncodableVector) {
        if (aSN1EncodableVector.size() < 1) {
            return EMPTY_SEQUENCE;
        }
        return new DERSequence(aSN1EncodableVector);
    }

    static DERSet createSet(ASN1EncodableVector aSN1EncodableVector) {
        if (aSN1EncodableVector.size() < 1) {
            return EMPTY_SET;
        }
        return new DERSet(aSN1EncodableVector);
    }
}
