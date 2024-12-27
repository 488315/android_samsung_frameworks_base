package com.android.systemui.power;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class BatteryStateData {
    public final int batteryLevel;
    public final int batteryStatus;
    public final int bucket;
    public final boolean plugged;

    public BatteryStateData() {
        this(0, false, 0, 0, 15, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BatteryStateData)) {
            return false;
        }
        BatteryStateData batteryStateData = (BatteryStateData) obj;
        return this.batteryLevel == batteryStateData.batteryLevel && this.plugged == batteryStateData.plugged && this.bucket == batteryStateData.bucket && this.batteryStatus == batteryStateData.batteryStatus;
    }

    public final int hashCode() {
        return Integer.hashCode(this.batteryStatus) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.bucket, TransitionData$$ExternalSyntheticOutline0.m(Integer.hashCode(this.batteryLevel) * 31, 31, this.plugged), 31);
    }

    public final String toString() {
        return "SecBatteryStatsSnapshot { batteryLevel = " + this.batteryLevel + ", plugged = " + this.plugged + ", , bucket = " + this.bucket + ", currentBatteryStatus = " + this.batteryStatus;
    }

    public BatteryStateData(int i, boolean z, int i2, int i3) {
        this.batteryLevel = i;
        this.plugged = z;
        this.bucket = i2;
        this.batteryStatus = i3;
    }

    public /* synthetic */ BatteryStateData(int i, boolean z, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? 100 : i, (i4 & 2) != 0 ? false : z, (i4 & 4) != 0 ? 1 : i2, (i4 & 8) != 0 ? 1 : i3);
    }
}
