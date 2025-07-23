package com.android.systemui.power;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecBatterySnapshot {
    public final boolean automaticTestMode;
    public final int batteryHealth;
    public final int batteryLevel;
    public final long chargingTime;
    public final int chargingType;
    public final int currentBatteryMode;
    public final boolean isHiccupState;
    public final String optimizationChargingFinishTime;

    public SecBatterySnapshot() {
        this(0, 0, 0L, 0, 0, false, false, null, 255, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SecBatterySnapshot)) {
            return false;
        }
        SecBatterySnapshot secBatterySnapshot = (SecBatterySnapshot) obj;
        return this.batteryLevel == secBatterySnapshot.batteryLevel && this.currentBatteryMode == secBatterySnapshot.currentBatteryMode && this.chargingTime == secBatterySnapshot.chargingTime && this.chargingType == secBatterySnapshot.chargingType && this.batteryHealth == secBatterySnapshot.batteryHealth && this.isHiccupState == secBatterySnapshot.isHiccupState && this.automaticTestMode == secBatterySnapshot.automaticTestMode && Intrinsics.areEqual(this.optimizationChargingFinishTime, secBatterySnapshot.optimizationChargingFinishTime);
    }

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.batteryHealth, ReorderTile$$ExternalSyntheticOutline0.m(this.chargingType, MoveResult$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.currentBatteryMode, Integer.hashCode(this.batteryLevel) * 31, 31), 31, this.chargingTime), 31), 31), 31, this.isHiccupState), 31, this.automaticTestMode);
        String str = this.optimizationChargingFinishTime;
        return m + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SecBatteryStatsSnapshot { batteryLevel = ");
        sb.append(this.batteryLevel);
        sb.append(", currentBatteryMode = ");
        sb.append(this.currentBatteryMode);
        sb.append(", chargingTime = ");
        sb.append(this.chargingTime);
        sb.append(", chargingType = ");
        sb.append(this.chargingType);
        sb.append(", batteryHealth = ");
        sb.append(this.batteryHealth);
        sb.append(", isHiccupState = ");
        sb.append(this.isHiccupState);
        sb.append(", automaticTestMode = ");
        sb.append(this.automaticTestMode);
        sb.append(", optChargingFinishTime = ");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.optimizationChargingFinishTime, " }");
    }

    public SecBatterySnapshot(int i, int i2, long j, int i3, int i4, boolean z, boolean z2, String str) {
        this.batteryLevel = i;
        this.currentBatteryMode = i2;
        this.chargingTime = j;
        this.chargingType = i3;
        this.batteryHealth = i4;
        this.isHiccupState = z;
        this.automaticTestMode = z2;
        this.optimizationChargingFinishTime = str;
    }

    public /* synthetic */ SecBatterySnapshot(int i, int i2, long j, int i3, int i4, boolean z, boolean z2, String str, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? 100 : i, (i5 & 2) != 0 ? 0 : i2, (i5 & 4) != 0 ? 0L : j, (i5 & 8) != 0 ? 0 : i3, (i5 & 16) != 0 ? 1 : i4, (i5 & 32) != 0 ? false : z, (i5 & 64) != 0 ? false : z2, (i5 & 128) != 0 ? "" : str);
    }
}
