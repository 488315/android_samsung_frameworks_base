package com.android.compose.gesture;

import com.android.compose.gesture.NestedDraggableNode;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class NestedDraggableNode$onPreFling$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ long $available;
    final /* synthetic */ NestedDraggableNode.NestedScrollController $controller;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NestedDraggableNode$onPreFling$2(NestedDraggableNode.NestedScrollController nestedScrollController, long j, Continuation continuation) {
        super(2, continuation);
        this.$controller = nestedScrollController;
        this.$available = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NestedDraggableNode$onPreFling$2(this.$controller, this.$available, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NestedDraggableNode$onPreFling$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        NestedDraggableNode.NestedScrollController nestedScrollController = this.$controller;
        long j = this.$available;
        this.label = 1;
        Object objM937flingWithOverscrollQWom1Mo = nestedScrollController.m937flingWithOverscrollQWom1Mo(j, this);
        return objM937flingWithOverscrollQWom1Mo == coroutineSingletons ? coroutineSingletons : objM937flingWithOverscrollQWom1Mo;
    }
}
