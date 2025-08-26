package com.samsung.android.wallpaper.legibilitycolors;

import android.hardware.scontext.SContextConstants;
import com.android.internal.graphics.ColorUtils;
import com.samsung.android.wallpaper.legibilitycolors.LegibilityDefinition;
import com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor;
import com.samsung.android.wallpaper.legibilitycolors.utils.IUXColorUtils;

/* loaded from: classes6.dex */
public class LegibilityColorByHSV {
    private static final float BASE_LUMINANCE = 68.0f;
    static final int BLACK_COLOR = -16777216;
    static final float CONTRAST_BLACK_THRESHOLD = 1.34f;
    static final float CONTRAST_WHITE_THRESHOLD = 1.24f;
    static final float DIFF_V = 0.11f;
    static final float PERCENTAGE_THRESHOLD = 0.22f;
    static final float SIMILAR_CONTRAST_THRESHOLD = 1.8f;
    static final float SIMILAR_PERCENTAGE_THRESHOLD = 0.1f;
    static final int WHITE_COLOR = -1;
    private static final double XYZ_EPSILON = 0.008856d;
    private static final double XYZ_KAPPA = 903.3d;
    static SimilarColorResult mSimilarColorResult = new SimilarColorResult();

    public static class EdgeCaseResultForIndicator {
        public float black_contrast_percent;
        public int color;
        public LegibilityDefinition.ColorType colorType;
        public LegibilityDefinition.ColorType initColorType;
        public boolean isEdgeCase;
        public float white_contrast_percent;
    }

