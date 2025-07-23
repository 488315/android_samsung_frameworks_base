package com.android.internal.org.bouncycastle.crypto.generators;

import com.android.internal.org.bouncycastle.math.ec.WNafUtil;
import com.android.internal.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Logger;

/* loaded from: classes5.dex */
class DHParametersHelper {
    private static final Logger logger = Logger.getLogger(DHParametersHelper.class.getName());
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);

    DHParametersHelper() {
    }

    static BigInteger[] generateSafePrimes(int i, int i2, SecureRandom secureRandom) {
        logger.info("Generating safe primes. This may take a long time.");
        long currentTimeMillis = System.currentTimeMillis();
        int i3 = i - 1;
        int i4 = i >>> 2;
        int i5 = 0;
        while (true) {
            i5++;
            BigInteger createRandomPrime = BigIntegers.createRandomPrime(i3, 2, secureRandom);
            BigInteger add = createRandomPrime.shiftLeft(1).add(ONE);
            if (add.isProbablePrime(i2) && (i2 <= 2 || createRandomPrime.isProbablePrime(i2 - 2))) {
                if (WNafUtil.getNafWeight(add) >= i4) {
                    long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                    logger.info("Generated safe primes: " + i5 + " tries took " + currentTimeMillis2 + "ms");
                    return new BigInteger[]{add, createRandomPrime};
                }
            }
        }
    }

    static BigInteger selectGenerator(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        BigInteger modPow;
        BigInteger subtract = bigInteger.subtract(TWO);
        do {
            BigInteger bigInteger3 = TWO;
            modPow = BigIntegers.createRandomInRange(bigInteger3, subtract, secureRandom).modPow(bigInteger3, bigInteger);
        } while (modPow.equals(ONE));
        return modPow;
    }
}
