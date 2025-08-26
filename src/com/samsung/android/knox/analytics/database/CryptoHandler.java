package com.samsung.android.knox.analytics.database;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.DeadObjectException;
import android.security.keystore.BackendBusyException;
import android.security.keystore.KeyProperties;
import android.security.keystore.KeyProtection;
import android.security.keystore.KeyStoreConnectException;
import android.security.keystore2.AndroidKeyStoreSpi;
import com.samsung.android.knox.analytics.util.Log;
import com.samsung.android.security.mdf.MdfUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Iterator;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;

/* loaded from: classes6.dex */
class CryptoHandler {
    private static final String TAG = "[KnoxAnalytics] CryptoHandler";
    private final Object mKeystoreGenerateAccessLock = new Object();
    private final Object mKeystoreAccessWaitLock = new Object();
    private SecretKey mKeyCache = null;
    private SecretKey mLegacyKeyCache = null;

    CryptoHandler() {
    }

    private static class Constraints {
        static final String CHARSET_ENCODING = "UTF-8";
        static final String KEYSTORE = "AndroidKeyStore";
        static final String KEY_GENERATOR_ALGORITHM = "AES";
        private static final String SYNTHETIC_PASSWORD_KEY_PREFIX = "synthetic_password_";

        private Constraints() {
        }

        static class GCM {
            static final String ALIAS = "synthetic_password_knox.analytics.service.cryptokey";
            static final String BLOCK_MODE = "GCM";
            static final String CIPHER_ALGORITHM = "AES/GCM/NoPadding";
            static final String ENCRYPTION_PADDING = "NoPadding";
            static final int IV_SIZE = 12;
            static final String LEGACY_ALIAS = "com.samsung.android.knox.analytics.service.cryptokey";
            static final int TLEN_SIZE = 128;

            GCM() {
            }
        }

        static class CBC {
            static final String ALIAS = "synthetic_password_knox.analytics.service.compression.cryptokey";
            static final String BLOCK_MODE = "CBC";
            static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";
            static final String ENCRYPTION_PADDING = "PKCS7Padding";
            static final int IV_SIZE = 16;

            CBC() {
            }
        }
    }

