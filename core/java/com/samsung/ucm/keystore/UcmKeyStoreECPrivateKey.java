package com.samsung.ucm.keystore;

import android.security.keystore.KeyProperties;
import java.security.interfaces.ECKey;
import java.security.spec.ECParameterSpec;

/* loaded from: classes6.dex */
public class UcmKeyStoreECPrivateKey extends UcmKeyStorePrivateKey implements ECKey {
    public UcmKeyStoreECPrivateKey(String alias) {
        super(alias, KeyProperties.KEY_ALGORITHM_EC);
    }

    public UcmKeyStoreECPrivateKey(String alias, byte[] certificateBytes) {
        super(alias, KeyProperties.KEY_ALGORITHM_EC, certificateBytes);
    }

    @Override // java.security.interfaces.ECKey
    public ECParameterSpec getParams() {
        return this.mECParameterSpec;
    }
}
