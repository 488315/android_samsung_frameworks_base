package com.android.systemui.communal.ui.compose;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpSize;
import androidx.compose.ui.unit.IntSize;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SizeInfo {
    public final long cellSize;
    public final PaddingValues contentPadding;
    public final long gridSize;
    public final float maxHeight;
    public final float verticalArrangement;

    public /* synthetic */ SizeInfo(long j, float f, long j2, PaddingValues paddingValues, float f2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, f, j2, paddingValues, f2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SizeInfo) {
            SizeInfo sizeInfo = (SizeInfo) obj;
            long j = sizeInfo.cellSize;
            DpSize.Companion companion = DpSize.Companion;
            if (this.cellSize == j && Dp.m836equalsimpl0(this.verticalArrangement, sizeInfo.verticalArrangement) && IntSize.m861equalsimpl0(this.gridSize, sizeInfo.gridSize) && Intrinsics.areEqual(this.contentPadding, sizeInfo.contentPadding) && Dp.m836equalsimpl0(this.maxHeight, sizeInfo.maxHeight)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        DpSize.Companion companion = DpSize.Companion;
        int hashCode = Long.hashCode(this.cellSize) * 31;
        Dp.Companion companion2 = Dp.Companion;
        int m = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.verticalArrangement, hashCode, 31);
        IntSize.Companion companion3 = IntSize.Companion;
        return Float.hashCode(this.maxHeight) + ((this.contentPadding.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(m, 31, this.gridSize)) * 31);
    }

    public final String toString() {
        String m846toStringimpl = DpSize.m846toStringimpl(this.cellSize);
        String m837toStringimpl = Dp.m837toStringimpl(this.verticalArrangement);
        String m862toStringimpl = IntSize.m862toStringimpl(this.gridSize);
        String m837toStringimpl2 = Dp.m837toStringimpl(this.maxHeight);
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("SizeInfo(cellSize=", m846toStringimpl, ", verticalArrangement=", m837toStringimpl, ", gridSize=");
        m.append(m862toStringimpl);
        m.append(", contentPadding=");
        m.append(this.contentPadding);
        m.append(", maxHeight=");
        m.append(m837toStringimpl2);
        m.append(")");
        return m.toString();
    }

    private SizeInfo(long j, float f, long j2, PaddingValues paddingValues, float f2) {
        this.cellSize = j;
        this.verticalArrangement = f;
        this.gridSize = j2;
        this.contentPadding = paddingValues;
        this.maxHeight = f2;
    }
}
