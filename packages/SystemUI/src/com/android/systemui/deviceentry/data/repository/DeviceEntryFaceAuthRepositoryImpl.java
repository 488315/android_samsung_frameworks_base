package com.android.systemui.deviceentry.data.repository;

import android.content.Context;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.os.CancellationSignal;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.SceneKey;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.deviceentry.shared.model.AcquiredFaceAuthenticationStatus;
import com.android.systemui.deviceentry.shared.model.ErrorFaceAuthenticationStatus;
import com.android.systemui.deviceentry.shared.model.FaceDetectionStatus;
import com.android.systemui.deviceentry.shared.model.FailedFaceAuthenticationStatus;
import com.android.systemui.deviceentry.shared.model.HelpFaceAuthenticationStatus;
import com.android.systemui.deviceentry.shared.model.SuccessFaceAuthenticationStatus;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl;
import com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepository;
import com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$$ExternalSyntheticLambda0;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isFinishedInStateWhere$$inlined$map$1;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.log.FaceAuthenticationLogger;
import com.android.systemui.log.FaceAuthenticationLogger$$ExternalSyntheticLambda0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$2;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DeviceEntryFaceAuthRepositoryImpl implements DeviceEntryFaceAuthRepository, Dumpable {
    public final StateFlowImpl _authenticationStatus;
    public final StateFlowImpl _detectionStatus;
    public final StateFlowImpl _isAuthRunning;
    public final StateFlowImpl _isAuthenticated;
    public final StateFlowImpl _isLockedOut;
    public final AlternateBouncerInteractor alternateBouncerInteractor;
    public final CoroutineScope applicationScope;
    public CancellationSignal authCancellationSignal;
    public final Executor backgroundExecutor;
    public final BiometricSettingsRepository biometricSettingsRepository;
    public final ReadonlyStateFlow canRunDetection;
    public final ReadonlyStateFlow canRunFaceAuth;
    public StandaloneCoroutine cancelNotReceivedHandlerJob;
    public final StateFlowImpl cancellationInProgress;
    public CancellationSignal detectCancellationSignal;
    public final DeviceEntryFaceAuthRepositoryImpl$detectionCallback$1 detectionCallback;
    public final DisplayStateInteractor displayStateInteractor;
    public final DeviceEntryFaceAuthRepositoryImpl$faceAuthCallback$1 faceAuthCallback;
    public final FaceAuthenticationLogger faceAuthLogger;
    public final DeviceEntryFaceAuthRepositoryImpl$faceLockoutResetCallback$1 faceLockoutResetCallback;
    public final FaceManager faceManager;
    public StandaloneCoroutine halErrorRetryJob;
    public final StateFlowImpl isAuthenticated;
    public final Flow isBypassEnabled;
    public final boolean isDetectionSupported;
    public final StateFlowImpl isLockedOut;
    public final KeyguardBypassController keyguardBypassController;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardRepository keyguardRepository;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final CoroutineDispatcher mainDispatcher;
    public final StateFlowImpl pendingAuthenticateRequest;
    public int retryCount;
    public final SessionTracker sessionTracker;
    public final UiEventLogger uiEventsLogger;
    public final UserRepository userRepository;

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

    /* JADX WARN: Type inference failed for: r1v16, types: [com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$faceAuthCallback$1] */
    /* JADX WARN: Type inference failed for: r1v17, types: [com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$detectionCallback$1] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$faceLockoutResetCallback$1] */
    public DeviceEntryFaceAuthRepositoryImpl(Context context, FaceManager faceManager, UserRepository userRepository, KeyguardBypassController keyguardBypassController, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, Executor executor, SessionTracker sessionTracker, UiEventLogger uiEventLogger, FaceAuthenticationLogger faceAuthenticationLogger, BiometricSettingsRepository biometricSettingsRepository, DeviceEntryFingerprintAuthRepository deviceEntryFingerprintAuthRepository, KeyguardRepository keyguardRepository, PowerInteractor powerInteractor, KeyguardInteractor keyguardInteractor, AlternateBouncerInteractor alternateBouncerInteractor, Lazy lazy, TableLogBuffer tableLogBuffer, TableLogBuffer tableLogBuffer2, KeyguardTransitionInteractor keyguardTransitionInteractor, DisplayStateInteractor displayStateInteractor, DumpManager dumpManager) {
        List sensorPropertiesInternal;
        FaceSensorPropertiesInternal faceSensorPropertiesInternal;
        this.faceManager = faceManager;
        this.userRepository = userRepository;
        this.keyguardBypassController = keyguardBypassController;
        this.applicationScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundExecutor = executor;
        this.sessionTracker = sessionTracker;
        this.uiEventsLogger = uiEventLogger;
        this.faceAuthLogger = faceAuthenticationLogger;
        this.biometricSettingsRepository = biometricSettingsRepository;
        this.keyguardRepository = keyguardRepository;
        this.keyguardInteractor = keyguardInteractor;
        this.alternateBouncerInteractor = alternateBouncerInteractor;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.displayStateInteractor = displayStateInteractor;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.pendingAuthenticateRequest = MutableStateFlow;
        this._authenticationStatus = StateFlowKt.MutableStateFlow(null);
        this._detectionStatus = StateFlowKt.MutableStateFlow(null);
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._isLockedOut = MutableStateFlow2;
        this.isLockedOut = MutableStateFlow2;
        this.isDetectionSupported = (faceManager == null || (sensorPropertiesInternal = faceManager.getSensorPropertiesInternal()) == null || (faceSensorPropertiesInternal = (FaceSensorPropertiesInternal) CollectionsKt___CollectionsKt.firstOrNull(sensorPropertiesInternal)) == null) ? false : faceSensorPropertiesInternal.supportsFaceDetection;
        this._isAuthRunning = StateFlowKt.MutableStateFlow(bool);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this._isAuthenticated = MutableStateFlow3;
        this.isAuthenticated = MutableStateFlow3;
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(bool);
        this.cancellationInProgress = MutableStateFlow4;
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = (keyguardBypassController == null || (flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = FlowConflatedKt.conflatedCallbackFlow(new DeviceEntryFaceAuthRepositoryImpl$isBypassEnabled$1$1(keyguardBypassController, null))) == null) ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool) : flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        this.isBypassEnabled = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        this.faceLockoutResetCallback = new FaceManager.LockoutResetCallback() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$faceLockoutResetCallback$1
            public final void onLockoutReset(int i) {
                DeviceEntryFaceAuthRepositoryImpl.this._isLockedOut.updateState(null, Boolean.FALSE);
            }
        };
        executor.execute(new Runnable() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl.1
            @Override // java.lang.Runnable
            public final void run() {
                DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl = DeviceEntryFaceAuthRepositoryImpl.this;
                FaceManager faceManager2 = deviceEntryFaceAuthRepositoryImpl.faceManager;
                if (faceManager2 != null) {
                    faceManager2.addLockoutResetCallback(deviceEntryFaceAuthRepositoryImpl.faceLockoutResetCallback);
                }
                FaceAuthenticationLogger faceAuthenticationLogger2 = DeviceEntryFaceAuthRepositoryImpl.this.faceAuthLogger;
                faceAuthenticationLogger2.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                FaceAuthenticationLogger$$ExternalSyntheticLambda0 faceAuthenticationLogger$$ExternalSyntheticLambda0 = new FaceAuthenticationLogger$$ExternalSyntheticLambda0(14);
                LogBuffer logBuffer = faceAuthenticationLogger2.logBuffer;
                logBuffer.commit(logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$$ExternalSyntheticLambda0, null));
            }
        });
        dumpManager.registerCriticalDumpable("DeviceEntryFaceAuthRepositoryImpl", this);
        SpreadBuilder spreadBuilder = new SpreadBuilder(5);
        spreadBuilder.addSpread(gatingConditionsForAuthAndDetect());
        spreadBuilder.add(new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(MutableStateFlow2), "isNotInLockOutState"));
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) keyguardRepository;
        spreadBuilder.add(new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(keyguardRepositoryImpl.isKeyguardDismissible), "keyguardIsNotDismissible"));
        BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl = (BiometricSettingsRepositoryImpl) biometricSettingsRepository;
        spreadBuilder.add(new Pair(biometricSettingsRepositoryImpl.isFaceAuthCurrentlyAllowed, "isFaceAuthCurrentlyAllowed"));
        spreadBuilder.add(new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(MutableStateFlow3), "faceNotAuthenticated"));
        Flow flowOn = FlowKt.flowOn(DeviceEntryFaceAuthRepositoryKt.access$andAllFlows(CollectionsKt__CollectionsKt.listOf(spreadBuilder.list.toArray(new Pair[spreadBuilder.list.size()])), "canFaceAuthRun", tableLogBuffer2), coroutineDispatcher2);
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flowOn, coroutineScope, startedEagerly, bool);
        this.canRunFaceAuth = stateIn;
        SpreadBuilder spreadBuilder2 = new SpreadBuilder(4);
        spreadBuilder2.addSpread(gatingConditionsForAuthAndDetect());
        spreadBuilder2.add(new Pair(flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, "isBypassEnabled"));
        spreadBuilder2.add(new Pair(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(biometricSettingsRepositoryImpl.isFaceAuthCurrentlyAllowed), keyguardRepositoryImpl.isKeyguardDismissible, new DeviceEntryFaceAuthRepositoryKt$or$1(null)), "faceAuthIsNotCurrentlyAllowedOrCurrentUserIsTrusted"));
        DeviceEntryFingerprintAuthRepositoryImpl deviceEntryFingerprintAuthRepositoryImpl = (DeviceEntryFingerprintAuthRepositoryImpl) deviceEntryFingerprintAuthRepository;
        final Flow availableFpSensorType = deviceEntryFingerprintAuthRepositoryImpl.getAvailableFpSensorType();
        spreadBuilder2.add(new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$isUdfps$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$isUdfps$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$isUdfps$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$isUdfps$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$isUdfps$$inlined$map$1$2$1 r0 = (com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$isUdfps$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$isUdfps$$inlined$map$1$2$1 r0 = new com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$isUdfps$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.data.repository.BiometricType r5 = (com.android.systemui.keyguard.data.repository.BiometricType) r5
                        com.android.systemui.keyguard.data.repository.BiometricType r6 = com.android.systemui.keyguard.data.repository.BiometricType.UNDER_DISPLAY_FINGERPRINT
                        if (r5 != r6) goto L3a
                        r5 = r3
                        goto L3b
                    L3a:
                        r5 = 0
                    L3b:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$isUdfps$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, deviceEntryFingerprintAuthRepositoryImpl.isRunning(), new DeviceEntryFaceAuthRepositoryKt$and$1(null))), "udfpsAuthIsNotPossibleAnymore"));
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(FlowKt.flowOn(DeviceEntryFaceAuthRepositoryKt.access$andAllFlows(CollectionsKt__CollectionsKt.listOf(spreadBuilder2.list.toArray(new Pair[spreadBuilder2.list.size()])), "canFaceDetectRun", tableLogBuffer), coroutineDispatcher2), coroutineScope, startedEagerly, bool);
        this.canRunDetection = stateIn2;
        FlowKt.launchIn(FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(stateIn, new DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthGatingChecks$1(this, null)), coroutineDispatcher), coroutineScope);
        FlowKt.launchIn(FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(stateIn2, new DeviceEntryFaceAuthRepositoryImpl$observeFaceDetectGatingChecks$1(this, null)), coroutineDispatcher), coroutineScope);
        PowerInteractor$special$$inlined$map$2 powerInteractor$special$$inlined$map$2 = powerInteractor.isAsleep;
        SceneKey sceneKey = Scenes.Gone;
        KeyguardState keyguardState = KeyguardState.GONE;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardTransitionInteractor.isFinishedIn(keyguardState), keyguardInteractor.statusBarState, new DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$1(null));
        final ReadonlyStateFlow readonlyStateFlow = ((UserRepositoryImpl) userRepository).selectedUser;
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.flowOn(FlowKt.merge(powerInteractor$special$$inlined$map$2, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new Flow() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$$inlined$map$1$2$1 r0 = (com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$$inlined$map$1$2$1 r0 = new com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.user.data.model.SelectedUserModel r5 = (com.android.systemui.user.data.model.SelectedUserModel) r5
                        com.android.systemui.user.data.model.SelectionStatus r5 = r5.selectionStatus
                        com.android.systemui.user.data.model.SelectionStatus r6 = com.android.systemui.user.data.model.SelectionStatus.SELECTION_IN_PROGRESS
                        if (r5 != r6) goto L3c
                        r5 = r3
                        goto L3d
                    L3c:
                        r5 = 0
                    L3d:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineDispatcher), new DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$3(this, null)), coroutineScope);
        Edge.Companion companion = Edge.Companion;
        Edge.Companion.create$default(companion, sceneKey);
        final Flow transition = keyguardTransitionInteractor.transition(Edge.Companion.create$default(companion, null, keyguardState, 1));
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$listenForSchedulingWatchdog$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$listenForSchedulingWatchdog$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$listenForSchedulingWatchdog$$inlined$filter$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$listenForSchedulingWatchdog$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$listenForSchedulingWatchdog$$inlined$filter$1$2$1 r0 = (com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$listenForSchedulingWatchdog$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$listenForSchedulingWatchdog$$inlined$filter$1$2$1 r0 = new com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$listenForSchedulingWatchdog$$inlined$filter$1$2$1
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
                        com.android.systemui.keyguard.shared.model.TransitionStep r6 = (com.android.systemui.keyguard.shared.model.TransitionStep) r6
                        com.android.systemui.keyguard.shared.model.TransitionState r6 = r6.transitionState
                        com.android.systemui.keyguard.shared.model.TransitionState r2 = com.android.systemui.keyguard.shared.model.TransitionState.FINISHED
                        if (r6 != r2) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$listenForSchedulingWatchdog$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new DeviceEntryFaceAuthRepositoryImpl$listenForSchedulingWatchdog$2(this, null)), coroutineScope);
        FlowKt.launchIn(FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.combine(MutableStateFlow, stateIn, stateIn2, MutableStateFlow4, new DeviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$1(this, null)), new DeviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$2(this, null)), coroutineDispatcher), coroutineScope);
        this.faceAuthCallback = new FaceManager.AuthenticationCallback() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$faceAuthCallback$1
            public final void onAuthenticationAcquired(int i) {
                DeviceEntryFaceAuthRepositoryImpl.this._authenticationStatus.updateState(null, new AcquiredFaceAuthenticationStatus(i, 0L, 2, null));
            }

            public final void onAuthenticationError(int i, CharSequence charSequence) {
                ErrorFaceAuthenticationStatus errorFaceAuthenticationStatus = new ErrorFaceAuthenticationStatus(i, String.valueOf(charSequence), 0L, 4, null);
                if (errorFaceAuthenticationStatus.isLockoutError()) {
                    DeviceEntryFaceAuthRepositoryImpl.this._isLockedOut.updateState(null, Boolean.TRUE);
                }
                DeviceEntryFaceAuthRepositoryImpl.this._isAuthenticated.updateState(null, Boolean.FALSE);
                DeviceEntryFaceAuthRepositoryImpl.this._authenticationStatus.updateState(null, errorFaceAuthenticationStatus);
                int i2 = errorFaceAuthenticationStatus.msgId;
                if (i2 == 1 || i2 == 2) {
                    FaceAuthenticationLogger faceAuthenticationLogger2 = DeviceEntryFaceAuthRepositoryImpl.this.faceAuthLogger;
                    faceAuthenticationLogger2.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    FaceAuthenticationLogger$$ExternalSyntheticLambda0 faceAuthenticationLogger$$ExternalSyntheticLambda0 = new FaceAuthenticationLogger$$ExternalSyntheticLambda0(11);
                    LogBuffer logBuffer = faceAuthenticationLogger2.logBuffer;
                    LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$$ExternalSyntheticLambda0, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                    logMessageImpl.str1 = String.valueOf(errorFaceAuthenticationStatus.msg);
                    logMessageImpl.int1 = i2;
                    logBuffer.commit(obtain);
                    DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl = DeviceEntryFaceAuthRepositoryImpl.this;
                    int i3 = deviceEntryFaceAuthRepositoryImpl.retryCount;
                    if (i3 < 5) {
                        deviceEntryFaceAuthRepositoryImpl.retryCount = i3 + 1;
                        StandaloneCoroutine standaloneCoroutine = deviceEntryFaceAuthRepositoryImpl.halErrorRetryJob;
                        if (standaloneCoroutine != null) {
                            standaloneCoroutine.cancel(null);
                        }
                        deviceEntryFaceAuthRepositoryImpl.halErrorRetryJob = CoroutineTracingKt.launchTraced$default(deviceEntryFaceAuthRepositoryImpl.applicationScope, null, null, new DeviceEntryFaceAuthRepositoryImpl$handleFaceHardwareError$1(deviceEntryFaceAuthRepositoryImpl, null), 7);
                    }
                }
                FaceAuthenticationLogger faceAuthenticationLogger3 = DeviceEntryFaceAuthRepositoryImpl.this.faceAuthLogger;
                boolean isLockoutError = errorFaceAuthenticationStatus.isLockoutError();
                boolean z = i2 == 5;
                faceAuthenticationLogger3.getClass();
                LogLevel logLevel2 = LogLevel.DEBUG;
                FaceAuthenticationLogger$$ExternalSyntheticLambda0 faceAuthenticationLogger$$ExternalSyntheticLambda02 = new FaceAuthenticationLogger$$ExternalSyntheticLambda0(6);
                LogBuffer logBuffer2 = faceAuthenticationLogger3.logBuffer;
                LogMessage obtain2 = logBuffer2.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel2, faceAuthenticationLogger$$ExternalSyntheticLambda02, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
                logMessageImpl2.int1 = i;
                logMessageImpl2.str1 = String.valueOf(charSequence);
                logMessageImpl2.bool1 = isLockoutError;
                logMessageImpl2.bool2 = z;
                logBuffer2.commit(obtain2);
                DeviceEntryFaceAuthRepositoryImpl.access$onFaceAuthRequestCompleted(DeviceEntryFaceAuthRepositoryImpl.this);
            }

            public final void onAuthenticationFailed() {
                DeviceEntryFaceAuthRepositoryImpl.this._isAuthenticated.updateState(null, Boolean.FALSE);
                FaceAuthenticationLogger faceAuthenticationLogger2 = DeviceEntryFaceAuthRepositoryImpl.this.faceAuthLogger;
                faceAuthenticationLogger2.getClass();
                LogBuffer.log$default(faceAuthenticationLogger2.logBuffer, "DeviceEntryFaceAuthRepositoryLog", LogLevel.DEBUG, "Face authentication failed");
                DeviceEntryFaceAuthRepositoryImpl.this._authenticationStatus.updateState(null, new FailedFaceAuthenticationStatus(0L, 1, null));
                if (((Boolean) DeviceEntryFaceAuthRepositoryImpl.this._isLockedOut.getValue()).booleanValue()) {
                    return;
                }
                DeviceEntryFaceAuthRepositoryImpl.access$onFaceAuthRequestCompleted(DeviceEntryFaceAuthRepositoryImpl.this);
            }

            public final void onAuthenticationHelp(int i, CharSequence charSequence) {
                DeviceEntryFaceAuthRepositoryImpl.this._authenticationStatus.updateState(null, new HelpFaceAuthenticationStatus(i, charSequence != null ? charSequence.toString() : null, 0L, 4, null));
            }

            public final void onAuthenticationSucceeded(FaceManager.AuthenticationResult authenticationResult) {
                DeviceEntryFaceAuthRepositoryImpl.this._isAuthenticated.updateState(null, Boolean.TRUE);
                DeviceEntryFaceAuthRepositoryImpl.this._authenticationStatus.updateState(null, new SuccessFaceAuthenticationStatus(authenticationResult, 0L, 2, null));
                FaceAuthenticationLogger faceAuthenticationLogger2 = DeviceEntryFaceAuthRepositoryImpl.this.faceAuthLogger;
                faceAuthenticationLogger2.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                FaceAuthenticationLogger$$ExternalSyntheticLambda0 faceAuthenticationLogger$$ExternalSyntheticLambda0 = new FaceAuthenticationLogger$$ExternalSyntheticLambda0(3);
                LogBuffer logBuffer = faceAuthenticationLogger2.logBuffer;
                LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.int1 = authenticationResult.getUserId();
                logMessageImpl.bool1 = authenticationResult.isStrongBiometric();
                logBuffer.commit(obtain);
                DeviceEntryFaceAuthRepositoryImpl.access$onFaceAuthRequestCompleted(DeviceEntryFaceAuthRepositoryImpl.this);
            }
        };
        this.detectionCallback = new FaceManager.FaceDetectionCallback() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$detectionCallback$1
            public final void onFaceDetected(int i, int i2, boolean z) {
                FaceAuthenticationLogger faceAuthenticationLogger2 = DeviceEntryFaceAuthRepositoryImpl.this.faceAuthLogger;
                faceAuthenticationLogger2.getClass();
                LogBuffer.log$default(faceAuthenticationLogger2.logBuffer, "DeviceEntryFaceAuthRepositoryLog", LogLevel.DEBUG, "Face detected");
                DeviceEntryFaceAuthRepositoryImpl.this._detectionStatus.updateState(null, new FaceDetectionStatus(i, i2, z, 0L, 8, null));
            }
        };
    }

    public static final void access$clearPendingAuthRequest(DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl, String str) {
        StateFlowImpl stateFlowImpl = deviceEntryFaceAuthRepositoryImpl.pendingAuthenticateRequest;
        AuthenticationRequest authenticationRequest = (AuthenticationRequest) stateFlowImpl.getValue();
        FaceAuthUiEvent faceAuthUiEvent = authenticationRequest != null ? authenticationRequest.uiEvent : null;
        AuthenticationRequest authenticationRequest2 = (AuthenticationRequest) stateFlowImpl.getValue();
        Boolean valueOf = authenticationRequest2 != null ? Boolean.valueOf(authenticationRequest2.fallbackToDetection) : null;
        FaceAuthenticationLogger faceAuthenticationLogger = deviceEntryFaceAuthRepositoryImpl.faceAuthLogger;
        faceAuthenticationLogger.getClass();
        if (faceAuthUiEvent != null) {
            LogLevel logLevel = LogLevel.DEBUG;
            FaceAuthenticationLogger$$ExternalSyntheticLambda0 faceAuthenticationLogger$$ExternalSyntheticLambda0 = new FaceAuthenticationLogger$$ExternalSyntheticLambda0(12);
            LogBuffer logBuffer = faceAuthenticationLogger.logBuffer;
            LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = faceAuthUiEvent.getReason();
            logMessageImpl.str2 = String.valueOf(valueOf);
            logMessageImpl.str3 = str;
            logBuffer.commit(obtain);
        }
        stateFlowImpl.setValue(null);
    }

    public static final void access$onFaceAuthRequestCompleted(DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl) {
        StandaloneCoroutine standaloneCoroutine = deviceEntryFaceAuthRepositoryImpl.cancelNotReceivedHandlerJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        Boolean bool = Boolean.FALSE;
        deviceEntryFaceAuthRepositoryImpl._isAuthRunning.updateState(null, bool);
        deviceEntryFaceAuthRepositoryImpl.authCancellationSignal = null;
        deviceEntryFaceAuthRepositoryImpl.cancellationInProgress.updateState(null, bool);
    }

    public final void cancel() {
        CancellationSignal cancellationSignal = this.authCancellationSignal;
        if (cancellationSignal == null) {
            return;
        }
        cancellationSignal.cancel();
        StandaloneCoroutine standaloneCoroutine = this.cancelNotReceivedHandlerJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.cancelNotReceivedHandlerJob = CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new DeviceEntryFaceAuthRepositoryImpl$cancel$1(this, null), 7);
        this.cancellationInProgress.updateState(null, Boolean.TRUE);
        this._isAuthRunning.updateState(null, Boolean.FALSE);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        List sensorPropertiesInternal;
        FaceSensorPropertiesInternal faceSensorPropertiesInternal;
        printWriter.println("DeviceEntryFaceAuthRepositoryImpl state:");
        printWriter.println("  cancellationInProgress: " + this.cancellationInProgress);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  _isLockedOut.value: ", this._isLockedOut.getValue(), printWriter);
        printWriter.println("  _isAuthRunning.value: " + this._isAuthRunning.getValue());
        printWriter.println("  isDetectionSupported: " + this.isDetectionSupported);
        printWriter.println("  FaceManager state:");
        printWriter.println("    faceManager: " + this.faceManager);
        FaceManager faceManager = this.faceManager;
        Boolean bool = null;
        printWriter.println("    sensorPropertiesInternal: " + (faceManager != null ? faceManager.getSensorPropertiesInternal() : null));
        FaceManager faceManager2 = this.faceManager;
        if (faceManager2 != null && (sensorPropertiesInternal = faceManager2.getSensorPropertiesInternal()) != null && (faceSensorPropertiesInternal = (FaceSensorPropertiesInternal) CollectionsKt___CollectionsKt.firstOrNull(sensorPropertiesInternal)) != null) {
            bool = Boolean.valueOf(faceSensorPropertiesInternal.supportsFaceDetection);
        }
        printWriter.println("    supportsFaceDetection: " + bool);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  _pendingAuthenticateRequest: ", this.pendingAuthenticateRequest.getValue(), printWriter);
        printWriter.println("  authCancellationSignal: " + this.authCancellationSignal);
        printWriter.println("  detectCancellationSignal: " + this.detectCancellationSignal);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  _authenticationStatus: ", this._authenticationStatus.getValue(), printWriter);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  _detectionStatus: ", this._detectionStatus.getValue(), printWriter);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  currentUserId: ", ((UserRepositoryImpl) this.userRepository).getSelectedUserInfo().id, printWriter);
        printWriter.println("  keyguardSessionId: " + this.sessionTracker.getSessionId(1));
        KeyguardBypassController keyguardBypassController = this.keyguardBypassController;
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  lockscreenBypassEnabled: ", keyguardBypassController != null ? keyguardBypassController.getBypassEnabled() : false);
    }

    public final Pair[] gatingConditionsForAuthAndDetect() {
        Flow flow = ((DisplayStateInteractorImpl) this.displayStateInteractor).isDefaultDisplayOff;
        KeyguardState.Companion companion = KeyguardState.Companion;
        DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$1 deviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$1 = new DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$1(companion);
        KeyguardTransitionInteractor keyguardTransitionInteractor = this.keyguardTransitionInteractor;
        Pair pair = new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, FlowKt.distinctUntilChanged(new KeyguardTransitionInteractor$isFinishedInStateWhere$$inlined$map$1(keyguardTransitionInteractor.finishedKeyguardState, deviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$1)), new DeviceEntryFaceAuthRepositoryKt$and$1(null))), "displayIsNotOffWhileFullyTransitionedToAwake");
        BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl = (BiometricSettingsRepositoryImpl) this.biometricSettingsRepository;
        Pair pair2 = new Pair(biometricSettingsRepositoryImpl.isFaceAuthEnrolledAndEnabled, "isFaceAuthEnrolledAndEnabled");
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) this.keyguardRepository;
        Pair pair3 = new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(keyguardRepositoryImpl.isKeyguardGoingAway), "keyguardNotGoingAway");
        Pair pair4 = new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(keyguardTransitionInteractor.isInTransitionWhere(new KeyguardTransitionInteractor$$ExternalSyntheticLambda0(), new DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$3(companion))), "deviceNotTransitioningToAsleepState");
        KeyguardInteractor keyguardInteractor = this.keyguardInteractor;
        Pair pair5 = new Pair(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(keyguardInteractor.isSecureCameraActive), new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.alternateBouncerInteractor.isVisible, keyguardInteractor.primaryBouncerShowing, new DeviceEntryFaceAuthRepositoryKt$or$1(null)), new DeviceEntryFaceAuthRepositoryKt$or$1(null)), "secureCameraNotActiveOrAnyBouncerIsShowing");
        Pair pair6 = new Pair(biometricSettingsRepositoryImpl.isFaceAuthSupportedInCurrentPosture, "isFaceAuthSupportedInCurrentPosture");
        Pair pair7 = new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(biometricSettingsRepositoryImpl.isCurrentUserInLockdown), "userHasNotLockedDownDevice");
        Pair pair8 = new Pair(keyguardRepositoryImpl.isKeyguardShowing, "isKeyguardShowing");
        final ReadonlyStateFlow readonlyStateFlow = ((UserRepositoryImpl) this.userRepository).selectedUser;
        return new Pair[]{pair, pair2, pair3, pair4, pair5, pair6, pair7, pair8, new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(new Flow() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$3$2$1 r0 = (com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$3$2$1 r0 = new com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.user.data.model.SelectedUserModel r5 = (com.android.systemui.user.data.model.SelectedUserModel) r5
                        com.android.systemui.user.data.model.SelectionStatus r5 = r5.selectionStatus
                        com.android.systemui.user.data.model.SelectionStatus r6 = com.android.systemui.user.data.model.SelectionStatus.SELECTION_IN_PROGRESS
                        if (r5 != r6) goto L3c
                        r5 = r3
                        goto L3d
                    L3c:
                        r5 = 0
                    L3d:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), "userSwitchingInProgress")};
    }

    public /* synthetic */ DeviceEntryFaceAuthRepositoryImpl(Context context, FaceManager faceManager, UserRepository userRepository, KeyguardBypassController keyguardBypassController, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, Executor executor, SessionTracker sessionTracker, UiEventLogger uiEventLogger, FaceAuthenticationLogger faceAuthenticationLogger, BiometricSettingsRepository biometricSettingsRepository, DeviceEntryFingerprintAuthRepository deviceEntryFingerprintAuthRepository, KeyguardRepository keyguardRepository, PowerInteractor powerInteractor, KeyguardInteractor keyguardInteractor, AlternateBouncerInteractor alternateBouncerInteractor, Lazy lazy, TableLogBuffer tableLogBuffer, TableLogBuffer tableLogBuffer2, KeyguardTransitionInteractor keyguardTransitionInteractor, DisplayStateInteractor displayStateInteractor, DumpManager dumpManager, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : faceManager, userRepository, (i & 8) != 0 ? null : keyguardBypassController, coroutineScope, coroutineDispatcher, coroutineDispatcher2, executor, sessionTracker, uiEventLogger, faceAuthenticationLogger, biometricSettingsRepository, deviceEntryFingerprintAuthRepository, keyguardRepository, powerInteractor, keyguardInteractor, alternateBouncerInteractor, lazy, tableLogBuffer, tableLogBuffer2, keyguardTransitionInteractor, displayStateInteractor, dumpManager);
    }
}
