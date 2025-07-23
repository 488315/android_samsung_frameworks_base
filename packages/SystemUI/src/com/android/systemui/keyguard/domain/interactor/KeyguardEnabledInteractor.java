package com.android.systemui.keyguard.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.kotlin.FlowKt;
import dagger.Lazy;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardEnabledInteractor {
    public final CoroutineDispatcher backgroundDispatcher;
    public final BiometricSettingsRepository biometricSettingsRepository;
    public final ReadonlyStateFlow isKeyguardEnabled;
    public final LockPatternUtils lockPatternUtils;
    public final KeyguardRepository repository;
    public final SelectedUserInteractor selectedUserInteractor;
    public final KeyguardEnabledInteractor$special$$inlined$map$1 showKeyguardWhenReenabled;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ InternalKeyguardTransitionInteractor $internalTransitionInteractor;
        final /* synthetic */ Lazy $keyguardDismissTransitionInteractor;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$4, reason: invalid class name */
        final /* synthetic */ class AnonymousClass4 extends AdaptedFunctionReference implements Function3 {
            public static final AnonymousClass4 INSTANCE = new AnonymousClass4();

            public AnonymousClass4() {
                super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                Boolean bool2 = (Boolean) obj2;
                bool2.booleanValue();
                return new Pair(bool, bool2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor, Lazy lazy, Continuation continuation) {
            super(2, continuation);
            this.$internalTransitionInteractor = internalKeyguardTransitionInteractor;
            this.$keyguardDismissTransitionInteractor = lazy;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardEnabledInteractor.this.new AnonymousClass1(this.$internalTransitionInteractor, this.$keyguardDismissTransitionInteractor, continuation);
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
                final ReadonlyStateFlow readonlyStateFlow = ((KeyguardRepositoryImpl) KeyguardEnabledInteractor.this.repository).isKeyguardEnabled;
                Flow sample = FlowKt.sample(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1$2$1
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
                                if (r6 != 0) goto L46
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L46
                                return r1
                            L46:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                }, ((BiometricSettingsRepositoryImpl) KeyguardEnabledInteractor.this.biometricSettingsRepository).isCurrentUserInLockdown, AnonymousClass4.INSTANCE);
                final InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor = this.$internalTransitionInteractor;
                final Lazy lazy = this.$keyguardDismissTransitionInteractor;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor.1.5
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) ((Pair) obj2).component2()).booleanValue();
                        if (InternalKeyguardTransitionInteractor.this.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to != KeyguardState.GONE && !booleanValue) {
                            KeyguardDismissTransitionInteractor.startDismissKeyguardTransition$default((KeyguardDismissTransitionInteractor) lazy.get(), "keyguard disabled");
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (sample.collect(flowCollector, this) == coroutineSingletons) {
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

    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1] */
    public KeyguardEnabledInteractor(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, KeyguardRepository keyguardRepository, BiometricSettingsRepository biometricSettingsRepository, SelectedUserInteractor selectedUserInteractor, LockPatternUtils lockPatternUtils, Lazy lazy, final InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.repository = keyguardRepository;
        this.biometricSettingsRepository = biometricSettingsRepository;
        this.selectedUserInteractor = selectedUserInteractor;
        this.lockPatternUtils = lockPatternUtils;
        ReadonlyStateFlow readonlyStateFlow = ((KeyguardRepositoryImpl) keyguardRepository).isKeyguardEnabled;
        this.isKeyguardEnabled = readonlyStateFlow;
        final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(readonlyStateFlow, new KeyguardEnabledInteractor$showKeyguardWhenReenabled$1(null));
        final Flow sample = FlowKt.sample(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1$2$1
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
                        if (r6 != 0) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, ((BiometricSettingsRepositoryImpl) biometricSettingsRepository).isCurrentUserInLockdown, KeyguardEnabledInteractor$showKeyguardWhenReenabled$5.INSTANCE);
        this.showKeyguardWhenReenabled = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ InternalKeyguardTransitionInteractor $internalTransitionInteractor$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$internalTransitionInteractor$inlined = internalKeyguardTransitionInteractor;
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5e
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Pair r5 = (kotlin.Pair) r5
                        java.lang.Object r5 = r5.component2()
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor r6 = r4.$internalTransitionInteractor$inlined
                        com.android.systemui.keyguard.shared.model.TransitionInfo r6 = r6.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core()
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = r6.to
                        com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
                        if (r6 == r2) goto L4e
                        if (r5 != 0) goto L4e
                        r5 = r3
                        goto L4f
                    L4e:
                        r5 = 0
                    L4f:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L5e
                        return r1
                    L5e:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, internalKeyguardTransitionInteractor), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(internalKeyguardTransitionInteractor, lazy, null), 7);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x005f, code lost:
    
        if (((java.lang.Boolean) r6).booleanValue() != false) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object isKeyguardEnabledAndNotSuppressed(kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$isKeyguardEnabledAndNotSuppressed$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$isKeyguardEnabledAndNotSuppressed$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$isKeyguardEnabledAndNotSuppressed$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$isKeyguardEnabledAndNotSuppressed$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$isKeyguardEnabledAndNotSuppressed$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r6)
            goto L59
        L27:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlinx.coroutines.flow.ReadonlyStateFlow r6 = r5.isKeyguardEnabled
            kotlinx.coroutines.flow.StateFlow r6 = r6.$$delegate_0
            java.lang.Object r6 = r6.getValue()
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L62
            r0.label = r3
            com.android.systemui.user.domain.interactor.SelectedUserInteractor r6 = r5.selectedUserInteractor
            int r6 = r6.getSelectedUserId()
            com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$isKeyguardSuppressed$2 r2 = new com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$isKeyguardSuppressed$2
            r4 = 0
            r2.<init>(r5, r6, r4)
            kotlinx.coroutines.CoroutineDispatcher r5 = r5.backgroundDispatcher
            java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r5, r2, r0)
            if (r6 != r1) goto L59
            return r1
        L59:
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r5 = r6.booleanValue()
            if (r5 != 0) goto L62
            goto L63
        L62:
            r3 = 0
        L63:
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r3)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor.isKeyguardEnabledAndNotSuppressed(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
