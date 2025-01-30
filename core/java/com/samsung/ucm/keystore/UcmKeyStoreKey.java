package com.samsung.ucm.keystore;

import java.security.Key;

/* loaded from: classes6.dex */
public class UcmKeyStoreKey implements Key {
  protected String mAlgorithm;
  private final String mAlias;

  public UcmKeyStoreKey(String alias, String algorithm) {
    this.mAlias = alias;
    this.mAlgorithm = algorithm;
  }

  String getAlias() {
    return this.mAlias;
  }

  @Override // java.security.Key
  public String getAlgorithm() {
    return this.mAlgorithm;
  }

  @Override // java.security.Key
  public String getFormat() {
    return null;
  }

  @Override // java.security.Key
  public byte[] getEncoded() {
    return null;
  }

  public int hashCode() {
    int i = 1 * 31;
    String str = this.mAlgorithm;
    int result = i + (str == null ? 0 : str.hashCode());
    int result2 = result * 31;
    String str2 = this.mAlias;
    return result2 + (str2 != null ? str2.hashCode() : 0);
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    UcmKeyStoreKey other = (UcmKeyStoreKey) obj;
    String str = this.mAlgorithm;
    if (str == null) {
      if (other.mAlgorithm != null) {
        return false;
      }
    } else if (!str.equals(other.mAlgorithm)) {
      return false;
    }
    String str2 = this.mAlias;
    if (str2 == null) {
      if (other.mAlias != null) {
        return false;
      }
    } else if (!str2.equals(other.mAlias)) {
      return false;
    }
    return true;
  }
}
