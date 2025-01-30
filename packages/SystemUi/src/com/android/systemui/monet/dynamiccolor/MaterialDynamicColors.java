package com.android.systemui.monet.dynamiccolor;

import com.android.systemui.monet.hct.Cam16;
import com.android.systemui.monet.hct.Hct;
import com.android.systemui.monet.hct.ViewingConditions;
import com.android.systemui.monet.scheme.DynamicScheme;
import com.android.systemui.monet.scheme.Variant;
import com.android.systemui.monet.utils.ColorUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MaterialDynamicColors {
    public static DynamicColor highestSurface(DynamicScheme dynamicScheme) {
        return dynamicScheme.isDark ? DynamicColor.fromPalette(new MaterialDynamicColors$$ExternalSyntheticLambda5(13), new MaterialDynamicColors$$ExternalSyntheticLambda5(14), null, null) : DynamicColor.fromPalette(new MaterialDynamicColors$$ExternalSyntheticLambda5(7), new MaterialDynamicColors$$ExternalSyntheticLambda5(8), null, null);
    }

    public static boolean isFidelity(DynamicScheme dynamicScheme) {
        Variant variant = dynamicScheme.variant;
        return variant == Variant.FIDELITY || variant == Variant.CONTENT;
    }

    public static double performAlbers(Hct hct, DynamicScheme dynamicScheme) {
        ViewingConditions defaultWithBackgroundLstar = ViewingConditions.defaultWithBackgroundLstar(dynamicScheme.isDark ? 30.0d : 80.0d);
        Cam16 fromInt = Cam16.fromInt(hct.argb);
        double d = fromInt.chroma;
        double d2 = fromInt.f318j;
        double pow = Math.pow(((d == 0.0d || d2 == 0.0d) ? 0.0d : d / Math.sqrt(d2 / 100.0d)) / Math.pow(1.64d - Math.pow(0.29d, defaultWithBackgroundLstar.f322n), 0.73d), 1.1111111111111112d);
        double radians = Math.toRadians(fromInt.hue);
        double cos = (Math.cos(2.0d + radians) + 3.8d) * 0.25d;
        double pow2 = Math.pow(d2 / 100.0d, (1.0d / defaultWithBackgroundLstar.f320c) / defaultWithBackgroundLstar.f324z) * defaultWithBackgroundLstar.f319aw;
        double d3 = cos * 3846.153846153846d * defaultWithBackgroundLstar.f323nc * defaultWithBackgroundLstar.ncb;
        double d4 = pow2 / defaultWithBackgroundLstar.nbb;
        double sin = Math.sin(radians);
        double cos2 = Math.cos(radians);
        double d5 = (((d4 + 0.305d) * 23.0d) * pow) / (((pow * 108.0d) * sin) + (((11.0d * pow) * cos2) + (d3 * 23.0d)));
        double d6 = cos2 * d5;
        double d7 = d5 * sin;
        double d8 = d4 * 460.0d;
        double d9 = ((288.0d * d7) + ((451.0d * d6) + d8)) / 1403.0d;
        double d10 = ((d8 - (891.0d * d6)) - (261.0d * d7)) / 1403.0d;
        double d11 = ((d8 - (d6 * 220.0d)) - (d7 * 6300.0d)) / 1403.0d;
        double max = Math.max(0.0d, (Math.abs(d9) * 27.13d) / (400.0d - Math.abs(d9)));
        double signum = Math.signum(d9);
        double d12 = 100.0d / defaultWithBackgroundLstar.f321fl;
        double pow3 = Math.pow(max, 2.380952380952381d) * signum * d12;
        double pow4 = Math.pow(Math.max(0.0d, (Math.abs(d10) * 27.13d) / (400.0d - Math.abs(d10))), 2.380952380952381d) * Math.signum(d10) * d12;
        double pow5 = Math.pow(Math.max(0.0d, (Math.abs(d11) * 27.13d) / (400.0d - Math.abs(d11))), 2.380952380952381d) * Math.signum(d11) * d12;
        double[] dArr = defaultWithBackgroundLstar.rgbD;
        double d13 = pow3 / dArr[0];
        double d14 = pow4 / dArr[1];
        double d15 = pow5 / dArr[2];
        double[][] dArr2 = Cam16.CAM16RGB_TO_XYZ;
        double[] dArr3 = dArr2[0];
        double d16 = (dArr3[2] * d15) + (dArr3[1] * d14) + (dArr3[0] * d13);
        double[] dArr4 = dArr2[1];
        double d17 = (dArr4[2] * d15) + (dArr4[1] * d14) + (dArr4[0] * d13);
        double[] dArr5 = dArr2[2];
        double d18 = (d15 * dArr5[2]) + (d14 * dArr5[1]) + (d13 * dArr5[0]);
        Cam16 fromXyzInViewingConditions = Cam16.fromXyzInViewingConditions(d16, d17, d18, ViewingConditions.DEFAULT);
        Hct from = Hct.from(fromXyzInViewingConditions.hue, fromXyzInViewingConditions.chroma, (ColorUtils.labF(new double[]{d16, d17, d18}[1] / 100.0d) * 116.0d) - 16.0d);
        if (Math.round(hct.tone) < 60) {
            if (!(Math.round(from.tone) <= 49)) {
                return DynamicColor.enableLightForeground(hct.tone);
            }
        }
        return DynamicColor.enableLightForeground(from.tone);
    }

    public final DynamicColor error() {
        return DynamicColor.fromPalette(new MaterialDynamicColors$$ExternalSyntheticLambda2(5), new MaterialDynamicColors$$ExternalSyntheticLambda2(6), new MaterialDynamicColors$$ExternalSyntheticLambda1(this, 12), new MaterialDynamicColors$$ExternalSyntheticLambda1(this, 13));
    }

    public final DynamicColor errorContainer() {
        return DynamicColor.fromPalette(new MaterialDynamicColors$$ExternalSyntheticLambda3(17), new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda1(this, 28), null);
    }

    public final DynamicColor outlineVariant() {
        return DynamicColor.fromPalette(new MaterialDynamicColors$$ExternalSyntheticLambda3(21), new MaterialDynamicColors$$ExternalSyntheticLambda3(22), new MaterialDynamicColors$$ExternalSyntheticLambda4(this, 0), null);
    }

    public final DynamicColor primary() {
        return DynamicColor.fromPalette(new MaterialDynamicColors$$ExternalSyntheticLambda5(9), new MaterialDynamicColors$$ExternalSyntheticLambda5(10), new MaterialDynamicColors$$ExternalSyntheticLambda4(this, 3), new MaterialDynamicColors$$ExternalSyntheticLambda4(this, 4));
    }

    public final DynamicColor primaryContainer() {
        return DynamicColor.fromPalette(new MaterialDynamicColors$$ExternalSyntheticLambda3(4), new MaterialDynamicColors$$ExternalSyntheticLambda3(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(this, 23), null);
    }

    public final DynamicColor primaryFixedDim() {
        return DynamicColor.fromPalette(new MaterialDynamicColors$$ExternalSyntheticLambda2(20), new MaterialDynamicColors$$ExternalSyntheticLambda2(21), new MaterialDynamicColors$$ExternalSyntheticLambda1(this, 20), null);
    }

    public final DynamicColor secondary() {
        return DynamicColor.fromPalette(new MaterialDynamicColors$$ExternalSyntheticLambda5(17), new MaterialDynamicColors$$ExternalSyntheticLambda5(18), new MaterialDynamicColors$$ExternalSyntheticLambda4(this, 7), new MaterialDynamicColors$$ExternalSyntheticLambda4(this, 8));
    }

    public final DynamicColor secondaryContainer() {
        return DynamicColor.fromPalette(new MaterialDynamicColors$$ExternalSyntheticLambda0(11), new MaterialDynamicColors$$ExternalSyntheticLambda0(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(this, 4), null);
    }

    public final DynamicColor secondaryFixedDim() {
        return DynamicColor.fromPalette(new MaterialDynamicColors$$ExternalSyntheticLambda0(6), new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(this, 0), null);
    }

    public final DynamicColor tertiary() {
        return DynamicColor.fromPalette(new MaterialDynamicColors$$ExternalSyntheticLambda2(7), new MaterialDynamicColors$$ExternalSyntheticLambda2(8), new MaterialDynamicColors$$ExternalSyntheticLambda1(this, 14), new MaterialDynamicColors$$ExternalSyntheticLambda1(this, 15));
    }

    public final DynamicColor tertiaryContainer() {
        return DynamicColor.fromPalette(new MaterialDynamicColors$$ExternalSyntheticLambda5(11), new MaterialDynamicColors$$ExternalSyntheticLambda5(12), new MaterialDynamicColors$$ExternalSyntheticLambda4(this, 5), null);
    }

    public final DynamicColor tertiaryFixedDim() {
        return DynamicColor.fromPalette(new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(this, 24), null);
    }
}
