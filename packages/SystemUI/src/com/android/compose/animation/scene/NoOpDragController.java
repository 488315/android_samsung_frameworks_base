package com.android.compose.animation.scene;

import com.android.compose.gesture.NestedDraggable;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class NoOpDragController implements NestedDraggable.Controller {
    public static final NoOpDragController INSTANCE = new NoOpDragController();

    private NoOpDragController() {
    }

    @Override // com.android.compose.gesture.NestedDraggable.Controller
    public final float onDrag(float f) {
        return 0.0f;
    }

    @Override // com.android.compose.gesture.NestedDraggable.Controller
    public final Object onDragStopped(float f, Function1 function1, SuspendLambda suspendLambda) {
        return new Float(0.0f);
    }
}
