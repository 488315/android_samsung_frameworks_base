package com.android.internal.org.bouncycastle.cms.jcajce;

import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.cert.X509CertificateHolder;
import com.android.internal.org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator;
import com.android.internal.org.bouncycastle.cms.CMSSignatureEncryptionAlgorithmFinder;
import com.android.internal.org.bouncycastle.cms.DefaultCMSSignatureEncryptionAlgorithmFinder;
import com.android.internal.org.bouncycastle.cms.SignerInfoGenerator;
import com.android.internal.org.bouncycastle.cms.SignerInfoGeneratorBuilder;
import com.android.internal.org.bouncycastle.operator.ContentSigner;
import com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider;
import com.android.internal.org.bouncycastle.operator.OperatorCreationException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

/* loaded from: classes5.dex */
public class JcaSignerInfoGeneratorBuilder {
    private SignerInfoGeneratorBuilder builder;

    public JcaSignerInfoGeneratorBuilder(DigestCalculatorProvider digestCalculatorProvider) {
        this(digestCalculatorProvider, new DefaultCMSSignatureEncryptionAlgorithmFinder());
    }

    public JcaSignerInfoGeneratorBuilder(DigestCalculatorProvider digestCalculatorProvider, CMSSignatureEncryptionAlgorithmFinder cMSSignatureEncryptionAlgorithmFinder) {
        this.builder = new SignerInfoGeneratorBuilder(digestCalculatorProvider, cMSSignatureEncryptionAlgorithmFinder);
    }

    public JcaSignerInfoGeneratorBuilder setDirectSignature(boolean z) {
        this.builder.setDirectSignature(z);
        return this;
    }

    public JcaSignerInfoGeneratorBuilder setContentDigest(AlgorithmIdentifier algorithmIdentifier) {
        this.builder.setContentDigest(algorithmIdentifier);
        return this;
    }

    public JcaSignerInfoGeneratorBuilder setSignedAttributeGenerator(CMSAttributeTableGenerator cMSAttributeTableGenerator) {
        this.builder.setSignedAttributeGenerator(cMSAttributeTableGenerator);
        return this;
    }

    public JcaSignerInfoGeneratorBuilder setUnsignedAttributeGenerator(CMSAttributeTableGenerator cMSAttributeTableGenerator) {
        this.builder.setUnsignedAttributeGenerator(cMSAttributeTableGenerator);
        return this;
    }

    public SignerInfoGenerator build(ContentSigner contentSigner, X509CertificateHolder x509CertificateHolder) throws OperatorCreationException {
        return this.builder.build(contentSigner, x509CertificateHolder);
    }

    public SignerInfoGenerator build(ContentSigner contentSigner, byte[] bArr) throws OperatorCreationException {
        return this.builder.build(contentSigner, bArr);
    }

    public SignerInfoGenerator build(ContentSigner contentSigner, X509Certificate x509Certificate) throws OperatorCreationException, CertificateEncodingException {
        return build(contentSigner, new JcaX509CertificateHolder(x509Certificate));
    }
}
