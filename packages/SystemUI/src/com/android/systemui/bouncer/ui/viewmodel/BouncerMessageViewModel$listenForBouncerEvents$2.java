package com.android.systemui.bouncer.ui.viewmodel;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$1;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$2;
import com.android.systemui.util.kotlin.Quad;
import com.android.systemui.util.kotlin.Utils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BouncerMessageViewModel$listenForBouncerEvents$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BouncerMessageViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForBouncerEvents$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerMessageViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerMessageViewModel;
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
                final BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                BouncerInteractor$special$$inlined$map$2 bouncerInteractor$special$$inlined$map$2 = bouncerMessageViewModel.bouncerInteractor.onLockoutStarted;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel.listenForBouncerEvents.2.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Unit access$startLockoutCountdown = BouncerMessageViewModel.access$startLockoutCountdown(BouncerMessageViewModel.this);
                        return access$startLockoutCountdown == CoroutineSingletons.COROUTINE_SUSPENDED ? access$startLockoutCountdown : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (bouncerInteractor$special$$inlined$map$2.collect(flowCollector, this) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForBouncerEvents$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerMessageViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerMessageViewModel;
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
                final BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                BouncerInteractor$special$$inlined$filter$1 bouncerInteractor$special$$inlined$filter$1 = bouncerMessageViewModel.bouncerInteractor.onPrimaryBouncerLockoutStarted;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel.listenForBouncerEvents.2.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ((Boolean) obj2).getClass();
                        Unit access$startLockoutCountdown = BouncerMessageViewModel.access$startLockoutCountdown(BouncerMessageViewModel.this);
                        return access$startLockoutCountdown == CoroutineSingletons.COROUTINE_SUSPENDED ? access$startLockoutCountdown : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (bouncerInteractor$special$$inlined$filter$1.collect(flowCollector, this) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForBouncerEvents$2$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerMessageViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerMessageViewModel;
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
                BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                this.label = 1;
                if (BouncerMessageViewModel.access$startLockoutCountdown(bouncerMessageViewModel) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForBouncerEvents$2$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerMessageViewModel this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForBouncerEvents$2$4$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ BouncerMessageViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerMessageViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((Quad) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code restructure failed: missing block: B:12:0x009a, code lost:
            
                if (r12.emit(r1, r11) == r0) goto L29;
             */
            /* JADX WARN: Code restructure failed: missing block: B:22:0x007e, code lost:
            
                if (r12.emit(r1, r11) == r0) goto L29;
             */
            /* JADX WARN: Code restructure failed: missing block: B:24:0x008b, code lost:
            
                if (kotlinx.coroutines.DelayKt.delay(com.android.systemui.util.DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY, r11) == r0) goto L29;
             */
            /* JADX WARN: Code restructure failed: missing block: B:27:0x0065, code lost:
            
                if (kotlin.Unit.INSTANCE == r0) goto L29;
             */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invokeSuspend(java.lang.Object r12) {
                /*
                    r11 = this;
                    kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r1 = r11.label
                    r2 = 0
                    r3 = 4
                    r4 = 3
                    r5 = 2
                    r6 = 1
                    if (r1 == 0) goto L2e
                    if (r1 == r6) goto L26
                    if (r1 == r5) goto L21
                    if (r1 == r4) goto L1c
                    if (r1 != r3) goto L14
                    goto L21
                L14:
                    java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                    java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                    r11.<init>(r12)
                    throw r11
                L1c:
                    kotlin.ResultKt.throwOnFailure(r12)
                    goto L8e
                L21:
                    kotlin.ResultKt.throwOnFailure(r12)
                    goto L9d
                L26:
                    java.lang.Object r1 = r11.L$0
                    com.android.systemui.bouncer.ui.viewmodel.MessageViewModel r1 = (com.android.systemui.bouncer.ui.viewmodel.MessageViewModel) r1
                    kotlin.ResultKt.throwOnFailure(r12)
                    goto L68
                L2e:
                    kotlin.ResultKt.throwOnFailure(r12)
                    java.lang.Object r12 = r11.L$0
                    com.android.systemui.util.kotlin.Quad r12 = (com.android.systemui.util.kotlin.Quad) r12
                    java.lang.Object r1 = r12.component2()
                    com.android.systemui.bouncer.ui.viewmodel.MessageViewModel r1 = (com.android.systemui.bouncer.ui.viewmodel.MessageViewModel) r1
                    java.lang.Object r7 = r12.component3()
                    com.android.systemui.authentication.shared.model.AuthenticationMethodModel r7 = (com.android.systemui.authentication.shared.model.AuthenticationMethodModel) r7
                    java.lang.Object r12 = r12.component4()
                    java.lang.Boolean r12 = (java.lang.Boolean) r12
                    boolean r12 = r12.booleanValue()
                    com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel r8 = r11.this$0
                    kotlinx.coroutines.flow.StateFlowImpl r9 = r8.message
                    com.android.systemui.bouncer.shared.model.BouncerMessageStrings r10 = com.android.systemui.bouncer.shared.model.BouncerMessageStrings.INSTANCE
                    r10.getClass()
                    kotlin.Pair r12 = com.android.systemui.bouncer.shared.model.BouncerMessageStrings.incorrectSecurityInput(r7, r12)
                    com.android.systemui.bouncer.ui.viewmodel.MessageViewModel r12 = r8.toMessage(r12)
                    r11.L$0 = r1
                    r11.label = r6
                    r9.updateState(r2, r12)
                    kotlin.Unit r12 = kotlin.Unit.INSTANCE
                    if (r12 != r0) goto L68
                    goto L9c
                L68:
                    if (r1 == 0) goto L6d
                    java.lang.String r12 = r1.text
                    goto L6e
                L6d:
                    r12 = r2
                L6e:
                    if (r12 == 0) goto L81
                    com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel r12 = r11.this$0
                    kotlinx.coroutines.flow.SharedFlowImpl r12 = r12.resetToDefault
                    java.lang.Boolean r1 = java.lang.Boolean.TRUE
                    r11.L$0 = r2
                    r11.label = r5
                    java.lang.Object r11 = r12.emit(r1, r11)
                    if (r11 != r0) goto L9d
                    goto L9c
                L81:
                    r11.L$0 = r2
                    r11.label = r4
                    r1 = 2000(0x7d0, double:9.88E-321)
                    java.lang.Object r12 = kotlinx.coroutines.DelayKt.delay(r1, r11)
                    if (r12 != r0) goto L8e
                    goto L9c
                L8e:
                    com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel r12 = r11.this$0
                    kotlinx.coroutines.flow.SharedFlowImpl r12 = r12.resetToDefault
                    java.lang.Boolean r1 = java.lang.Boolean.FALSE
                    r11.label = r3
                    java.lang.Object r11 = r12.emit(r1, r11)
                    if (r11 != r0) goto L9d
                L9c:
                    return r0
                L9d:
                    kotlin.Unit r11 = kotlin.Unit.INSTANCE
                    return r11
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForBouncerEvents$2.AnonymousClass4.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerMessageViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass4(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Utils.Companion companion = Utils.Companion;
                BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                Flow sample = companion.sample(bouncerMessageViewModel.bouncerInteractor.onIncorrectBouncerInput, bouncerMessageViewModel.lockoutMessage, bouncerMessageViewModel.authenticationInteractor.authenticationMethod, bouncerMessageViewModel.deviceEntryBiometricsAllowedInteractor.isFingerprintCurrentlyAllowedOnBouncer);
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
                this.label = 1;
                if (FlowKt.collectLatest(sample, anonymousClass1, this) == coroutineSingletons) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerMessageViewModel$listenForBouncerEvents$2(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bouncerMessageViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BouncerMessageViewModel$listenForBouncerEvents$2 bouncerMessageViewModel$listenForBouncerEvents$2 = new BouncerMessageViewModel$listenForBouncerEvents$2(this.this$0, continuation);
        bouncerMessageViewModel$listenForBouncerEvents$2.L$0 = obj;
        return bouncerMessageViewModel$listenForBouncerEvents$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BouncerMessageViewModel$listenForBouncerEvents$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, null), 7);
        return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.this$0, null), 7);
    }
}
