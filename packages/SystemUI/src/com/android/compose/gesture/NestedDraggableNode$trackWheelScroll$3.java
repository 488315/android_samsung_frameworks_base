package com.android.compose.gesture;

import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerEventType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class NestedDraggableNode$trackWheelScroll$3 extends RestrictedSuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NestedDraggableNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NestedDraggableNode$trackWheelScroll$3(NestedDraggableNode nestedDraggableNode, Continuation continuation) {
        super(2, continuation);
        this.this$0 = nestedDraggableNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NestedDraggableNode$trackWheelScroll$3 nestedDraggableNode$trackWheelScroll$3 = new NestedDraggableNode$trackWheelScroll$3(this.this$0, continuation);
        nestedDraggableNode$trackWheelScroll$3.L$0 = obj;
        return nestedDraggableNode$trackWheelScroll$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NestedDraggableNode$trackWheelScroll$3) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AwaitPointerEventScope awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
            PointerEventPass pointerEventPass = PointerEventPass.Initial;
            this.label = 1;
            obj = awaitPointerEventScope.awaitPointerEvent(pointerEventPass, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        NestedDraggableNode nestedDraggableNode = this.this$0;
        int i2 = ((PointerEvent) obj).type;
        PointerEventType.Companion.getClass();
        nestedDraggableNode.lastEventWasScrollWheel = i2 == PointerEventType.Scroll;
        return Unit.INSTANCE;
    }
}
