package com.samsung.android.wallpaper.legibilitycolors;

import com.samsung.android.wallpaper.legibilitycolors.LegibilityLogic;

/* loaded from: classes6.dex */
public class LegibilityAutoDim {
    static float mMaximumComplexityForAutoDim = 1.0f;
    static float mMinimumComplexityForAutoDim = 0.8f;

    public static class AutoDimResult {
        public int color;
        public float maxComplexity;
        public float opacity;
        public float validMaxComplexity;
    }

    public static float getMinimumComplexityForAutoDim() {
        return mMinimumComplexityForAutoDim;
    }

    public static void setMinimumComplexityForAutoDim(float f) {
        mMinimumComplexityForAutoDim = f;
    }

    public static float getMaximumComplexityForAutoDim() {
        return mMaximumComplexityForAutoDim;
    }

    public static void setMaximumComplexityForAutoDim(float f) {
        mMaximumComplexityForAutoDim = f;
    }

    public static AutoDimResult calculateAdaptiveDim(LegibilityLogic.LegibilityResult[] legibilityResultArr) {
        AutoDimResult autoDimResult = new AutoDimResult();
        float f = mMinimumComplexityForAutoDim;
        float f2 = mMaximumComplexityForAutoDim;
        int length = legibilityResultArr.length;
        int i = -1;
        float f3 = 0.0f;
        float f4 = 0.0f;
        for (int i2 = 0; i2 < length; i2++) {
            float f5 = legibilityResultArr[i2].adaptiveShadowData.totalComplexity;
            if (f5 > f && f5 > f4) {
                i = i2;
                f4 = f5;
            }
            if (f5 > f3) {
                f3 = f5;
            }
        }
        autoDimResult.maxComplexity = f3;
        autoDimResult.validMaxComplexity = f4;
        if (i != -1) {
            LegibilityLogic.AdaptiveShadowData adaptiveShadowData = legibilityResultArr[i].adaptiveShadowData;
            autoDimResult.opacity = Math.min((adaptiveShadowData.totalComplexity - f) / (f2 - f), 1.0f) * 0.1f;
            autoDimResult.color = -16777216 != adaptiveShadowData.contentColor ? -16777216 : -1;
            return autoDimResult;
        }
        autoDimResult.opacity = 0.0f;
        autoDimResult.color = -16777216;
        return autoDimResult;
    }
}
