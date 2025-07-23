package com.samsung.android.wallpaper.legibilitycolors;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import com.samsung.android.wallpaper.legibilitycolors.LegibilityDefinition;
import com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor;
import com.samsung.android.wallpaper.legibilitycolors.utils.IUXColorUtils;
import com.samsung.android.wallpaper.legibilitycolors.utils.IUXMathUtils;
import com.samsung.android.wallpaper.legibilitycolors.utils.image.BitmapHelper;
import com.samsung.android.wallpaper.legibilitycolors.utils.image.ConvolutionMatrixPresets;
import com.samsung.android.wallpaper.legibilitycolors.utils.image.ImageConvolution;
import com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.EasingQuintic;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes6.dex */
public class LegibilityLogic extends ColorExtractor {
    private static final String TAG = "LegibilityLogic";
    static final float mBgBrightnessRangeBlackMax = 0.7f;
    static final float mBgBrightnessRangeBlackMin = 0.0f;
    static final float mBgBrightnessRangeWhiteMax = 0.7f;
    static final float mBgBrightnessRangeWhiteMin = 0.0f;
    static final float mBrightnessThresholdForGrayscale = 0.25f;
    static final float mSaturationThresholdForGrayscale = 0.12f;
    static float mShapeAndColorComplexityRatio = 0.6666667f;
    static final float mTextBrightnessRangeBlackMax = 0.35f;
    static final float mTextBrightnessRangeBlackMin = 0.05f;
    static final float mTextBrightnessRangeWhiteMax = 0.98f;
    static final float mTextBrightnessRangeWhiteMin = 0.89f;

    public static float getShapeAndColorComplexityRatio() {
        return mShapeAndColorComplexityRatio;
    }

    public static void setShapeAndColorComplexityRatio(float f) {
        mShapeAndColorComplexityRatio = f;
    }

    public static AdaptiveShadowData calculateAdaptiveShadow(int[] iArr, int i, int i2, LegibilityDefinition.ColorType colorType) {
        return calculateAdaptiveShadow(iArr, i, i2, colorType, calculateAdjustedDominantColors(iArr));
    }

    public static AdaptiveShadowData calculateAdaptiveShadow(int[] iArr, int i, int i2, LegibilityDefinition.ColorType colorType, ColorExtractor.DominantColorResult[] dominantColorResultArr) {
        AdaptiveShadowData adaptiveShadowData = new AdaptiveShadowData();
        float computeLuminosityComplexity = computeLuminosityComplexity(dominantColorResultArr);
        float computeContentContrastDifferentiation = computeContentContrastDifferentiation(colorType == LegibilityDefinition.ColorType.DARK ? -16777216 : -1, dominantColorResultArr);
        float computeShapeComplexity = computeShapeComplexity(iArr, i, i2);
        adaptiveShadowData.luminanceComplexity = computeLuminosityComplexity;
        adaptiveShadowData.contentContrastDiff = computeContentContrastDifferentiation;
        adaptiveShadowData.shapeComplexity = computeShapeComplexity;
        adaptiveShadowData.dominantColorResults = dominantColorResultArr;
        float f = mShapeAndColorComplexityRatio;
        float min = Math.min(Math.max((computeShapeComplexity - 0.02f) / 0.099999994f, 0.0f), 1.0f);
        float min2 = 1.0f - Math.min(Math.max((computeContentContrastDifferentiation - 0.1f) / 0.79999995f, 0.0f), 1.0f);
        float easeIn = EasingQuintic.getInstance().easeIn(min, 0.0f, 1.0f, 1.0f);
        float max = Math.max((easeIn * f) + ((1.0f - f) * min2), 0.0f);
        adaptiveShadowData.shadowOpacityNormalized = Math.max(Math.min(max, 0.8f), 1.0E-4f) / 0.8f;
        adaptiveShadowData.shadowSizeNormalized = Math.max(Math.min(max, 1.0f), 1.0E-4f);
        adaptiveShadowData.contentOpacityNormalized = adaptiveShadowData.shadowOpacityNormalized;
        adaptiveShadowData.contentContrastDiffNormalized = min2;
        adaptiveShadowData.shapeComplexityNormalized = easeIn;
        adaptiveShadowData.totalComplexity = max;
        return adaptiveShadowData;
    }

