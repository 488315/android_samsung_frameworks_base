package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardBypassInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ AlternateBouncerInteractor $alternateBouncerInteractor$inlined;
    final /* synthetic */ KeyguardQuickAffordanceInteractor $keyguardQuickAffordanceInteractor$inlined;
    final /* synthetic */ PulseExpansionInteractor $pulseExpansionInteractor$inlined;
    final /* synthetic */ SceneInteractor $sceneInteractor$inlined;
    final /* synthetic */ ShadeInteractor $shadeInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBypassInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, SceneInteractor sceneInteractor, AlternateBouncerInteractor alternateBouncerInteractor, KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, PulseExpansionInteractor pulseExpansionInteractor, ShadeInteractor shadeInteractor) {
        super(3, continuation);
        this.$sceneInteractor$inlined = sceneInteractor;
        this.$alternateBouncerInteractor$inlined = alternateBouncerInteractor;
        this.$keyguardQuickAffordanceInteractor$inlined = keyguardQuickAffordanceInteractor;
        this.$pulseExpansionInteractor$inlined = pulseExpansionInteractor;
        this.$shadeInteractor$inlined = shadeInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardBypassInteractor$special$$inlined$flatMapLatest$1 keyguardBypassInteractor$special$$inlined$flatMapLatest$1 = new KeyguardBypassInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$sceneInteractor$inlined, this.$alternateBouncerInteractor$inlined, this.$keyguardQuickAffordanceInteractor$inlined, this.$pulseExpansionInteractor$inlined, this.$shadeInteractor$inlined);
        keyguardBypassInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        keyguardBypassInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return keyguardBypassInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                final StateFlow stateFlow = this.$sceneInteractor$inlined.currentOverlays;
                Flow flow = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$1$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L49
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                java.util.Set r5 = (java.util.Set) r5
                                com.android.compose.animation.scene.OverlayKey r6 = com.android.systemui.scene.shared.model.Overlays.Bouncer
                                boolean r5 = r5.contains(r6)
                                java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L49
                                return r1
                            L49:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                StateFlow stateFlow2 = this.$alternateBouncerInteractor$inlined.isVisible;
                final StateFlow stateFlow3 = this.$sceneInteractor$inlined.currentScene;
                final Flow[] flowArr = {flow, stateFlow2, new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$2

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$2$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$2$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$2.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$2$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$2$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$2$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L49
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                com.android.compose.animation.scene.SceneKey r5 = (com.android.compose.animation.scene.SceneKey) r5
                                com.android.compose.animation.scene.SceneKey r6 = com.android.systemui.scene.shared.model.Scenes.Lockscreen
                                boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                                java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L49
                                return r1
                            L49:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                }, this.$keyguardQuickAffordanceInteractor$inlined.launchingAffordance, this.$pulseExpansionInteractor$inlined.isPulseExpanding, ((ShadeInteractorImpl) this.$shadeInteractor$inlined).baseShadeInteractor.isQsExpanded()};
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$combine$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$combine$1$3, reason: invalid class name */
                    public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                        private /* synthetic */ Object L$0;
                        /* synthetic */ Object L$1;
                        int label;

                        public AnonymousClass3(Continuation continuation) {
                            super(3, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3);
                            anonymousClass3.L$0 = (FlowCollector) obj;
                            anonymousClass3.L$1 = (Object[]) obj2;
                            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i = this.label;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                FlowCollector flowCollector = (FlowCollector) this.L$0;
                                Object[] objArr = (Object[]) this.L$1;
                                boolean z = false;
                                Object obj2 = objArr[0];
                                Object obj3 = objArr[1];
                                Object obj4 = objArr[2];
                                Object obj5 = objArr[3];
                                Object obj6 = objArr[4];
                                boolean booleanValue = ((Boolean) objArr[5]).booleanValue();
                                boolean booleanValue2 = ((Boolean) obj6).booleanValue();
                                boolean booleanValue3 = ((Boolean) obj5).booleanValue();
                                boolean booleanValue4 = ((Boolean) obj4).booleanValue();
                                boolean booleanValue5 = ((Boolean) obj3).booleanValue();
                                if (((Boolean) obj2).booleanValue() || booleanValue5 || (booleanValue4 && !booleanValue3 && !booleanValue2 && !booleanValue)) {
                                    z = true;
                                }
                                Boolean valueOf = Boolean.valueOf(z);
                                this.label = 1;
                                if (flowCollector.emit(valueOf, this) == coroutineSingletons) {
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

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        final Flow[] flowArr2 = flowArr;
                        Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$combine$1.2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return new Object[flowArr2.length];
                            }
                        }, new AnonymousClass3(null), flowCollector2, continuation);
                        return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
                    }
                };
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this) == coroutineSingletons) {
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
