package com.android.internal.org.bouncycastle.crypto.generators;

import android.content.pm.PackageManager;
import com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import com.android.internal.org.bouncycastle.crypto.CryptoServicePurpose;
import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters;
import com.android.internal.org.bouncycastle.crypto.constraints.ConstraintUtils;
import com.android.internal.org.bouncycastle.crypto.constraints.DefaultServiceProperties;
import com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.internal.org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import com.android.internal.org.bouncycastle.math.Primes;
import com.android.internal.org.bouncycastle.math.ec.WNafUtil;
import com.android.internal.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class RSAKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private RSAKeyGenerationParameters param;

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.param = (RSAKeyGenerationParameters) keyGenerationParameters;
        CryptoServicesRegistrar.checkConstraints(new DefaultServiceProperties("RSAKeyGen", ConstraintUtils.bitsOfSecurityForFF(keyGenerationParameters.getStrength()), null, CryptoServicePurpose.KEYGEN));
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public AsymmetricCipherKeyPair generateKeyPair() {
        BigInteger chooseRandomPrime;
        BigInteger multiply;
        RSAKeyPairGenerator rSAKeyPairGenerator = this;
        int strength = rSAKeyPairGenerator.param.getStrength();
        int i = (strength + 1) / 2;
        int i2 = strength - i;
        int i3 = strength / 2;
        int i4 = i3 - 100;
        int i5 = strength / 3;
        if (i4 < i5) {
            i4 = i5;
        }
        int i6 = strength >> 2;
        BigInteger pow = BigInteger.valueOf(2L).pow(i3);
        BigInteger bigInteger = ONE;
        BigInteger shiftLeft = bigInteger.shiftLeft(strength - 1);
        BigInteger shiftLeft2 = bigInteger.shiftLeft(i4);
        AsymmetricCipherKeyPair asymmetricCipherKeyPair = null;
        boolean z = false;
        while (!z) {
            BigInteger publicExponent = rSAKeyPairGenerator.param.getPublicExponent();
            BigInteger chooseRandomPrime2 = rSAKeyPairGenerator.chooseRandomPrime(i, publicExponent, shiftLeft);
            while (true) {
                chooseRandomPrime = rSAKeyPairGenerator.chooseRandomPrime(i2, publicExponent, shiftLeft);
                BigInteger abs = chooseRandomPrime.subtract(chooseRandomPrime2).abs();
                if (abs.bitLength() >= i4 && abs.compareTo(shiftLeft2) > 0) {
                    multiply = chooseRandomPrime2.multiply(chooseRandomPrime);
                    if (multiply.bitLength() != strength) {
                        chooseRandomPrime2 = chooseRandomPrime2.max(chooseRandomPrime);
                    } else {
                        if (WNafUtil.getNafWeight(multiply) >= i6) {
                            break;
                        }
                        chooseRandomPrime2 = rSAKeyPairGenerator.chooseRandomPrime(i, publicExponent, shiftLeft);
                    }
                } else {
                    rSAKeyPairGenerator = this;
                    strength = strength;
                }
            }
            if (chooseRandomPrime2.compareTo(chooseRandomPrime) < 0) {
                chooseRandomPrime = chooseRandomPrime2;
                chooseRandomPrime2 = chooseRandomPrime;
            }
            BigInteger bigInteger2 = ONE;
            BigInteger subtract = chooseRandomPrime2.subtract(bigInteger2);
            BigInteger subtract2 = chooseRandomPrime.subtract(bigInteger2);
            int i7 = strength;
            BigInteger modInverse = publicExponent.modInverse(subtract.divide(subtract.gcd(subtract2)).multiply(subtract2));
            if (modInverse.compareTo(pow) > 0) {
                z = true;
                asymmetricCipherKeyPair = new AsymmetricCipherKeyPair((AsymmetricKeyParameter) new RSAKeyParameters(false, multiply, publicExponent, true), (AsymmetricKeyParameter) new RSAPrivateCrtKeyParameters(multiply, publicExponent, modInverse, chooseRandomPrime2, chooseRandomPrime, modInverse.remainder(subtract), modInverse.remainder(subtract2), BigIntegers.modOddInverse(chooseRandomPrime2, chooseRandomPrime), true));
            }
            rSAKeyPairGenerator = this;
            strength = i7;
        }
        return asymmetricCipherKeyPair;
    }

    protected BigInteger chooseRandomPrime(int i, BigInteger bigInteger, BigInteger bigInteger2) {
        for (int i2 = 0; i2 != i * 5; i2++) {
            BigInteger createRandomPrime = BigIntegers.createRandomPrime(i, 1, this.param.getRandom());
            BigInteger mod = createRandomPrime.mod(bigInteger);
            BigInteger bigInteger3 = ONE;
            if (!mod.equals(bigInteger3) && createRandomPrime.multiply(createRandomPrime).compareTo(bigInteger2) >= 0 && isProbablePrime(createRandomPrime) && bigInteger.gcd(createRandomPrime.subtract(bigInteger3)).equals(bigInteger3)) {
                return createRandomPrime;
            }
        }
        throw new IllegalStateException("unable to generate prime number for RSA key");
    }

    protected boolean isProbablePrime(BigInteger bigInteger) {
        return !Primes.hasAnySmallFactors(bigInteger) && Primes.isMRProbablePrime(bigInteger, this.param.getRandom(), getNumberOfIterations(bigInteger.bitLength(), this.param.getCertainty()));
    }

    private static int getNumberOfIterations(int i, int i2) {
        if (i >= 1536) {
            if (i2 <= 100) {
                return 3;
            }
            if (i2 <= 128) {
                return 4;
            }
            return ((i2 - 127) / 2) + 4;
        }
        if (i >= 1024) {
            if (i2 <= 100) {
                return 4;
            }
            if (i2 <= 112) {
                return 5;
            }
            return ((i2 + PackageManager.INSTALL_FAILED_USER_RESTRICTED) / 2) + 5;
        }
        if (i < 512) {
            if (i2 <= 80) {
                return 40;
            }
            return ((i2 - 79) / 2) + 40;
        }
        if (i2 <= 80) {
            return 5;
        }
        if (i2 <= 100) {
            return 7;
        }
        return ((i2 - 99) / 2) + 7;
    }
}
