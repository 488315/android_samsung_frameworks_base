package com.android.systemui.monet;

import com.google.ux.material.libmonet.dynamiccolor.DynamicScheme;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CustomDynamicColors$$ExternalSyntheticLambda22 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        DynamicScheme dynamicScheme = (DynamicScheme) obj;
        switch (this.$r8$classId) {
            case 0:
                return dynamicScheme.secondaryPalette;
            case 1:
                return Double.valueOf(dynamicScheme.isDark ? 60.0d : 30.0d);
            case 2:
                return dynamicScheme.tertiaryPalette;
            case 3:
                return Double.valueOf(dynamicScheme.isDark ? 90.0d : 80.0d);
            case 4:
                return dynamicScheme.primaryPalette;
            case 5:
                return Double.valueOf(70.0d);
            case 6:
                return dynamicScheme.primaryPalette;
            case 7:
                return Double.valueOf(dynamicScheme.isDark ? 60.0d : 50.0d);
            case 8:
                return dynamicScheme.primaryPalette;
            case 9:
                return Double.valueOf(dynamicScheme.isDark ? 90.0d : 40.0d);
            case 10:
                return dynamicScheme.tertiaryPalette;
            case 11:
                return Double.valueOf(dynamicScheme.isDark ? 90.0d : 59.0d);
            case 12:
                return dynamicScheme.primaryPalette;
            case 13:
                return Double.valueOf(90.0d);
            case 14:
                return dynamicScheme.primaryPalette;
            case 15:
                return Double.valueOf(30.0d);
            case 16:
                return dynamicScheme.primaryPalette;
            case 17:
                return Double.valueOf(dynamicScheme.isDark ? 20.0d : 95.0d);
            case 18:
                return dynamicScheme.secondaryPalette;
            case 19:
                return Double.valueOf(dynamicScheme.isDark ? 98.0d : 70.0d);
            case 20:
                return dynamicScheme.primaryPalette;
            case 21:
                return Double.valueOf(dynamicScheme.isDark ? 80.0d : 40.0d);
            case 22:
                return dynamicScheme.neutralVariantPalette;
            case 23:
                return dynamicScheme.neutralPalette;
            case 24:
                return Double.valueOf(4.0d);
            case 25:
                return dynamicScheme.primaryPalette;
            case 26:
                return Double.valueOf(dynamicScheme.isDark ? 80.0d : 40.0d);
            case 27:
                return dynamicScheme.neutralVariantPalette;
            case 28:
                return Double.valueOf(80.0d);
            default:
                return dynamicScheme.neutralPalette;
        }
    }
}
