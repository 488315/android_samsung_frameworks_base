package com.samsung.android.wallpaper.legibilitycolors.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Spanned;
import android.util.Log;
import com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor;
import com.samsung.android.wallpaper.legibilitycolors.utils.ColorPaletteExtractor;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/* loaded from: classes6.dex */
public class ColorExtractor {
    static final String TAG = "ColorExtractor";
    public static final String VERSION = "1.0.0";
    static float sBrightnessThresholdForGrayscale = 0.25f;
    static float sSaturationThresholdForGrayscale = 0.12f;
    static float[] sClusterHsvDistanceWeight = {1.0f, 0.1f, 0.1f};
    static float[] sClusterGrayscaleDistanceWeight = {0.0f, 0.0f, 1.0f};

    public static float getSaturationThresholdForGrayscale() {
        return sSaturationThresholdForGrayscale;
    }

    public static void setSaturationThresholdForGrayscale(float f) {
        sSaturationThresholdForGrayscale = f;
    }

    public static float getBrightnessThresholdForGrayscale() {
        return sBrightnessThresholdForGrayscale;
    }

    public static void setBrightnessThresholdForGrayscale(float f) {
        sBrightnessThresholdForGrayscale = f;
    }

    public static float[] getHsvDistanceWeight() {
        return (float[]) sClusterHsvDistanceWeight.clone();
    }

    public static void setHsvDistanceWeight(float[] fArr) {
        setHsvDistanceWeight(fArr[0], fArr[1], fArr[2]);
    }

    public static void setHsvDistanceWeight(float f, float f2, float f3) {
        float[] fArr = sClusterHsvDistanceWeight;
        fArr[0] = f;
        fArr[1] = f2;
        fArr[2] = f3;
    }

    public static void setGrayscaleDistanceWeight(float[] fArr) {
        setGrayscaleDistanceWeight(fArr[0], fArr[1], fArr[2]);
    }

    public static void setGrayscaleDistanceWeight(float f, float f2, float f3) {
        float[] fArr = sClusterGrayscaleDistanceWeight;
        fArr[0] = f;
        fArr[1] = f2;
        fArr[2] = f3;
    }

    public static int[] makeClusterrGroup_preset1(int i) {
        if (i < 3) {
            i = 3;
        }
        int[] iArr = new int[i];
        iArr[0] = -1;
        iArr[1] = -16777216;
        iArr[2] = -7829368;
        for (int i2 = 3; i2 < i; i2++) {
            iArr[i2] = Color.HSVToColor(new float[]{(i2 - 3) * (360.0f / (i - 3)), 0.5f, 0.5f});
        }
        return iArr;
    }

