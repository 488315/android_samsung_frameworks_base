package com.android.systemui.model;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.shared.system.QuickStepContract;

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
