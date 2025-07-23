package com.samsung.ucm.keystore;

import java.math.BigInteger;
import java.security.interfaces.RSAKey;

/* loaded from: classes6.dex */
public class UcmKeyStoreRSAPrivateKey extends UcmKeyStorePrivateKey implements RSAKey {
    public UcmKeyStoreRSAPrivateKey(String str) {
        super(str, "RSA");
    }

    public UcmKeyStoreRSAPrivateKey(String str, byte[] bArr) {
        super(str, "RSA", bArr);
    }

    @Override // java.security.interfaces.RSAKey
    public BigInteger getModulus() {
        return this.mModulus;
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStorePrivateKey, com.samsung.ucm.keystore.UcmKeyStoreKey
    public boolean equals(Object obj) {
        if ((obj instanceof UcmKeyStoreRSAPrivateKey) && super.equals(obj)) {
            UcmKeyStoreRSAPrivateKey ucmKeyStoreRSAPrivateKey = (UcmKeyStoreRSAPrivateKey) obj;
            if (this.mModulus != null && ucmKeyStoreRSAPrivateKey.mModulus != null && this.mModulus.equals(ucmKeyStoreRSAPrivateKey.mModulus)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStorePrivateKey, com.samsung.ucm.keystore.UcmKeyStoreKey
    public int hashCode() {
        if (this.mModulus == null) {
            return 1;
        }
        return this.mModulus.hashCode();
    }
}
