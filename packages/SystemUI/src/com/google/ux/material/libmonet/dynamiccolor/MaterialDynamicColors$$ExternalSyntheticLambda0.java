package com.google.ux.material.libmonet.dynamiccolor;

import java.util.function.Function;

/* loaded from: classes4.dex */
public final /* synthetic */ class MaterialDynamicColors$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MaterialDynamicColors$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        DynamicScheme dynamicScheme = (DynamicScheme) obj;
        switch (this.$r8$classId) {
            case 0:
                return dynamicScheme.neutralPalette;
            case 1:
                return dynamicScheme.primaryPalette;
            case 2:
                return Double.valueOf(dynamicScheme.isDark ? 40.0d : 80.0d);
            case 3:
                return dynamicScheme.secondaryPalette;
            case 4:
                return Double.valueOf(MaterialDynamicColors.isMonochrome(dynamicScheme) ? 25.0d : 30.0d);
            case 5:
                return dynamicScheme.primaryPalette;
            case 6:
                return Double.valueOf(dynamicScheme.primaryPalette.keyColor.tone);
            case 7:
                return dynamicScheme.tertiaryPalette;
            case 8:
                return Double.valueOf(dynamicScheme.tertiaryPalette.keyColor.tone);
            case 9:
                return dynamicScheme.tertiaryPalette;
            case 10:
                return dynamicScheme.errorPalette;
            case 11:
                return Double.valueOf(dynamicScheme.isDark ? 30.0d : 90.0d);
            case 12:
                return dynamicScheme.tertiaryPalette;
            case 13:
                return Double.valueOf(MaterialDynamicColors.isMonochrome(dynamicScheme) ? 40.0d : 90.0d);
            case 14:
                return dynamicScheme.neutralPalette;
            case 15:
                return Double.valueOf(dynamicScheme.isDark ? 90.0d : 10.0d);
            case 16:
                return dynamicScheme.primaryPalette;
            case 17:
                return dynamicScheme.secondaryPalette;
            case 18:
                return Double.valueOf(MaterialDynamicColors.isMonochrome(dynamicScheme) ? 80.0d : 90.0d);
            case 19:
                return dynamicScheme.neutralPalette;
            case 20:
                return dynamicScheme.secondaryPalette;
            case 21:
                return dynamicScheme.neutralVariantPalette;
            case 22:
                return Double.valueOf(dynamicScheme.isDark ? 80.0d : 30.0d);
            case 23:
                return dynamicScheme.neutralVariantPalette;
            case 24:
                return Double.valueOf(dynamicScheme.neutralVariantPalette.keyColor.tone);
            case 25:
                return dynamicScheme.neutralPalette;
            case 26:
                return Double.valueOf(dynamicScheme.isDark ? 100.0d : 0.0d);
            case 27:
                return Double.valueOf(dynamicScheme.isDark ? 0.2d : 0.12d);
            case 28:
                return dynamicScheme.neutralPalette;
            default:
                return dynamicScheme.errorPalette;
        }
    }
}
