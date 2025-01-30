package com.samsung.ucm.ucmservice.keystore;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes2.dex */
public class UcmSignHelperEcdsa extends UcmSignHelper {
    public static final Set algorithmSet = new HashSet(Arrays.asList("nonewithecdsa", "sha1withecdsa", "sha224withecdsa", "sha256withecdsa", "sha384withecdsa", "sha512withecdsa"));

    @Override // com.samsung.ucm.ucmservice.keystore.UcmSignHelper
    public String getProcessAlgorithm() {
        return "NONEwithECDSA";
    }

    public UcmSignHelperEcdsa(String str) {
        super(str);
    }

    public static boolean isSupportedAlgorithm(String str) {
        return algorithmSet.contains(str.toLowerCase());
    }

    @Override // com.samsung.ucm.ucmservice.keystore.UcmSignHelper
    public byte[] processInput(byte[] bArr) {
        return this.algorithm.equalsIgnoreCase("nonewithecdsa") ? bArr : MessageDigest.getInstance(getMdAlgorithm(this.algorithm)).digest(bArr);
    }
}
