package com.android.systemui.qs.panels.ui.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.qs.panels.shared.model.SizedTile;
import com.android.systemui.qs.panels.ui.viewmodel.EditTileViewModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.CategoryAndName;
import com.android.systemui.qs.shared.model.TileCategory;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AvailableTileGridCell implements SizedTile, CategoryAndName {
    public final boolean isAvailable;
    public final TileSpec key;
    public final EditTileViewModel tile;
    public final int width;

    public AvailableTileGridCell(EditTileViewModel editTileViewModel, int i, boolean z, TileSpec tileSpec) {
        this.tile = editTileViewModel;
        this.width = i;
        this.isAvailable = z;
        this.key = tileSpec;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AvailableTileGridCell)) {
            return false;
        }
        AvailableTileGridCell availableTileGridCell = (AvailableTileGridCell) obj;
        return Intrinsics.areEqual(this.tile, availableTileGridCell.tile) && this.width == availableTileGridCell.width && this.isAvailable == availableTileGridCell.isAvailable && Intrinsics.areEqual(this.key, availableTileGridCell.key);
    }

    @Override // com.android.systemui.qs.shared.model.CategoryAndName
    public final TileCategory getCategory() {
        return this.tile.category;
    }

    @Override // com.android.systemui.qs.shared.model.CategoryAndName
    public final String getName() {
        return this.tile.label.text;
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
        return this.key.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.width, this.tile.hashCode() * 31, 31), 31, this.isAvailable);
    }

    public final String toString() {
        return "AvailableTileGridCell(tile=" + this.tile + ", width=" + this.width + ", isAvailable=" + this.isAvailable + ", key=" + this.key + ")";
    }

    public AvailableTileGridCell(EditTileViewModel editTileViewModel, int i, boolean z, TileSpec tileSpec, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(editTileViewModel, (i2 & 2) != 0 ? 1 : i, (i2 & 4) != 0 ? true : z, (i2 & 8) != 0 ? editTileViewModel.tileSpec : tileSpec);
    }
}
