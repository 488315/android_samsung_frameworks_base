package com.android.systemui.keyguard.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBuffer$$ExternalSyntheticLambda0;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.scene.shared.model.Scenes;
import dagger.Lazy;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardDismissActionInteractor extends ExclusiveActivatable {
    public final StateFlow dismissAction;
    public final KeyguardDismissInteractor dismissInteractor;
    public final KeyguardDismissActionInteractor$special$$inlined$map$5 finishedTransitionToGone;
    public final SafeFlow isOnShadeWhileUnlocked;
    public final KeyguardLogger keyguardLogger;
    public final PrimaryBouncerInteractor primaryBouncerInteractor;
    public final KeyguardRepository repository;
    public final ReadonlyStateFlow willAnimateDismissActionOnLockscreen;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$5] */
    public KeyguardDismissActionInteractor(KeyguardRepository keyguardRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardDismissInteractor keyguardDismissInteractor, CoroutineScope coroutineScope, Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, KeyguardLogger keyguardLogger, PrimaryBouncerInteractor primaryBouncerInteractor) {
        this.repository = keyguardRepository;
        this.dismissInteractor = keyguardDismissInteractor;
        this.keyguardLogger = keyguardLogger;
        this.primaryBouncerInteractor = primaryBouncerInteractor;
        final ReadonlyStateFlow readonlyStateFlow = ((KeyguardRepositoryImpl) keyguardRepository).dismissAction;
        this.dismissAction = readonlyStateFlow;
        new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L43
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.DismissAction r5 = (com.android.systemui.keyguard.shared.model.DismissAction) r5
                        java.lang.String r5 = r5.getMessage()
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L43
                        return r1
                    L43:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.willAnimateDismissActionOnLockscreen = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.DismissAction r5 = (com.android.systemui.keyguard.shared.model.DismissAction) r5
                        boolean r5 = r5.getWillAnimateOnLockscreen()
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
        SceneKey sceneKey = Scenes.Communal;
        final Flow isFinishedIn = keyguardTransitionInteractor.isFinishedIn(KeyguardState.GONE);
        final Flow flow = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$filter$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$filter$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$filter$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$filter$2$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$filter$2$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$filter$2$2$1
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$filter$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.finishedTransitionToGone = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$5

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$5$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$5$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$5$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$5$2$1
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        ComposeBouncerFlags.INSTANCE.getClass();
        this.isOnShadeWhileUnlocked = new SafeFlow(new KeyguardDismissActionInteractor$isOnShadeWhileUnlocked$3(null));
    }

    public static final void access$log(KeyguardDismissActionInteractor keyguardDismissActionInteractor, String str) {
        keyguardDismissActionInteractor.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        LogBuffer$$ExternalSyntheticLambda0 logBuffer$$ExternalSyntheticLambda0 = new LogBuffer$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = keyguardDismissActionInteractor.keyguardLogger.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardDismissAction", logLevel, logBuffer$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$runDismissAction(com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor r4, kotlin.coroutines.Continuation r5) {
        /*
            r4.getClass()
            boolean r0 = r5 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$runDismissAction$1
            if (r0 == 0) goto L16
            r0 = r5
            com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$runDismissAction$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$runDismissAction$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$runDismissAction$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$runDismissAction$1
            r0.<init>(r4, r5)
        L1b:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r4 = r0.L$0
            com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor r4 = (com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor) r4
            kotlin.ResultKt.throwOnFailure(r5)
            goto L78
        L2e:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L36:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.keyguard.data.repository.KeyguardRepository r5 = r4.repository
            com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl r5 = (com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl) r5
            kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r5.dismissAction
            kotlinx.coroutines.flow.StateFlow r5 = r5.$$delegate_0
            java.lang.Object r5 = r5.getValue()
            com.android.systemui.keyguard.shared.model.DismissAction r5 = (com.android.systemui.keyguard.shared.model.DismissAction) r5
            com.android.systemui.keyguard.shared.model.KeyguardDone r2 = com.android.systemui.keyguard.shared.model.KeyguardDone.IMMEDIATE
            com.android.systemui.keyguard.shared.model.DismissAction$None r2 = com.android.systemui.keyguard.shared.model.DismissAction.None.INSTANCE
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r2)
            if (r2 != 0) goto L86
            kotlin.jvm.functions.Function0 r5 = r5.getOnDismissAction()
            java.lang.Object r5 = r5.invoke()
            com.android.systemui.keyguard.shared.model.KeyguardDone r5 = (com.android.systemui.keyguard.shared.model.KeyguardDone) r5
            r0.L$0 = r4
            r0.label = r3
            com.android.systemui.keyguard.domain.interactor.KeyguardDismissInteractor r2 = r4.dismissInteractor
            com.android.systemui.keyguard.data.repository.KeyguardRepository r2 = r2.keyguardRepository
            com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl r2 = (com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl) r2
            kotlinx.coroutines.flow.SharedFlowImpl r2 = r2._keyguardDone
            java.lang.Object r5 = r2.emit(r5, r0)
            if (r5 != r1) goto L6e
            goto L70
        L6e:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
        L70:
            if (r5 != r1) goto L73
            goto L75
        L73:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
        L75:
            if (r5 != r1) goto L78
            return r1
        L78:
            r4.getClass()
            com.android.systemui.keyguard.shared.model.DismissAction$None r5 = com.android.systemui.keyguard.shared.model.DismissAction.None.INSTANCE
            com.android.systemui.keyguard.data.repository.KeyguardRepository r0 = r4.repository
            com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl r0 = (com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl) r0
            kotlinx.coroutines.flow.StateFlowImpl r0 = r0._dismissAction
            r0.setValue(r5)
        L86:
            com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor r4 = r4.primaryBouncerInteractor
            com.android.systemui.bouncer.data.repository.KeyguardBouncerRepository r4 = r4.repository
            com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl r4 = (com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl) r4
            kotlinx.coroutines.flow.StateFlowImpl r4 = r4._keyguardAuthenticatedBiometrics
            r5 = 0
            r4.setValue(r5)
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor.access$runDismissAction(com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L41
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2 r5 = new com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r5, r0)
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
