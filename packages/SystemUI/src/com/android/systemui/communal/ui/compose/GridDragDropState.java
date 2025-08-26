package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.unit.IntRect;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import java.util.ListIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class GridDragDropState {
    public final GridDragDropStateV1 dragDropState;

    public GridDragDropState(LazyGridState lazyGridState, ContentListState contentListState, CoroutineScope coroutineScope, float f, Function1 function1) {
        this.dragDropState = new GridDragDropStateV1(lazyGridState, contentListState, coroutineScope, function1);
    }

    public final void onDragInterrupted() {
        GridDragDropStateV1 gridDragDropStateV1 = this.dragDropState;
        String draggingItemKey = gridDragDropStateV1.getDraggingItemKey();
        ContentListState contentListState = gridDragDropStateV1.contentListState;
        if (draggingItemKey != null) {
            if (gridDragDropStateV1.isDraggingToRemove()) {
                ListIterator listIterator = contentListState.list.listIterator();
                int i = 0;
                while (true) {
                    if (!listIterator.hasNext()) {
                        i = -1;
                        break;
                    } else if (Intrinsics.areEqual(((CommunalContentModel) listIterator.next()).getKey(), gridDragDropStateV1.getDraggingItemKey())) {
                        break;
                    } else {
                        i++;
                    }
                }
                contentListState.onRemove(i);
                gridDragDropStateV1.setDraggingToRemove(false);
                IntRect.Companion.getClass();
                gridDragDropStateV1.updateDragPositionForRemove.mo781invoke(IntRect.Zero);
            }
            ContentListState.onSaveList$default(contentListState);
            ((SnapshotMutableStateImpl) gridDragDropStateV1.draggingItemKey$delegate).setValue(null);
        }
        gridDragDropStateV1.previousTargetItemKey = null;
        Offset.Companion.getClass();
        ((SnapshotMutableStateImpl) gridDragDropStateV1.draggingItemDraggedDelta$delegate).setValue(Offset.m395boximpl(0L));
        ((SnapshotMutableStateImpl) gridDragDropStateV1.draggingItemInitialOffset$delegate).setValue(Offset.m395boximpl(0L));
        Integer num = gridDragDropStateV1.spacerIndex;
        if (num != null) {
            contentListState.list.remove(num.intValue());
            gridDragDropStateV1.spacerIndex = null;
        }
    }
}
