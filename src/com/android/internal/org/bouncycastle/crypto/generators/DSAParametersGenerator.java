package com.android.internal.org.bouncycastle.crypto.generators;

import com.android.internal.org.bouncycastle.crypto.Digest;
import com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory;
import com.android.internal.org.bouncycastle.crypto.params.DSAParameterGenerationParameters;
import com.android.internal.org.bouncycastle.crypto.params.DSAParameters;
import com.android.internal.org.bouncycastle.crypto.params.DSAValidationParameters;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.BigIntegers;
import com.android.internal.org.bouncycastle.util.encoders.Hex;
import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes5.dex */
public class DSAParametersGenerator {
    private int L;
    private int N;
    private int certainty;
    private Digest digest;
    private int iterations;
    private SecureRandom random;
    private int usageIndex;
    private boolean use186_3;
    private static final BigInteger ZERO = BigInteger.valueOf(0);
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);

    private static int getDefaultN(int i) {
        return i > 1024 ? 256 : 160;
    }

    public DSAParametersGenerator() {
        this(AndroidDigestFactory.getSHA1());
    }

    public DSAParametersGenerator(Digest digest) {
        this.digest = digest;
    }

    public void init(int i, int i2, SecureRandom secureRandom) {
        this.L = i;
        this.N = getDefaultN(i);
        this.certainty = i2;
        this.iterations = Math.max(getMinimumIterations(this.L), (i2 + 1) / 2);
        this.random = secureRandom;
        this.use186_3 = false;
        this.usageIndex = -1;
    }

    public void init(DSAParameterGenerationParameters dSAParameterGenerationParameters) {
        int l = dSAParameterGenerationParameters.getL();
        int n = dSAParameterGenerationParameters.getN();
        if (l < 1024 || l > 3072 || l % 1024 != 0) {
            throw new IllegalArgumentException("L values must be between 1024 and 3072 and a multiple of 1024");
        }
        if (l == 1024 && n != 160) {
            throw new IllegalArgumentException("N must be 160 for L = 1024");
        }
        if (l == 2048 && n != 224 && n != 256) {
            throw new IllegalArgumentException("N must be 224 or 256 for L = 2048");
        }
        if (l == 3072 && n != 256) {
            throw new IllegalArgumentException("N must be 256 for L = 3072");
        }
        if (this.digest.getDigestSize() * 8 < n) {
            throw new IllegalStateException("Digest output size too small for value of N");
        }
        this.L = l;
        this.N = n;
        this.certainty = dSAParameterGenerationParameters.getCertainty();
        this.iterations = Math.max(getMinimumIterations(l), (this.certainty + 1) / 2);
        this.random = dSAParameterGenerationParameters.getRandom();
        this.use186_3 = true;
        this.usageIndex = dSAParameterGenerationParameters.getUsageIndex();
    }

    public DSAParameters generateParameters() {
        if (this.use186_3) {
            return generateParameters_FIPS186_3();
        }
        return generateParameters_FIPS186_2();
    }

    private DSAParameters generateParameters_FIPS186_2() {
        int i = 20;
        byte[] bArr = new byte[20];
        byte[] bArr2 = new byte[20];
        byte[] bArr3 = new byte[20];
        byte[] bArr4 = new byte[20];
        int i2 = this.L;
        int i3 = (i2 - 1) / 160;
        int i4 = i2 / 8;
        byte[] bArr5 = new byte[i4];
        if (!this.digest.getAlgorithmName().equals("SHA-1")) {
            throw new IllegalStateException("can only use SHA-1 for generating FIPS 186-2 parameters");
        }
        while (true) {
            this.random.nextBytes(bArr);
            hash(this.digest, bArr, bArr2, 0);
            System.arraycopy(bArr, 0, bArr3, 0, i);
            inc(bArr3);
            hash(this.digest, bArr3, bArr3, 0);
            for (int i5 = 0; i5 != i; i5++) {
                bArr4[i5] = (byte) (bArr2[i5] ^ bArr3[i5]);
            }
            bArr4[0] = (byte) (bArr4[0] | Byte.MIN_VALUE);
            bArr4[19] = (byte) (bArr4[19] | 1);
            BigInteger bigInteger = new BigInteger(1, bArr4);
            if (isProbablePrime(bigInteger)) {
                byte[] clone = Arrays.clone(bArr);
                inc(clone);
                int i6 = 0;
                while (i6 < 4096) {
                    for (int i7 = 1; i7 <= i3; i7++) {
                        inc(clone);
                        hash(this.digest, clone, bArr5, i4 - (i7 * 20));
                    }
                    int i8 = i4 - (i3 * 20);
                    inc(clone);
                    hash(this.digest, clone, bArr2, 0);
                    System.arraycopy(bArr2, 20 - i8, bArr5, 0, i8);
                    bArr5[0] = (byte) (bArr5[0] | Byte.MIN_VALUE);
                    BigInteger bigInteger2 = new BigInteger(1, bArr5);
                    BigInteger subtract = bigInteger2.subtract(bigInteger2.mod(bigInteger.shiftLeft(1)).subtract(ONE));
                    if (subtract.bitLength() == this.L && isProbablePrime(subtract)) {
                        return new DSAParameters(subtract, bigInteger, calculateGenerator_FIPS186_2(subtract, bigInteger, this.random), new DSAValidationParameters(bArr, i6));
                    }
                    i6++;
                    i = 20;
                }
            }
        }
    }

    private static BigInteger calculateGenerator_FIPS186_2(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        BigInteger modPow;
        BigInteger divide = bigInteger.subtract(ONE).divide(bigInteger2);
        BigInteger subtract = bigInteger.subtract(TWO);
        do {
            modPow = BigIntegers.createRandomInRange(TWO, subtract, secureRandom).modPow(divide, bigInteger);
        } while (modPow.bitLength() <= 1);
        return modPow;
    }

    private DSAParameters generateParameters_FIPS186_3() {
        BigInteger bit;
        int i;
        BigInteger subtract;
        BigInteger calculateGenerator_FIPS186_3_Verifiable;
        Digest digest = this.digest;
        int digestSize = digest.getDigestSize() * 8;
        byte[] bArr = new byte[this.N / 8];
        int i2 = this.L;
        int i3 = (i2 - 1) / digestSize;
        int i4 = (i2 - 1) % digestSize;
        int i5 = i2 / 8;
        byte[] bArr2 = new byte[i5];
        int digestSize2 = digest.getDigestSize();
        byte[] bArr3 = new byte[digestSize2];
        loop0: while (true) {
            this.random.nextBytes(bArr);
            int i6 = 0;
            hash(digest, bArr, bArr3, 0);
            bit = new BigInteger(1, bArr3).mod(ONE.shiftLeft(this.N - 1)).setBit(0).setBit(this.N - 1);
            if (isProbablePrime(bit)) {
                byte[] clone = Arrays.clone(bArr);
                int i7 = this.L * 4;
                i = 0;
                while (i < i7) {
                    for (int i8 = 1; i8 <= i3; i8++) {
                        inc(clone);
                        hash(digest, clone, bArr2, i5 - (i8 * digestSize2));
                    }
                    int i9 = i5 - (i3 * digestSize2);
                    inc(clone);
                    hash(digest, clone, bArr3, i6);
                    System.arraycopy(bArr3, digestSize2 - i9, bArr2, i6, i9);
                    bArr2[i6] = (byte) (bArr2[i6] | Byte.MIN_VALUE);
                    BigInteger bigInteger = new BigInteger(1, bArr2);
                    subtract = bigInteger.subtract(bigInteger.mod(bit.shiftLeft(1)).subtract(ONE));
                    if (subtract.bitLength() == this.L && isProbablePrime(subtract)) {
                        break loop0;
                    }
                    i++;
                    i6 = 0;
                }
            }
        }
        int i10 = this.usageIndex;
        if (i10 >= 0 && (calculateGenerator_FIPS186_3_Verifiable = calculateGenerator_FIPS186_3_Verifiable(digest, subtract, bit, bArr, i10)) != null) {
            return new DSAParameters(subtract, bit, calculateGenerator_FIPS186_3_Verifiable, new DSAValidationParameters(bArr, i, this.usageIndex));
        }
        return new DSAParameters(subtract, bit, calculateGenerator_FIPS186_3_Unverifiable(subtract, bit, this.random), new DSAValidationParameters(bArr, i));
    }

    private boolean isProbablePrime(BigInteger bigInteger) {
        return bigInteger.isProbablePrime(this.certainty);
    }

    private static BigInteger calculateGenerator_FIPS186_3_Unverifiable(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        return calculateGenerator_FIPS186_2(bigInteger, bigInteger2, secureRandom);
    }

    private static BigInteger calculateGenerator_FIPS186_3_Verifiable(Digest digest, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr, int i) {
        BigInteger divide = bigInteger.subtract(ONE).divide(bigInteger2);
        byte[] decodeStrict = Hex.decodeStrict("6767656E");
        int length = bArr.length + decodeStrict.length;
        byte[] bArr2 = new byte[length + 3];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        System.arraycopy(decodeStrict, 0, bArr2, bArr.length, decodeStrict.length);
        bArr2[length] = (byte) i;
        byte[] bArr3 = new byte[digest.getDigestSize()];
        for (int i2 = 1; i2 < 65536; i2++) {
            inc(bArr2);
            hash(digest, bArr2, bArr3, 0);
            BigInteger modPow = new BigInteger(1, bArr3).modPow(divide, bigInteger);
            if (modPow.compareTo(TWO) >= 0) {
                return modPow;
            }
        }
        return null;
    }

    private static void hash(Digest digest, byte[] bArr, byte[] bArr2, int i) {
        digest.update(bArr, 0, bArr.length);
        digest.doFinal(bArr2, i);
    }

    private static int getMinimumIterations(int i) {
        if (i <= 1024) {
            return 40;
        }
        return (((i - 1) / 1024) * 8) + 48;
    }

    private static void inc(byte[] bArr) {
        for (int length = bArr.length - 1; length >= 0; length--) {
            byte b = (byte) ((bArr[length] + 1) & 255);
            bArr[length] = b;
            if (b != 0) {
                return;
            }
        }
    }
}
