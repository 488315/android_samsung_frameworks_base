package com.android.systemui.statusbar;

import com.android.settingslib.fuelgauge.BatteryStatus;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes3.dex */
public class KeyguardBatteryStatus extends BatteryStatus {
    public final boolean highVoltage;
    public final int mSlowCharger;
    public final int mSuperFastCharger;
    public final int online;
    public final boolean protectedFully;
    public long remaining;
    public final int swellingMode;

    public KeyguardBatteryStatus(int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2) {
        this(i, i2, i3, i4, i5, i6, z, 0, 0, z2, 0);
    }

    @Override // com.android.settingslib.fuelgauge.BatteryStatus
    public final boolean isCharged() {
        return this.status == 5;
    }

    @Override // com.android.settingslib.fuelgauge.BatteryStatus
    public final boolean isPluggedIn() {
        int i = this.plugged;
        return i == 1 || i == 2 || i == 8 || i == 4;
    }

    @Override // com.android.settingslib.fuelgauge.BatteryStatus
    public final boolean isPluggedInWired() {
        int i = this.plugged;
        return i == 1 || i == 2;
    }

    @Override // com.android.settingslib.fuelgauge.BatteryStatus
    public final String toString() {
        StringBuilder sb = new StringBuilder("BatteryStatus{status=");
        sb.append(this.status);
        sb.append(",level=");
        sb.append(this.level);
        sb.append(",plugged=");
        sb.append(this.plugged);
        sb.append(",chargingStatus=");
        sb.append(this.chargingStatus);
        sb.append(",maxChargingWattage=");
        sb.append(this.maxChargingWattage);
        sb.append(",remaining=");
        sb.append(this.remaining);
        sb.append("ultraFastCharger=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.mSuperFastCharger, "}", sb);
    }

    public KeyguardBatteryStatus(int i, int i2, int i3, int i4, int i5, int i6, boolean z, int i7, int i8, boolean z2, int i9) {
        super(i, i2, i3, i4, i5, false);
        this.remaining = -1L;
        this.online = i6;
        this.highVoltage = z;
        this.swellingMode = i7;
        this.mSuperFastCharger = i8;
        this.protectedFully = z2;
        this.mSlowCharger = i9;
    }
}
