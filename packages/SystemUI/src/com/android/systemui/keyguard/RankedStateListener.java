package com.android.systemui.keyguard;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardFoldController;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class RankedStateListener {
    public final int rank;
    public final boolean skipInitState;
    public final KeyguardFoldController.StateListener stateListener;

    public RankedStateListener(KeyguardFoldController.StateListener stateListener, int i, boolean z) {
        this.stateListener = stateListener;
        this.rank = i;
        this.skipInitState = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RankedStateListener)) {
            return false;
        }
        RankedStateListener rankedStateListener = (RankedStateListener) obj;
        return Intrinsics.areEqual(this.stateListener, rankedStateListener.stateListener) && this.rank == rankedStateListener.rank && this.skipInitState == rankedStateListener.skipInitState;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.skipInitState) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.rank, this.stateListener.hashCode() * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("RankedStateListener(stateListener=");
        sb.append(this.stateListener);
        sb.append(", rank=");
        sb.append(this.rank);
        sb.append(", skipInitState=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.skipInitState, ")");
    }

    public /* synthetic */ RankedStateListener(KeyguardFoldController.StateListener stateListener, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(stateListener, i, (i2 & 4) != 0 ? false : z);
    }
}
