package com.android.compose.gesture;

import androidx.compose.ui.unit.Velocity;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
final class NestedDraggableNode$flingWithOverscroll$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $performFling;
    /* synthetic */ long J$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NestedDraggableNode$flingWithOverscroll$3(Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$performFling = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NestedDraggableNode$flingWithOverscroll$3 nestedDraggableNode$flingWithOverscroll$3 = new NestedDraggableNode$flingWithOverscroll$3(this.$performFling, continuation);
        nestedDraggableNode$flingWithOverscroll$3.J$0 = ((Velocity) obj).packedValue;
        return nestedDraggableNode$flingWithOverscroll$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NestedDraggableNode$flingWithOverscroll$3) create(Velocity.m878boximpl(((Velocity) obj).packedValue), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
        long j = this.J$0;
        Function2 function2 = this.$performFling;
        Velocity velocityM878boximpl = Velocity.m878boximpl(j);
        this.label = 1;
        Object objInvoke = function2.invoke(velocityM878boximpl, this);
        return objInvoke == coroutineSingletons ? coroutineSingletons : objInvoke;
    }
}
