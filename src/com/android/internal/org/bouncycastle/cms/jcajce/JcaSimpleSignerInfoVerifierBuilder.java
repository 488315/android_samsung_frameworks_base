package com.android.internal.org.bouncycastle.cms.jcajce;

import com.android.internal.org.bouncycastle.cert.X509CertificateHolder;
import com.android.internal.org.bouncycastle.cms.DefaultCMSSignatureAlgorithmNameGenerator;
import com.android.internal.org.bouncycastle.cms.SignerInformationVerifier;
import com.android.internal.org.bouncycastle.operator.ContentVerifierProvider;
import com.android.internal.org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider;
import com.android.internal.org.bouncycastle.operator.OperatorCreationException;
import com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import com.android.internal.org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/* loaded from: classes5.dex */
public class JcaSimpleSignerInfoVerifierBuilder {
    private Helper helper = new Helper();

    public JcaSimpleSignerInfoVerifierBuilder setProvider(Provider provider) {
        this.helper = new ProviderHelper(provider);
        return this;
    }

    public JcaSimpleSignerInfoVerifierBuilder setProvider(String str) {
        this.helper = new NamedHelper(str);
        return this;
    }

    public SignerInformationVerifier build(X509CertificateHolder x509CertificateHolder) throws OperatorCreationException, CertificateException {
        return new SignerInformationVerifier(new DefaultCMSSignatureAlgorithmNameGenerator(), new DefaultSignatureAlgorithmIdentifierFinder(), this.helper.createContentVerifierProvider(x509CertificateHolder), this.helper.createDigestCalculatorProvider());
    }

    public SignerInformationVerifier build(X509Certificate x509Certificate) throws OperatorCreationException {
        return new SignerInformationVerifier(new DefaultCMSSignatureAlgorithmNameGenerator(), new DefaultSignatureAlgorithmIdentifierFinder(), this.helper.createContentVerifierProvider(x509Certificate), this.helper.createDigestCalculatorProvider());
    }

    public SignerInformationVerifier build(PublicKey publicKey) throws OperatorCreationException {
        return new SignerInformationVerifier(new DefaultCMSSignatureAlgorithmNameGenerator(), new DefaultSignatureAlgorithmIdentifierFinder(), this.helper.createContentVerifierProvider(publicKey), this.helper.createDigestCalculatorProvider());
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

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        ContentVerifierProvider createContentVerifierProvider(PublicKey publicKey) throws OperatorCreationException {
            return new JcaContentVerifierProviderBuilder().setProvider(this.providerName).build(publicKey);
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        ContentVerifierProvider createContentVerifierProvider(X509Certificate x509Certificate) throws OperatorCreationException {
            return new JcaContentVerifierProviderBuilder().setProvider(this.providerName).build(x509Certificate);
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        DigestCalculatorProvider createDigestCalculatorProvider() throws OperatorCreationException {
            return new JcaDigestCalculatorProviderBuilder().setProvider(this.providerName).build();
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
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

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        ContentVerifierProvider createContentVerifierProvider(PublicKey publicKey) throws OperatorCreationException {
            return new JcaContentVerifierProviderBuilder().setProvider(this.provider).build(publicKey);
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        ContentVerifierProvider createContentVerifierProvider(X509Certificate x509Certificate) throws OperatorCreationException {
            return new JcaContentVerifierProviderBuilder().setProvider(this.provider).build(x509Certificate);
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        DigestCalculatorProvider createDigestCalculatorProvider() throws OperatorCreationException {
            return new JcaDigestCalculatorProviderBuilder().setProvider(this.provider).build();
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        ContentVerifierProvider createContentVerifierProvider(X509CertificateHolder x509CertificateHolder) throws OperatorCreationException, CertificateException {
            return new JcaContentVerifierProviderBuilder().setProvider(this.provider).build(x509CertificateHolder);
        }
    }
}
