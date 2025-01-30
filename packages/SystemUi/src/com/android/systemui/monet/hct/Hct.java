package com.android.systemui.monet.hct;

import com.android.systemui.monet.utils.ColorUtils;
import com.android.systemui.monet.utils.MathUtils;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Hct {
    public int argb;
    public double chroma;
    public double hue;
    public double tone;

    private Hct(int i) {
        this.argb = i;
        Cam16 fromInt = Cam16.fromInt(i);
        this.hue = fromInt.hue;
        this.chroma = fromInt.chroma;
        this.tone = (ColorUtils.labF(MathUtils.matrixMultiply(new double[]{ColorUtils.linearized((i >> 16) & 255), ColorUtils.linearized((i >> 8) & 255), ColorUtils.linearized(i & 255)}, ColorUtils.SRGB_TO_XYZ)[1] / 100.0d) * 116.0d) - 16.0d;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x01d3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Hct from(double d, double d2, double d3) {
        int delinearized;
        int i;
        int i2;
        double[] dArr;
        double d4;
        int ceil;
        double floor;
        double[] dArr2;
        double[] dArr3;
        double[] dArr4;
        double d5;
        double[] matrixMultiply;
        if (d2 >= 1.0E-4d && d3 >= 1.0E-4d && d3 <= 99.9999d) {
            double sanitizeDegreesDouble = (MathUtils.sanitizeDegreesDouble(d) / 180.0d) * 3.141592653589793d;
            double yFromLstar = ColorUtils.yFromLstar(d3);
            double sqrt = Math.sqrt(yFromLstar) * 11.0d;
            ViewingConditions viewingConditions = ViewingConditions.DEFAULT;
            double pow = 1.0d / Math.pow(1.64d - Math.pow(0.29d, viewingConditions.f322n), 0.73d);
            double cos = (Math.cos(sanitizeDegreesDouble + 2.0d) + 3.8d) * 0.25d * 3846.153846153846d * viewingConditions.f323nc * viewingConditions.ncb;
            double sin = Math.sin(sanitizeDegreesDouble);
            double cos2 = Math.cos(sanitizeDegreesDouble);
            int i3 = 0;
            while (true) {
                dArr = HctSolver.Y_FROM_LINRGB;
                if (i3 >= 5) {
                    d4 = sanitizeDegreesDouble;
                    break;
                }
                double d6 = sqrt / 100.0d;
                if (d2 == 0.0d || sqrt == 0.0d) {
                    d4 = sanitizeDegreesDouble;
                    d5 = 0.0d;
                } else {
                    d5 = d2 / Math.sqrt(d6);
                    d4 = sanitizeDegreesDouble;
                }
                double d7 = d5 * pow;
                double d8 = pow;
                double pow2 = Math.pow(d7, 1.1111111111111112d);
                double d9 = sqrt;
                double pow3 = (Math.pow(d6, (1.0d / viewingConditions.f320c) / viewingConditions.f324z) * viewingConditions.f319aw) / viewingConditions.nbb;
                double d10 = (((0.305d + pow3) * 23.0d) * pow2) / (((pow2 * 108.0d) * sin) + (((pow2 * 11.0d) * cos2) + (23.0d * cos)));
                double d11 = d10 * cos2;
                double d12 = d10 * sin;
                double d13 = pow3 * 460.0d;
                matrixMultiply = MathUtils.matrixMultiply(new double[]{HctSolver.inverseChromaticAdaptation(((288.0d * d12) + ((451.0d * d11) + d13)) / 1403.0d), HctSolver.inverseChromaticAdaptation(((d13 - (891.0d * d11)) - (261.0d * d12)) / 1403.0d), HctSolver.inverseChromaticAdaptation(((d13 - (d11 * 220.0d)) - (d12 * 6300.0d)) / 1403.0d)}, HctSolver.LINRGB_FROM_SCALED_DISCOUNT);
                double d14 = matrixMultiply[0];
                if (d14 < 0.0d) {
                    break;
                }
                double d15 = matrixMultiply[1];
                if (d15 < 0.0d) {
                    break;
                }
                double d16 = matrixMultiply[2];
                if (d16 < 0.0d) {
                    break;
                }
                double d17 = (dArr[2] * d16) + (dArr[1] * d15) + (dArr[0] * d14);
                if (d17 <= 0.0d) {
                    break;
                }
                if (i3 == 4) {
                    break;
                }
                double d18 = d17 - yFromLstar;
                if (Math.abs(d18) < 0.002d) {
                    break;
                }
                sqrt = d9 - ((d18 * d9) / (d17 * 2.0d));
                i3++;
                pow = d8;
                sanitizeDegreesDouble = d4;
            }
            double d19 = matrixMultiply[0];
            if (d19 <= 100.01d && matrixMultiply[1] <= 100.01d && matrixMultiply[2] <= 100.01d) {
                i2 = ((ColorUtils.delinearized(matrixMultiply[1]) & 255) << 8) | ((ColorUtils.delinearized(d19) & 255) << 16) | EmergencyPhoneWidget.BG_COLOR | (ColorUtils.delinearized(matrixMultiply[2]) & 255);
                if (i2 == 0) {
                    double[] dArr5 = {-1.0d, -1.0d, -1.0d};
                    double[] dArr6 = dArr5;
                    boolean z = false;
                    double d20 = 0.0d;
                    double d21 = 0.0d;
                    boolean z2 = true;
                    for (int i4 = 0; i4 < 12; i4++) {
                        double d22 = dArr[0];
                        double d23 = dArr[1];
                        double d24 = dArr[2];
                        double d25 = i4 % 4 <= 1 ? 0.0d : 100.0d;
                        double d26 = i4 % 2 == 0 ? 0.0d : 100.0d;
                        if (i4 < 4) {
                            double d27 = ((yFromLstar - (d23 * d25)) - (d24 * d26)) / d22;
                            dArr4 = new double[]{-1.0d, -1.0d, -1.0d};
                            if (0.0d <= d27 && d27 <= 100.0d) {
                                dArr4[0] = d27;
                                dArr4[1] = d25;
                                dArr4[2] = d26;
                            }
                        } else if (i4 < 8) {
                            double d28 = ((yFromLstar - (d22 * d26)) - (d24 * d25)) / d23;
                            if (0.0d <= d28 && d28 <= 100.0d) {
                                dArr3 = new double[]{d26, d28, d25};
                                dArr4 = dArr3;
                            } else {
                                dArr2 = new double[]{-1.0d, -1.0d, -1.0d};
                                dArr4 = dArr2;
                            }
                        } else {
                            double d29 = ((yFromLstar - (d22 * d25)) - (d23 * d26)) / d24;
                            if (0.0d <= d29 && d29 <= 100.0d) {
                                dArr3 = new double[]{d25, d26, d29};
                                dArr4 = dArr3;
                            } else {
                                dArr2 = new double[]{-1.0d, -1.0d, -1.0d};
                                dArr4 = dArr2;
                            }
                        }
                        if (dArr4[0] >= 0.0d) {
                            double hueOf = HctSolver.hueOf(dArr4);
                            if (!z) {
                                dArr6 = dArr4;
                                dArr5 = dArr6;
                                d20 = hueOf;
                                d21 = d20;
                                z = true;
                            } else if (z2 || HctSolver.areInCyclicOrder(d20, hueOf, d21)) {
                                if (HctSolver.areInCyclicOrder(d20, d4, hueOf)) {
                                    dArr6 = dArr4;
                                    d21 = hueOf;
                                } else {
                                    dArr5 = dArr4;
                                    d20 = hueOf;
                                }
                                z2 = false;
                            }
                        }
                    }
                    double[][] dArr7 = {dArr5, dArr6};
                    double[] dArr8 = dArr7[0];
                    double hueOf2 = HctSolver.hueOf(dArr8);
                    double[] dArr9 = dArr7[1];
                    for (int i5 = 0; i5 < 3; i5++) {
                        double d30 = dArr8[i5];
                        double d31 = dArr9[i5];
                        if (d30 != d31) {
                            if (d30 < d31) {
                                ceil = (int) Math.floor(HctSolver.trueDelinearized(d30) - 0.5d);
                                floor = Math.ceil(HctSolver.trueDelinearized(dArr9[i5]) - 0.5d);
                            } else {
                                ceil = (int) Math.ceil(HctSolver.trueDelinearized(d30) - 0.5d);
                                floor = Math.floor(HctSolver.trueDelinearized(dArr9[i5]) - 0.5d);
                            }
                            int i6 = (int) floor;
                            for (int i7 = 0; i7 < 8 && Math.abs(i6 - ceil) > 1; i7++) {
                                int floor2 = (int) Math.floor((ceil + i6) / 2.0d);
                                double d32 = HctSolver.CRITICAL_PLANES[floor2];
                                double d33 = dArr8[i5];
                                double d34 = (d32 - d33) / (dArr9[i5] - d33);
                                double d35 = dArr8[0];
                                double d36 = dArr8[1];
                                double d37 = dArr8[2];
                                double[] dArr10 = {((dArr9[0] - d35) * d34) + d35, ((dArr9[1] - d36) * d34) + d36, ((dArr9[2] - d37) * d34) + d37};
                                double hueOf3 = HctSolver.hueOf(dArr10);
                                if (HctSolver.areInCyclicOrder(hueOf2, d4, hueOf3)) {
                                    i6 = floor2;
                                    dArr9 = dArr10;
                                } else {
                                    ceil = floor2;
                                    hueOf2 = hueOf3;
                                    dArr8 = dArr10;
                                }
                            }
                        }
                    }
                    double d38 = (dArr8[0] + dArr9[0]) / 2.0d;
                    double[] dArr11 = {d38, (dArr8[1] + dArr9[1]) / 2.0d, (dArr8[2] + dArr9[2]) / 2.0d};
                    int delinearized2 = ColorUtils.delinearized(d38);
                    int delinearized3 = ColorUtils.delinearized(dArr11[1]);
                    int delinearized4 = ColorUtils.delinearized(dArr11[2]);
                    delinearized = ((delinearized2 & 255) << 16) | EmergencyPhoneWidget.BG_COLOR | ((delinearized3 & 255) << 8);
                    i = delinearized4 & 255;
                }
                return new Hct(i2);
            }
            i2 = 0;
            if (i2 == 0) {
            }
            return new Hct(i2);
        }
        delinearized = ColorUtils.delinearized(ColorUtils.yFromLstar(d3)) & 255;
        i = (delinearized << 16) | EmergencyPhoneWidget.BG_COLOR | (delinearized << 8);
        i2 = delinearized | i;
        return new Hct(i2);
    }

    public static Hct fromInt(int i) {
        return new Hct(i);
    }
}
