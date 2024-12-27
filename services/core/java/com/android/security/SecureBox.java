package com.android.security;

import com.android.internal.util.ArrayUtils;

import java.math.BigInteger;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECFieldFp;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class SecureBox {
    public static final BigInteger BIG_INT_02;
    public static final byte[] CONSTANT_01;
    public static final BigInteger EC_PARAM_A;
    public static final BigInteger EC_PARAM_B;
    public static final BigInteger EC_PARAM_P;
    static final ECParameterSpec EC_PARAM_SPEC;
    public static final byte[] EMPTY_BYTE_ARRAY;
    public static final byte[] HKDF_INFO_WITHOUT_PUBLIC_KEY;
    public static final byte[] HKDF_INFO_WITH_PUBLIC_KEY;
    public static final byte[] HKDF_SALT;
    public static final byte[] VERSION;

    final class AesGcmOperation {
        public static final /* synthetic */ AesGcmOperation[] $VALUES;
        public static final AesGcmOperation DECRYPT;
        public static final AesGcmOperation ENCRYPT;

        static {
            AesGcmOperation aesGcmOperation = new AesGcmOperation("ENCRYPT", 0);
            ENCRYPT = aesGcmOperation;
            AesGcmOperation aesGcmOperation2 = new AesGcmOperation("DECRYPT", 1);
            DECRYPT = aesGcmOperation2;
            $VALUES = new AesGcmOperation[] {aesGcmOperation, aesGcmOperation2};
        }

        public static AesGcmOperation valueOf(String str) {
            return (AesGcmOperation) Enum.valueOf(AesGcmOperation.class, str);
        }

        public static AesGcmOperation[] values() {
            return (AesGcmOperation[]) $VALUES.clone();
        }
    }

    static {
        byte[] bArr = {2, 0};
        VERSION = bArr;
        Charset charset = StandardCharsets.UTF_8;
        HKDF_SALT = ArrayUtils.concat(new byte[][] {"SECUREBOX".getBytes(charset), bArr});
        HKDF_INFO_WITH_PUBLIC_KEY = "P256 HKDF-SHA-256 AES-128-GCM".getBytes(charset);
        HKDF_INFO_WITHOUT_PUBLIC_KEY = "SHARED HKDF-SHA-256 AES-128-GCM".getBytes(charset);
        CONSTANT_01 = new byte[] {1};
        EMPTY_BYTE_ARRAY = new byte[0];
        BIG_INT_02 = BigInteger.valueOf(2L);
        BigInteger bigInteger =
                new BigInteger(
                        "ffffffff00000001000000000000000000000000ffffffffffffffffffffffff", 16);
        EC_PARAM_P = bigInteger;
        BigInteger subtract = bigInteger.subtract(new BigInteger("3"));
        EC_PARAM_A = subtract;
        BigInteger bigInteger2 =
                new BigInteger(
                        "5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b", 16);
        EC_PARAM_B = bigInteger2;
        EC_PARAM_SPEC =
                new ECParameterSpec(
                        new EllipticCurve(new ECFieldFp(bigInteger), subtract, bigInteger2),
                        new ECPoint(
                                new BigInteger(
                                        "6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296",
                                        16),
                                new BigInteger(
                                        "4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5",
                                        16)),
                        new BigInteger(
                                "ffffffff00000000ffffffffffffffffbce6faada7179e84f3b9cac2fc632551",
                                16),
                        1);
    }

    public static byte[] aesGcmInternal(
            AesGcmOperation aesGcmOperation,
            SecretKey secretKey,
            byte[] bArr,
            byte[] bArr2,
            byte[] bArr3) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gCMParameterSpec = new GCMParameterSpec(128, bArr);
            try {
                if (aesGcmOperation == AesGcmOperation.DECRYPT) {
                    cipher.init(2, secretKey, gCMParameterSpec);
                } else {
                    cipher.init(1, secretKey, gCMParameterSpec);
                }
                try {
                    cipher.updateAAD(bArr3);
                    return cipher.doFinal(bArr2);
                } catch (AEADBadTagException e) {
                    throw e;
                } catch (BadPaddingException | IllegalBlockSizeException e2) {
                    throw new RuntimeException(e2);
                }
            } catch (InvalidAlgorithmParameterException e3) {
                throw new RuntimeException(e3);
            }
        } catch (NoSuchPaddingException e4) {
            throw new RuntimeException(e4);
        }
    }

    public static byte[] decrypt(PrivateKey privateKey, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArr4;
        byte[] bArr5 = EMPTY_BYTE_ARRAY;
        if (bArr == null) {
            bArr = bArr5;
        }
        if (privateKey == null && bArr.length == 0) {
            throw new IllegalArgumentException("Both the private key and shared secret are empty");
        }
        if (bArr2 == null) {
            bArr2 = bArr5;
        }
        if (bArr3 == null) {
            throw new NullPointerException("Encrypted payload must not be null.");
        }
        ByteBuffer wrap = ByteBuffer.wrap(bArr3);
        if (!Arrays.equals(readEncryptedPayload(wrap, 2), VERSION)) {
            throw new AEADBadTagException("The payload was not encrypted by SecureBox v2");
        }
        if (privateKey == null) {
            bArr4 = HKDF_INFO_WITHOUT_PUBLIC_KEY;
        } else {
            byte[] readEncryptedPayload = readEncryptedPayload(wrap, 65);
            BigInteger bigInteger =
                    new BigInteger(1, Arrays.copyOfRange(readEncryptedPayload, 1, 33));
            BigInteger bigInteger2 =
                    new BigInteger(1, Arrays.copyOfRange(readEncryptedPayload, 33, 65));
            BigInteger bigInteger3 = EC_PARAM_P;
            if (bigInteger.compareTo(bigInteger3) >= 0
                    || bigInteger2.compareTo(bigInteger3) >= 0
                    || bigInteger.signum() == -1
                    || bigInteger2.signum() == -1) {
                throw new InvalidKeyException("Point lies outside of the expected curve");
            }
            BigInteger bigInteger4 = BIG_INT_02;
            if (!bigInteger2
                    .modPow(bigInteger4, bigInteger3)
                    .equals(
                            bigInteger
                                    .modPow(bigInteger4, bigInteger3)
                                    .add(EC_PARAM_A)
                                    .mod(bigInteger3)
                                    .multiply(bigInteger)
                                    .add(EC_PARAM_B)
                                    .mod(bigInteger3))) {
                throw new InvalidKeyException("Point lies outside of the expected curve");
            }
            try {
                PublicKey generatePublic =
                        KeyFactory.getInstance("EC")
                                .generatePublic(
                                        new ECPublicKeySpec(
                                                new ECPoint(bigInteger, bigInteger2),
                                                EC_PARAM_SPEC));
                KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH");
                try {
                    keyAgreement.init(privateKey);
                    keyAgreement.doPhase(generatePublic, true);
                    bArr5 = keyAgreement.generateSecret();
                    bArr4 = HKDF_INFO_WITH_PUBLIC_KEY;
                } catch (RuntimeException e) {
                    throw new InvalidKeyException(e);
                }
            } catch (InvalidKeySpecException e2) {
                throw new RuntimeException(e2);
            }
        }
        byte[] readEncryptedPayload2 = readEncryptedPayload(wrap, 12);
        byte[] readEncryptedPayload3 = readEncryptedPayload(wrap, wrap.remaining());
        return aesGcmInternal(
                AesGcmOperation.DECRYPT,
                hkdfDeriveKey(ArrayUtils.concat(new byte[][] {bArr5, bArr}), HKDF_SALT, bArr4),
                readEncryptedPayload2,
                readEncryptedPayload3,
                bArr2);
    }

    public static byte[] encodePublicKey(PublicKey publicKey) {
        ECPoint w = ((ECPublicKey) publicKey).getW();
        byte[] byteArray = w.getAffineX().toByteArray();
        byte[] byteArray2 = w.getAffineY().toByteArray();
        byte[] bArr = new byte[65];
        System.arraycopy(byteArray2, 0, bArr, 65 - byteArray2.length, byteArray2.length);
        System.arraycopy(byteArray, 0, bArr, 33 - byteArray.length, byteArray.length);
        bArr[0] = 4;
        return bArr;
    }

    public static byte[] encrypt(PublicKey publicKey, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArr4;
        KeyPair keyPair;
        byte[] bArr5 = EMPTY_BYTE_ARRAY;
        if (bArr == null) {
            bArr = bArr5;
        }
        if (publicKey == null && bArr.length == 0) {
            throw new IllegalArgumentException("Both the public key and shared secret are empty");
        }
        if (bArr2 == null) {
            bArr2 = bArr5;
        }
        if (bArr3 == null) {
            bArr3 = bArr5;
        }
        if (publicKey == null) {
            keyPair = null;
            bArr4 = HKDF_INFO_WITHOUT_PUBLIC_KEY;
        } else {
            KeyPair genKeyPair = genKeyPair();
            PrivateKey privateKey = genKeyPair.getPrivate();
            KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH");
            try {
                keyAgreement.init(privateKey);
                keyAgreement.doPhase(publicKey, true);
                byte[] generateSecret = keyAgreement.generateSecret();
                bArr4 = HKDF_INFO_WITH_PUBLIC_KEY;
                bArr5 = generateSecret;
                keyPair = genKeyPair;
            } catch (RuntimeException e) {
                throw new InvalidKeyException(e);
            }
        }
        byte[] bArr6 = new byte[12];
        new SecureRandom().nextBytes(bArr6);
        try {
            byte[] aesGcmInternal =
                    aesGcmInternal(
                            AesGcmOperation.ENCRYPT,
                            hkdfDeriveKey(
                                    ArrayUtils.concat(new byte[][] {bArr5, bArr}),
                                    HKDF_SALT,
                                    bArr4),
                            bArr6,
                            bArr3,
                            bArr2);
            byte[] bArr7 = VERSION;
            return keyPair == null
                    ? ArrayUtils.concat(new byte[][] {bArr7, bArr6, aesGcmInternal})
                    : ArrayUtils.concat(
                            new byte[][] {
                                bArr7, encodePublicKey(keyPair.getPublic()), bArr6, aesGcmInternal
                            });
        } catch (AEADBadTagException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static KeyPair genKeyPair() {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        try {
            try {
                keyPairGenerator.initialize(new ECGenParameterSpec("prime256v1"));
                return keyPairGenerator.generateKeyPair();
            } catch (InvalidAlgorithmParameterException unused) {
                keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"));
                return keyPairGenerator.generateKeyPair();
            }
        } catch (InvalidAlgorithmParameterException e) {
            throw new NoSuchAlgorithmException("Unable to find the NIST P-256 curve", e);
        }
    }

    public static SecretKey hkdfDeriveKey(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        Mac mac = Mac.getInstance("HmacSHA256");
        try {
            mac.init(new SecretKeySpec(bArr2, "HmacSHA256"));
            try {
                mac.init(new SecretKeySpec(mac.doFinal(bArr), "HmacSHA256"));
                mac.update(bArr3);
                return new SecretKeySpec(Arrays.copyOf(mac.doFinal(CONSTANT_01), 16), "AES");
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        } catch (InvalidKeyException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static byte[] readEncryptedPayload(ByteBuffer byteBuffer, int i) {
        byte[] bArr = new byte[i];
        try {
            byteBuffer.get(bArr);
            return bArr;
        } catch (BufferUnderflowException unused) {
            throw new AEADBadTagException("The encrypted payload is too short");
        }
    }
}
