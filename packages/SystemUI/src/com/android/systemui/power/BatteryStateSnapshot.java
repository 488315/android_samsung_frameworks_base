package com.android.systemui.power;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;

public final class BatteryStateSnapshot {
    public final long averageTimeToDischargeMillis;
    public final int batteryLevel;
    public final int batteryStatus;
    public final int bucket;
    public final boolean isBasedOnUsage;
    public final boolean isLowWarningEnabled;
    public final boolean isPowerSaver;
    public final int lowLevelThreshold;
    public final long lowThresholdMillis;
    public final boolean plugged;
    public final int severeLevelThreshold;
    public final long severeThresholdMillis;
    public final long timeRemainingMillis;

    public BatteryStateSnapshot(int i, boolean z, boolean z2, int i2, int i3, int i4, int i5, long j, long j2, long j3, long j4, boolean z3, boolean z4) {
        this.batteryLevel = i;
        this.isPowerSaver = z;
        this.plugged = z2;
        this.bucket = i2;
        this.batteryStatus = i3;
        this.severeLevelThreshold = i4;
        this.lowLevelThreshold = i5;
        this.timeRemainingMillis = j;
        this.averageTimeToDischargeMillis = j2;
        this.severeThresholdMillis = j3;
        this.lowThresholdMillis = j4;
        this.isBasedOnUsage = z3;
        this.isLowWarningEnabled = z4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BatteryStateSnapshot)) {
            return false;
        }
        BatteryStateSnapshot batteryStateSnapshot = (BatteryStateSnapshot) obj;
        return this.batteryLevel == batteryStateSnapshot.batteryLevel && this.isPowerSaver == batteryStateSnapshot.isPowerSaver && this.plugged == batteryStateSnapshot.plugged && this.bucket == batteryStateSnapshot.bucket && this.batteryStatus == batteryStateSnapshot.batteryStatus && this.severeLevelThreshold == batteryStateSnapshot.severeLevelThreshold && this.lowLevelThreshold == batteryStateSnapshot.lowLevelThreshold && this.timeRemainingMillis == batteryStateSnapshot.timeRemainingMillis && this.averageTimeToDischargeMillis == batteryStateSnapshot.averageTimeToDischargeMillis && this.severeThresholdMillis == batteryStateSnapshot.severeThresholdMillis && this.lowThresholdMillis == batteryStateSnapshot.lowThresholdMillis && this.isBasedOnUsage == batteryStateSnapshot.isBasedOnUsage && this.isLowWarningEnabled == batteryStateSnapshot.isLowWarningEnabled;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isLowWarningEnabled) + TransitionData$$ExternalSyntheticOutline0.m(Scale$$ExternalSyntheticOutline0.m(Scale$$ExternalSyntheticOutline0.m(Scale$$ExternalSyntheticOutline0.m(Scale$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.lowLevelThreshold, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.severeLevelThreshold, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.batteryStatus, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.bucket, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Integer.hashCode(this.batteryLevel) * 31, 31, this.isPowerSaver), 31, this.plugged), 31), 31), 31), 31), 31, this.timeRemainingMillis), 31, this.averageTimeToDischargeMillis), 31, this.severeThresholdMillis), 31, this.lowThresholdMillis), 31, this.isBasedOnUsage);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BatteryStateSnapshot(batteryLevel=");
        sb.append(this.batteryLevel);
        sb.append(", isPowerSaver=");
        sb.append(this.isPowerSaver);
        sb.append(", plugged=");
        sb.append(this.plugged);
        sb.append(", bucket=");
        sb.append(this.bucket);
        sb.append(", batteryStatus=");
        sb.append(this.batteryStatus);
        sb.append(", severeLevelThreshold=");
        sb.append(this.severeLevelThreshold);
        sb.append(", lowLevelThreshold=");
        sb.append(this.lowLevelThreshold);
        sb.append(", timeRemainingMillis=");
        sb.append(this.timeRemainingMillis);
        sb.append(", averageTimeToDischargeMillis=");
        sb.append(this.averageTimeToDischargeMillis);
        sb.append(", severeThresholdMillis=");
        sb.append(this.severeThresholdMillis);
        sb.append(", lowThresholdMillis=");
        sb.append(this.lowThresholdMillis);
        sb.append(", isBasedOnUsage=");
        sb.append(this.isBasedOnUsage);
        sb.append(", isLowWarningEnabled=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isLowWarningEnabled, ")");
    }

    public BatteryStateSnapshot(int i, boolean z, boolean z2, int i2, int i3, int i4, int i5) {
        this(i, z, z2, i2, i3, i4, i5, -1L, -1L, -1L, -1L, false, true);
    }
}
