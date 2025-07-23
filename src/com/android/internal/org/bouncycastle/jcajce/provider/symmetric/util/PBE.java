package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator;
import com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory;
import com.android.internal.org.bouncycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import com.android.internal.org.bouncycastle.crypto.generators.PKCS12ParametersGenerator;
import com.android.internal.org.bouncycastle.crypto.generators.PKCS5S1ParametersGenerator;
import com.android.internal.org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import com.android.internal.org.bouncycastle.crypto.params.DESParameters;
import com.android.internal.org.bouncycastle.crypto.params.KeyParameter;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/* loaded from: classes5.dex */
public interface PBE {
    public static final int MD5 = 0;
    public static final int OPENSSL = 3;
    public static final int PKCS12 = 2;
    public static final int PKCS5S1 = 0;
    public static final int PKCS5S1_UTF8 = 4;
    public static final int PKCS5S2 = 1;
    public static final int PKCS5S2_UTF8 = 5;
    public static final int SHA1 = 1;
    public static final int SHA224 = 7;
    public static final int SHA256 = 4;
    public static final int SHA384 = 8;
    public static final int SHA512 = 9;

    public static class Util {
        private static PBEParametersGenerator makePBEGenerator(int i, int i2) {
            if (i == 0 || i == 4) {
                if (i2 == 0) {
                    return new PKCS5S1ParametersGenerator(AndroidDigestFactory.getMD5());
                }
                if (i2 == 1) {
                    return new PKCS5S1ParametersGenerator(AndroidDigestFactory.getSHA1());
                }
                throw new IllegalStateException("PKCS5 scheme 1 only supports MD2, MD5 and SHA1.");
            }
            if (i == 1 || i == 5) {
                if (i2 == 0) {
                    return new PKCS5S2ParametersGenerator(AndroidDigestFactory.getMD5());
                }
                if (i2 == 1) {
                    return new PKCS5S2ParametersGenerator(AndroidDigestFactory.getSHA1());
                }
                if (i2 == 4) {
                    return new PKCS5S2ParametersGenerator(AndroidDigestFactory.getSHA256());
                }
                if (i2 == 7) {
                    return new PKCS5S2ParametersGenerator(AndroidDigestFactory.getSHA224());
                }
                if (i2 == 8) {
                    return new PKCS5S2ParametersGenerator(AndroidDigestFactory.getSHA384());
                }
                if (i2 == 9) {
                    return new PKCS5S2ParametersGenerator(AndroidDigestFactory.getSHA512());
                }
                throw new IllegalStateException("unknown digest scheme for PBE PKCS5S2 encryption.");
            }
            if (i != 2) {
                return new OpenSSLPBEParametersGenerator();
            }
            if (i2 == 0) {
                return new PKCS12ParametersGenerator(AndroidDigestFactory.getMD5());
            }
            if (i2 == 1) {
                return new PKCS12ParametersGenerator(AndroidDigestFactory.getSHA1());
            }
            if (i2 == 4) {
                return new PKCS12ParametersGenerator(AndroidDigestFactory.getSHA256());
            }
            if (i2 == 7) {
                return new PKCS12ParametersGenerator(AndroidDigestFactory.getSHA224());
            }
            if (i2 == 8) {
                return new PKCS12ParametersGenerator(AndroidDigestFactory.getSHA384());
            }
            if (i2 == 9) {
                return new PKCS12ParametersGenerator(AndroidDigestFactory.getSHA512());
            }
            throw new IllegalStateException("unknown digest scheme for PBE encryption.");
        }

