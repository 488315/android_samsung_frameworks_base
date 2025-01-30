package com.android.systemui.keyguard.shared.model;

import android.support.v4.media.AbstractC0000x2c234b15;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.shared.model.WakeSleepReason;
import com.android.systemui.keyguard.shared.model.WakefulnessState;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WakefulnessModel {
    public static final Companion Companion = new Companion(null);
    public final WakeSleepReason lastSleepReason;
    public final WakeSleepReason lastWakeReason;
    public final WakefulnessState state;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static WakefulnessModel fromWakefulnessLifecycle(WakefulnessLifecycle wakefulnessLifecycle) {
            WakefulnessState wakefulnessState;
            WakefulnessState.Companion companion = WakefulnessState.Companion;
            int i = wakefulnessLifecycle.mWakefulness;
            companion.getClass();
            if (i == 0) {
                wakefulnessState = WakefulnessState.ASLEEP;
            } else if (i == 1) {
                wakefulnessState = WakefulnessState.STARTING_TO_WAKE;
            } else if (i == 2) {
                wakefulnessState = WakefulnessState.AWAKE;
            } else {
                if (i != 3) {
                    throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Invalid Wakefulness value: ", i));
                }
                wakefulnessState = WakefulnessState.STARTING_TO_SLEEP;
            }
            WakeSleepReason.Companion companion2 = WakeSleepReason.Companion;
            int i2 = wakefulnessLifecycle.mLastWakeReason;
            companion2.getClass();
            return new WakefulnessModel(wakefulnessState, i2 != 1 ? i2 != 15 ? WakeSleepReason.OTHER : WakeSleepReason.TAP : WakeSleepReason.POWER_BUTTON, wakefulnessLifecycle.mLastSleepReason == 4 ? WakeSleepReason.POWER_BUTTON : WakeSleepReason.OTHER);
        }
    }

    public WakefulnessModel(WakefulnessState wakefulnessState, WakeSleepReason wakeSleepReason, WakeSleepReason wakeSleepReason2) {
        this.state = wakefulnessState;
        this.lastWakeReason = wakeSleepReason;
        this.lastSleepReason = wakeSleepReason2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WakefulnessModel)) {
            return false;
        }
        WakefulnessModel wakefulnessModel = (WakefulnessModel) obj;
        return this.state == wakefulnessModel.state && this.lastWakeReason == wakefulnessModel.lastWakeReason && this.lastSleepReason == wakefulnessModel.lastSleepReason;
    }

    public final int hashCode() {
        return this.lastSleepReason.hashCode() + ((this.lastWakeReason.hashCode() + (this.state.hashCode() * 31)) * 31);
    }

    public final String toString() {
        return "WakefulnessModel(state=" + this.state + ", lastWakeReason=" + this.lastWakeReason + ", lastSleepReason=" + this.lastSleepReason + ")";
    }
}
