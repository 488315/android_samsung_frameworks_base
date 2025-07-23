package com.android.internal.org.bouncycastle.asn1.cms;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* loaded from: classes5.dex */
public class CMSAlgorithmProtection extends ASN1Object {
    public static final int MAC = 2;
    public static final int SIGNATURE = 1;
    private final AlgorithmIdentifier digestAlgorithm;
    private final AlgorithmIdentifier macAlgorithm;
    private final AlgorithmIdentifier signatureAlgorithm;

    public CMSAlgorithmProtection(AlgorithmIdentifier algorithmIdentifier, int i, AlgorithmIdentifier algorithmIdentifier2) {
        if (algorithmIdentifier == null || algorithmIdentifier2 == null) {
            throw new NullPointerException("AlgorithmIdentifiers cannot be null");
        }
        this.digestAlgorithm = algorithmIdentifier;
        if (i == 1) {
            this.signatureAlgorithm = algorithmIdentifier2;
            this.macAlgorithm = null;
        } else if (i == 2) {
            this.signatureAlgorithm = null;
            this.macAlgorithm = algorithmIdentifier2;
        } else {
            throw new IllegalArgumentException("Unknown type: " + i);
        }
    }

    private CMSAlgorithmProtection(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new IllegalArgumentException("Sequence wrong size: One of signatureAlgorithm or macAlgorithm must be present");
        }
        this.digestAlgorithm = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(1));
        if (aSN1TaggedObject.getTagNo() == 1) {
            this.signatureAlgorithm = AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
            this.macAlgorithm = null;
        } else if (aSN1TaggedObject.getTagNo() == 2) {
            this.signatureAlgorithm = null;
            this.macAlgorithm = AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
        } else {
            throw new IllegalArgumentException("Unknown tag found: " + aSN1TaggedObject.getTagNo());
        }
    }

    public static CMSAlgorithmProtection getInstance(Object obj) {
        if (obj instanceof CMSAlgorithmProtection) {
            return (CMSAlgorithmProtection) obj;
        }
        if (obj != null) {
            return new CMSAlgorithmProtection(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public AlgorithmIdentifier getDigestAlgorithm() {
        return this.digestAlgorithm;
    }

    public AlgorithmIdentifier getMacAlgorithm() {
        return this.macAlgorithm;
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.digestAlgorithm);
        if (this.signatureAlgorithm != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, (ASN1Encodable) this.signatureAlgorithm));
        }
        if (this.macAlgorithm != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 2, (ASN1Encodable) this.macAlgorithm));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
