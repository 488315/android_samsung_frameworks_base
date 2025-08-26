package com.android.internal.display;

import android.util.MathUtils;

/* loaded from: classes5.dex */
public class BrightnessUtils {
    private static final float A = 0.17883277f;
    private static final float B = 0.28466892f;
    private static final float C = 0.5599107f;
    private static final float R = 0.5f;

    public static final float convertGammaToLinear(float f) {
        float fExp;
        if (f <= 0.5f) {
            fExp = MathUtils.sq(f / 0.5f);
        } else {
            fExp = MathUtils.exp((f - C) / A) + B;
        }
        return MathUtils.constrain(fExp, 0.0f, 12.0f) / 12.0f;
    }

    public static final float convertLinearToGamma(float f) {
        float f2 = f * 12.0f;
        if (f2 <= 1.0f) {
            return MathUtils.sqrt(f2) * 0.5f;
        }
        return (MathUtils.log(f2 - B) * A) + C;
    }
}
