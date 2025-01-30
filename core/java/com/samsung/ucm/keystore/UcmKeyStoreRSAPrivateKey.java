package com.samsung.ucm.keystore;

import java.math.BigInteger;
import java.security.interfaces.RSAKey;

/* loaded from: classes6.dex */
public class UcmKeyStoreRSAPrivateKey extends UcmKeyStorePrivateKey implements RSAKey {
  public UcmKeyStoreRSAPrivateKey(String alias) {
    super(alias, "RSA");
  }

  public UcmKeyStoreRSAPrivateKey(String alias, byte[] certificateBytes) {
    super(alias, "RSA", certificateBytes);
  }

  @Override // java.security.interfaces.RSAKey
  public BigInteger getModulus() {
    return this.mModulus;
  }

  @Override // com.samsung.ucm.keystore.UcmKeyStorePrivateKey,
            // com.samsung.ucm.keystore.UcmKeyStoreKey
  public boolean equals(Object obj) {
    if (!(obj instanceof UcmKeyStoreRSAPrivateKey) || !super.equals(obj)) {
      return false;
    }
    UcmKeyStoreRSAPrivateKey objKey = (UcmKeyStoreRSAPrivateKey) obj;
    return (this.mModulus == null
            || objKey.mModulus == null
            || !this.mModulus.equals(objKey.mModulus))
        ? false
        : true;
  }

  @Override // com.samsung.ucm.keystore.UcmKeyStorePrivateKey,
            // com.samsung.ucm.keystore.UcmKeyStoreKey
  public int hashCode() {
    if (this.mModulus == null) {
      return 1;
    }
    return this.mModulus.hashCode();
  }
}
