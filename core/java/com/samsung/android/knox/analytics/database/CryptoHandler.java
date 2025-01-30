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
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;

/* loaded from: classes5.dex */
class CryptoHandler {
  private static final String TAG = "[KnoxAnalytics] " + CryptoHandler.class.getSimpleName();
  private final Object mKeystoreGenerateAccessLock = new Object();
  private final Object mKeystoreAccessWaitLock = new Object();
  private SecretKey mKeyCache = null;
  private SecretKey mLegacyKeyCache = null;

  CryptoHandler() {}

  private static class Constraints {
    static final String CHARSET_ENCODING = "UTF-8";
    static final String KEYSTORE = "AndroidKeyStore";
    static final String KEY_GENERATOR_ALGORITHM = "AES";
    private static final String SYNTHETIC_PASSWORD_KEY_PREFIX = "synthetic_password_";

    private Constraints() {}

    static class GCM {
      static final String ALIAS = "synthetic_password_knox.analytics.service.cryptokey";
      static final String BLOCK_MODE = "GCM";
      static final String CIPHER_ALGORITHM = "AES/GCM/NoPadding";
      static final String ENCRYPTION_PADDING = "NoPadding";
      static final int IV_SIZE = 12;
      static final String LEGACY_ALIAS = "com.samsung.android.knox.analytics.service.cryptokey";
      static final int TLEN_SIZE = 128;

      GCM() {}
    }

    static class CBC {
      static final String ALIAS = "synthetic_password_knox.analytics.service.compression.cryptokey";
      static final String BLOCK_MODE = "CBC";
      static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";
      static final String ENCRYPTION_PADDING = "PKCS7Padding";
      static final int IV_SIZE = 16;

      CBC() {}
    }
  }

  private KeyStore getKeyStore() {
    try {
      KeyStore ks = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
      ks.load(null);
      return ks;
    } catch (IOException e) {
      Log.m288e(TAG, "IOException", e);
      return null;
    } catch (KeyStoreException e2) {
      Log.m288e(TAG, "KeyStoreException", e2);
      return null;
    } catch (NoSuchAlgorithmException e3) {
      Log.m288e(TAG, "NoSuchAlgorithmException", e3);
      return null;
    } catch (CertificateException e4) {
      Log.m288e(TAG, "CertificateException", e4);
      return null;
    }
  }

  String decrypt(byte[] encText, boolean isLegacyKey)
      throws UnsupportedEncodingException, GeneralSecurityException {
    Log.m286d(TAG, "decrypt(): isLegacyKey = " + isLegacyKey);
    try {
      return decryptInternal(encText, isLegacyKey, false);
    } catch (DeadObjectException e) {
      Log.m288e(TAG, "decrypt(): DeadObjectException", e);
      try {
        return decryptInternal(encText, isLegacyKey, true);
      } catch (DeadObjectException e1) {
        Log.m288e(TAG, "decrypt(): DeadObjectException", e1);
        return null;
      } catch (KeyStoreConnectException e12) {
        Log.m288e(TAG, "decrypt(): KeyStoreConnectException", e12);
        return null;
      }
    } catch (KeyStoreConnectException e2) {
      Log.m288e(TAG, "decrypt(): KeyStoreConnectException", e2);
      return null;
    }
  }

  private String decryptInternal(byte[] encText, boolean isLegacyKey, boolean skipCache)
      throws UnsupportedEncodingException,
          GeneralSecurityException,
          DeadObjectException,
          KeyStoreConnectException {
    Cipher cipherDec = Cipher.getInstance(MdfUtils.MDF_CIPHER_MODE);
    int cipherTextSize = encText.length - 12;
    byte[] cipherText = new byte[cipherTextSize];
    byte[] iv = new byte[12];
    System.arraycopy(encText, 0, cipherText, 0, cipherTextSize);
    System.arraycopy(encText, cipherTextSize, iv, 0, 12);
    GCMParameterSpec gcmParamSpec = new GCMParameterSpec(128, iv);
    cipherDec.init(2, getGCMKey(isLegacyKey, skipCache), gcmParamSpec);
    String text = new String(cipherDec.doFinal(cipherText), "UTF-8");
    Log.m286d(TAG, "decryptInternal(): " + text);
    return text;
  }

  String decryptBulk(byte[] encText) throws GeneralSecurityException, UnsupportedEncodingException {
    byte[] decText = decryptBlob(encText);
    return new String(decText, "UTF-8");
  }

