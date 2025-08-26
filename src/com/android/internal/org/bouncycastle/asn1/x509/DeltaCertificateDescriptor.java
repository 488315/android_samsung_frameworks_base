package com.android.internal.org.bouncycastle.asn1.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1BitString;
import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;
import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import java.util.Enumeration;

/* loaded from: classes5.dex */
public class DeltaCertificateDescriptor extends ASN1Object {
    private Extensions extensions;
    private X500Name issuer;
    private final ASN1Integer serialNumber;
    private AlgorithmIdentifier signature;
    private final ASN1BitString signatureValue;
    private X500Name subject;
    private SubjectPublicKeyInfo subjectPublicKeyInfo;
    private ASN1Sequence validity;

    public static DeltaCertificateDescriptor getInstance(Object obj) {
        if (obj instanceof DeltaCertificateDescriptor) {
            return (DeltaCertificateDescriptor) obj;
        }
        if (obj != null) {
            return new DeltaCertificateDescriptor(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static DeltaCertificateDescriptor fromExtensions(Extensions extensions) {
        return getInstance(Extensions.getExtensionParsedValue(extensions, Extension.deltaCertificateDescriptor));
    }

    private DeltaCertificateDescriptor(ASN1Sequence aSN1Sequence) {
        this.serialNumber = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
        ASN1Encodable objectAt = aSN1Sequence.getObjectAt(1);
        int i = 1;
        while (objectAt instanceof ASN1TaggedObject) {
            ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(objectAt);
            int tagNo = aSN1TaggedObject.getTagNo();
            if (tagNo == 0) {
                this.signature = AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
            } else if (tagNo == 1) {
                this.issuer = X500Name.getInstance(aSN1TaggedObject, true);
            } else if (tagNo == 2) {
                this.validity = ASN1Sequence.getInstance(aSN1TaggedObject, false);
            } else if (tagNo == 3) {
                this.subject = X500Name.getInstance(aSN1TaggedObject, true);
            }
            int i2 = i + 1;
            ASN1Encodable objectAt2 = aSN1Sequence.getObjectAt(i);
            i = i2;
            objectAt = objectAt2;
        }
        this.subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(objectAt);
        ASN1Encodable objectAt3 = aSN1Sequence.getObjectAt(i);
        while (objectAt3 instanceof ASN1TaggedObject) {
            ASN1TaggedObject aSN1TaggedObject2 = ASN1TaggedObject.getInstance(objectAt3);
            if (aSN1TaggedObject2.getTagNo() == 4) {
                this.extensions = Extensions.getInstance(aSN1TaggedObject2, false);
            }
            ASN1Encodable objectAt4 = aSN1Sequence.getObjectAt(i);
            i++;
            objectAt3 = objectAt4;
        }
        this.signatureValue = ASN1BitString.getInstance(objectAt3);
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

    public ASN1Sequence getValidity() {
        return this.validity;
    }

    public X500Name getSubject() {
        return this.subject;
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.subjectPublicKeyInfo;
    }

    public Extensions getExtensions() {
        return this.extensions;
    }

    public ASN1BitString getSignatureValue() {
        return this.signatureValue;
    }

    public DeltaCertificateDescriptor trimTo(TBSCertificate tBSCertificate, Extensions extensions) {
        ASN1Encodable aSN1Encodable;
        AlgorithmIdentifier algorithmIdentifier = tBSCertificate.signature;
        X500Name x500Name = tBSCertificate.issuer;
        DERSequence dERSequence = new DERSequence(new ASN1Encodable[]{tBSCertificate.startDate, tBSCertificate.endDate});
        X500Name x500Name2 = tBSCertificate.subject;
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(toASN1Primitive());
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Enumeration objects = aSN1Sequence.getObjects();
        aSN1EncodableVector.add((ASN1Encodable) objects.nextElement());
        Object objNextElement = objects.nextElement();
        while (true) {
            aSN1Encodable = (ASN1Encodable) objNextElement;
            if (!(aSN1Encodable instanceof ASN1TaggedObject)) {
                break;
            }
            ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(aSN1Encodable);
            int tagNo = aSN1TaggedObject.getTagNo();
            if (tagNo != 0) {
                if (tagNo != 1) {
                    if (tagNo == 2) {
                        if (!ASN1Sequence.getInstance(aSN1TaggedObject, false).equals((ASN1Primitive) dERSequence)) {
                            aSN1EncodableVector.add(aSN1Encodable);
                        }
                    } else if (tagNo == 3 && !X500Name.getInstance(aSN1TaggedObject, true).equals(x500Name2)) {
                        aSN1EncodableVector.add(aSN1Encodable);
                    }
                } else if (!X500Name.getInstance(aSN1TaggedObject, true).equals(x500Name)) {
                    aSN1EncodableVector.add(aSN1Encodable);
                }
            } else if (!AlgorithmIdentifier.getInstance(aSN1TaggedObject, false).equals(algorithmIdentifier)) {
                aSN1EncodableVector.add(aSN1Encodable);
            }
            objNextElement = objects.nextElement();
        }
        aSN1EncodableVector.add(aSN1Encodable);
        Object objNextElement2 = objects.nextElement();
        while (true) {
            ASN1Encodable aSN1Encodable2 = (ASN1Encodable) objNextElement2;
            if (aSN1Encodable2 instanceof ASN1TaggedObject) {
                ASN1TaggedObject aSN1TaggedObject2 = ASN1TaggedObject.getInstance(aSN1Encodable2);
                if (aSN1TaggedObject2.getTagNo() == 4) {
                    Extensions extensions2 = Extensions.getInstance(aSN1TaggedObject2, false);
                    ExtensionsGenerator extensionsGenerator = new ExtensionsGenerator();
                    Enumeration enumerationOids = extensions2.oids();
                    while (enumerationOids.hasMoreElements()) {
                        Extension extension = extensions2.getExtension((ASN1ObjectIdentifier) enumerationOids.nextElement());
                        Extension extension2 = extensions.getExtension(extension.getExtnId());
                        if (extension2 != null && !extension.equals(extension2)) {
                            extensionsGenerator.addExtension(extension);
                        }
                    }
                    if (!extensionsGenerator.isEmpty()) {
                        aSN1EncodableVector.add(new DERTaggedObject(false, 4, (ASN1Encodable) extensionsGenerator.generate()));
                    }
                }
                objNextElement2 = objects.nextElement();
            } else {
                aSN1EncodableVector.add(aSN1Encodable2);
                return new DeltaCertificateDescriptor(new DERSequence(aSN1EncodableVector));
            }
        }
    }

    private void addOptional(ASN1EncodableVector aSN1EncodableVector, int i, boolean z, ASN1Object aSN1Object) {
        if (aSN1Object != null) {
            aSN1EncodableVector.add(new DERTaggedObject(z, i, aSN1Object));
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(7);
        aSN1EncodableVector.add(this.serialNumber);
        addOptional(aSN1EncodableVector, 0, false, this.signature);
        addOptional(aSN1EncodableVector, 1, true, this.issuer);
        addOptional(aSN1EncodableVector, 2, false, this.validity);
        addOptional(aSN1EncodableVector, 3, true, this.subject);
        aSN1EncodableVector.add(this.subjectPublicKeyInfo);
        addOptional(aSN1EncodableVector, 4, false, this.extensions);
        aSN1EncodableVector.add(this.signatureValue);
        return new DERSequence(aSN1EncodableVector);
    }
}
