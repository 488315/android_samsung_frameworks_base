package com.samsung.ucm.keystore;

import android.security.keystore.KeyProperties;

/* loaded from: classes6.dex */
public class UcmKeyStoreKeyFactory {
  public static UcmKeyStorePrivateKey getPrivateKey(String uri, byte[] certificateBytes) {
    String algorithm = new UcmKeyStorePrivateKey(uri, null, certificateBytes).getAlgorithm();
    if (algorithm.equals(KeyProperties.KEY_ALGORITHM_EC)) {
      return new UcmKeyStoreECPrivateKey(uri, certificateBytes);
    }
    return new UcmKeyStoreRSAPrivateKey(uri, certificateBytes);
  }
}
