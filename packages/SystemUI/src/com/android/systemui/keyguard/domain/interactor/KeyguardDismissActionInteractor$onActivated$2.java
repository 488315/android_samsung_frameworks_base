package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.shared.model.DismissAction;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardDismissActionInteractor$onActivated$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardDismissActionInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ KeyguardDismissActionInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(KeyguardDismissActionInteractor keyguardDismissActionInteractor, Continuation continuation) {
            super(2, continuation);
            this.this$0 = keyguardDismissActionInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                KeyguardDismissActionInteractor keyguardDismissActionInteractor = this.this$0;
                KeyguardDismissActionInteractor$special$$inlined$map$5 keyguardDismissActionInteractor$special$$inlined$map$5 = keyguardDismissActionInteractor.finishedTransitionToGone;
                final SafeFlow safeFlow = keyguardDismissActionInteractor.isOnShadeWhileUnlocked;
                final Flow flow = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$filter$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector) {
                            this.$this_unsafeFlow = flowCollector;
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
                                boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$filter$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L46
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                r6 = r5
                                java.lang.Boolean r6 = (java.lang.Boolean) r6
                                boolean r6 = r6.booleanValue()
                                if (r6 == 0) goto L46
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L46
                                return r1
                            L46:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                ChannelLimitedFlowMerge merge = FlowKt.merge(keyguardDismissActionInteractor$special$$inlined$map$5, new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$map$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L44
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                java.lang.Boolean r5 = (java.lang.Boolean) r5
                                r5.getClass()
                                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L44
                                return r1
                            L44:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                });
                final KeyguardDismissActionInteractor keyguardDismissActionInteractor2 = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor.onActivated.2.1.3
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        KeyguardDismissActionInteractor keyguardDismissActionInteractor3 = KeyguardDismissActionInteractor.this;
                        KeyguardDismissActionInteractor.access$log(keyguardDismissActionInteractor3, "finishedTransitionToGone");
                        Object access$runDismissAction = KeyguardDismissActionInteractor.access$runDismissAction(keyguardDismissActionInteractor3, continuation);
                        return access$runDismissAction == CoroutineSingletons.COROUTINE_SUSPENDED ? access$runDismissAction : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (merge.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ KeyguardDismissActionInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(KeyguardDismissActionInteractor keyguardDismissActionInteractor, Continuation continuation) {
            super(2, continuation);
            this.this$0 = keyguardDismissActionInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final KeyguardDismissActionInteractor keyguardDismissActionInteractor = this.this$0;
                KeyguardDismissInteractor$special$$inlined$map$5 keyguardDismissInteractor$special$$inlined$map$5 = keyguardDismissActionInteractor.dismissInteractor.dismissKeyguardRequestWithImmediateDismissAction;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor.onActivated.2.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        KeyguardDismissActionInteractor keyguardDismissActionInteractor2 = KeyguardDismissActionInteractor.this;
                        KeyguardDismissActionInteractor.access$log(keyguardDismissActionInteractor2, "eventsThatRequireKeyguardDismissal");
                        Object access$runDismissAction = KeyguardDismissActionInteractor.access$runDismissAction(keyguardDismissActionInteractor2, continuation);
                        return access$runDismissAction == CoroutineSingletons.COROUTINE_SUSPENDED ? access$runDismissAction : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (keyguardDismissInteractor$special$$inlined$map$5.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ KeyguardDismissActionInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(KeyguardDismissActionInteractor keyguardDismissActionInteractor, Continuation continuation) {
            super(2, continuation);
            this.this$0 = keyguardDismissActionInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final KeyguardDismissActionInteractor keyguardDismissActionInteractor = this.this$0;
                ReadonlyStateFlow readonlyStateFlow = ((KeyguardRepositoryImpl) keyguardDismissActionInteractor.repository).dismissAction;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor.onActivated.2.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        KeyguardDismissActionInteractor.access$log(KeyguardDismissActionInteractor.this, "updatedDismissAction=" + ((DismissAction) obj2));
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardDismissActionInteractor$onActivated$2(KeyguardDismissActionInteractor keyguardDismissActionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardDismissActionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardDismissActionInteractor$onActivated$2 keyguardDismissActionInteractor$onActivated$2 = new KeyguardDismissActionInteractor$onActivated$2(this.this$0, continuation);
        keyguardDismissActionInteractor$onActivated$2.L$0 = obj;
        return keyguardDismissActionInteractor$onActivated$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardDismissActionInteractor$onActivated$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, null), 3);
            this.label = 1;
            if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
