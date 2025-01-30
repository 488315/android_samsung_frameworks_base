package com.android.systemui.monet.hct;

import com.android.systemui.monet.utils.ColorUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ViewingConditions {
    public static final ViewingConditions DEFAULT = defaultWithBackgroundLstar(50.0d);

    /* renamed from: aw */
    public final double f319aw;

    /* renamed from: c */
    public final double f320c;

    /* renamed from: fl */
    public final double f321fl;
    public final double flRoot;

    /* renamed from: n */
    public final double f322n;
    public final double nbb;

    /* renamed from: nc */
    public final double f323nc;
    public final double ncb;
    public final double[] rgbD;

    /* renamed from: z */
    public final double f324z;

    private ViewingConditions(double d, double d2, double d3, double d4, double d5, double d6, double[] dArr, double d7, double d8, double d9) {
        this.f322n = d;
        this.f319aw = d2;
        this.nbb = d3;
        this.ncb = d4;
        this.f320c = d5;
        this.f323nc = d6;
        this.rgbD = dArr;
        this.f321fl = d7;
        this.flRoot = d8;
        this.f324z = d9;
    }

    public static ViewingConditions defaultWithBackgroundLstar(double d) {
        double[] dArr = ColorUtils.WHITE_POINT_D65;
        double yFromLstar = (ColorUtils.yFromLstar(50.0d) * 63.66197723675813d) / 100.0d;
        double max = Math.max(0.1d, d);
        double[][] dArr2 = Cam16.XYZ_TO_CAM16RGB;
        double d2 = dArr[0];
        double[] dArr3 = dArr2[0];
        double d3 = dArr3[0] * d2;
        double d4 = dArr[1];
        double d5 = (dArr3[1] * d4) + d3;
        double d6 = dArr[2];
        double d7 = (dArr3[2] * d6) + d5;
        double[] dArr4 = dArr2[1];
        double d8 = (dArr4[2] * d6) + (dArr4[1] * d4) + (dArr4[0] * d2);
        double[] dArr5 = dArr2[2];
        double d9 = (d6 * dArr5[2]) + (d4 * dArr5[1]) + (d2 * dArr5[0]);
        double exp = (1.0d - (Math.exp(((-yFromLstar) - 42.0d) / 92.0d) * 0.2777777777777778d)) * 1.0d;
        if (exp < 0.0d) {
            exp = 0.0d;
        } else if (exp > 1.0d) {
            exp = 1.0d;
        }
        double[] dArr6 = {(((100.0d / d7) * exp) + 1.0d) - exp, (((100.0d / d8) * exp) + 1.0d) - exp, (((100.0d / d9) * exp) + 1.0d) - exp};
        double d10 = 5.0d * yFromLstar;
        double d11 = 1.0d / (d10 + 1.0d);
        double d12 = d11 * d11 * d11 * d11;
        double d13 = 1.0d - d12;
        double cbrt = (Math.cbrt(d10) * 0.1d * d13 * d13) + (d12 * yFromLstar);
        double yFromLstar2 = ColorUtils.yFromLstar(max) / dArr[1];
        double sqrt = Math.sqrt(yFromLstar2) + 1.48d;
        double pow = 0.725d / Math.pow(yFromLstar2, 0.2d);
        double pow2 = Math.pow(((dArr6[2] * cbrt) * d9) / 100.0d, 0.42d);
        double[] dArr7 = {Math.pow(((dArr6[0] * cbrt) * d7) / 100.0d, 0.42d), Math.pow(((dArr6[1] * cbrt) * d8) / 100.0d, 0.42d), pow2};
        double d14 = dArr7[0];
        double d15 = (d14 * 400.0d) / (d14 + 27.13d);
        double d16 = dArr7[1];
        return new ViewingConditions(yFromLstar2, ((((400.0d * pow2) / (pow2 + 27.13d)) * 0.05d) + (d15 * 2.0d) + ((d16 * 400.0d) / (d16 + 27.13d))) * pow, pow, pow, 0.69d, 1.0d, dArr6, cbrt, Math.pow(cbrt, 0.25d), sqrt);
    }
}
