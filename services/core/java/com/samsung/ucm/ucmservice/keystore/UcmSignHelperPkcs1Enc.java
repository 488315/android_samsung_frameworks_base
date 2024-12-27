package com.samsung.ucm.ucmservice.keystore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UcmSignHelperPkcs1Enc extends UcmSignHelper {
    public static final Set algorithmSet = new HashSet(Arrays.asList("rsa/ecb/nopadding", "rsa/ecb/pkcs1padding"));

    @Override // com.samsung.ucm.ucmservice.keystore.UcmSignHelper
    public final boolean isEncryptFunction() {
        return true;
    }
}
