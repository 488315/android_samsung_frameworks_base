package com.google.ux.material.libmonet.dynamiccolor;

import java.util.function.Function;

/* loaded from: classes4.dex */
public final /* synthetic */ class MaterialDynamicColors$$ExternalSyntheticLambda3 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MaterialDynamicColors$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        DynamicScheme dynamicScheme = (DynamicScheme) obj;
        switch (this.$r8$classId) {
            case 0:
                return dynamicScheme.primaryPalette;
            case 1:
                return dynamicScheme.secondaryPalette;
            case 2:
                return Double.valueOf(dynamicScheme.isDark ? 80.0d : 40.0d);
            case 3:
                return dynamicScheme.primaryPalette;
            case 4:
                return Double.valueOf(MaterialDynamicColors.isMonochrome(dynamicScheme) ? 30.0d : 80.0d);
            case 5:
                return dynamicScheme.primaryPalette;
            case 6:
                return dynamicScheme.neutralPalette;
            case 7:
                return Double.valueOf(dynamicScheme.isDark ? 10.0d : 90.0d);
            case 8:
                return dynamicScheme.primaryPalette;
            case 9:
                return Double.valueOf(MaterialDynamicColors.isMonochrome(dynamicScheme) ? 90.0d : 30.0d);
            case 10:
                return dynamicScheme.secondaryPalette;
            case 11:
                boolean zIsMonochrome = MaterialDynamicColors.isMonochrome(dynamicScheme);
                boolean z = dynamicScheme.isDark;
                if (zIsMonochrome) {
                    return Double.valueOf(z ? 10.0d : 90.0d);
                }
                return Double.valueOf(z ? 20.0d : 100.0d);
            case 12:
                return dynamicScheme.neutralPalette;
            case 13:
                return Double.valueOf(dynamicScheme.isDark ? new ContrastCurve(4.0d, 4.0d, 2.0d, 0.0d).get(dynamicScheme.contrastLevel) : 100.0d);
            case 14:
                return dynamicScheme.neutralPalette;
            case 15:
                return Double.valueOf(dynamicScheme.isDark ? 6.0d : 98.0d);
            case 16:
                return dynamicScheme.tertiaryPalette;
            case 17:
                return Double.valueOf(MaterialDynamicColors.isMonochrome(dynamicScheme) ? 90.0d : 30.0d);
            case 18:
                return dynamicScheme.secondaryPalette;
            case 19:
                return Double.valueOf(dynamicScheme.secondaryPalette.keyColor.tone);
            case 20:
                return dynamicScheme.errorPalette;
            case 21:
                boolean zIsMonochrome2 = MaterialDynamicColors.isMonochrome(dynamicScheme);
                boolean z2 = dynamicScheme.isDark;
                if (zIsMonochrome2) {
                    return Double.valueOf(z2 ? 90.0d : 10.0d);
                }
                return Double.valueOf(z2 ? 90.0d : 30.0d);
            case 22:
                return dynamicScheme.neutralPalette;
            case 23:
                boolean z3 = dynamicScheme.isDark;
                double d = dynamicScheme.contrastLevel;
                return Double.valueOf(z3 ? new ContrastCurve(10.0d, 10.0d, 11.0d, 12.0d).get(d) : new ContrastCurve(96.0d, 96.0d, 96.0d, 95.0d).get(d));
            case 24:
                return dynamicScheme.secondaryPalette;
            case 25:
                boolean zIsMonochrome3 = MaterialDynamicColors.isMonochrome(dynamicScheme);
                boolean z4 = dynamicScheme.isDark;
                if (zIsMonochrome3) {
                    return Double.valueOf(z4 ? 10.0d : 100.0d);
                }
                return Double.valueOf(z4 ? 20.0d : 100.0d);
            case 26:
                return dynamicScheme.neutralPalette;
            case 27:
                return dynamicScheme.neutralPalette;
            case 28:
                return Double.valueOf(dynamicScheme.isDark ? 10.0d : 90.0d);
            default:
                return dynamicScheme.neutralPalette;
        }
    }
}
