package com.google.ux.material.libmonet.contrast;

import com.google.ux.material.libmonet.utils.ColorUtils;

/* loaded from: classes4.dex */
public final class Contrast {
    private Contrast() {
    }

    public static double darker(double d, double d2) {
        if (d < 0.0d || d > 100.0d) {
            return -1.0d;
        }
        double dYFromLstar = ColorUtils.yFromLstar(d);
        double d3 = ((dYFromLstar + 5.0d) / d2) - 5.0d;
        if (d3 < 0.0d || d3 > 100.0d) {
            return -1.0d;
        }
        double dRatioOfYs = ratioOfYs(dYFromLstar, d3);
        double dAbs = Math.abs(dRatioOfYs - d2);
        if (dRatioOfYs < d2 && dAbs > 0.04d) {
            return -1.0d;
        }
        double dLabF = ((ColorUtils.labF(d3 / 100.0d) * 116.0d) - 16.0d) - 0.4d;
        if (dLabF < 0.0d || dLabF > 100.0d) {
            return -1.0d;
        }
        return dLabF;
    }

    public static double lighter(double d, double d2) {
        if (d < 0.0d || d > 100.0d) {
            return -1.0d;
        }
        double dYFromLstar = ColorUtils.yFromLstar(d);
        double d3 = ((dYFromLstar + 5.0d) * d2) - 5.0d;
        if (d3 < 0.0d || d3 > 100.0d) {
            return -1.0d;
        }
        double dRatioOfYs = ratioOfYs(d3, dYFromLstar);
        double dAbs = Math.abs(dRatioOfYs - d2);
        if (dRatioOfYs < d2 && dAbs > 0.04d) {
            return -1.0d;
        }
        double dLabF = ((ColorUtils.labF(d3 / 100.0d) * 116.0d) - 16.0d) + 0.4d;
        if (dLabF < 0.0d || dLabF > 100.0d) {
            return -1.0d;
        }
        return dLabF;
    }

    public static double ratioOfTones(double d, double d2) {
        return ratioOfYs(ColorUtils.yFromLstar(d), ColorUtils.yFromLstar(d2));
    }

    public static double ratioOfYs(double d, double d2) {
        double dMax = Math.max(d, d2);
        if (dMax != d2) {
            d = d2;
        }
        return (dMax + 5.0d) / (d + 5.0d);
    }
}
