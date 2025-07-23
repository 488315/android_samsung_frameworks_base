package com.android.systemui.qs.pipeline.domain.model;

import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileModel {
    public final TileSpec spec;
    public final QSTile tile;

    public TileModel(TileSpec tileSpec, QSTile qSTile) {
        this.spec = tileSpec;
        this.tile = qSTile;
        if (!Intrinsics.areEqual(tileSpec.getSpec(), qSTile.getTileSpec())) {
            throw new IllegalStateException("Check failed.");
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TileModel)) {
            return false;
        }
        TileModel tileModel = (TileModel) obj;
        return Intrinsics.areEqual(this.spec, tileModel.spec) && Intrinsics.areEqual(this.tile, tileModel.tile);
    }

    public final int hashCode() {
        return this.tile.hashCode() + (this.spec.hashCode() * 31);
    }

    public final String toString() {
        return "TileModel(spec=" + this.spec + ", tile=" + this.tile + ")";
    }
}
