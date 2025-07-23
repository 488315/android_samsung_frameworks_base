package com.android.compose.gesture;

import androidx.compose.foundation.OverscrollKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.Modifier;
import com.android.compose.animation.scene.DraggableHandler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class NestedDraggableKt {
    public static final Modifier nestedDraggable(Modifier modifier, DraggableHandler draggableHandler, Orientation orientation, DraggableHandler.DelegatingOverscrollEffect delegatingOverscrollEffect, boolean z, boolean z2) {
        if (delegatingOverscrollEffect != null) {
            modifier = modifier.then(OverscrollKt.overscroll(Modifier.Companion, delegatingOverscrollEffect));
        }
        return modifier.then(new NestedDraggableElement(draggableHandler, orientation, delegatingOverscrollEffect, z, z2));
    }
}
