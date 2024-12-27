package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.runtime.MutableState;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScope;

public final class DragAndDropTargetState {
    public final MutableState autoScrollSpeed;
    public final float autoScrollThreshold;
    public final ContentListState contentListState;
    public boolean isOnRemoveButton;
    public final CommunalContentModel.WidgetPlaceholder placeHolder = new CommunalContentModel.WidgetPlaceholder();
    public Integer placeHolderIndex;
    public final CoroutineScope scope;
    public final LazyGridState state;
    public final Function1 updateDragPositionForRemove;

    public DragAndDropTargetState(LazyGridState lazyGridState, ContentListState contentListState, CoroutineScope coroutineScope, MutableState mutableState, float f, Function1 function1) {
        this.state = lazyGridState;
        this.contentListState = contentListState;
        this.scope = coroutineScope;
        this.autoScrollSpeed = mutableState;
        this.autoScrollThreshold = f;
        this.updateDragPositionForRemove = function1;
    }
}
