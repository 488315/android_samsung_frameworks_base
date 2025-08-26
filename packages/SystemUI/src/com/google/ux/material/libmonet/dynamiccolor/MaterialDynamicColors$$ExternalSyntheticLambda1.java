package com.google.ux.material.libmonet.dynamiccolor;

import java.util.function.Function;

/* loaded from: classes4.dex */
public final /* synthetic */ class MaterialDynamicColors$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MaterialDynamicColors$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        DynamicScheme dynamicScheme = (DynamicScheme) obj;
        switch (this.$r8$classId) {
            case 0:
                return Double.valueOf(dynamicScheme.isDark ? 90.0d : 10.0d);
            case 1:
                return Double.valueOf(dynamicScheme.isDark ? 80.0d : 40.0d);
            case 2:
                return dynamicScheme.tertiaryPalette;
            case 3:
                boolean zIsMonochrome = MaterialDynamicColors.isMonochrome(dynamicScheme);
                boolean z = dynamicScheme.isDark;
                if (zIsMonochrome) {
                    return Double.valueOf(z ? 90.0d : 25.0d);
                }
                return Double.valueOf(z ? 80.0d : 40.0d);
            case 4:
                return dynamicScheme.tertiaryPalette;
            case 5:
                return dynamicScheme.errorPalette;
            case 6:
                return Double.valueOf(dynamicScheme.isDark ? 20.0d : 100.0d);
            case 7:
                return dynamicScheme.neutralVariantPalette;
            case 8:
                return Double.valueOf(dynamicScheme.isDark ? 30.0d : 80.0d);
            case 9:
                return dynamicScheme.secondaryPalette;
            case 10:
                return dynamicScheme.secondaryPalette;
            case 11:
                return Double.valueOf(MaterialDynamicColors.isMonochrome(dynamicScheme) ? 70.0d : 80.0d);
            case 12:
                return dynamicScheme.neutralPalette;
            case 13:
                return Double.valueOf(dynamicScheme.isDark ? 10.0d : 90.0d);
            case 14:
                return dynamicScheme.neutralVariantPalette;
            case 15:
                return Double.valueOf(dynamicScheme.isDark ? 30.0d : 80.0d);
            case 16:
                return dynamicScheme.primaryPalette;
            case 17:
                return Double.valueOf(MaterialDynamicColors.isMonochrome(dynamicScheme) ? 40.0d : 90.0d);
            case 18:
                return dynamicScheme.neutralVariantPalette;
            case 19:
                return Double.valueOf(dynamicScheme.isDark ? 80.0d : 30.0d);
            case 20:
                return dynamicScheme.primaryPalette;
            case 21:
                boolean zIsMonochrome2 = MaterialDynamicColors.isMonochrome(dynamicScheme);
                boolean z2 = dynamicScheme.isDark;
                if (zIsMonochrome2) {
                    return Double.valueOf(z2 ? 100.0d : 0.0d);
                }
                return Double.valueOf(z2 ? 80.0d : 40.0d);
            case 22:
                return dynamicScheme.neutralPalette;
            case 23:
                boolean z3 = dynamicScheme.isDark;
                double d = dynamicScheme.contrastLevel;
                return Double.valueOf(z3 ? new ContrastCurve(12.0d, 12.0d, 16.0d, 20.0d).get(d) : new ContrastCurve(94.0d, 94.0d, 92.0d, 90.0d).get(d));
            case 24:
                return dynamicScheme.neutralPalette;
            case 25:
                return Double.valueOf(dynamicScheme.isDark ? 6.0d : new ContrastCurve(87.0d, 87.0d, 80.0d, 75.0d).get(dynamicScheme.contrastLevel));
            case 26:
                return dynamicScheme.neutralVariantPalette;
            case 27:
                return Double.valueOf(dynamicScheme.isDark ? 60.0d : 50.0d);
            case 28:
                return dynamicScheme.neutralPalette;
            default:
                boolean z4 = dynamicScheme.isDark;
                double d2 = dynamicScheme.contrastLevel;
                return Double.valueOf(z4 ? new ContrastCurve(17.0d, 17.0d, 21.0d, 25.0d).get(d2) : new ContrastCurve(92.0d, 92.0d, 88.0d, 85.0d).get(d2));
        }
    }
}
