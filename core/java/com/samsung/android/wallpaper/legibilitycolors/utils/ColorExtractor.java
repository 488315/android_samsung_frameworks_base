package com.samsung.android.wallpaper.legibilitycolors.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Spanned;
import android.util.Log;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/* loaded from: classes6.dex */
public class ColorExtractor {
    static final String TAG = "ColorExtractor";
    public static final String VERSION = "1.0.0";
    static float sSaturationThresholdForGrayscale = 0.12f;
    static float sBrightnessThresholdForGrayscale = 0.25f;
    static float[] sClusterHsvDistanceWeight = {1.0f, 0.1f, 0.1f};
    static float[] sClusterGrayscaleDistanceWeight = {0.0f, 0.0f, 1.0f};

    public static float getSaturationThresholdForGrayscale() {
        return sSaturationThresholdForGrayscale;
    }

    public static void setSaturationThresholdForGrayscale(float saturationThreshold) {
        sSaturationThresholdForGrayscale = saturationThreshold;
    }

    public static float getBrightnessThresholdForGrayscale() {
        return sBrightnessThresholdForGrayscale;
    }

    public static void setBrightnessThresholdForGrayscale(float brightnessThreshold) {
        sBrightnessThresholdForGrayscale = brightnessThreshold;
    }

    public static float[] getHsvDistanceWeight() {
        return (float[]) sClusterHsvDistanceWeight.clone();
    }

    public static void setHsvDistanceWeight(float[] hsvWeight) {
        setHsvDistanceWeight(hsvWeight[0], hsvWeight[1], hsvWeight[2]);
    }

    public static void setHsvDistanceWeight(float h, float s, float v) {
        sClusterHsvDistanceWeight[0] = h;
        sClusterHsvDistanceWeight[1] = s;
        sClusterHsvDistanceWeight[2] = v;
    }

    public static void setGrayscaleDistanceWeight(float[] grayscaleWeight) {
        setGrayscaleDistanceWeight(grayscaleWeight[0], grayscaleWeight[1], grayscaleWeight[2]);
    }

    public static void setGrayscaleDistanceWeight(float h, float s, float v) {
        sClusterGrayscaleDistanceWeight[0] = h;
        sClusterGrayscaleDistanceWeight[1] = s;
        sClusterGrayscaleDistanceWeight[2] = v;
    }

    public static int[] makeClusterrGroup_preset1(int clusterNum) {
        if (clusterNum < 3) {
            clusterNum = 3;
        }
        int[] clusterGroups = new int[clusterNum];
        clusterGroups[0] = -1;
        clusterGroups[1] = -16777216;
        clusterGroups[2] = -7829368;
        for (int i = 3; i < clusterNum; i++) {
            clusterGroups[i] =
                    Color.HSVToColor(
                            new float[] {(i - 3) * (360.0f / (clusterNum - 3)), 0.5f, 0.5f});
        }
        return clusterGroups;
    }

