package com.samsung.ucm.keystore;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import javax.crypto.SecretKey;

/* loaded from: classes6.dex */
public class UcmKeyStoreSecretKey extends UcmKeyStoreKey implements SecretKey {
    private static final String TAG = "UcmKeyStoreSecretKey";

    public UcmKeyStoreSecretKey(String str, String str2) {
        super(str, str2);
        Log.d(TAG, "UcmKeyStoreSecretKey(" + str + ", " + str2 + NavigationBarInflaterView.KEY_CODE_END);
    }
}
