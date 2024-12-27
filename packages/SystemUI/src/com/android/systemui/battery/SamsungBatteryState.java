package com.android.systemui.battery;

import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SamsungBatteryState {
    public static final int BATTERY_HEALTH_OVERHEAT_LIMIT;
    public final int batteryHealth;
    public final int batteryOnline;
    public final int batteryStatus;
    public final boolean charging;
    public final boolean isDirectPowerMode;
    public final int level;
    public final int miscEvent;
    public final boolean pluggedIn;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        BATTERY_HEALTH_OVERHEAT_LIMIT = 8;
    }

    public SamsungBatteryState(int i, boolean z, boolean z2, int i2, int i3, int i4, boolean z3, int i5) {
        this.level = i;
        this.pluggedIn = z;
        this.charging = z2;
        this.batteryStatus = i2;
        this.batteryHealth = i3;
        this.batteryOnline = i4;
        this.isDirectPowerMode = z3;
        this.miscEvent = i5;
    }

    public final boolean shouldShowChargingIcon() {
        int i;
        return this.isDirectPowerMode || !(!this.pluggedIn || (i = this.batteryStatus) == 5 || i == 3 || i == 4);
    }

    public SamsungBatteryState() {
        this(-1, false, false, 1, 1, 1, false, 0);
    }
}
