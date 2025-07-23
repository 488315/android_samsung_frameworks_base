package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes5.dex */
class DLFactory {
    static final DLSequence EMPTY_SEQUENCE = new DLSequence();
    static final DLSet EMPTY_SET = new DLSet();

    DLFactory() {
    }

    static DLSequence createSequence(ASN1EncodableVector aSN1EncodableVector) {
        if (aSN1EncodableVector.size() < 1) {
            return EMPTY_SEQUENCE;
        }
        return new DLSequence(aSN1EncodableVector);
    }

    static DLSet createSet(ASN1EncodableVector aSN1EncodableVector) {
        if (aSN1EncodableVector.size() < 1) {
            return EMPTY_SET;
        }
        return new DLSet(aSN1EncodableVector);
    }
}