    private static float getLABLfromHSV(float f, float f2, float f3) {
        double d;
        double d2;
        double dPow;
        double d3;
        double d4;
        double d5;
        double d6;
        double d7 = f;
        double d8 = f2;
        double d9 = f3;
        double d10 = ((2.0d - d8) * d9) / 2.0d;
        if (SContextConstants.ENVIRONMENT_VALUE_UNKNOWN != d10) {
            if (1.0d == d10) {
                d8 = 0.0d;
            } else {
                if (d10 < 0.5d) {
                    d5 = d8 * d9;
                    d6 = d10 * 2.0d;
                } else {
                    d5 = d8 * d9;
                    d6 = 2.0d - (d10 * 2.0d);
                }
                d8 = d5 / d6;
            }
        }
        int i = ((int) d7) / 60;
        double dAbs = 1.0d - Math.abs(((d7 / 60.0d) % 2.0d) - 1.0d);
        double d11 = (2.0d * d10) - 1.0d;
        if (d11 <= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            d11 = -d11;
        }
        double d12 = (1.0d - d11) * d8;
        double d13 = d10 - (0.5d * d12);
        double d14 = dAbs * d12;
        switch (i) {
            case 0:
                d = d12 + d13;
                d2 = d14 + d13;
                dPow = d13;
                d13 = d;
                break;
            case 1:
                d2 = d12 + d13;
                d3 = d13;
                d13 = d14 + d13;
                dPow = d3;
                break;
            case 2:
                d2 = d12 + d13;
                dPow = d14 + d13;
                break;
            case 3:
                d3 = d12 + d13;
                d2 = d14 + d13;
                dPow = d3;
                break;
            case 4:
                double d15 = d12 + d13;
                d13 = d14 + d13;
                dPow = d15;
                d2 = d13;
                break;
            case 5:
            case 6:
                dPow = d14 + d13;
                d = d12 + d13;
                d2 = d13;
                d13 = d;
                break;
            default:
                dPow = 0.0d;
                d13 = 0.0d;
                d2 = 0.0d;
                break;
        }
        double dPow2 = d13 < 0.04045d ? d13 * 0.01645510835913313d : 0.2126d * Math.pow((d13 / 1.055d) + 0.05213270142180095d, 2.4d);
        double dPow3 = d2 < 0.04045d ? d2 * 0.05535603715170278d : Math.pow((d2 / 1.055d) + 0.05213270142180095d, 2.4d) * 0.7152d;
        if (dPow < 0.04045d) {
            d4 = 0.005588235294117647d;
        } else {
            dPow = Math.pow((dPow / 1.055d) + 0.05213270142180095d, 2.4d);
            d4 = 0.0722d;
        }
        double d16 = dPow2 + dPow3 + (dPow * d4);
        return (float) Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, ((d16 > XYZ_EPSILON ? Math.cbrt(d16) : 0.13793103448275862d + (d16 * 7.787068965517241d)) * 116.0d) - 16.0d);
    }

    public static LegibilityDefinition.ColorType getLegibilityColorType(float f, float f2, float f3) {
        return BASE_LUMINANCE < getLABLfromHSV(f, f2, f3) ? LegibilityDefinition.ColorType.DARK : LegibilityDefinition.ColorType.LIGHT;
    }

    public static LegibilityDefinition.ColorWeightType getLegibilityColorWeight(LegibilityDefinition.ColorType colorType, float f, float f2, float f3, LegibilityDefinition.ColorType colorType2, float f4, float f5, float f6) {
        return mGetLegibilityColorWeight(colorType, f, f2, f3, colorType2, f4, f5, f6);
    }

    private static LegibilityDefinition.ColorWeightType mGetLegibilityColorWeight(LegibilityDefinition.ColorType colorType, float f, float f2, float f3, LegibilityDefinition.ColorType colorType2, float f4, float f5, float f6) {
        LegibilityDefinition.ColorWeightType colorWeightType = LegibilityDefinition.ColorWeightType.EACH;
        if (Math.abs(f3 - f6) >= DIFF_V || colorType2 != LegibilityDefinition.ColorType.LIGHT) {
            return colorWeightType;
        }
        if (f5 < 0.9f && f6 > 90.0f) {
            return LegibilityDefinition.ColorWeightType.EACH;
        }
        return LegibilityDefinition.ColorWeightType.UNITY;
    }

    public static EdgeCaseResultForIndicator calcurateIndicatorLegibility(int[] iArr) {
        ColorHSV colorHSV = new ColorHSV();
        colorHSV.calcAvgColor(iArr);
        return checkEdgeCaseForIndicator(ColorExtractor.kMeansHsv(iArr, ColorExtractor.makeClusterGroupColorBandBased2()), getLegibilityColorType(colorHSV.getAvgH(), colorHSV.getAvgS(), colorHSV.getAvgV()));
    }

    private static EdgeCaseResultForIndicator checkEdgeCaseForIndicator(ColorExtractor.DominantColorResult[] dominantColorResultArr, LegibilityDefinition.ColorType colorType) {
        EdgeCaseResultForIndicator edgeCaseResultForIndicator = new EdgeCaseResultForIndicator();
        edgeCaseResultForIndicator.initColorType = colorType;
        edgeCaseResultForIndicator.colorType = LegibilityDefinition.ColorType.NONE;
        edgeCaseResultForIndicator.isEdgeCase = false;
        edgeCaseResultForIndicator.white_contrast_percent = 0.0f;
        edgeCaseResultForIndicator.black_contrast_percent = 0.0f;
        edgeCaseResultForIndicator.color = -1;
        float f = 0.0f;
        float f2 = 0.0f;
        for (ColorExtractor.DominantColorResult dominantColorResult : dominantColorResultArr) {
            if (dominantColorResult.percentage <= 0.0f) {
                break;
            }
            if (ColorUtils.calculateContrast(-1, dominantColorResult.color) < 1.2400000095367432d) {
                f += dominantColorResult.percentage;
            }
            if (ColorUtils.calculateContrast(-16777216, dominantColorResult.color) < 1.340000033378601d) {
                f2 += dominantColorResult.percentage;
            }
        }
        edgeCaseResultForIndicator.white_contrast_percent = f;
        edgeCaseResultForIndicator.black_contrast_percent = f2;
        if (f > PERCENTAGE_THRESHOLD && f2 > PERCENTAGE_THRESHOLD) {
            edgeCaseResultForIndicator.isEdgeCase = true;
            if (f >= f2) {
                edgeCaseResultForIndicator.color = IUXColorUtils.HSVToColor(new float[]{0.0f, 0.0f, ((f2 / 0.5f) * 0.3f) + 0.2f});
                edgeCaseResultForIndicator.colorType = LegibilityDefinition.ColorType.GRAY;
            } else if (f < f2) {
                edgeCaseResultForIndicator.color = IUXColorUtils.HSVToColor(new float[]{0.0f, 0.0f, 0.8f - ((f / 0.5f) * 0.3f)});
                edgeCaseResultForIndicator.colorType = LegibilityDefinition.ColorType.GRAY;
            }
            if (checkSimilarColor(dominantColorResultArr, colorType, edgeCaseResultForIndicator.color)) {
                edgeCaseResultForIndicator.color = mSimilarColorResult.color;
                edgeCaseResultForIndicator.colorType = mSimilarColorResult.colorType;
                edgeCaseResultForIndicator.isEdgeCase = false;
            }
            return edgeCaseResultForIndicator;
        }
        if (f > PERCENTAGE_THRESHOLD) {
            edgeCaseResultForIndicator.isEdgeCase = true;
            edgeCaseResultForIndicator.color = -16777216;
            edgeCaseResultForIndicator.colorType = LegibilityDefinition.ColorType.DARK;
            return edgeCaseResultForIndicator;
        }
        if (f2 > PERCENTAGE_THRESHOLD) {
            edgeCaseResultForIndicator.isEdgeCase = true;
            edgeCaseResultForIndicator.color = -1;
            edgeCaseResultForIndicator.colorType = LegibilityDefinition.ColorType.LIGHT;
            return edgeCaseResultForIndicator;
        }
        edgeCaseResultForIndicator.isEdgeCase = false;
        edgeCaseResultForIndicator.colorType = colorType;
        return edgeCaseResultForIndicator;
    }

    private static boolean checkSimilarColor(ColorExtractor.DominantColorResult[] dominantColorResultArr, LegibilityDefinition.ColorType colorType, int i) {
        for (ColorExtractor.DominantColorResult dominantColorResult : dominantColorResultArr) {
            if (ColorUtils.calculateContrast(i, dominantColorResult.color) < 1.7999999523162842d && dominantColorResult.percentage > 0.1f) {
                if (colorType == LegibilityDefinition.ColorType.LIGHT) {
                    mSimilarColorResult.color = -1;
                    mSimilarColorResult.colorType = LegibilityDefinition.ColorType.LIGHT;
                    return true;
                }
                if (colorType != LegibilityDefinition.ColorType.DARK) {
                    return true;
                }
                mSimilarColorResult.color = -16777216;
                mSimilarColorResult.colorType = LegibilityDefinition.ColorType.DARK;
                return true;
            }
        }
        return false;
    }

    private static class SimilarColorResult {
        int color;
        LegibilityDefinition.ColorType colorType;

        private SimilarColorResult() {
        }
    }
}
