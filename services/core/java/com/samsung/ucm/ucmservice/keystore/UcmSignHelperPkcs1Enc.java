package com.samsung.ucm.ucmservice.keystore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class UcmSignHelperPkcs1Enc extends UcmSignHelper {
    public static final Set algorithmSet =
            new HashSet(Arrays.asList("rsa/ecb/nopadding", "rsa/ecb/pkcs1padding"));

    @Override // com.samsung.ucm.ucmservice.keystore.UcmSignHelper
    public final boolean isEncryptFunction() {
        return true;
    }
}
