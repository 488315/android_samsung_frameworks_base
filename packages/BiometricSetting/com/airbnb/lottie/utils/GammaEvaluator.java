package com.airbnb.lottie.utils;

import androidx.constraintlayout.core.widgets.analyzer.RunGroup$$ExternalSyntheticOutline0;

/* loaded from: classes.dex */
public final class GammaEvaluator {
    private static float EOCF_sRGB(float f) {
        return f <= 0.04045f ? f / 12.92f : (float) Math.pow((f + 0.055f) / 1.055f, 2.4000000953674316d);
    }

    private static float OECF_sRGB(float f) {
        return f <= 0.0031308f ? f * 12.92f : (float) ((Math.pow(f, 0.4166666567325592d) * 1.0549999475479126d) - 0.054999999701976776d);
    }

    public static int evaluate(float f, int i, int i2) {
        if (i == i2) {
            return i;
        }
        float f2 = ((i >> 24) & 255) / 255.0f;
        float f3 = ((i2 >> 24) & 255) / 255.0f;
        float EOCF_sRGB = EOCF_sRGB(((i >> 16) & 255) / 255.0f);
        float EOCF_sRGB2 = EOCF_sRGB(((i >> 8) & 255) / 255.0f);
        float EOCF_sRGB3 = EOCF_sRGB((i & 255) / 255.0f);
        float EOCF_sRGB4 = EOCF_sRGB(((i2 >> 16) & 255) / 255.0f);
        float EOCF_sRGB5 = EOCF_sRGB(((i2 >> 8) & 255) / 255.0f);
        float EOCF_sRGB6 = EOCF_sRGB((i2 & 255) / 255.0f);
        float m3m = RunGroup$$ExternalSyntheticOutline0.m3m(f3, f2, f, f2);
        float m3m2 = RunGroup$$ExternalSyntheticOutline0.m3m(EOCF_sRGB4, EOCF_sRGB, f, EOCF_sRGB);
        float m3m3 = RunGroup$$ExternalSyntheticOutline0.m3m(EOCF_sRGB5, EOCF_sRGB2, f, EOCF_sRGB2);
        float m3m4 = RunGroup$$ExternalSyntheticOutline0.m3m(EOCF_sRGB6, EOCF_sRGB3, f, EOCF_sRGB3);
        float OECF_sRGB = OECF_sRGB(m3m2) * 255.0f;
        float OECF_sRGB2 = OECF_sRGB(m3m3) * 255.0f;
        return Math.round(OECF_sRGB(m3m4) * 255.0f) | (Math.round(OECF_sRGB) << 16) | (Math.round(m3m * 255.0f) << 24) | (Math.round(OECF_sRGB2) << 8);
    }
}
