package com.android.systemui.monet;

import com.google.ux.material.libmonet.dynamiccolor.DynamicScheme;
import java.util.function.Function;

/* loaded from: classes2.dex */
public final /* synthetic */ class CustomDynamicColors$$ExternalSyntheticLambda75 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        DynamicScheme dynamicScheme = (DynamicScheme) obj;
        switch (this.$r8$classId) {
            case 0:
                return Double.valueOf(20.0d);
            case 1:
                return dynamicScheme.tertiaryPalette;
            case 2:
                return Double.valueOf(dynamicScheme.isDark ? 90.0d : 40.0d);
            case 3:
                return dynamicScheme.primaryPalette;
            case 4:
                return Double.valueOf(0.0d);
            case 5:
                return dynamicScheme.primaryPalette;
            case 6:
                return Double.valueOf(dynamicScheme.isDark ? 30.0d : 90.0d);
            case 7:
                return dynamicScheme.neutralVariantPalette;
            case 8:
                return Double.valueOf(dynamicScheme.isDark ? 35.0d : 80.0d);
            case 9:
                return dynamicScheme.primaryPalette;
            case 10:
                return Double.valueOf(dynamicScheme.isDark ? 80.0d : 55.0d);
            case 11:
                return dynamicScheme.primaryPalette;
            default:
                return Double.valueOf(10.0d);
        }
    }
}
