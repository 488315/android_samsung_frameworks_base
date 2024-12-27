package com.android.systemui.statusbar.domain.interactor;

import kotlin.jvm.internal.DefaultConstructorMarker;

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
