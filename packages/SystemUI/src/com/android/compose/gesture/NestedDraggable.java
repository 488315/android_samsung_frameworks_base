package com.android.compose.gesture;

import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public interface NestedDraggable {

    public interface Controller {
        default boolean isReadyToDrag() {
            return true;
        }

        float onDrag(float f);

        Object onDragStopped(float f, Function1 function1, SuspendLambda suspendLambda);
    }
}
