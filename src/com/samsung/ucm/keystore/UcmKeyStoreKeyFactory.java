package com.samsung.ucm.keystore;

import android.security.keystore.KeyProperties;

/* loaded from: classes6.dex */
public class UcmKeyStoreKeyFactory {
    public static UcmKeyStorePrivateKey getPrivateKey(String str, byte[] bArr) {
        if (new UcmKeyStorePrivateKey(str, null, bArr).getAlgorithm().equals(KeyProperties.KEY_ALGORITHM_EC)) {
            return new UcmKeyStoreECPrivateKey(str, bArr);
        }
        return new UcmKeyStoreRSAPrivateKey(str, bArr);
    }
}
