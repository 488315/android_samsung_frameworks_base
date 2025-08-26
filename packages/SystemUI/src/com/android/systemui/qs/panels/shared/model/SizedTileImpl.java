package com.android.systemui.qs.panels.shared.model;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class SizedTileImpl implements SizedTile {
    public final Object tile;
    public final int width;

    public SizedTileImpl(Object obj, int i) {
        this.tile = obj;
        this.width = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SizedTileImpl)) {
            return false;
        }
        SizedTileImpl sizedTileImpl = (SizedTileImpl) obj;
        return Intrinsics.areEqual(this.tile, sizedTileImpl.tile) && this.width == sizedTileImpl.width;
    }

    @Override // com.android.systemui.qs.panels.shared.model.SizedTile
    public final Object getTile() {
        return this.tile;
    }

    @Override // com.android.systemui.qs.panels.shared.model.SizedTile
    public final int getWidth() {
        return this.width;
    }

    public final int hashCode() {
        Object obj = this.tile;
        return Integer.hashCode(this.width) + ((obj == null ? 0 : obj.hashCode()) * 31);
    }

    public final String toString() {
        return "SizedTileImpl(tile=" + this.tile + ", width=" + this.width + ")";
    }
}
