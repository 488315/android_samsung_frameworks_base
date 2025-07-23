package com.android.internal.org.bouncycastle.operator.jcajce;

import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper;
import com.android.internal.org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import com.android.internal.org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import com.android.internal.org.bouncycastle.operator.DigestCalculator;
import com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider;
import com.android.internal.org.bouncycastle.operator.OperatorCreationException;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.Provider;

/* loaded from: classes5.dex */
public class JcaDigestCalculatorProviderBuilder {
    private OperatorHelper helper = new OperatorHelper(new DefaultJcaJceHelper());

    public JcaDigestCalculatorProviderBuilder setHelper(JcaJceHelper jcaJceHelper) {
        this.helper = new OperatorHelper(jcaJceHelper);
        return this;
    }

    public JcaDigestCalculatorProviderBuilder setProvider(Provider provider) {
        this.helper = new OperatorHelper(new ProviderJcaJceHelper(provider));
        return this;
    }

    public JcaDigestCalculatorProviderBuilder setProvider(String str) {
        this.helper = new OperatorHelper(new NamedJcaJceHelper(str));
        return this;
    }

    public DigestCalculatorProvider build() throws OperatorCreationException {
        return new DigestCalculatorProvider() { // from class: com.android.internal.org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder.1
            @Override // com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider
            public DigestCalculator get(final AlgorithmIdentifier algorithmIdentifier) throws OperatorCreationException {
                try {
                    final DigestOutputStream digestOutputStream = new DigestOutputStream(JcaDigestCalculatorProviderBuilder.this.helper.createDigest(algorithmIdentifier));
                    return new DigestCalculator(this) { // from class: com.android.internal.org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder.1.1
                        @Override // com.android.internal.org.bouncycastle.operator.DigestCalculator
                        public AlgorithmIdentifier getAlgorithmIdentifier() {
                            return algorithmIdentifier;
                        }

                        @Override // com.android.internal.org.bouncycastle.operator.DigestCalculator
                        public OutputStream getOutputStream() {
                            return digestOutputStream;
                        }

                        @Override // com.android.internal.org.bouncycastle.operator.DigestCalculator
                        public byte[] getDigest() {
                            return digestOutputStream.getDigest();
                        }
                    };
                } catch (GeneralSecurityException e) {
                    throw new OperatorCreationException("exception on setup: " + e, e);
                }
            }
        };
    }

    private static class DigestOutputStream extends OutputStream {
        private MessageDigest dig;

        DigestOutputStream(MessageDigest messageDigest) {
            this.dig = messageDigest;
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            this.dig.update(bArr, i, i2);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            this.dig.update(bArr);
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            this.dig.update((byte) i);
        }

        byte[] getDigest() {
            return this.dig.digest();
        }
    }
}
