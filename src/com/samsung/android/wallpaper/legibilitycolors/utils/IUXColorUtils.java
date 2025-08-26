package com.samsung.android.wallpaper.legibilitycolors.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.scontext.SContextConstants;
import android.text.Spanned;

/* loaded from: classes6.dex */
public class IUXColorUtils {
    public static final float COLOR8_TO_NORMALIZED = 0.003921569f;
    static final float GRAYSCALE_THRESHOLD_BRIGHTNESS = 0.25f;
    static final float GRAYSCALE_THRESHOLD_SATURATION = 0.12f;

    public static int argb(int i, int i2, int i3, int i4) {
        return ((i << 24) & (-16777216)) | ((i2 << 16) & Spanned.SPAN_PRIORITY) | ((i3 << 8) & 65280) | (i4 & 255);
    }

    @Deprecated
    public static float caculateLuminosity(int i, int i2, int i3) {
        return ((i * 0.2126f) + (i2 * 0.7152f) + (i3 * 0.0722f)) * 0.003921569f;
    }

    @Deprecated
    public static float caculateLuminosity2(int i, int i2, int i3) {
        return (i * 0.299f) + (i2 * 0.587f) + (i3 * 0.114f);
    }

    @Deprecated
    public static float calculateGrayScaleColor(int i, int i2, int i3) {
        return ((i * i2) + i3) * 0.333333f;
    }

    public static float calculateLuminanceInLinearSpace(int i, int i2, int i3) {
        return ((i * 0.2126f) + (i2 * 0.7152f) + (i3 * 0.0722f)) * 0.003921569f;
    }

    public static int combinAlphaIntoIntColor(int i, int i2) {
        return ((i << 24) & (-16777216)) | (i2 & 16777215);
    }

    public static float getHumanEyeBasedHueNormalizedDistance(float f) {
        if (f > 252.0f) {
            f = (f - 252.0f) + 374.40002f;
        } else if (f > 180.0f) {
            f = 277.2f + (97.200005f * ((f - 180.0f) / 72.0f));
        } else if (f > 72.0f) {
            f = (205.2f * ((f - 72.0f) / 108.0f)) + 72.0f;
        }
        return f / 482.40002f;
    }

    public static void colorToHSV(int i, float[] fArr) {
        colorToHSV((i >> 16) & 255, (i >> 8) & 255, i & 255, fArr);
    }

