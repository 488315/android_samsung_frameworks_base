package com.samsung.ucm.ucmservice.keystore;

import android.text.TextUtils;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UcmSignHelperFactory {
    public static volatile UcmSignHelperFactory sInstance;

    public static UcmSignHelper getInstance(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            throw new NoSuchAlgorithmException("algorithm is empty");
        }
        if (((HashSet) UcmSignHelperPkcs1Enc.algorithmSet).contains(str.toLowerCase())) {
            return new UcmSignHelperPkcs1Enc(str);
        }
        if (z) {
            return new UcmSignHelperSupportSign(str);
        }
        if (UcmSignHelperPkcs1.algorithmSet.contains(str.toLowerCase())) {
            return new UcmSignHelperPkcs1(str);
        }
        if (((HashSet) UcmSignHelperEcdsa.algorithmSet).contains(str.toLowerCase())) {
            return new UcmSignHelperEcdsa(str);
        }
        throw new NoSuchAlgorithmException("Not supported algorithm ".concat(str));
    }
}
