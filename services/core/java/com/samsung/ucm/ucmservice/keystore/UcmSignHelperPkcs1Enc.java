package com.samsung.ucm.ucmservice.keystore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes2.dex */
public class UcmSignHelperPkcs1Enc extends UcmSignHelper {
    public static final Set algorithmSet = new HashSet(Arrays.asList("rsa/ecb/nopadding", "rsa/ecb/pkcs1padding"));

    @Override // com.samsung.ucm.ucmservice.keystore.UcmSignHelper
    public boolean isEncryptFunction() {
        return true;
    }

    public static boolean isSupportedAlgorithm(String str) {
        return algorithmSet.contains(str.toLowerCase());
    }

    public UcmSignHelperPkcs1Enc(String str) {
        super(str);
    }
}
