package com.google.ux.material.libmonet.hct;

import com.google.ux.material.libmonet.utils.ColorUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Cam16 {
    public static final double[][] XYZ_TO_CAM16RGB = {new double[]{0.401288d, 0.650173d, -0.051461d}, new double[]{-0.250268d, 1.204414d, 0.045854d}, new double[]{-0.002079d, 0.048952d, 0.953127d}};
    public final double chroma;
    public final double hue;

    private Cam16(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
        this.hue = d;
        this.chroma = d2;
    }

    public static Cam16 fromInt(int i) {
        ViewingConditions viewingConditions = ViewingConditions.DEFAULT;
        double linearized = ColorUtils.linearized((16711680 & i) >> 16);
        double linearized2 = ColorUtils.linearized((65280 & i) >> 8);
        double linearized3 = ColorUtils.linearized(i & 255);
        double d = (0.18051042d * linearized3) + (0.35762064d * linearized2) + (0.41233895d * linearized);
        double d2 = (0.0722d * linearized3) + (0.7152d * linearized2) + (0.2126d * linearized);
        double d3 = (linearized3 * 0.95034478d) + (linearized2 * 0.11916382d) + (linearized * 0.01932141d);
        double[][] dArr = XYZ_TO_CAM16RGB;
        double[] dArr2 = dArr[0];
        double d4 = (dArr2[2] * d3) + (dArr2[1] * d2) + (dArr2[0] * d);
        double[] dArr3 = dArr[1];
        double d5 = (dArr3[2] * d3) + (dArr3[1] * d2) + (dArr3[0] * d);
        double[] dArr4 = dArr[2];
        double d6 = (d3 * dArr4[2]) + (d2 * dArr4[1]) + (d * dArr4[0]);
        double[] dArr5 = viewingConditions.rgbD;
        double d7 = dArr5[0] * d4;
        double d8 = dArr5[1] * d5;
        double d9 = dArr5[2] * d6;
        double abs = Math.abs(d7);
        double d10 = viewingConditions.fl;
        double pow = Math.pow((abs * d10) / 100.0d, 0.42d);
        double pow2 = Math.pow((Math.abs(d8) * d10) / 100.0d, 0.42d);
        double pow3 = Math.pow((Math.abs(d9) * d10) / 100.0d, 0.42d);
        double signum = ((Math.signum(d7) * 400.0d) * pow) / (pow + 27.13d);
        double signum2 = ((Math.signum(d8) * 400.0d) * pow2) / (pow2 + 27.13d);
        double signum3 = ((Math.signum(d9) * 400.0d) * pow3) / (pow3 + 27.13d);
        double d11 = ((((-12.0d) * signum2) + (signum * 11.0d)) + signum3) / 11.0d;
        double d12 = ((signum + signum2) - (signum3 * 2.0d)) / 9.0d;
        double d13 = signum2 * 20.0d;
        double d14 = ((21.0d * signum3) + ((signum * 20.0d) + d13)) / 20.0d;
        double d15 = (((signum * 40.0d) + d13) + signum3) / 20.0d;
        double degrees = Math.toDegrees(Math.atan2(d12, d11));
        if (degrees < 0.0d) {
            degrees += 360.0d;
        } else if (degrees >= 360.0d) {
            degrees -= 360.0d;
        }
        double d16 = degrees;
        double radians = Math.toRadians(d16);
        double d17 = d15 * viewingConditions.nbb;
        double d18 = viewingConditions.aw;
        double d19 = viewingConditions.c;
        double pow4 = Math.pow(d17 / d18, viewingConditions.z * d19) * 100.0d;
        double d20 = pow4 / 100.0d;
        double sqrt = Math.sqrt(d20);
        double d21 = d18 + 4.0d;
        double d22 = viewingConditions.flRoot;
        double d23 = sqrt * (4.0d / d19) * d21 * d22;
        double pow5 = Math.pow((Math.hypot(d11, d12) * (((((Math.cos(Math.toRadians(d16 < 20.14d ? d16 + 360.0d : d16) + 2.0d) + 3.8d) * 0.25d) * 3846.153846153846d) * viewingConditions.nc) * viewingConditions.ncb)) / (d14 + 0.305d), 0.9d) * Math.pow(1.64d - Math.pow(0.29d, viewingConditions.n), 0.73d);
        double sqrt2 = Math.sqrt(d20) * pow5;
        double d24 = sqrt2 * d22;
        double sqrt3 = Math.sqrt((pow5 * d19) / d21) * 50.0d;
        double d25 = (1.7000000000000002d * pow4) / ((0.007d * pow4) + 1.0d);
        double log1p = Math.log1p(0.0228d * d24) * 43.859649122807014d;
        return new Cam16(d16, sqrt2, pow4, d23, d24, sqrt3, d25, Math.cos(radians) * log1p, Math.sin(radians) * log1p);
    }
}
