package com.android.systemui.kairos;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ToColdFlowKt$toColdConflatedFlow$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ KairosNetwork $network;
    final /* synthetic */ State $this_toColdConflatedFlow;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ToColdFlowKt$toColdConflatedFlow$2(KairosNetwork kairosNetwork, State state, Continuation continuation) {
        super(2, continuation);
        this.$network = kairosNetwork;
        this.$this_toColdConflatedFlow = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ToColdFlowKt$toColdConflatedFlow$2 toColdFlowKt$toColdConflatedFlow$2 = new ToColdFlowKt$toColdConflatedFlow$2(this.$network, this.$this_toColdConflatedFlow, continuation);
        toColdFlowKt$toColdConflatedFlow$2.L$0 = obj;
        return toColdFlowKt$toColdConflatedFlow$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ToColdFlowKt$toColdConflatedFlow$2) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            KairosNetwork kairosNetwork = this.$network;
            ToColdFlowKt$toColdConflatedFlow$1$$ExternalSyntheticLambda0 toColdFlowKt$toColdConflatedFlow$1$$ExternalSyntheticLambda0 = new ToColdFlowKt$toColdConflatedFlow$1$$ExternalSyntheticLambda0(this.$this_toColdConflatedFlow, producerScope, 1);
            this.label = 1;
            if (kairosNetwork.activateSpec(toColdFlowKt$toColdConflatedFlow$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
