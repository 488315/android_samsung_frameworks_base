package com.android.systemui.p016qs.pipeline.domain.model;

import com.android.systemui.p016qs.pipeline.shared.TileSpec;
import com.android.systemui.plugins.p013qs.QSTile;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
