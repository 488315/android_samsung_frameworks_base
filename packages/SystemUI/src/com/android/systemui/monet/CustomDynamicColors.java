package com.android.systemui.monet;

import com.google.ux.material.libmonet.dynamiccolor.ContrastCurve;
import com.google.ux.material.libmonet.dynamiccolor.DynamicColor;
import com.google.ux.material.libmonet.dynamiccolor.MaterialDynamicColors;

public final class CustomDynamicColors {
    public final MaterialDynamicColors mMdc;

    public CustomDynamicColors(boolean z) {
        this.mMdc = new MaterialDynamicColors(z);
    }

    public static DynamicColor themeAppRing() {
        return new DynamicColor("theme_app_ring", new CustomDynamicColors$$ExternalSyntheticLambda5(9), new CustomDynamicColors$$ExternalSyntheticLambda5(10), true, null, null, new ContrastCurve(1.0d, 1.0d, 1.0d, 1.0d), null);
    }

    public static DynamicColor underSurface() {
        return new DynamicColor("under_surface", new CustomDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda0(29), true, null, null, null, null);
    }

    public static DynamicColor widgetBackground() {
        return new DynamicColor("widget_background", new CustomDynamicColors$$ExternalSyntheticLambda0(12), new CustomDynamicColors$$ExternalSyntheticLambda0(13), true, null, null, null, null);
    }

    public final DynamicColor brandA() {
        return new DynamicColor("brand_a", new CustomDynamicColors$$ExternalSyntheticLambda0(16), new CustomDynamicColors$$ExternalSyntheticLambda0(17), true, new CustomDynamicColors$$ExternalSyntheticLambda2(this, 12), null, new ContrastCurve(1.0d, 3.0d, 7.0d, 17.0d), new CustomDynamicColors$$ExternalSyntheticLambda2(this, 13));
    }

    public final DynamicColor brandB() {
        return new DynamicColor("brand_b", new CustomDynamicColors$$ExternalSyntheticLambda0(14), new CustomDynamicColors$$ExternalSyntheticLambda0(15), true, new CustomDynamicColors$$ExternalSyntheticLambda2(this, 10), null, new ContrastCurve(1.0d, 3.0d, 3.0d, 6.0d), new CustomDynamicColors$$ExternalSyntheticLambda2(this, 11));
    }

    public final DynamicColor brandC() {
        return new DynamicColor("brand_c", new CustomDynamicColors$$ExternalSyntheticLambda0(1), new CustomDynamicColors$$ExternalSyntheticLambda0(2), false, new CustomDynamicColors$$ExternalSyntheticLambda2(this, 1), null, new ContrastCurve(1.0d, 3.0d, 4.0d, 9.0d), new CustomDynamicColors$$ExternalSyntheticLambda2(this, 2));
    }

    public final DynamicColor brandD() {
        return new DynamicColor("brand_d", new CustomDynamicColors$$ExternalSyntheticLambda0(5), new CustomDynamicColors$$ExternalSyntheticLambda0(6), false, new CustomDynamicColors$$ExternalSyntheticLambda2(this, 4), null, new ContrastCurve(1.0d, 3.0d, 4.0d, 13.0d), new CustomDynamicColors$$ExternalSyntheticLambda2(this, 5));
    }

    public final DynamicColor clockHour() {
        return new DynamicColor("clock_hour", new CustomDynamicColors$$ExternalSyntheticLambda0(0), new CustomDynamicColors$$ExternalSyntheticLambda0(7), false, new CustomDynamicColors$$ExternalSyntheticLambda2(this, 0), null, new ContrastCurve(1.0d, 4.0d, 5.0d, 15.0d), new CustomDynamicColors$$ExternalSyntheticLambda2(this, 14));
    }

    public final DynamicColor clockMinute() {
        return new DynamicColor("clock_minute", new CustomDynamicColors$$ExternalSyntheticLambda0(3), new CustomDynamicColors$$ExternalSyntheticLambda0(4), false, new CustomDynamicColors$$ExternalSyntheticLambda2(this, 3), null, new ContrastCurve(1.0d, 6.5d, 10.0d, 15.0d), null);
    }

