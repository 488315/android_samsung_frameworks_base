package com.android.systemui.qs.panels.ui.compose;

import com.android.compose.animation.Bounceable;
import com.android.systemui.qs.panels.ui.viewmodel.BounceableTileViewModel;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class BounceableInfo {
    public final boolean bounceEnd;
    public final BounceableTileViewModel bounceable;
    public final Bounceable nextTile;
    public final Bounceable previousTile;

    public BounceableInfo(BounceableTileViewModel bounceableTileViewModel, Bounceable bounceable, Bounceable bounceable2, boolean z) {
        this.bounceable = bounceableTileViewModel;
        this.previousTile = bounceable;
        this.nextTile = bounceable2;
        this.bounceEnd = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BounceableInfo)) {
            return false;
        }
        BounceableInfo bounceableInfo = (BounceableInfo) obj;
        return Intrinsics.areEqual(this.bounceable, bounceableInfo.bounceable) && Intrinsics.areEqual(this.previousTile, bounceableInfo.previousTile) && Intrinsics.areEqual(this.nextTile, bounceableInfo.nextTile) && this.bounceEnd == bounceableInfo.bounceEnd;
    }

    public final int hashCode() {
        int iHashCode = this.bounceable.hashCode() * 31;
        Bounceable bounceable = this.previousTile;
        int iHashCode2 = (iHashCode + (bounceable == null ? 0 : bounceable.hashCode())) * 31;
        Bounceable bounceable2 = this.nextTile;
        return Boolean.hashCode(this.bounceEnd) + ((iHashCode2 + (bounceable2 != null ? bounceable2.hashCode() : 0)) * 31);
    }

    public final String toString() {
        return "BounceableInfo(bounceable=" + this.bounceable + ", previousTile=" + this.previousTile + ", nextTile=" + this.nextTile + ", bounceEnd=" + this.bounceEnd + ")";
    }
}
