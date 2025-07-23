package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

import java.security.AlgorithmParameters;
import java.security.spec.AlgorithmParameterSpec;

/* loaded from: classes5.dex */
class SpecUtil {
    SpecUtil() {
    }

    static AlgorithmParameterSpec extractSpec(AlgorithmParameters algorithmParameters, Class[] clsArr) {
        try {
            return algorithmParameters.getParameterSpec(AlgorithmParameterSpec.class);
        } catch (Exception unused) {
            for (int i = 0; i != clsArr.length; i++) {
                Class cls = clsArr[i];
                if (cls != null) {
                    try {
                        return algorithmParameters.getParameterSpec(cls);
                    } catch (Exception unused2) {
                        continue;
                    }
                }
            }
            return null;
        }
    }
}
