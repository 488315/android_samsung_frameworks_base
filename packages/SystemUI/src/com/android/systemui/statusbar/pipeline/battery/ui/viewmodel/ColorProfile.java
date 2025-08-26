package com.android.systemui.statusbar.pipeline.battery.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryColors;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class ColorProfile {
    public final BatteryColors dark;
    public final BatteryColors light;

    public ColorProfile(BatteryColors batteryColors, BatteryColors batteryColors2) {
        this.dark = batteryColors;
        this.light = batteryColors2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ColorProfile)) {
            return false;
        }
        ColorProfile colorProfile = (ColorProfile) obj;
        return Intrinsics.areEqual(this.dark, colorProfile.dark) && Intrinsics.areEqual(this.light, colorProfile.light);
    }

    public final int hashCode() {
        return this.light.hashCode() + (this.dark.hashCode() * 31);
    }

    public final String toString() {
        return "ColorProfile(dark=" + this.dark + ", light=" + this.light + ")";
    }
}
