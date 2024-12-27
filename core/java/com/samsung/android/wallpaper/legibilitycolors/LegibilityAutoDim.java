package com.samsung.android.wallpaper.legibilitycolors;


public class LegibilityAutoDim {
    static float mMinimumComplexityForAutoDim = 0.8f;
    static float mMaximumComplexityForAutoDim = 1.0f;

    public static class AutoDimResult {
        public int color;
        public float maxComplexity;
        public float opacity;
        public float validMaxComplexity;
    }

    public static float getMinimumComplexityForAutoDim() {
        return mMinimumComplexityForAutoDim;
    }

    public static void setMinimumComplexityForAutoDim(float minimumValue) {
        mMinimumComplexityForAutoDim = minimumValue;
    }

    public static float getMaximumComplexityForAutoDim() {
        return mMaximumComplexityForAutoDim;
    }

    public static void setMaximumComplexityForAutoDim(float maximumValue) {
        mMaximumComplexityForAutoDim = maximumValue;
    }

    public static AutoDimResult calculateAdaptiveDim(
            LegibilityLogic.LegibilityResult[] legibilityResultsArray) {
        AutoDimResult dimValues = new AutoDimResult();
        float minimumComplexValue = mMinimumComplexityForAutoDim;
        float maximumComplexValue = mMaximumComplexityForAutoDim;
        int maxComplexRegionID = -1;
        float validMaxComplexValue = 0.0f;
        float maxComplexValue = 0.0f;
        int size = legibilityResultsArray.length;
        for (int i = 0; i < size; i++) {
            float totalComplexity = legibilityResultsArray[i].adaptiveShadowData.totalComplexity;
            if (totalComplexity > minimumComplexValue && totalComplexity > validMaxComplexValue) {
                maxComplexRegionID = i;
                validMaxComplexValue = totalComplexity;
            }
            if (totalComplexity > maxComplexValue) {
                maxComplexValue = totalComplexity;
            }
        }
        dimValues.maxComplexity = maxComplexValue;
        dimValues.validMaxComplexity = validMaxComplexValue;
        if (maxComplexRegionID != -1) {
            LegibilityLogic.AdaptiveShadowData adaptiveShadowData =
                    legibilityResultsArray[maxComplexRegionID].adaptiveShadowData;
            dimValues.opacity =
                    Math.min(
                                    (adaptiveShadowData.totalComplexity - minimumComplexValue)
                                            / (maximumComplexValue - minimumComplexValue),
                                    1.0f)
                            * 0.1f;
            dimValues.color = -16777216 != adaptiveShadowData.contentColor ? -16777216 : -1;
        } else {
            dimValues.opacity = 0.0f;
            dimValues.color = -16777216;
        }
        return dimValues;
    }
}
