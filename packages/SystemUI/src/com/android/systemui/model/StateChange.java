package com.android.systemui.model;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.shared.system.QuickStepContract;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class StateChange {
    public long flagsToClear;
    public long flagsToSet;

    public final void setFlag(long j, boolean z) {
        if (z) {
            this.flagsToSet |= j;
            this.flagsToClear = (~j) & this.flagsToClear;
        } else {
            this.flagsToClear |= j;
            this.flagsToSet = (~j) & this.flagsToSet;
        }
    }

    public final String toString() {
        return MotionLayout$$ExternalSyntheticOutline0.m("StateChange(flagsToSet=", QuickStepContract.getSystemUiStateString(this.flagsToSet), ", flagsToClear=", QuickStepContract.getSystemUiStateString(this.flagsToClear), ")");
    }
}
