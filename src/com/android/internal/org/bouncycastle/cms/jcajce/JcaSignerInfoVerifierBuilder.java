package com.android.internal.org.bouncycastle.cms.jcajce;

import com.android.internal.org.bouncycastle.cert.X509CertificateHolder;
import com.android.internal.org.bouncycastle.cms.CMSSignatureAlgorithmNameGenerator;
import com.android.internal.org.bouncycastle.cms.DefaultCMSSignatureAlgorithmNameGenerator;
import com.android.internal.org.bouncycastle.cms.SignerInformationVerifier;
import com.android.internal.org.bouncycastle.operator.ContentVerifierProvider;
import com.android.internal.org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider;
import com.android.internal.org.bouncycastle.operator.OperatorCreationException;
import com.android.internal.org.bouncycastle.operator.SignatureAlgorithmIdentifierFinder;
import com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import com.android.internal.org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/* loaded from: classes5.dex */
public class JcaSignerInfoVerifierBuilder {
    private DigestCalculatorProvider digestProvider;
    private Helper helper = new Helper();
    private CMSSignatureAlgorithmNameGenerator sigAlgNameGen = new DefaultCMSSignatureAlgorithmNameGenerator();
    private SignatureAlgorithmIdentifierFinder sigAlgIDFinder = new DefaultSignatureAlgorithmIdentifierFinder();

    public JcaSignerInfoVerifierBuilder(DigestCalculatorProvider digestCalculatorProvider) {
        this.digestProvider = digestCalculatorProvider;
    }

    public JcaSignerInfoVerifierBuilder setProvider(Provider provider) {
        this.helper = new ProviderHelper(provider);
        return this;
    }

    public JcaSignerInfoVerifierBuilder setProvider(String str) {
        this.helper = new NamedHelper(str);
        return this;
    }

    public JcaSignerInfoVerifierBuilder setSignatureAlgorithmNameGenerator(CMSSignatureAlgorithmNameGenerator cMSSignatureAlgorithmNameGenerator) {
        this.sigAlgNameGen = cMSSignatureAlgorithmNameGenerator;
        return this;
    }

    public JcaSignerInfoVerifierBuilder setSignatureAlgorithmFinder(SignatureAlgorithmIdentifierFinder signatureAlgorithmIdentifierFinder) {
        this.sigAlgIDFinder = signatureAlgorithmIdentifierFinder;
        return this;
    }

    public SignerInformationVerifier build(X509CertificateHolder x509CertificateHolder) throws OperatorCreationException, CertificateException {
        return new SignerInformationVerifier(this.sigAlgNameGen, this.sigAlgIDFinder, this.helper.createContentVerifierProvider(x509CertificateHolder), this.digestProvider);
    }

    public SignerInformationVerifier build(X509Certificate x509Certificate) throws OperatorCreationException {
        return new SignerInformationVerifier(this.sigAlgNameGen, this.sigAlgIDFinder, this.helper.createContentVerifierProvider(x509Certificate), this.digestProvider);
    }

    public SignerInformationVerifier build(PublicKey publicKey) throws OperatorCreationException {
        return new SignerInformationVerifier(this.sigAlgNameGen, this.sigAlgIDFinder, this.helper.createContentVerifierProvider(publicKey), this.digestProvider);
    }

    private static class Helper {
        private Helper() {
        }

        ContentVerifierProvider createContentVerifierProvider(PublicKey publicKey) throws OperatorCreationException {
            return new JcaContentVerifierProviderBuilder().build(publicKey);
        }

        ContentVerifierProvider createContentVerifierProvider(X509Certificate x509Certificate) throws OperatorCreationException {
            return new JcaContentVerifierProviderBuilder().build(x509Certificate);
        }

        ContentVerifierProvider createContentVerifierProvider(X509CertificateHolder x509CertificateHolder) throws OperatorCreationException, CertificateException {
            return new JcaContentVerifierProviderBuilder().build(x509CertificateHolder);
        }

        DigestCalculatorProvider createDigestCalculatorProvider() throws OperatorCreationException {
            return new JcaDigestCalculatorProviderBuilder().build();
        }
    }

    private static class NamedHelper extends Helper {
        private final String providerName;

        public NamedHelper(String str) {
            super();
            this.providerName = str;
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper
        ContentVerifierProvider createContentVerifierProvider(PublicKey publicKey) throws OperatorCreationException {
            return new JcaContentVerifierProviderBuilder().setProvider(this.providerName).build(publicKey);
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper
        ContentVerifierProvider createContentVerifierProvider(X509Certificate x509Certificate) throws OperatorCreationException {
            return new JcaContentVerifierProviderBuilder().setProvider(this.providerName).build(x509Certificate);
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper
        DigestCalculatorProvider createDigestCalculatorProvider() throws OperatorCreationException {
            return new JcaDigestCalculatorProviderBuilder().setProvider(this.providerName).build();
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper
        ContentVerifierProvider createContentVerifierProvider(X509CertificateHolder x509CertificateHolder) throws OperatorCreationException, CertificateException {
            return new JcaContentVerifierProviderBuilder().setProvider(this.providerName).build(x509CertificateHolder);
        }
    }

    private static class ProviderHelper extends Helper {
        private final Provider provider;

        public ProviderHelper(Provider provider) {
            super();
            this.provider = provider;
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper
        ContentVerifierProvider createContentVerifierProvider(PublicKey publicKey) throws OperatorCreationException {
            return new JcaContentVerifierProviderBuilder().setProvider(this.provider).build(publicKey);
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper
        ContentVerifierProvider createContentVerifierProvider(X509Certificate x509Certificate) throws OperatorCreationException {
            return new JcaContentVerifierProviderBuilder().setProvider(this.provider).build(x509Certificate);
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper
        DigestCalculatorProvider createDigestCalculatorProvider() throws OperatorCreationException {
            return new JcaDigestCalculatorProviderBuilder().setProvider(this.provider).build();
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper
        ContentVerifierProvider createContentVerifierProvider(X509CertificateHolder x509CertificateHolder) throws OperatorCreationException, CertificateException {
            return new JcaContentVerifierProviderBuilder().setProvider(this.provider).build(x509CertificateHolder);
        }
    }
}
