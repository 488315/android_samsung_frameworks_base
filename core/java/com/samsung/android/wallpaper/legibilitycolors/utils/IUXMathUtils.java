package com.samsung.android.wallpaper.legibilitycolors.utils;

import android.graphics.Color;

import java.util.Random;

/* loaded from: classes6.dex */
public class IUXMathUtils {
    public static final float CHAR_2_NOMALIZED = 0.003921569f;
    public static final float DEG_2_RADIAN = 0.0174532f;
    public static final float PI = 3.1415927f;
    public static final float PI_2 = 6.2831855f;
    public static final float PI_4 = 12.566371f;
    public static final float PI_HALF = 1.5707964f;
    public static final float RADIAN_2_DEG = 57.29578f;
    public static final float TRI_PRECISION = 1000.0f;
    public static final int TRI_PRECISION_INT = 1000;
    public static final float fZERO = 0.0f;
    public static final Random sRandom = new Random();
    public static IUXMathUtils mMathUtils = null;
    private static final float[] sSinCache = new float[360000];
    private static final float[] sCosCache = new float[360000];

    static {
        prepare();
    }

    public static IUXMathUtils getInstance() {
        if (mMathUtils == null) {
            mMathUtils = new IUXMathUtils();
        }
        return mMathUtils;
    }

    public static float max(float fA, float fB) {
        if (fA > fB) {
            return fA;
        }
        return fB;
    }

    public static float min(float fA, float fB) {
        if (fA < fB) {
            return fA;
        }
        return fB;
    }

    public static float nearZero(float velocity, float epsilon) {
        if (velocity < epsilon && velocity > (-epsilon)) {
            return 0.0f;
        }
        return velocity;
    }

    public static boolean isZero(float velocity, float epsilon) {
        return velocity < epsilon && velocity > (-epsilon);
    }

    public static int invPow2(int val) {
        int count = 0;
        while (true) {
            int i = val / 2;
            val = i;
            if (i >= 1) {
                count++;
            } else {
                return count;
            }
        }
    }

    public static float rangeRevolving(float min, float max, float fVal) {
        if (fVal > max) {
            fVal %= max;
        }
        if (fVal < min) {
            return (fVal % max) + max;
        }
        return fVal;
    }

    public static float range(float min, float max, float fVal) {
        float fVal2 = min >= fVal ? min : fVal;
        return max <= fVal2 ? max : fVal2;
    }

    public static boolean isInRange(float min, float max, float fVal) {
        return fVal >= min && fVal <= max;
    }

    public static float findMaxNumber(float[] aFloat, int[] aIndex) {
        int maxIndex = 0;
        float maxNum = aFloat[0];
        int size = aFloat.length;
        for (int i = 1; i < size; i++) {
            float number = aFloat[i];
            if (maxNum < number) {
                maxIndex = i;
                maxNum = number;
            }
        }
        if (aIndex != null) {
            aIndex[0] = maxIndex;
        }
        return maxNum;
    }

    public static float findMinNumber(float[] aFloat, int[] aIndex) {
        int minIndex = 0;
        float minNum = aFloat[0];
        for (int i = 1; i < aFloat.length; i++) {
            float number = aFloat[i];
            if (minNum > number) {
                minIndex = i;
                minNum = number;
            }
        }
        if (aIndex != null) {
            aIndex[0] = minIndex;
        }
        return minNum;
    }

    public static float getNormalizedValueInRange(float value, float min, float max) {
        return Math.min(Math.max((value - min) / (max - min), 0.0f), 1.0f);
    }

    public static double distance(double x, double y, double z) {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public static float distance(float x, float y, float z) {
        return (float) Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public static float distanceSqrt2(float x, float y, float z) {
        return (x * x) + (y * y) + (z * z);
    }

    public static double distanceSqrt2(double x, double y, double z) {
        return (x * x) + (y * y) + (z * z);
    }

    public static float gaussFunc(float sigma, float x) {
        return (float) Math.exp(-((x * x) / ((sigma * sigma) * 2.0f)));
    }

    public static short lerp(float t, short a, short b) {
        return (short) (a + ((b - a) * t));
    }

    public static float lerp(float t, float a, float b) {
        return ((b - a) * t) + a;
    }

    public static int lerp(float t, int a, int b) {
        return ((int) ((b - a) * t)) + a;
    }

    public static int lerpColor(float t, int a, int b) {
        return Color.argb(
                lerp(t, Color.alpha(a), Color.alpha(b)),
                lerp(t, Color.red(a), Color.red(b)),
                lerp(t, Color.green(a), Color.green(b)),
                lerp(t, Color.blue(a), Color.blue(b)));
    }

    public static int getRangedVal() {
        return sRandom.nextBoolean() ? -1 : 1;
    }

    public static float getRangedVal(float min, float max) {
        return (sRandom.nextFloat() * (max - min)) + min;
    }

    public static float getRatioFromRange(float val, float min, float max) {
        return (Math.min(Math.max(min, val), max) - min) / (max - min);
    }

    public static float trimValue(float val, float max, float min) {
        return Math.max(Math.min(val, max), min);
    }

    public static int trimValue(int val, int max, int min) {
        return Math.max(Math.min(val, max), min);
    }

    public static double getGaussianRangedVal(double min, double max) {
        return (sRandom.nextGaussian() * (max - min)) + min;
    }

    public static int getRangedVal(int min, int max) {
        if (max < min) {
            return min - (Math.abs(sRandom.nextInt(Integer.MAX_VALUE)) % (min - max));
        }
        if (max > min) {
            return (Math.abs(sRandom.nextInt(Integer.MAX_VALUE)) % (max - min)) + min;
        }
        return min;
    }

    public static float computeAverageValue(float prevAvg, int prevN, float newData) {
        return ((prevN * prevAvg) + newData) / (prevN + 1);
    }

    public double distance(double x, double y) {
        return Math.sqrt((x * x) + (y * y));
    }

    public float distance(float x, float y) {
        return (float) Math.sqrt((x * x) + (y * y));
    }

    public float distanceSqrt2(float x, float y) {
        return (x * x) + (y * y);
    }

    public double distanceSqrt2(double x, double y) {
        return (x * x) + (y * y);
    }

    private static void prepare() {
        for (int i = 0; i < 360000; i++) {
            sSinCache[i] = (float) Math.sin((i / 1000.0f) * 0.0174532f);
            sCosCache[i] = (float) Math.cos((i / 1000.0f) * 0.0174532f);
        }
    }

    public static float sin(float degree) {
        return sSinCache[(int) (1000.0f * degree)];
    }

    public static float sin(int degree) {
        return sSinCache[degree * 1000];
    }

    public static float cos(float degree) {
        return sCosCache[(int) (1000.0f * degree)];
    }

    public static float cos(int degree) {
        return sCosCache[degree * 1000];
    }
}
