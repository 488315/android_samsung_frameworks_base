package com.android.systemui.power.shared.model;

import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class WakefulnessModel implements Diffable {
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

    @Override // com.android.systemui.log.table.Diffable
    public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        tableRowLoggerImpl.logChange("wakefulness", toString());
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
