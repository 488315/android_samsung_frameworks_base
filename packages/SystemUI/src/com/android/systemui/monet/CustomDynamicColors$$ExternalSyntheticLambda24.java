package com.android.systemui.monet;

import com.google.ux.material.libmonet.dynamiccolor.DynamicColor;
import com.google.ux.material.libmonet.dynamiccolor.MaterialDynamicColors;
import com.google.ux.material.libmonet.dynamiccolor.ToneDeltaPair;
import com.google.ux.material.libmonet.dynamiccolor.TonePolarity;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CustomDynamicColors$$ExternalSyntheticLambda24 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CustomDynamicColors f$0;

    public /* synthetic */ CustomDynamicColors$$ExternalSyntheticLambda24(CustomDynamicColors customDynamicColors, int i) {
        this.$r8$classId = i;
        this.f$0 = customDynamicColors;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.getClass();
                return CustomDynamicColors.widgetBackground();
            case 1:
                CustomDynamicColors customDynamicColors = this.f$0;
                return new ToneDeltaPair(customDynamicColors.clockHour(), customDynamicColors.clockMinute(), 10.0d, TonePolarity.DARKER, false);
            case 2:
                this.f$0.getClass();
                return CustomDynamicColors.themeAppRing();
            case 3:
                return new ToneDeltaPair(this.f$0.themeNotif(), CustomDynamicColors.themeAppRing(), 10.0d, TonePolarity.NEARER, false);
            case 4:
                this.f$0.mMdc.getClass();
                return MaterialDynamicColors.surfaceContainerLow();
            case 5:
                CustomDynamicColors customDynamicColors2 = this.f$0;
                return new ToneDeltaPair(customDynamicColors2.brandC(), customDynamicColors2.brandD(), 10.0d, TonePolarity.NEARER, false);
            case 6:
                this.f$0.getClass();
                return CustomDynamicColors.widgetBackground();
            case 7:
                this.f$0.mMdc.getClass();
                return MaterialDynamicColors.surfaceContainerLow();
            case 8:
                CustomDynamicColors customDynamicColors3 = this.f$0;
                return new ToneDeltaPair(customDynamicColors3.brandD(), customDynamicColors3.brandA(), 10.0d, TonePolarity.NEARER, false);
            case 9:
                this.f$0.getClass();
                return CustomDynamicColors.underSurface();
            case 10:
                CustomDynamicColors customDynamicColors4 = this.f$0;
                return new ToneDeltaPair(customDynamicColors4.shadeActive(), customDynamicColors4.shadeInactive(), 30.0d, TonePolarity.LIGHTER, false);
            case 11:
                return this.f$0.shadeActive();
            case 12:
                CustomDynamicColors customDynamicColors5 = this.f$0;
                return new ToneDeltaPair(customDynamicColors5.onShadeActiveVariant(), customDynamicColors5.onShadeActive(), 20.0d, TonePolarity.NEARER, false);
            case 13:
                this.f$0.mMdc.getClass();
                return MaterialDynamicColors.surfaceContainerLow();
            case 14:
                CustomDynamicColors customDynamicColors6 = this.f$0;
                return new ToneDeltaPair(customDynamicColors6.brandB(), customDynamicColors6.brandC(), 10.0d, TonePolarity.NEARER, false);
            case 15:
                this.f$0.mMdc.getClass();
                return MaterialDynamicColors.surfaceContainerLow();
            case 16:
                CustomDynamicColors customDynamicColors7 = this.f$0;
                return new ToneDeltaPair(customDynamicColors7.brandA(), customDynamicColors7.brandB(), 10.0d, TonePolarity.NEARER, false);
            case 17:
                return this.f$0.shadeInactive();
            case 18:
                CustomDynamicColors customDynamicColors8 = this.f$0;
                return new ToneDeltaPair(customDynamicColors8.onShadeInactive(), customDynamicColors8.onShadeInactiveVariant(), 10.0d, TonePolarity.NEARER, false);
            case 19:
                this.f$0.getClass();
                return CustomDynamicColors.underSurface();
            case 20:
                return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
            case 21:
                return this.f$0.shadeInactive();
            case 22:
                CustomDynamicColors customDynamicColors9 = this.f$0;
                return new ToneDeltaPair(customDynamicColors9.onShadeInactive(), customDynamicColors9.onShadeInactiveVariant(), 10.0d, TonePolarity.NEARER, false);
            case 23:
                this.f$0.getClass();
                return CustomDynamicColors.underSurface();
            case 24:
                CustomDynamicColors customDynamicColors10 = this.f$0;
                return new ToneDeltaPair(customDynamicColors10.shadeInactive(), customDynamicColors10.shadeDisabled(), 15.0d, TonePolarity.LIGHTER, false);
            case 25:
                return CustomDynamicColors.widgetBackground();
            case 26:
                return CustomDynamicColors.widgetBackground();
            case 27:
                return this.f$0.shadeActive();
            default:
                CustomDynamicColors customDynamicColors11 = this.f$0;
                return new ToneDeltaPair(customDynamicColors11.onShadeActive(), customDynamicColors11.onShadeActiveVariant(), 20.0d, TonePolarity.NEARER, false);
        }
    }
}
