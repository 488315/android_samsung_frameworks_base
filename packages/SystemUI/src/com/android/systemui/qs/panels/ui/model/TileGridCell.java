package com.android.systemui.qs.panels.ui.model;

import androidx.compose.foundation.lazy.grid.GridItemSpan;
import androidx.compose.foundation.lazy.grid.LazyGridSpanKt;
import com.android.systemui.qs.panels.shared.model.SizedTile;
import com.android.systemui.qs.panels.ui.viewmodel.EditTileViewModel;
import com.android.systemui.qs.shared.model.CategoryAndName;
import com.android.systemui.qs.shared.model.TileCategory;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileGridCell implements GridCell, SizedTile, CategoryAndName {
    public final int column;
    public final String key;
    public final int row;
    public final long span;
    public final EditTileViewModel tile;
    public final int width;

    public /* synthetic */ TileGridCell(EditTileViewModel editTileViewModel, int i, int i2, long j, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(editTileViewModel, i, i2, j, i3);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TileGridCell) {
            TileGridCell tileGridCell = (TileGridCell) obj;
            if (Intrinsics.areEqual(this.tile, tileGridCell.tile) && this.row == tileGridCell.row && this.width == tileGridCell.width && this.span == tileGridCell.span && this.column == tileGridCell.column) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.qs.shared.model.CategoryAndName
    public final TileCategory getCategory() {
        return this.tile.category;
    }

    @Override // com.android.systemui.qs.shared.model.CategoryAndName
    public final String getName() {
        return this.tile.label.text;
    }

    @Override // com.android.systemui.qs.panels.ui.model.GridCell
    public final int getRow() {
        return this.row;
    }

    @Override // com.android.systemui.qs.panels.ui.model.GridCell
    /* renamed from: getSpan-hRN5aJ8 */
    public final long mo2891getSpanhRN5aJ8() {
        return this.span;
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
        return Integer.hashCode(this.column) + MoveResult$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.width, ReorderTile$$ExternalSyntheticOutline0.m(this.row, this.tile.hashCode() * 31, 31), 31), 31, this.span);
    }

    public final String toString() {
        String m158toStringimpl = GridItemSpan.m158toStringimpl(this.span);
        StringBuilder sb = new StringBuilder("TileGridCell(tile=");
        sb.append(this.tile);
        sb.append(", row=");
        sb.append(this.row);
        sb.append(", width=");
        sb.append(this.width);
        sb.append(", span=");
        sb.append(m158toStringimpl);
        sb.append(", column=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.column, ")", sb);
    }

    private TileGridCell(EditTileViewModel editTileViewModel, int i, int i2, long j, int i3) {
        this.tile = editTileViewModel;
        this.row = i;
        this.width = i2;
        this.span = j;
        this.column = i3;
        this.key = editTileViewModel.tileSpec.getSpec() + "-" + i;
    }

    public /* synthetic */ TileGridCell(EditTileViewModel editTileViewModel, int i, int i2, long j, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(editTileViewModel, i, i2, (i4 & 8) != 0 ? LazyGridSpanKt.GridItemSpan(i2) : j, i3, null);
    }

    public TileGridCell(SizedTile sizedTile, int i, int i2) {
        this((EditTileViewModel) sizedTile.getTile(), i, sizedTile.getWidth(), 0L, i2, 8, null);
    }
}
