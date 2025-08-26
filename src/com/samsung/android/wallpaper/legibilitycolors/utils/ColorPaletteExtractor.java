package com.samsung.android.wallpaper.legibilitycolors.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.scontext.SContextConstants;
import android.text.Spanned;
import android.util.Log;
import com.android.internal.graphics.ColorUtils;
import com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class ColorPaletteExtractor extends ColorExtractor {
    public static final float DEFAULT_GRAYSCALE_THRESHOLD_BRIGHTNESS = 0.18f;
    public static final float DEFAULT_GRAYSCALE_THRESHOLD_SATURATION = 0.12f;
    static final float LAB_BRIGHTNESS_SCALE = 10.0f;
    static final String TAG = "ColorPaletteExtractor";
    static float sBrightnessThresholdForGrayscale = 0.18f;
    static float sSaturationThresholdForGrayscale = 0.12f;
    static float sMaxRGB = (float) Math.sqrt(Math.pow(255.0d, 2.0d) * 3.0d);
    static float sHsvSpaceHueRadiusValue = 1.0f;
    static float sMaxHSV = sHsvSpaceHueRadiusValue * 2.0f;
    static float sLabSpaceLightnessScale = 50.0f;
    static float sMaxLab = (int) Math.sqrt((Math.pow(sLabSpaceLightnessScale * 100.0f, 2.0d) + Math.pow(255.0d, 2.0d)) + Math.pow(255.0d, 2.0d));

    public enum ColorMergeType {
        MIX,
        A,
        B
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v0 com.samsung.android.wallpaper.legibilitycolors.utils.ColorPaletteExtractor$ColorSpace, still in use, count: 1, list:
      (r0v0 com.samsung.android.wallpaper.legibilitycolors.utils.ColorPaletteExtractor$ColorSpace) from 0x002e: FILLED_NEW_ARRAY 
      (r0v0 com.samsung.android.wallpaper.legibilitycolors.utils.ColorPaletteExtractor$ColorSpace)
      (r1v1 com.samsung.android.wallpaper.legibilitycolors.utils.ColorPaletteExtractor$ColorSpace)
      (r2v2 com.samsung.android.wallpaper.legibilitycolors.utils.ColorPaletteExtractor$ColorSpace)
     A[WRAPPED] (LINE:18) elemType: com.samsung.android.wallpaper.legibilitycolors.utils.ColorPaletteExtractor$ColorSpace
    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
    	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
    	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
    	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:252)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:180)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class ColorSpace {
        RGB,
        HSV,
        LAB,
        HUE;

        static ColorSpace[] ColorSpaceIndex = {new ColorSpace(), new ColorSpace(), new ColorSpace()};

        private ColorSpace() {
        }

        public static ColorSpace valueOf(String str) {
            return (ColorSpace) Enum.valueOf(ColorSpace.class, str);
        }

        public static ColorSpace[] values() {
            return (ColorSpace[]) $VALUES.clone();
        }

        static {
        }
    }

    public static void setSaturationThresholdForGrayscale(float f) {
        sSaturationThresholdForGrayscale = f;
    }

    public static void setBrightnessThresholdForGrayscale(float f) {
        sBrightnessThresholdForGrayscale = f;
    }

    public static float getSaturationThresholdForGrayscale() {
        return sSaturationThresholdForGrayscale;
    }

    public static float getBrightnessThresholdForGrayscale() {
        return sBrightnessThresholdForGrayscale;
    }

    public static void setHsvSpaceHueRadiusValue(float f) {
        sHsvSpaceHueRadiusValue = f;
        sMaxHSV = f * 2.0f;
    }

    public static float getHsvSpaceHueRadiusValue() {
        return sHsvSpaceHueRadiusValue;
    }

    public static void setLabSpaceLightnessValue(float f) {
        sLabSpaceLightnessScale = f;
    }

    public static float getHsvSpaceClusteringRadiusValue() {
        return sLabSpaceLightnessScale;
    }

    public static int[] makeClusterGroupColorBandBasedFromHueInterval(float f) {
        return makeClusterGroupColorBandBasedFromHueInterval(f, new float[]{0.5f, 0.2f});
    }

    public static int[] makeClusterGroupColorBandBasedFromHueInterval(float f, float[] fArr) {
        int i;
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(Color.HSVToColor(new float[]{0.0f, 0.0f, 0.0f})));
        arrayList.add(Integer.valueOf(Color.HSVToColor(new float[]{0.0f, 0.0f, 0.3333f})));
        arrayList.add(Integer.valueOf(Color.HSVToColor(new float[]{0.0f, 0.0f, 0.6666f})));
        arrayList.add(Integer.valueOf(Color.HSVToColor(new float[]{0.0f, 0.0f, 1.0f})));
        if (f < 0.0f) {
            f *= -1.0f;
        }
        float f2 = 0.0f;
        while (true) {
            i = 0;
            if (f2 >= 360.0f) {
                break;
            }
            arrayList.add(Integer.valueOf(Color.HSVToColor(new float[]{f2, 1.0f, 1.0f})));
            f2 += f;
        }
        for (int i2 = 0; i2 < fArr.length; i2++) {
            for (float f3 = 0.0f; f3 < 360.0f; f3 += f) {
                arrayList.add(Integer.valueOf(Color.HSVToColor(new float[]{f3, fArr[i2], 1.0f})));
            }
            for (float f4 = 0.0f; f4 < 360.0f; f4 += f) {
                arrayList.add(Integer.valueOf(Color.HSVToColor(new float[]{f4, 1.0f, fArr[i2]})));
            }
            for (float f5 = 0.0f; f5 < 360.0f; f5 += f) {
                float f6 = fArr[i2];
                arrayList.add(Integer.valueOf(Color.HSVToColor(new float[]{f5, f6, f6})));
            }
        }
        int[] iArr = new int[arrayList.size()];
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            iArr[i] = ((Integer) it.next()).intValue();
            i++;
        }
        return iArr;
    }

    static class ColorResultData {
        double dist;
        int index;
        int indexTarget;

        ColorResultData(int i, int i2, double d) {
            this.index = i;
            this.indexTarget = i2;
            this.dist = d;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0161  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int discardSameColorFromDominantColorsForColorPalette(ColorExtractor.DominantColorResult[] dominantColorResultArr, double d, ColorSpace colorSpace, boolean z) {
        double d2;
        double d3;
        float f;
        int i;
        float f2;
        int i2;
        double d4;
        double d5;
        ColorExtractor.DominantColorResult[] dominantColorResultArr2 = dominantColorResultArr;
        ColorSpace colorSpace2 = colorSpace;
        float[] fArr = new float[3];
        float[] fArr2 = new float[3];
        double[] dArr = new double[3];
        double[] dArr2 = new double[3];
        int length = dominantColorResultArr2.length;
        if (colorSpace2 == ColorSpace.HUE) {
            d2 = 360.0d;
            d3 = d * 360.0d;
        } else if (colorSpace2 == ColorSpace.RGB) {
            d2 = 360.0d;
            d3 = d * sMaxRGB;
        } else {
            d2 = 360.0d;
            if (colorSpace2 == ColorSpace.HSV) {
                f = sMaxHSV;
            } else if (colorSpace2 == ColorSpace.LAB) {
                f = sMaxLab;
            } else {
                d3 = d;
            }
            d3 = f * d;
        }
        int i3 = 0;
        int i4 = 0;
        float f3 = 0.0f;
        float f4 = 0.0f;
        double dColorDistance_rgb_sqaure2 = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        while (true) {
            ColorResultData[] colorResultDataArr = new ColorResultData[length];
            double d6 = d2;
            int i5 = 0;
            boolean z2 = false;
            while (i5 < length) {
                ColorExtractor.DominantColorResult dominantColorResult = dominantColorResultArr2[i5];
                if (dominantColorResult.percentage == 0.0f) {
                    break;
                }
                int i6 = dominantColorResult.color;
                if (colorSpace2 == ColorSpace.HSV || colorSpace2 == ColorSpace.HUE) {
                    dominantColorResult.copyHSV(fArr);
                    float fCalculateLuminance = IUXColorUtils.calculateLuminance(i6);
                    f2 = fArr[0];
                    f4 = fCalculateLuminance;
                } else {
                    if (colorSpace2 == ColorSpace.LAB) {
                        ColorUtils.colorToLAB(i6, dArr);
                    }
                    f2 = f3;
                }
                int i7 = i5 + 1;
                int i8 = i7;
                double d7 = Double.MAX_VALUE;
                double[] dArr3 = dArr;
                int i9 = -1;
                while (true) {
                    if (i8 >= length) {
                        i2 = i3;
                        break;
                    }
                    int i10 = i8;
                    ColorExtractor.DominantColorResult dominantColorResult2 = dominantColorResultArr[i10];
                    i2 = i3;
                    if (dominantColorResult2.percentage == 0.0f) {
                        break;
                    }
                    int i11 = dominantColorResult2.color;
                    int i12 = i7;
                    if (colorSpace2 == ColorSpace.HSV || colorSpace2 == ColorSpace.HUE) {
                        dominantColorResult2.copyHSV(fArr2);
                    } else if (colorSpace2 == ColorSpace.LAB) {
                        ColorUtils.colorToLAB(i11, dArr2);
                    }
                    int iOrdinal = colorSpace2.ordinal();
                    int i13 = i4;
                    if (iOrdinal == 0) {
                        dColorDistance_rgb_sqaure2 = IUXColorUtils.colorDistance_rgb_sqaure2(dominantColorResult.color, dominantColorResult2.color);
                    } else if (iOrdinal == 1) {
                        dColorDistance_rgb_sqaure2 = IUXColorUtils.getHsvDistanceSquare2FromCornSpaceDoubleFast(fArr, fArr2, sHsvSpaceHueRadiusValue);
                    } else if (iOrdinal != 2) {
                        if (iOrdinal == 3) {
                            if (dominantColorResult.isGrayScale == dominantColorResult2.isGrayScale) {
                                if (dominantColorResult.isGrayScale) {
                                    d4 = 2.0d;
                                    dColorDistance_rgb_sqaure2 = Math.pow((f4 - IUXColorUtils.calculateLuminance(i11)) * d3 * 1.5d, 2.0d);
                                } else {
                                    d4 = 2.0d;
                                    dColorDistance_rgb_sqaure2 = Math.pow(IUXColorUtils.colorDistanceHueFast(f2, fArr2[0]), 2.0d);
                                }
                            } else {
                                dColorDistance_rgb_sqaure2 = Math.pow(Math.max((((Math.sqrt(IUXColorUtils.getHsvDistanceSquare2FromCornSpaceDoubleFast(fArr, fArr2, sHsvSpaceHueRadiusValue)) / sMaxHSV) - 0.019999999552965164d) * d6) + d3, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN), 2.0d);
                                d4 = 2.0d;
                            }
                        }
                        if (dColorDistance_rgb_sqaure2 >= Math.pow(d3, d4)) {
                            d5 = d7;
                            if (d5 > dColorDistance_rgb_sqaure2) {
                                d7 = dColorDistance_rgb_sqaure2;
                                i9 = i10;
                            }
                            i8 = i10 + 1;
                            colorSpace2 = colorSpace;
                            i3 = i2;
                            i7 = i12;
                            i4 = i13;
                        } else {
                            d5 = d7;
                        }
                        d7 = d5;
                        i8 = i10 + 1;
                        colorSpace2 = colorSpace;
                        i3 = i2;
                        i7 = i12;
                        i4 = i13;
                    } else {
                        double d8 = dArr3[0];
                        float f5 = sLabSpaceLightnessScale;
                        dColorDistance_rgb_sqaure2 = IUXMathUtils.distanceSqrt2((f5 * d8) - (dArr2[0] * f5), dArr3[1] - dArr2[1], dArr3[2] - dArr2[2]);
                    }
                    d4 = 2.0d;
                    if (dColorDistance_rgb_sqaure2 >= Math.pow(d3, d4)) {
                    }
                    d7 = d5;
                    i8 = i10 + 1;
                    colorSpace2 = colorSpace;
                    i3 = i2;
                    i7 = i12;
                    i4 = i13;
                }
                int i14 = i4;
                int i15 = i7;
                double d9 = d7;
                if (i9 != -1) {
                    colorResultDataArr[i5] = new ColorResultData(i5, i9, d9);
                    z2 = true;
                }
                dominantColorResultArr2 = dominantColorResultArr;
                colorSpace2 = colorSpace;
                f3 = f2;
                dArr = dArr3;
                i3 = i2;
                i5 = i15;
                i4 = i14;
            }
            double[] dArr4 = dArr;
            int i16 = i3;
            int i17 = i4;
            if (z2) {
                Arrays.sort(colorResultDataArr, new Comparator<ColorResultData>() { // from class: com.samsung.android.wallpaper.legibilitycolors.utils.ColorPaletteExtractor.1
                    @Override // java.util.Comparator
                    public int compare(ColorResultData colorResultData, ColorResultData colorResultData2) {
                        if (colorResultData == null && colorResultData2 == null) {
                            return 0;
                        }
                        if (colorResultData == null) {
                            return 1;
                        }
                        if (colorResultData2 == null) {
                            return -1;
                        }
                        return Double.compare(colorResultData.dist, colorResultData2.dist);
                    }
                });
                i3 = i16;
                for (int i18 = 0; i18 < length; i18 = i + 1) {
                    ColorResultData colorResultData = colorResultDataArr[i18];
                    if (colorResultData == null) {
                        break;
                    }
                    int i19 = colorResultData.index;
                    int i20 = colorResultData.indexTarget;
                    ColorExtractor.DominantColorResult dominantColorResult3 = dominantColorResultArr[i19];
                    ColorExtractor.DominantColorResult dominantColorResult4 = dominantColorResultArr[i20];
                    if (dominantColorResult3.percentage <= 0.0f || dominantColorResult4.percentage <= 0.0f) {
                        i = i18;
                    } else {
                        if (!z) {
                            mergeDominantColorUnit(dominantColorResult3, dominantColorResult4, ColorMergeType.A);
                            i = i18;
                        } else {
                            int i21 = dominantColorResult3.color;
                            dominantColorResult3.copyHSV(fArr);
                            int i22 = dominantColorResult4.color;
                            dominantColorResult4.copyHSV(fArr2);
                            boolean z3 = dominantColorResult3.isGrayScale;
                            float f6 = dominantColorResult3.percentage;
                            float f7 = dominantColorResult4.percentage;
                            float fCalculateLuminance2 = IUXColorUtils.calculateLuminance(i21);
                            float fCalculateLuminance3 = IUXColorUtils.calculateLuminance(i22);
                            float f8 = f7 / f6;
                            i = i18;
                            if (dominantColorResult3.isGrayScale != dominantColorResult4.isGrayScale) {
                                if (z3 && fArr[2] < fArr2[2] * f8 * 1.2f) {
                                    mergeDominantColorUnit(dominantColorResult3, dominantColorResult4, ColorMergeType.B);
                                } else {
                                    mergeDominantColorUnit(dominantColorResult3, dominantColorResult4, ColorMergeType.A);
                                }
                                i3++;
                            } else if (!dominantColorResult3.isGrayScale) {
                                if (fArr[1] * fArr[2] < fArr2[1] * fArr2[2] * f8) {
                                    mergeDominantColorUnit(dominantColorResult3, dominantColorResult4, ColorMergeType.B);
                                } else {
                                    mergeDominantColorUnit(dominantColorResult3, dominantColorResult4, ColorMergeType.A);
                                }
                                i3++;
                            } else if (fCalculateLuminance2 < fCalculateLuminance3 * f8) {
                                mergeDominantColorUnit(dominantColorResult3, dominantColorResult4, ColorMergeType.B);
                            } else {
                                mergeDominantColorUnit(dominantColorResult3, dominantColorResult4, ColorMergeType.A);
                            }
                        }
                        i3++;
                    }
                }
                sortColorResult(dominantColorResultArr);
            } else {
                i3 = i16;
            }
            if (!z2) {
                break;
            }
            i4 = i17 + 1;
            if (i17 >= 1000) {
                break;
            }
            dominantColorResultArr2 = dominantColorResultArr;
            colorSpace2 = colorSpace;
            d2 = d6;
            dArr = dArr4;
        }
        return i3;
    }

    public static int[] getOnlyColorsFromDominantColor(ColorExtractor.DominantColorResult[] dominantColorResultArr, double d) {
        int i;
        ArrayList arrayList = new ArrayList();
        int length = dominantColorResultArr.length;
        while (true) {
            length--;
            if (length <= 0) {
                break;
            }
            ColorExtractor.DominantColorResult dominantColorResult = dominantColorResultArr[length];
            if (dominantColorResult.percentage > 0.009f && !dominantColorResult.isGrayScale) {
                float[] fArr = dominantColorResult.hsv;
                int i2 = length - 1;
                while (true) {
                    if (i2 >= 0) {
                        if (!dominantColorResultArr[i2].isGrayScale) {
                            if (((float) IUXColorUtils.colorDistanceHueFast(fArr[0], r6.hsv[0])) < d) {
                                break;
                            }
                        }
                        i2--;
                    } else {
                        arrayList.add(dominantColorResult);
                        break;
                    }
                }
            }
        }
        if (!dominantColorResultArr[0].isGrayScale) {
            arrayList.add(dominantColorResultArr[0]);
        }
        int size = arrayList.size();
        if (size <= 0) {
            return null;
        }
        int[] iArr = new int[size];
        for (i = 0; i < size; i++) {
            iArr[i] = ((ColorExtractor.DominantColorResult) arrayList.get((size - 1) - i)).color;
        }
        return iArr;
    }

    public static ColorExtractor.DominantColorResult[] kMeansHsv(Bitmap bitmap, int[] iArr) {
        return kMeansHsv(bitmap, iArr, false);
    }

    public static ColorExtractor.DominantColorResult[] kMeansHsv(Bitmap bitmap, int[] iArr, boolean z) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.i(TAG, "kMeansHsv input bitmap size = " + width + " x " + height + " | ClusterGroups Num = " + iArr.length);
        int[] iArr2 = new int[width * height];
        bitmap.getPixels(iArr2, 0, width, 0, 0, width, height);
        return kMeansHsv(iArr2, iArr, z, width, height);
    }

    public static ColorExtractor.DominantColorResult[] kMeansHsv(int[] iArr, int[] iArr2) {
        return kMeansHsv(iArr, iArr2, false, 0, 0);
    }

    public static ColorExtractor.DominantColorResult[] kMeansHsv(int[] iArr, int[] iArr2, boolean z, int i, int i2) {
        int i3;
        int i4;
        int i5;
        int[] iArr3;
        int length = iArr2.length;
        ColorExtractor.DominantColorResult[] dominantColorResultArr = new ColorExtractor.DominantColorResult[length];
        int[] iArr4 = new int[length];
        float[][] fArr = new float[length][];
        float[][] fArr2 = new float[length][];
        int[] iArr5 = new int[length];
        long[][] jArr = new long[length][];
        int i6 = 3;
        float[] fArr3 = new float[3];
        float f = sHsvSpaceHueRadiusValue;
        float f2 = sSaturationThresholdForGrayscale;
        float f3 = sBrightnessThresholdForGrayscale;
        for (int i7 = 0; i7 < length; i7++) {
            fArr2[i7] = new float[i6];
            float[] fArr4 = new float[i6];
            IUXColorUtils.colorToHSV(iArr2[i7], fArr4);
            IUXColorUtils.convertHsv2CornSpace(fArr4, f, fArr4);
            fArr[i7] = fArr4;
            iArr5[i7] = 0;
            i6 = 3;
            jArr[i7] = new long[]{0, 0, 0};
        }
        Runtime runtime = Runtime.getRuntime();
        long jFreeMemory = runtime.totalMemory() - runtime.freeMemory();
        int length2 = iArr.length;
        int i8 = 0;
        int i9 = 0;
        while (i8 < length2) {
            int i10 = i8;
            int i11 = iArr[i10];
            if ((i11 >>> 24) <= 0) {
                iArr3 = iArr4;
            } else {
                int i12 = i9 + 1;
                IUXColorUtils.colorToHSV(i11, fArr3);
                IUXColorUtils.convertHsv2CornSpace(fArr3, f, fArr3);
                float f4 = fArr3[0];
                float f5 = fArr3[1];
                float f6 = fArr3[2];
                float f7 = Float.MAX_VALUE;
                int i13 = 0;
                for (int i14 = 0; i14 < length; i14++) {
                    float[] fArr5 = fArr[i14];
                    float f8 = f4 - fArr5[0];
                    float f9 = f5 - fArr5[1];
                    float f10 = f6 - fArr5[2];
                    float f11 = (f8 * f8) + (f9 * f9) + (f10 * f10);
                    if (f11 < f7) {
                        i13 = i14;
                        f7 = f11;
                    }
                }
                long[] jArr2 = jArr[i13];
                iArr5[i13] = iArr5[i13] + 1;
                iArr3 = iArr4;
                jArr2[0] = jArr2[0] + (i11 & Spanned.SPAN_PRIORITY);
                jArr2[1] = jArr2[1] + (i11 & 65280);
                jArr2[2] = jArr2[2] + (i11 & 255);
                i9 = i12;
            }
            i8 = i10 + 1;
            iArr4 = iArr3;
        }
        int[] iArr6 = iArr4;
        for (int i15 = 0; i15 < length; i15++) {
            long[] jArr3 = jArr[i15];
            int i16 = iArr5[i15];
            if (i16 <= 0) {
                i5 = 0;
                i3 = 0;
                i4 = 0;
            } else {
                long j = jArr3[0];
                long j2 = i16;
                i3 = ((int) (j / j2)) & Spanned.SPAN_PRIORITY;
                i4 = ((int) (jArr3[1] / j2)) & 65280;
                i5 = ((int) (jArr3[2] / j2)) & 255;
            }
            int i17 = i5 | (-16777216) | i3 | i4;
            iArr6[i15] = i17;
            IUXColorUtils.colorToHSV(i17, fArr2[i15]);
        }
        float f12 = i9 > 0 ? 1.0f / i9 : 0.0f;
        for (int i18 = 0; i18 < length; i18++) {
            dominantColorResultArr[i18] = new ColorExtractor.DominantColorResult(iArr6[i18], iArr5[i18] * f12, checkGayScaleWithSV(fArr2[i18], f2, f3));
        }
        Log.i(TAG, "ColorExtractor Memory Usage " + ((runtime.totalMemory() - runtime.freeMemory()) - jFreeMemory) + " length: " + iArr.length);
        sortColorResult(dominantColorResultArr);
        return dominantColorResultArr;
    }
}
