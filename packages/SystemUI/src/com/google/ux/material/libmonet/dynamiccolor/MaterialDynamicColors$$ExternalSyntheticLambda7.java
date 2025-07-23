package com.google.ux.material.libmonet.dynamiccolor;

import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class MaterialDynamicColors$$ExternalSyntheticLambda7 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MaterialDynamicColors$$ExternalSyntheticLambda7(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        DynamicScheme dynamicScheme = (DynamicScheme) obj;
        switch (this.$r8$classId) {
            case 0:
                return Double.valueOf(dynamicScheme.isDark ? new ContrastCurve(24.0d, 24.0d, 29.0d, 34.0d).get(dynamicScheme.contrastLevel) : 98.0d);
            case 1:
                return Double.valueOf(dynamicScheme.isDark ? 6.0d : 98.0d);
            case 2:
                return dynamicScheme.tertiaryPalette;
            case 3:
                return Double.valueOf(MaterialDynamicColors.isMonochrome(dynamicScheme) ? 100.0d : 10.0d);
            case 4:
                return dynamicScheme.neutralPalette;
            case 5:
                return Double.valueOf(dynamicScheme.isDark ? 10.0d : 90.0d);
            case 6:
                return dynamicScheme.neutralPalette;
            case 7:
                return Double.valueOf(dynamicScheme.isDark ? 90.0d : 20.0d);
            case 8:
                return dynamicScheme.tertiaryPalette;
            case 9:
                return Double.valueOf(MaterialDynamicColors.isMonochrome(dynamicScheme) ? 30.0d : 80.0d);
            case 10:
                return dynamicScheme.neutralVariantPalette;
            case 11:
                return Double.valueOf(dynamicScheme.isDark ? 30.0d : 90.0d);
            case 12:
                return dynamicScheme.neutralPalette;
            case 13:
                boolean z = dynamicScheme.isDark;
                double d = dynamicScheme.contrastLevel;
                return Double.valueOf(z ? new ContrastCurve(22.0d, 22.0d, 26.0d, 30.0d).get(d) : new ContrastCurve(90.0d, 90.0d, 84.0d, 80.0d).get(d));
            case 14:
                return dynamicScheme.tertiaryPalette;
            case 15:
                boolean isMonochrome = MaterialDynamicColors.isMonochrome(dynamicScheme);
                boolean z2 = dynamicScheme.isDark;
                if (isMonochrome) {
                    return Double.valueOf(z2 ? 10.0d : 90.0d);
                }
                return Double.valueOf(z2 ? 20.0d : 100.0d);
            case 16:
                return dynamicScheme.primaryPalette;
            case 17:
                return dynamicScheme.neutralPalette;
            case 18:
                return Double.valueOf(dynamicScheme.isDark ? 20.0d : 95.0d);
            case 19:
                return dynamicScheme.primaryPalette;
            case 20:
                return Double.valueOf(dynamicScheme.isDark ? 30.0d : 90.0d);
            case 21:
                return dynamicScheme.primaryPalette;
            case 22:
                return Double.valueOf(dynamicScheme.isDark ? 80.0d : 40.0d);
            case 23:
                return dynamicScheme.neutralPalette;
            case 24:
                return Double.valueOf(dynamicScheme.neutralPalette.keyColor.tone);
            default:
                return Double.valueOf(MaterialDynamicColors.isMonochrome(dynamicScheme) ? 100.0d : 10.0d);
        }
    }
}
