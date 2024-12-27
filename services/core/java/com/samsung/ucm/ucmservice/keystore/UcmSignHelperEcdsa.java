package com.samsung.ucm.ucmservice.keystore;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UcmSignHelperEcdsa extends UcmSignHelper {
    public static final Set algorithmSet = new HashSet(Arrays.asList("nonewithecdsa", "sha1withecdsa", "sha224withecdsa", "sha256withecdsa", "sha384withecdsa", "sha512withecdsa"));

    @Override // com.samsung.ucm.ucmservice.keystore.UcmSignHelper
    public final String getProcessAlgorithm() {
        return "NONEwithECDSA";
    }

    @Override // com.samsung.ucm.ucmservice.keystore.UcmSignHelper
    public final byte[] processInput(byte[] bArr) {
        String str = this.algorithm;
        return str.equalsIgnoreCase("nonewithecdsa") ? bArr : MessageDigest.getInstance(UcmSignHelper.getMdAlgorithm(str)).digest(bArr);
    }
}
