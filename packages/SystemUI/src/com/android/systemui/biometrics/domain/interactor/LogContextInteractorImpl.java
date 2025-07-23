package com.android.systemui.biometrics.domain.interactor;

import android.hardware.biometrics.IBiometricContextListener;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.display.data.repository.DeviceStateRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LogContextInteractorImpl implements LogContextInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope applicationScope;
    public final Lazy displayState$delegate;
    public final ReadonlySharedFlow foldState;
    public final LogContextInteractorImpl$special$$inlined$map$1 isHardwareIgnoringTouches;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[DeviceStateRepository.DeviceState.values().length];
            try {
                iArr[DeviceStateRepository.DeviceState.UNFOLDED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DeviceStateRepository.DeviceState.REAR_DISPLAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DeviceStateRepository.DeviceState.CONCURRENT_DISPLAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DeviceStateRepository.DeviceState.FOLDED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[DeviceStateRepository.DeviceState.HALF_FOLDED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[KeyguardState.values().length];
            try {
                iArr2[KeyguardState.LOCKSCREEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[KeyguardState.OCCLUDED.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[KeyguardState.ALTERNATE_BOUNCER.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[KeyguardState.PRIMARY_BOUNCER.ordinal()] = 4;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[KeyguardState.AOD.ordinal()] = 5;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr2[KeyguardState.OFF.ordinal()] = 6;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr2[KeyguardState.DOZING.ordinal()] = 7;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr2[KeyguardState.DREAMING.ordinal()] = 8;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr2[KeyguardState.GONE.ordinal()] = 9;
            } catch (NoSuchFieldError unused14) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$1] */
    public LogContextInteractorImpl(CoroutineScope coroutineScope, DeviceStateRepository deviceStateRepository, final KeyguardTransitionInteractor keyguardTransitionInteractor, UdfpsOverlayInteractor udfpsOverlayInteractor, final dagger.Lazy lazy) {
        this.applicationScope = coroutineScope;
        Lazy lazy2 = LazyKt__LazyJVMKt.lazy(new Function0(lazy, keyguardTransitionInteractor, this) { // from class: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ KeyguardTransitionInteractor f$1;
            public final /* synthetic */ LogContextInteractorImpl f$2;

            {
                this.f$1 = keyguardTransitionInteractor;
                this.f$2 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = LogContextInteractorImpl.$r8$clinit;
                final ReadonlyStateFlow readonlyStateFlow = this.f$1.startedKeyguardTransitionStep;
                final LogContextInteractorImpl logContextInteractorImpl = this.f$2;
                return new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$displayState_delegate$lambda$1$$inlined$map$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$displayState_delegate$lambda$1$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ LogContextInteractorImpl this$0;

                        /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$displayState_delegate$lambda$1$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, LogContextInteractorImpl logContextInteractorImpl) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = logContextInteractorImpl;
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
                                boolean r0 = r6 instanceof com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$displayState_delegate$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$displayState_delegate$lambda$1$$inlined$map$1$2$1 r0 = (com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$displayState_delegate$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$displayState_delegate$lambda$1$$inlined$map$1$2$1 r0 = new com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$displayState_delegate$lambda$1$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L61
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                com.android.systemui.keyguard.shared.model.TransitionStep r5 = (com.android.systemui.keyguard.shared.model.TransitionStep) r5
                                int r6 = com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl.$r8$clinit
                                com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl r6 = r4.this$0
                                r6.getClass()
                                com.android.systemui.keyguard.shared.model.KeyguardState r5 = r5.to
                                int[] r6 = com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl.WhenMappings.$EnumSwitchMapping$1
                                int r5 = r5.ordinal()
                                r5 = r6[r5]
                                r6 = 0
                                switch(r5) {
                                    case 1: goto L50;
                                    case 2: goto L50;
                                    case 3: goto L50;
                                    case 4: goto L50;
                                    case 5: goto L4e;
                                    case 6: goto L4c;
                                    case 7: goto L4c;
                                    case 8: goto L4a;
                                    case 9: goto L51;
                                    default: goto L49;
                                }
                            L49:
                                goto L51
                            L4a:
                                r6 = 3
                                goto L51
                            L4c:
                                r6 = 2
                                goto L51
                            L4e:
                                r6 = 4
                                goto L51
                            L50:
                                r6 = r3
                            L51:
                                java.lang.Integer r5 = new java.lang.Integer
                                r5.<init>(r6)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L61
                                return r1
                            L61:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$displayState_delegate$lambda$1$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, logContextInteractorImpl), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
            }
        });
        this.displayState$delegate = lazy2;
        final ReadonlyStateFlow readonlyStateFlow = udfpsOverlayInteractor.shouldHandleTouches;
        this.isHardwareIgnoringTouches = new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        r5 = r5 ^ r3
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow flow = (Flow) lazy2.getValue();
        FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$2$2$1 r0 = (com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$2$2$1 r0 = new com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4d
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        r6 = 4
                        if (r5 != r6) goto L3d
                        r5 = r3
                        goto L3e
                    L3d:
                        r5 = 0
                    L3e:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4d
                        return r1
                    L4d:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        final Flow flow2 = (Flow) lazy2.getValue();
        FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$3$2$1 r0 = (com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$3$2$1 r0 = new com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$3$2$1
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
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        if (r5 == 0) goto L41
                        if (r5 == r3) goto L41
                        r6 = 3
                        if (r5 == r6) goto L41
                        r5 = 0
                        goto L42
                    L41:
                        r5 = r3
                    L42:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L51
                        return r1
                    L51:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        final ReadonlyStateFlow readonlyStateFlow2 = ((DeviceStateRepositoryImpl) deviceStateRepository).state;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$4

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$4$2$1 r0 = (com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$4$2$1 r0 = new com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.display.data.repository.DeviceStateRepository$DeviceState r5 = (com.android.systemui.display.data.repository.DeviceStateRepository.DeviceState) r5
                        int[] r6 = com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl.WhenMappings.$EnumSwitchMapping$0
                        int r5 = r5.ordinal()
                        r5 = r6[r5]
                        r6 = 2
                        if (r5 == r3) goto L4f
                        if (r5 == r6) goto L4f
                        r2 = 3
                        if (r5 == r2) goto L4f
                        r6 = 4
                        if (r5 == r6) goto L4e
                        r6 = 5
                        if (r5 == r6) goto L4c
                        r6 = 0
                        goto L4f
                    L4c:
                        r6 = r3
                        goto L4f
                    L4e:
                        r6 = r2
                    L4f:
                        java.lang.Integer r5 = new java.lang.Integer
                        r5.<init>(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L5f
                        return r1
                    L5f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        SharingStarted.Companion.getClass();
        this.foldState = FlowKt.shareIn(distinctUntilChanged, coroutineScope, SharingStarted.Companion.Eagerly, 1);
    }

    public final StandaloneCoroutine addBiometricContextListener(IBiometricContextListener iBiometricContextListener) {
        return CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new LogContextInteractorImpl$addBiometricContextListener$1(this, iBiometricContextListener, null), 7);
    }
}
