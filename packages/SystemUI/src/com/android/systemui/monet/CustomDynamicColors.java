package com.android.systemui.monet;

import com.google.ux.material.libmonet.dynamiccolor.ContrastCurve;
import com.google.ux.material.libmonet.dynamiccolor.DynamicColor;
import com.google.ux.material.libmonet.dynamiccolor.MaterialDynamicColors;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class CustomDynamicColors {
    public final Supplier[] allColors;
    public final MaterialDynamicColors mMdc;

    public CustomDynamicColors(boolean z) {
        this.mMdc = new MaterialDynamicColors(z);
        final int i = 0;
        final int i2 = 2;
        final int i3 = 5;
        final int i4 = 6;
        final int i5 = 7;
        final int i6 = 8;
        final int i7 = 9;
        final int i8 = 10;
        final int i9 = 12;
        final int i10 = 13;
        final int i11 = 11;
        final int i12 = 14;
        final int i13 = 15;
        final int i14 = 16;
        final int i15 = 17;
        final int i16 = 18;
        final int i17 = 19;
        final int i18 = 20;
        final int i19 = 21;
        final int i20 = 1;
        final int i21 = 3;
        final int i22 = 4;
        this.allColors = new Supplier[]{new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i2) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i3) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i4) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i5) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i6) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i7) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i8) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i9) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i10) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i11) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i12) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i13) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i14) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i15) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i16) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i17) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i18) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i19) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i20) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i21) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.monet.CustomDynamicColors$$ExternalSyntheticLambda0
            public final /* synthetic */ CustomDynamicColors f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i22) {
                    case 0:
                        return CustomDynamicColors.widgetBackground();
                    case 1:
                        return this.f$0.onShadeInactiveVariant();
                    case 2:
                        return this.f$0.clockHour();
                    case 3:
                        return this.f$0.shadeDisabled();
                    case 4:
                        this.f$0.getClass();
                        return new DynamicColor("overview_background", new CustomDynamicColors$$ExternalSyntheticLambda75(7), new CustomDynamicColors$$ExternalSyntheticLambda75(8), true, null, null, null, null);
                    case 5:
                        return this.f$0.clockMinute();
                    case 6:
                        return new DynamicColor("clock_second", new CustomDynamicColors$$ExternalSyntheticLambda75(1), new CustomDynamicColors$$ExternalSyntheticLambda75(2), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 25), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 7:
                        return new DynamicColor("weather_temp", new CustomDynamicColors$$ExternalSyntheticLambda75(9), new CustomDynamicColors$$ExternalSyntheticLambda75(10), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 26), null, new ContrastCurve(5.0d, 5.0d, 70.0d, 11.0d), null);
                    case 8:
                        return new DynamicColor("theme_app", new CustomDynamicColors$$ExternalSyntheticLambda75(5), new CustomDynamicColors$$ExternalSyntheticLambda75(6), true, null, null, null, null);
                    case 9:
                        return new DynamicColor("on_theme_app", new CustomDynamicColors$$ExternalSyntheticLambda22(25), new CustomDynamicColors$$ExternalSyntheticLambda22(26), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this.f$0, 20), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 10.0d), null);
                    case 10:
                        return CustomDynamicColors.themeAppRing();
                    case 11:
                        return this.f$0.brandB();
                    case 12:
                        return this.f$0.themeNotif();
                    case 13:
                        return this.f$0.brandA();
                    case 14:
                        return this.f$0.brandC();
                    case 15:
                        return this.f$0.brandD();
                    case 16:
                        return CustomDynamicColors.underSurface();
                    case 17:
                        return this.f$0.shadeActive();
                    case 18:
                        return this.f$0.onShadeActive();
                    case 19:
                        return this.f$0.onShadeActiveVariant();
                    case 20:
                        return this.f$0.shadeInactive();
                    default:
                        return this.f$0.onShadeInactive();
                }
            }
        }};
    }

    public static DynamicColor themeAppRing() {
        return new DynamicColor("theme_app_ring", new CustomDynamicColors$$ExternalSyntheticLambda22(4), new CustomDynamicColors$$ExternalSyntheticLambda22(5), true, null, null, new ContrastCurve(1.0d, 1.0d, 1.0d, 1.0d), null);
    }

    public static DynamicColor underSurface() {
        return new DynamicColor("under_surface", new CustomDynamicColors$$ExternalSyntheticLambda75(3), new CustomDynamicColors$$ExternalSyntheticLambda75(4), true, null, null, null, null);
    }

    public static DynamicColor widgetBackground() {
        return new DynamicColor("widget_background", new CustomDynamicColors$$ExternalSyntheticLambda22(16), new CustomDynamicColors$$ExternalSyntheticLambda22(17), true, null, null, null, null);
    }

    public final DynamicColor brandA() {
        return new DynamicColor("brand_a", new CustomDynamicColors$$ExternalSyntheticLambda22(20), new CustomDynamicColors$$ExternalSyntheticLambda22(21), true, new CustomDynamicColors$$ExternalSyntheticLambda24(this, 15), null, new ContrastCurve(3.0d, 3.0d, 7.0d, 17.0d), new CustomDynamicColors$$ExternalSyntheticLambda24(this, 16));
    }

    public final DynamicColor brandB() {
        return new DynamicColor("brand_b", new CustomDynamicColors$$ExternalSyntheticLambda22(18), new CustomDynamicColors$$ExternalSyntheticLambda22(19), true, new CustomDynamicColors$$ExternalSyntheticLambda24(this, 13), null, new ContrastCurve(3.0d, 3.0d, 3.0d, 6.0d), new CustomDynamicColors$$ExternalSyntheticLambda24(this, 14));
    }

    public final DynamicColor brandC() {
        return new DynamicColor("brand_c", new CustomDynamicColors$$ExternalSyntheticLambda22(6), new CustomDynamicColors$$ExternalSyntheticLambda22(7), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this, 4), null, new ContrastCurve(3.0d, 3.0d, 4.0d, 9.0d), new CustomDynamicColors$$ExternalSyntheticLambda24(this, 5));
    }

    public final DynamicColor brandD() {
        return new DynamicColor("brand_d", new CustomDynamicColors$$ExternalSyntheticLambda22(10), new CustomDynamicColors$$ExternalSyntheticLambda22(11), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this, 7), null, new ContrastCurve(3.0d, 3.0d, 4.0d, 13.0d), new CustomDynamicColors$$ExternalSyntheticLambda24(this, 8));
    }

    public final DynamicColor clockHour() {
        return new DynamicColor("clock_hour", new CustomDynamicColors$$ExternalSyntheticLambda22(0), new CustomDynamicColors$$ExternalSyntheticLambda22(1), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this, 0), null, new ContrastCurve(4.0d, 4.0d, 5.0d, 15.0d), new CustomDynamicColors$$ExternalSyntheticLambda24(this, 1));
    }

    public final DynamicColor clockMinute() {
        return new DynamicColor("clock_minute", new CustomDynamicColors$$ExternalSyntheticLambda22(8), new CustomDynamicColors$$ExternalSyntheticLambda22(9), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this, 6), null, new ContrastCurve(6.5d, 6.5d, 10.0d, 15.0d), null);
    }

    public final DynamicColor onShadeActive() {
        return new DynamicColor("on_shade_active", new CustomDynamicColors$$ExternalSyntheticLambda75(11), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this, 27), null, new ContrastCurve(4.5d, 4.5d, 7.0d, 11.0d), new CustomDynamicColors$$ExternalSyntheticLambda24(this, 28));
    }

    public final DynamicColor onShadeActiveVariant() {
        return new DynamicColor("on_shade_active_variant", new CustomDynamicColors$$ExternalSyntheticLambda22(14), new CustomDynamicColors$$ExternalSyntheticLambda22(15), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this, 11), null, new ContrastCurve(4.5d, 4.5d, 7.0d, 11.0d), new CustomDynamicColors$$ExternalSyntheticLambda24(this, 12));
    }

    public final DynamicColor onShadeInactive() {
        return new DynamicColor("on_shade_inactive", new CustomDynamicColors$$ExternalSyntheticLambda22(22), new CustomDynamicColors$$ExternalSyntheticLambda22(13), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this, 17), null, new ContrastCurve(4.5d, 4.5d, 7.0d, 11.0d), new CustomDynamicColors$$ExternalSyntheticLambda24(this, 18));
    }

    public final DynamicColor onShadeInactiveVariant() {
        return new DynamicColor("on_shade_inactive_variant", new CustomDynamicColors$$ExternalSyntheticLambda22(27), new CustomDynamicColors$$ExternalSyntheticLambda22(28), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this, 21), null, new ContrastCurve(4.5d, 4.5d, 7.0d, 11.0d), new CustomDynamicColors$$ExternalSyntheticLambda24(this, 22));
    }

    public final DynamicColor shadeActive() {
        return new DynamicColor("shade_active", new CustomDynamicColors$$ExternalSyntheticLambda22(12), new CustomDynamicColors$$ExternalSyntheticLambda22(13), true, new CustomDynamicColors$$ExternalSyntheticLambda24(this, 9), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), new CustomDynamicColors$$ExternalSyntheticLambda24(this, 10));
    }

    public final DynamicColor shadeDisabled() {
        return new DynamicColor("shade_disabled", new CustomDynamicColors$$ExternalSyntheticLambda22(23), new CustomDynamicColors$$ExternalSyntheticLambda22(24), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this, 19), null, new ContrastCurve(1.0d, 1.0d, 1.0d, 1.0d), null);
    }

    public final DynamicColor shadeInactive() {
        return new DynamicColor("shade_inactive", new CustomDynamicColors$$ExternalSyntheticLambda22(29), new CustomDynamicColors$$ExternalSyntheticLambda75(0), true, new CustomDynamicColors$$ExternalSyntheticLambda24(this, 23), null, new ContrastCurve(1.0d, 1.0d, 1.0d, 1.0d), new CustomDynamicColors$$ExternalSyntheticLambda24(this, 24));
    }

    public final DynamicColor themeNotif() {
        return new DynamicColor("theme_notif", new CustomDynamicColors$$ExternalSyntheticLambda22(2), new CustomDynamicColors$$ExternalSyntheticLambda22(3), false, new CustomDynamicColors$$ExternalSyntheticLambda24(this, 2), null, new ContrastCurve(1.0d, 1.0d, 1.0d, 1.0d), new CustomDynamicColors$$ExternalSyntheticLambda24(this, 3));
    }
}
