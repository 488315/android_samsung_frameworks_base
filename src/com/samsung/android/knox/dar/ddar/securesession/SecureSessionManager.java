package com.samsung.android.knox.dar.ddar.securesession;

import android.security.keystore.KeyProperties;
import com.samsung.android.security.mdf.MdfUtils;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.security.auth.DestroyFailedException;

/* loaded from: classes6.dex */
class SecureSessionManager {
    private static final String CRYPTO_PROVIDER = "AndroidOpenSSL";
    private static final SecureRandom sSecureRandom = new SecureRandom();

    SecureSessionManager() {
    }

    static class PrivateSessionEndpoint {
        private PrivateKey privateKey;
        private PublicKey publicKey;

        PrivateSessionEndpoint() throws Exception {
            try {
                KeyPair keyPairCreateKeyPair = createKeyPair();
                this.publicKey = keyPairCreateKeyPair.getPublic();
                this.privateKey = keyPairCreateKeyPair.getPrivate();
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Error: PrivateSessionEndpoint creation failure");
            }
        }

        String getPublicKeyString() {
            return Util.byteArrayToHexString(this.publicKey.getEncoded());
        }

        PublicKey getPublicKey() {
            return this.publicKey;
        }

        PrivateKey getPrivateKey() {
            return this.privateKey;
        }

        void destroy() throws Exception {
            try {
                this.privateKey.destroy();
                this.privateKey = null;
                this.publicKey = null;
            } catch (DestroyFailedException unused) {
            }
        }

        private KeyPair createKeyPair() throws Exception {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, SecureSessionManager.CRYPTO_PROVIDER);
            keyPairGenerator.initialize(new ECGenParameterSpec("secp521r1"));
            return keyPairGenerator.generateKeyPair();
        }
    }

    static class PublicSessionEndpoint {
        private final PublicKey publicKey;

        PublicSessionEndpoint(String str) throws Exception {
            try {
                this.publicKey = createPublicKey(str);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Error: PublicSessionEndpoint creation failure");
            }
        }

        String getPublicKeyString() {
            return Util.byteArrayToHexString(this.publicKey.getEncoded());
        }

        PublicKey getPublicKey() {
            return this.publicKey;
        }

        private PublicKey createPublicKey(String str) throws Exception {
            return KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_EC, SecureSessionManager.CRYPTO_PROVIDER).generatePublic(new X509EncodedKeySpec(Util.fromHexString(str)));
        }
    }

    static class SecureSession {
        private final PrivateSessionEndpoint privateSessionEndpoint;
        private final PublicSessionEndpoint publicSessionEndpoint;
        private SecretKey sessionKey;
        private byte[] xorMask;

        SecureSession(PrivateSessionEndpoint privateSessionEndpoint, PublicSessionEndpoint publicSessionEndpoint) throws Exception {
            this.privateSessionEndpoint = privateSessionEndpoint;
            this.publicSessionEndpoint = publicSessionEndpoint;
            generateSessionKey();
        }

        private void generateSessionKey() throws Exception {
            KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH", SecureSessionManager.CRYPTO_PROVIDER);
            keyAgreement.init(this.privateSessionEndpoint.getPrivateKey());
            keyAgreement.doPhase(this.publicSessionEndpoint.getPublicKey(), true);
            byte[] bArrGenerateSecret = keyAgreement.generateSecret();
            byte[] bArrCopyOf = Arrays.copyOf(bArrGenerateSecret, 16);
            this.xorMask = Arrays.copyOfRange(bArrGenerateSecret, 16, bArrGenerateSecret.length);
            this.sessionKey = new SessionSecretKeySpec(bArrCopyOf, "AES");
            Wiper.wipe(bArrGenerateSecret);
            Wiper.wipe(bArrCopyOf);
        }

        void destroySessionkey() throws Exception {
            Wiper.wipe(this.xorMask);
            this.sessionKey.destroy();
        }

        String encryptString(String str) throws Exception {
            if (str == null) {
                return null;
            }
            return encryptData(generateIV(), str.getBytes(StandardCharsets.UTF_8));
        }

        byte[] encryptBytes(byte[] bArr) throws Exception {
            if (bArr == null) {
                return null;
            }
            return encryptData(generateIV(), bArr).getBytes(StandardCharsets.UTF_8);
        }

        String decryptString(String str) throws Exception {
            if (str == null) {
                return null;
            }
            return new String(decryptData(str), StandardCharsets.UTF_8);
        }

        byte[] decryptBytes(byte[] bArr) throws Exception {
            if (bArr == null) {
                return null;
            }
            return decryptData(new String(bArr, StandardCharsets.UTF_8));
        }

        private String encryptData(byte[] bArr, byte[] bArr2) throws Exception {
            return Util.encodeBase64(bArr) + ":" + Util.encodeBase64(encrypt(bArr, bArr2));
        }

        private byte[] decryptData(String str) throws Exception {
            String[] strArrSplit = str.split(":");
            return decrypt(Util.decodeBase64(strArrSplit[0]), Util.decodeBase64(strArrSplit[1]));
        }

        private byte[] generateIV() {
            byte[] bArr = new byte[12];
            SecureSessionManager.sSecureRandom.nextBytes(bArr);
            return bArr;
        }

        private byte[] encrypt(byte[] bArr, byte[] bArr2) throws Exception {
            applyXorMask(bArr2);
            Cipher cipher = Cipher.getInstance(MdfUtils.MDF_CIPHER_MODE, Security.getProvider(SecureSessionManager.CRYPTO_PROVIDER));
            cipher.init(1, this.sessionKey, new IvParameterSpec(bArr));
            return cipher.doFinal(bArr2);
        }

        private byte[] decrypt(byte[] bArr, byte[] bArr2) throws Exception {
            Cipher cipher = Cipher.getInstance(MdfUtils.MDF_CIPHER_MODE, Security.getProvider(SecureSessionManager.CRYPTO_PROVIDER));
            cipher.init(2, this.sessionKey, new IvParameterSpec(bArr));
            byte[] bArrDoFinal = cipher.doFinal(bArr2);
            applyXorMask(bArrDoFinal);
            return bArrDoFinal;
        }

        private void applyXorMask(byte[] bArr) {
            int i = 0;
            int i2 = 0;
            while (i < bArr.length) {
                byte[] bArr2 = this.xorMask;
                if (i2 >= bArr2.length) {
                    i2 = 0;
                }
                bArr[i] = (byte) (bArr2[i2] ^ bArr[i]);
                i++;
                i2++;
            }
        }
    }
}
