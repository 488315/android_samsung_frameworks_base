package com.google.ux.material.libmonet.utils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MathUtils {
    private MathUtils() {
    }

    public static double[] matrixMultiply(double[] dArr, double[][] dArr2) {
        double d = dArr[0];
        double[] dArr3 = dArr2[0];
        double d2 = dArr3[0] * d;
        double d3 = dArr[1];
        double d4 = (dArr3[1] * d3) + d2;
        double d5 = dArr[2];
        double d6 = (dArr3[2] * d5) + d4;
        double[] dArr4 = dArr2[1];
        double d7 = (dArr4[2] * d5) + (dArr4[1] * d3) + (dArr4[0] * d);
        double[] dArr5 = dArr2[2];
        return new double[]{d6, d7, (d5 * dArr5[2]) + (d3 * dArr5[1]) + (d * dArr5[0])};
    }

    public static double sanitizeDegreesDouble(double d) {
        double d2 = d % 360.0d;
        return d2 < 0.0d ? d2 + 360.0d : d2;
    }
}