  byte[] decryptBlob(byte[] encText) throws GeneralSecurityException {
    Log.m286d(TAG, "decryptBlob(): cipherLength: " + encText.length);
    Cipher cipherDec = Cipher.getInstance("AES/CBC/PKCS7Padding");
    int cipherTextSize = encText.length - 16;
    byte[] cipherText = new byte[cipherTextSize];
    byte[] iv = new byte[16];
    System.arraycopy(encText, 0, cipherText, 0, cipherTextSize);
    System.arraycopy(encText, cipherTextSize, iv, 0, 16);
    cipherDec.init(2, getCBCKey(), new IvParameterSpec(iv));
    return cipherDec.doFinal(cipherText);
  }

  byte[] encryptBlob(byte[] text) {
    Log.m286d(TAG, "encryptBlob()");
    for (int tries = 0; tries < 5; tries++) {
      try {
        return encryptInternal(text);
      } catch (BackendBusyException e) {
        Log.m288e(TAG, "encryptBlob(): BackendBusyException", e);
        try {
        } catch (InterruptedException e2) {
          Log.m287e(TAG, "encryptBlob(): Interrupted exception");
        }
        synchronized (this.mKeystoreAccessWaitLock) {
          this.mKeystoreAccessWaitLock.wait(e.getBackOffHintMillis());
        }
      } catch (InvalidKeyException e3) {
        Log.m288e(TAG, "encryptBlob(): InvalidKeyException", e3);
        return null;
      } catch (GeneralSecurityException e4) {
        Log.m288e(TAG, "encryptBlob(): GeneralSecurityException", e4);
        return null;
      }
    }
    return null;
  }

  byte[] encrypt(String text) {
    Log.m286d(TAG, "encrypt(" + text + NavigationBarInflaterView.KEY_CODE_END);
    for (int tries = 0; tries < 5; tries++) {
      try {
        return encryptInternal(text, false);
      } catch (DeadObjectException e) {
        Log.m288e(TAG, "encrypt(): DeadObjectException", e);
        try {
          return encryptInternal(text, true);
        } catch (DeadObjectException
            | KeyStoreConnectException
            | UnsupportedEncodingException
            | GeneralSecurityException e1) {
          Log.m288e(TAG, "encrypt()", e1);
          return null;
        }
      } catch (BackendBusyException e2) {
        Log.m288e(TAG, "encrypt(): BackendBusyException", e2);
        try {
        } catch (InterruptedException e3) {
          Log.m287e(TAG, "encrypt(): Interrupted exception");
        }
        synchronized (this.mKeystoreAccessWaitLock) {
          this.mKeystoreAccessWaitLock.wait(e2.getBackOffHintMillis());
        }
      } catch (KeyStoreConnectException e4) {
        Log.m288e(TAG, "encrypt(): KeyStoreConnectException", e4);
        return null;
      } catch (UnsupportedEncodingException e5) {
        Log.m288e(TAG, "encrypt(): UnsupportedEncodingException", e5);
        return null;
      } catch (InvalidKeyException e6) {
        Log.m288e(TAG, "encrypt(): InvalidKeyException", e6);
        return null;
      } catch (GeneralSecurityException e7) {
        Log.m288e(TAG, "encrypt(): GeneralSecurityException", e7);
        return null;
      }
    }
    return null;
  }

  byte[] encryptBulk(List<String> textList) {
    Log.m286d(TAG, "encryptBulk()");
    for (int tries = 0; tries < 5; tries++) {
      try {
        return encryptBulkInternal(textList);
      } catch (BackendBusyException e) {
        Log.m288e(TAG, "encryptBulk(): BackendBusyException", e);
        try {
        } catch (InterruptedException e2) {
          Log.m287e(TAG, "encryptBulk(): Interrupted exception");
        }
        synchronized (this.mKeystoreAccessWaitLock) {
          this.mKeystoreAccessWaitLock.wait(e.getBackOffHintMillis());
        }
      } catch (IOException e3) {
        Log.m288e(TAG, "encryptBulk(): IOException", e3);
        return null;
      } catch (InvalidKeyException e4) {
        Log.m288e(TAG, "encryptBulk(): InvalidKeyException", e4);
        return null;
      } catch (NoSuchAlgorithmException e5) {
        Log.m288e(TAG, "encryptBulk(): NoSuchAlgorithmException", e5);
        return null;
      } catch (BadPaddingException e6) {
        Log.m288e(TAG, "encryptBulk(): BadPaddingException", e6);
        return null;
      } catch (IllegalBlockSizeException e7) {
        Log.m288e(TAG, "encryptBulk(): IllegalBlockSizeException", e7);
        return null;
      } catch (NoSuchPaddingException e8) {
        Log.m288e(TAG, "encryptBulk(): NoSuchPaddingException", e8);
        return null;
      } catch (GeneralSecurityException e9) {
        Log.m288e(TAG, "encryptBulk(): GeneralSecurityException", e9);
        return null;
      }
    }
    return null;
  }

