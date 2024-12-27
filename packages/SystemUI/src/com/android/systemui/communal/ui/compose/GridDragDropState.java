package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridItemInfo;
import androidx.compose.foundation.lazy.grid.LazyGridMeasuredItem;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.runtime.ParcelableSnapshotMutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.unit.IntOffset;
import java.util.Iterator;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class GridDragDropState {
    public final ContentListState contentListState;
    public final ParcelableSnapshotMutableState dragStartPointerOffset$delegate;
    public final ParcelableSnapshotMutableState draggingItemDraggedDelta$delegate;
    public final ParcelableSnapshotMutableState draggingItemIndex$delegate;
    public final ParcelableSnapshotMutableState draggingItemInitialOffset$delegate;
    public final ParcelableSnapshotMutableState isDraggingToRemove$delegate;
    public final CoroutineScope scope;
    public final BufferedChannel scrollChannel;
    public final LazyGridState state;
    public final Function1 updateDragPositionForRemove;

    public GridDragDropState(LazyGridState lazyGridState, ContentListState contentListState, CoroutineScope coroutineScope, Function1 function1) {
        this.state = lazyGridState;
        this.contentListState = contentListState;
        this.scope = coroutineScope;
        this.updateDragPositionForRemove = function1;
        StructuralEqualityPolicy structuralEqualityPolicy = StructuralEqualityPolicy.INSTANCE;
        this.draggingItemIndex$delegate = SnapshotStateKt.mutableStateOf(null, structuralEqualityPolicy);
        this.isDraggingToRemove$delegate = SnapshotStateKt.mutableStateOf(Boolean.FALSE, structuralEqualityPolicy);
        this.scrollChannel = ChannelKt.Channel$default(0, null, null, 7);
        Offset.Companion.getClass();
        this.draggingItemDraggedDelta$delegate = SnapshotStateKt.mutableStateOf(Offset.m320boximpl(0L), structuralEqualityPolicy);
        this.draggingItemInitialOffset$delegate = SnapshotStateKt.mutableStateOf(Offset.m320boximpl(0L), structuralEqualityPolicy);
        this.dragStartPointerOffset$delegate = SnapshotStateKt.mutableStateOf(Offset.m320boximpl(0L), structuralEqualityPolicy);
    }

    public final LazyGridItemInfo getDraggingItemLayoutInfo() {
        Object obj;
        Iterator it = this.state.getLayoutInfo().visibleItemsInfo.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            int i = ((LazyGridMeasuredItem) ((LazyGridItemInfo) obj)).index;
            Integer num = (Integer) this.draggingItemIndex$delegate.getValue();
            if (num != null && i == num.intValue()) {
                break;
            }
        }
        return (LazyGridItemInfo) obj;
    }

    /* renamed from: getDraggingItemOffset-F1C5BW0$frameworks__base__packages__SystemUI__android_common__SystemUI_core, reason: not valid java name */
    public final long m943x6f1c0cc2() {
        LazyGridItemInfo draggingItemLayoutInfo = getDraggingItemLayoutInfo();
        if (draggingItemLayoutInfo == null) {
            Offset.Companion.getClass();
            return 0L;
        }
        long m329plusMKHz9U = Offset.m329plusMKHz9U(((Offset) this.draggingItemInitialOffset$delegate.getValue()).packedValue, ((Offset) this.draggingItemDraggedDelta$delegate.getValue()).packedValue);
        long j = ((LazyGridMeasuredItem) draggingItemLayoutInfo).offset;
        IntOffset.Companion companion = IntOffset.Companion;
        return Offset.m328minusMKHz9U(m329plusMKHz9U, OffsetKt.Offset((int) (j >> 32), (int) (j & 4294967295L)));
    }

    public final void onDragInterrupted$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        ParcelableSnapshotMutableState parcelableSnapshotMutableState = this.draggingItemIndex$delegate;
        Integer num = (Integer) parcelableSnapshotMutableState.getValue();
        if (num != null) {
            int intValue = num.intValue();
            ParcelableSnapshotMutableState parcelableSnapshotMutableState2 = this.isDraggingToRemove$delegate;
            boolean booleanValue = ((Boolean) parcelableSnapshotMutableState2.getValue()).booleanValue();
            ContentListState contentListState = this.contentListState;
            if (booleanValue) {
                contentListState.onRemove(intValue);
                parcelableSnapshotMutableState2.setValue(Boolean.FALSE);
                Offset.Companion.getClass();
                this.updateDragPositionForRemove.invoke(Offset.m320boximpl(0L));
            }
            contentListState.onSaveList(null, null, null);
            parcelableSnapshotMutableState.setValue(null);
        }
        Offset.Companion.getClass();
        this.draggingItemDraggedDelta$delegate.setValue(Offset.m320boximpl(0L));
        this.draggingItemInitialOffset$delegate.setValue(Offset.m320boximpl(0L));
        this.dragStartPointerOffset$delegate.setValue(Offset.m320boximpl(0L));
    }
}
