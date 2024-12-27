package com.android.systemui.power.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class WakefulnessModel {
    public final WakefulnessState internalWakefulnessState;
    public final WakeSleepReason lastSleepReason;
    public final WakeSleepReason lastWakeReason;
    public final boolean powerButtonLaunchGestureTriggered;

    public WakefulnessModel() {
        this(null, null, null, false, 15, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WakefulnessModel)) {
            return false;
        }
        WakefulnessModel wakefulnessModel = (WakefulnessModel) obj;
        return this.internalWakefulnessState == wakefulnessModel.internalWakefulnessState && this.lastWakeReason == wakefulnessModel.lastWakeReason && this.lastSleepReason == wakefulnessModel.lastSleepReason && this.powerButtonLaunchGestureTriggered == wakefulnessModel.powerButtonLaunchGestureTriggered;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.powerButtonLaunchGestureTriggered) + ((this.lastSleepReason.hashCode() + ((this.lastWakeReason.hashCode() + (this.internalWakefulnessState.hashCode() * 31)) * 31)) * 31);
    }

    public final boolean isAsleep() {
        return !isAwake();
    }

    public final boolean isAwake() {
        WakefulnessState wakefulnessState = WakefulnessState.AWAKE;
        WakefulnessState wakefulnessState2 = this.internalWakefulnessState;
        return wakefulnessState2 == wakefulnessState || wakefulnessState2 == WakefulnessState.STARTING_TO_WAKE;
    }

    public final String toString() {
        return "WakefulnessModel(internalWakefulnessState=" + this.internalWakefulnessState + ", lastWakeReason=" + this.lastWakeReason + ", lastSleepReason=" + this.lastSleepReason + ", powerButtonLaunchGestureTriggered=" + this.powerButtonLaunchGestureTriggered + ")";
    }

    public WakefulnessModel(WakefulnessState wakefulnessState, WakeSleepReason wakeSleepReason, WakeSleepReason wakeSleepReason2, boolean z) {
        this.internalWakefulnessState = wakefulnessState;
        this.lastWakeReason = wakeSleepReason;
        this.lastSleepReason = wakeSleepReason2;
        this.powerButtonLaunchGestureTriggered = z;
    }

    public /* synthetic */ WakefulnessModel(WakefulnessState wakefulnessState, WakeSleepReason wakeSleepReason, WakeSleepReason wakeSleepReason2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? WakefulnessState.AWAKE : wakefulnessState, (i & 2) != 0 ? WakeSleepReason.OTHER : wakeSleepReason, (i & 4) != 0 ? WakeSleepReason.OTHER : wakeSleepReason2, (i & 8) != 0 ? false : z);
    }
}
