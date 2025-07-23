package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridState;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
