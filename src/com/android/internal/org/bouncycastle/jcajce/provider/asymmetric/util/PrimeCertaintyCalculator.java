package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util;

/* loaded from: classes5.dex */
public class PrimeCertaintyCalculator {
    private PrimeCertaintyCalculator() {
    }

    public static int getDefaultCertainty(int i) {
        if (i <= 1024) {
            return 80;
        }
        return (((i - 1) / 1024) * 16) + 96;
    }
}