    public final DynamicColor onShadeActive() {
        return new DynamicColor("on_shade_active", new CustomDynamicColors$$ExternalSyntheticLambda5(7), new CustomDynamicColors$$ExternalSyntheticLambda5(8), false, new CustomDynamicColors$$ExternalSyntheticLambda2(this, 24), null, new ContrastCurve(1.0d, 4.5d, 7.0d, 11.0d), new CustomDynamicColors$$ExternalSyntheticLambda2(this, 25));
    }

    public final DynamicColor onShadeActiveVariant() {
        return new DynamicColor("on_shade_active_variant", new CustomDynamicColors$$ExternalSyntheticLambda0(10), new CustomDynamicColors$$ExternalSyntheticLambda0(11), false, new CustomDynamicColors$$ExternalSyntheticLambda2(this, 8), null, new ContrastCurve(1.0d, 4.5d, 7.0d, 11.0d), new CustomDynamicColors$$ExternalSyntheticLambda2(this, 9));
    }

    public final DynamicColor onShadeInactive() {
        return new DynamicColor("on_shade_inactive", new CustomDynamicColors$$ExternalSyntheticLambda0(18), new CustomDynamicColors$$ExternalSyntheticLambda0(9), true, new CustomDynamicColors$$ExternalSyntheticLambda2(this, 15), null, new ContrastCurve(1.0d, 4.5d, 7.0d, 11.0d), new CustomDynamicColors$$ExternalSyntheticLambda2(this, 16));
    }

    public final DynamicColor onShadeInactiveVariant() {
        return new DynamicColor("on_shade_inactive_variant", new CustomDynamicColors$$ExternalSyntheticLambda0(23), new CustomDynamicColors$$ExternalSyntheticLambda0(24), false, new CustomDynamicColors$$ExternalSyntheticLambda2(this, 19), null, new ContrastCurve(1.0d, 4.5d, 7.0d, 11.0d), new CustomDynamicColors$$ExternalSyntheticLambda2(this, 20));
    }

    public final DynamicColor shadeActive() {
        return new DynamicColor("shade_active", new CustomDynamicColors$$ExternalSyntheticLambda0(8), new CustomDynamicColors$$ExternalSyntheticLambda0(9), false, new CustomDynamicColors$$ExternalSyntheticLambda2(this, 6), null, new ContrastCurve(1.0d, 3.0d, 4.5d, 7.0d), new CustomDynamicColors$$ExternalSyntheticLambda2(this, 7));
    }

    public final DynamicColor shadeDisabled() {
        return new DynamicColor("shade_disabled", new CustomDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda0(20), false, new CustomDynamicColors$$ExternalSyntheticLambda2(this, 17), null, new ContrastCurve(1.0d, 1.0d, 1.0d, 1.0d), null);
    }

    public final DynamicColor shadeInactive() {
        return new DynamicColor("shade_inactive", new CustomDynamicColors$$ExternalSyntheticLambda0(26), new CustomDynamicColors$$ExternalSyntheticLambda0(27), true, new CustomDynamicColors$$ExternalSyntheticLambda2(this, 21), null, new ContrastCurve(1.0d, 1.0d, 1.0d, 1.0d), new CustomDynamicColors$$ExternalSyntheticLambda2(this, 22));
    }

    public final DynamicColor themeNotif() {
        return new DynamicColor("theme_notif", new CustomDynamicColors$$ExternalSyntheticLambda0(25), new CustomDynamicColors$$ExternalSyntheticLambda5(0), false, new CustomDynamicColors$$ExternalSyntheticLambda2(this, 26), null, new ContrastCurve(1.0d, 1.0d, 1.0d, 1.0d), new CustomDynamicColors$$ExternalSyntheticLambda2(this, 27));
    }
}
