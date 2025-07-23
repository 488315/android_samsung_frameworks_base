package com.android.systemui.battery;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        if (this.isDirectPowerMode) {
            return true;
        }
        return (!this.pluggedIn || (i = this.batteryStatus) == 5 || i == 3 || i == 4) ? false : true;
    }

    public SamsungBatteryState() {
        this(-1, false, false, 1, 1, 1, false, 0);
    }
}
