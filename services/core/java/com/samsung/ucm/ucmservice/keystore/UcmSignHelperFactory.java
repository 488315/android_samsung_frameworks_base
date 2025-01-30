package com.samsung.ucm.ucmservice.keystore;

import android.text.TextUtils;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes2.dex */
public class UcmSignHelperFactory {
    public static volatile UcmSignHelperFactory sInstance;

    public static synchronized UcmSignHelperFactory getInstance() {
        UcmSignHelperFactory ucmSignHelperFactory;
        synchronized (UcmSignHelperFactory.class) {
            if (sInstance == null) {
                synchronized (UcmSignHelperFactory.class) {
                    if (sInstance == null) {
                        sInstance = new UcmSignHelperFactory();
                    }
                }
            }
            ucmSignHelperFactory = sInstance;
        }
        return ucmSignHelperFactory;
    }

    public UcmSignHelper getInstance(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            throw new NoSuchAlgorithmException("algorithm is empty");
        }
        if (UcmSignHelperPkcs1Enc.isSupportedAlgorithm(str)) {
            return new UcmSignHelperPkcs1Enc(str);
        }
        if (z) {
            return new UcmSignHelperSupportSign(str);
        }
        if (UcmSignHelperPkcs1.isSupportedAlgorithm(str)) {
            return new UcmSignHelperPkcs1(str);
        }
        if (UcmSignHelperEcdsa.isSupportedAlgorithm(str)) {
            return new UcmSignHelperEcdsa(str);
        }
        throw new NoSuchAlgorithmException("Not supported algorithm " + str);
    }
}