    public static int[] makeClusterGroupColorBandBased() {
        return new int[]{-1, -16777216, Color.GRAY, Color.HSVToColor(new float[]{0.0f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{34.5f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{69.5f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{124.5f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{169.5f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{214.5f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{264.5f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{289.5f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{319.5f, 0.5f, 0.5f})};
    }

    public static int[] makeClusterGroupColorBandBased2() {
        return new int[]{-1, -16777216, Color.GRAY, Color.HSVToColor(new float[]{0.0f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{57.0f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{60.0f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{117.0f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{182.0f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{239.0f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{299.0f, 0.5f, 0.5f})};
    }

    public static int[] makeClusterGroupColorBandBased3() {
        return new int[]{-1, -16777216, Color.GRAY, Color.HSVToColor(new float[]{0.0f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{36.0f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{72.0f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{126.0f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{180.0f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{252.0f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{288.0f, 0.5f, 0.5f}), Color.HSVToColor(new float[]{324.0f, 0.5f, 0.5f})};
    }

    public static int[] makeClusterGroupColorBandBased4() {
        float f = sSaturationThresholdForGrayscale;
        float f2 = sBrightnessThresholdForGrayscale;
        return new int[]{-1, -16777216, Color.GRAY, Color.HSVToColor(new float[]{0.0f, 1.0f, 1.0f}), Color.HSVToColor(new float[]{36.0f, 1.0f, 1.0f}), Color.HSVToColor(new float[]{72.0f, 1.0f, 1.0f}), Color.HSVToColor(new float[]{126.0f, 1.0f, 1.0f}), Color.HSVToColor(new float[]{180.0f, 1.0f, 1.0f}), Color.HSVToColor(new float[]{252.0f, 1.0f, 1.0f}), Color.HSVToColor(new float[]{288.0f, 1.0f, 1.0f}), Color.HSVToColor(new float[]{324.0f, 1.0f, 1.0f}), Color.HSVToColor(new float[]{0.0f, f, f2}), Color.HSVToColor(new float[]{36.0f, f, f2}), Color.HSVToColor(new float[]{72.0f, f, f2}), Color.HSVToColor(new float[]{126.0f, f, f2}), Color.HSVToColor(new float[]{180.0f, f, f2}), Color.HSVToColor(new float[]{252.0f, f, f2}), Color.HSVToColor(new float[]{288.0f, f, f2}), Color.HSVToColor(new float[]{324.0f, f, f2})};
    }

    public static DominantColorResult[] kMeansHsv(Bitmap bitmap, int[] iArr) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.i(TAG, "kMeansHsv input bitmap size = " + width + " x " + height + " | ClusterGroups Num = " + iArr.length);
        int[] iArr2 = new int[width * height];
        bitmap.getPixels(iArr2, 0, width, 0, 0, width, height);
        return kMeansHsv(iArr2, iArr);
    }

    public static DominantColorResult[] kMeansHsv(int[] iArr, int[] iArr2) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int length = iArr2.length;
        DominantColorResult[] dominantColorResultArr = new DominantColorResult[length];
        int[] iArr3 = new int[length];
        boolean[] zArr = new boolean[length];
        float[][] fArr = new float[length][];
        boolean[] zArr2 = new boolean[length];
        int[] iArr4 = new int[length];
        long[][] jArr = new long[length][];
        float[] fArr2 = sClusterHsvDistanceWeight;
        float[] fArr3 = sClusterGrayscaleDistanceWeight;
        float f = sSaturationThresholdForGrayscale;
        float f2 = sBrightnessThresholdForGrayscale;
        float[] fArr4 = new float[3];
        int i6 = 0;
        while (i6 < length) {
            int i7 = iArr2[i6];
            iArr3[i6] = i7;
            boolean[] zArr3 = zArr;
            float[] fArr5 = new float[3];
            Color.colorToHSV(i7, fArr5);
            zArr2[i6] = checkGayScaleWithSV(fArr5, f, f2);
            fArr[i6] = fArr5;
            iArr4[i6] = 0;
            jArr[i6] = new long[]{0, 0, 0};
            i6++;
            zArr = zArr3;
            iArr3 = iArr3;
        }
        int[] iArr5 = iArr3;
        boolean[] zArr4 = zArr;
        long freeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        Arrays.fill(iArr4, 0);
        int i8 = 0;
        while (i8 < length) {
            Arrays.fill(jArr[i8], 0L);
            i8++;
            freeMemory = freeMemory;
        }
        long j = freeMemory;
        int length2 = iArr.length;
        int i9 = 0;
        boolean z = false;
        int i10 = 0;
        while (i9 < length2) {
            int i11 = iArr[i9];
            if ((i11 >>> 24) <= 0) {
                i4 = length2;
                i5 = i9;
            } else {
                i10++;
                IUXColorUtils.colorToHSV(i11, fArr4);
                boolean checkGayScaleWithSV = checkGayScaleWithSV(fArr4, f, f2);
                float f3 = Float.MAX_VALUE;
                int i12 = 0;
                i4 = length2;
                int i13 = 0;
                while (i13 < length) {
                    int i14 = i13;
                    float[] fArr6 = fArr[i14];
                    boolean z2 = zArr2[i14];
                    if (!checkGayScaleWithSV && !z2) {
                        float colorDistance_hsv_square2 = colorDistance_hsv_square2(fArr4, fArr6, fArr2);
                        if (colorDistance_hsv_square2 < f3) {
                            i12 = i14;
                            f3 = colorDistance_hsv_square2;
                            z = false;
                        }
                    } else if (checkGayScaleWithSV && z2) {
                        float colorDistance_hsv_square22 = colorDistance_hsv_square2(fArr4, fArr6, fArr3);
                        if (colorDistance_hsv_square22 < f3) {
                            i12 = i14;
                            f3 = colorDistance_hsv_square22;
                            z = true;
                        }
                    }
                    i13 = i14 + 1;
                }
                long[] jArr2 = jArr[i12];
                iArr4[i12] = iArr4[i12] + 1;
                i5 = i9;
                jArr2[0] = jArr2[0] + (i11 & Spanned.SPAN_PRIORITY);
                jArr2[1] = jArr2[1] + (i11 & 65280);
                jArr2[2] = jArr2[2] + (i11 & 255);
                z = z;
            }
            i9 = i5 + 1;
            length2 = i4;
        }
        zArr4[0] = z;
        for (int i15 = 0; i15 < length; i15++) {
            long[] jArr3 = jArr[i15];
            int i16 = iArr4[i15];
            if (i16 == 0) {
                i3 = 0;
                i = 0;
                i2 = 0;
            } else {
                long j2 = i16;
                i = ((int) (jArr3[0] / j2)) & Spanned.SPAN_PRIORITY;
                i2 = ((int) (jArr3[1] / j2)) & 65280;
                i3 = ((int) (jArr3[2] / j2)) & 255;
            }
            iArr5[i15] = i3 | i | (-16777216) | i2;
        }
        float f4 = i10 > 0 ? 1.0f / i10 : 0.0f;
        for (int i17 = 0; i17 < length; i17++) {
            dominantColorResultArr[i17] = new DominantColorResult(iArr5[i17], iArr4[i17] * f4, zArr4[i17]);
        }
        Log.i(TAG, "ColorExtractor Memory Usage " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) - j));
        Arrays.sort(dominantColorResultArr, new Comparator() { // from class: com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare;
                compare = Integer.compare((int) (((ColorExtractor.DominantColorResult) obj2).percentage * 1000000.0f), (int) (((ColorExtractor.DominantColorResult) obj).percentage * 1000000.0f));
                return compare;
            }
        });
        return dominantColorResultArr;
    }

    public static DominantColorResult[] sampleColorsWithBias(Bitmap bitmap, DominantColorResult[] dominantColorResultArr, float[] fArr) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.i(TAG, "sampleColorsWithBias input bitmap size = " + width + " x " + height + " | ClusterGroups Num = " + dominantColorResultArr.length);
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return sampleColorsWithBias(iArr, dominantColorResultArr, fArr);
    }

    public static DominantColorResult[] sampleColorsWithBias(int[] iArr, DominantColorResult[] dominantColorResultArr, float[] fArr) {
        int i;
        int length = dominantColorResultArr.length;
        int length2 = iArr.length;
        int[] iArr2 = new int[length];
        float[] fArr2 = new float[3];
        float f = sSaturationThresholdForGrayscale;
        float f2 = sBrightnessThresholdForGrayscale;
        DominantColorResult[] dominantColorResultArr2 = new DominantColorResult[length];
        int i2 = 0;
        int i3 = 0;
        while (i3 < length) {
            float[] fArr3 = dominantColorResultArr[i3].hsv;
            if (!dominantColorResultArr[i3].isGrayScale && !checkGayScaleWithSV(fArr3, f, f2)) {
                i = i2;
                fArr2[i] = IUXMathUtils.rangeRevolving(0.0f, 360.0f, fArr3[i2] + fArr[i2]);
                fArr2[1] = IUXMathUtils.range(0.0f, 1.0f, fArr3[1] + fArr[1]);
            } else {
                i = i2;
                fArr2[i] = fArr3[i];
                fArr2[1] = fArr3[1];
            }
            fArr2[2] = IUXMathUtils.range(0.0f, 1.0f, fArr3[2] + fArr[2]);
            int HSVToColor = IUXColorUtils.HSVToColor(fArr2);
            float f3 = Float.MAX_VALUE;
            int i4 = i;
            int i5 = i4;
            while (i4 < length2) {
                if (Color.alpha(iArr[i4]) > 0) {
                    float colorDistance_rgb_sqaure2 = colorDistance_rgb_sqaure2(iArr[i4], HSVToColor);
                    if (f3 > colorDistance_rgb_sqaure2) {
                        i5 = i4;
                        f3 = colorDistance_rgb_sqaure2;
                    }
                }
                i4++;
            }
            iArr2[i3] = iArr[i5];
            i3++;
            i2 = i;
        }
        while (i2 < length) {
            dominantColorResultArr2[i2] = new DominantColorResult(iArr2[i2], dominantColorResultArr[i2].percentage, dominantColorResultArr[i2].isGrayScale);
            i2++;
        }
        return dominantColorResultArr2;
    }

    protected static float colorDistance_hsv_square2(float[] fArr, float[] fArr2, float[] fArr3) {
        float abs = Math.abs(fArr2[0] - fArr[0]);
        if (abs >= 180.0f) {
            abs = 360.0f - abs;
        }
        float f = (abs / 180.0f) * fArr3[0];
        float f2 = (fArr2[1] - fArr[1]) * fArr3[1];
        float f3 = (fArr2[2] - fArr[2]) * fArr3[2];
        return (f * f) + (f2 * f2) + (f3 * f3);
    }

    protected static float colorDistance_rgb_sqaure2(int i, int i2) {
        return (((float) Math.pow(Color.red(i) - Color.red(i2), 2.0d)) * 0.9f) + (((float) Math.pow(Color.green(i) - Color.green(i2), 2.0d)) * 1.2f) + (((float) Math.pow(Color.blue(i) - Color.blue(i2), 2.0d)) * 0.9f);
    }

    protected static boolean checkGayScaleWithSV(float[] fArr, float f, float f2) {
        return fArr[1] <= f || fArr[2] <= f2;
    }

    public static int getAverageColorFromDominantColors(DominantColorResult[] dominantColorResultArr) {
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        for (DominantColorResult dominantColorResult : dominantColorResultArr) {
            int i = dominantColorResult.color;
            float f4 = dominantColorResult.percentage;
            if (f4 <= 0.0f) {
                break;
            }
            f += Color.red(i) * f4;
            f2 += Color.green(i) * f4;
            f3 += Color.blue(i) * f4;
        }
        return Color.rgb((int) f, (int) f2, (int) f3);
    }

    public static void discardSameHSVfromDominantColors(DominantColorResult[] dominantColorResultArr, float f) {
        discardSameHSVfromDominantColors(dominantColorResultArr, f, true);
    }

    public static void discardSameHSVfromDominantColors(DominantColorResult[] dominantColorResultArr, float f, boolean z) {
        float[] fArr;
        float f2;
        float f3;
        float[] fArr2;
        float f4;
        float[] fArr3;
        float f5;
        float f6;
        int i;
        float f7;
        float[] fArr4 = sClusterHsvDistanceWeight;
        float f8 = 1.7320508f * f;
        float f9 = f8 * f8;
        float[] fArr5 = sClusterGrayscaleDistanceWeight;
        float f10 = 2.0f * f;
        float f11 = f10 * f10;
        float f12 = sSaturationThresholdForGrayscale;
        float f13 = sBrightnessThresholdForGrayscale;
        float[] fArr6 = new float[3];
        float[] fArr7 = new float[3];
        int length = dominantColorResultArr.length;
        int i2 = 0;
        int i3 = 0;
        while (i3 < length) {
            DominantColorResult dominantColorResult = dominantColorResultArr[i3];
            if (dominantColorResult.percentage == 0.0f) {
                break;
            }
            dominantColorResult.copyHSV(fArr6);
            boolean checkGayScaleWithSV = checkGayScaleWithSV(fArr6, f12, f13);
            if (checkGayScaleWithSV) {
                fArr = fArr5;
                f2 = 0.0f;
                f3 = f11;
            } else {
                fArr = fArr4;
                f2 = 0.0f;
                f3 = f9;
            }
            fArr6[i2] = IUXColorUtils.getHumanEyeBasedHueNormalizedDistance(fArr6[i2]) * 360.0f;
            i3++;
            int i4 = i2;
            int i5 = i3;
            while (true) {
                fArr2 = fArr4;
                if (i5 >= length) {
                    f4 = f9;
                    break;
                }
                DominantColorResult dominantColorResult2 = dominantColorResultArr[i5];
                f4 = f9;
                if (dominantColorResult2.percentage == f2) {
                    break;
                }
                dominantColorResult2.copyHSV(fArr7);
                boolean checkGayScaleWithSV2 = checkGayScaleWithSV(fArr7, f12, f13);
                fArr7[i4] = IUXColorUtils.getHumanEyeBasedHueNormalizedDistance(fArr7[i4]) * 360.0f;
                if (checkGayScaleWithSV == checkGayScaleWithSV2) {
                    fArr3 = fArr5;
                    if (IUXColorUtils.colorDistance_hsv_square2(fArr6, fArr7, fArr) < f3) {
                        float f14 = dominantColorResult.percentage + dominantColorResult2.percentage;
                        if (z) {
                            f5 = f11;
                            f6 = f12;
                            dominantColorResult.setColor(IUXColorUtils.getInterpolatedColorHSVBased(dominantColorResult.hsv, dominantColorResult2.hsv, dominantColorResult2.percentage / f14));
                        } else {
                            f5 = f11;
                            f6 = f12;
                        }
                        dominantColorResult.percentage = f14;
                        dominantColorResult2.setColor(i4);
                        dominantColorResult2.percentage = f2;
                        ArrayUtils.arrayChangePos(dominantColorResultArr, i5, length - 1);
                        fArr4 = fArr2;
                        f9 = f4;
                        f11 = f5;
                        f12 = f6;
                        fArr5 = fArr3;
                    } else {
                        f7 = f2;
                        f5 = f11;
                        f6 = f12;
                        i = i4;
                    }
                } else {
                    fArr3 = fArr5;
                    f5 = f11;
                    f6 = f12;
                    i = i4;
                    f7 = f2;
                }
                i5++;
                i4 = i;
                f2 = f7;
                fArr4 = fArr2;
                f9 = f4;
                f11 = f5;
                f12 = f6;
                fArr5 = fArr3;
            }
            i2 = i4;
            fArr4 = fArr2;
            f9 = f4;
            f11 = f11;
            f12 = f12;
            fArr5 = fArr5;
        }
        Arrays.sort(dominantColorResultArr, new Comparator() { // from class: com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare;
                compare = Integer.compare((int) (((ColorExtractor.DominantColorResult) obj2).percentage * 1000000.0f), (int) (((ColorExtractor.DominantColorResult) obj).percentage * 1000000.0f));
                return compare;
            }
        });
    }

    static void mergeDominantColorUnit(DominantColorResult dominantColorResult, DominantColorResult dominantColorResult2, ColorPaletteExtractor.ColorMergeType colorMergeType) {
        float f = dominantColorResult.percentage + dominantColorResult2.percentage;
        if (colorMergeType != ColorPaletteExtractor.ColorMergeType.A) {
            if (colorMergeType == ColorPaletteExtractor.ColorMergeType.B) {
                dominantColorResult.setColor(dominantColorResult2.color);
                dominantColorResult.isGrayScale = dominantColorResult2.isGrayScale;
            } else {
                int interpolatedColorHSVBased = IUXColorUtils.getInterpolatedColorHSVBased(dominantColorResult.hsv, dominantColorResult2.hsv, dominantColorResult2.percentage / f);
                dominantColorResult.setColor(interpolatedColorHSVBased);
                dominantColorResult.isGrayScale = checkGayScaleWithSV(IUXColorUtils.getHSVFromColor(interpolatedColorHSVBased), sSaturationThresholdForGrayscale, sBrightnessThresholdForGrayscale);
            }
        }
        dominantColorResult2.setColor(0);
        dominantColorResult.percentage = f;
        dominantColorResult2.percentage = 0.0f;
    }

    static int getAvgColorFromTwoColors(int i, float f, int i2, float f2) {
        return Color.argb((int) ((Color.alpha(i) * f) + (Color.alpha(i2) * f2)), (int) ((Color.red(i) * f) + (Color.red(i2) * f2)), (int) ((Color.green(i) * f) + (Color.green(i2) * f2)), (int) ((f * Color.blue(i)) + (f2 * Color.blue(i2))));
    }

    static void sortColorResult(DominantColorResult[] dominantColorResultArr) {
        Arrays.sort(dominantColorResultArr, new Comparator() { // from class: com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare;
                compare = Float.compare(((ColorExtractor.DominantColorResult) obj2).percentage, ((ColorExtractor.DominantColorResult) obj).percentage);
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

        public DominantColorResult(int i, float f) {
            float[] fArr = new float[3];
            this.hsv = fArr;
            this.color = i;
            IUXColorUtils.colorToHSV(i, fArr);
            this.isGrayScale = false;
            this.percentage = f;
        }

        public DominantColorResult(int i, float f, boolean z) {
            float[] fArr = new float[3];
            this.hsv = fArr;
            this.color = i;
            IUXColorUtils.colorToHSV(i, fArr);
            this.isGrayScale = z;
            this.percentage = f;
        }

        public void setColor(int i) {
            this.color = i;
            IUXColorUtils.colorToHSV(i, this.hsv);
        }

        public void copyHSV(float[] fArr) {
            float[] fArr2 = this.hsv;
            fArr[0] = fArr2[0];
            fArr[1] = fArr2[1];
            fArr[2] = fArr2[2];
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.color), Float.valueOf(this.percentage), Boolean.valueOf(this.isGrayScale));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DominantColorResult)) {
                return false;
            }
            DominantColorResult dominantColorResult = (DominantColorResult) obj;
            return this.color == dominantColorResult.color && Float.compare(dominantColorResult.percentage, this.percentage) == 0 && this.isGrayScale == dominantColorResult.isGrayScale;
        }

        public String toString() {
            return "\nDominantColorResult{\ncolor=" + Integer.toHexString(this.color) + "\npercentage=" + this.percentage + "\n\nisGrayScale=" + this.isGrayScale + '}';
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public DominantColorResult m9628clone() {
            try {
                DominantColorResult dominantColorResult = (DominantColorResult) super.clone();
                float[] fArr = this.hsv;
                if (fArr != null) {
                    dominantColorResult.hsv = new float[fArr.length];
                    int i = 0;
                    while (true) {
                        float[] fArr2 = this.hsv;
                        if (i >= fArr2.length) {
                            break;
                        }
                        dominantColorResult.hsv[i] = fArr2[i];
                        i++;
                    }
                }
                return dominantColorResult;
            } catch (CloneNotSupportedException e) {
                Log.e(TAG, "clone: " + e.getMessage());
                return null;
            }
        }
    }
}
