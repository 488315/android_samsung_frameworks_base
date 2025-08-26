package com.google.ux.material.libmonet.hct;

import com.google.ux.material.libmonet.utils.ColorUtils;
import com.google.ux.material.libmonet.utils.MathUtils;

/* loaded from: classes4.dex */
public final class Hct {
    public final int argb;
    public final double chroma;
    public final double hue;
    public final double tone;

    private Hct(int i) {
        this.argb = i;
        Cam16 cam16FromInt = Cam16.fromInt(i);
        this.hue = cam16FromInt.hue;
        this.chroma = cam16FromInt.chroma;
        this.tone = (ColorUtils.labF(MathUtils.matrixMultiply(new double[]{ColorUtils.linearized((i >> 16) & 255), ColorUtils.linearized((i >> 8) & 255), ColorUtils.linearized(i & 255)}, ColorUtils.SRGB_TO_XYZ)[1] / 100.0d) * 116.0d) - 16.0d;
    }

    public static Hct from(double d, double d2, double d3) {
        int iDelinearized;
        double[] dArr;
        int i;
        double d4;
        int i2;
        int i3;
        int iCeil;
        double dFloor;
        double[] dArr2;
        double[] dArr3;
        int i4;
        double[] dArrMatrixMultiply;
        if (d2 < 1.0E-4d || d3 < 1.0E-4d || d3 > 99.9999d) {
            int iDelinearized2 = ColorUtils.delinearized(ColorUtils.yFromLstar(d3)) & 255;
            iDelinearized = iDelinearized2 | (iDelinearized2 << 16) | (-16777216) | (iDelinearized2 << 8);
        } else {
            double dSanitizeDegreesDouble = (MathUtils.sanitizeDegreesDouble(d) / 180.0d) * 3.141592653589793d;
            double dYFromLstar = ColorUtils.yFromLstar(d3);
            double dSqrt = Math.sqrt(dYFromLstar) * 11.0d;
            ViewingConditions viewingConditions = ViewingConditions.DEFAULT;
            double dPow = 1.0d / Math.pow(1.64d - Math.pow(0.29d, viewingConditions.n), 0.73d);
            double dCos = (Math.cos(dSanitizeDegreesDouble + 2.0d) + 3.8d) * 0.25d * 3846.153846153846d * viewingConditions.nc * viewingConditions.ncb;
            double dSin = Math.sin(dSanitizeDegreesDouble);
            double dCos2 = Math.cos(dSanitizeDegreesDouble);
            int i5 = 0;
            while (true) {
                dArr = HctSolver.Y_FROM_LINRGB;
                i = 8;
                if (i5 >= 5) {
                    d4 = dSanitizeDegreesDouble;
                    i2 = 1;
                    break;
                }
                d4 = dSanitizeDegreesDouble;
                double d5 = dSqrt / 100.0d;
                i2 = 1;
                double dPow2 = Math.pow(((d2 == 0.0d || dSqrt == 0.0d) ? 0.0d : d2 / Math.sqrt(d5)) * dPow, 1.1111111111111112d);
                double dPow3 = (Math.pow(d5, (1.0d / viewingConditions.c) / viewingConditions.z) * viewingConditions.aw) / viewingConditions.nbb;
                double d6 = (((0.305d + dPow3) * 23.0d) * dPow2) / (((108.0d * dPow2) * dSin) + (((dPow2 * 11.0d) * dCos2) + (23.0d * dCos)));
                double d7 = d6 * dCos2;
                double d8 = d6 * dSin;
                double d9 = dPow3 * 460.0d;
                dArrMatrixMultiply = MathUtils.matrixMultiply(new double[]{HctSolver.inverseChromaticAdaptation(((288.0d * d8) + ((451.0d * d7) + d9)) / 1403.0d), HctSolver.inverseChromaticAdaptation(((d9 - (891.0d * d7)) - (261.0d * d8)) / 1403.0d), HctSolver.inverseChromaticAdaptation(((d9 - (d7 * 220.0d)) - (d8 * 6300.0d)) / 1403.0d)}, HctSolver.LINRGB_FROM_SCALED_DISCOUNT);
                double d10 = dArrMatrixMultiply[0];
                if (d10 < 0.0d) {
                    break;
                }
                double d11 = dArrMatrixMultiply[1];
                if (d11 < 0.0d) {
                    break;
                }
                double d12 = dArrMatrixMultiply[2];
                if (d12 < 0.0d) {
                    break;
                }
                double d13 = (dArr[2] * d12) + (dArr[1] * d11) + (dArr[0] * d10);
                if (d13 <= 0.0d) {
                    break;
                }
                if (i5 == 4) {
                    break;
                }
                double d14 = d13 - dYFromLstar;
                if (Math.abs(d14) < 0.002d) {
                    break;
                }
                dSqrt -= (d14 * dSqrt) / (d13 * 2.0d);
                i5++;
                dSanitizeDegreesDouble = d4;
            }
            double d15 = dArrMatrixMultiply[0];
            iDelinearized = (d15 > 100.01d || dArrMatrixMultiply[1] > 100.01d || dArrMatrixMultiply[2] > 100.01d) ? 0 : (ColorUtils.delinearized(dArrMatrixMultiply[2]) & 255) | ((ColorUtils.delinearized(d15) & 255) << 16) | (-16777216) | ((ColorUtils.delinearized(dArrMatrixMultiply[1]) & 255) << 8);
            if (iDelinearized == 0) {
                double[] dArr4 = new double[3];
                dArr4[0] = -1.0d;
                dArr4[i2] = -1.0d;
                dArr4[2] = -1.0d;
                int i6 = i2;
                double[] dArr5 = dArr4;
                double[] dArr6 = dArr5;
                boolean z = false;
                int i7 = 0;
                double d16 = 0.0d;
                double d17 = 0.0d;
                while (i7 < 12) {
                    double d18 = dArr[0];
                    double d19 = dArr[i2];
                    double d20 = dArr[2];
                    double d21 = i7 % 4 <= i2 ? 0.0d : 100.0d;
                    double d22 = i7 % 2 == 0 ? 0.0d : 100.0d;
                    if (i7 < 4) {
                        double d23 = ((dYFromLstar - (d19 * d21)) - (d20 * d22)) / d18;
                        dArr2 = HctSolver.isBounded(d23) ? new double[]{d23, d21, d22} : new double[]{-1.0d, -1.0d, -1.0d};
                    } else if (i7 < i) {
                        double d24 = ((dYFromLstar - (d18 * d22)) - (d20 * d21)) / d19;
                        if (HctSolver.isBounded(d24)) {
                            dArr3 = new double[]{d22, d24, d21};
                            dArr2 = dArr3;
                        } else {
                            dArr2 = new double[]{-1.0d, -1.0d, -1.0d};
                        }
                    } else {
                        double d25 = ((dYFromLstar - (d18 * d21)) - (d19 * d22)) / d20;
                        if (HctSolver.isBounded(d25)) {
                            dArr3 = new double[]{d21, d22, d25};
                            dArr2 = dArr3;
                        } else {
                            dArr2 = new double[]{-1.0d, -1.0d, -1.0d};
                        }
                    }
                    if (dArr2[0] < 0.0d) {
                        i4 = 1;
                    } else {
                        double dHueOf = HctSolver.hueOf(dArr2);
                        if (z) {
                            if (i6 != 0 || HctSolver.areInCyclicOrder(d16, dHueOf, d17)) {
                                double d26 = d4;
                                double d27 = d16;
                                d16 = d27;
                                d4 = d26;
                                if (HctSolver.areInCyclicOrder(d27, d26, dHueOf)) {
                                    dArr6 = dArr2;
                                    i6 = 0;
                                    d17 = dHueOf;
                                } else {
                                    dArr5 = dArr2;
                                    i6 = 0;
                                    d16 = dHueOf;
                                }
                            }
                            i4 = 1;
                        } else {
                            dArr5 = dArr2;
                            dArr6 = dArr5;
                            d16 = dHueOf;
                            d17 = d16;
                            i4 = 1;
                            z = true;
                        }
                    }
                    i7 += i4;
                    i2 = i4;
                    i = 8;
                }
                double[][] dArr7 = {dArr5, dArr6};
                double[] dArr8 = dArr7[0];
                double dHueOf2 = HctSolver.hueOf(dArr8);
                double[] dArr9 = dArr7[i2];
                int i8 = 0;
                while (i8 < 3) {
                    double d28 = dArr8[i8];
                    double d29 = dArr9[i8];
                    if (d28 != d29) {
                        if (d28 < d29) {
                            iCeil = (int) Math.floor(HctSolver.trueDelinearized(d28) - 0.5d);
                            dFloor = Math.ceil(HctSolver.trueDelinearized(dArr9[i8]) - 0.5d);
                        } else {
                            iCeil = (int) Math.ceil(HctSolver.trueDelinearized(d28) - 0.5d);
                            dFloor = Math.floor(HctSolver.trueDelinearized(dArr9[i8]) - 0.5d);
                        }
                        int i9 = (int) dFloor;
                        for (int i10 = 0; i10 < 8 && Math.abs(i9 - iCeil) > 1; i10++) {
                            int iFloor = (int) Math.floor((iCeil + i9) / 2.0d);
                            double d30 = HctSolver.CRITICAL_PLANES[iFloor];
                            double d31 = dArr8[i8];
                            double d32 = (d30 - d31) / (dArr9[i8] - d31);
                            double d33 = dArr8[0];
                            double d34 = ((dArr9[0] - d33) * d32) + d33;
                            double d35 = dArr8[1];
                            double d36 = ((dArr9[1] - d35) * d32) + d35;
                            double d37 = dArr8[2];
                            double[] dArr10 = {d34, d36, ((dArr9[2] - d37) * d32) + d37};
                            double dHueOf3 = HctSolver.hueOf(dArr10);
                            double d38 = dHueOf2;
                            if (HctSolver.areInCyclicOrder(d38, d4, dHueOf3)) {
                                i9 = iFloor;
                                dArr9 = dArr10;
                                dHueOf2 = d38;
                            } else {
                                iCeil = iFloor;
                                dArr8 = dArr10;
                                dHueOf2 = dHueOf3;
                            }
                        }
                        i3 = 1;
                        dHueOf2 = dHueOf2;
                    } else {
                        i3 = 1;
                    }
                    i8 += i3;
                }
                double[] dArr11 = {(dArr8[0] + dArr9[0]) / 2.0d, (dArr8[1] + dArr9[1]) / 2.0d, (dArr8[2] + dArr9[2]) / 2.0d};
                iDelinearized = ((ColorUtils.delinearized(dArr11[1]) & 255) << 8) | ((ColorUtils.delinearized(dArr11[0]) & 255) << 16) | (-16777216) | (ColorUtils.delinearized(dArr11[2]) & 255);
            }
        }
        return new Hct(iDelinearized);
    }

    public static Hct fromInt(int i) {
        return new Hct(i);
    }
}
