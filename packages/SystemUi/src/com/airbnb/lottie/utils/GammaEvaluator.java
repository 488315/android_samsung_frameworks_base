package com.airbnb.lottie.utils;

import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GammaEvaluator {
    public static float EOCF_sRGB(float f) {
        return f <= 0.04045f ? f / 12.92f : (float) Math.pow((f + 0.055f) / 1.055f, 2.4000000953674316d);
    }

    public static float OECF_sRGB(float f) {
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
        float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(f3, f2, f, f2);
        float m20m2 = DependencyGraph$$ExternalSyntheticOutline0.m20m(EOCF_sRGB4, EOCF_sRGB, f, EOCF_sRGB);
        float m20m3 = DependencyGraph$$ExternalSyntheticOutline0.m20m(EOCF_sRGB5, EOCF_sRGB2, f, EOCF_sRGB2);
        float m20m4 = DependencyGraph$$ExternalSyntheticOutline0.m20m(EOCF_sRGB6, EOCF_sRGB3, f, EOCF_sRGB3);
        float OECF_sRGB = OECF_sRGB(m20m2) * 255.0f;
        float OECF_sRGB2 = OECF_sRGB(m20m3) * 255.0f;
        return Math.round(OECF_sRGB(m20m4) * 255.0f) | (Math.round(OECF_sRGB) << 16) | (Math.round(m20m * 255.0f) << 24) | (Math.round(OECF_sRGB2) << 8);
    }
}