    public static float getInterpolatedShadowSize(AdaptiveShadowData adaptiveShadowData, float f, float f2) {
        return IUXMathUtils.lerp(adaptiveShadowData.shadowSizeNormalized, f, f2);
    }

    public static float getInterpolatedShadowOpacity(AdaptiveShadowData adaptiveShadowData, float f, float f2) {
        return IUXMathUtils.lerp(adaptiveShadowData.shadowOpacityNormalized, f, f2);
    }

    public static float getInterpolatedShadowYOffset(AdaptiveShadowData adaptiveShadowData, float f, float f2) {
        return IUXMathUtils.lerp(adaptiveShadowData.shadowSizeNormalized, f, f2);
    }

    public static float getInterpolatedContentOpacity(AdaptiveShadowData adaptiveShadowData, float f, float f2) {
        return IUXMathUtils.lerp(adaptiveShadowData.contentOpacityNormalized, f, f2);
    }

    public static AdaptiveShadowData calculateAdaptiveShadow(Bitmap bitmap, LegibilityDefinition.ColorType colorType) {
        int[] iArr = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return calculateAdaptiveShadow(iArr, bitmap.getWidth(), bitmap.getHeight(), colorType);
    }

    public static ColorExtractor.DominantColorResult[] calculateAdjustedDominantColors(Bitmap bitmap) {
        ColorExtractor.setSaturationThresholdForGrayscale(0.12f);
        ColorExtractor.setBrightnessThresholdForGrayscale(0.25f);
        return ColorExtractor.kMeansHsv(bitmap, ColorExtractor.makeClusterrGroup_preset1(8));
    }

    public static ColorExtractor.DominantColorResult[] calculateAdjustedDominantColors(int[] iArr) {
        ColorExtractor.DominantColorResult[] calculateDominantColors = calculateDominantColors(iArr);
        ColorExtractor.discardSameHSVfromDominantColors(calculateDominantColors, 0.0692f);
        return calculateDominantColors;
    }

    public static ColorExtractor.DominantColorResult[] calculateDominantColors(int[] iArr) {
        ColorExtractor.setSaturationThresholdForGrayscale(0.12f);
        ColorExtractor.setBrightnessThresholdForGrayscale(0.25f);
        return ColorExtractor.kMeansHsv(iArr, ColorExtractor.makeClusterGroupColorBandBased2());
    }

