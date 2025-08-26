package com.android.systemui.keyguard.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public final class TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 implements Flow {
    public final /* synthetic */ Function1 $predicate$inlined$1;
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ TransitionInteractor this$0;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ Function1 $predicate$inlined$1;
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ TransitionInteractor this$0;

        /* renamed from: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1$2$1, reason: invalid class name */
        public final class AnonymousClass1 extends ContinuationImpl {
            Object L$0;
            Object L$1;
            int label;
            /* synthetic */ Object result;

            public AnonymousClass1(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass2.this.emit(null, this);
            }
        }

        public AnonymousClass2(FlowCollector flowCollector, TransitionInteractor transitionInteractor, Function1 function1) {
            this.$this_unsafeFlow = flowCollector;
            this.this$0 = transitionInteractor;
            this.$predicate$inlined$1 = function1;
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1;
            if (continuation instanceof AnonymousClass1) {
                anonymousClass1 = (AnonymousClass1) continuation;
                int i = anonymousClass1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    anonymousClass1.label = i - Integer.MIN_VALUE;
                } else {
                    anonymousClass1 = new AnonymousClass1(continuation);
                }
            }
            Object obj2 = anonymousClass1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = anonymousClass1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj2);
                TransitionInteractor transitionInteractor = this.this$0;
                if (transitionInteractor.getInternalTransitionInteractor().currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to == transitionInteractor.fromState && ((Boolean) this.$predicate$inlined$1.mo781invoke(obj)).booleanValue()) {
                    anonymousClass1.label = 1;
                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj2);
            }
            return Unit.INSTANCE;
        }
    }

    public TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(Flow flow, TransitionInteractor transitionInteractor, Function1 function1) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = transitionInteractor;
        this.$predicate$inlined$1 = function1;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object objCollect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.this$0, this.$predicate$inlined$1), continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }
}
