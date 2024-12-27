package com.android.systemui.keyguard.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ TransitionInteractor this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ TransitionInteractor this$0;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1$2$1, reason: invalid class name */
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

        public AnonymousClass2(FlowCollector flowCollector, TransitionInteractor transitionInteractor) {
            this.$this_unsafeFlow = flowCollector;
            this.this$0 = transitionInteractor;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
            /*
                r4 = this;
                boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                if (r0 == 0) goto L13
                r0 = r6
                com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1$2$1
                r0.<init>(r6)
            L18:
                java.lang.Object r6 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L2f
                if (r2 != r3) goto L27
                kotlin.ResultKt.throwOnFailure(r6)
                goto L51
            L27:
                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                r4.<init>(r5)
                throw r4
            L2f:
                kotlin.ResultKt.throwOnFailure(r6)
                com.android.systemui.keyguard.domain.interactor.TransitionInteractor r6 = r4.this$0
                com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r2 = r6.transitionInteractor
                kotlinx.coroutines.flow.ReadonlyStateFlow r2 = r2.currentTransitionInfoInternal
                kotlinx.coroutines.flow.StateFlow r2 = r2.$$delegate_0
                java.lang.Object r2 = r2.getValue()
                com.android.systemui.keyguard.shared.model.TransitionInfo r2 = (com.android.systemui.keyguard.shared.model.TransitionInfo) r2
                com.android.systemui.keyguard.shared.model.KeyguardState r2 = r2.to
                com.android.systemui.keyguard.shared.model.KeyguardState r6 = r6.fromState
                if (r2 != r6) goto L51
                r0.label = r3
                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                java.lang.Object r4 = r4.emit(r5, r0)
                if (r4 != r1) goto L51
                return r1
            L51:
                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1(Flow flow, TransitionInteractor transitionInteractor) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = transitionInteractor;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.this$0), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
