package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.cert.X509CertificateHolder;
import com.android.internal.org.bouncycastle.operator.ContentVerifier;
import com.android.internal.org.bouncycastle.operator.ContentVerifierProvider;
import com.android.internal.org.bouncycastle.operator.DigestCalculator;
import com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider;
import com.android.internal.org.bouncycastle.operator.OperatorCreationException;
import com.android.internal.org.bouncycastle.operator.SignatureAlgorithmIdentifierFinder;

/* loaded from: classes5.dex */
public class SignerInformationVerifier {
    private DigestCalculatorProvider digestProvider;
    private SignatureAlgorithmIdentifierFinder sigAlgorithmFinder;
    private CMSSignatureAlgorithmNameGenerator sigNameGenerator;
    private ContentVerifierProvider verifierProvider;

    public SignerInformationVerifier(CMSSignatureAlgorithmNameGenerator cMSSignatureAlgorithmNameGenerator, SignatureAlgorithmIdentifierFinder signatureAlgorithmIdentifierFinder, ContentVerifierProvider contentVerifierProvider, DigestCalculatorProvider digestCalculatorProvider) {
        this.sigNameGenerator = cMSSignatureAlgorithmNameGenerator;
        this.sigAlgorithmFinder = signatureAlgorithmIdentifierFinder;
        this.verifierProvider = contentVerifierProvider;
        this.digestProvider = digestCalculatorProvider;
    }

    public boolean hasAssociatedCertificate() {
        return this.verifierProvider.hasAssociatedCertificate();
    }

    public X509CertificateHolder getAssociatedCertificate() {
        return this.verifierProvider.getAssociatedCertificate();
    }

    public ContentVerifier getContentVerifier(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2) throws OperatorCreationException {
        return this.verifierProvider.get(new AlgorithmIdentifier(this.sigAlgorithmFinder.find(this.sigNameGenerator.getSignatureName(algorithmIdentifier2, algorithmIdentifier)).getAlgorithm(), algorithmIdentifier.getParameters()));
    }

    public DigestCalculator getDigestCalculator(AlgorithmIdentifier algorithmIdentifier) throws OperatorCreationException {
        return this.digestProvider.get(algorithmIdentifier);
    }
}
