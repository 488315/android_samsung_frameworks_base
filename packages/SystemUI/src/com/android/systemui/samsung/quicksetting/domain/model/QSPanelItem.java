package com.android.systemui.samsung.quicksetting.domain.model;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntRect;
import com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class QSPanelItem {
    public final float expandedHeight;
    public final int expandedSpanHeight;
    public final float extraHeight;
    public final GridTileItem gridTileItem;
    public final boolean isTarget;
    public final IntRect resizeSpanRect;
    public final int spanHeight;
    public final long spanOffset;
    public final int spanWidth;
    public final IntOffset translationOffset;
    public final IntOffset translationSpanOffset;

    public /* synthetic */ QSPanelItem(GridTileItem gridTileItem, int i, int i2, long j, float f, float f2, int i3, IntOffset intOffset, IntOffset intOffset2, IntRect intRect, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(gridTileItem, i, i2, j, f, f2, i3, intOffset, intOffset2, intRect, z);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof QSPanelItem)) {
            return false;
        }
        QSPanelItem qSPanelItem = (QSPanelItem) obj;
        return Intrinsics.areEqual(this.gridTileItem, qSPanelItem.gridTileItem) && this.spanWidth == qSPanelItem.spanWidth && this.spanHeight == qSPanelItem.spanHeight && IntOffset.m851equalsimpl0(this.spanOffset, qSPanelItem.spanOffset) && Dp.m838equalsimpl0(this.extraHeight, qSPanelItem.extraHeight) && Dp.m838equalsimpl0(this.expandedHeight, qSPanelItem.expandedHeight) && this.expandedSpanHeight == qSPanelItem.expandedSpanHeight && Intrinsics.areEqual(this.translationSpanOffset, qSPanelItem.translationSpanOffset) && Intrinsics.areEqual(this.translationOffset, qSPanelItem.translationOffset) && Intrinsics.areEqual(this.resizeSpanRect, qSPanelItem.resizeSpanRect) && this.isTarget == qSPanelItem.isTarget;
    }

    public final int hashCode() {
        int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.spanHeight, ReorderTile$$ExternalSyntheticOutline0.m(this.spanWidth, this.gridTileItem.hashCode() * 31, 31), 31);
        IntOffset.Companion companion = IntOffset.Companion;
        int iM2 = MoveResult$$ExternalSyntheticOutline0.m(iM, 31, this.spanOffset);
        Dp.Companion companion2 = Dp.Companion;
        int iM3 = ReorderTile$$ExternalSyntheticOutline0.m(this.expandedSpanHeight, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.expandedHeight, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.extraHeight, iM2, 31), 31), 31);
        IntOffset intOffset = this.translationSpanOffset;
        int iHashCode = (iM3 + (intOffset == null ? 0 : Long.hashCode(intOffset.packedValue))) * 31;
        IntOffset intOffset2 = this.translationOffset;
        int iHashCode2 = (iHashCode + (intOffset2 == null ? 0 : Long.hashCode(intOffset2.packedValue))) * 31;
        IntRect intRect = this.resizeSpanRect;
        return Boolean.hashCode(this.isTarget) + ((iHashCode2 + (intRect != null ? intRect.hashCode() : 0)) * 31);
    }

    public final String toString() {
        String strM854toStringimpl = IntOffset.m854toStringimpl(this.spanOffset);
        String strM839toStringimpl = Dp.m839toStringimpl(this.extraHeight);
        String strM839toStringimpl2 = Dp.m839toStringimpl(this.expandedHeight);
        StringBuilder sb = new StringBuilder("QSPanelItem(gridTileItem=");
        sb.append(this.gridTileItem);
        sb.append(", spanWidth=");
        sb.append(this.spanWidth);
        sb.append(", spanHeight=");
        sb.append(this.spanHeight);
        sb.append(", spanOffset=");
        sb.append(strM854toStringimpl);
        sb.append(", extraHeight=");
        MoveResult$$ExternalSyntheticOutline0.m(sb, strM839toStringimpl, ", expandedHeight=", strM839toStringimpl2, ", expandedSpanHeight=");
        sb.append(this.expandedSpanHeight);
        sb.append(", translationSpanOffset=");
        sb.append(this.translationSpanOffset);
        sb.append(", translationOffset=");
        sb.append(this.translationOffset);
        sb.append(", resizeSpanRect=");
        sb.append(this.resizeSpanRect);
        sb.append(", isTarget=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isTarget, ")");
    }

    private QSPanelItem(GridTileItem gridTileItem, int i, int i2, long j, float f, float f2, int i3, IntOffset intOffset, IntOffset intOffset2, IntRect intRect, boolean z) {
        this.gridTileItem = gridTileItem;
        this.spanWidth = i;
        this.spanHeight = i2;
        this.spanOffset = j;
        this.extraHeight = f;
        this.expandedHeight = f2;
        this.expandedSpanHeight = i3;
        this.translationSpanOffset = intOffset;
        this.translationOffset = intOffset2;
        this.resizeSpanRect = intRect;
        this.isTarget = z;
        gridTileItem.setOwnerGridItem(this);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public QSPanelItem(GridTileItem gridTileItem, int i, int i2, long j, float f, float f2, int i3, IntOffset intOffset, IntOffset intOffset2, IntRect intRect, boolean z, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        long j2;
        float f3;
        float f4;
        float f5;
        float f6;
        int iMo2938getDefaultSpanSizeYbymL2g = (i4 & 2) != 0 ? (int) (gridTileItem.mo2938getDefaultSpanSizeYbymL2g() >> 32) : i;
        int iMo2938getDefaultSpanSizeYbymL2g2 = (i4 & 4) != 0 ? (int) (gridTileItem.mo2938getDefaultSpanSizeYbymL2g() & 4294967295L) : i2;
        if ((i4 & 8) != 0) {
            long j3 = -1;
            IntOffset.Companion companion = IntOffset.Companion;
            j2 = (4294967295L & j3) | (j3 << 32);
        } else {
            j2 = j;
        }
        if ((i4 & 16) != 0) {
            if (Intrinsics.areEqual(gridTileItem.getType(), "QuickTileDrawer")) {
                f6 = 10;
                Dp.Companion companion2 = Dp.Companion;
            } else {
                f6 = 0;
                Dp.Companion companion3 = Dp.Companion;
            }
            f3 = f6;
        } else {
            f3 = f;
        }
        if ((i4 & 32) != 0) {
            String type = gridTileItem.getType();
            if (Intrinsics.areEqual(type, "QuickTileDrawer")) {
                Dp.Companion.getClass();
                f5 = Dp.Infinity;
            } else if (Intrinsics.areEqual(type, "MediaPlayer")) {
                f5 = 300;
                Dp.Companion companion4 = Dp.Companion;
            } else {
                f5 = 0;
                Dp.Companion companion5 = Dp.Companion;
            }
            f4 = f5;
        } else {
            f4 = f2;
        }
        this(gridTileItem, iMo2938getDefaultSpanSizeYbymL2g, iMo2938getDefaultSpanSizeYbymL2g2, j2, f3, f4, (i4 & 64) != 0 ? 0 : i3, (i4 & 128) != 0 ? null : intOffset, (i4 & 256) != 0 ? null : intOffset2, (i4 & 512) != 0 ? null : intRect, (i4 & 1024) != 0 ? false : z, null);
    }
}
