package com.samsung.android.wallpaper.legibilitycolors;

import android.hardware.scontext.SContextConstants;

import com.android.internal.graphics.ColorUtils;

import com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor;
import com.samsung.android.wallpaper.legibilitycolors.utils.IUXColorUtils;

/* loaded from: classes6.dex */
public class LegibilityColorByHSV {
    private static final float BASE_LUMINANCE = 67.0f;
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

    private static float getLABLfromHSV(float hsv_h, float hsv_S, float hsv_v) {
        double h = hsv_h;
        double s = hsv_S;
        double l = ((2.0d - s) * hsv_v) / 2.0d;
        if (SContextConstants.ENVIRONMENT_VALUE_UNKNOWN != l) {
            if (1.0d != l) {
                s = l < 0.5d ? (hsv_v * s) / (l * 2.0d) : (hsv_v * s) / (2.0d - (l * 2.0d));
            } else {
                s = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            }
        }
        int hueSegment = ((int) h) / 60;
        double xH = 1.0d - Math.abs(((h / 60.0d) % 2.0d) - 1.0d);
        double sr = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        double sg = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        double sb = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        double labs = (2.0d * l) - 1.0d;
        double c = (1.0d - (labs > SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? labs : -labs)) * s;
        double m = l - (0.5d * c);
        double x = c * xH;
        switch (hueSegment) {
            case 0:
                sr = c + m;
                sg = x + m;
                sb = m;
                break;
            case 1:
                sr = x + m;
                sg = c + m;
                sb = m;
                break;
            case 2:
                sr = m;
                sg = c + m;
                sb = x + m;
                break;
            case 3:
                sr = m;
                sg = x + m;
                sb = c + m;
                break;
            case 4:
                sr = x + m;
                sg = m;
                sb = c + m;
                break;
            case 5:
            case 6:
                sr = c + m;
                sg = m;
                sb = x + m;
                break;
        }
        double y =
                (sr < 0.04045d
                                ? 0.01645510835913313d * sr
                                : 0.2126d * Math.pow((sr / 1.055d) + 0.05213270142180095d, 2.4d))
                        + (sg < 0.04045d
                                ? 0.05535603715170278d * sg
                                : 0.7152d * Math.pow((sg / 1.055d) + 0.05213270142180095d, 2.4d))
                        + (sb < 0.04045d
                                ? 0.005588235294117647d * sb
                                : Math.pow((sb / 1.055d) + 0.05213270142180095d, 2.4d) * 0.0722d);
        double sb2 =
                (116.0d
                                * (y > XYZ_EPSILON
                                        ? Math.cbrt(y)
                                        : (7.787068965517241d * y) + 0.13793103448275862d))
                        - 16.0d;
        return (float) Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, sb2);
    }

    public static LegibilityDefinition.ColorType getLegibilityColorType(float h, float s, float v) {
        return BASE_LUMINANCE < getLABLfromHSV(h, s, v)
                ? LegibilityDefinition.ColorType.DARK
                : LegibilityDefinition.ColorType.LIGHT;
    }

    public static LegibilityDefinition.ColorWeightType getLegibilityColorWeight(
            LegibilityDefinition.ColorType majorColorType,
            float majorH,
            float majorS,
            float majorV,
            LegibilityDefinition.ColorType minorColorType,
            float minorH,
            float minorS,
            float minorV) {
        return mGetLegibilityColorWeight(
                majorColorType, majorH, majorS, majorV, minorColorType, minorH, minorS, minorV);
    }

    private static LegibilityDefinition.ColorWeightType mGetLegibilityColorWeight(
            LegibilityDefinition.ColorType majorColorType,
            float majorH,
            float majorS,
            float majorV,
            LegibilityDefinition.ColorType minorColorType,
            float minorH,
            float minorS,
            float minorV) {
        LegibilityDefinition.ColorWeightType ret = LegibilityDefinition.ColorWeightType.EACH;
        if (Math.abs(majorV - minorV) >= DIFF_V
                || minorColorType != LegibilityDefinition.ColorType.LIGHT) {
            return ret;
        }
        if (minorS < 0.9f && minorV > 90.0f) {
            return LegibilityDefinition.ColorWeightType.EACH;
        }
        return LegibilityDefinition.ColorWeightType.UNITY;
    }

    public static EdgeCaseResultForIndicator calcurateIndicatorLegibility(int[] pixels) {
        ColorHSV indicator_hsv = new ColorHSV();
        indicator_hsv.calcAvgColor(pixels);
        LegibilityDefinition.ColorType colorType =
                getLegibilityColorType(
                        indicator_hsv.getAvgH(), indicator_hsv.getAvgS(), indicator_hsv.getAvgV());
        ColorExtractor.DominantColorResult[] result =
                ColorExtractor.kMeansHsv(pixels, ColorExtractor.makeClusterGroupColorBandBased2());
        return checkEdgeCaseForIndicator(result, colorType);
    }

    private static EdgeCaseResultForIndicator checkEdgeCaseForIndicator(
            ColorExtractor.DominantColorResult[] result, LegibilityDefinition.ColorType colorType) {
        EdgeCaseResultForIndicator ret = new EdgeCaseResultForIndicator();
        ret.initColorType = colorType;
        ret.colorType = LegibilityDefinition.ColorType.NONE;
        ret.isEdgeCase = false;
        ret.white_contrast_percent = 0.0f;
        ret.black_contrast_percent = 0.0f;
        ret.color = -1;
        float white_contrast_percent = 0.0f;
        float black_contrast_percent = 0.0f;
        for (ColorExtractor.DominantColorResult dominantColorResult : result) {
            if (dominantColorResult.percentage <= 0.0f) {
                break;
            }
            if (ColorUtils.calculateContrast(-1, dominantColorResult.color) < 1.2400000095367432d) {
                white_contrast_percent += dominantColorResult.percentage;
            }
            if (ColorUtils.calculateContrast(-16777216, dominantColorResult.color)
                    < 1.340000033378601d) {
                black_contrast_percent += dominantColorResult.percentage;
            }
        }
        ret.white_contrast_percent = white_contrast_percent;
        ret.black_contrast_percent = black_contrast_percent;
        if (white_contrast_percent > PERCENTAGE_THRESHOLD
                && black_contrast_percent > PERCENTAGE_THRESHOLD) {
            ret.isEdgeCase = true;
            if (white_contrast_percent >= black_contrast_percent) {
                float[] color = {0.0f, 0.0f, ((black_contrast_percent / 0.5f) * 0.3f) + 0.2f};
                ret.color = IUXColorUtils.HSVToColor(color);
                ret.colorType = LegibilityDefinition.ColorType.GRAY;
            } else if (white_contrast_percent < black_contrast_percent) {
                float[] color2 = {0.0f, 0.0f, 0.8f - ((white_contrast_percent / 0.5f) * 0.3f)};
                ret.color = IUXColorUtils.HSVToColor(color2);
                ret.colorType = LegibilityDefinition.ColorType.GRAY;
            }
            if (checkSimilarColor(result, colorType, ret.color)) {
                ret.color = mSimilarColorResult.color;
                ret.colorType = mSimilarColorResult.colorType;
                ret.isEdgeCase = false;
            }
        } else if (white_contrast_percent > PERCENTAGE_THRESHOLD) {
            ret.isEdgeCase = true;
            ret.color = -16777216;
            ret.colorType = LegibilityDefinition.ColorType.DARK;
        } else if (black_contrast_percent > PERCENTAGE_THRESHOLD) {
            ret.isEdgeCase = true;
            ret.color = -1;
            ret.colorType = LegibilityDefinition.ColorType.LIGHT;
        } else {
            ret.isEdgeCase = false;
            ret.colorType = colorType;
        }
        return ret;
    }

    private static boolean checkSimilarColor(
            ColorExtractor.DominantColorResult[] result,
            LegibilityDefinition.ColorType colorType,
            int refColor) {
        for (ColorExtractor.DominantColorResult colorResult : result) {
            double tmpContrast = ColorUtils.calculateContrast(refColor, colorResult.color);
            if (tmpContrast < 1.7999999523162842d && colorResult.percentage > 0.1f) {
                if (colorType == LegibilityDefinition.ColorType.LIGHT) {
                    mSimilarColorResult.color = -1;
                    mSimilarColorResult.colorType = LegibilityDefinition.ColorType.LIGHT;
                } else if (colorType == LegibilityDefinition.ColorType.DARK) {
                    mSimilarColorResult.color = -16777216;
                    mSimilarColorResult.colorType = LegibilityDefinition.ColorType.DARK;
                }
                return true;
            }
        }
        return false;
    }

    private static class SimilarColorResult {
        int color;
        LegibilityDefinition.ColorType colorType;

        private SimilarColorResult() {}
    }
}
