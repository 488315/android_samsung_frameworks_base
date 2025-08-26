package com.android.systemui.kairos;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.ChannelFlowBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public abstract class ToColdFlowKt {

    /* renamed from: com.android.systemui.kairos.ToColdFlowKt$toColdConflatedFlow$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ KairosNetwork $network;
        final /* synthetic */ Events $this_toColdConflatedFlow;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(KairosNetwork kairosNetwork, Events events, Continuation continuation) {
            super(2, continuation);
            this.$network = kairosNetwork;
            this.$this_toColdConflatedFlow = events;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$network, this.$this_toColdConflatedFlow, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ProducerScope producerScope = (ProducerScope) this.L$0;
                KairosNetwork kairosNetwork = this.$network;
                ToColdFlowKt$toColdConflatedFlow$1$$ExternalSyntheticLambda0 toColdFlowKt$toColdConflatedFlow$1$$ExternalSyntheticLambda0 = new ToColdFlowKt$toColdConflatedFlow$1$$ExternalSyntheticLambda0(this.$this_toColdConflatedFlow, producerScope, 0);
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

    /* renamed from: com.android.systemui.kairos.ToColdFlowKt$toColdConflatedFlow$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ KairosNetwork $network;
        final /* synthetic */ State $this_toColdConflatedFlow;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(KairosNetwork kairosNetwork, State state, Continuation continuation) {
            super(2, continuation);
            this.$network = kairosNetwork;
            this.$this_toColdConflatedFlow = state;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$network, this.$this_toColdConflatedFlow, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

    public static final Flow toColdConflatedFlow(Events events, KairosNetwork kairosNetwork) {
        return FlowKt.buffer$default(new ChannelFlowBuilder(new AnonymousClass1(kairosNetwork, events, null), null, 0, null, 14, null), -1, 2);
    }

    public static final Flow toColdConflatedFlow(State state, KairosNetwork kairosNetwork) {
        return FlowKt.buffer$default(new ChannelFlowBuilder(new AnonymousClass2(kairosNetwork, state, null), null, 0, null, 14, null), -1, 2);
    }
}
