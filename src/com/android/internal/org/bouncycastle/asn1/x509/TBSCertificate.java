package com.android.internal.org.bouncycastle.asn1.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1BitString;
import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;
import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import com.android.internal.org.bouncycastle.util.Properties;

/* loaded from: classes5.dex */
public class TBSCertificate extends ASN1Object {
    Time endDate;
    Extensions extensions;
    X500Name issuer;
    ASN1BitString issuerUniqueId;
    ASN1Sequence seq;
    ASN1Integer serialNumber;
    AlgorithmIdentifier signature;
    Time startDate;
    X500Name subject;
    SubjectPublicKeyInfo subjectPublicKeyInfo;
    ASN1BitString subjectUniqueId;
    ASN1Integer version;

    public static TBSCertificate getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static TBSCertificate getInstance(Object obj) {
        if (obj instanceof TBSCertificate) {
            return (TBSCertificate) obj;
        }
        if (obj != null) {
            return new TBSCertificate(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private TBSCertificate(ASN1Sequence aSN1Sequence) {
        int i;
        boolean z;
        boolean z2;
        this.seq = aSN1Sequence;
        if (aSN1Sequence.getObjectAt(0) instanceof ASN1TaggedObject) {
            this.version = ASN1Integer.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(0), true);
            i = 0;
        } else {
            this.version = new ASN1Integer(0L);
            i = -1;
        }
        if (this.version.hasValue(0)) {
            z2 = false;
            z = true;
        } else if (this.version.hasValue(1)) {
            z = false;
            z2 = true;
        } else {
            if (!this.version.hasValue(2)) {
                throw new IllegalArgumentException("version number not recognised");
            }
            z = false;
            z2 = false;
        }
        this.serialNumber = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(i + 1));
        this.signature = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i + 2));
        this.issuer = X500Name.getInstance(aSN1Sequence.getObjectAt(i + 3));
        ASN1Sequence aSN1Sequence2 = (ASN1Sequence) aSN1Sequence.getObjectAt(i + 4);
        this.startDate = Time.getInstance(aSN1Sequence2.getObjectAt(0));
        this.endDate = Time.getInstance(aSN1Sequence2.getObjectAt(1));
        this.subject = X500Name.getInstance(aSN1Sequence.getObjectAt(i + 5));
        int i2 = i + 6;
        this.subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(aSN1Sequence.getObjectAt(i2));
        int size = (aSN1Sequence.size() - i2) - 1;
        if (size != 0 && z) {
            throw new IllegalArgumentException("version 1 certificate contains extra data");
        }
        while (size > 0) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Sequence.getObjectAt(i2 + size);
            int tagNo = aSN1TaggedObject.getTagNo();
            if (tagNo == 1) {
                this.issuerUniqueId = ASN1BitString.getInstance(aSN1TaggedObject, false);
            } else if (tagNo == 2) {
                this.subjectUniqueId = ASN1BitString.getInstance(aSN1TaggedObject, false);
            } else {
                if (tagNo != 3) {
                    throw new IllegalArgumentException("Unknown tag encountered in structure: " + aSN1TaggedObject.getTagNo());
                }
                if (z2) {
                    throw new IllegalArgumentException("version 2 certificate cannot contain extensions");
                }
                this.extensions = Extensions.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, true));
            }
            size--;
        }
    }

    public int getVersionNumber() {
        return this.version.intValueExact() + 1;
    }

    public ASN1Integer getVersion() {
        return this.version;
    }

    public ASN1Integer getSerialNumber() {
        return this.serialNumber;
    }

    public AlgorithmIdentifier getSignature() {
        return this.signature;
    }

    public X500Name getIssuer() {
        return this.issuer;
    }

    public Time getStartDate() {
        return this.startDate;
    }

    public Time getEndDate() {
        return this.endDate;
    }

    public X500Name getSubject() {
        return this.subject;
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.subjectPublicKeyInfo;
    }

    public ASN1BitString getIssuerUniqueId() {
        return this.issuerUniqueId;
    }

    public ASN1BitString getSubjectUniqueId() {
        return this.subjectUniqueId;
    }

    public Extensions getExtensions() {
        return this.extensions;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        if (Properties.getPropertyValue("com.android.internal.org.bouncycastle.x509.allow_non-der_tbscert") != null) {
            if (Properties.isOverrideSet("com.android.internal.org.bouncycastle.x509.allow_non-der_tbscert")) {
                return this.seq;
            }
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            if (!this.version.hasValue(0)) {
                aSN1EncodableVector.add(new DERTaggedObject(true, 0, (ASN1Encodable) this.version));
            }
            aSN1EncodableVector.add(this.serialNumber);
            aSN1EncodableVector.add(this.signature);
            aSN1EncodableVector.add(this.issuer);
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector(2);
            aSN1EncodableVector2.add(this.startDate);
            aSN1EncodableVector2.add(this.endDate);
            aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
            X500Name x500Name = this.subject;
            if (x500Name != null) {
                aSN1EncodableVector.add(x500Name);
            } else {
                aSN1EncodableVector.add(new DERSequence());
            }
            aSN1EncodableVector.add(this.subjectPublicKeyInfo);
            if (this.issuerUniqueId != null) {
                aSN1EncodableVector.add(new DERTaggedObject(false, 1, (ASN1Encodable) this.issuerUniqueId));
            }
            if (this.subjectUniqueId != null) {
                aSN1EncodableVector.add(new DERTaggedObject(false, 2, (ASN1Encodable) this.subjectUniqueId));
            }
            if (this.extensions != null) {
                aSN1EncodableVector.add(new DERTaggedObject(true, 3, (ASN1Encodable) this.extensions));
            }
            return new DERSequence(aSN1EncodableVector);
        }
        return this.seq;
    }
}
