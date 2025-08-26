package com.android.compose.gesture;

import androidx.compose.foundation.OverscrollKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.Modifier;
import com.android.compose.animation.scene.DraggableHandler;

/* loaded from: classes.dex */
public abstract class NestedDraggableKt {
    public static final Modifier nestedDraggable(Modifier modifier, DraggableHandler draggableHandler, Orientation orientation, DraggableHandler.DelegatingOverscrollEffect delegatingOverscrollEffect, boolean z, boolean z2) {
        if (delegatingOverscrollEffect != null) {
            modifier = modifier.then(OverscrollKt.overscroll(Modifier.Companion, delegatingOverscrollEffect));
        }
        return modifier.then(new NestedDraggableElement(draggableHandler, orientation, delegatingOverscrollEffect, z, z2));
    }
}
