package com.android.systemui.statusbar.domain.interactor;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class OccludedState {
    public final boolean animate;
    public final boolean occluded;

    public OccludedState(boolean z, boolean z2) {
        this.occluded = z;
        this.animate = z2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OccludedState)) {
            return false;
        }
        OccludedState occludedState = (OccludedState) obj;
        return this.occluded == occludedState.occluded && this.animate == occludedState.animate;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.animate) + (Boolean.hashCode(this.occluded) * 31);
    }

    public final String toString() {
        return "OccludedState(occluded=" + this.occluded + ", animate=" + this.animate + ")";
    }

    public /* synthetic */ OccludedState(boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, (i & 2) != 0 ? false : z2);
    }
}