    public static void colorToHSV(int i, int i2, int i3, float[] fArr) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6 = i < i2 ? i : i2;
        float f7 = i3;
        if (f6 >= f7) {
            f6 = f7;
        }
        float f8 = i > i2 ? i : i2;
        if (f8 > f7) {
            f7 = f8;
        }
        float f9 = f7 - f6;
        float f10 = 0.0f;
        if (f7 == 0.0f) {
            f = 0.0f;
            f2 = 0.0f;
        } else {
            if (f9 == 0.0f) {
                fArr[0] = 0.0f;
                fArr[1] = 0.0f;
                fArr[2] = f7 / 255.0f;
                return;
            }
            f2 = f9 / f7;
            if (i == f7) {
                f5 = (i2 - i3) / f9;
            } else {
                if (i2 == f7) {
                    f3 = (i3 - i) / f9;
                    f4 = 2.0f;
                } else {
                    f3 = (i - i2) / f9;
                    f4 = 4.0f;
                }
                f5 = f3 + f4;
            }
            float f11 = f5 * 60.0f;
            if (f11 < 0.0f) {
                f11 += 360.0f;
            }
            f10 = f11;
            f = f7 / 255.0f;
        }
        fArr[0] = f10;
        fArr[1] = f2;
        fArr[2] = f;
    }

    public static void copyHSVToHSV(float[] fArr, float[] fArr2) {
        System.arraycopy(fArr2, 0, fArr, 0, Math.min(fArr2.length, fArr.length));
    }

    public static void resetHSVBlack(float[] fArr) {
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        fArr[2] = 0.0f;
    }

    public static void setHSV(float[] fArr, float f, float f2, float f3) {
        fArr[0] = f;
        fArr[1] = f2;
        fArr[2] = f3;
    }

    public static boolean checkSameHSV(float[] fArr, float[] fArr2) {
        return fArr[0] == fArr2[0] && fArr[1] == fArr2[1] && fArr[2] == fArr2[2];
    }

    public static float[] getCopiedHSV(float[] fArr) {
        return new float[]{fArr[0], fArr[1], fArr[2]};
    }

    public static float[] getHSVFromColor(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        return fArr;
    }

    public static int HSVToColor(float[] fArr) {
        return HSVToColor(fArr, 1.0f);
    }

    public static int HSVToColor(float[] fArr, float f) {
        float f2;
        float f3;
        float f4;
        float f5;
        float f6 = fArr[0];
        float f7 = fArr[1];
        float f8 = fArr[2];
        float f9 = f6 * 0.0027777778f;
        if (f7 == 0.0f) {
            f2 = f8 * 255.0f;
            f3 = f2;
            f4 = f3;
        } else {
            float f10 = f9 * 6.0f;
            float f11 = f10 != 6.0f ? f10 : 0.0f;
            int iFloor = (int) Math.floor(f11);
            float f12 = (1.0f - f7) * f8;
            float f13 = f11 - iFloor;
            float f14 = (1.0f - (f7 * f13)) * f8;
            float f15 = (1.0f - (f7 * (1.0f - f13))) * f8;
            if (iFloor == 0) {
                f12 = f15;
                f15 = f12;
            } else if (iFloor != 1) {
                if (iFloor == 2) {
                    f5 = f12;
                    f12 = f8;
                } else if (iFloor == 3) {
                    f15 = f8;
                    f8 = f12;
                    f12 = f14;
                } else if (iFloor != 4) {
                    f15 = f14;
                } else {
                    f5 = f15;
                    f15 = f8;
                }
                f8 = f5;
            } else {
                f15 = f12;
                f12 = f8;
                f8 = f14;
            }
            f2 = f8 * 255.0f;
            f3 = f12 * 255.0f;
            f4 = f15 * 255.0f;
        }
        return argb((int) (f * 255.0f), (int) f2, (int) f3, (int) f4);
    }

    public static int rgb(int i, int i2, int i3) {
        return argb(255, i, i2, i3);
    }

    public static int getAverageColor(int[] iArr) {
        if (iArr == null) {
            return 0;
        }
        int length = iArr.length;
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        for (int i : iArr) {
            j += 16711680 & i;
            j2 += 65280 & i;
            j3 += i & 255;
        }
        long j4 = length;
        return (((int) (j / j4)) & Spanned.SPAN_PRIORITY) | (-16777216) | (((int) (j2 / j4)) & 65280) | (((int) (j3 / j4)) & 255);
    }

    public static float[] getAvgHSVFromTwoHSV(float[] fArr, float[] fArr2) {
        float f = fArr[0];
        float f2 = fArr2[0];
        float f3 = (f + f2) * 0.5f;
        if (Math.abs(f - f2) > 180.0f) {
            f3 += 180.0f;
            if (f3 >= 360.0f) {
                f3 -= 360.0f;
            }
        }
        return new float[]{f3, (fArr[1] + fArr2[1]) * 0.5f, (fArr[2] + fArr2[2]) * 0.5f};
    }

    public static float[] interpolateHSV(float[] fArr, float[] fArr2, float f) {
        float f2;
        float fAbs = Math.abs(fArr[0] - fArr2[0]);
        if (fAbs > 180.0f) {
            float f3 = (360.0f - fAbs) * f;
            float f4 = fArr[0];
            if (f4 < fArr2[0]) {
                f2 = f4 - f3;
                if (f2 < 0.0f) {
                    f2 += 360.0f;
                }
            } else {
                f2 = f4 + f3;
                if (f2 >= 360.0f) {
                    f2 -= 360.0f;
                }
            }
        } else {
            float f5 = fArr[0];
            f2 = f5 + ((fArr2[0] - f5) * f);
        }
        float f6 = fArr[1];
        float f7 = f6 + ((fArr2[1] - f6) * f);
        float f8 = fArr[2];
        return new float[]{f2, f7, f8 + ((fArr2[2] - f8) * f)};
    }

    public static float[] getAverageHSV(int[] iArr) {
        float[] fArr = new float[3];
        colorToHSV(getAverageColor(iArr), fArr);
        return fArr;
    }

    public static double colorDistance_hue(float f, float f2) {
        double d = f;
        double[] dArr = {Math.cos(Math.toRadians(d)), Math.sin(Math.toRadians(d))};
        double d2 = f2;
        double[] dArr2 = {Math.cos(Math.toRadians(d2)), Math.sin(Math.toRadians(d2))};
        return Math.toDegrees(Math.acos((dArr[0] * dArr2[0]) + (dArr[1] * dArr2[1])));
    }

    public static double colorDistanceHueFast(float f, float f2) {
        float fCos = IUXMathUtils.cos(f);
        float fSin = IUXMathUtils.sin(f);
        return Math.toDegrees(Math.acos((fCos * IUXMathUtils.cos(f2)) + (fSin * IUXMathUtils.sin(f2))));
    }

    public static double colorDistance_hue(float[] fArr, float[] fArr2) {
        return colorDistance_hue(fArr[0], fArr2[0]);
    }

    public static double hsvDistanceSquare2(float[] fArr, float[] fArr2, float[] fArr3) {
        double[] dArr = {Math.cos(Math.toRadians(fArr[0])), Math.sin(Math.toRadians(fArr[0]))};
        double[] dArr2 = {Math.cos(Math.toRadians(fArr2[0])), Math.sin(Math.toRadians(fArr2[0]))};
        double dAcos = Math.acos((dArr[0] * dArr2[0]) + (dArr[1] * dArr2[1])) / 3.141592653589793d;
        double d = fArr2[1] - fArr[1];
        double d2 = dAcos * fArr3[0] * 1.0d;
        double d3 = d * fArr3[1] * 1.0d;
        double d4 = (fArr2[2] - fArr[2]) * fArr3[2] * 1.0d;
        return (d2 * d2) + (d3 * d3) + (d4 * d4);
    }

    public static double colorDistance_hsv_square2(float[] fArr, float[] fArr2, float[] fArr3) {
        double[] dArr = {Math.cos(Math.toRadians(fArr[0])), Math.sin(Math.toRadians(fArr[0]))};
        double[] dArr2 = {Math.cos(Math.toRadians(fArr2[0])), Math.sin(Math.toRadians(fArr2[0]))};
        double dAcos = Math.acos((dArr[0] * dArr2[0]) + (dArr[1] * dArr2[1])) / 3.141592653589793d;
        double d = fArr2[1] - fArr[1];
        double d2 = dAcos * fArr3[0] * 1.0d;
        double d3 = d * fArr3[1] * 1.0d;
        double d4 = (fArr2[2] - fArr[2]) * fArr3[2] * 1.0d;
        return (d2 * d2) + (d3 * d3) + (d4 * d4);
    }

    public static float getHsvDistanceSquare2FromCornSpace(float[] fArr, float[] fArr2, float f) {
        float[] hsvPositionFromCornSpace = getHsvPositionFromCornSpace(fArr, f);
        float[] hsvPositionFromCornSpace2 = getHsvPositionFromCornSpace(fArr2, f);
        return IUXMathUtils.distanceSqrt2(hsvPositionFromCornSpace[0] - hsvPositionFromCornSpace2[0], hsvPositionFromCornSpace[1] - hsvPositionFromCornSpace2[1], hsvPositionFromCornSpace[2] - hsvPositionFromCornSpace2[2]);
    }

    public static void convertHsv2CornSpace(float[] fArr, float f, float[] fArr2) {
        float f2 = fArr[2];
        float f3 = fArr[0];
        float f4 = fArr[1] * f2 * f;
        fArr2[0] = IUXMathUtils.cos(f3) * f4;
        fArr2[1] = IUXMathUtils.sin(f3) * f4;
        fArr2[2] = f2;
    }

    public static float getHsvDistanceSquare2FromCornSpace(float[] fArr, float[] fArr2) {
        return IUXMathUtils.distanceSqrt2(fArr[0] - fArr2[0], fArr[1] - fArr2[1], fArr[2] - fArr2[2]);
    }

    public static float getHsvDistanceSquare2FromCornSpace(float[] fArr, float[] fArr2, float f, float[] fArr3, float[] fArr4) {
        float f2 = fArr[2];
        float f3 = fArr[0];
        float f4 = fArr[1] * f2 * f;
        float fCos = IUXMathUtils.cos(f3) * f4;
        float fSin = IUXMathUtils.sin(f3) * f4;
        float f5 = fArr2[2];
        float f6 = fArr2[0];
        float f7 = fArr2[1] * f5 * f;
        return IUXMathUtils.distanceSqrt2(fCos - (IUXMathUtils.cos(f6) * f7), fSin - (IUXMathUtils.sin(f6) * f7), f2 - f5);
    }

    public static float[] getHsvPositionFromCornSpace(float[] fArr, float f) {
        float[] fArr2 = new float[3];
        calculateHsvPositionFromCornSpace(fArr, f, fArr2);
        return fArr2;
    }

    public static void calculateHsvPositionFromCornSpace(float[] fArr, float f, float[] fArr2) {
        float f2 = fArr[2];
        float f3 = fArr[0] * 0.0174532f;
        float f4 = fArr[1] * f2 * f;
        double d = f3;
        fArr2[0] = ((float) Math.cos(d)) * f4;
        fArr2[1] = ((float) Math.sin(d)) * f4;
        fArr2[2] = f2;
    }

    public static double getHsvDistanceSquare2FromCornSpaceDouble(float[] fArr, float[] fArr2, float f) {
        float[] hsvPositionFromCornSpace = getHsvPositionFromCornSpace(fArr, f);
        float[] hsvPositionFromCornSpace2 = getHsvPositionFromCornSpace(fArr2, f);
        return IUXMathUtils.distanceSqrt2(hsvPositionFromCornSpace[0] - hsvPositionFromCornSpace2[0], hsvPositionFromCornSpace[1] - hsvPositionFromCornSpace2[1], hsvPositionFromCornSpace[2] - hsvPositionFromCornSpace2[2]);
    }

    public static double getHsvDistanceSquare2FromCornSpaceDouble(float[] fArr, float[] fArr2, float f, float[] fArr3, float[] fArr4) {
        calculateHsvPositionFromCornSpace(fArr, f, fArr3);
        calculateHsvPositionFromCornSpace(fArr2, f, fArr4);
        return IUXMathUtils.distanceSqrt2(fArr3[0] - fArr4[0], fArr3[1] - fArr4[1], fArr3[2] - fArr4[2]);
    }

    public static double getHsvDistanceSquare2FromCornSpaceDoubleFast(float[] fArr, float[] fArr2, float f) {
        float f2 = fArr[2];
        float f3 = fArr[0];
        float f4 = fArr[1] * f2 * f;
        float fCos = IUXMathUtils.cos(f3) * f4;
        float fSin = IUXMathUtils.sin(f3) * f4;
        float f5 = fArr2[2];
        float f6 = fArr2[0];
        float f7 = fArr2[1] * f5 * f;
        return IUXMathUtils.distanceSqrt2(fCos - (IUXMathUtils.cos(f6) * f7), fSin - (IUXMathUtils.sin(f6) * f7), f2 - f5);
    }

    public static double[] getHsvPositionFromCornSpaceDouble(float[] fArr, double d) {
        double[] dArr = new double[3];
        calculateHsvPositionFromCornSpaceDouble(fArr, d, dArr);
        return dArr;
    }

    public static void calculateHsvPositionFromCornSpaceDouble(float[] fArr, double d, double[] dArr) {
        double d2 = fArr[2];
        double d3 = fArr[0] * 0.0174532f;
        double d4 = fArr[1] * d2 * d;
        dArr[0] = Math.cos(d3) * d4;
        dArr[1] = Math.sin(d3) * d4;
        dArr[2] = d2;
    }

    public static double colorDistanceHSV(float[] fArr, float[] fArr2, float[] fArr3) {
        double[] dArr = {Math.cos(Math.toRadians(fArr[0])), Math.sin(Math.toRadians(fArr[0]))};
        double[] dArr2 = {Math.cos(Math.toRadians(fArr2[0])), Math.sin(Math.toRadians(fArr2[0]))};
        double dAcos = Math.acos((dArr[0] * dArr2[0]) + (dArr[1] * dArr2[1])) / 3.141592653589793d;
        double d = fArr2[1] - fArr[1];
        double d2 = fArr2[2] - fArr[2];
        float f = fArr3[0];
        float f2 = fArr3[1];
        float f3 = fArr3[2];
        double d3 = 1.0f / ((f + f2) + f3);
        return (dAcos * f * d3) + (d * f2 * d3) + (d2 * f3 * d3);
    }

    public static double colorDistance_hsv(float[] fArr, float[] fArr2, float[] fArr3) {
        return Math.sqrt(colorDistance_hsv_square2(fArr, fArr2, fArr3));
    }

    public static double colorDistance_rgb(int i, int i2) {
        return Math.sqrt(Math.pow(Color.red(i) - Color.red(i2), 2.0d) + Math.pow(Color.green(i) - Color.green(i2), 2.0d) + Math.pow(Color.blue(i) - Color.blue(i2), 2.0d));
    }

    public static double colorDistance_rgb_sqaure2(int i, int i2) {
        return Math.pow(Color.red(i) - Color.red(i2), 2.0d) + Math.pow(Color.green(i) - Color.green(i2), 2.0d) + Math.pow(Color.blue(i) - Color.blue(i2), 2.0d);
    }

    public static boolean checkGayScale(int i, double d) {
        float[] fArr = new float[3];
        Color.RGBToHSV(Color.red(i), Color.green(i), Color.blue(i), fArr);
        return checkGayScale(fArr, d);
    }

    public static boolean checkGayScale(float[] fArr, double d) {
        return ((double) fArr[1]) <= 0.10000000149011612d + d || ((double) fArr[2]) <= d + 0.15000000596046448d;
    }

    public static boolean checkGrayScaleWithSV(float[] fArr, float f, float f2) {
        return fArr[1] <= f || fArr[2] <= f2;
    }

    public static boolean checkWhite(int i, double d) {
        float[] fArr = new float[3];
        Color.RGBToHSV(Color.red(i), Color.green(i), Color.blue(i), fArr);
        return checkWhite(fArr, d);
    }

    public static boolean checkWhite(float[] fArr, double d) {
        return ((double) fArr[1]) <= 0.05000000074505806d + d && ((double) fArr[2]) >= d + 0.8999999761581421d;
    }

    @Deprecated
    public static float calculateGrayScaleColor(int i) {
        return calculateGrayScaleColor(Color.red(i), Color.green(i), Color.blue(i));
    }

    @Deprecated
    public static float caculateLuminosity(int i) {
        return caculateLuminosity(Color.red(i), Color.green(i), Color.blue(i));
    }

    @Deprecated
    public static float caculateLuminosity2(int i) {
        return caculateLuminosity(Color.red(i), Color.green(i), Color.blue(i));
    }

    public static float calculateLuminance(int i) {
        return calculateLuminance((i >> 16) & 255, (i >> 8) & 255, i & 255);
    }

    public static float calculateLuminance(int i, int i2, int i3) {
        double d = i * 0.00392156862745098d;
        double d2 = i2 * 0.00392156862745098d;
        double d3 = i3 * 0.00392156862745098d;
        return (float) (((d < 0.04045d ? d / 12.92d : Math.pow((d + 0.055d) / 1.055d, 2.4d)) * 0.2126d) + ((d2 < 0.04045d ? d2 / 12.92d : Math.pow((d2 + 0.055d) / 1.055d, 2.4d)) * 0.7152d) + ((d3 < 0.04045d ? d3 / 12.92d : Math.pow((d3 + 0.055d) / 1.055d, 2.4d)) * 0.0722d));
    }

    public static float getGammaCorrectedValue(float f) {
        return (float) (f < 0.04045f ? f / 12.92d : Math.pow((f + 0.055d) / 1.055d, 2.4d));
    }

    public static double getGammaCorrectionFromValue(double d) {
        return d < 0.04045d ? d / 12.92d : Math.pow((d + 0.055d) / 1.055d, 2.4d);
    }

    public static float calculateLuminanceInLinearSpace(int i) {
        return calculateLuminance(Color.red(i), Color.green(i), Color.blue(i));
    }

    public static float convertPixelsToDp(float f) {
        return Math.round(f / (Resources.getSystem().getDisplayMetrics().densityDpi / 160.0f));
    }

    public static int convertDpToPixel(float f) {
        return Math.round(f * (Resources.getSystem().getDisplayMetrics().densityDpi / 160.0f));
    }

    public static int getInterpolatedColorHSVBased(float[] fArr, float[] fArr2, float f) {
        return Color.HSVToColor(getInterpolatedHSV(fArr, fArr2, f));
    }

    public static float[] getInterpolatedHSV(float[] fArr, float[] fArr2, float f) {
        float[] fArr3 = new float[3];
        float f2 = fArr[0];
        if (f2 == fArr2[0]) {
            fArr3[0] = f2;
        } else {
            double[] dArr = {IUXMathUtils.cos(f2), IUXMathUtils.sin(fArr[0])};
            double[] dArr2 = {IUXMathUtils.cos(fArr2[0]), IUXMathUtils.sin(fArr2[0])};
            float f3 = (fArr[0] + 90.0f) % 360.0f;
            boolean z = (dArr2[0] * ((double) IUXMathUtils.cos(f3))) + (dArr2[1] * ((double) IUXMathUtils.sin(f3))) >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            double degrees = Math.toDegrees(Math.acos((dArr[0] * dArr2[0]) + (dArr[1] * dArr2[1]))) * f;
            if (z) {
                float f4 = (float) (fArr[0] + degrees);
                fArr3[0] = f4;
                if (f4 > 360.0f) {
                    fArr3[0] = f4 - 360.0f;
                }
            } else {
                float f5 = (float) (fArr[0] - degrees);
                fArr3[0] = f5;
                if (f5 < 0.0f) {
                    fArr3[0] = f5 + 360.0f;
                }
            }
        }
        fArr3[1] = IUXMathUtils.lerp(f, fArr[1], fArr2[1]);
        fArr3[2] = IUXMathUtils.lerp(f, fArr[2], fArr2[2]);
        return fArr3;
    }

    public static boolean checkSameHSV(float[] fArr, float[] fArr2, float f) {
        boolean zCheckGrayScaleWithSV = checkGrayScaleWithSV(fArr, 0.12f, 0.25f);
        if (zCheckGrayScaleWithSV == checkGrayScaleWithSV(fArr2, 0.12f, 0.25f)) {
            float[] fArr3 = {1.0f, 0.35f, 0.65f};
            float f2 = 1.7320508f * f;
            float f3 = f2 * f2;
            float[] fArr4 = {0.0f, 0.0f, 1.0f};
            float f4 = f * 1.0f;
            float f5 = f4 * f4;
            if (zCheckGrayScaleWithSV) {
                f3 = f5;
                fArr3 = fArr4;
            }
            if (colorDistance_hsv_square2(fArr, fArr2, fArr3) < f3) {
                return true;
            }
        }
        return false;
    }

    public static int[] getRGBFromColor(int i) {
        return new int[]{Color.red(i), Color.green(i), Color.blue(i), Color.alpha(i)};
    }

    public static int multipleColorValue(int i, float f) {
        float fMin = Math.min(f, 1.0f);
        return Color.rgb((int) (Color.red(i) * fMin), (int) (Color.green(i) * fMin), (int) (Color.blue(i) * fMin));
    }

    public static int addColorColor(int i, int i2) {
        return Color.rgb(Math.min(Color.red(i) + Color.red(i2), 255), Math.min(Color.green(i) + Color.green(i2), 255), Math.min(Color.blue(i) + Color.blue(i2), 255));
    }

    public static float getRedRatio(int i) {
        return Color.red(i) * 0.003921569f;
    }

    public static float getGreenRatio(int i) {
        return Color.green(i) * 0.003921569f;
    }

    public static float getBlueRatio(int i) {
        return Color.blue(i) * 0.003921569f;
    }

    public static float getAlphaRatio(int i) {
        return Color.alpha(i) * 0.003921569f;
    }

    public static int getColorFromLuminance(float f) {
        int i = (int) (f * 255.0f);
        return Color.rgb(i, i, i);
    }

    public static int getColorFromChannelValue(int i) {
        return Color.rgb(i, i, i);
    }

    public static float[] getInverseHSV(float[] fArr) {
        return new float[]{((int) (fArr[0] + 180.0f)) % 360, fArr[1], fArr[2]};
    }

    Bitmap getGradation(final int i, final int i2, final int i3, final int i4, final float f) {
        ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory(this) { // from class: com.samsung.android.wallpaper.legibilitycolors.utils.IUXColorUtils.1
            @Override // android.graphics.drawable.ShapeDrawable.ShaderFactory
            public Shader resize(int i5, int i6) {
                LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, i4, new int[]{i, i2}, new float[]{0.0f, 1.0f}, Shader.TileMode.MIRROR);
                Matrix matrix = new Matrix();
                matrix.setRotate(f, i3 * 0.5f, i4 * 0.5f);
                Matrix matrix2 = new Matrix();
                Matrix matrix3 = new Matrix();
                matrix3.setConcat(matrix, matrix2);
                linearGradient.setLocalMatrix(matrix3);
                return linearGradient;
            }
        };
        PaintDrawable paintDrawable = new PaintDrawable();
        paintDrawable.setShape(new RectShape());
        paintDrawable.setShaderFactory(shaderFactory);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i3, i4, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        paintDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        paintDrawable.draw(canvas);
        return bitmapCreateBitmap;
    }
}
