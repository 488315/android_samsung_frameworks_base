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
        int result = (1 * 31) + (this.mAlgorithm == null ? 0 : this.mAlgorithm.hashCode());
        return (result * 31) + (this.mAlias != null ? this.mAlias.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UcmKeyStoreKey other = (UcmKeyStoreKey) obj;
        if (this.mAlgorithm == null) {
            if (other.mAlgorithm != null) {
                return false;
            }
        } else if (!this.mAlgorithm.equals(other.mAlgorithm)) {
            return false;
        }
        if (this.mAlias == null) {
            if (other.mAlias != null) {
                return false;
            }
        } else if (!this.mAlias.equals(other.mAlias)) {
            return false;
        }
        return true;
    }
}
