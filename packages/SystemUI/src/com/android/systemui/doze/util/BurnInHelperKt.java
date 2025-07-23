package com.android.systemui.doze.util;

import android.util.MathUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class BurnInHelperKt {
    public static final int getBurnInOffset(int i, boolean z) {
        return (int) zigzag(System.currentTimeMillis() / 60000.0f, i, z ? 83.0f : 521.0f);
    }

    public static final float zigzag(float f, float f2, float f3) {
        float f4 = 2;
        float f5 = (f % f3) / (f3 / f4);
        if (f5 > 1.0f) {
            f5 = f4 - f5;
        }
        return MathUtils.lerp(0.0f, f2, f5);
    }
}
