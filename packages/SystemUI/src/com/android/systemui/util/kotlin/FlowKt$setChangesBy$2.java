package com.android.systemui.util.kotlin;

import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class FlowKt$setChangesBy$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ Function3 $transform;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowKt$setChangesBy$2(Function3 function3, Continuation continuation) {
        super(3, continuation);
        this.$transform = function3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Set set = (Set) this.L$0;
            Set set2 = (Set) this.L$1;
            Set minus = SetsKt___SetsKt.minus(set, (Iterable) set2);
            Set minus2 = SetsKt___SetsKt.minus(set2, (Iterable) set);
            Function3 function3 = this.$transform;
            this.L$0 = null;
            this.label = 1;
            obj = function3.invoke(minus, minus2, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Set<Object> set, Set<Object> set2, Continuation continuation) {
        FlowKt$setChangesBy$2 flowKt$setChangesBy$2 = new FlowKt$setChangesBy$2(this.$transform, continuation);
        flowKt$setChangesBy$2.L$0 = set;
        flowKt$setChangesBy$2.L$1 = set2;
        return flowKt$setChangesBy$2.invokeSuspend(Unit.INSTANCE);
    }
}
