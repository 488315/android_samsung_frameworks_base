package com.android.internal.org.bouncycastle.pkcs;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.pkcs.Attribute;
import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.Extensions;
import com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

/* loaded from: classes5.dex */
public class DeltaCertificateRequestAttributeValue implements ASN1Encodable {
    private final ASN1Sequence attrSeq;
    private final Extensions extensions;
    private final AlgorithmIdentifier signatureAlgorithm;
    private final X500Name subject;
    private final SubjectPublicKeyInfo subjectPKInfo;

    public DeltaCertificateRequestAttributeValue(Attribute attribute) {
        this(ASN1Sequence.getInstance(attribute.getAttributeValues()[0]));
    }

    DeltaCertificateRequestAttributeValue(ASN1Sequence aSN1Sequence) {
        int i;
        AlgorithmIdentifier algorithmIdentifier;
        this.attrSeq = aSN1Sequence;
        Extensions extensions = null;
        if (aSN1Sequence.getObjectAt(0) instanceof ASN1TaggedObject) {
            this.subject = X500Name.getInstance(ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(0)), true);
            i = 1;
        } else {
            this.subject = null;
            i = 0;
        }
        this.subjectPKInfo = SubjectPublicKeyInfo.getInstance(aSN1Sequence.getObjectAt(i));
        int i2 = i + 1;
        if (i2 != aSN1Sequence.size()) {
            algorithmIdentifier = null;
            while (i2 < aSN1Sequence.size()) {
                ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i2));
                if (aSN1TaggedObject.getTagNo() == 1) {
                    extensions = Extensions.getInstance(aSN1TaggedObject, false);
                } else if (aSN1TaggedObject.getTagNo() == 2) {
                    algorithmIdentifier = AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
                } else {
                    throw new IllegalArgumentException("unknown tag");
                }
                i2++;
            }
        } else {
            algorithmIdentifier = null;
        }
        this.extensions = extensions;
        this.signatureAlgorithm = algorithmIdentifier;
    }

    public X500Name getSubject() {
        return this.subject;
    }

    public SubjectPublicKeyInfo getSubjectPKInfo() {
        return this.subjectPKInfo;
    }

    public Extensions getExtensions() {
        return this.extensions;
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return this.attrSeq;
    }
}
