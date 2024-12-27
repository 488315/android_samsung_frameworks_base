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
import android.util.DisplayMetrics;

/* loaded from: classes6.dex */
public class IUXColorUtils {
    public static final float COLOR8_TO_NORMALIZED = 0.003921569f;
    static final float GRAYSCALE_THRESHOLD_BRIGHTNESS = 0.25f;
    static final float GRAYSCALE_THRESHOLD_SATURATION = 0.12f;

    public static void colorToHSV(int color, float[] hsv) {
        colorToHSV((color >> 16) & 255, (color >> 8) & 255, color & 255, hsv);
    }

    public static void colorToHSV(int r, int g, int b, float[] hsv) {
        float h;
        float s;
        float v;
        float h2;
        float min = r < g ? r : g;
        float min2 = min < ((float) b) ? min : b;
        float max = r > g ? r : g;
        float max2 = max > ((float) b) ? max : b;
        float max3 = max2 - min2;
        if (max2 == 0.0f) {
            h = 0.0f;
            s = 0.0f;
            v = 0.0f;
        } else {
            if (max3 == 0.0f) {
                hsv[0] = 0.0f;
                hsv[1] = 0.0f;
                hsv[2] = max2 / 255.0f;
                return;
            }
            s = max3 / max2;
            if (r == max2) {
                h2 = (g - b) / max3;
            } else {
                float h3 = g;
                if (h3 == max2) {
                    h2 = ((b - r) / max3) + 2.0f;
                } else {
                    h2 = ((r - g) / max3) + 4.0f;
                }
            }
            h = h2 * 60.0f;
            if (h < 0.0f) {
                h += 360.0f;
            }
            v = max2 / 255.0f;
        }
        hsv[0] = h;
        hsv[1] = s;
        hsv[2] = v;
    }

    public static void copyHSVToHSV(float[] dst, float[] src) {
        System.arraycopy(src, 0, dst, 0, Math.min(src.length, dst.length));
    }

    public static void resetHSVBlack(float[] dst) {
        dst[0] = 0.0f;
        dst[1] = 0.0f;
        dst[2] = 0.0f;
    }

    public static void setHSV(float[] dst, float hue, float saturation, float brightness) {
        dst[0] = hue;
        dst[1] = saturation;
        dst[2] = brightness;
    }

    public static boolean checkSameHSV(float[] hsvA, float[] hsvB) {
        return hsvA[0] == hsvB[0] && hsvA[1] == hsvB[1] && hsvA[2] == hsvB[2];
    }

    public static float[] getCopiedHSV(float[] srcHSV) {
        return new float[]{srcHSV[0], srcHSV[1], srcHSV[2]};
    }

