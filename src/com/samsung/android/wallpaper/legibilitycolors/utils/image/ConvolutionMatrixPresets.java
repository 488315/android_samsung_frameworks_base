package com.samsung.android.wallpaper.legibilitycolors.utils.image;

import android.hardware.scontext.SContextConstants;
import android.util.SparseArray;

/* loaded from: classes6.dex */
public class ConvolutionMatrixPresets {
    public static double[][] HIGHPASS_3_FILTER = {new double[]{-0.125d, -0.125d, -0.125d}, new double[]{-0.125d, 1.0d, -0.125d}, new double[]{-0.125d, -0.125d, -0.125d}};
    public static double[][] HIGHPASS_5_FILTER = {new double[]{SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, -0.03571428571428571d, -0.03571428571428571d, -0.03571428571428571d, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN}, new double[]{-0.03571428571428571d, 0.10714285714285714d, -0.14285714285714285d, 0.10714285714285714d, -0.03571428571428571d}, new double[]{-0.03571428571428571d, -0.14285714285714285d, 0.5714285714285714d, -0.14285714285714285d, -0.03571428571428571d}, new double[]{-0.03571428571428571d, 0.10714285714285714d, -0.14285714285714285d, 0.10714285714285714d, -0.03571428571428571d}, new double[]{SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, -0.03571428571428571d, -0.03571428571428571d, -0.03571428571428571d, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN}};
    public static SparseArray<double[][]> HIGHPASS_FILTER_CACHE = new SparseArray<>();

    public static double[][] highPassFilter(int i) {
        double[][] dArr = HIGHPASS_FILTER_CACHE.get(i);
        if (dArr != null) {
            return dArr;
        }
        int i2 = i / 2;
        double[][] dArr2 = new double[i][];
        double dSqrt = Math.sqrt(i2 * i2 * 2);
        double d = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        double d2 = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        for (int i3 = 0; i3 < i; i3++) {
            dArr2[i3] = new double[i];
            double d3 = i3 - i2;
            int i4 = 0;
            while (i4 < i) {
                double d4 = i4 - i2;
                double dSqrt2 = Math.sqrt((d4 * d4) + (d3 * d3)) / dSqrt;
                double d5 = dSqrt;
                double d6 = 1.4f;
                double dSin = Math.sin((dSqrt2 + (0.5d / d6)) * 3.141592653589793d * d6);
                dArr2[i3][i4] = dSin;
                if (dSin < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                    d2 += dSin;
                } else {
                    d += dSin;
                }
                i4++;
                dSqrt = d5;
            }
        }
        double dAbs = Math.abs(d / d2);
        for (int i5 = 0; i5 < i; i5++) {
            double[] dArr3 = dArr2[i5];
            for (int i6 = 0; i6 < i; i6++) {
                double d7 = dArr3[i6];
                if (d7 < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                    dArr3[i6] = d7 * dAbs;
                }
                dArr3[i6] = dArr3[i6] / d;
            }
        }
        HIGHPASS_FILTER_CACHE.put(i, dArr2);
        return dArr2;
    }

    public void setAll(double[][] dArr, double d) {
        for (double[] dArr2 : dArr) {
            int length = dArr2.length;
            for (int i = 0; i < length; i++) {
                dArr2[i] = d;
            }
        }
    }
}
