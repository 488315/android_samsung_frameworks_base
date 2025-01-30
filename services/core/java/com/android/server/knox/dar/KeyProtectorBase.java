package com.android.server.knox.dar;

import android.security.keystore.KeyProtection;
import android.security.keystore2.AndroidKeyStoreLoadStoreParameter;
import android.util.Log;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/* loaded from: classes2.dex */
public class KeyProtectorBase {
  public static String androidKeystoreProviderName() {
    return "AndroidKeyStore";
  }

  public static int keyNamespace() {
    return 1250;
  }

  public static KeyStore getKeyStore() {
    KeyStore keyStore = KeyStore.getInstance(androidKeystoreProviderName());
    keyStore.load(new AndroidKeyStoreLoadStoreParameter(keyNamespace()));
    return keyStore;
  }

  public boolean setSecretKey(String str) {
    try {
      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(256, new SecureRandom());
      getKeyStore()
          .setEntry(
              str,
              new KeyStore.SecretKeyEntry(keyGenerator.generateKey()),
              new KeyProtection.Builder(3)
                  .setBlockModes("GCM")
                  .setEncryptionPaddings("NoPadding")
                  .setCriticalToDeviceEncryption(true)
                  .build());
      return true;
    } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
      Log.e("KeyProtectorBase", "Failed to set secret key - " + str);
      e.printStackTrace();
      return false;
    }
  }

  public SecretKey getSecretKey(String str) {
    try {
      return (SecretKey) getKeyStore().getKey(str, null);
    } catch (IOException
        | KeyStoreException
        | NoSuchAlgorithmException
        | UnrecoverableKeyException
        | CertificateException e) {
      Log.e("KeyProtectorBase", "Failed to get secret key - " + str);
      e.printStackTrace();
      return null;
    }
  }

  public boolean checkSecretKey(String str) {
    try {
      return getKeyStore().containsAlias(str);
    } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
      Log.e("KeyProtectorBase", "Failed to check secret key - " + str);
      e.printStackTrace();
      return false;
    }
  }

  public boolean deleteSecretKey(String str) {
    try {
      getKeyStore().deleteEntry(str);
      return true;
    } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
      Log.e("KeyProtectorBase", "Failed to delete secret key - " + str);
      e.printStackTrace();
      return false;
    }
  }
}