  byte[] encryptBulkInternal(List<String> textList)
      throws GeneralSecurityException, UnsupportedEncodingException, IOException {
    Cipher cipherEnc = Cipher.getInstance("AES/CBC/PKCS7Padding");
    cipherEnc.init(1, getCBCKey());
    byte[] tempIv = cipherEnc.getIV();
    byte[] iv = new byte[16];
    System.arraycopy(tempIv, 0, iv, 0, tempIv.length);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    for (String text : textList) {
      byte[] originalText = text.getBytes("UTF-8");
      byte[] encryptedData = cipherEnc.update(originalText);
      if (encryptedData != null) {
        outputStream.write(encryptedData);
      }
    }
    outputStream.write(cipherEnc.doFinal());
    outputStream.write(iv);
    return outputStream.toByteArray();
  }

  private byte[] encryptInternal(byte[] text) throws GeneralSecurityException {
    Cipher cipherEnc = Cipher.getInstance("AES/CBC/PKCS7Padding");
    cipherEnc.init(1, getCBCKey());
    byte[] tempiv = cipherEnc.getIV();
    byte[] iv = new byte[16];
    System.arraycopy(tempiv, 0, iv, 0, tempiv.length);
    byte[] cipherText = cipherEnc.doFinal(text);
    byte[] final_data = new byte[cipherText.length + 16];
    System.arraycopy(cipherText, 0, final_data, 0, cipherText.length);
    System.arraycopy(iv, 0, final_data, cipherText.length, 16);
    return final_data;
  }

  private byte[] encryptInternal(String text, boolean skipCache)
      throws UnsupportedEncodingException,
          GeneralSecurityException,
          DeadObjectException,
          KeyStoreConnectException {
    Cipher cipherEnc = Cipher.getInstance(MdfUtils.MDF_CIPHER_MODE);
    cipherEnc.init(1, getGCMKey(false, skipCache));
    byte[] tempiv = cipherEnc.getIV();
    byte[] iv = new byte[12];
    System.arraycopy(tempiv, 0, iv, 0, tempiv.length);
    byte[] cipherText = cipherEnc.doFinal(text.getBytes("UTF-8"));
    byte[] final_data = new byte[cipherText.length + 12];
    System.arraycopy(cipherText, 0, final_data, 0, cipherText.length);
    System.arraycopy(iv, 0, final_data, cipherText.length, 12);
    return final_data;
  }

  private void generateCBCKeyInternal() throws IOException, GeneralSecurityException {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(new SecureRandom());
    SecretKey secretKey = keyGenerator.generateKey();
    KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
    keyStore.load(null);
    KeyProtection.Builder builder =
        new KeyProtection.Builder(3)
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .setCriticalToDeviceEncryption(true);
    keyStore.setEntry(
        "synthetic_password_knox.analytics.service.compression.cryptokey",
        new KeyStore.SecretKeyEntry(secretKey),
        builder.build());
  }

  private void generateGCMKeyInternal() throws IOException, GeneralSecurityException {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(new SecureRandom());
    SecretKey secretKey = keyGenerator.generateKey();
    KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
    keyStore.load(null);
    KeyProtection.Builder builder =
        new KeyProtection.Builder(3)
            .setBlockModes("GCM")
            .setEncryptionPaddings("NoPadding")
            .setCriticalToDeviceEncryption(true);
    keyStore.setEntry(
        "synthetic_password_knox.analytics.service.cryptokey",
        new KeyStore.SecretKeyEntry(secretKey),
        builder.build());
  }

  boolean generateGCMKey() {
    Log.m286d(TAG, "generateGCMKey()");
    KeyStore ks = getKeyStore();
    if (ks != null) {
      try {
        if (!ks.containsAlias("synthetic_password_knox.analytics.service.cryptokey")) {
          generateGCMKeyInternal();
          return true;
        }
        return false;
      } catch (IOException e) {
        Log.m287e(TAG, "generateGCMKey(): IOException");
        return false;
      } catch (GeneralSecurityException e2) {
        Log.m287e(TAG, "generateGCMKey(): GeneralSecurityException");
        return false;
      }
    }
    return false;
  }

