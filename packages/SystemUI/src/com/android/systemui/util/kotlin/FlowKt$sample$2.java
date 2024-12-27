package com.android.systemui.util.kotlin;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class FlowKt$sample$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;

    public FlowKt$sample$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.L$0;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Continuation continuation) {
        FlowKt$sample$2 flowKt$sample$2 = new FlowKt$sample$2(continuation);
        flowKt$sample$2.L$0 = obj2;
        return flowKt$sample$2.invokeSuspend(Unit.INSTANCE);
    }
}
