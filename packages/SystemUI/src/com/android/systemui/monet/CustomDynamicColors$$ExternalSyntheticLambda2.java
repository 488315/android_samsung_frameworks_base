package com.android.systemui.monet;

import com.google.ux.material.libmonet.dynamiccolor.DynamicColor;
import com.google.ux.material.libmonet.dynamiccolor.MaterialDynamicColors;
import com.google.ux.material.libmonet.dynamiccolor.ToneDeltaPair;
import com.google.ux.material.libmonet.dynamiccolor.TonePolarity;
import java.util.function.Function;

public final /* synthetic */ class CustomDynamicColors$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CustomDynamicColors f$0;

    public /* synthetic */ CustomDynamicColors$$ExternalSyntheticLambda2(CustomDynamicColors customDynamicColors, int i) {
        this.$r8$classId = i;
        this.f$0 = customDynamicColors;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        CustomDynamicColors customDynamicColors = this.f$0;
        switch (i) {
            case 0:
                customDynamicColors.getClass();
                return CustomDynamicColors.widgetBackground();
            case 1:
                customDynamicColors.mMdc.getClass();
                return MaterialDynamicColors.surfaceContainerLow();
            case 2:
                return new ToneDeltaPair(customDynamicColors.brandC(), customDynamicColors.brandD(), 10.0d, TonePolarity.NEARER, false);
            case 3:
                customDynamicColors.getClass();
                return CustomDynamicColors.widgetBackground();
            case 4:
                customDynamicColors.mMdc.getClass();
                return MaterialDynamicColors.surfaceContainerLow();
            case 5:
                return new ToneDeltaPair(customDynamicColors.brandD(), customDynamicColors.brandA(), 10.0d, TonePolarity.NEARER, false);
            case 6:
                customDynamicColors.getClass();
                return CustomDynamicColors.underSurface();
            case 7:
                return new ToneDeltaPair(customDynamicColors.shadeActive(), customDynamicColors.shadeInactive(), 30.0d, TonePolarity.LIGHTER, false);
            case 8:
                return customDynamicColors.shadeActive();
            case 9:
                return new ToneDeltaPair(customDynamicColors.onShadeActiveVariant(), customDynamicColors.onShadeActive(), 20.0d, TonePolarity.NEARER, false);
            case 10:
                customDynamicColors.mMdc.getClass();
                return MaterialDynamicColors.surfaceContainerLow();
            case 11:
                return new ToneDeltaPair(customDynamicColors.brandB(), customDynamicColors.brandC(), 10.0d, TonePolarity.NEARER, false);
            case 12:
                customDynamicColors.mMdc.getClass();
                return MaterialDynamicColors.surfaceContainerLow();
            case 13:
                return new ToneDeltaPair(customDynamicColors.brandA(), customDynamicColors.brandB(), 10.0d, TonePolarity.NEARER, false);
            case 14:
                return new ToneDeltaPair(customDynamicColors.clockHour(), customDynamicColors.clockMinute(), 10.0d, TonePolarity.DARKER, false);
            case 15:
                return customDynamicColors.shadeInactive();
            case 16:
                return new ToneDeltaPair(customDynamicColors.onShadeInactive(), customDynamicColors.onShadeInactiveVariant(), 10.0d, TonePolarity.NEARER, false);
            case 17:
                customDynamicColors.getClass();
                return CustomDynamicColors.underSurface();
            case 18:
                customDynamicColors.getClass();
                return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda5(1), new CustomDynamicColors$$ExternalSyntheticLambda5(2), true, null, null, null, null);
            case 19:
                return customDynamicColors.shadeInactive();
            case 20:
                return new ToneDeltaPair(customDynamicColors.onShadeInactiveVariant(), customDynamicColors.onShadeInactive(), 10.0d, TonePolarity.NEARER, false);
            case 21:
                customDynamicColors.getClass();
                return CustomDynamicColors.underSurface();
            case 22:
                return new ToneDeltaPair(customDynamicColors.shadeInactive(), customDynamicColors.shadeDisabled(), 15.0d, TonePolarity.LIGHTER, false);
            case 23:
                customDynamicColors.getClass();
                return CustomDynamicColors.widgetBackground();
            case 24:
                return customDynamicColors.shadeActive();
            case 25:
                return new ToneDeltaPair(customDynamicColors.onShadeActive(), customDynamicColors.onShadeActiveVariant(), 20.0d, TonePolarity.NEARER, false);
            case 26:
                customDynamicColors.getClass();
                return CustomDynamicColors.themeAppRing();
            default:
                return new ToneDeltaPair(customDynamicColors.themeNotif(), CustomDynamicColors.themeAppRing(), 10.0d, TonePolarity.NEARER, false);
        }
    }
}
