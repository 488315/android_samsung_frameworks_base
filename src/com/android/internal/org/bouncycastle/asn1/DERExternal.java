package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes5.dex */
public class DERExternal extends ASN1External {
    @Override // com.android.internal.org.bouncycastle.asn1.ASN1External, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDERObject() {
        return this;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1External, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDLObject() {
        return this;
    }

    public DERExternal(ASN1EncodableVector aSN1EncodableVector) {
        this(DERFactory.createSequence(aSN1EncodableVector));
    }

    public DERExternal(DERSequence dERSequence) {
        super(dERSequence);
    }

    public DERExternal(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Integer aSN1Integer, ASN1Primitive aSN1Primitive, DERTaggedObject dERTaggedObject) {
        super(aSN1ObjectIdentifier, aSN1Integer, aSN1Primitive, dERTaggedObject);
    }

    public DERExternal(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Integer aSN1Integer, ASN1Primitive aSN1Primitive, int i, ASN1Primitive aSN1Primitive2) {
        super(aSN1ObjectIdentifier, aSN1Integer, aSN1Primitive, i, aSN1Primitive2);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1External
    ASN1Sequence buildSequence() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(4);
        if (this.directReference != null) {
            aSN1EncodableVector.add(this.directReference);
        }
        if (this.indirectReference != null) {
            aSN1EncodableVector.add(this.indirectReference);
        }
        if (this.dataValueDescriptor != null) {
            aSN1EncodableVector.add(this.dataValueDescriptor.toDERObject());
        }
        aSN1EncodableVector.add(new DERTaggedObject(this.encoding == 0, this.encoding, this.externalContent));
        return new DERSequence(aSN1EncodableVector);
    }
}
