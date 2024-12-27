package com.android.systemui.qs.footer.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.qs.FgsManagerController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

public final class ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ FgsManagerController $fgsManagerController$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ ForegroundServicesRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1(Continuation continuation, ForegroundServicesRepositoryImpl foregroundServicesRepositoryImpl, FgsManagerController fgsManagerController) {
        super(3, continuation);
        this.this$0 = foregroundServicesRepositoryImpl;
        this.$fgsManagerController$inlined = fgsManagerController;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1 foregroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1 = new ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$fgsManagerController$inlined);
        foregroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        foregroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return foregroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow distinctUntilChanged;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
                ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1 foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1 = new ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1(this.$fgsManagerController$inlined, null);
                conflatedCallbackFlow.getClass();
                final ChannelLimitedFlowMerge merge = FlowKt.merge(this.this$0.foregroundServicesCount, FlowConflatedKt.conflatedCallbackFlow(foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1));
                final FgsManagerController fgsManagerController = this.$fgsManagerController$inlined;
                distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$lambda$1$$inlined$map$1

                    /* renamed from: com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$lambda$1$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FgsManagerController $fgsManagerController$inlined;
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$lambda$1$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, FgsManagerController fgsManagerController) {
                            this.$this_unsafeFlow = flowCollector;
                            this.$fgsManagerController$inlined = fgsManagerController;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r4, kotlin.coroutines.Continuation r5) {
                            /*
                                r3 = this;
                                boolean r4 = r5 instanceof com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r4 == 0) goto L13
                                r4 = r5
                                com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$lambda$1$$inlined$map$1$2$1 r4 = (com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1) r4
                                int r0 = r4.label
                                r1 = -2147483648(0xffffffff80000000, float:-0.0)
                                r2 = r0 & r1
                                if (r2 == 0) goto L13
                                int r0 = r0 - r1
                                r4.label = r0
                                goto L18
                            L13:
                                com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$lambda$1$$inlined$map$1$2$1 r4 = new com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$lambda$1$$inlined$map$1$2$1
                                r4.<init>(r5)
                            L18:
                                java.lang.Object r5 = r4.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r1 = r4.label
                                r2 = 1
                                if (r1 == 0) goto L2f
                                if (r1 != r2) goto L27
                                kotlin.ResultKt.throwOnFailure(r5)
                                goto L47
                            L27:
                                java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
                                java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
                                r3.<init>(r4)
                                throw r3
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r5)
                                com.android.systemui.qs.FgsManagerController r5 = r3.$fgsManagerController$inlined
                                com.android.systemui.qs.FgsManagerControllerImpl r5 = (com.android.systemui.qs.FgsManagerControllerImpl) r5
                                boolean r5 = r5.newChangesSinceDialogWasDismissed
                                java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                                r4.label = r2
                                kotlinx.coroutines.flow.FlowCollector r3 = r3.$this_unsafeFlow
                                java.lang.Object r3 = r3.emit(r5, r4)
                                if (r3 != r0) goto L47
                                return r0
                            L47:
                                kotlin.Unit r3 = kotlin.Unit.INSTANCE
                                return r3
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$lambda$1$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, fgsManagerController), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                });
            } else {
                distinctUntilChanged = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
            this.label = 1;
            if (FlowKt.emitAll(this, distinctUntilChanged, flowCollector) == coroutineSingletons) {
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
