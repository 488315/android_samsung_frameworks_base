package com.samsung.ucm.keystore;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;

import javax.crypto.SecretKey;

/* loaded from: classes6.dex */
public class UcmKeyStoreSecretKey extends UcmKeyStoreKey implements SecretKey {
    private static final String TAG = UcmKeyStoreSecretKey.class.getSimpleName();

    public UcmKeyStoreSecretKey(String alias, String algorithm) {
        super(alias, algorithm);
        Log.d(
                TAG,
                "UcmKeyStoreSecretKey("
                        + alias
                        + ", "
                        + algorithm
                        + NavigationBarInflaterView.KEY_CODE_END);
    }
}
