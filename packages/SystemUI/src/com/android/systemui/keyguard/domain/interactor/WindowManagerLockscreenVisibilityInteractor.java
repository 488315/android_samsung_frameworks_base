package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.Flags;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationLaunchAnimationInteractor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

public final class WindowManagerLockscreenVisibilityInteractor {
    public static final Companion Companion = new Companion(null);
    public final Flow aodVisibility;
    public final WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$1 defaultSurfaceBehindVisibility;
    public final Lazy isDeviceEntered$delegate;
    public final Flow lockscreenVisibility;
    public final Flow surfaceBehindVisibility;
    public final Flow transitionSpecificSurfaceBehindVisibility;
    public final Flow usingKeyguardGoingAwayAnimation;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KeyguardState.values().length];
            try {
                iArr[KeyguardState.LOCKSCREEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.PRIMARY_BOUNCER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardState.ALTERNATE_BOUNCER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public WindowManagerLockscreenVisibilityInteractor(KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardSurfaceBehindInteractor keyguardSurfaceBehindInteractor, FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor, FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor, NotificationLaunchAnimationInteractor notificationLaunchAnimationInteractor, dagger.Lazy lazy, final dagger.Lazy lazy2) {
        final ReadonlySharedFlow readonlySharedFlow = keyguardTransitionInteractor.finishedKeyguardState;
        final Companion companion = Companion;
        this.defaultSurfaceBehindVisibility = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ WindowManagerLockscreenVisibilityInteractor.Companion receiver$inlined;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, WindowManagerLockscreenVisibilityInteractor.Companion companion) {
                    this.$this_unsafeFlow = flowCollector;
                    this.receiver$inlined = companion;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L55
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.KeyguardState r5 = (com.android.systemui.keyguard.shared.model.KeyguardState) r5
                        com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$Companion r6 = r4.receiver$inlined
                        r6.getClass()
                        com.android.systemui.keyguard.shared.model.KeyguardState$Companion r6 = com.android.systemui.keyguard.shared.model.KeyguardState.Companion
                        r6.getClass()
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
                        if (r5 == r6) goto L44
                        r5 = r3
                        goto L45
                    L44:
                        r5 = 0
                    L45:
                        r5 = r5 ^ r3
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L55
                        return r1
                    L55:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, companion), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.transitionSpecificSurfaceBehindVisibility = FlowKt.distinctUntilChanged(FlowKt.transformLatest(keyguardTransitionInteractor.startedKeyguardTransitionStep, new WindowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$1(null, fromLockscreenTransitionInteractor, fromPrimaryBouncerTransitionInteractor, fromAlternateBouncerTransitionInteractor)));
        this.isDeviceEntered$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$isDeviceEntered$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ((DeviceEntryInteractor) dagger.Lazy.this.get()).isDeviceEntered;
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$isDeviceNotEntered$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final Flow flow = (Flow) WindowManagerLockscreenVisibilityInteractor.this.isDeviceEntered$delegate.getValue();
                return new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$isDeviceNotEntered$2$invoke$$inlined$map$1

                    /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$isDeviceNotEntered$2$invoke$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$isDeviceNotEntered$2$invoke$$inlined$map$1$2$1, reason: invalid class name */
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
                        public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                            /*
                                r4 = this;
                                boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$isDeviceNotEntered$2$invoke$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$isDeviceNotEntered$2$invoke$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$isDeviceNotEntered$2$invoke$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$isDeviceNotEntered$2$invoke$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$isDeviceNotEntered$2$invoke$$inlined$map$1$2$1
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
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$isDeviceNotEntered$2$invoke$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
            }
        });
        Flags.sceneContainer();
        this.surfaceBehindVisibility = FlowKt.distinctUntilChanged(FlowKt.transformLatest(keyguardTransitionInteractor.isInTransitionToAnyState, new WindowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$2(null, this)));
        Flags.sceneContainer();
        Edge.Companion companion2 = Edge.Companion;
        this.usingKeyguardGoingAwayAnimation = FlowKt.distinctUntilChanged(FlowKt.combine(keyguardTransitionInteractor.isInTransition(Edge.Companion.create$default(companion2, Scenes.Gone), Edge.Companion.create$default(companion2, null, KeyguardState.GONE, 1)), readonlySharedFlow, keyguardSurfaceBehindInteractor.isAnimatingSurface, notificationLaunchAnimationInteractor.repository.isLaunchAnimationRunning, new WindowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2(null)));
        Flags.sceneContainer();
        final Flow sample = com.android.systemui.util.kotlin.FlowKt.sample(keyguardTransitionInteractor.currentKeyguardState, keyguardTransitionInteractor.startedStepWithPrecedingStep, WindowManagerLockscreenVisibilityInteractor$lockscreenVisibility$2.INSTANCE);
        this.lockscreenVisibility = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ dagger.Lazy $deviceEntryInteractor$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, dagger.Lazy lazy) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$deviceEntryInteractor$inlined = lazy;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r12, kotlin.coroutines.Continuation r13) {
                    /*
                        Method dump skipped, instructions count: 205
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, lazy2), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.aodVisibility = FlowKt.distinctUntilChanged(FlowKt.combine(keyguardInteractor.isDozing, keyguardInteractor.isAodAvailable, keyguardInteractor.biometricUnlockState, new WindowManagerLockscreenVisibilityInteractor$aodVisibility$1(null)));
    }
}
