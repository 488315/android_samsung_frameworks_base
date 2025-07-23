package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Set;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.DERSet;
import com.android.internal.org.bouncycastle.asn1.cms.AttributeTable;
import com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier;
import com.android.internal.org.bouncycastle.asn1.cms.SignerInfo;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.cert.X509CertificateHolder;
import com.android.internal.org.bouncycastle.operator.ContentSigner;
import com.android.internal.org.bouncycastle.operator.DigestCalculator;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.io.TeeOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class SignerInfoGenerator {
    private byte[] calculatedDigest;
    private X509CertificateHolder certHolder;
    private final AlgorithmIdentifier digestAlgorithm;
    private final DigestCalculator digester;
    private final CMSAttributeTableGenerator sAttrGen;
    private final CMSSignatureEncryptionAlgorithmFinder sigEncAlgFinder;
    private final ContentSigner signer;
    private final SignerIdentifier signerIdentifier;
    private final CMSAttributeTableGenerator unsAttrGen;

    SignerInfoGenerator(SignerIdentifier signerIdentifier, ContentSigner contentSigner, AlgorithmIdentifier algorithmIdentifier, CMSSignatureEncryptionAlgorithmFinder cMSSignatureEncryptionAlgorithmFinder) {
        this.calculatedDigest = null;
        this.signerIdentifier = signerIdentifier;
        this.signer = contentSigner;
        this.digestAlgorithm = algorithmIdentifier;
        this.digester = null;
        this.sAttrGen = null;
        this.unsAttrGen = null;
        this.sigEncAlgFinder = cMSSignatureEncryptionAlgorithmFinder;
    }

    SignerInfoGenerator(SignerIdentifier signerIdentifier, ContentSigner contentSigner, DigestCalculator digestCalculator, CMSSignatureEncryptionAlgorithmFinder cMSSignatureEncryptionAlgorithmFinder, CMSAttributeTableGenerator cMSAttributeTableGenerator, CMSAttributeTableGenerator cMSAttributeTableGenerator2) {
        this.calculatedDigest = null;
        this.signerIdentifier = signerIdentifier;
        this.signer = contentSigner;
        this.digestAlgorithm = digestCalculator.getAlgorithmIdentifier();
        this.digester = digestCalculator;
        this.sAttrGen = cMSAttributeTableGenerator;
        this.unsAttrGen = cMSAttributeTableGenerator2;
        this.sigEncAlgFinder = cMSSignatureEncryptionAlgorithmFinder;
    }

    public SignerInfoGenerator(SignerInfoGenerator signerInfoGenerator, CMSAttributeTableGenerator cMSAttributeTableGenerator, CMSAttributeTableGenerator cMSAttributeTableGenerator2) {
        this.calculatedDigest = null;
        this.signerIdentifier = signerInfoGenerator.signerIdentifier;
        this.signer = signerInfoGenerator.signer;
        this.digestAlgorithm = signerInfoGenerator.digestAlgorithm;
        this.digester = signerInfoGenerator.digester;
        this.sigEncAlgFinder = signerInfoGenerator.sigEncAlgFinder;
        this.sAttrGen = cMSAttributeTableGenerator;
        this.unsAttrGen = cMSAttributeTableGenerator2;
    }

    public SignerIdentifier getSID() {
        return this.signerIdentifier;
    }

    public int getGeneratedVersion() {
        return this.signerIdentifier.isTagged() ? 3 : 1;
    }

    public boolean hasAssociatedCertificate() {
        return this.certHolder != null;
    }

    public X509CertificateHolder getAssociatedCertificate() {
        return this.certHolder;
    }

    public AlgorithmIdentifier getDigestAlgorithm() {
        return this.digestAlgorithm;
    }

    public OutputStream getCalculatingOutputStream() {
        DigestCalculator digestCalculator = this.digester;
        if (digestCalculator != null) {
            if (this.sAttrGen == null) {
                return new TeeOutputStream(this.digester.getOutputStream(), this.signer.getOutputStream());
            }
            return digestCalculator.getOutputStream();
        }
        return this.signer.getOutputStream();
    }

    public SignerInfo generate(ASN1ObjectIdentifier aSN1ObjectIdentifier) throws CMSException {
        AlgorithmIdentifier algorithmIdentifier;
        ASN1Set aSN1Set;
        try {
            AlgorithmIdentifier findEncryptionAlgorithm = this.sigEncAlgFinder.findEncryptionAlgorithm(this.signer.getAlgorithmIdentifier());
            ASN1Set aSN1Set2 = null;
            if (this.sAttrGen != null) {
                algorithmIdentifier = this.digester.getAlgorithmIdentifier();
                this.calculatedDigest = this.digester.getDigest();
                ASN1Set attributeSet = getAttributeSet(this.sAttrGen.getAttributes(Collections.unmodifiableMap(getBaseParameters(aSN1ObjectIdentifier, this.digester.getAlgorithmIdentifier(), findEncryptionAlgorithm, this.calculatedDigest))));
                OutputStream outputStream = this.signer.getOutputStream();
                outputStream.write(attributeSet.getEncoded(ASN1Encoding.DER));
                outputStream.close();
                aSN1Set = attributeSet;
            } else {
                algorithmIdentifier = this.digestAlgorithm;
                DigestCalculator digestCalculator = this.digester;
                if (digestCalculator != null) {
                    this.calculatedDigest = digestCalculator.getDigest();
                } else {
                    this.calculatedDigest = null;
                }
                aSN1Set = null;
            }
            AlgorithmIdentifier algorithmIdentifier2 = algorithmIdentifier;
            byte[] signature = this.signer.getSignature();
            if (this.unsAttrGen != null) {
                Map baseParameters = getBaseParameters(aSN1ObjectIdentifier, algorithmIdentifier2, findEncryptionAlgorithm, this.calculatedDigest);
                baseParameters.put(CMSAttributeTableGenerator.SIGNATURE, Arrays.clone(signature));
                aSN1Set2 = getAttributeSet(this.unsAttrGen.getAttributes(Collections.unmodifiableMap(baseParameters)));
            }
            return new SignerInfo(this.signerIdentifier, algorithmIdentifier2, aSN1Set, findEncryptionAlgorithm, new DEROctetString(signature), aSN1Set2);
        } catch (IOException e) {
            throw new CMSException("encoding error.", e);
        }
    }

    void setAssociatedCertificate(X509CertificateHolder x509CertificateHolder) {
        this.certHolder = x509CertificateHolder;
    }

    private ASN1Set getAttributeSet(AttributeTable attributeTable) {
        if (attributeTable != null) {
            return new DERSet(attributeTable.toASN1EncodableVector());
        }
        return null;
    }

    private Map getBaseParameters(ASN1ObjectIdentifier aSN1ObjectIdentifier, AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2, byte[] bArr) {
        HashMap hashMap = new HashMap();
        if (aSN1ObjectIdentifier != null) {
            hashMap.put("contentType", aSN1ObjectIdentifier);
        }
        hashMap.put(CMSAttributeTableGenerator.DIGEST_ALGORITHM_IDENTIFIER, algorithmIdentifier);
        hashMap.put(CMSAttributeTableGenerator.SIGNATURE_ALGORITHM_IDENTIFIER, algorithmIdentifier2);
        hashMap.put(CMSAttributeTableGenerator.DIGEST, Arrays.clone(bArr));
        return hashMap;
    }

    public byte[] getCalculatedDigest() {
        byte[] bArr = this.calculatedDigest;
        if (bArr != null) {
            return Arrays.clone(bArr);
        }
        return null;
    }

    public CMSAttributeTableGenerator getSignedAttributeTableGenerator() {
        return this.sAttrGen;
    }

    public CMSAttributeTableGenerator getUnsignedAttributeTableGenerator() {
        return this.unsAttrGen;
    }
}
