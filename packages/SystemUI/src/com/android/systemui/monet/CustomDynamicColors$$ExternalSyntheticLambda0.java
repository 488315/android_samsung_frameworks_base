package com.android.systemui.monet;

import com.google.ux.material.libmonet.dynamiccolor.DynamicScheme;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class CustomDynamicColors$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        DynamicScheme dynamicScheme = (DynamicScheme) obj;
        switch (this.$r8$classId) {
            case 0:
                return dynamicScheme.secondaryPalette;
            case 1:
                return dynamicScheme.primaryPalette;
            case 2:
                return Double.valueOf(dynamicScheme.isDark ? 50.0d : 60.0d);
            case 3:
                return dynamicScheme.primaryPalette;
            case 4:
                return Double.valueOf(dynamicScheme.isDark ? 40.0d : 90.0d);
            case 5:
                return dynamicScheme.tertiaryPalette;
            case 6:
                return Double.valueOf(dynamicScheme.isDark ? 59.0d : 90.0d);
            case 7:
                return Double.valueOf(dynamicScheme.isDark ? 30.0d : 60.0d);
            case 8:
                return dynamicScheme.primaryPalette;
            case 9:
                return Double.valueOf(90.0d);
            case 10:
                return dynamicScheme.primaryPalette;
            case 11:
                return Double.valueOf(30.0d);
            case 12:
                return dynamicScheme.primaryPalette;
            case 13:
                return Double.valueOf(dynamicScheme.isDark ? 20.0d : 95.0d);
            case 14:
                return dynamicScheme.secondaryPalette;
            case 15:
                return Double.valueOf(dynamicScheme.isDark ? 70.0d : 98.0d);
            case 16:
                return dynamicScheme.primaryPalette;
            case 17:
                return Double.valueOf(dynamicScheme.isDark ? 40.0d : 80.0d);
            case 18:
                return dynamicScheme.neutralVariantPalette;
            case 19:
                return dynamicScheme.neutralPalette;
            case 20:
                return Double.valueOf(4.0d);
            case 21:
                return dynamicScheme.primaryPalette;
            case 22:
                return Double.valueOf(dynamicScheme.isDark ? 40.0d : 80.0d);
            case 23:
                return dynamicScheme.neutralVariantPalette;
            case 24:
                return Double.valueOf(80.0d);
            case 25:
                return dynamicScheme.tertiaryPalette;
            case 26:
                return dynamicScheme.neutralPalette;
            case 27:
                return Double.valueOf(20.0d);
            case 28:
                return dynamicScheme.primaryPalette;
            default:
                return Double.valueOf(0.0d);
        }
    }
}
