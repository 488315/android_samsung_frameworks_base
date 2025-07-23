package com.android.compose.gesture;

import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface NestedDraggable {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Controller {
        default boolean isReadyToDrag() {
            return true;
        }

        float onDrag(float f);

        Object onDragStopped(float f, Function1 function1, SuspendLambda suspendLambda);
    }
}
