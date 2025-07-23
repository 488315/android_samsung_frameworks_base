package com.android.systemui.util.kotlin;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class Utils$Companion$combineState$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Function2 $transform;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Utils$Companion$combineState$1(Function2 function2, Continuation continuation) {
        super(3, continuation);
        this.$transform = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.$transform.invoke(this.L$0, this.L$1);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Continuation continuation) {
        Utils$Companion$combineState$1 utils$Companion$combineState$1 = new Utils$Companion$combineState$1(this.$transform, continuation);
        utils$Companion$combineState$1.L$0 = obj;
        utils$Companion$combineState$1.L$1 = obj2;
        return utils$Companion$combineState$1.invokeSuspend(Unit.INSTANCE);
    }
}
