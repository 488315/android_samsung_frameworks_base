package com.android.internal.org.bouncycastle.asn1.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1UTCTime;
import com.android.internal.org.bouncycastle.asn1.DERBitString;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;
import com.android.internal.org.bouncycastle.asn1.x500.X500Name;

/* loaded from: classes5.dex */
public class V3TBSCertificateGenerator {
    private boolean altNamePresentAndCritical;
    Time endDate;
    Extensions extensions;
    X500Name issuer;
    private DERBitString issuerUniqueID;
    ASN1Integer serialNumber;
    AlgorithmIdentifier signature;
    Time startDate;
    X500Name subject;
    SubjectPublicKeyInfo subjectPublicKeyInfo;
    private DERBitString subjectUniqueID;
    DERTaggedObject version = new DERTaggedObject(true, 0, (ASN1Encodable) new ASN1Integer(2));

    public void setSerialNumber(ASN1Integer aSN1Integer) {
        this.serialNumber = aSN1Integer;
    }

    public void setSignature(AlgorithmIdentifier algorithmIdentifier) {
        this.signature = algorithmIdentifier;
    }

    public void setIssuer(X509Name x509Name) {
        this.issuer = X500Name.getInstance(x509Name);
    }

    public void setIssuer(X500Name x500Name) {
        this.issuer = x500Name;
    }

    public void setStartDate(ASN1UTCTime aSN1UTCTime) {
        this.startDate = new Time(aSN1UTCTime);
    }

    public void setStartDate(Time time) {
        this.startDate = time;
    }

    public void setEndDate(ASN1UTCTime aSN1UTCTime) {
        this.endDate = new Time(aSN1UTCTime);
    }

    public void setEndDate(Time time) {
        this.endDate = time;
    }

    public void setSubject(X509Name x509Name) {
        this.subject = X500Name.getInstance(x509Name.toASN1Primitive());
    }

    public void setSubject(X500Name x500Name) {
        this.subject = x500Name;
    }

    public void setIssuerUniqueID(DERBitString dERBitString) {
        this.issuerUniqueID = dERBitString;
    }

    public void setSubjectUniqueID(DERBitString dERBitString) {
        this.subjectUniqueID = dERBitString;
    }

    public void setSubjectPublicKeyInfo(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.subjectPublicKeyInfo = subjectPublicKeyInfo;
    }

    public void setExtensions(X509Extensions x509Extensions) {
        setExtensions(Extensions.getInstance(x509Extensions));
    }

    public void setExtensions(Extensions extensions) {
        Extension extension;
        this.extensions = extensions;
        if (extensions == null || (extension = extensions.getExtension(Extension.subjectAlternativeName)) == null || !extension.isCritical()) {
            return;
        }
        this.altNamePresentAndCritical = true;
    }

    public ASN1Sequence generatePreTBSCertificate() {
        if (this.signature != null) {
            throw new IllegalStateException("signature field should not be set in PreTBSCertificate");
        }
        if (this.serialNumber == null || this.issuer == null || this.startDate == null || this.endDate == null || ((this.subject == null && !this.altNamePresentAndCritical) || this.subjectPublicKeyInfo == null)) {
            throw new IllegalStateException("not all mandatory fields set in V3 TBScertificate generator");
        }
        return generateTBSStructure();
    }

    private ASN1Sequence generateTBSStructure() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(10);
        aSN1EncodableVector.add(this.version);
        aSN1EncodableVector.add(this.serialNumber);
        AlgorithmIdentifier algorithmIdentifier = this.signature;
        if (algorithmIdentifier != null) {
            aSN1EncodableVector.add(algorithmIdentifier);
        }
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
        if (this.issuerUniqueID != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, (ASN1Encodable) this.issuerUniqueID));
        }
        if (this.subjectUniqueID != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 2, (ASN1Encodable) this.subjectUniqueID));
        }
        if (this.extensions != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 3, (ASN1Encodable) this.extensions));
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public TBSCertificate generateTBSCertificate() {
        if (this.serialNumber == null || this.signature == null || this.issuer == null || this.startDate == null || this.endDate == null || ((this.subject == null && !this.altNamePresentAndCritical) || this.subjectPublicKeyInfo == null)) {
            throw new IllegalStateException("not all mandatory fields set in V3 TBScertificate generator");
        }
        return TBSCertificate.getInstance(generateTBSStructure());
    }
}
