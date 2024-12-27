package com.android.systemui.util.kotlin;

import java.util.Map;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class ParallelKt$mapValuesParallel$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $transform;
    /* synthetic */ Object L$0;
    int label;

    public ParallelKt$mapValuesParallel$2(Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$transform = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ParallelKt$mapValuesParallel$2 parallelKt$mapValuesParallel$2 = new ParallelKt$mapValuesParallel$2(this.$transform, continuation);
        parallelKt$mapValuesParallel$2.L$0 = obj;
        return parallelKt$mapValuesParallel$2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Map.Entry entry = (Map.Entry) this.L$0;
            Object key = entry.getKey();
            Function2 function2 = this.$transform;
            this.L$0 = key;
            this.label = 1;
            obj = function2.invoke(entry, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj2 = key;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            obj2 = this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        return new Pair(obj2, obj);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Map.Entry<Object, Object> entry, Continuation continuation) {
        return ((ParallelKt$mapValuesParallel$2) create(entry, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
