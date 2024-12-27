package com.android.systemui.qs.pipeline.domain.model;

import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TileModel {
    public final TileSpec spec;
    public final QSTile tile;

    public TileModel(TileSpec tileSpec, QSTile qSTile) {
        this.spec = tileSpec;
        this.tile = qSTile;
        if (!Intrinsics.areEqual(tileSpec.getSpec(), qSTile.getTileSpec())) {
            throw new IllegalStateException("Check failed.".toString());
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
