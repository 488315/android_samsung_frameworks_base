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
            if (this.cellSize == j && Dp.m838equalsimpl0(this.verticalArrangement, sizeInfo.verticalArrangement) && IntSize.m863equalsimpl0(this.gridSize, sizeInfo.gridSize) && Intrinsics.areEqual(this.contentPadding, sizeInfo.contentPadding) && Dp.m838equalsimpl0(this.maxHeight, sizeInfo.maxHeight)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        DpSize.Companion companion = DpSize.Companion;
        int iHashCode = Long.hashCode(this.cellSize) * 31;
        Dp.Companion companion2 = Dp.Companion;
        int iM = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.verticalArrangement, iHashCode, 31);
        IntSize.Companion companion3 = IntSize.Companion;
        return Float.hashCode(this.maxHeight) + ((this.contentPadding.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(iM, 31, this.gridSize)) * 31);
    }

    public final String toString() {
        String strM848toStringimpl = DpSize.m848toStringimpl(this.cellSize);
        String strM839toStringimpl = Dp.m839toStringimpl(this.verticalArrangement);
        String strM864toStringimpl = IntSize.m864toStringimpl(this.gridSize);
        String strM839toStringimpl2 = Dp.m839toStringimpl(this.maxHeight);
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("SizeInfo(cellSize=", strM848toStringimpl, ", verticalArrangement=", strM839toStringimpl, ", gridSize=");
        sbM.append(strM864toStringimpl);
        sbM.append(", contentPadding=");
        sbM.append(this.contentPadding);
        sbM.append(", maxHeight=");
        sbM.append(strM839toStringimpl2);
        sbM.append(")");
        return sbM.toString();
    }

    private SizeInfo(long j, float f, long j2, PaddingValues paddingValues, float f2) {
        this.cellSize = j;
        this.verticalArrangement = f;
        this.gridSize = j2;
        this.contentPadding = paddingValues;
        this.maxHeight = f2;
    }
}
