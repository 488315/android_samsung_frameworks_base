package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SwipeToDismissInteractor {
    public final ReadonlyStateFlow dismissFling;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor transitionInteractor;

    public SwipeToDismissInteractor(CoroutineScope coroutineScope, ShadeRepository shadeRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor) {
        this.transitionInteractor = keyguardTransitionInteractor;
        this.keyguardInteractor = keyguardInteractor;
        final SharedFlowImpl sharedFlowImpl = ((ShadeRepositoryImpl) shadeRepository).currentFling;
        Flow flow = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.SwipeToDismissInteractor$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.SwipeToDismissInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ SwipeToDismissInteractor this$0;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.SwipeToDismissInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, SwipeToDismissInteractor swipeToDismissInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = swipeToDismissInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.keyguard.domain.interactor.SwipeToDismissInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.keyguard.domain.interactor.SwipeToDismissInteractor$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.SwipeToDismissInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.SwipeToDismissInteractor$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.SwipeToDismissInteractor$special$$inlined$filter$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L78
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        r7 = r6
                        com.android.systemui.shade.data.repository.FlingInfo r7 = (com.android.systemui.shade.data.repository.FlingInfo) r7
                        if (r7 == 0) goto L78
                        boolean r7 = r7.expand
                        if (r7 != 0) goto L78
                        com.android.systemui.keyguard.domain.interactor.SwipeToDismissInteractor r7 = r5.this$0
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r2 = r7.keyguardInteractor
                        kotlinx.coroutines.flow.ReadonlyStateFlow r2 = r2.statusBarState
                        kotlinx.coroutines.flow.StateFlow r2 = r2.$$delegate_0
                        java.lang.Object r2 = r2.getValue()
                        com.android.systemui.keyguard.shared.model.StatusBarState r4 = com.android.systemui.keyguard.shared.model.StatusBarState.SHADE_LOCKED
                        if (r2 == r4) goto L78
                        com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r2 = r7.transitionInteractor
                        kotlinx.coroutines.flow.ReadonlyStateFlow r2 = r2.startedKeyguardTransitionStep
                        kotlinx.coroutines.flow.StateFlow r2 = r2.$$delegate_0
                        java.lang.Object r2 = r2.getValue()
                        com.android.systemui.keyguard.shared.model.TransitionStep r2 = (com.android.systemui.keyguard.shared.model.TransitionStep) r2
                        com.android.systemui.keyguard.shared.model.KeyguardState r2 = r2.to
                        com.android.systemui.keyguard.shared.model.KeyguardState r4 = com.android.systemui.keyguard.shared.model.KeyguardState.LOCKSCREEN
                        if (r2 != r4) goto L78
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r7 = r7.keyguardInteractor
                        kotlinx.coroutines.flow.StateFlowImpl r7 = r7.isKeyguardDismissible
                        java.lang.Object r7 = r7.getValue()
                        java.lang.Boolean r7 = (java.lang.Boolean) r7
                        boolean r7 = r7.booleanValue()
                        if (r7 == 0) goto L78
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r6, r0)
                        if (r5 != r1) goto L78
                        return r1
                    L78:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.SwipeToDismissInteractor$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.dismissFling = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, null);
    }
}
