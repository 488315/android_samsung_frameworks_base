package com.android.systemui.bouncer.domain.interactor;

import com.android.app.tracing.FlowTracing;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.bouncer.data.repository.BouncerRepository;
import com.android.systemui.classifier.domain.interactor.FalsingInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.domain.interactor.SceneBackInteractor;
import com.android.systemui.scene.domain.interactor.SceneBackInteractor$special$$inlined$map$1;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BouncerInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SharedFlowImpl _onImeHiddenByUser;
    public final SharedFlowImpl _onIncorrectBouncerInput;
    public final CoroutineScope applicationScope;
    public final AuthenticationInteractor authenticationInteractor;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 bouncerExpansion;
    public final DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor;
    public final FalsingInteractor falsingInteractor;
    public final ReadonlyStateFlow hintedPinLength;
    public final ReadonlyStateFlow isAutoConfirmEnabled;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 isOneHandedModeSupported;
    public final ReadonlyStateFlow isPatternVisible;
    public final ReadonlyStateFlow isPinEnhancedPrivacyEnabled;
    public final BouncerInteractor$special$$inlined$map$1 isUserSwitcherVisible;
    public final StateFlowImpl lastRecordedLockscreenTouchPosition;
    public final SharedFlowImpl onImeHiddenByUser;
    public final SharedFlowImpl onIncorrectBouncerInput;
    public final BouncerInteractor$special$$inlined$map$2 onLockoutStarted;
    public final BouncerInteractor$special$$inlined$filter$1 onPrimaryBouncerLockoutStarted;
    public final PowerInteractor powerInteractor;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 preferredBouncerInputSide;
    public final BouncerRepository repository;
    public final ReadonlyStateFlow scale;
    public final SessionTracker sessionTracker;
    public final UiEventLogger uiEventLogger;

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

    public BouncerInteractor(CoroutineScope coroutineScope, BouncerRepository bouncerRepository, AuthenticationInteractor authenticationInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, FalsingInteractor falsingInteractor, PowerInteractor powerInteractor, UiEventLogger uiEventLogger, SessionTracker sessionTracker, SceneInteractor sceneInteractor, SceneBackInteractor sceneBackInteractor, ConfigurationInteractor configurationInteractor) {
        this.applicationScope = coroutineScope;
        this.repository = bouncerRepository;
        this.authenticationInteractor = authenticationInteractor;
        this.deviceEntryFaceAuthInteractor = deviceEntryFaceAuthInteractor;
        this.falsingInteractor = falsingInteractor;
        this.powerInteractor = powerInteractor;
        this.uiEventLogger = uiEventLogger;
        this.sessionTracker = sessionTracker;
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._onIncorrectBouncerInput = MutableSharedFlow$default;
        this.onIncorrectBouncerInput = MutableSharedFlow$default;
        this.isAutoConfirmEnabled = authenticationInteractor.isAutoConfirmEnabled;
        this.hintedPinLength = authenticationInteractor.hintedPinLength;
        this.isPatternVisible = authenticationInteractor.isPatternVisible;
        this.isPinEnhancedPrivacyEnabled = authenticationInteractor.isPinEnhancedPrivacyEnabled;
        Flow flow = authenticationInteractor.authenticationMethod;
        BouncerInteractor$special$$inlined$map$1 bouncerInteractor$special$$inlined$map$1 = new BouncerInteractor$special$$inlined$map$1(flow, this);
        this.isUserSwitcherVisible = bouncerInteractor$special$$inlined$map$1;
        ConfigurationInteractorImpl configurationInteractorImpl = (ConfigurationInteractorImpl) configurationInteractor;
        this.isOneHandedModeSupported = FlowKt.combine(bouncerInteractor$special$$inlined$map$1, flow, configurationInteractorImpl.onAnyConfigurationChange, new BouncerInteractor$isOneHandedModeSupported$1(this, null));
        this.preferredBouncerInputSide = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(configurationInteractorImpl.onAnyConfigurationChange, bouncerRepository.preferredBouncerInputSide, new BouncerInteractor$preferredBouncerInputSide$1(this, null));
        SharedFlowImpl MutableSharedFlow$default2 = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._onImeHiddenByUser = MutableSharedFlow$default2;
        this.onImeHiddenByUser = MutableSharedFlow$default2;
        this.onPrimaryBouncerLockoutStarted = new BouncerInteractor$special$$inlined$filter$1(authenticationInteractor.onPrimaryBouncerAuthenticationResult, this);
        final ReadonlySharedFlow readonlySharedFlow = authenticationInteractor.onAuthenticationResult;
        this.onLockoutStarted = new BouncerInteractor$special$$inlined$map$2(new Flow() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ BouncerInteractor this$0;

                /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, BouncerInteractor bouncerInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = bouncerInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                    /*
                        r8 = this;
                        boolean r0 = r10 instanceof com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r10
                        com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$2$2$1 r0 = (com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$2$2$1 r0 = new com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$2$2$1
                        r0.<init>(r10)
                    L18:
                        java.lang.Object r10 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto L6a
                    L27:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r10)
                        r10 = r9
                        java.lang.Boolean r10 = (java.lang.Boolean) r10
                        boolean r10 = r10.booleanValue()
                        if (r10 != 0) goto L6a
                        com.android.systemui.bouncer.domain.interactor.BouncerInteractor r10 = r8.this$0
                        com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r10 = r10.authenticationInteractor
                        com.android.systemui.authentication.data.repository.AuthenticationRepository r10 = r10.repository
                        com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r10 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r10
                        com.android.internal.widget.LockPatternUtils r2 = r10.lockPatternUtils
                        int r4 = r10.getSelectedUserId()
                        long r4 = r2.getLockoutAttemptDeadline(r4)
                        java.lang.Long r2 = java.lang.Long.valueOf(r4)
                        com.android.systemui.util.time.SystemClock r10 = r10.clock
                        long r6 = r10.elapsedRealtime()
                        int r10 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                        if (r10 >= 0) goto L5c
                        goto L5d
                    L5c:
                        r2 = 0
                    L5d:
                        if (r2 == 0) goto L6a
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                        java.lang.Object r8 = r8.emit(r9, r0)
                        if (r8 != r1) goto L6a
                        return r1
                    L6a:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.lastRecordedLockscreenTouchPosition = bouncerRepository.lastRecordedLockscreenTouchPosition;
        this.scale = FlowKt.asStateFlow(bouncerRepository.scale);
        final SceneBackInteractor$special$$inlined$map$1 sceneBackInteractor$special$$inlined$map$1 = sceneBackInteractor.backScene;
        new Flow() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$3$2$1 r0 = (com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$3$2$1 r0 = new com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$3$2$1
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
                        com.android.compose.animation.scene.SceneKey r5 = (com.android.compose.animation.scene.SceneKey) r5
                        if (r5 != 0) goto L38
                        com.android.compose.animation.scene.SceneKey r5 = com.android.systemui.scene.shared.model.Scenes.Lockscreen
                    L38:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L43
                        return r1
                    L43:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.bouncerExpansion = FlowTracing.traceAsCounter$default(FlowTracing.INSTANCE, FlowKt.distinctUntilChanged(new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1(new Float[0])), "bouncer_expansion", new BouncerInteractor$$ExternalSyntheticLambda0());
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x00f4, code lost:
    
        if (r12 == r1) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0085, code lost:
    
        if (r12 == r1) goto L49;
     */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object authenticate(java.util.List r10, boolean r11, kotlin.coroutines.jvm.internal.ContinuationImpl r12) {
        /*
            Method dump skipped, instructions count: 283
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.domain.interactor.BouncerInteractor.authenticate(java.util.List, boolean, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
