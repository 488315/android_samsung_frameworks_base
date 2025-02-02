package com.android.server.locksettings.recoverablekeystore;

import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/* loaded from: classes2.dex */
public class KeyStoreProxyImpl implements KeyStoreProxy {
  public final KeyStore mKeyStore;

  public KeyStoreProxyImpl(KeyStore keyStore) {
    this.mKeyStore = keyStore;
  }

  @Override // com.android.server.locksettings.recoverablekeystore.KeyStoreProxy
  public boolean containsAlias(String str) {
    return this.mKeyStore.containsAlias(str);
  }

  @Override // com.android.server.locksettings.recoverablekeystore.KeyStoreProxy
  public Key getKey(String str, char[] cArr) {
    return this.mKeyStore.getKey(str, cArr);
  }

  @Override // com.android.server.locksettings.recoverablekeystore.KeyStoreProxy
  public void setEntry(
      String str, KeyStore.Entry entry, KeyStore.ProtectionParameter protectionParameter) {
    this.mKeyStore.setEntry(str, entry, protectionParameter);
  }

  @Override // com.android.server.locksettings.recoverablekeystore.KeyStoreProxy
  public void deleteEntry(String str) {
    this.mKeyStore.deleteEntry(str);
  }

  public static KeyStore getAndLoadAndroidKeyStore() {
    KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
    try {
      keyStore.load(null);
      return keyStore;
    } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
      throw new KeyStoreException("Unable to load keystore.", e);
    }
  }
}
