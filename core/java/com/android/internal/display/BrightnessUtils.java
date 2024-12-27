package com.android.internal.display;

import android.util.MathUtils;

public class BrightnessUtils {
    private static final float A = 0.17883277f;
    private static final float B = 0.28466892f;
    private static final float C = 0.5599107f;
    private static final float R = 0.5f;

    public static final float convertGammaToLinear(float val) {
        float ret;
        if (val <= 0.5f) {
            ret = MathUtils.sq(val / 0.5f);
        } else {
            ret = MathUtils.exp((val - C) / A) + B;
        }
        float normalizedRet = MathUtils.constrain(ret, 0.0f, 12.0f);
        return normalizedRet / 12.0f;
    }

    public static final float convertLinearToGamma(float val) {
        float normalizedVal = 12.0f * val;
        if (normalizedVal <= 1.0f) {
            float ret = MathUtils.sqrt(normalizedVal) * 0.5f;
            return ret;
        }
        float ret2 = (MathUtils.log(normalizedVal - B) * A) + C;
        return ret2;
    }
}
