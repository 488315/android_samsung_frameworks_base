package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridItemInfo;
import androidx.compose.foundation.lazy.grid.LazyGridMeasureResult;
import androidx.compose.foundation.lazy.grid.LazyGridMeasuredItem;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.unit.IntOffset;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class GridDragDropStateInternal {
    public final MutableState draggingItemDraggedDelta$delegate;
    public final MutableState draggingItemInitialOffset$delegate;
    public final MutableState draggingItemKey$delegate = SnapshotStateKt.mutableStateOf$default(null);
    public final MutableState isDraggingToRemove$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
    public final LazyGridState state;

    public GridDragDropStateInternal(LazyGridState lazyGridState) {
        this.state = lazyGridState;
        Offset.Companion.getClass();
        this.draggingItemDraggedDelta$delegate = SnapshotStateKt.mutableStateOf$default(Offset.m393boximpl(0L));
        this.draggingItemInitialOffset$delegate = SnapshotStateKt.mutableStateOf$default(Offset.m393boximpl(0L));
    }

    /* renamed from: getDraggingItemDraggedDelta-F1C5BW0, reason: not valid java name */
    public final long m1083getDraggingItemDraggedDeltaF1C5BW0() {
        return ((Offset) ((SnapshotMutableStateImpl) this.draggingItemDraggedDelta$delegate).getValue()).packedValue;
    }

    public final String getDraggingItemKey() {
        return (String) ((SnapshotMutableStateImpl) this.draggingItemKey$delegate).getValue();
    }

    public final LazyGridItemInfo getDraggingItemLayoutInfo() {
        Object obj;
        Iterator it = ((LazyGridMeasureResult) this.state.getLayoutInfo()).visibleItemsInfo.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((LazyGridMeasuredItem) ((LazyGridItemInfo) obj)).key, getDraggingItemKey())) {
                break;
            }
        }
        return (LazyGridItemInfo) obj;
    }

    /* renamed from: getDraggingItemOffset-F1C5BW0, reason: not valid java name */
    public final long m1084getDraggingItemOffsetF1C5BW0() {
        LazyGridItemInfo draggingItemLayoutInfo = getDraggingItemLayoutInfo();
        if (draggingItemLayoutInfo == null) {
            Offset.Companion.getClass();
            return 0L;
        }
        long m401plusMKHz9U = Offset.m401plusMKHz9U(((Offset) ((SnapshotMutableStateImpl) this.draggingItemInitialOffset$delegate).getValue()).packedValue, m1083getDraggingItemDraggedDeltaF1C5BW0());
        long j = ((LazyGridMeasuredItem) draggingItemLayoutInfo).offset;
        IntOffset.Companion companion = IntOffset.Companion;
        return Offset.m400minusMKHz9U(m401plusMKHz9U, (Float.floatToRawIntBits((int) (j & 4294967295L)) & 4294967295L) | (Float.floatToRawIntBits((int) (j >> 32)) << 32));
    }

    public final boolean isDraggingToRemove() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.isDraggingToRemove$delegate).getValue()).booleanValue();
    }

    public final void setDraggingToRemove(boolean z) {
        ((SnapshotMutableStateImpl) this.isDraggingToRemove$delegate).setValue(Boolean.valueOf(z));
    }
}