        public static CipherParameters makePBEParameters(byte[] bArr, int i, int i2, int i3, int i4, AlgorithmParameterSpec algorithmParameterSpec, String str) throws InvalidAlgorithmParameterException {
            CipherParameters generateDerivedParameters;
            if (algorithmParameterSpec == null || !(algorithmParameterSpec instanceof PBEParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Need a PBEParameter spec with a PBE key.");
            }
            PBEParameterSpec pBEParameterSpec = (PBEParameterSpec) algorithmParameterSpec;
            PBEParametersGenerator makePBEGenerator = makePBEGenerator(i, i2);
            makePBEGenerator.init(bArr, pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
            if (i4 != 0) {
                generateDerivedParameters = makePBEGenerator.generateDerivedParameters(i3, i4);
                AlgorithmParameterSpec parameterSpecFromPBEParameterSpec = getParameterSpecFromPBEParameterSpec(pBEParameterSpec);
                if ((i == 1 || i == 5) && (parameterSpecFromPBEParameterSpec instanceof IvParameterSpec)) {
                    generateDerivedParameters = new ParametersWithIV((KeyParameter) ((ParametersWithIV) generateDerivedParameters).getParameters(), ((IvParameterSpec) parameterSpecFromPBEParameterSpec).getIV());
                }
            } else {
                generateDerivedParameters = makePBEGenerator.generateDerivedParameters(i3);
            }
            if (str.startsWith("DES")) {
                if (generateDerivedParameters instanceof ParametersWithIV) {
                    DESParameters.setOddParity(((KeyParameter) ((ParametersWithIV) generateDerivedParameters).getParameters()).getKey());
                    return generateDerivedParameters;
                }
                DESParameters.setOddParity(((KeyParameter) generateDerivedParameters).getKey());
            }
            return generateDerivedParameters;
        }

        public static CipherParameters makePBEParameters(BCPBEKey bCPBEKey, AlgorithmParameterSpec algorithmParameterSpec, String str) {
            CipherParameters generateDerivedParameters;
            if (algorithmParameterSpec == null || !(algorithmParameterSpec instanceof PBEParameterSpec)) {
                throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
            }
            PBEParameterSpec pBEParameterSpec = (PBEParameterSpec) algorithmParameterSpec;
            PBEParametersGenerator makePBEGenerator = makePBEGenerator(bCPBEKey.getType(), bCPBEKey.getDigest());
            byte[] encoded = bCPBEKey.getEncoded();
            if (bCPBEKey.shouldTryWrongPKCS12()) {
                encoded = new byte[2];
            }
            makePBEGenerator.init(encoded, pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
            if (bCPBEKey.getIvSize() != 0) {
                generateDerivedParameters = makePBEGenerator.generateDerivedParameters(bCPBEKey.getKeySize(), bCPBEKey.getIvSize());
                AlgorithmParameterSpec parameterSpecFromPBEParameterSpec = getParameterSpecFromPBEParameterSpec(pBEParameterSpec);
                if ((bCPBEKey.getType() == 1 || bCPBEKey.getType() == 5) && (parameterSpecFromPBEParameterSpec instanceof IvParameterSpec)) {
                    generateDerivedParameters = new ParametersWithIV((KeyParameter) ((ParametersWithIV) generateDerivedParameters).getParameters(), ((IvParameterSpec) parameterSpecFromPBEParameterSpec).getIV());
                }
            } else {
                generateDerivedParameters = makePBEGenerator.generateDerivedParameters(bCPBEKey.getKeySize());
            }
            if (str.startsWith("DES")) {
                if (generateDerivedParameters instanceof ParametersWithIV) {
                    DESParameters.setOddParity(((KeyParameter) ((ParametersWithIV) generateDerivedParameters).getParameters()).getKey());
                    return generateDerivedParameters;
                }
                DESParameters.setOddParity(((KeyParameter) generateDerivedParameters).getKey());
            }
            return generateDerivedParameters;
        }

        public static CipherParameters makePBEMacParameters(BCPBEKey bCPBEKey, AlgorithmParameterSpec algorithmParameterSpec) {
            if (algorithmParameterSpec == null || !(algorithmParameterSpec instanceof PBEParameterSpec)) {
                throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
            }
            PBEParameterSpec pBEParameterSpec = (PBEParameterSpec) algorithmParameterSpec;
            PBEParametersGenerator makePBEGenerator = makePBEGenerator(bCPBEKey.getType(), bCPBEKey.getDigest());
            makePBEGenerator.init(bCPBEKey.getEncoded(), pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
            return makePBEGenerator.generateDerivedMacParameters(bCPBEKey.getKeySize());
        }

        public static CipherParameters makePBEMacParameters(PBEKeySpec pBEKeySpec, int i, int i2, int i3) {
            PBEParametersGenerator makePBEGenerator = makePBEGenerator(i, i2);
            byte[] convertPassword = convertPassword(i, pBEKeySpec);
            makePBEGenerator.init(convertPassword, pBEKeySpec.getSalt(), pBEKeySpec.getIterationCount());
            CipherParameters generateDerivedMacParameters = makePBEGenerator.generateDerivedMacParameters(i3);
            for (int i4 = 0; i4 != convertPassword.length; i4++) {
                convertPassword[i4] = 0;
            }
            return generateDerivedMacParameters;
        }

        public static CipherParameters makePBEParameters(PBEKeySpec pBEKeySpec, int i, int i2, int i3, int i4) {
            CipherParameters generateDerivedParameters;
            PBEParametersGenerator makePBEGenerator = makePBEGenerator(i, i2);
            byte[] convertPassword = convertPassword(i, pBEKeySpec);
            makePBEGenerator.init(convertPassword, pBEKeySpec.getSalt(), pBEKeySpec.getIterationCount());
            if (i4 != 0) {
                generateDerivedParameters = makePBEGenerator.generateDerivedParameters(i3, i4);
            } else {
                generateDerivedParameters = makePBEGenerator.generateDerivedParameters(i3);
            }
            for (int i5 = 0; i5 != convertPassword.length; i5++) {
                convertPassword[i5] = 0;
            }
            return generateDerivedParameters;
        }

        public static CipherParameters makePBEMacParameters(SecretKey secretKey, int i, int i2, int i3, PBEParameterSpec pBEParameterSpec) {
            PBEParametersGenerator makePBEGenerator = makePBEGenerator(i, i2);
            byte[] encoded = secretKey.getEncoded();
            makePBEGenerator.init(secretKey.getEncoded(), pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
            CipherParameters generateDerivedMacParameters = makePBEGenerator.generateDerivedMacParameters(i3);
            for (int i4 = 0; i4 != encoded.length; i4++) {
                encoded[i4] = 0;
            }
            return generateDerivedMacParameters;
        }

        public static AlgorithmParameterSpec getParameterSpecFromPBEParameterSpec(PBEParameterSpec pBEParameterSpec) {
            try {
                Class[] clsArr = new Class[0];
                return (AlgorithmParameterSpec) PBE.class.getClassLoader().loadClass("javax.crypto.spec.PBEParameterSpec").getMethod("getParameterSpec", null).invoke(pBEParameterSpec, null);
            } catch (Exception unused) {
                return null;
            }
        }

        private static byte[] convertPassword(int i, PBEKeySpec pBEKeySpec) {
            if (i == 2) {
                return PBEParametersGenerator.PKCS12PasswordToBytes(pBEKeySpec.getPassword());
            }
            if (i == 5 || i == 4) {
                return PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(pBEKeySpec.getPassword());
            }
            return PBEParametersGenerator.PKCS5PasswordToBytes(pBEKeySpec.getPassword());
        }
    }
}