    private KeyStore getKeyStore() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        try {
            KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
            keyStore.load(null);
            return keyStore;
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
            return null;
        } catch (KeyStoreException e2) {
            Log.e(TAG, "KeyStoreException", e2);
            return null;
        } catch (NoSuchAlgorithmException e3) {
            Log.e(TAG, "NoSuchAlgorithmException", e3);
            return null;
        } catch (CertificateException e4) {
            Log.e(TAG, "CertificateException", e4);
            return null;
        }
    }

    String decrypt(byte[] bArr, boolean z) throws GeneralSecurityException, UnsupportedEncodingException {
        Log.d(TAG, "decrypt(): isLegacyKey = " + z);
        try {
            return decryptInternal(bArr, z, false);
        } catch (DeadObjectException e) {
            Log.e(TAG, "decrypt(): DeadObjectException", e);
            try {
                return this.decryptInternal(bArr, z, true);
            } catch (DeadObjectException e2) {
                Log.e(TAG, "decrypt(): DeadObjectException", e2);
                return null;
            } catch (KeyStoreConnectException e3) {
                Log.e(TAG, "decrypt(): KeyStoreConnectException", e3);
                return null;
            }
        } catch (KeyStoreConnectException e4) {
            Log.e(TAG, "decrypt(): KeyStoreConnectException", e4);
            return null;
        }
    }

    private String decryptInternal(byte[] bArr, boolean z, boolean z2) throws KeyStoreConnectException, GeneralSecurityException, UnsupportedEncodingException, DeadObjectException {
        Cipher cipher = Cipher.getInstance(MdfUtils.MDF_CIPHER_MODE);
        int length = bArr.length - 12;
        byte[] bArr2 = new byte[length];
        byte[] bArr3 = new byte[12];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        System.arraycopy(bArr, length, bArr3, 0, 12);
        cipher.init(2, getGCMKey(z, z2), new GCMParameterSpec(128, bArr3));
        String str = new String(cipher.doFinal(bArr2), "UTF-8");
        Log.d(TAG, "decryptInternal(): ".concat(str));
        return str;
    }

    String decryptBulk(byte[] bArr) throws GeneralSecurityException, UnsupportedEncodingException {
        return new String(decryptBlob(bArr), "UTF-8");
    }

    byte[] decryptBlob(byte[] bArr) throws GeneralSecurityException {
        Log.d(TAG, "decryptBlob(): cipherLength: " + bArr.length);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        int length = bArr.length - 16;
        byte[] bArr2 = new byte[length];
        byte[] bArr3 = new byte[16];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        System.arraycopy(bArr, length, bArr3, 0, 16);
        cipher.init(2, getCBCKey(), new IvParameterSpec(bArr3));
        return cipher.doFinal(bArr2);
    }

    byte[] encryptBlob(byte[] bArr) {
        Log.d(TAG, "encryptBlob()");
        for (int i = 0; i < 5; i++) {
            try {
                return this.encryptInternal(bArr);
            } catch (BackendBusyException e) {
                Log.e(TAG, "encryptBlob(): BackendBusyException", e);
                try {
                } catch (InterruptedException unused) {
                    Log.e(TAG, "encryptBlob(): Interrupted exception");
                }
                synchronized (this.mKeystoreAccessWaitLock) {
                    this.mKeystoreAccessWaitLock.wait(e.getBackOffHintMillis());
                }
            } catch (InvalidKeyException e2) {
                Log.e(TAG, "encryptBlob(): InvalidKeyException", e2);
                return null;
            } catch (GeneralSecurityException e3) {
                Log.e(TAG, "encryptBlob(): GeneralSecurityException", e3);
                return null;
            }
        }
        return null;
    }

    byte[] encrypt(String str) {
        Log.d(TAG, "encrypt(" + str + NavigationBarInflaterView.KEY_CODE_END);
        for (int i = 0; i < 5; i++) {
            try {
                return this.encryptInternal(str, false);
            } catch (DeadObjectException e) {
                Log.e(TAG, "encrypt(): DeadObjectException", e);
                try {
                    return this.encryptInternal(str, true);
                } catch (DeadObjectException | KeyStoreConnectException | UnsupportedEncodingException | GeneralSecurityException e2) {
                    Log.e(TAG, "encrypt()", e2);
                    return null;
                }
            } catch (BackendBusyException e3) {
                Log.e(TAG, "encrypt(): BackendBusyException", e3);
                try {
                } catch (InterruptedException unused) {
                    Log.e(TAG, "encrypt(): Interrupted exception");
                }
                synchronized (this.mKeystoreAccessWaitLock) {
                    this.mKeystoreAccessWaitLock.wait(e3.getBackOffHintMillis());
                }
            } catch (KeyStoreConnectException e4) {
                Log.e(TAG, "encrypt(): KeyStoreConnectException", e4);
                return null;
            } catch (UnsupportedEncodingException e5) {
                Log.e(TAG, "encrypt(): UnsupportedEncodingException", e5);
                return null;
            } catch (InvalidKeyException e6) {
                Log.e(TAG, "encrypt(): InvalidKeyException", e6);
                return null;
            } catch (GeneralSecurityException e7) {
                Log.e(TAG, "encrypt(): GeneralSecurityException", e7);
                return null;
            }
        }
        return null;
    }

    byte[] encryptBulk(List<String> list) {
        Log.d(TAG, "encryptBulk()");
        for (int i = 0; i < 5; i++) {
            try {
                return this.encryptBulkInternal(list);
            } catch (BackendBusyException e) {
                Log.e(TAG, "encryptBulk(): BackendBusyException", e);
                try {
                } catch (InterruptedException unused) {
                    Log.e(TAG, "encryptBulk(): Interrupted exception");
                }
                synchronized (this.mKeystoreAccessWaitLock) {
                    this.mKeystoreAccessWaitLock.wait(e.getBackOffHintMillis());
                }
            } catch (IOException e2) {
                Log.e(TAG, "encryptBulk(): IOException", e2);
                return null;
            } catch (InvalidKeyException e3) {
                Log.e(TAG, "encryptBulk(): InvalidKeyException", e3);
                return null;
            } catch (NoSuchAlgorithmException e4) {
                Log.e(TAG, "encryptBulk(): NoSuchAlgorithmException", e4);
                return null;
            } catch (BadPaddingException e5) {
                Log.e(TAG, "encryptBulk(): BadPaddingException", e5);
                return null;
            } catch (IllegalBlockSizeException e6) {
                Log.e(TAG, "encryptBulk(): IllegalBlockSizeException", e6);
                return null;
            } catch (NoSuchPaddingException e7) {
                Log.e(TAG, "encryptBulk(): NoSuchPaddingException", e7);
                return null;
            } catch (GeneralSecurityException e8) {
                Log.e(TAG, "encryptBulk(): GeneralSecurityException", e8);
                return null;
            }
        }
        return null;
    }

    byte[] encryptBulkInternal(List<String> list) throws GeneralSecurityException, IOException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(1, getCBCKey());
        byte[] iv = cipher.getIV();
        byte[] bArr = new byte[16];
        System.arraycopy(iv, 0, bArr, 0, iv.length);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            byte[] bArrUpdate = cipher.update(it.next().getBytes("UTF-8"));
            if (bArrUpdate != null) {
                byteArrayOutputStream.write(bArrUpdate);
            }
        }
        byteArrayOutputStream.write(cipher.doFinal());
        byteArrayOutputStream.write(bArr);
        return byteArrayOutputStream.toByteArray();
    }

    private byte[] encryptInternal(byte[] bArr) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(1, getCBCKey());
        byte[] iv = cipher.getIV();
        byte[] bArr2 = new byte[16];
        System.arraycopy(iv, 0, bArr2, 0, iv.length);
        byte[] bArrDoFinal = cipher.doFinal(bArr);
        byte[] bArr3 = new byte[bArrDoFinal.length + 16];
        System.arraycopy(bArrDoFinal, 0, bArr3, 0, bArrDoFinal.length);
        System.arraycopy(bArr2, 0, bArr3, bArrDoFinal.length, 16);
        return bArr3;
    }

    private byte[] encryptInternal(String str, boolean z) throws KeyStoreConnectException, GeneralSecurityException, UnsupportedEncodingException, DeadObjectException {
        Cipher cipher = Cipher.getInstance(MdfUtils.MDF_CIPHER_MODE);
        cipher.init(1, getGCMKey(false, z));
        byte[] iv = cipher.getIV();
        byte[] bArr = new byte[12];
        System.arraycopy(iv, 0, bArr, 0, iv.length);
        byte[] bArrDoFinal = cipher.doFinal(str.getBytes("UTF-8"));
        byte[] bArr2 = new byte[bArrDoFinal.length + 12];
        System.arraycopy(bArrDoFinal, 0, bArr2, 0, bArrDoFinal.length);
        System.arraycopy(bArr, 0, bArr2, bArrDoFinal.length, 12);
        return bArr2;
    }

    private void generateCBCKeyInternal() throws GeneralSecurityException, IOException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(new SecureRandom());
        SecretKey secretKeyGenerateKey = keyGenerator.generateKey();
        KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
        keyStore.load(null);
        keyStore.setEntry("synthetic_password_knox.analytics.service.compression.cryptokey", new KeyStore.SecretKeyEntry(secretKeyGenerateKey), new KeyProtection.Builder(3).setBlockModes(KeyProperties.BLOCK_MODE_CBC).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).setCriticalToDeviceEncryption(true).build());
    }

    private void generateGCMKeyInternal() throws GeneralSecurityException, IOException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(new SecureRandom());
        SecretKey secretKeyGenerateKey = keyGenerator.generateKey();
        KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
        keyStore.load(null);
        keyStore.setEntry("synthetic_password_knox.analytics.service.cryptokey", new KeyStore.SecretKeyEntry(secretKeyGenerateKey), new KeyProtection.Builder(3).setBlockModes("GCM").setEncryptionPaddings("NoPadding").setCriticalToDeviceEncryption(true).build());
    }

    boolean generateGCMKey() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        Log.d(TAG, "generateGCMKey()");
        KeyStore keyStore = getKeyStore();
        if (keyStore == null) {
            return false;
        }
        try {
            if (keyStore.containsAlias("synthetic_password_knox.analytics.service.cryptokey")) {
                return false;
            }
            generateGCMKeyInternal();
            return true;
        } catch (IOException unused) {
            Log.e(TAG, "generateGCMKey(): IOException");
            return false;
        } catch (GeneralSecurityException unused2) {
            Log.e(TAG, "generateGCMKey(): GeneralSecurityException");
            return false;
        }
    }

    void generateCBCKey() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        KeyStore keyStore = getKeyStore();
        if (keyStore != null) {
            try {
                if (keyStore.containsAlias("synthetic_password_knox.analytics.service.compression.cryptokey")) {
                    return;
                }
                generateCBCKeyInternal();
            } catch (IOException unused) {
                Log.e(TAG, "generateCBCKey(): IOException");
            } catch (GeneralSecurityException unused2) {
                Log.e(TAG, "generateCBCKey(): GeneralSecurityException");
            }
        }
    }

    private SecretKey getCBCKey() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, UnrecoverableEntryException {
        KeyStore keyStore = getKeyStore();
        if (keyStore == null) {
            return null;
        }
        if (!keyStore.containsAlias("synthetic_password_knox.analytics.service.compression.cryptokey")) {
            Log.d(TAG, "getCBCKey() - synthetic_password_knox.analytics.service.compression.cryptokey is not on Keystore");
            return null;
        }
        KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry("synthetic_password_knox.analytics.service.compression.cryptokey", null);
        if (secretKeyEntry == null) {
            Log.d(TAG, "getCBCKey() - null synthetic_password_knox.analytics.service.compression.cryptokey");
            return null;
        }
        return secretKeyEntry.getSecretKey();
    }

    private SecretKey getGCMKey(boolean z, boolean z2) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableEntryException {
        SecretKey secretKey = z ? this.mLegacyKeyCache : this.mKeyCache;
        if (secretKey != null && !z2) {
            return secretKey;
        }
        synchronized (this.mKeystoreGenerateAccessLock) {
            SecretKey keyStoreKey = getKeyStoreKey(z);
            if (z) {
                this.mLegacyKeyCache = keyStoreKey;
                return keyStoreKey;
            }
            this.mKeyCache = keyStoreKey;
            return keyStoreKey;
        }
    }

    private SecretKey getKeyStoreKey(boolean z) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, UnrecoverableEntryException {
        KeyStore keyStore = getKeyStore();
        String str = z ? "com.samsung.android.knox.analytics.service.cryptokey" : "synthetic_password_knox.analytics.service.cryptokey";
        String str2 = str.equals("com.samsung.android.knox.analytics.service.cryptokey") ? "legacy key" : "key";
        if (keyStore == null) {
            Log.d(TAG, "getKeyStore(): null");
            return null;
        }
        if (!keyStore.containsAlias(str)) {
            Log.d(TAG, "getKeyStoreKey() - " + str2 + " is not on Keystore");
            return null;
        }
        KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(str, null);
        if (secretKeyEntry == null) {
            Log.d(TAG, "getKeyStoreKey() - null ".concat(str2));
            return null;
        }
        return secretKeyEntry.getSecretKey();
    }

    boolean isGCMKeyGenerated() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        KeyStore keyStore = getKeyStore();
        if (keyStore == null) {
            return false;
        }
        try {
            keyStore.containsAlias("synthetic_password_knox.analytics.service.cryptokey");
            return false;
        } catch (KeyStoreException unused) {
            Log.e(TAG, "isGCMKeyGenerated(): KeyStoreException");
            return false;
        }
    }

    void deleteAnalyticsLegacyKey() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        String str = TAG;
        Log.d(str, "deleteAnalyticsLegacyKey()");
        if (this.mLegacyKeyCache == null) {
            return;
        }
        KeyStore keyStore = getKeyStore();
        if (keyStore != null) {
            try {
                if (!keyStore.containsAlias("com.samsung.android.knox.analytics.service.cryptokey")) {
                    Log.d(str, "deleteAnalyticsLegacyKey(): Key already deleted");
                    this.mLegacyKeyCache = null;
                    return;
                } else {
                    keyStore.deleteEntry("com.samsung.android.knox.analytics.service.cryptokey");
                    Log.d(str, "deleteAnalyticsLegacyKey(): Key deleted. Invalidating cache");
                }
            } catch (KeyStoreException unused) {
                Log.e(TAG, "deleteAnalyticsLegacyKey(): KeyStoreException");
            }
        }
        this.mLegacyKeyCache = null;
    }
}
