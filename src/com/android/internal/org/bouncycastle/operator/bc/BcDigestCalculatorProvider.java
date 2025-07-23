package com.android.internal.org.bouncycastle.operator.bc;

import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.crypto.Digest;
import com.android.internal.org.bouncycastle.operator.DigestCalculator;
import com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider;
import com.android.internal.org.bouncycastle.operator.OperatorCreationException;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes5.dex */
public class BcDigestCalculatorProvider implements DigestCalculatorProvider {
    private BcDigestProvider digestProvider = BcDefaultDigestProvider.INSTANCE;

    @Override // com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider
    public DigestCalculator get(final AlgorithmIdentifier algorithmIdentifier) throws OperatorCreationException {
        final DigestOutputStream digestOutputStream = new DigestOutputStream(this.digestProvider.get(algorithmIdentifier));
        return new DigestCalculator(this) { // from class: com.android.internal.org.bouncycastle.operator.bc.BcDigestCalculatorProvider.1
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
    }

    private static class DigestOutputStream extends OutputStream {
        private Digest dig;

        DigestOutputStream(Digest digest) {
            this.dig = digest;
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            this.dig.update(bArr, i, i2);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            this.dig.update(bArr, 0, bArr.length);
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            this.dig.update((byte) i);
        }

        byte[] getDigest() {
            byte[] bArr = new byte[this.dig.getDigestSize()];
            this.dig.doFinal(bArr, 0);
            return bArr;
        }
    }
}
