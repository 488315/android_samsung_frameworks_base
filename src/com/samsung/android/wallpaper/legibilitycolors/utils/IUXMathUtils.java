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

    public static float computeAverageValue(float f, int i, float f2) {
        return ((f * i) + f2) / (i + 1);
    }

    public static double distanceSqrt2(double d, double d2, double d3) {
        return (d * d) + (d2 * d2) + (d3 * d3);
    }

    public static float distanceSqrt2(float f, float f2, float f3) {
        return (f * f) + (f2 * f2) + (f3 * f3);
    }

    public static boolean isInRange(float f, float f2, float f3) {
        return f3 >= f && f3 <= f2;
    }

    public static boolean isZero(float f, float f2) {
        return f < f2 && f > (-f2);
    }

    public static float lerp(float f, float f2, float f3) {
        return f2 + ((f3 - f2) * f);
    }

    public static int lerp(float f, int i, int i2) {
        return i + ((int) ((i2 - i) * f));
    }

    public static short lerp(float f, short s, short s2) {
        return (short) (s + ((s2 - s) * f));
    }

    public static float max(float f, float f2) {
        return f > f2 ? f : f2;
    }

    public static float min(float f, float f2) {
        return f < f2 ? f : f2;
    }

    public static float nearZero(float f, float f2) {
        if (f >= f2 || f <= (-f2)) {
            return f;
        }
        return 0.0f;
    }

    public static float range(float f, float f2, float f3) {
        if (f < f3) {
            f = f3;
        }
        return f2 <= f ? f2 : f;
    }

    public static float rangeRevolving(float f, float f2, float f3) {
        if (f3 > f2) {
            f3 %= f2;
        }
        return f3 < f ? (f3 % f2) + f2 : f3;
    }

    public double distanceSqrt2(double d, double d2) {
        return (d * d) + (d2 * d2);
    }

    public float distanceSqrt2(float f, float f2) {
        return (f * f) + (f2 * f2);
    }

    static {
        prepare();
    }

    public static IUXMathUtils getInstance() {
        if (mMathUtils == null) {
            mMathUtils = new IUXMathUtils();
        }
        return mMathUtils;
    }

    public static int invPow2(int i) {
        int i2 = 0;
        while (true) {
            i /= 2;
            if (i < 1) {
                return i2;
            }
            i2++;
        }
    }

    public static float findMaxNumber(float[] fArr, int[] iArr) {
        float f = fArr[0];
        int length = fArr.length;
        int i = 0;
        for (int i2 = 1; i2 < length; i2++) {
            float f2 = fArr[i2];
            if (f < f2) {
                i = i2;
                f = f2;
            }
        }
        if (iArr != null) {
            iArr[0] = i;
        }
        return f;
    }

    public static float findMinNumber(float[] fArr, int[] iArr) {
        float f = fArr[0];
        int i = 0;
        for (int i2 = 1; i2 < fArr.length; i2++) {
            float f2 = fArr[i2];
            if (f > f2) {
                i = i2;
                f = f2;
            }
        }
        if (iArr != null) {
            iArr[0] = i;
        }
        return f;
    }

    public static float getNormalizedValueInRange(float f, float f2, float f3) {
        return Math.min(Math.max((f - f2) / (f3 - f2), 0.0f), 1.0f);
    }

    public static double distance(double d, double d2, double d3) {
        return Math.sqrt((d * d) + (d2 * d2) + (d3 * d3));
    }

    public static float distance(float f, float f2, float f3) {
        return (float) Math.sqrt((f * f) + (f2 * f2) + (f3 * f3));
    }

    public static float gaussFunc(float f, float f2) {
        return (float) Math.exp(-((f2 * f2) / ((f * f) * 2.0f)));
    }

    public static int lerpColor(float f, int i, int i2) {
        return Color.argb(lerp(f, Color.alpha(i), Color.alpha(i2)), lerp(f, Color.red(i), Color.red(i2)), lerp(f, Color.green(i), Color.green(i2)), lerp(f, Color.blue(i), Color.blue(i2)));
    }

    public static int getRangedVal() {
        return sRandom.nextBoolean() ? -1 : 1;
    }

    public static float getRangedVal(float f, float f2) {
        return f + (sRandom.nextFloat() * (f2 - f));
    }

    public static float getRatioFromRange(float f, float f2, float f3) {
        return (Math.min(Math.max(f2, f), f3) - f2) / (f3 - f2);
    }

    public static float trimValue(float f, float f2, float f3) {
        return Math.max(Math.min(f, f2), f3);
    }

    public static int trimValue(int i, int i2, int i3) {
        return Math.max(Math.min(i, i2), i3);
    }

    public static double getGaussianRangedVal(double d, double d2) {
        return d + (sRandom.nextGaussian() * (d2 - d));
    }

    public static int getRangedVal(int i, int i2) {
        if (i2 < i) {
            return i - (Math.abs(sRandom.nextInt(Integer.MAX_VALUE)) % (i - i2));
        }
        return i2 > i ? i + (Math.abs(sRandom.nextInt(Integer.MAX_VALUE)) % (i2 - i)) : i;
    }

    public double distance(double d, double d2) {
        return Math.sqrt((d * d) + (d2 * d2));
    }

    public float distance(float f, float f2) {
        return (float) Math.sqrt((f * f) + (f2 * f2));
    }

    private static void prepare() {
        for (int i = 0; i < 360000; i++) {
            double d = (i / 1000.0f) * 0.0174532f;
            sSinCache[i] = (float) Math.sin(d);
            sCosCache[i] = (float) Math.cos(d);
        }
    }

    public static float sin(float f) {
        return sSinCache[(int) (f * 1000.0f)];
    }

    public static float sin(int i) {
        return sSinCache[i * 1000];
    }

    public static float cos(float f) {
        return sCosCache[(int) (f * 1000.0f)];
    }

    public static float cos(int i) {
        return sCosCache[i * 1000];
    }
}
