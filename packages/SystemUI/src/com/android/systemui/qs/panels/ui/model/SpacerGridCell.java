package com.android.systemui.qs.panels.ui.model;

import androidx.compose.foundation.lazy.grid.GridItemSpan;
import androidx.compose.foundation.lazy.grid.LazyGridSpanKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class SpacerGridCell implements GridCell {
    public final int row;
    public final long span;

    public /* synthetic */ SpacerGridCell(int i, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, j);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SpacerGridCell) {
            SpacerGridCell spacerGridCell = (SpacerGridCell) obj;
            return this.row == spacerGridCell.row && this.span == spacerGridCell.span;
        }
        return false;
    }

    @Override // com.android.systemui.qs.panels.ui.model.GridCell
    public final int getRow() {
        return this.row;
    }

    @Override // com.android.systemui.qs.panels.ui.model.GridCell
    /* renamed from: getSpan-hRN5aJ8 */
    public final long mo2906getSpanhRN5aJ8() {
        return this.span;
    }

    public final int hashCode() {
        return Long.hashCode(this.span) + (Integer.hashCode(this.row) * 31);
    }

    public final String toString() {
        return "SpacerGridCell(row=" + this.row + ", span=" + GridItemSpan.m159toStringimpl(this.span) + ")";
    }

    private SpacerGridCell(int i, long j) {
        this.row = i;
        this.span = j;
    }

    public /* synthetic */ SpacerGridCell(int i, long j, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? LazyGridSpanKt.GridItemSpan(1) : j, null);
    }
}
