package com.samsung.ucm.keystore;

import java.security.Key;

/* loaded from: classes6.dex */
public class UcmKeyStoreKey implements Key {
    protected String mAlgorithm;
    private final String mAlias;

    @Override // java.security.Key
    public byte[] getEncoded() {
        return null;
    }

    @Override // java.security.Key
    public String getFormat() {
        return null;
    }

    public UcmKeyStoreKey(String str, String str2) {
        this.mAlias = str;
        this.mAlgorithm = str2;
    }

    String getAlias() {
        return this.mAlias;
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return this.mAlgorithm;
    }

    public int hashCode() {
        String str = this.mAlgorithm;
        int iHashCode = ((str == null ? 0 : str.hashCode()) + 31) * 31;
        String str2 = this.mAlias;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UcmKeyStoreKey ucmKeyStoreKey = (UcmKeyStoreKey) obj;
        String str = this.mAlgorithm;
        if (str == null) {
            if (ucmKeyStoreKey.mAlgorithm != null) {
                return false;
            }
        } else if (!str.equals(ucmKeyStoreKey.mAlgorithm)) {
            return false;
        }
        String str2 = this.mAlias;
        if (str2 == null) {
            if (ucmKeyStoreKey.mAlias != null) {
                return false;
            }
        } else if (!str2.equals(ucmKeyStoreKey.mAlias)) {
            return false;
        }
        return true;
    }
}
