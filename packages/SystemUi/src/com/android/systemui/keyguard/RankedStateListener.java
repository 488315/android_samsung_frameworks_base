package com.android.systemui.keyguard;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardFoldController;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.rank, this.stateListener.hashCode() * 31, 31);
        boolean z = this.skipInitState;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return m42m + i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("RankedStateListener(stateListener=");
        sb.append(this.stateListener);
        sb.append(", rank=");
        sb.append(this.rank);
        sb.append(", skipInitState=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.skipInitState, ")");
    }

    public /* synthetic */ RankedStateListener(KeyguardFoldController.StateListener stateListener, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(stateListener, i, (i2 & 4) != 0 ? false : z);
    }
}
