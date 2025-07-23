package com.airbnb.lottie.utils;

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class GammaEvaluator {
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
        float m$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f3, f2, f, f2);
        float m$12 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(EOCF_sRGB4, EOCF_sRGB, f, EOCF_sRGB);
        float m$13 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(EOCF_sRGB5, EOCF_sRGB2, f, EOCF_sRGB2);
        float m$14 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(EOCF_sRGB6, EOCF_sRGB3, f, EOCF_sRGB3);
        float OECF_sRGB = OECF_sRGB(m$12) * 255.0f;
        float OECF_sRGB2 = OECF_sRGB(m$13) * 255.0f;
        return Math.round(OECF_sRGB(m$14) * 255.0f) | (Math.round(OECF_sRGB) << 16) | (Math.round(m$1 * 255.0f) << 24) | (Math.round(OECF_sRGB2) << 8);
    }
}
