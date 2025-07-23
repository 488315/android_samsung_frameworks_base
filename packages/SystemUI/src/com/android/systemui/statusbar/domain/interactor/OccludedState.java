package com.android.systemui.statusbar.domain.interactor;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
