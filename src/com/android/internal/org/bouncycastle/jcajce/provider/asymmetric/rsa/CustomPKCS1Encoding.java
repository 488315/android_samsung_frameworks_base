package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa;

import com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher;
import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.internal.org.bouncycastle.crypto.encodings.PKCS1Encoding;
import com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.Properties;
import java.security.SecureRandom;

/* compiled from: CustomPkcs1Encoding.java */
/* loaded from: classes5.dex */
class CustomPKCS1Encoding implements AsymmetricBlockCipher {
    private static final int HEADER_LENGTH = 10;
    private byte[] blockBuffer;
    private AsymmetricBlockCipher engine;
    private boolean forEncryption;
    private boolean forPrivateKey;
    private SecureRandom random;
    private boolean useStrictLength = useStrict();

    CustomPKCS1Encoding(AsymmetricBlockCipher asymmetricBlockCipher) {
        this.engine = asymmetricBlockCipher;
    }

    private boolean useStrict() {
        if (Properties.isOverrideSetTo(PKCS1Encoding.NOT_STRICT_LENGTH_ENABLED_PROPERTY, true)) {
            return false;
        }
        return !Properties.isOverrideSetTo(PKCS1Encoding.STRICT_LENGTH_ENABLED_PROPERTY, false);
    }

    public AsymmetricBlockCipher getUnderlyingCipher() {
        return this.engine;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public void init(boolean z, CipherParameters cipherParameters) {
        AsymmetricKeyParameter asymmetricKeyParameter;
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.random = parametersWithRandom.getRandom();
            asymmetricKeyParameter = (AsymmetricKeyParameter) parametersWithRandom.getParameters();
        } else {
            asymmetricKeyParameter = (AsymmetricKeyParameter) cipherParameters;
            if (!asymmetricKeyParameter.isPrivate() && z) {
                this.random = CryptoServicesRegistrar.getSecureRandom();
            }
        }
        this.engine.init(z, cipherParameters);
        this.forPrivateKey = asymmetricKeyParameter.isPrivate();
        this.forEncryption = z;
        this.blockBuffer = new byte[this.engine.getOutputBlockSize()];
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getInputBlockSize() {
        int inputBlockSize = this.engine.getInputBlockSize();
        return this.forEncryption ? inputBlockSize - 10 : inputBlockSize;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getOutputBlockSize() {
        int outputBlockSize = this.engine.getOutputBlockSize();
        return this.forEncryption ? outputBlockSize : outputBlockSize - 10;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public byte[] processBlock(byte[] bArr, int i, int i2) throws InvalidCipherTextException {
        if (this.forEncryption) {
            return encodeBlock(bArr, i, i2);
        }
        return decodeBlock(bArr, i, i2);
    }

    private byte[] encodeBlock(byte[] bArr, int i, int i2) throws InvalidCipherTextException {
        if (i2 > getInputBlockSize()) {
            throw new IllegalArgumentException("input data too large");
        }
        int inputBlockSize = this.engine.getInputBlockSize();
        byte[] bArr2 = new byte[inputBlockSize];
        if (this.forPrivateKey) {
            bArr2[0] = 1;
            for (int i3 = 1; i3 != (inputBlockSize - i2) - 1; i3++) {
                bArr2[i3] = -1;
            }
        } else {
            this.random.nextBytes(bArr2);
            bArr2[0] = 2;
            for (int i4 = 1; i4 != (inputBlockSize - i2) - 1; i4++) {
                while (bArr2[i4] == 0) {
                    bArr2[i4] = (byte) this.random.nextInt();
                }
            }
        }
        int i5 = inputBlockSize - i2;
        bArr2[i5 - 1] = 0;
        System.arraycopy(bArr, i, bArr2, i5, i2);
        return this.engine.processBlock(bArr2, 0, inputBlockSize);
    }

    private static int checkPkcs1Encoding1(byte[] bArr) {
        int i = 0;
        int i2 = -((bArr[0] & 255) ^ 1);
        int i3 = 0;
        for (int i4 = 1; i4 < bArr.length; i4++) {
            int i5 = bArr[i4] & 255;
            int i6 = (i5 - 1) >> 31;
            i ^= ((~i3) & i4) & i6;
            i3 |= i6;
            i2 |= ~((((i5 ^ 255) - 1) >> 31) | i3);
        }
        return ((bArr.length - 1) - i) | (((i - 9) | i2) >> 31);
    }

    private static int checkPkcs1Encoding2(byte[] bArr) {
        int i = 0;
        int i2 = -((bArr[0] & 255) ^ 2);
        int i3 = 0;
        for (int i4 = 1; i4 < bArr.length; i4++) {
            int i5 = ((bArr[i4] & 255) - 1) >> 31;
            i ^= ((~i3) & i4) & i5;
            i3 |= i5;
        }
        return ((bArr.length - 1) - i) | ((i2 | (i - 9)) >> 31);
    }

    private byte[] decodeBlock(byte[] bArr, int i, int i2) throws InvalidCipherTextException {
        int outputBlockSize = this.engine.getOutputBlockSize();
        byte[] processBlock = this.engine.processBlock(bArr, i, i2);
        boolean z = this.useStrictLength & (processBlock.length != outputBlockSize);
        byte[] bArr2 = processBlock.length < outputBlockSize ? this.blockBuffer : processBlock;
        int checkPkcs1Encoding2 = this.forPrivateKey ? checkPkcs1Encoding2(bArr2) : checkPkcs1Encoding1(bArr2);
        if (!(z | (checkPkcs1Encoding2 < 0))) {
            try {
                byte[] bArr3 = new byte[checkPkcs1Encoding2];
                System.arraycopy(bArr2, bArr2.length - checkPkcs1Encoding2, bArr3, 0, checkPkcs1Encoding2);
                return bArr3;
            } finally {
                Arrays.fill(processBlock, (byte) 0);
                byte[] bArr4 = this.blockBuffer;
                Arrays.fill(bArr4, 0, Math.max(0, bArr4.length - processBlock.length), (byte) 0);
            }
        }
        Arrays.fill(processBlock, (byte) 0);
        byte[] bArr5 = this.blockBuffer;
        Arrays.fill(bArr5, 0, Math.max(0, bArr5.length - processBlock.length), (byte) 0);
        return null;
    }
}
