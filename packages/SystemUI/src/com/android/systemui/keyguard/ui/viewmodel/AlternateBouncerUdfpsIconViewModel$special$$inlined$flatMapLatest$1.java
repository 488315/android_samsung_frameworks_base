package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class AlternateBouncerUdfpsIconViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ FingerprintPropertyInteractor $fingerprintPropertyInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public AlternateBouncerUdfpsIconViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, FingerprintPropertyInteractor fingerprintPropertyInteractor) {
        super(3, continuation);
        this.$fingerprintPropertyInteractor$inlined = fingerprintPropertyInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AlternateBouncerUdfpsIconViewModel$special$$inlined$flatMapLatest$1 alternateBouncerUdfpsIconViewModel$special$$inlined$flatMapLatest$1 = new AlternateBouncerUdfpsIconViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$fingerprintPropertyInteractor$inlined);
        alternateBouncerUdfpsIconViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        alternateBouncerUdfpsIconViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return alternateBouncerUdfpsIconViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                final Flow flow2 = this.$fingerprintPropertyInteractor$inlined.udfpsSensorBounds;
                flow = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$iconLocation$lambda$2$$inlined$map$1

                    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$iconLocation$lambda$2$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$iconLocation$lambda$2$$inlined$map$1$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
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

                        public AnonymousClass2(FlowCollector flowCollector) {
                            this.$this_unsafeFlow = flowCollector;
                        }

                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                            /*
                                r6 = this;
                                boolean r0 = r8 instanceof com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$iconLocation$lambda$2$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r8
                                com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$iconLocation$lambda$2$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$iconLocation$lambda$2$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$iconLocation$lambda$2$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$iconLocation$lambda$2$$inlined$map$1$2$1
                                r0.<init>(r8)
                            L18:
                                java.lang.Object r8 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r8)
                                goto L4c
                            L27:
                                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                                r6.<init>(r7)
                                throw r6
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r8)
                                android.graphics.Rect r7 = (android.graphics.Rect) r7
                                com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$IconLocation r8 = new com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$IconLocation
                                int r2 = r7.left
                                int r4 = r7.top
                                int r5 = r7.right
                                int r7 = r7.bottom
                                r8.<init>(r2, r4, r5, r7)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                                java.lang.Object r6 = r6.emit(r8, r0)
                                if (r6 != r1) goto L4c
                                return r1
                            L4c:
                                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                return r6
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$iconLocation$lambda$2$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
            } else {
                flow = EmptyFlow.INSTANCE;
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
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