    public static int[] makeClusterGroupColorBandBased() {
        int[] clusterGroups = {
            -1,
            -16777216,
            Color.GRAY,
            Color.HSVToColor(new float[] {0.0f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {34.5f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {69.5f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {124.5f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {169.5f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {214.5f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {264.5f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {289.5f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {319.5f, 0.5f, 0.5f})
        };
        return clusterGroups;
    }

    public static int[] makeClusterGroupColorBandBased2() {
        int[] clusterGroups = {
            -1,
            -16777216,
            Color.GRAY,
            Color.HSVToColor(new float[] {0.0f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {57.0f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {60.0f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {117.0f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {182.0f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {239.0f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {299.0f, 0.5f, 0.5f})
        };
        return clusterGroups;
    }

    public static int[] makeClusterGroupColorBandBased3() {
        int[] clusterGroups = {
            -1,
            -16777216,
            Color.GRAY,
            Color.HSVToColor(new float[] {0.0f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {36.0f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {72.0f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {126.0f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {180.0f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {252.0f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {288.0f, 0.5f, 0.5f}),
            Color.HSVToColor(new float[] {324.0f, 0.5f, 0.5f})
        };
        return clusterGroups;
    }

    public static int[] makeClusterGroupColorBandBased4() {
        int[] clusterGroups = new int[19];
        int i = 0 + 1;
        clusterGroups[0] = -1;
        int i2 = i + 1;
        clusterGroups[i] = -16777216;
        int i3 = i2 + 1;
        clusterGroups[i2] = -7829368;
        int i4 = i3 + 1;
        clusterGroups[i3] = Color.HSVToColor(new float[] {0.0f, 1.0f, 1.0f});
        int i5 = i4 + 1;
        clusterGroups[i4] = Color.HSVToColor(new float[] {36.0f, 1.0f, 1.0f});
        int i6 = i5 + 1;
        clusterGroups[i5] = Color.HSVToColor(new float[] {72.0f, 1.0f, 1.0f});
        int i7 = i6 + 1;
        clusterGroups[i6] = Color.HSVToColor(new float[] {126.0f, 1.0f, 1.0f});
        int i8 = i7 + 1;
        clusterGroups[i7] = Color.HSVToColor(new float[] {180.0f, 1.0f, 1.0f});
        int i9 = i8 + 1;
        clusterGroups[i8] = Color.HSVToColor(new float[] {252.0f, 1.0f, 1.0f});
        int i10 = i9 + 1;
        clusterGroups[i9] = Color.HSVToColor(new float[] {288.0f, 1.0f, 1.0f});
        int i11 = i10 + 1;
        clusterGroups[i10] = Color.HSVToColor(new float[] {324.0f, 1.0f, 1.0f});
        float saturationThresholdForGrayscale = sSaturationThresholdForGrayscale;
        float brightnessThresholdForGrayscale = sBrightnessThresholdForGrayscale;
        int i12 = i11 + 1;
        clusterGroups[i11] =
                Color.HSVToColor(
                        new float[] {
                            0.0f, saturationThresholdForGrayscale, brightnessThresholdForGrayscale
                        });
        int i13 = i12 + 1;
        clusterGroups[i12] =
                Color.HSVToColor(
                        new float[] {
                            36.0f, saturationThresholdForGrayscale, brightnessThresholdForGrayscale
                        });
        int i14 = i13 + 1;
        clusterGroups[i13] =
                Color.HSVToColor(
                        new float[] {
                            72.0f, saturationThresholdForGrayscale, brightnessThresholdForGrayscale
                        });
        int i15 = i14 + 1;
        clusterGroups[i14] =
                Color.HSVToColor(
                        new float[] {
                            126.0f, saturationThresholdForGrayscale, brightnessThresholdForGrayscale
                        });
        int i16 = i15 + 1;
        clusterGroups[i15] =
                Color.HSVToColor(
                        new float[] {
                            180.0f, saturationThresholdForGrayscale, brightnessThresholdForGrayscale
                        });
        int i17 = i16 + 1;
        clusterGroups[i16] =
                Color.HSVToColor(
                        new float[] {
                            252.0f, saturationThresholdForGrayscale, brightnessThresholdForGrayscale
                        });
        int i18 = i17 + 1;
        clusterGroups[i17] =
                Color.HSVToColor(
                        new float[] {
                            288.0f, saturationThresholdForGrayscale, brightnessThresholdForGrayscale
                        });
        int i19 = i18 + 1;
        clusterGroups[i18] =
                Color.HSVToColor(
                        new float[] {
                            324.0f, saturationThresholdForGrayscale, brightnessThresholdForGrayscale
                        });
        return clusterGroups;
    }

    public static DominantColorResult[] kMeansHsv(Bitmap bitmap, int[] clusterGroups) {
        if (bitmap == null) {
            return null;
        }
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        Log.i(
                TAG,
                "kMeansHsv input bitmap size = "
                        + bitmapWidth
                        + " x "
                        + bitmapHeight
                        + " | ClusterGroups Num = "
                        + clusterGroups.length);
        int[] pixels = new int[bitmapWidth * bitmapHeight];
        bitmap.getPixels(pixels, 0, bitmapWidth, 0, 0, bitmapWidth, bitmapHeight);
        return kMeansHsv(pixels, clusterGroups);
    }

    public static DominantColorResult[] kMeansHsv(int[] pixels, int[] clusterGroups) {
        DominantColorResult[] dominantColorResults;
        int numberOfVisiblePixels;
        float invPixelLength;
        int numberOfVisiblePixels2;
        int avgG;
        int avgB;
        int avgR;
        float[][] clusterGroups_hsv_copied;
        float[] pixels_hsv;
        float[] clusterCalcWeightColor;
        float[] clusterCalcWeightGrayScale;
        int[] iArr = pixels;
        int[] iArr2 = clusterGroups;
        int clusterSize = iArr2.length;
        DominantColorResult[] dominantColorResults2 = new DominantColorResult[clusterSize];
        int[] clusterGroups_copied = new int[clusterSize];
        boolean[] grayScaleFlags = new boolean[clusterSize];
        float[][] clusterGroups_hsv_copied2 = new float[clusterSize][];
        boolean[] clusterGroupGrayColors = new boolean[clusterSize];
        int[] aN = new int[clusterSize];
        long[][] aColorAvg = new long[clusterSize][];
        float[] clusterCalcWeightColor2 = sClusterHsvDistanceWeight;
        float[] clusterCalcWeightGrayScale2 = sClusterGrayscaleDistanceWeight;
        float saturationThresholdForGrayscale = sSaturationThresholdForGrayscale;
        float brightnessThresholdForGrayscale = sBrightnessThresholdForGrayscale;
        float[] pixels_hsv2 = new float[3];
        int numberOfVisiblePixels3 = 0;
        int i = 0;
        while (true) {
            dominantColorResults = dominantColorResults2;
            if (i >= clusterSize) {
                break;
            }
            clusterGroups_copied[i] = iArr2[i];
            float[] clusterGroupsHsv = new float[3];
            Color.colorToHSV(clusterGroups_copied[i], clusterGroupsHsv);
            clusterGroupGrayColors[i] =
                    checkGayScaleWithSV(
                            clusterGroupsHsv,
                            saturationThresholdForGrayscale,
                            brightnessThresholdForGrayscale);
            clusterGroups_hsv_copied2[i] = clusterGroupsHsv;
            aN[i] = 0;
            aColorAvg[i] = new long[] {0, 0, 0};
            i++;
            iArr2 = clusterGroups;
            dominantColorResults2 = dominantColorResults;
        }
        int k = 0;
        while (true) {
            if (k >= 1) {
                break;
            }
            long prevMemoryUsage =
                    Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            boolean clusterColorGrayFlag = false;
            Arrays.fill(aN, 0);
            int i2 = 0;
            while (i2 < clusterSize) {
                Arrays.fill(aColorAvg[i2], 0L);
                i2++;
                clusterColorGrayFlag = clusterColorGrayFlag;
                clusterGroups_copied = clusterGroups_copied;
                grayScaleFlags = grayScaleFlags;
            }
            int[] clusterGroups_copied2 = clusterGroups_copied;
            boolean[] grayScaleFlags2 = grayScaleFlags;
            int i3 = iArr.length;
            int numberOfVisiblePixels4 = numberOfVisiblePixels3;
            int i4 = 0;
            while (i4 < i3) {
                int i5 = i3;
                int pixel = iArr[i4];
                int alpha = pixel >>> 24;
                if (alpha <= 0) {
                    pixels_hsv = pixels_hsv2;
                    clusterGroups_hsv_copied = clusterGroups_hsv_copied2;
                    clusterCalcWeightColor = clusterCalcWeightColor2;
                    clusterCalcWeightGrayScale = clusterCalcWeightGrayScale2;
                } else {
                    int numberOfVisiblePixels5 = numberOfVisiblePixels4 + 1;
                    float minDist = Float.MAX_VALUE;
                    int minDistID = 0;
                    IUXColorUtils.colorToHSV(pixel, pixels_hsv2);
                    boolean isPixelColorGray =
                            checkGayScaleWithSV(
                                    pixels_hsv2,
                                    saturationThresholdForGrayscale,
                                    brightnessThresholdForGrayscale);
                    int clusterIdx = 0;
                    while (clusterIdx < clusterSize) {
                        int numberOfVisiblePixels6 = numberOfVisiblePixels5;
                        float[] clusterGroupsHsv2 = clusterGroups_hsv_copied2[clusterIdx];
                        boolean isClusterColorGray = clusterGroupGrayColors[clusterIdx];
                        if (!isPixelColorGray && !isClusterColorGray) {
                            float dist =
                                    colorDistance_hsv_square2(
                                            pixels_hsv2,
                                            clusterGroupsHsv2,
                                            clusterCalcWeightColor2);
                            if (dist < minDist) {
                                minDist = dist;
                                minDistID = clusterIdx;
                                clusterColorGrayFlag = false;
                            }
                        } else if (isPixelColorGray && isClusterColorGray) {
                            float dist2 =
                                    colorDistance_hsv_square2(
                                            pixels_hsv2,
                                            clusterGroupsHsv2,
                                            clusterCalcWeightGrayScale2);
                            if (dist2 < minDist) {
                                minDist = dist2;
                                minDistID = clusterIdx;
                                clusterColorGrayFlag = true;
                            }
                        }
                        clusterIdx++;
                        numberOfVisiblePixels5 = numberOfVisiblePixels6;
                    }
                    long[] colorAvg = aColorAvg[minDistID];
                    aN[minDistID] = aN[minDistID] + 1;
                    clusterGroups_hsv_copied = clusterGroups_hsv_copied2;
                    colorAvg[0] = colorAvg[0] + (pixel & Spanned.SPAN_PRIORITY);
                    pixels_hsv = pixels_hsv2;
                    clusterCalcWeightColor = clusterCalcWeightColor2;
                    clusterCalcWeightGrayScale = clusterCalcWeightGrayScale2;
                    colorAvg[1] = colorAvg[1] + (pixel & 65280);
                    colorAvg[2] = colorAvg[2] + (pixel & 255);
                    numberOfVisiblePixels4 = numberOfVisiblePixels5;
                }
                i4++;
                iArr = pixels;
                i3 = i5;
                pixels_hsv2 = pixels_hsv;
                clusterCalcWeightColor2 = clusterCalcWeightColor;
                clusterGroups_hsv_copied2 = clusterGroups_hsv_copied;
                clusterCalcWeightGrayScale2 = clusterCalcWeightGrayScale;
            }
            float[] pixels_hsv3 = pixels_hsv2;
            float[][] clusterGroups_hsv_copied3 = clusterGroups_hsv_copied2;
            float[] clusterCalcWeightColor3 = clusterCalcWeightColor2;
            float[] clusterCalcWeightGrayScale3 = clusterCalcWeightGrayScale2;
            grayScaleFlags2[k] = clusterColorGrayFlag;
            int i6 = 0;
            while (i6 < clusterSize) {
                long[] colorAvg2 = aColorAvg[i6];
                int number = aN[i6];
                if (number == 0) {
                    avgR = 0;
                    avgG = 0;
                    avgB = 0;
                    numberOfVisiblePixels2 = numberOfVisiblePixels4;
                } else {
                    int avgR2 = ((int) (colorAvg2[0] / number)) & Spanned.SPAN_PRIORITY;
                    numberOfVisiblePixels2 = numberOfVisiblePixels4;
                    avgG = ((int) (colorAvg2[1] / number)) & 65280;
                    avgB = ((int) (colorAvg2[2] / number)) & 255;
                    avgR = avgR2;
                }
                clusterGroups_copied2[i6] = (-16777216) | avgR | avgG | avgB;
                i6++;
                numberOfVisiblePixels4 = numberOfVisiblePixels2;
            }
            int numberOfVisiblePixels7 = numberOfVisiblePixels4;
            if (k != 0) {
                k++;
                iArr = pixels;
                numberOfVisiblePixels3 = numberOfVisiblePixels7;
                clusterGroups_copied = clusterGroups_copied2;
                grayScaleFlags = grayScaleFlags2;
                pixels_hsv2 = pixels_hsv3;
                clusterCalcWeightColor2 = clusterCalcWeightColor3;
                clusterGroups_hsv_copied2 = clusterGroups_hsv_copied3;
                clusterCalcWeightGrayScale2 = clusterCalcWeightGrayScale3;
            } else {
                if (numberOfVisiblePixels7 > 0) {
                    numberOfVisiblePixels = numberOfVisiblePixels7;
                    invPixelLength = 1.0f / numberOfVisiblePixels;
                } else {
                    numberOfVisiblePixels = numberOfVisiblePixels7;
                    invPixelLength = 0.0f;
                }
                for (int i7 = 0; i7 < clusterSize; i7++) {
                    dominantColorResults[i7] =
                            new DominantColorResult(
                                    clusterGroups_copied2[i7],
                                    aN[i7] * invPixelLength,
                                    grayScaleFlags2[i7]);
                }
                long currentMemoryUsage =
                        Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                Log.i(TAG, "ColorExtractor Memory Usage " + (currentMemoryUsage - prevMemoryUsage));
            }
        }
        Arrays.sort(
                dominantColorResults,
                new Comparator() { // from class:
                                   // com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor$$ExternalSyntheticLambda1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        int compare;
                        compare =
                                Integer.compare(
                                        (int)
                                                (((ColorExtractor.DominantColorResult) obj2)
                                                                .percentage
                                                        * 1000000.0f),
                                        (int)
                                                (((ColorExtractor.DominantColorResult) obj)
                                                                .percentage
                                                        * 1000000.0f));
                        return compare;
                    }
                });
        return dominantColorResults;
    }

    public static DominantColorResult[] sampleColorsWithBias(
            Bitmap bitmap, DominantColorResult[] dominantColoResults, float[] biasHSV) {
        if (bitmap == null) {
            return null;
        }
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        Log.i(
                TAG,
                "sampleColorsWithBias input bitmap size = "
                        + bitmapWidth
                        + " x "
                        + bitmapHeight
                        + " | ClusterGroups Num = "
                        + dominantColoResults.length);
        int[] pixels = new int[bitmapWidth * bitmapHeight];
        bitmap.getPixels(pixels, 0, bitmapWidth, 0, 0, bitmapWidth, bitmapHeight);
        return sampleColorsWithBias(pixels, dominantColoResults, biasHSV);
    }

    public static DominantColorResult[] sampleColorsWithBias(
            int[] pixels, DominantColorResult[] dominantColoResults, float[] weightColorHSV) {
        int clusterNum = dominantColoResults.length;
        int pixelLength = pixels.length;
        int[] sampleColors = new int[clusterNum];
        float[] resultClusterColorHSV = new float[3];
        float saturationThresholdForGrayscale = sSaturationThresholdForGrayscale;
        float brightnessThresholdForGrayscale = sBrightnessThresholdForGrayscale;
        DominantColorResult[] dominantColorResults = new DominantColorResult[clusterNum];
        for (int j = 0; j < clusterNum; j++) {
            float minPixelDistance = Float.MAX_VALUE;
            int minPixelDistanceID = 0;
            float[] currentDominantColorHSV = dominantColoResults[j].hsv;
            if (!dominantColoResults[j].isGrayScale
                    && !checkGayScaleWithSV(
                            currentDominantColorHSV,
                            saturationThresholdForGrayscale,
                            brightnessThresholdForGrayscale)) {
                resultClusterColorHSV[0] =
                        IUXMathUtils.rangeRevolving(
                                0.0f, 360.0f, currentDominantColorHSV[0] + weightColorHSV[0]);
                resultClusterColorHSV[1] =
                        IUXMathUtils.range(
                                0.0f, 1.0f, currentDominantColorHSV[1] + weightColorHSV[1]);
            } else {
                resultClusterColorHSV[0] = currentDominantColorHSV[0];
                resultClusterColorHSV[1] = currentDominantColorHSV[1];
            }
            resultClusterColorHSV[2] =
                    IUXMathUtils.range(0.0f, 1.0f, currentDominantColorHSV[2] + weightColorHSV[2]);
            int resultClusterColor = IUXColorUtils.HSVToColor(resultClusterColorHSV);
            for (int i = 0; i < pixelLength; i++) {
                int alpha = Color.alpha(pixels[i]);
                if (alpha > 0) {
                    float dist = colorDistance_rgb_sqaure2(pixels[i], resultClusterColor);
                    if (minPixelDistance > dist) {
                        minPixelDistance = dist;
                        minPixelDistanceID = i;
                    }
                }
            }
            int i2 = pixels[minPixelDistanceID];
            sampleColors[j] = i2;
        }
        for (int i3 = 0; i3 < clusterNum; i3++) {
            dominantColorResults[i3] =
                    new DominantColorResult(
                            sampleColors[i3],
                            dominantColoResults[i3].percentage,
                            dominantColoResults[i3].isGrayScale);
        }
        return dominantColorResults;
    }

    protected static float colorDistance_hsv_square2(
            float[] hsv_a, float[] hsv_b, float[] hsv_weight) {
        float diff_h = Math.abs(hsv_b[0] - hsv_a[0]);
        if (diff_h >= 180.0f) {
            diff_h = 360.0f - diff_h;
        }
        float diff_h2 = (diff_h / 180.0f) * hsv_weight[0];
        float diff_s = (hsv_b[1] - hsv_a[1]) * hsv_weight[1];
        float diff_b = (hsv_b[2] - hsv_a[2]) * hsv_weight[2];
        return (diff_h2 * diff_h2) + (diff_s * diff_s) + (diff_b * diff_b);
    }

    protected static float colorDistance_rgb_sqaure2(int c1, int c2) {
        return (((float) Math.pow(Color.red(c1) - Color.red(c2), 2.0d)) * 0.9f)
                + (((float) Math.pow(Color.green(c1) - Color.green(c2), 2.0d)) * 1.2f)
                + (((float) Math.pow(Color.blue(c1) - Color.blue(c2), 2.0d)) * 0.9f);
    }

    protected static boolean checkGayScaleWithSV(
            float[] hsv, float offsetValue_s, float offsetValue_b) {
        return hsv[1] <= offsetValue_s || hsv[2] <= offsetValue_b;
    }

    public static int getAverageColorFromDominantColors(DominantColorResult[] dominantColorResult) {
        float avgR = 0.0f;
        float avgG = 0.0f;
        float avgB = 0.0f;
        for (DominantColorResult colorResult : dominantColorResult) {
            int color = colorResult.color;
            float percentage = colorResult.percentage;
            if (percentage <= 0.0f) {
                break;
            }
            avgR += Color.red(color) * percentage;
            avgG += Color.green(color) * percentage;
            avgB += Color.blue(color) * percentage;
        }
        return Color.rgb((int) avgR, (int) avgG, (int) avgB);
    }

    public static void discardSameHSVfromDominantColors(
            DominantColorResult[] dominantColorArray, float hsvDistance) {
        discardSameHSVfromDominantColors(dominantColorArray, hsvDistance, true);
    }

    public static void discardSameHSVfromDominantColors(
            DominantColorResult[] dominantColorArray,
            float distanceThresholdRatio,
            boolean mixSameColors) {
        float[] clusterCalcWeight;
        float squaredThresholdDistance;
        float[] clusterCalcWeightColor;
        float squaredThresholdDistanceColor;
        float[] clusterCalcWeightGrayscale;
        float squaredThresholdDistanceMono;
        float saturationThresholdForGrayscale;
        float[] clusterCalcWeightColor2 = sClusterHsvDistanceWeight;
        float squaredThresholdDistanceColor2 = 1.7320508f * distanceThresholdRatio;
        float percentageSum = squaredThresholdDistanceColor2 * squaredThresholdDistanceColor2;
        float[] clusterCalcWeightGrayscale2 = sClusterGrayscaleDistanceWeight;
        float squaredThresholdDistanceMono2 = 2.0f * distanceThresholdRatio;
        float squaredThresholdDistanceMono3 =
                squaredThresholdDistanceMono2 * squaredThresholdDistanceMono2;
        float saturationThresholdForGrayscale2 = sSaturationThresholdForGrayscale;
        float brightnessThresholdForGrayscale = sBrightnessThresholdForGrayscale;
        float[] hsvA = new float[3];
        float[] hsvB = new float[3];
        int dominantColorLength = dominantColorArray.length;
        int a = 0;
        while (a < dominantColorLength) {
            DominantColorResult dominantColorResult_A = dominantColorArray[a];
            if (dominantColorResult_A.percentage == 0.0f) {
                break;
            }
            dominantColorResult_A.copyHSV(hsvA);
            boolean isGrayScaleA =
                    checkGayScaleWithSV(
                            hsvA,
                            saturationThresholdForGrayscale2,
                            brightnessThresholdForGrayscale);
            if (isGrayScaleA) {
                clusterCalcWeight = clusterCalcWeightGrayscale2;
                squaredThresholdDistance = squaredThresholdDistanceMono3;
            } else {
                clusterCalcWeight = clusterCalcWeightColor2;
                squaredThresholdDistance = percentageSum;
            }
            hsvA[0] = IUXColorUtils.getHumanEyeBasedHueNormalizedDistance(hsvA[0]) * 360.0f;
            int b = a + 1;
            while (true) {
                if (b >= dominantColorLength) {
                    clusterCalcWeightColor = clusterCalcWeightColor2;
                    squaredThresholdDistanceColor = percentageSum;
                    break;
                }
                clusterCalcWeightColor = clusterCalcWeightColor2;
                DominantColorResult dominantColorResult_B = dominantColorArray[b];
                squaredThresholdDistanceColor = percentageSum;
                if (dominantColorResult_B.percentage == 0.0f) {
                    break;
                }
                dominantColorResult_B.copyHSV(hsvB);
                boolean isGrayScaleB =
                        checkGayScaleWithSV(
                                hsvB,
                                saturationThresholdForGrayscale2,
                                brightnessThresholdForGrayscale);
                hsvB[0] = IUXColorUtils.getHumanEyeBasedHueNormalizedDistance(hsvB[0]) * 360.0f;
                if (isGrayScaleA == isGrayScaleB) {
                    clusterCalcWeightGrayscale = clusterCalcWeightGrayscale2;
                    if (IUXColorUtils.colorDistance_hsv_square2(hsvA, hsvB, clusterCalcWeight)
                            < squaredThresholdDistance) {
                        float percentageSum2 =
                                dominantColorResult_A.percentage + dominantColorResult_B.percentage;
                        if (!mixSameColors) {
                            squaredThresholdDistanceMono = squaredThresholdDistanceMono3;
                            saturationThresholdForGrayscale = saturationThresholdForGrayscale2;
                        } else {
                            float[] fArr = dominantColorResult_A.hsv;
                            squaredThresholdDistanceMono = squaredThresholdDistanceMono3;
                            float[] fArr2 = dominantColorResult_B.hsv;
                            saturationThresholdForGrayscale = saturationThresholdForGrayscale2;
                            float saturationThresholdForGrayscale3 =
                                    dominantColorResult_B.percentage;
                            dominantColorResult_A.setColor(
                                    IUXColorUtils.getInterpolatedColorHSVBased(
                                            fArr,
                                            fArr2,
                                            saturationThresholdForGrayscale3 / percentageSum2));
                        }
                        dominantColorResult_A.percentage = percentageSum2;
                        dominantColorResult_B.setColor(0);
                        dominantColorResult_B.percentage = 0.0f;
                        ArrayUtils.arrayChangePos(dominantColorArray, b, dominantColorLength - 1);
                        clusterCalcWeightColor2 = clusterCalcWeightColor;
                        percentageSum = squaredThresholdDistanceColor;
                        squaredThresholdDistanceMono3 = squaredThresholdDistanceMono;
                        saturationThresholdForGrayscale2 = saturationThresholdForGrayscale;
                        clusterCalcWeightGrayscale2 = clusterCalcWeightGrayscale;
                    }
                } else {
                    clusterCalcWeightGrayscale = clusterCalcWeightGrayscale2;
                }
                b++;
                clusterCalcWeightColor2 = clusterCalcWeightColor;
                percentageSum = squaredThresholdDistanceColor;
                squaredThresholdDistanceMono3 = squaredThresholdDistanceMono3;
                saturationThresholdForGrayscale2 = saturationThresholdForGrayscale2;
                clusterCalcWeightGrayscale2 = clusterCalcWeightGrayscale;
            }
            a++;
            clusterCalcWeightColor2 = clusterCalcWeightColor;
            percentageSum = squaredThresholdDistanceColor;
            squaredThresholdDistanceMono3 = squaredThresholdDistanceMono3;
            saturationThresholdForGrayscale2 = saturationThresholdForGrayscale2;
            clusterCalcWeightGrayscale2 = clusterCalcWeightGrayscale2;
        }
        Arrays.sort(
                dominantColorArray,
                new Comparator() { // from class:
                                   // com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor$$ExternalSyntheticLambda2
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        int compare;
                        compare =
                                Integer.compare(
                                        (int)
                                                (((ColorExtractor.DominantColorResult) obj2)
                                                                .percentage
                                                        * 1000000.0f),
                                        (int)
                                                (((ColorExtractor.DominantColorResult) obj)
                                                                .percentage
                                                        * 1000000.0f));
                        return compare;
                    }
                });
    }

    static void mergeDominantColorUnit(
            DominantColorResult dominantColorResultA,
            DominantColorResult dominantColorResultB,
            ColorPaletteExtractor.ColorMergeType colorMergeType) {
        float percentageSum = dominantColorResultA.percentage + dominantColorResultB.percentage;
        if (colorMergeType != ColorPaletteExtractor.ColorMergeType.A) {
            if (colorMergeType == ColorPaletteExtractor.ColorMergeType.B) {
                dominantColorResultA.setColor(dominantColorResultB.color);
                dominantColorResultA.isGrayScale = dominantColorResultB.isGrayScale;
            } else {
                int colorSum =
                        IUXColorUtils.getInterpolatedColorHSVBased(
                                dominantColorResultA.hsv,
                                dominantColorResultB.hsv,
                                dominantColorResultB.percentage / percentageSum);
                dominantColorResultA.setColor(colorSum);
                dominantColorResultA.isGrayScale =
                        checkGayScaleWithSV(
                                IUXColorUtils.getHSVFromColor(colorSum),
                                sSaturationThresholdForGrayscale,
                                sBrightnessThresholdForGrayscale);
            }
        }
        dominantColorResultB.setColor(0);
        dominantColorResultA.percentage = percentageSum;
        dominantColorResultB.percentage = 0.0f;
    }

    static int getAvgColorFromTwoColors(int colorA, float weightA, int colorB, float weightB) {
        return Color.argb(
                (int) ((Color.alpha(colorA) * weightA) + (Color.alpha(colorB) * weightB)),
                (int) ((Color.red(colorA) * weightA) + (Color.red(colorB) * weightB)),
                (int) ((Color.green(colorA) * weightA) + (Color.green(colorB) * weightB)),
                (int) ((Color.blue(colorA) * weightA) + (Color.blue(colorB) * weightB)));
    }

    static void sortColorResult(DominantColorResult[] dominantColorArray) {
        Arrays.sort(
                dominantColorArray,
                new Comparator() { // from class:
                                   // com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor$$ExternalSyntheticLambda0
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        int compare;
                        compare =
                                Float.compare(
                                        ((ColorExtractor.DominantColorResult) obj2).percentage,
                                        ((ColorExtractor.DominantColorResult) obj).percentage);
                        return compare;
                    }
                });
    }

    public static class DominantColorResult implements Cloneable {
        private static final String TAG = "DominantColorResult";
        public int color;
        public float[] hsv;
        public boolean isGrayScale;
        public float percentage;

        public DominantColorResult(int color, float percentage) {
            this.hsv = new float[3];
            this.color = color;
            IUXColorUtils.colorToHSV(color, this.hsv);
            this.isGrayScale = false;
            this.percentage = percentage;
        }

        public DominantColorResult(int color, float percentage, boolean isGrayScale) {
            this.hsv = new float[3];
            this.color = color;
            IUXColorUtils.colorToHSV(color, this.hsv);
            this.isGrayScale = isGrayScale;
            this.percentage = percentage;
        }

        public void setColor(int color) {
            this.color = color;
            IUXColorUtils.colorToHSV(color, this.hsv);
        }

        public void copyHSV(float[] dst) {
            dst[0] = this.hsv[0];
            dst[1] = this.hsv[1];
            dst[2] = this.hsv[2];
        }

        public int hashCode() {
            return Objects.hash(
                    Integer.valueOf(this.color),
                    Float.valueOf(this.percentage),
                    Boolean.valueOf(this.isGrayScale));
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof DominantColorResult)) {
                return false;
            }
            DominantColorResult that = (DominantColorResult) o;
            return this.color == that.color
                    && Float.compare(that.percentage, this.percentage) == 0
                    && this.isGrayScale == that.isGrayScale;
        }

        public String toString() {
            return "\nDominantColorResult{\ncolor="
                    + Integer.toHexString(this.color)
                    + "\npercentage="
                    + this.percentage
                    + "\n\nisGrayScale="
                    + this.isGrayScale
                    + '}';
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public DominantColorResult m9227clone() {
            try {
                DominantColorResult clone = (DominantColorResult) super.clone();
                if (this.hsv != null) {
                    clone.hsv = new float[this.hsv.length];
                    for (int i = 0; i < this.hsv.length; i++) {
                        clone.hsv[i] = this.hsv[i];
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
