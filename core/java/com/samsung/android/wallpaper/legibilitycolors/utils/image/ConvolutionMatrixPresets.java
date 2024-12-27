package com.samsung.android.wallpaper.legibilitycolors.utils.image;

import android.hardware.scontext.SContextConstants;
import android.util.SparseArray;

/* loaded from: classes6.dex */
public class ConvolutionMatrixPresets {
    public static double[][] HIGHPASS_3_FILTER = {
        new double[] {-0.125d, -0.125d, -0.125d},
        new double[] {-0.125d, 1.0d, -0.125d},
        new double[] {-0.125d, -0.125d, -0.125d}
    };
    public static double[][] HIGHPASS_5_FILTER = {
        new double[] {
            SContextConstants.ENVIRONMENT_VALUE_UNKNOWN,
            -0.03571428571428571d,
            -0.03571428571428571d,
            -0.03571428571428571d,
            SContextConstants.ENVIRONMENT_VALUE_UNKNOWN
        },
        new double[] {
            -0.03571428571428571d,
            0.10714285714285714d,
            -0.14285714285714285d,
            0.10714285714285714d,
            -0.03571428571428571d
        },
        new double[] {
            -0.03571428571428571d,
            -0.14285714285714285d,
            0.5714285714285714d,
            -0.14285714285714285d,
            -0.03571428571428571d
        },
        new double[] {
            -0.03571428571428571d,
            0.10714285714285714d,
            -0.14285714285714285d,
            0.10714285714285714d,
            -0.03571428571428571d
        },
        new double[] {
            SContextConstants.ENVIRONMENT_VALUE_UNKNOWN,
            -0.03571428571428571d,
            -0.03571428571428571d,
            -0.03571428571428571d,
            SContextConstants.ENVIRONMENT_VALUE_UNKNOWN
        }
    };
    public static SparseArray<double[][]> HIGHPASS_FILTER_CACHE = new SparseArray<>();

    public static double[][] highPassFilter(int size) {
        double[][] filter = HIGHPASS_FILTER_CACHE.get(size);
        if (filter != null) {
            return filter;
        }
        int half = size / 2;
        double[][] kernel = new double[size][];
        double negativeKernelSum = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        double positiveKernelSum = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        double maxDistance = Math.sqrt(half * half * 2);
        for (int i = 0; i < size; i++) {
            kernel[i] = new double[size];
            double iDistance = i - half;
            int j = 0;
            while (j < size) {
                double[][] filter2 = filter;
                int half2 = half;
                double jDistance = j - half;
                double distanceWeight =
                        Math.sqrt((jDistance * jDistance) + (iDistance * iDistance)) / maxDistance;
                double kernelWeight =
                        Math.sin((distanceWeight + (0.5d / 1.4f)) * 3.141592653589793d * 1.4f);
                kernel[i][j] = kernelWeight;
                if (kernelWeight < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                    negativeKernelSum += kernelWeight;
                } else {
                    positiveKernelSum += kernelWeight;
                }
                j++;
                filter = filter2;
                half = half2;
            }
        }
        double kernelAdjustingValue = Math.abs(positiveKernelSum / negativeKernelSum);
        for (int i2 = 0; i2 < size; i2++) {
            double[] kernal_i = kernel[i2];
            for (int j2 = 0; j2 < size; j2++) {
                if (kernal_i[j2] < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                    kernal_i[j2] = kernal_i[j2] * kernelAdjustingValue;
                }
                kernal_i[j2] = kernal_i[j2] / positiveKernelSum;
            }
        }
        HIGHPASS_FILTER_CACHE.put(size, kernel);
        return kernel;
    }

    public void setAll(double[][] kernel, double value) {
        for (double[] kernel_x : kernel) {
            int len = kernel_x.length;
            for (int i = 0; i < len; i++) {
                kernel_x[i] = value;
            }
        }
    }
}
