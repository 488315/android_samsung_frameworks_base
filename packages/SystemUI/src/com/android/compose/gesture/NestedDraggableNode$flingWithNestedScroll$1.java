package com.android.compose.gesture;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class NestedDraggableNode$flingWithNestedScroll$1 extends ContinuationImpl {
    long J$0;
    long J$1;
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ NestedDraggableNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NestedDraggableNode$flingWithNestedScroll$1(NestedDraggableNode nestedDraggableNode, Continuation continuation) {
        super(continuation);
        this.this$0 = nestedDraggableNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return NestedDraggableNode.m927access$flingWithNestedScrollTK7Wm2c(this.this$0, 0L, null, this);
    }
}
