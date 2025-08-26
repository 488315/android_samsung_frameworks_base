package com.android.internal.org.bouncycastle.cert;

import com.android.internal.org.bouncycastle.asn1.ASN1BitString;
import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERBitString;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;
import com.android.internal.org.bouncycastle.asn1.x509.Certificate;
import com.android.internal.org.bouncycastle.asn1.x509.Extension;
import com.android.internal.org.bouncycastle.asn1.x509.ExtensionsGenerator;
import java.io.IOException;

/* loaded from: classes5.dex */
public class DeltaCertificateTool {
    public static Extension makeDeltaCertificateExtension(boolean z, X509CertificateHolder x509CertificateHolder) throws IOException {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new ASN1Integer(x509CertificateHolder.getSerialNumber()));
        aSN1EncodableVector.add(new DERTaggedObject(false, 0, (ASN1Encodable) x509CertificateHolder.getSignatureAlgorithm()));
        aSN1EncodableVector.add(new DERTaggedObject(false, 1, (ASN1Encodable) x509CertificateHolder.getIssuer()));
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector(2);
        aSN1EncodableVector2.add(x509CertificateHolder.toASN1Structure().getStartDate());
        aSN1EncodableVector2.add(x509CertificateHolder.toASN1Structure().getEndDate());
        aSN1EncodableVector.add(new DERTaggedObject(false, 2, (ASN1Encodable) new DERSequence(aSN1EncodableVector2)));
        aSN1EncodableVector.add(new DERTaggedObject(false, 3, (ASN1Encodable) x509CertificateHolder.getSubject()));
        aSN1EncodableVector.add(x509CertificateHolder.getSubjectPublicKeyInfo());
        if (x509CertificateHolder.getExtensions() != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 4, (ASN1Encodable) x509CertificateHolder.getExtensions()));
        }
        aSN1EncodableVector.add(new DERBitString(x509CertificateHolder.getSignature()));
        return new Extension(Extension.deltaCertificateDescriptor, z, new DERSequence(aSN1EncodableVector).getEncoded(ASN1Encoding.DER));
    }

    public static X509CertificateHolder extractDeltaCertificate(X509CertificateHolder x509CertificateHolder) {
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(x509CertificateHolder.getExtension(Extension.deltaCertificateDescriptor).getParsedValue());
        ASN1Sequence aSN1Sequence2 = ASN1Sequence.getInstance(x509CertificateHolder.toASN1Structure().getTBSCertificate().toASN1Primitive());
        ASN1Encodable[] array = aSN1Sequence2.toArray();
        array[0] = aSN1Sequence2.getObjectAt(0);
        array[1] = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
        ASN1Encodable objectAt = aSN1Sequence.getObjectAt(1);
        int i = 2;
        while (objectAt instanceof ASN1TaggedObject) {
            ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(objectAt);
            int tagNo = aSN1TaggedObject.getTagNo();
            if (tagNo == 0) {
                array[2] = ASN1Sequence.getInstance(aSN1TaggedObject, false);
            } else if (tagNo == 1) {
                array[3] = ASN1Sequence.getInstance(aSN1TaggedObject, true);
            } else if (tagNo == 2) {
                array[4] = ASN1Sequence.getInstance(aSN1TaggedObject, false);
            } else if (tagNo == 3) {
                array[5] = ASN1Sequence.getInstance((ASN1TaggedObject) objectAt, true);
            }
            int i2 = i + 1;
            ASN1Encodable objectAt2 = aSN1Sequence.getObjectAt(i);
            i = i2;
            objectAt = objectAt2;
        }
        array[6] = objectAt;
        if (array[2] == null) {
            array[2] = aSN1Sequence2.getObjectAt(2);
        }
        if (array[3] == null) {
            array[3] = aSN1Sequence2.getObjectAt(3);
        }
        if (array[4] == null) {
            array[4] = aSN1Sequence2.getObjectAt(4);
        }
        if (array[5] == null) {
            array[5] = aSN1Sequence2.getObjectAt(5);
        }
        ExtensionsGenerator extensionsGeneratorExtractExtensions = extractExtensions(aSN1Sequence2);
        if (i < aSN1Sequence.size() - 1) {
            ASN1TaggedObject aSN1TaggedObject2 = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i));
            if (aSN1TaggedObject2.getTagNo() != 4) {
                throw new IllegalArgumentException("malformed delta extension");
            }
            ASN1Sequence aSN1Sequence3 = ASN1Sequence.getInstance(aSN1TaggedObject2, false);
            for (int i3 = 0; i3 != aSN1Sequence3.size(); i3++) {
                extensionsGeneratorExtractExtensions.replaceExtension(Extension.getInstance(aSN1Sequence3.getObjectAt(i3)));
            }
            array[7] = new DERTaggedObject(3, extensionsGeneratorExtractExtensions.generate());
        } else if (!extensionsGeneratorExtractExtensions.isEmpty()) {
            array[7] = new DERTaggedObject(3, extensionsGeneratorExtractExtensions.generate());
        } else {
            array[7] = null;
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(7);
        for (int i4 = 0; i4 != array.length; i4++) {
            ASN1Encodable aSN1Encodable = array[i4];
            if (aSN1Encodable != null) {
                aSN1EncodableVector.add(aSN1Encodable);
            }
        }
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        aSN1EncodableVector2.add(new DERSequence(aSN1EncodableVector));
        aSN1EncodableVector2.add(ASN1Sequence.getInstance(array[2]));
        aSN1EncodableVector2.add(ASN1BitString.getInstance(aSN1Sequence.getObjectAt(aSN1Sequence.size() - 1)));
        return new X509CertificateHolder(Certificate.getInstance(new DERSequence(aSN1EncodableVector2)));
    }

    private static ExtensionsGenerator extractExtensions(ASN1Sequence aSN1Sequence) {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = Extension.deltaCertificateDescriptor;
        ASN1Sequence aSN1Sequence2 = ASN1Sequence.getInstance(ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(aSN1Sequence.size() - 1)), true);
        ExtensionsGenerator extensionsGenerator = new ExtensionsGenerator();
        for (int i = 0; i != aSN1Sequence2.size(); i++) {
            Extension extension = Extension.getInstance(aSN1Sequence2.getObjectAt(i));
            if (!aSN1ObjectIdentifier.equals((ASN1Primitive) extension.getExtnId())) {
                extensionsGenerator.addExtension(extension);
            }
        }
        return extensionsGenerator;
    }
}
