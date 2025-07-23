package com.android.systemui.keyguard;

import com.android.systemui.keyguard.KeyguardFoldController;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
        return Boolean.hashCode(this.skipInitState) + ReorderTile$$ExternalSyntheticOutline0.m(this.rank, this.stateListener.hashCode() * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("RankedStateListener(stateListener=");
        sb.append(this.stateListener);
        sb.append(", rank=");
        sb.append(this.rank);
        sb.append(", skipInitState=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.skipInitState, ")");
    }

    public /* synthetic */ RankedStateListener(KeyguardFoldController.StateListener stateListener, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(stateListener, i, (i2 & 4) != 0 ? false : z);
    }
}
