package com.android.systemui.communal.ui.compose;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class DragAndDropTargetStateKt$rememberDragAndDropTargetState$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ CoroutineScope $scope;
    final /* synthetic */ DragAndDropTargetState $state;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DragAndDropTargetStateKt$rememberDragAndDropTargetState$1$1(DragAndDropTargetState dragAndDropTargetState, CoroutineScope coroutineScope, Continuation continuation) {
        super(2, continuation);
        this.$state = dragAndDropTargetState;
        this.$scope = coroutineScope;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DragAndDropTargetStateKt$rememberDragAndDropTargetState$1$1(this.$state, this.$scope, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DragAndDropTargetStateKt$rememberDragAndDropTargetState$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DragAndDropTargetState dragAndDropTargetState = this.$state;
            this.label = 1;
            Object processScrollRequests = dragAndDropTargetState.dragDropState.processScrollRequests(this);
            if (processScrollRequests != coroutineSingletons) {
                processScrollRequests = Unit.INSTANCE;
            }
            if (processScrollRequests == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
