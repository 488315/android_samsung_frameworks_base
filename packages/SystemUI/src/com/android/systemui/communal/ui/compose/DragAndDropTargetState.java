package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridState;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class DragAndDropTargetState {
    public final DragAndDropTargetStateV1 dragDropState;

    public /* synthetic */ DragAndDropTargetState(LazyGridState lazyGridState, long j, ContentListState contentListState, float f, CoroutineScope coroutineScope, DefaultConstructorMarker defaultConstructorMarker) {
        this(lazyGridState, j, contentListState, f, coroutineScope);
    }

    private DragAndDropTargetState(LazyGridState lazyGridState, long j, ContentListState contentListState, float f, CoroutineScope coroutineScope) {
        this.dragDropState = new DragAndDropTargetStateV1(lazyGridState, j, contentListState, f, coroutineScope, null);
    }
}