    public static float[] getHSVFromColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return hsv;
    }

    public static int HSVToColor(float[] hsv) {
        return HSVToColor(hsv, 1.0f);
    }

    public static int HSVToColor(float[] hsv, float alpha) {
        float var_r;
        float var_g;
        float var_b;
        float r;
        float var_h;
        float var_3;
        float h = hsv[0];
        float s = hsv[1];
        float v = hsv[2];
        float h2 = h * 0.0027777778f;
        if (s == 0.0f) {
            r = v * 255.0f;
            var_h = v * 255.0f;
            var_3 = v * 255.0f;
        } else {
            float var_h2 = h2 * 6.0f;
            if (var_h2 == 6.0f) {
                var_h2 = 0.0f;
            }
            int var_i = (int) Math.floor(var_h2);
            float var_1 = (1.0f - s) * v;
            float var_2 = (1.0f - ((var_h2 - var_i) * s)) * v;
            float var_32 = (1.0f - ((1.0f - (var_h2 - var_i)) * s)) * v;
            switch (var_i) {
                case 0:
                    var_r = v;
                    var_g = var_32;
                    var_b = var_1;
                    break;
                case 1:
                    var_r = var_2;
                    var_g = v;
                    var_b = var_1;
                    break;
                case 2:
                    var_r = var_1;
                    var_g = v;
                    var_b = var_32;
                    break;
                case 3:
                    var_r = var_1;
                    var_g = var_2;
                    var_b = v;
                    break;
                case 4:
                    var_r = var_32;
                    var_g = var_1;
                    var_b = v;
                    break;
                default:
                    var_r = v;
                    var_g = var_1;
                    var_b = var_2;
                    break;
            }
            float r2 = var_r * 255.0f;
            float g = var_g * 255.0f;
            r = r2;
            var_h = g;
            var_3 = var_b * 255.0f;
        }
        return argb((int) (255.0f * alpha), (int) r, (int) var_h, (int) var_3);
    }

    public static int rgb(int r, int g, int b) {
        return argb(255, r, g, b);
    }

    public static int argb(int a, int r, int g, int b) {
        int result = 0 | ((a << 24) & (-16777216));
        return result | ((r << 16) & Spanned.SPAN_PRIORITY) | ((g << 8) & 65280) | (b & 255);
    }

    public static int getAverageColor(int[] pixels) {
        int i = 0;
        if (pixels == null) {
            return 0;
        }
        int A_MASK = -16777216;
        int R_MASK = Spanned.SPAN_PRIORITY;
        int pixelNum = pixels.length;
        long r = 0;
        long g = 0;
        long b = 0;
        int length = pixels.length;
        while (i < length) {
            int pixel = pixels[i];
            r += 16711680 & pixel;
            g += 65280 & pixel;
            b += pixel & 255;
            i++;
            A_MASK = A_MASK;
            R_MASK = R_MASK;
        }
        int avgR = ((int) (r / pixelNum)) & Spanned.SPAN_PRIORITY;
        int avgG = ((int) (g / pixelNum)) & 65280;
        int avgB = ((int) (b / pixelNum)) & 255;
        return (-16777216) | avgR | avgG | avgB;
    }

    public static float[] getAvgHSVFromTwoHSV(float[] hsvA, float[] hsvB) {
        float[] hsvResult = new float[3];
        float avgHue = (hsvA[0] + hsvB[0]) * 0.5f;
        if (Math.abs(hsvA[0] - hsvB[0]) > 180.0f) {
            avgHue += 180.0f;
            if (avgHue >= 360.0f) {
                avgHue -= 360.0f;
            }
        }
        hsvResult[0] = avgHue;
        hsvResult[1] = (hsvA[1] + hsvB[1]) * 0.5f;
        hsvResult[2] = (hsvA[2] + hsvB[2]) * 0.5f;
        return hsvResult;
    }

    public static float[] interpolateHSV(float[] hsvA, float[] hsvB, float value) {
        float avgHue;
        float[] hsvResult = new float[3];
        float angleDiff = Math.abs(hsvA[0] - hsvB[0]);
        if (angleDiff <= 180.0f) {
            avgHue = hsvA[0] + ((hsvB[0] - hsvA[0]) * value);
        } else {
            float invAngleDiff = (360.0f - angleDiff) * value;
            if (hsvA[0] < hsvB[0]) {
                avgHue = hsvA[0] - invAngleDiff;
                if (avgHue < 0.0f) {
                    avgHue += 360.0f;
                }
            } else {
                float avgHue2 = hsvA[0];
                avgHue = avgHue2 + invAngleDiff;
                if (avgHue >= 360.0f) {
                    avgHue -= 360.0f;
                }
            }
        }
        hsvResult[0] = avgHue;
        hsvResult[1] = hsvA[1] + ((hsvB[1] - hsvA[1]) * value);
        hsvResult[2] = hsvA[2] + ((hsvB[2] - hsvA[2]) * value);
        return hsvResult;
    }

    public static float[] getAverageHSV(int[] pixels) {
        int avgColor = getAverageColor(pixels);
        float[] avgHSV = new float[3];
        colorToHSV(avgColor, avgHSV);
        return avgHSV;
    }

    public static double colorDistance_hue(float hueA, float hueB) {
        double[] hueVector_color_A = {Math.cos(Math.toRadians(hueA)), Math.sin(Math.toRadians(hueA))};
        double[] hueVector_color_B = {Math.cos(Math.toRadians(hueB)), Math.sin(Math.toRadians(hueB))};
        double dot = (hueVector_color_A[0] * hueVector_color_B[0]) + (hueVector_color_A[1] * hueVector_color_B[1]);
        return Math.toDegrees(Math.acos(dot));
    }

    public static double colorDistanceHueFast(float hueA, float hueB) {
        float hueVectorAX = IUXMathUtils.cos(hueA);
        float hueVectorAY = IUXMathUtils.sin(hueA);
        float hueVectorBX = IUXMathUtils.cos(hueB);
        float hueVectorBY = IUXMathUtils.sin(hueB);
        return Math.toDegrees(Math.acos((hueVectorAX * hueVectorBX) + (hueVectorAY * hueVectorBY)));
    }

    public static double colorDistance_hue(float[] hsvA, float[] hsvB) {
        return colorDistance_hue(hsvA[0], hsvB[0]);
    }

    public static double hsvDistanceSquare2(float[] hsv_a, float[] hsv_b, float[] hsv_weight) {
        double[] hueVector_color_A = {Math.cos(Math.toRadians(hsv_a[0])), Math.sin(Math.toRadians(hsv_a[0]))};
        double[] hueVector_color_B = {Math.cos(Math.toRadians(hsv_b[0])), Math.sin(Math.toRadians(hsv_b[0]))};
        double dot = (hueVector_color_A[0] * hueVector_color_B[0]) + (hueVector_color_A[1] * hueVector_color_B[1]);
        double diff_h = Math.acos(dot) / 3.141592653589793d;
        double diff_s = hsv_b[1] - hsv_a[1];
        double diff_h2 = diff_h * hsv_weight[0] * 1.0d;
        double diff_s2 = diff_s * hsv_weight[1] * 1.0d;
        double diff_b = (hsv_b[2] - hsv_a[2]) * hsv_weight[2] * 1.0d;
        return (diff_h2 * diff_h2) + (diff_s2 * diff_s2) + (diff_b * diff_b);
    }

    public static double colorDistance_hsv_square2(float[] hsv_a, float[] hsv_b, float[] hsv_weight) {
        double[] hueVector_color_A = {Math.cos(Math.toRadians(hsv_a[0])), Math.sin(Math.toRadians(hsv_a[0]))};
        double[] hueVector_color_B = {Math.cos(Math.toRadians(hsv_b[0])), Math.sin(Math.toRadians(hsv_b[0]))};
        double dot = (hueVector_color_A[0] * hueVector_color_B[0]) + (hueVector_color_A[1] * hueVector_color_B[1]);
        double diff_h = Math.acos(dot) / 3.141592653589793d;
        double diff_s = hsv_b[1] - hsv_a[1];
        double diff_h2 = diff_h * hsv_weight[0] * 1.0d;
        double diff_s2 = diff_s * hsv_weight[1] * 1.0d;
        double diff_b = (hsv_b[2] - hsv_a[2]) * hsv_weight[2] * 1.0d;
        double hsvDistanceSquare2 = (diff_h2 * diff_h2) + (diff_s2 * diff_s2) + (diff_b * diff_b);
        return hsvDistanceSquare2;
    }

    public static float getHsvDistanceSquare2FromCornSpace(float[] hsvA, float[] hsvB, float d) {
        float[] hsvPositionA = getHsvPositionFromCornSpace(hsvA, d);
        float[] hsvPositionB = getHsvPositionFromCornSpace(hsvB, d);
        return IUXMathUtils.distanceSqrt2(hsvPositionA[0] - hsvPositionB[0], hsvPositionA[1] - hsvPositionB[1], hsvPositionA[2] - hsvPositionB[2]);
    }

    public static void convertHsv2CornSpace(float[] hsv, float weight, float[] dst) {
        float v = hsv[2];
        float degree = hsv[0];
        float diameterValue = hsv[1] * v * weight;
        dst[0] = IUXMathUtils.cos(degree) * diameterValue;
        dst[1] = IUXMathUtils.sin(degree) * diameterValue;
        dst[2] = v;
    }

    public static float getHsvDistanceSquare2FromCornSpace(float[] hsvPositionA, float[] hsvPositionB) {
        return IUXMathUtils.distanceSqrt2(hsvPositionA[0] - hsvPositionB[0], hsvPositionA[1] - hsvPositionB[1], hsvPositionA[2] - hsvPositionB[2]);
    }

    public static float getHsvDistanceSquare2FromCornSpace(float[] hsvA, float[] hsvB, float d, float[] bufferA, float[] bufferB) {
        float v = hsvA[2];
        float hRadian = hsvA[0];
        float diameterValue = hsvA[1] * v * d;
        float hA = IUXMathUtils.cos(hRadian) * diameterValue;
        float sA = IUXMathUtils.sin(hRadian) * diameterValue;
        float v2 = hsvB[2];
        float v3 = hsvB[0];
        float diameterValueB = hsvB[1] * v2 * d;
        float hB = IUXMathUtils.cos(v3) * diameterValueB;
        float sB = IUXMathUtils.sin(v3) * diameterValueB;
        return IUXMathUtils.distanceSqrt2(hA - hB, sA - sB, v - v2);
    }

    public static float[] getHsvPositionFromCornSpace(float[] hsv, float sWeight) {
        float[] result = new float[3];
        calculateHsvPositionFromCornSpace(hsv, sWeight, result);
        return result;
    }

    public static void calculateHsvPositionFromCornSpace(float[] hsv, float sWeight, float[] result) {
        float v = hsv[2];
        float hRadian = hsv[0] * 0.0174532f;
        float diameterValue = hsv[1] * v * sWeight;
        result[0] = ((float) Math.cos(hRadian)) * diameterValue;
        result[1] = ((float) Math.sin(hRadian)) * diameterValue;
        result[2] = v;
    }

    public static double getHsvDistanceSquare2FromCornSpaceDouble(float[] hsvA, float[] hsvB, float d) {
        float[] hsvPositionA = getHsvPositionFromCornSpace(hsvA, d);
        float[] hsvPositionB = getHsvPositionFromCornSpace(hsvB, d);
        return IUXMathUtils.distanceSqrt2(hsvPositionA[0] - hsvPositionB[0], hsvPositionA[1] - hsvPositionB[1], hsvPositionA[2] - hsvPositionB[2]);
    }

    public static double getHsvDistanceSquare2FromCornSpaceDouble(float[] hsvA, float[] hsvB, float d, float[] bufferA, float[] bufferB) {
        calculateHsvPositionFromCornSpace(hsvA, d, bufferA);
        calculateHsvPositionFromCornSpace(hsvB, d, bufferB);
        return IUXMathUtils.distanceSqrt2(bufferA[0] - bufferB[0], bufferA[1] - bufferB[1], bufferA[2] - bufferB[2]);
    }

    public static double getHsvDistanceSquare2FromCornSpaceDoubleFast(float[] hsvA, float[] hsvB, float d) {
        float v = hsvA[2];
        float degree = hsvA[0];
        float diameterValue = hsvA[1] * v * d;
        float hA = IUXMathUtils.cos(degree) * diameterValue;
        float sA = IUXMathUtils.sin(degree) * diameterValue;
        float v2 = hsvB[2];
        float v3 = hsvB[0];
        float diameterValue2 = hsvB[1] * v2 * d;
        float hB = IUXMathUtils.cos(v3) * diameterValue2;
        float sB = IUXMathUtils.sin(v3) * diameterValue2;
        return IUXMathUtils.distanceSqrt2(hA - hB, sA - sB, v - v2);
    }

    public static double[] getHsvPositionFromCornSpaceDouble(float[] hsv, double sWeight) {
        double[] result = new double[3];
        calculateHsvPositionFromCornSpaceDouble(hsv, sWeight, result);
        return result;
    }

    public static void calculateHsvPositionFromCornSpaceDouble(float[] hsv, double sWeight, double[] result) {
        double v = hsv[2];
        double hRadian = hsv[0] * 0.0174532f;
        double diameterValue = hsv[1] * v * sWeight;
        result[0] = Math.cos(hRadian) * diameterValue;
        result[1] = Math.sin(hRadian) * diameterValue;
        result[2] = v;
    }

    public static double colorDistanceHSV(float[] hsv_a, float[] hsv_b, float[] hsv_weight) {
        double[] hueVector_color_A = {Math.cos(Math.toRadians(hsv_a[0])), Math.sin(Math.toRadians(hsv_a[0]))};
        double[] hueVector_color_B = {Math.cos(Math.toRadians(hsv_b[0])), Math.sin(Math.toRadians(hsv_b[0]))};
        double dot = (hueVector_color_A[0] * hueVector_color_B[0]) + (hueVector_color_A[1] * hueVector_color_B[1]);
        double diff_h = Math.acos(dot) / 3.141592653589793d;
        double diff_s = hsv_b[1] - hsv_a[1];
        double diff_b = hsv_b[2] - hsv_a[2];
        double invTotalLengthAdjuster = 1.0f / ((hsv_weight[0] + hsv_weight[1]) + hsv_weight[2]);
        double hsvDistance_square2 = (diff_h * hsv_weight[0] * invTotalLengthAdjuster) + (diff_s * hsv_weight[1] * invTotalLengthAdjuster) + (diff_b * hsv_weight[2] * invTotalLengthAdjuster);
        return hsvDistance_square2;
    }

    public static double colorDistance_hsv(float[] hsv_a, float[] hsv_b, float[] hsv_weight) {
        return Math.sqrt(colorDistance_hsv_square2(hsv_a, hsv_b, hsv_weight));
    }

    public static double colorDistance_rgb(int c1, int c2) {
        return Math.sqrt(Math.pow(Color.red(c1) - Color.red(c2), 2.0d) + Math.pow(Color.green(c1) - Color.green(c2), 2.0d) + Math.pow(Color.blue(c1) - Color.blue(c2), 2.0d));
    }

    public static double colorDistance_rgb_sqaure2(int c1, int c2) {
        return Math.pow(Color.red(c1) - Color.red(c2), 2.0d) + Math.pow(Color.green(c1) - Color.green(c2), 2.0d) + Math.pow(Color.blue(c1) - Color.blue(c2), 2.0d);
    }

    public static boolean checkGayScale(int color, double offsetValue) {
        float[] hsv = new float[3];
        Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color), hsv);
        return checkGayScale(hsv, offsetValue);
    }

    public static boolean checkGayScale(float[] hsv, double offsetValue) {
        return ((double) hsv[1]) <= 0.10000000149011612d + offsetValue || ((double) hsv[2]) <= 0.15000000596046448d + offsetValue;
    }

    public static boolean checkGrayScaleWithSV(float[] hsv, float offsetValue_s, float offsetValue_b) {
        return hsv[1] <= offsetValue_s || hsv[2] <= offsetValue_b;
    }

    public static boolean checkWhite(int color, double offsetValue) {
        float[] hsv = new float[3];
        Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color), hsv);
        return checkWhite(hsv, offsetValue);
    }

    public static boolean checkWhite(float[] hsv, double offsetValue) {
        return ((double) hsv[1]) <= 0.05000000074505806d + offsetValue && ((double) hsv[2]) >= 0.8999999761581421d + offsetValue;
    }

    @Deprecated
    public static float calculateGrayScaleColor(int r, int g, int b) {
        return ((r * g) + b) * 0.333333f;
    }

    @Deprecated
    public static float calculateGrayScaleColor(int color) {
        return calculateGrayScaleColor(Color.red(color), Color.green(color), Color.blue(color));
    }

    @Deprecated
    public static float caculateLuminosity(int r, int g, int b) {
        return ((r * 0.2126f) + (g * 0.7152f) + (b * 0.0722f)) * 0.003921569f;
    }

    @Deprecated
    public static float caculateLuminosity(int color) {
        return caculateLuminosity(Color.red(color), Color.green(color), Color.blue(color));
    }

    @Deprecated
    public static float caculateLuminosity2(int r, int g, int b) {
        return (r * 0.299f) + (g * 0.587f) + (b * 0.114f);
    }

    @Deprecated
    public static float caculateLuminosity2(int color) {
        return caculateLuminosity(Color.red(color), Color.green(color), Color.blue(color));
    }

    public static float calculateLuminance(int color) {
        return calculateLuminance((color >> 16) & 255, (color >> 8) & 255, color & 255);
    }

    public static float calculateLuminance(int r, int g, int b) {
        double nr = r * 0.00392156862745098d;
        double ng = g * 0.00392156862745098d;
        double nb = b * 0.00392156862745098d;
        return (float) ((0.2126d * (nr < 0.04045d ? nr / 12.92d : Math.pow((nr + 0.055d) / 1.055d, 2.4d))) + (0.7152d * (ng < 0.04045d ? ng / 12.92d : Math.pow((ng + 0.055d) / 1.055d, 2.4d))) + (0.0722d * (nb < 0.04045d ? nb / 12.92d : Math.pow((nb + 0.055d) / 1.055d, 2.4d))));
    }

    public static float getGammaCorrectedValue(float value) {
        return (float) (value < 0.04045f ? value / 12.92d : Math.pow((value + 0.055d) / 1.055d, 2.4d));
    }

    public static double getGammaCorrectionFromValue(double val) {
        return val < 0.04045d ? val / 12.92d : Math.pow((0.055d + val) / 1.055d, 2.4d);
    }

    public static float calculateLuminanceInLinearSpace(int r, int g, int b) {
        return ((r * 0.2126f) + (g * 0.7152f) + (b * 0.0722f)) * 0.003921569f;
    }

    public static float calculateLuminanceInLinearSpace(int color) {
        return calculateLuminance(Color.red(color), Color.green(color), Color.blue(color));
    }

    public static float convertPixelsToDp(float px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160.0f);
        return Math.round(dp);
    }

    public static int convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = (metrics.densityDpi / 160.0f) * dp;
        return Math.round(px);
    }

    public static int combinAlphaIntoIntColor(int alpha, int color) {
        return ((alpha << 24) & (-16777216)) | (16777215 & color);
    }

    public static int getInterpolatedColorHSVBased(float[] hsvA, float[] hsvB, float value) {
        float[] resultHSV = getInterpolatedHSV(hsvA, hsvB, value);
        return Color.HSVToColor(resultHSV);
    }

    public static float[] getInterpolatedHSV(float[] hsvA, float[] hsvB, float value) {
        float[] resultHSV = new float[3];
        if (hsvA[0] == hsvB[0]) {
            resultHSV[0] = hsvA[0];
        } else {
            double[] hueVector_color_A = {IUXMathUtils.cos(hsvA[0]), IUXMathUtils.sin(hsvA[0])};
            double[] hueVector_color_B = {IUXMathUtils.cos(hsvB[0]), IUXMathUtils.sin(hsvB[0])};
            float shiftedAngleA = (hsvA[0] + 90.0f) % 360.0f;
            boolean cw = (hueVector_color_B[0] * ((double) IUXMathUtils.cos(shiftedAngleA))) + (hueVector_color_B[1] * ((double) IUXMathUtils.sin(shiftedAngleA))) >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            double dot = (hueVector_color_A[0] * hueVector_color_B[0]) + (hueVector_color_A[1] * hueVector_color_B[1]);
            double hDiff = Math.toDegrees(Math.acos(dot)) * value;
            if (cw) {
                resultHSV[0] = (float) (hsvA[0] + hDiff);
                if (resultHSV[0] > 360.0f) {
                    resultHSV[0] = resultHSV[0] - 360.0f;
                }
            } else {
                resultHSV[0] = (float) (hsvA[0] - hDiff);
                if (resultHSV[0] < 0.0f) {
                    resultHSV[0] = resultHSV[0] + 360.0f;
                }
            }
        }
        resultHSV[1] = IUXMathUtils.lerp(value, hsvA[1], hsvB[1]);
        resultHSV[2] = IUXMathUtils.lerp(value, hsvA[2], hsvB[2]);
        return resultHSV;
    }

    public static float getHumanEyeBasedHueNormalizedDistance(float hue) {
        float adjustedHue;
        float hueScopeLength_lc = (180.0f - 72.0f) * 1.9f;
        float hueScopeLength_cb = (252.0f - 180.0f) * 1.35f;
        if (hue > 252.0f) {
            adjustedHue = (hue - 252.0f) + 72.0f + hueScopeLength_lc + hueScopeLength_cb;
        } else {
            adjustedHue = hue > 180.0f ? (((hue - 180.0f) / (252.0f - 180.0f)) * hueScopeLength_cb) + 72.0f + hueScopeLength_lc : hue > 72.0f ? (((hue - 72.0f) / (180.0f - 72.0f)) * hueScopeLength_lc) + 72.0f : hue;
        }
        float maxHue = (360.0f - 252.0f) + 72.0f + hueScopeLength_lc + hueScopeLength_cb;
        return adjustedHue / maxHue;
    }

    public static boolean checkSameHSV(float[] hsvA, float[] hsvB, float distancThresholdRatio) {
        float[] clusterCalcWeight;
        float squaredThresholdDistance;
        boolean isGrayScaleA = checkGrayScaleWithSV(hsvA, 0.12f, 0.25f);
        boolean isGrayScaleB = checkGrayScaleWithSV(hsvB, 0.12f, 0.25f);
        if (isGrayScaleA != isGrayScaleB) {
            return false;
        }
        float[] clusterCalcWeightColor = {1.0f, 0.35f, 0.65f};
        float squaredThresholdDistanceColor = 1.7320508f * distancThresholdRatio;
        float squaredThresholdDistanceColor2 = squaredThresholdDistanceColor * squaredThresholdDistanceColor;
        float[] clusterCalcWeightMono = {0.0f, 0.0f, 1.0f};
        float squaredThresholdDistanceMono = 1.0f * distancThresholdRatio;
        float squaredThresholdDistanceMono2 = squaredThresholdDistanceMono * squaredThresholdDistanceMono;
        if (isGrayScaleA) {
            clusterCalcWeight = clusterCalcWeightMono;
            squaredThresholdDistance = squaredThresholdDistanceMono2;
        } else {
            clusterCalcWeight = clusterCalcWeightColor;
            squaredThresholdDistance = squaredThresholdDistanceColor2;
        }
        if (colorDistance_hsv_square2(hsvA, hsvB, clusterCalcWeight) >= squaredThresholdDistance) {
            return false;
        }
        return true;
    }

    public static int[] getRGBFromColor(int color) {
        return new int[]{Color.red(color), Color.green(color), Color.blue(color), Color.alpha(color)};
    }

    public static int multipleColorValue(int color, float value) {
        float value2 = Math.min(value, 1.0f);
        return Color.rgb((int) (Color.red(color) * value2), (int) (Color.green(color) * value2), (int) (Color.blue(color) * value2));
    }

    public static int addColorColor(int colorA, int colorB) {
        return Color.rgb(Math.min(Color.red(colorA) + Color.red(colorB), 255), Math.min(Color.green(colorA) + Color.green(colorB), 255), Math.min(Color.blue(colorA) + Color.blue(colorB), 255));
    }

    public static float getRedRatio(int color) {
        return Color.red(color) * 0.003921569f;
    }

    public static float getGreenRatio(int color) {
        return Color.green(color) * 0.003921569f;
    }

    public static float getBlueRatio(int color) {
        return Color.blue(color) * 0.003921569f;
    }

    public static float getAlphaRatio(int color) {
        return Color.alpha(color) * 0.003921569f;
    }

    public static int getColorFromLuminance(float luminance) {
        int iLuma = (int) (255.0f * luminance);
        return Color.rgb(iLuma, iLuma, iLuma);
    }

    public static int getColorFromChannelValue(int aChannel) {
        return Color.rgb(aChannel, aChannel, aChannel);
    }

    public static float[] getInverseHSV(float[] hsv) {
        float inverseHue = ((int) (hsv[0] + 180.0f)) % 360;
        return new float[]{inverseHue, hsv[1], hsv[2]};
    }

    Bitmap getGradation(final int colorA, final int colorB, final int gradient_width, final int gradient_hegith, final float angle) {
        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() { // from class: com.samsung.android.wallpaper.legibilitycolors.utils.IUXColorUtils.1
            @Override // android.graphics.drawable.ShapeDrawable.ShaderFactory
            public Shader resize(int width, int height) {
                LinearGradient lg = new LinearGradient(0.0f, 0.0f, 0.0f, gradient_hegith, new int[]{colorA, colorB}, new float[]{0.0f, 1.0f}, Shader.TileMode.MIRROR);
                Matrix rotMat = new Matrix();
                rotMat.setRotate(angle, gradient_width * 0.5f, gradient_hegith * 0.5f);
                Matrix transMat = new Matrix();
                Matrix resultMat = new Matrix();
                resultMat.setConcat(rotMat, transMat);
                lg.setLocalMatrix(resultMat);
                return lg;
            }
        };
        PaintDrawable gradationDrawable = new PaintDrawable();
        gradationDrawable.setShape(new RectShape());
        gradationDrawable.setShaderFactory(sf);
        Bitmap gradientBitmap = Bitmap.createBitmap(gradient_width, gradient_hegith, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(gradientBitmap);
        gradationDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        gradationDrawable.draw(canvas);
        return gradientBitmap;
    }
}