    public static float computeBrightnessComplexity(ColorExtractor.DominantColorResult[] dominantColorResultArr) {
        float[][] fArr = new float[dominantColorResultArr.length][];
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i = 0; i < dominantColorResultArr.length; i++) {
            if (dominantColorResultArr[i].percentage == 0.0f) {
                fArr[i] = null;
            } else {
                fArr[i] = new float[3];
                Color.colorToHSV(dominantColorResultArr[i].color, fArr[i]);
                f2 += fArr[i][2] * dominantColorResultArr[i].percentage;
            }
        }
        for (int i2 = 0; i2 < dominantColorResultArr.length; i2++) {
            float[] fArr2 = fArr[i2];
            if (fArr2 != null) {
                f += Math.abs(fArr2[2] - f2) * dominantColorResultArr[i2].percentage;
            }
        }
        return f;
    }

    public static float computeLuminosityComplexity(ColorExtractor.DominantColorResult[] dominantColorResultArr) {
        int length = dominantColorResultArr.length;
        float[] fArr = new float[length];
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i = 0; i < length; i++) {
            ColorExtractor.DominantColorResult dominantColorResult = dominantColorResultArr[i];
            if (dominantColorResult.percentage == 0.0f) {
                fArr[i] = Float.MAX_VALUE;
            } else {
                float caculateLuminosity = IUXColorUtils.caculateLuminosity(dominantColorResult.color);
                fArr[i] = caculateLuminosity;
                f2 += caculateLuminosity * dominantColorResult.percentage;
            }
        }
        for (int i2 = 0; i2 < length; i2++) {
            float f3 = fArr[i2];
            if (f3 != Float.MAX_VALUE) {
                f += Math.abs(f3 - f2) * dominantColorResultArr[i2].percentage;
            }
        }
        return f;
    }

    public static float computeContentContrastDifferentiation(int i, ColorExtractor.DominantColorResult[] dominantColorResultArr) {
        float caculateLuminosity = IUXColorUtils.caculateLuminosity(i);
        Log.i(TAG, "Content Luminance = " + caculateLuminosity);
        float f = 1.0f;
        for (ColorExtractor.DominantColorResult dominantColorResult : dominantColorResultArr) {
            if (dominantColorResult.percentage > 0.03d) {
                float caculateLuminosity2 = IUXColorUtils.caculateLuminosity(dominantColorResult.color);
                if (caculateLuminosity2 != Float.MAX_VALUE) {
                    float abs = Math.abs(caculateLuminosity2 - caculateLuminosity);
                    if (abs < f) {
                        f = abs;
                    }
                }
            }
        }
        return f;
    }

    public static float computeShapeComplexity(Bitmap bitmap) {
        int[] iArr = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return computeShapeComplexity(iArr, bitmap.getWidth(), bitmap.getHeight());
    }

    public static float computeShapeComplexity(int[] iArr, int i, int i2) {
        return getShapeComplexityConvolution(iArr, i, i2).getDifferentialValueFromRed(128.0f);
    }

    public static ImageConvolution getShapeComplexityConvolution(int[] iArr, int i, int i2) {
        ImageConvolution imageConvolution = new ImageConvolution(iArr, i, i2, Bitmap.Config.ARGB_8888);
        imageConvolution.mFactor = 1.0d;
        imageConvolution.mOffset = 128.0d;
        imageConvolution.convertToLuminosity();
        imageConvolution.computeConvolution(ConvolutionMatrixPresets.highPassFilter(5));
        return imageConvolution;
    }

    public static int calculatedAdaptiveContrastContentsColor(LegibilityDefinition.ColorType colorType, int i) {
        float lerp;
        float caculateLuminosity = IUXColorUtils.caculateLuminosity(i);
        if (LegibilityDefinition.ColorType.LIGHT == colorType) {
            lerp = IUXMathUtils.lerp(IUXMathUtils.getRatioFromRange(caculateLuminosity, 0.0f, 0.7f), mTextBrightnessRangeWhiteMin, mTextBrightnessRangeWhiteMax);
        } else {
            lerp = IUXMathUtils.lerp(IUXMathUtils.getRatioFromRange(1.0f - caculateLuminosity, 0.0f, 0.7f), mTextBrightnessRangeBlackMax, mTextBrightnessRangeBlackMin);
        }
        int i2 = (int) (lerp * 255.0f);
        return Color.rgb(i2, i2, i2);
    }

    public static int getUnequivalanttColor(int i, int i2) {
        int red = Color.red(i);
        int red2 = Color.red(i2);
        int abs = Math.abs(red - red2);
        if (abs < 4) {
            if (red >= 127) {
                if (red >= red2) {
                    red += 4 - abs;
                    if (red > Color.red(LegibilityDefinition.CONTENT_COLOR_LIGHT)) {
                        red = red2 - 4;
                    }
                }
                red -= 4 - abs;
            } else {
                if (red >= red2) {
                    red += 4 - abs;
                }
                red -= 4 - abs;
            }
        }
        return Color.rgb(red, red, red);
    }

    public static LegibilityResult calculateTotalLegibilityResult(Bitmap bitmap, LegibilityResult legibilityResult, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return calculateTotalLegibilityResult(iArr, width, height, legibilityResult, i);
    }

    public static LegibilityResult calculateTotalLegibilityResult(Bitmap bitmap, LegibilityResult legibilityResult, LegibilityDefinition.ColorType colorType, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return calculateTotalLegibilityResult(iArr, BitmapHelper.getAverageHSV(iArr), width, height, legibilityResult, colorType, i);
    }

    public static LegibilityResult calculateTotalLegibilityResult(int[] iArr, float[] fArr, int i, int i2, LegibilityResult legibilityResult, LegibilityDefinition.ColorType colorType, int i3) {
        ColorExtractor.DominantColorResult[] calculateAdjustedDominantColors = calculateAdjustedDominantColors(iArr);
        LegibilityResult legibilityResult2 = new LegibilityResult();
        legibilityResult2.avgHSV = fArr;
        legibilityResult2.adaptiveShadowData = calculateAdaptiveShadow(iArr, i, i2, colorType, calculateAdjustedDominantColors);
        legibilityResult2.contentsColorType = colorType;
        legibilityResult2.contentsColor = colorType == LegibilityDefinition.ColorType.LIGHT ? LegibilityDefinition.CONTENT_COLOR_LIGHT : -12303292;
        legibilityResult2.adjustedContentsColor = calculatedAdaptiveContrastContentsColor(colorType, IUXColorUtils.HSVToColor(legibilityResult2.avgHSV));
        legibilityResult2.dominantColorResult = calculateAdjustedDominantColors;
        return legibilityResult2;
    }

    public static LegibilityResult calculateTotalLegibilityResult(int[] iArr, int i, int i2, LegibilityResult legibilityResult, int i3) {
        float[] averageHSV = BitmapHelper.getAverageHSV(iArr);
        LegibilityDefinition.ColorType legibilityColorType = LegibilityColorByHSV.getLegibilityColorType(averageHSV[0], averageHSV[1], averageHSV[2]);
        if (legibilityResult != null && LegibilityColorByHSV.getLegibilityColorWeight(legibilityResult.contentsColorType, legibilityResult.avgHSV[0], legibilityResult.avgHSV[1], legibilityResult.avgHSV[2], legibilityColorType, averageHSV[0], averageHSV[1], averageHSV[2]) == LegibilityDefinition.ColorWeightType.UNITY) {
            legibilityColorType = legibilityResult.contentsColorType;
        }
        return calculateTotalLegibilityResult(iArr, averageHSV, i, i2, legibilityResult, legibilityColorType, i3);
    }

    public static class AdaptiveShadowData implements Cloneable {
        private static final String TAG = "AdaptiveShadowData";
        public int contentColor;
        public float contentContrastDiff;
        public float contentContrastDiffNormalized;
        public float contentOpacityNormalized;
        public ColorExtractor.DominantColorResult[] dominantColorResults;
        public float luminanceComplexity;
        public float shadowOpacityNormalized;
        public float shadowSizeNormalized;
        public float shapeComplexity;
        public float shapeComplexityNormalized;
        public float totalComplexity;

        public int hashCode() {
            return (Objects.hash(Float.valueOf(this.shadowOpacityNormalized), Float.valueOf(this.shadowSizeNormalized), Float.valueOf(this.contentOpacityNormalized), Integer.valueOf(this.contentColor), Float.valueOf(this.luminanceComplexity), Float.valueOf(this.contentContrastDiff), Float.valueOf(this.contentContrastDiffNormalized), Float.valueOf(this.shapeComplexity), Float.valueOf(this.shapeComplexityNormalized), Float.valueOf(this.totalComplexity)) * 31) + Arrays.hashCode(this.dominantColorResults);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AdaptiveShadowData)) {
                return false;
            }
            AdaptiveShadowData adaptiveShadowData = (AdaptiveShadowData) obj;
            return Math.abs(adaptiveShadowData.shadowOpacityNormalized - this.shadowOpacityNormalized) < 1.0E-4f && Math.abs(adaptiveShadowData.shadowSizeNormalized - this.shadowSizeNormalized) < 1.0E-4f && Math.abs(adaptiveShadowData.contentOpacityNormalized - this.contentOpacityNormalized) < 1.0E-4f && this.contentColor == adaptiveShadowData.contentColor && Math.abs(adaptiveShadowData.luminanceComplexity - this.luminanceComplexity) < 1.0E-4f && Math.abs(adaptiveShadowData.contentContrastDiff - this.contentContrastDiff) < 1.0E-4f && Math.abs(adaptiveShadowData.contentContrastDiffNormalized - this.contentContrastDiffNormalized) < 1.0E-4f && Math.abs(adaptiveShadowData.shapeComplexity - this.shapeComplexity) < 1.0E-4f && Math.abs(adaptiveShadowData.shapeComplexityNormalized - this.shapeComplexityNormalized) < 1.0E-4f && Math.abs(adaptiveShadowData.totalComplexity - this.totalComplexity) < 1.0E-4f && Arrays.equals(this.dominantColorResults, adaptiveShadowData.dominantColorResults);
        }

        public String toString() {
            return "\nAdaptiveShadowData{\ndominantColorResults=" + Arrays.toString(this.dominantColorResults) + "\nshadowOpacityNormalized=" + this.shadowOpacityNormalized + "\nshadowSizeNormalized=" + this.shadowSizeNormalized + "\ncontentOpacityNormalized=" + this.contentOpacityNormalized + "\ncontentColor=" + this.contentColor + "\nluminanceComplexity=" + this.luminanceComplexity + "\ncontentContrastDiff=" + this.contentContrastDiff + "\ncontentContrastDiffNormalized=" + this.contentContrastDiffNormalized + "\nshapeComplexity=" + this.shapeComplexity + "\nshapeComplexityNormalized=" + this.shapeComplexityNormalized + "\ntotalComplexity=" + this.totalComplexity + '}';
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public AdaptiveShadowData m9626clone() {
            try {
                AdaptiveShadowData adaptiveShadowData = (AdaptiveShadowData) super.clone();
                ColorExtractor.DominantColorResult[] dominantColorResultArr = this.dominantColorResults;
                if (dominantColorResultArr != null) {
                    adaptiveShadowData.dominantColorResults = new ColorExtractor.DominantColorResult[dominantColorResultArr.length];
                    int i = 0;
                    while (true) {
                        ColorExtractor.DominantColorResult[] dominantColorResultArr2 = this.dominantColorResults;
                        if (i >= dominantColorResultArr2.length) {
                            break;
                        }
                        adaptiveShadowData.dominantColorResults[i] = dominantColorResultArr2[i].m9628clone();
                        i++;
                    }
                }
                return adaptiveShadowData;
            } catch (CloneNotSupportedException e) {
                Log.d(TAG, "clone: " + e.getMessage());
                return null;
            }
        }
    }

    public static class LegibilityResult implements Cloneable {
        private static final String TAG = "LegibilityResult";
        public AdaptiveShadowData adaptiveShadowData;
        public int adjustedContentsColor;
        public float[] avgHSV;
        public int contentsColor;
        public LegibilityDefinition.ColorType contentsColorType;
        public ColorExtractor.DominantColorResult[] dominantColorResult;

        public LegibilityResult() {
            this.adaptiveShadowData = null;
            this.contentsColorType = null;
            this.contentsColor = -12303292;
            this.dominantColorResult = null;
            this.avgHSV = null;
            this.adjustedContentsColor = -16777216;
        }

        public LegibilityResult(LegibilityResult legibilityResult) {
            this.adaptiveShadowData = legibilityResult.adaptiveShadowData;
            LegibilityDefinition.ColorType colorType = legibilityResult.contentsColorType;
            this.contentsColorType = colorType;
            this.contentsColor = colorType == LegibilityDefinition.ColorType.LIGHT ? LegibilityDefinition.CONTENT_COLOR_LIGHT : -12303292;
            this.dominantColorResult = legibilityResult.dominantColorResult;
            this.avgHSV = legibilityResult.avgHSV;
            this.adjustedContentsColor = legibilityResult.adjustedContentsColor;
        }

        public LegibilityResult(LegibilityDefinition.ColorType colorType, float[] fArr) {
            this.adaptiveShadowData = null;
            this.contentsColorType = colorType;
            this.contentsColor = -12303292;
            this.dominantColorResult = null;
            this.avgHSV = fArr;
            this.adjustedContentsColor = -16777216;
        }

        public int hashCode() {
            return (((Objects.hash(this.adaptiveShadowData, this.contentsColorType, Integer.valueOf(this.contentsColor), Integer.valueOf(this.adjustedContentsColor)) * 31) + Arrays.hashCode(this.dominantColorResult)) * 31) + Arrays.hashCode(this.avgHSV);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LegibilityResult)) {
                return false;
            }
            LegibilityResult legibilityResult = (LegibilityResult) obj;
            if (this.contentsColor == legibilityResult.contentsColor && this.adjustedContentsColor == legibilityResult.adjustedContentsColor && this.adaptiveShadowData.equals(legibilityResult.adaptiveShadowData)) {
                if (((this.contentsColorType == legibilityResult.contentsColorType) & Arrays.equals(this.dominantColorResult, legibilityResult.dominantColorResult)) && Arrays.equals(this.avgHSV, legibilityResult.avgHSV)) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return "\nLegibilityResult{\n  contentsColorType=" + this.contentsColorType + "\n  contentsColor=" + this.contentsColor + "\n  adjustedContentsColor=" + this.adjustedContentsColor + "\n  dominantColorResult=" + Arrays.toString(this.dominantColorResult) + "\n  avgHSV=" + Arrays.toString(this.avgHSV) + "\n  adaptiveShadowData=" + this.adaptiveShadowData + '}';
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public LegibilityResult m9627clone() {
            try {
                LegibilityResult legibilityResult = (LegibilityResult) super.clone();
                legibilityResult.contentsColorType = this.contentsColorType;
                AdaptiveShadowData adaptiveShadowData = this.adaptiveShadowData;
                if (adaptiveShadowData != null) {
                    legibilityResult.adaptiveShadowData = adaptiveShadowData.m9626clone();
                }
                ColorExtractor.DominantColorResult[] dominantColorResultArr = this.dominantColorResult;
                int i = 0;
                if (dominantColorResultArr != null) {
                    legibilityResult.dominantColorResult = new ColorExtractor.DominantColorResult[dominantColorResultArr.length];
                    int i2 = 0;
                    while (true) {
                        ColorExtractor.DominantColorResult[] dominantColorResultArr2 = this.dominantColorResult;
                        if (i2 >= dominantColorResultArr2.length) {
                            break;
                        }
                        legibilityResult.dominantColorResult[i2] = dominantColorResultArr2[i2].m9628clone();
                        i2++;
                    }
                }
                float[] fArr = this.avgHSV;
                if (fArr != null) {
                    legibilityResult.avgHSV = new float[fArr.length];
                    while (true) {
                        float[] fArr2 = this.avgHSV;
                        if (i >= fArr2.length) {
                            break;
                        }
                        legibilityResult.avgHSV[i] = fArr2[i];
                        i++;
                    }
                }
                return legibilityResult;
            } catch (CloneNotSupportedException e) {
                Log.e(TAG, "clone: " + e.getMessage());
                return null;
            }
        }
    }
}
