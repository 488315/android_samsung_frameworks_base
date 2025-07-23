package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.GraphKt;
import com.android.systemui.kairos.internal.InputNode;
import com.android.systemui.kairos.internal.TransactionCache;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CoalescingMutableEvents$emit$3 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CoalescingMutableEvents this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CoalescingMutableEvents$emit$3(CoalescingMutableEvents coalescingMutableEvents, Continuation continuation) {
        super(2, continuation);
        this.this$0 = coalescingMutableEvents;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CoalescingMutableEvents$emit$3 coalescingMutableEvents$emit$3 = new CoalescingMutableEvents$emit$3(this.this$0, continuation);
        coalescingMutableEvents$emit$3.L$0 = obj;
        return coalescingMutableEvents$emit$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CoalescingMutableEvents$emit$3) create((EvalScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        EvalScope evalScope = (EvalScope) this.L$0;
        CoalescingMutableEvents coalescingMutableEvents = this.this$0;
        Lazy lazy = (Lazy) ((Pair) coalescingMutableEvents.storage.getAndSet(new Pair(Boolean.FALSE, LazyKt__LazyJVMKt.lazy(new CoalescingMutableEvents$$ExternalSyntheticLambda0(coalescingMutableEvents, 1))))).component2();
        InputNode inputNode = this.this$0.impl;
        Object value = lazy.getValue();
        TransactionCache transactionCache = inputNode.transactionCache;
        transactionCache.getClass();
        transactionCache.epoch = evalScope.getEpoch();
        evalScope.getTransactionStore().set(transactionCache.key, value);
        if (!GraphKt.scheduleAll(inputNode.downstreamSet, evalScope)) {
            evalScope.scheduleDeactivation(inputNode);
        }
        return Unit.INSTANCE;
    }
}