  void generateCBCKey() {
    KeyStore ks = getKeyStore();
    if (ks != null) {
      try {
        if (!ks.containsAlias("synthetic_password_knox.analytics.service.compression.cryptokey")) {
          generateCBCKeyInternal();
        }
      } catch (IOException e) {
        Log.m287e(TAG, "generateCBCKey(): IOException");
      } catch (GeneralSecurityException e2) {
        Log.m287e(TAG, "generateCBCKey(): GeneralSecurityException");
      }
    }
  }

  private SecretKey getCBCKey()
      throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException {
    KeyStore ks = getKeyStore();
    if (ks == null) {
      return null;
    }
    if (!ks.containsAlias("synthetic_password_knox.analytics.service.compression.cryptokey")) {
      Log.m286d(
          TAG,
          "getCBCKey() - synthetic_password_knox.analytics.service.compression.cryptokey is not on"
              + " Keystore");
      return null;
    }
    KeyStore.SecretKeyEntry secretKeyEntry =
        (KeyStore.SecretKeyEntry)
            ks.getEntry("synthetic_password_knox.analytics.service.compression.cryptokey", null);
    if (secretKeyEntry == null) {
      Log.m286d(
          TAG,
          "getCBCKey() - null synthetic_password_knox.analytics.service.compression.cryptokey");
      return null;
    }
    return secretKeyEntry.getSecretKey();
  }

  private SecretKey getGCMKey(boolean isLegacyKey, boolean skipCache)
      throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
    SecretKey cachedKey = isLegacyKey ? this.mLegacyKeyCache : this.mKeyCache;
    if (cachedKey != null && !skipCache) {
      return cachedKey;
    }
    synchronized (this.mKeystoreGenerateAccessLock) {
      SecretKey cachedKey2 = getKeyStoreKey(isLegacyKey);
      if (isLegacyKey) {
        this.mLegacyKeyCache = cachedKey2;
        return cachedKey2;
      }
      this.mKeyCache = cachedKey2;
      return cachedKey2;
    }
  }

  private SecretKey getKeyStoreKey(boolean isLegacyKey)
      throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException {
    KeyStore ks = getKeyStore();
    String alias =
        isLegacyKey
            ? "com.samsung.android.knox.analytics.service.cryptokey"
            : "synthetic_password_knox.analytics.service.cryptokey";
    String targetAlias =
        alias.equals("com.samsung.android.knox.analytics.service.cryptokey") ? "legacy key" : "key";
    if (ks == null) {
      Log.m286d(TAG, "getKeyStore(): null");
      return null;
    }
    if (!ks.containsAlias(alias)) {
      Log.m286d(TAG, "getKeyStoreKey() - " + targetAlias + " is not on Keystore");
      return null;
    }
    KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) ks.getEntry(alias, null);
    if (secretKeyEntry == null) {
      Log.m286d(TAG, "getKeyStoreKey() - null " + targetAlias);
      return null;
    }
    return secretKeyEntry.getSecretKey();
  }

  boolean isGCMKeyGenerated() {
    KeyStore ks = getKeyStore();
    if (ks != null) {
      try {
        ks.containsAlias("synthetic_password_knox.analytics.service.cryptokey");
        return false;
      } catch (KeyStoreException e) {
        Log.m287e(TAG, "isGCMKeyGenerated(): KeyStoreException");
        return false;
      }
    }
    return false;
  }

  void deleteAnalyticsLegacyKey() {
    String str = TAG;
    Log.m286d(str, "deleteAnalyticsLegacyKey()");
    if (this.mLegacyKeyCache == null) {
      return;
    }
    KeyStore keyStore = getKeyStore();
    if (keyStore != null) {
      try {
        if (!keyStore.containsAlias("com.samsung.android.knox.analytics.service.cryptokey")) {
          Log.m286d(str, "deleteAnalyticsLegacyKey(): Key already deleted");
          this.mLegacyKeyCache = null;
          return;
        } else {
          keyStore.deleteEntry("com.samsung.android.knox.analytics.service.cryptokey");
          Log.m286d(str, "deleteAnalyticsLegacyKey(): Key deleted. Invalidating cache");
        }
      } catch (KeyStoreException e) {
        Log.m287e(TAG, "deleteAnalyticsLegacyKey(): KeyStoreException");
      }
    }
    this.mLegacyKeyCache = null;
  }
}
