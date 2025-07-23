package com.android.internal.org.bouncycastle.crypto.engines;

import com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher;
import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import com.android.internal.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes5.dex */
public class RSABlindedEngine implements AsymmetricBlockCipher {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private RSACoreEngine core = new RSACoreEngine();
    private RSAKeyParameters key;
    private SecureRandom random;

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public void init(boolean z, CipherParameters cipherParameters) {
        this.core.init(z, cipherParameters);
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            RSAKeyParameters rSAKeyParameters = (RSAKeyParameters) parametersWithRandom.getParameters();
            this.key = rSAKeyParameters;
            if (rSAKeyParameters instanceof RSAPrivateCrtKeyParameters) {
                this.random = parametersWithRandom.getRandom();
                return;
            } else {
                this.random = null;
                return;
            }
        }
        RSAKeyParameters rSAKeyParameters2 = (RSAKeyParameters) cipherParameters;
        this.key = rSAKeyParameters2;
        if (rSAKeyParameters2 instanceof RSAPrivateCrtKeyParameters) {
            this.random = CryptoServicesRegistrar.getSecureRandom();
        } else {
            this.random = null;
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getInputBlockSize() {
        return this.core.getInputBlockSize();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getOutputBlockSize() {
        return this.core.getOutputBlockSize();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public byte[] processBlock(byte[] bArr, int i, int i2) {
        if (this.key == null) {
            throw new IllegalStateException("RSA engine not initialised");
        }
        return this.core.convertOutput(processInput(this.core.convertInput(bArr, i, i2)));
    }

    private BigInteger processInput(BigInteger bigInteger) {
        RSAPrivateCrtKeyParameters rSAPrivateCrtKeyParameters;
        BigInteger publicExponent;
        RSAKeyParameters rSAKeyParameters = this.key;
        if ((rSAKeyParameters instanceof RSAPrivateCrtKeyParameters) && (publicExponent = (rSAPrivateCrtKeyParameters = (RSAPrivateCrtKeyParameters) rSAKeyParameters).getPublicExponent()) != null) {
            BigInteger modulus = rSAPrivateCrtKeyParameters.getModulus();
            BigInteger bigInteger2 = ONE;
            BigInteger createRandomInRange = BigIntegers.createRandomInRange(bigInteger2, modulus.subtract(bigInteger2), this.random);
            return BigIntegers.modOddInverse(modulus, createRandomInRange).multiply(this.core.processBlock(createRandomInRange.modPow(publicExponent, modulus).multiply(bigInteger).mod(modulus))).mod(modulus);
        }
        return this.core.processBlock(bigInteger);
    }
}
