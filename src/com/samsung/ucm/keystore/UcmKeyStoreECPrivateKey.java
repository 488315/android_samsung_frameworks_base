package com.samsung.ucm.keystore;

import android.security.keystore.KeyProperties;
import java.security.interfaces.ECKey;
import java.security.spec.ECParameterSpec;

/* loaded from: classes6.dex */
public class UcmKeyStoreECPrivateKey extends UcmKeyStorePrivateKey implements ECKey {
    public UcmKeyStoreECPrivateKey(String str) {
        super(str, KeyProperties.KEY_ALGORITHM_EC);
    }

    public UcmKeyStoreECPrivateKey(String str, byte[] bArr) {
        super(str, KeyProperties.KEY_ALGORITHM_EC, bArr);
    }

    @Override // java.security.interfaces.ECKey
    public ECParameterSpec getParams() {
        return this.mECParameterSpec;
    }
}
