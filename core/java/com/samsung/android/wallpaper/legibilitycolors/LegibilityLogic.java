package com.samsung.android.wallpaper.legibilitycolors;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

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

    public static void setShapeAndColorComplexityRatio(float ratio) {
        mShapeAndColorComplexityRatio = ratio;
    }

    public static AdaptiveShadowData calculateAdaptiveShadow(
            int[] pixels, int width, int height, LegibilityDefinition.ColorType colorType) {
        ColorExtractor.DominantColorResult[] dominantColorResult =
                calculateAdjustedDominantColors(pixels);
        return calculateAdaptiveShadow(pixels, width, height, colorType, dominantColorResult);
    }

    public static AdaptiveShadowData calculateAdaptiveShadow(
            int[] pixels,
            int width,
            int height,
            LegibilityDefinition.ColorType colorType,
            ColorExtractor.DominantColorResult[] dominantColorResults) {
        AdaptiveShadowData resultData = new AdaptiveShadowData();
        float luminanceComplexity = computeLuminosityComplexity(dominantColorResults);
        int contentColor = colorType == LegibilityDefinition.ColorType.DARK ? -16777216 : -1;
        float contentContrastComplexity =
                computeContentContrastDifferentiation(contentColor, dominantColorResults);
        float shapeComplexity = computeShapeComplexity(pixels, width, height);
        resultData.luminanceComplexity = luminanceComplexity;
        resultData.contentContrastDiff = contentContrastComplexity;
        resultData.shapeComplexity = shapeComplexity;
        resultData.dominantColorResults = dominantColorResults;
        float shapeAndColorComplexityRatio = mShapeAndColorComplexityRatio;
        float normalizedShapeComplxityValue =
                Math.min(Math.max((shapeComplexity - 0.02f) / (0.12f - 0.02f), 0.0f), 1.0f);
        float normalizedContrastComplexityValue =
                1.0f
                        - Math.min(
                                Math.max((contentContrastComplexity - 0.1f) / (0.9f - 0.1f), 0.0f),
                                1.0f);
        float normalizedShapeComplxityValue2 =
                EasingQuintic.getInstance().easeIn(normalizedShapeComplxityValue, 0.0f, 1.0f, 1.0f);
        float totalComplexityValue =
                Math.max(
                        (normalizedShapeComplxityValue2 * shapeAndColorComplexityRatio)
                                + ((1.0f - shapeAndColorComplexityRatio)
                                        * normalizedContrastComplexityValue),
                        0.0f);
        resultData.shadowOpacityNormalized =
                Math.max(Math.min(totalComplexityValue, 0.8f), 1.0E-4f) / 0.8f;
        resultData.shadowSizeNormalized = Math.max(Math.min(totalComplexityValue, 1.0f), 1.0E-4f);
        resultData.contentOpacityNormalized = resultData.shadowOpacityNormalized;
        resultData.contentContrastDiffNormalized = normalizedContrastComplexityValue;
        resultData.shapeComplexityNormalized = normalizedShapeComplxityValue2;
        resultData.totalComplexity = totalComplexityValue;
        return resultData;
    }

    public static float getInterpolatedShadowSize(
            AdaptiveShadowData resultValue, float minSizeValue, float maxSizeValue) {
        return IUXMathUtils.lerp(resultValue.shadowSizeNormalized, minSizeValue, maxSizeValue);
    }

    public static float getInterpolatedShadowOpacity(
            AdaptiveShadowData resultValue, float minOpacityValue, float maxOpacityValue) {
        return IUXMathUtils.lerp(
                resultValue.shadowOpacityNormalized, minOpacityValue, maxOpacityValue);
    }

    public static float getInterpolatedShadowYOffset(
            AdaptiveShadowData resultValue, float minYOffset, float maxYOffset) {
        return IUXMathUtils.lerp(resultValue.shadowSizeNormalized, minYOffset, maxYOffset);
    }

    public static float getInterpolatedContentOpacity(
            AdaptiveShadowData resultValue, float minSizeValue, float maxSizeValue) {
        return IUXMathUtils.lerp(resultValue.contentOpacityNormalized, minSizeValue, maxSizeValue);
    }

    public static AdaptiveShadowData calculateAdaptiveShadow(
            Bitmap bitmap, LegibilityDefinition.ColorType colorType) {
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return calculateAdaptiveShadow(pixels, bitmap.getWidth(), bitmap.getHeight(), colorType);
    }

    public static ColorExtractor.DominantColorResult[] calculateAdjustedDominantColors(
            Bitmap bitmap) {
        ColorExtractor.setSaturationThresholdForGrayscale(0.12f);
        ColorExtractor.setBrightnessThresholdForGrayscale(0.25f);
        return ColorExtractor.kMeansHsv(bitmap, ColorExtractor.makeClusterrGroup_preset1(8));
    }

    public static ColorExtractor.DominantColorResult[] calculateAdjustedDominantColors(
            int[] pixels) {
        ColorExtractor.DominantColorResult[] dominantColorResult = calculateDominantColors(pixels);
        ColorExtractor.discardSameHSVfromDominantColors(dominantColorResult, 0.0692f);
        return dominantColorResult;
    }

    public static ColorExtractor.DominantColorResult[] calculateDominantColors(int[] pixels) {
        ColorExtractor.setSaturationThresholdForGrayscale(0.12f);
        ColorExtractor.setBrightnessThresholdForGrayscale(0.25f);
        return ColorExtractor.kMeansHsv(pixels, ColorExtractor.makeClusterGroupColorBandBased2());
    }

    public static float computeBrightnessComplexity(
            ColorExtractor.DominantColorResult[] dominantColorResults) {
        float[][] hsvColors = new float[dominantColorResults.length][];
        float avgBrightness = 0.0f;
        for (int i = 0; i < dominantColorResults.length; i++) {
            if (dominantColorResults[i].percentage == 0.0f) {
                hsvColors[i] = null;
            } else {
                hsvColors[i] = new float[3];
                Color.colorToHSV(dominantColorResults[i].color, hsvColors[i]);
                avgBrightness += hsvColors[i][2] * dominantColorResults[i].percentage;
            }
        }
        float colorComplexity = 0.0f;
        for (int i2 = 0; i2 < dominantColorResults.length; i2++) {
            if (hsvColors[i2] != null) {
                colorComplexity +=
                        Math.abs(hsvColors[i2][2] - avgBrightness)
                                * dominantColorResults[i2].percentage;
            }
        }
        return colorComplexity;
    }

    public static float computeLuminosityComplexity(
            ColorExtractor.DominantColorResult[] dominantColorResults) {
        int length = dominantColorResults.length;
        float[] luminosity = new float[length];
        float avgLuminosity = 0.0f;
        for (int i = 0; i < length; i++) {
            ColorExtractor.DominantColorResult dominantColorResult = dominantColorResults[i];
            if (dominantColorResult.percentage == 0.0f) {
                luminosity[i] = Float.MAX_VALUE;
            } else {
                luminosity[i] = IUXColorUtils.caculateLuminosity(dominantColorResult.color);
                avgLuminosity += luminosity[i] * dominantColorResult.percentage;
            }
        }
        float colorComplexity = 0.0f;
        for (int i2 = 0; i2 < length; i2++) {
            if (luminosity[i2] != Float.MAX_VALUE) {
                colorComplexity +=
                        Math.abs(luminosity[i2] - avgLuminosity)
                                * dominantColorResults[i2].percentage;
            }
        }
        return colorComplexity;
    }

    public static float computeContentContrastDifferentiation(
            int contentColor, ColorExtractor.DominantColorResult[] dominantColorResults) {
        float contentContrast = IUXColorUtils.caculateLuminosity(contentColor);
        float minContrastDiff = 1.0f;
        Log.i(TAG, "Content Luminance = " + contentContrast);
        for (ColorExtractor.DominantColorResult dominantColorResult : dominantColorResults) {
            if (dominantColorResult.percentage > 0.03d) {
                float luminance = IUXColorUtils.caculateLuminosity(dominantColorResult.color);
                if (luminance != Float.MAX_VALUE) {
                    float contrast = Math.abs(luminance - contentContrast);
                    minContrastDiff = contrast < minContrastDiff ? contrast : minContrastDiff;
                }
            }
        }
        float contrastComplexity = minContrastDiff;
        return contrastComplexity;
    }

    public static float computeShapeComplexity(Bitmap bitmap) {
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return computeShapeComplexity(pixels, bitmap.getWidth(), bitmap.getHeight());
    }

    public static float computeShapeComplexity(int[] pixels, int width, int height) {
        ImageConvolution imageConvolution = getShapeComplexityConvolution(pixels, width, height);
        float complexity = imageConvolution.getDifferentialValueFromRed(128.0f);
        return complexity;
    }

    public static ImageConvolution getShapeComplexityConvolution(
            int[] pixels, int width, int height) {
        ImageConvolution imageConvolution =
                new ImageConvolution(pixels, width, height, Bitmap.Config.ARGB_8888);
        imageConvolution.mFactor = 1.0d;
        imageConvolution.mOffset = 128.0d;
        imageConvolution.convertToLuminosity();
        double[][] hf5 = ConvolutionMatrixPresets.highPassFilter(5);
        imageConvolution.computeConvolution(hf5);
        return imageConvolution;
    }

    public static int calculatedAdaptiveContrastContentsColor(
            LegibilityDefinition.ColorType colorType, int dstColor) {
        float contrastValue;
        float lumi = IUXColorUtils.caculateLuminosity(dstColor);
        if (LegibilityDefinition.ColorType.LIGHT == colorType) {
            contrastValue =
                    IUXMathUtils.lerp(
                            IUXMathUtils.getRatioFromRange(lumi, 0.0f, 0.7f),
                            mTextBrightnessRangeWhiteMin,
                            mTextBrightnessRangeWhiteMax);
        } else {
            float contrastValue2 = 1.0f - lumi;
            contrastValue =
                    IUXMathUtils.lerp(
                            IUXMathUtils.getRatioFromRange(contrastValue2, 0.0f, 0.7f),
                            mTextBrightnessRangeBlackMax,
                            mTextBrightnessRangeBlackMin);
        }
        float adjustedBrightness = contrastValue * 255.0f;
        return Color.rgb(
                (int) adjustedBrightness, (int) adjustedBrightness, (int) adjustedBrightness);
    }

    public static int getUnequivalanttColor(int currentColor, int prevContentColor) {
        int currentContentsColorBrightness = Color.red(currentColor);
        int prevContentColorBrightness = Color.red(prevContentColor);
        int diff = Math.abs(currentContentsColorBrightness - prevContentColorBrightness);
        if (diff < 4) {
            if (currentContentsColorBrightness >= 127) {
                if (currentContentsColorBrightness >= prevContentColorBrightness) {
                    currentContentsColorBrightness += 4 - diff;
                    if (currentContentsColorBrightness
                            > Color.red(LegibilityDefinition.CONTENT_COLOR_LIGHT)) {
                        currentContentsColorBrightness = prevContentColorBrightness - 4;
                    }
                } else {
                    currentContentsColorBrightness -= 4 - diff;
                }
            } else if (currentContentsColorBrightness >= prevContentColorBrightness) {
                currentContentsColorBrightness += 4 - diff;
            } else {
                currentContentsColorBrightness -= 4 - diff;
            }
        }
        int result =
                Color.rgb(
                        currentContentsColorBrightness,
                        currentContentsColorBrightness,
                        currentContentsColorBrightness);
        return result;
    }

    public static LegibilityResult calculateTotalLegibilityResult(
            Bitmap bitmap, LegibilityResult majorLegibilityResult, int userPredefinedColor) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        return calculateTotalLegibilityResult(
                pixels, width, height, majorLegibilityResult, userPredefinedColor);
    }

    public static LegibilityResult calculateTotalLegibilityResult(
            Bitmap bitmap,
            LegibilityResult majorLegibilityResult,
            LegibilityDefinition.ColorType contentColorType,
            int userPredefinedColor) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        float[] avgHSV = BitmapHelper.getAverageHSV(pixels);
        return calculateTotalLegibilityResult(
                pixels,
                avgHSV,
                width,
                height,
                majorLegibilityResult,
                contentColorType,
                userPredefinedColor);
    }

    public static LegibilityResult calculateTotalLegibilityResult(
            int[] pixels,
            float[] avgHSV,
            int width,
            int height,
            LegibilityResult majorLegibilityResult,
            LegibilityDefinition.ColorType contentColorType,
            int userPredefinedColor) {
        ColorExtractor.DominantColorResult[] dominantColorResult =
                calculateAdjustedDominantColors(pixels);
        LegibilityResult result = new LegibilityResult();
        result.avgHSV = avgHSV;
        result.adaptiveShadowData =
                calculateAdaptiveShadow(
                        pixels, width, height, contentColorType, dominantColorResult);
        result.contentsColorType = contentColorType;
        result.contentsColor =
                contentColorType == LegibilityDefinition.ColorType.LIGHT
                        ? LegibilityDefinition.CONTENT_COLOR_LIGHT
                        : -12303292;
        result.adjustedContentsColor =
                calculatedAdaptiveContrastContentsColor(
                        contentColorType, IUXColorUtils.HSVToColor(result.avgHSV));
        result.dominantColorResult = dominantColorResult;
        return result;
    }

    public static LegibilityResult calculateTotalLegibilityResult(
            int[] pixels,
            int width,
            int height,
            LegibilityResult majorLegibilityResult,
            int userPredefinedColor) {
        LegibilityDefinition.ColorType contentColorType;
        float[] avgHSV = BitmapHelper.getAverageHSV(pixels);
        LegibilityDefinition.ColorType contentColorType2 =
                LegibilityColorByHSV.getLegibilityColorType(avgHSV[0], avgHSV[1], avgHSV[2]);
        if (majorLegibilityResult != null) {
            LegibilityDefinition.ColorWeightType colorWeightType =
                    LegibilityColorByHSV.getLegibilityColorWeight(
                            majorLegibilityResult.contentsColorType,
                            majorLegibilityResult.avgHSV[0],
                            majorLegibilityResult.avgHSV[1],
                            majorLegibilityResult.avgHSV[2],
                            contentColorType2,
                            avgHSV[0],
                            avgHSV[1],
                            avgHSV[2]);
            if (colorWeightType == LegibilityDefinition.ColorWeightType.UNITY) {
                contentColorType = majorLegibilityResult.contentsColorType;
                return calculateTotalLegibilityResult(
                        pixels,
                        avgHSV,
                        width,
                        height,
                        majorLegibilityResult,
                        contentColorType,
                        userPredefinedColor);
            }
        }
        contentColorType = contentColorType2;
        return calculateTotalLegibilityResult(
                pixels,
                avgHSV,
                width,
                height,
                majorLegibilityResult,
                contentColorType,
                userPredefinedColor);
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
            int result =
                    Objects.hash(
                            Float.valueOf(this.shadowOpacityNormalized),
                            Float.valueOf(this.shadowSizeNormalized),
                            Float.valueOf(this.contentOpacityNormalized),
                            Integer.valueOf(this.contentColor),
                            Float.valueOf(this.luminanceComplexity),
                            Float.valueOf(this.contentContrastDiff),
                            Float.valueOf(this.contentContrastDiffNormalized),
                            Float.valueOf(this.shapeComplexity),
                            Float.valueOf(this.shapeComplexityNormalized),
                            Float.valueOf(this.totalComplexity));
            return (result * 31) + Arrays.hashCode(this.dominantColorResults);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof AdaptiveShadowData)) {
                return false;
            }
            AdaptiveShadowData that = (AdaptiveShadowData) o;
            return Math.abs(that.shadowOpacityNormalized - this.shadowOpacityNormalized) < 1.0E-4f
                    && Math.abs(that.shadowSizeNormalized - this.shadowSizeNormalized) < 1.0E-4f
                    && Math.abs(that.contentOpacityNormalized - this.contentOpacityNormalized)
                            < 1.0E-4f
                    && this.contentColor == that.contentColor
                    && Math.abs(that.luminanceComplexity - this.luminanceComplexity) < 1.0E-4f
                    && Math.abs(that.contentContrastDiff - this.contentContrastDiff) < 1.0E-4f
                    && Math.abs(
                                    that.contentContrastDiffNormalized
                                            - this.contentContrastDiffNormalized)
                            < 1.0E-4f
                    && Math.abs(that.shapeComplexity - this.shapeComplexity) < 1.0E-4f
                    && Math.abs(that.shapeComplexityNormalized - this.shapeComplexityNormalized)
                            < 1.0E-4f
                    && Math.abs(that.totalComplexity - this.totalComplexity) < 1.0E-4f
                    && Arrays.equals(this.dominantColorResults, that.dominantColorResults);
        }

        public String toString() {
            return "\nAdaptiveShadowData{\ndominantColorResults="
                    + Arrays.toString(this.dominantColorResults)
                    + "\nshadowOpacityNormalized="
                    + this.shadowOpacityNormalized
                    + "\nshadowSizeNormalized="
                    + this.shadowSizeNormalized
                    + "\ncontentOpacityNormalized="
                    + this.contentOpacityNormalized
                    + "\ncontentColor="
                    + this.contentColor
                    + "\nluminanceComplexity="
                    + this.luminanceComplexity
                    + "\ncontentContrastDiff="
                    + this.contentContrastDiff
                    + "\ncontentContrastDiffNormalized="
                    + this.contentContrastDiffNormalized
                    + "\nshapeComplexity="
                    + this.shapeComplexity
                    + "\nshapeComplexityNormalized="
                    + this.shapeComplexityNormalized
                    + "\ntotalComplexity="
                    + this.totalComplexity
                    + '}';
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public AdaptiveShadowData m9225clone() {
            try {
                AdaptiveShadowData clone = (AdaptiveShadowData) super.clone();
                if (this.dominantColorResults != null) {
                    clone.dominantColorResults =
                            new ColorExtractor.DominantColorResult
                                    [this.dominantColorResults.length];
                    for (int i = 0; i < this.dominantColorResults.length; i++) {
                        clone.dominantColorResults[i] = this.dominantColorResults[i].m9227clone();
                    }
                }
                return clone;
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
            this.contentsColorType = legibilityResult.contentsColorType;
            this.contentsColor =
                    this.contentsColorType == LegibilityDefinition.ColorType.LIGHT
                            ? LegibilityDefinition.CONTENT_COLOR_LIGHT
                            : -12303292;
            this.dominantColorResult = legibilityResult.dominantColorResult;
            this.avgHSV = legibilityResult.avgHSV;
            this.adjustedContentsColor = legibilityResult.adjustedContentsColor;
        }

        public LegibilityResult(LegibilityDefinition.ColorType contentsColorType, float[] avgHSV) {
            this.adaptiveShadowData = null;
            this.contentsColorType = contentsColorType;
            this.contentsColor = -12303292;
            this.dominantColorResult = null;
            this.avgHSV = avgHSV;
            this.adjustedContentsColor = -16777216;
        }

        public int hashCode() {
            int result =
                    Objects.hash(
                            this.adaptiveShadowData,
                            this.contentsColorType,
                            Integer.valueOf(this.contentsColor),
                            Integer.valueOf(this.adjustedContentsColor));
            return (((result * 31) + Arrays.hashCode(this.dominantColorResult)) * 31)
                    + Arrays.hashCode(this.avgHSV);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof LegibilityResult)) {
                return false;
            }
            LegibilityResult that = (LegibilityResult) o;
            if (this.contentsColor == that.contentsColor
                    && this.adjustedContentsColor == that.adjustedContentsColor
                    && this.adaptiveShadowData.equals(that.adaptiveShadowData)) {
                if (((this.contentsColorType == that.contentsColorType)
                                & Arrays.equals(this.dominantColorResult, that.dominantColorResult))
                        && Arrays.equals(this.avgHSV, that.avgHSV)) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return "\nLegibilityResult{\n  contentsColorType="
                    + this.contentsColorType
                    + "\n  contentsColor="
                    + this.contentsColor
                    + "\n  adjustedContentsColor="
                    + this.adjustedContentsColor
                    + "\n  dominantColorResult="
                    + Arrays.toString(this.dominantColorResult)
                    + "\n  avgHSV="
                    + Arrays.toString(this.avgHSV)
                    + "\n  adaptiveShadowData="
                    + this.adaptiveShadowData
                    + '}';
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public LegibilityResult m9226clone() {
            try {
                LegibilityResult clone = (LegibilityResult) super.clone();
                clone.contentsColorType = this.contentsColorType;
                if (this.adaptiveShadowData != null) {
                    clone.adaptiveShadowData = this.adaptiveShadowData.m9225clone();
                }
                if (this.dominantColorResult != null) {
                    clone.dominantColorResult =
                            new ColorExtractor.DominantColorResult[this.dominantColorResult.length];
                    for (int i = 0; i < this.dominantColorResult.length; i++) {
                        clone.dominantColorResult[i] = this.dominantColorResult[i].m9227clone();
                    }
                }
                if (this.avgHSV != null) {
                    clone.avgHSV = new float[this.avgHSV.length];
                    for (int i2 = 0; i2 < this.avgHSV.length; i2++) {
                        clone.avgHSV[i2] = this.avgHSV[i2];
                    }
                }
                return clone;
            } catch (CloneNotSupportedException e) {
                Log.e(TAG, "clone: " + e.getMessage());
                return null;
            }
        }
    }
}
