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
import com.android.systemui.keyguard.data.repository.BiometricType;
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
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
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
import com.android.systemui.user.data.model.SelectedUserModel;
import com.android.systemui.user.data.model.SelectionStatus;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.SpreadBuilder;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$cancel$1, reason: invalid class name and case insensitive filesystem */
    final class C08601 extends SuspendLambda implements Function2 {
        int label;

        public C08601(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return DeviceEntryFaceAuthRepositoryImpl.this.new C08601(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08601) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(3000L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl = DeviceEntryFaceAuthRepositoryImpl.this;
            FaceAuthenticationLogger faceAuthenticationLogger = deviceEntryFaceAuthRepositoryImpl.faceAuthLogger;
            boolean zBooleanValue = ((Boolean) deviceEntryFaceAuthRepositoryImpl._isAuthRunning.getValue()).booleanValue();
            boolean zBooleanValue2 = ((Boolean) DeviceEntryFaceAuthRepositoryImpl.this._isLockedOut.getValue()).booleanValue();
            boolean zBooleanValue3 = ((Boolean) DeviceEntryFaceAuthRepositoryImpl.this.cancellationInProgress.getValue()).booleanValue();
            AuthenticationRequest authenticationRequest = (AuthenticationRequest) DeviceEntryFaceAuthRepositoryImpl.this.pendingAuthenticateRequest.getValue();
            FaceAuthUiEvent faceAuthUiEvent = authenticationRequest != null ? authenticationRequest.uiEvent : null;
            faceAuthenticationLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            FaceAuthenticationLogger$$ExternalSyntheticLambda0 faceAuthenticationLogger$$ExternalSyntheticLambda0 = new FaceAuthenticationLogger$$ExternalSyntheticLambda0(8);
            LogBuffer logBuffer = faceAuthenticationLogger.logBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.bool1 = zBooleanValue;
            logMessageImpl.bool2 = zBooleanValue2;
            logMessageImpl.bool3 = zBooleanValue3;
            logMessageImpl.str1 = String.valueOf(faceAuthUiEvent != null ? faceAuthUiEvent.getReason() : null);
            logBuffer.commit(logMessageObtain);
            StateFlowImpl stateFlowImpl = DeviceEntryFaceAuthRepositoryImpl.this._authenticationStatus;
            ErrorFaceAuthenticationStatus.Companion.getClass();
            stateFlowImpl.updateState(null, new ErrorFaceAuthenticationStatus(-1, "", 0L, 4, null));
            DeviceEntryFaceAuthRepositoryImpl.access$onFaceAuthRequestCompleted(DeviceEntryFaceAuthRepositoryImpl.this);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$1, reason: invalid class name and case insensitive filesystem */
    final /* synthetic */ class C08611 extends FunctionReferenceImpl implements Function1 {
        public C08611(Object obj) {
            super(1, obj, KeyguardState.Companion.class, "deviceIsAwakeInState", "deviceIsAwakeInState(Lcom/android/systemui/keyguard/shared/model/KeyguardState;)Z", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            ((KeyguardState.Companion) this.receiver).getClass();
            return Boolean.valueOf(KeyguardState.Companion.deviceIsAwakeInState((KeyguardState) obj));
        }
    }

    /* renamed from: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass3(Object obj) {
            super(1, obj, KeyguardState.Companion.class, "deviceIsAsleepInState", "deviceIsAsleepInState(Lcom/android/systemui/keyguard/shared/model/KeyguardState;)Z", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            ((KeyguardState.Companion) this.receiver).getClass();
            return Boolean.valueOf(KeyguardState.Companion.deviceIsAsleepInState((KeyguardState) obj));
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
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.pendingAuthenticateRequest = stateFlowImplMutableStateFlow;
        this._authenticationStatus = StateFlowKt.MutableStateFlow(null);
        this._detectionStatus = StateFlowKt.MutableStateFlow(null);
        Boolean bool = Boolean.FALSE;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._isLockedOut = stateFlowImplMutableStateFlow2;
        this.isLockedOut = stateFlowImplMutableStateFlow2;
        this.isDetectionSupported = (faceManager == null || (sensorPropertiesInternal = faceManager.getSensorPropertiesInternal()) == null || (faceSensorPropertiesInternal = (FaceSensorPropertiesInternal) CollectionsKt___CollectionsKt.firstOrNull(sensorPropertiesInternal)) == null) ? false : faceSensorPropertiesInternal.supportsFaceDetection;
        this._isAuthRunning = StateFlowKt.MutableStateFlow(bool);
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this._isAuthenticated = stateFlowImplMutableStateFlow3;
        this.isAuthenticated = stateFlowImplMutableStateFlow3;
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(bool);
        this.cancellationInProgress = stateFlowImplMutableStateFlow4;
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = (keyguardBypassController == null || (flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = FlowConflatedKt.conflatedCallbackFlow(new DeviceEntryFaceAuthRepositoryImpl$isBypassEnabled$1$1(keyguardBypassController, null))) == null) ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool) : flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        this.isBypassEnabled = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        this.faceLockoutResetCallback = new FaceManager.LockoutResetCallback() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$faceLockoutResetCallback$1
            public final void onLockoutReset(int i) {
                this.this$0._isLockedOut.updateState(null, Boolean.FALSE);
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
        spreadBuilder.add(new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(stateFlowImplMutableStateFlow2), "isNotInLockOutState"));
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) keyguardRepository;
        spreadBuilder.add(new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(keyguardRepositoryImpl.isKeyguardDismissible), "keyguardIsNotDismissible"));
        BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl = (BiometricSettingsRepositoryImpl) biometricSettingsRepository;
        spreadBuilder.add(new Pair(biometricSettingsRepositoryImpl.isFaceAuthCurrentlyAllowed, "isFaceAuthCurrentlyAllowed"));
        spreadBuilder.add(new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(stateFlowImplMutableStateFlow3), "faceNotAuthenticated"));
        Flow flowFlowOn = FlowKt.flowOn(DeviceEntryFaceAuthRepositoryKt.access$andAllFlows(CollectionsKt__CollectionsKt.listOf(spreadBuilder.list.toArray(new Pair[spreadBuilder.list.size()])), "canFaceAuthRun", tableLogBuffer2), coroutineDispatcher2);
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowFlowOn, coroutineScope, startedEagerly, bool);
        this.canRunFaceAuth = readonlyStateFlowStateIn;
        SpreadBuilder spreadBuilder2 = new SpreadBuilder(4);
        spreadBuilder2.addSpread(gatingConditionsForAuthAndDetect());
        spreadBuilder2.add(new Pair(flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, "isBypassEnabled"));
        spreadBuilder2.add(new Pair(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(biometricSettingsRepositoryImpl.isFaceAuthCurrentlyAllowed), keyguardRepositoryImpl.isKeyguardDismissible, new DeviceEntryFaceAuthRepositoryKt$or$1(null)), "faceAuthIsNotCurrentlyAllowedOrCurrentUserIsTrusted"));
        DeviceEntryFingerprintAuthRepositoryImpl deviceEntryFingerprintAuthRepositoryImpl = (DeviceEntryFingerprintAuthRepositoryImpl) deviceEntryFingerprintAuthRepository;
        final Flow availableFpSensorType = deviceEntryFingerprintAuthRepositoryImpl.getAvailableFpSensorType();
        spreadBuilder2.add(new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$isUdfps$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((BiometricType) obj) == BiometricType.UNDER_DISPLAY_FINGERPRINT);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = availableFpSensorType.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, deviceEntryFingerprintAuthRepositoryImpl.isRunning(), new DeviceEntryFaceAuthRepositoryKt$and$1(null))), "udfpsAuthIsNotPossibleAnymore"));
        ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(FlowKt.flowOn(DeviceEntryFaceAuthRepositoryKt.access$andAllFlows(CollectionsKt__CollectionsKt.listOf(spreadBuilder2.list.toArray(new Pair[spreadBuilder2.list.size()])), "canFaceDetectRun", tableLogBuffer), coroutineDispatcher2), coroutineScope, startedEagerly, bool);
        this.canRunDetection = readonlyStateFlowStateIn2;
        FlowKt.launchIn(FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(readonlyStateFlowStateIn, new DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthGatingChecks$1(this, null)), coroutineDispatcher), coroutineScope);
        FlowKt.launchIn(FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(readonlyStateFlowStateIn2, new DeviceEntryFaceAuthRepositoryImpl$observeFaceDetectGatingChecks$1(this, null)), coroutineDispatcher), coroutineScope);
        PowerInteractor$special$$inlined$map$2 powerInteractor$special$$inlined$map$2 = powerInteractor.isAsleep;
        SceneKey sceneKey = Scenes.Gone;
        KeyguardState keyguardState = KeyguardState.GONE;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardTransitionInteractor.isFinishedIn(keyguardState), keyguardInteractor.statusBarState, new DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$1(null));
        final ReadonlyStateFlow readonlyStateFlow = ((UserRepositoryImpl) userRepository).selectedUser;
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.flowOn(FlowKt.merge(powerInteractor$special$$inlined$map$2, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new Flow() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((SelectedUserModel) obj).selectionStatus == SelectionStatus.SELECTION_IN_PROGRESS);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), coroutineDispatcher), new DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$3(this, null)), coroutineScope);
        Edge.Companion companion = Edge.Companion;
        Edge.Companion.create$default(companion, sceneKey);
        final Flow flowTransition = keyguardTransitionInteractor.transition(Edge.Companion.create$default(companion, null, keyguardState, 1));
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$listenForSchedulingWatchdog$$inlined$filter$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        if (((TransitionStep) obj).transitionState == TransitionState.FINISHED) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowTransition.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new DeviceEntryFaceAuthRepositoryImpl$listenForSchedulingWatchdog$2(this, null)), coroutineScope);
        FlowKt.launchIn(FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.combine(stateFlowImplMutableStateFlow, readonlyStateFlowStateIn, readonlyStateFlowStateIn2, stateFlowImplMutableStateFlow4, new DeviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$1(this, null)), new DeviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$2(this, null)), coroutineDispatcher), coroutineScope);
        this.faceAuthCallback = new FaceManager.AuthenticationCallback() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$faceAuthCallback$1
            public final void onAuthenticationAcquired(int i) {
                this.this$0._authenticationStatus.updateState(null, new AcquiredFaceAuthenticationStatus(i, 0L, 2, null));
            }

            public final void onAuthenticationError(int i, CharSequence charSequence) {
                ErrorFaceAuthenticationStatus errorFaceAuthenticationStatus = new ErrorFaceAuthenticationStatus(i, String.valueOf(charSequence), 0L, 4, null);
                if (errorFaceAuthenticationStatus.isLockoutError()) {
                    this.this$0._isLockedOut.updateState(null, Boolean.TRUE);
                }
                this.this$0._isAuthenticated.updateState(null, Boolean.FALSE);
                this.this$0._authenticationStatus.updateState(null, errorFaceAuthenticationStatus);
                int i2 = errorFaceAuthenticationStatus.msgId;
                if (i2 == 1 || i2 == 2) {
                    FaceAuthenticationLogger faceAuthenticationLogger2 = this.this$0.faceAuthLogger;
                    faceAuthenticationLogger2.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    FaceAuthenticationLogger$$ExternalSyntheticLambda0 faceAuthenticationLogger$$ExternalSyntheticLambda0 = new FaceAuthenticationLogger$$ExternalSyntheticLambda0(11);
                    LogBuffer logBuffer = faceAuthenticationLogger2.logBuffer;
                    LogMessage logMessageObtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$$ExternalSyntheticLambda0, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                    logMessageImpl.str1 = String.valueOf(errorFaceAuthenticationStatus.msg);
                    logMessageImpl.int1 = i2;
                    logBuffer.commit(logMessageObtain);
                    DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl = this.this$0;
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
                FaceAuthenticationLogger faceAuthenticationLogger3 = this.this$0.faceAuthLogger;
                boolean zIsLockoutError = errorFaceAuthenticationStatus.isLockoutError();
                boolean z = i2 == 5;
                faceAuthenticationLogger3.getClass();
                LogLevel logLevel2 = LogLevel.DEBUG;
                FaceAuthenticationLogger$$ExternalSyntheticLambda0 faceAuthenticationLogger$$ExternalSyntheticLambda02 = new FaceAuthenticationLogger$$ExternalSyntheticLambda0(6);
                LogBuffer logBuffer2 = faceAuthenticationLogger3.logBuffer;
                LogMessage logMessageObtain2 = logBuffer2.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel2, faceAuthenticationLogger$$ExternalSyntheticLambda02, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                logMessageImpl2.int1 = i;
                logMessageImpl2.str1 = String.valueOf(charSequence);
                logMessageImpl2.bool1 = zIsLockoutError;
                logMessageImpl2.bool2 = z;
                logBuffer2.commit(logMessageObtain2);
                DeviceEntryFaceAuthRepositoryImpl.access$onFaceAuthRequestCompleted(this.this$0);
            }

            public final void onAuthenticationFailed() {
                this.this$0._isAuthenticated.updateState(null, Boolean.FALSE);
                FaceAuthenticationLogger faceAuthenticationLogger2 = this.this$0.faceAuthLogger;
                faceAuthenticationLogger2.getClass();
                LogBuffer.log$default(faceAuthenticationLogger2.logBuffer, "DeviceEntryFaceAuthRepositoryLog", LogLevel.DEBUG, "Face authentication failed");
                this.this$0._authenticationStatus.updateState(null, new FailedFaceAuthenticationStatus(0L, 1, null));
                if (((Boolean) this.this$0._isLockedOut.getValue()).booleanValue()) {
                    return;
                }
                DeviceEntryFaceAuthRepositoryImpl.access$onFaceAuthRequestCompleted(this.this$0);
            }

            public final void onAuthenticationHelp(int i, CharSequence charSequence) {
                this.this$0._authenticationStatus.updateState(null, new HelpFaceAuthenticationStatus(i, charSequence != null ? charSequence.toString() : null, 0L, 4, null));
            }

            public final void onAuthenticationSucceeded(FaceManager.AuthenticationResult authenticationResult) {
                this.this$0._isAuthenticated.updateState(null, Boolean.TRUE);
                this.this$0._authenticationStatus.updateState(null, new SuccessFaceAuthenticationStatus(authenticationResult, 0L, 2, null));
                FaceAuthenticationLogger faceAuthenticationLogger2 = this.this$0.faceAuthLogger;
                faceAuthenticationLogger2.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                FaceAuthenticationLogger$$ExternalSyntheticLambda0 faceAuthenticationLogger$$ExternalSyntheticLambda0 = new FaceAuthenticationLogger$$ExternalSyntheticLambda0(3);
                LogBuffer logBuffer = faceAuthenticationLogger2.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.int1 = authenticationResult.getUserId();
                logMessageImpl.bool1 = authenticationResult.isStrongBiometric();
                logBuffer.commit(logMessageObtain);
                DeviceEntryFaceAuthRepositoryImpl.access$onFaceAuthRequestCompleted(this.this$0);
            }
        };
        this.detectionCallback = new FaceManager.FaceDetectionCallback() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$detectionCallback$1
            public final void onFaceDetected(int i, int i2, boolean z) {
                FaceAuthenticationLogger faceAuthenticationLogger2 = this.this$0.faceAuthLogger;
                faceAuthenticationLogger2.getClass();
                LogBuffer.log$default(faceAuthenticationLogger2.logBuffer, "DeviceEntryFaceAuthRepositoryLog", LogLevel.DEBUG, "Face detected");
                this.this$0._detectionStatus.updateState(null, new FaceDetectionStatus(i, i2, z, 0L, 8, null));
            }
        };
    }

    public static final void access$clearPendingAuthRequest(DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl, String str) {
        StateFlowImpl stateFlowImpl = deviceEntryFaceAuthRepositoryImpl.pendingAuthenticateRequest;
        AuthenticationRequest authenticationRequest = (AuthenticationRequest) stateFlowImpl.getValue();
        FaceAuthUiEvent faceAuthUiEvent = authenticationRequest != null ? authenticationRequest.uiEvent : null;
        AuthenticationRequest authenticationRequest2 = (AuthenticationRequest) stateFlowImpl.getValue();
        Boolean boolValueOf = authenticationRequest2 != null ? Boolean.valueOf(authenticationRequest2.fallbackToDetection) : null;
        FaceAuthenticationLogger faceAuthenticationLogger = deviceEntryFaceAuthRepositoryImpl.faceAuthLogger;
        faceAuthenticationLogger.getClass();
        if (faceAuthUiEvent != null) {
            LogLevel logLevel = LogLevel.DEBUG;
            FaceAuthenticationLogger$$ExternalSyntheticLambda0 faceAuthenticationLogger$$ExternalSyntheticLambda0 = new FaceAuthenticationLogger$$ExternalSyntheticLambda0(12);
            LogBuffer logBuffer = faceAuthenticationLogger.logBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = faceAuthUiEvent.getReason();
            logMessageImpl.str2 = String.valueOf(boolValueOf);
            logMessageImpl.str3 = str;
            logBuffer.commit(logMessageObtain);
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
        this.cancelNotReceivedHandlerJob = CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new C08601(null), 7);
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
        Boolean boolValueOf = null;
        printWriter.println("    sensorPropertiesInternal: " + (faceManager != null ? faceManager.getSensorPropertiesInternal() : null));
        FaceManager faceManager2 = this.faceManager;
        if (faceManager2 != null && (sensorPropertiesInternal = faceManager2.getSensorPropertiesInternal()) != null && (faceSensorPropertiesInternal = (FaceSensorPropertiesInternal) CollectionsKt___CollectionsKt.firstOrNull(sensorPropertiesInternal)) != null) {
            boolValueOf = Boolean.valueOf(faceSensorPropertiesInternal.supportsFaceDetection);
        }
        printWriter.println("    supportsFaceDetection: " + boolValueOf);
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
        C08611 c08611 = new C08611(companion);
        KeyguardTransitionInteractor keyguardTransitionInteractor = this.keyguardTransitionInteractor;
        Pair pair = new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, FlowKt.distinctUntilChanged(new KeyguardTransitionInteractor$isFinishedInStateWhere$$inlined$map$1(keyguardTransitionInteractor.finishedKeyguardState, c08611)), new DeviceEntryFaceAuthRepositoryKt$and$1(null))), "displayIsNotOffWhileFullyTransitionedToAwake");
        BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl = (BiometricSettingsRepositoryImpl) this.biometricSettingsRepository;
        Pair pair2 = new Pair(biometricSettingsRepositoryImpl.isFaceAuthEnrolledAndEnabled, "isFaceAuthEnrolledAndEnabled");
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) this.keyguardRepository;
        Pair pair3 = new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(keyguardRepositoryImpl.isKeyguardGoingAway), "keyguardNotGoingAway");
        Pair pair4 = new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(keyguardTransitionInteractor.isInTransitionWhere(new KeyguardTransitionInteractor$$ExternalSyntheticLambda0(), new AnonymousClass3(companion))), "deviceNotTransitioningToAsleepState");
        KeyguardInteractor keyguardInteractor = this.keyguardInteractor;
        Pair pair5 = new Pair(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(keyguardInteractor.isSecureCameraActive), new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.alternateBouncerInteractor.isVisible, keyguardInteractor.primaryBouncerShowing, new DeviceEntryFaceAuthRepositoryKt$or$1(null)), new DeviceEntryFaceAuthRepositoryKt$or$1(null)), "secureCameraNotActiveOrAnyBouncerIsShowing");
        Pair pair6 = new Pair(biometricSettingsRepositoryImpl.isFaceAuthSupportedInCurrentPosture, "isFaceAuthSupportedInCurrentPosture");
        Pair pair7 = new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(biometricSettingsRepositoryImpl.isCurrentUserInLockdown), "userHasNotLockedDownDevice");
        Pair pair8 = new Pair(keyguardRepositoryImpl.isKeyguardShowing, "isKeyguardShowing");
        final ReadonlyStateFlow readonlyStateFlow = ((UserRepositoryImpl) this.userRepository).selectedUser;
        return new Pair[]{pair, pair2, pair3, pair4, pair5, pair6, pair7, pair8, new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(new Flow() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$3

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((SelectedUserModel) obj).selectionStatus == SelectionStatus.SELECTION_IN_PROGRESS);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), "userSwitchingInProgress")};
    }

    public /* synthetic */ DeviceEntryFaceAuthRepositoryImpl(Context context, FaceManager faceManager, UserRepository userRepository, KeyguardBypassController keyguardBypassController, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, Executor executor, SessionTracker sessionTracker, UiEventLogger uiEventLogger, FaceAuthenticationLogger faceAuthenticationLogger, BiometricSettingsRepository biometricSettingsRepository, DeviceEntryFingerprintAuthRepository deviceEntryFingerprintAuthRepository, KeyguardRepository keyguardRepository, PowerInteractor powerInteractor, KeyguardInteractor keyguardInteractor, AlternateBouncerInteractor alternateBouncerInteractor, Lazy lazy, TableLogBuffer tableLogBuffer, TableLogBuffer tableLogBuffer2, KeyguardTransitionInteractor keyguardTransitionInteractor, DisplayStateInteractor displayStateInteractor, DumpManager dumpManager, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : faceManager, userRepository, (i & 8) != 0 ? null : keyguardBypassController, coroutineScope, coroutineDispatcher, coroutineDispatcher2, executor, sessionTracker, uiEventLogger, faceAuthenticationLogger, biometricSettingsRepository, deviceEntryFingerprintAuthRepository, keyguardRepository, powerInteractor, keyguardInteractor, alternateBouncerInteractor, lazy, tableLogBuffer, tableLogBuffer2, keyguardTransitionInteractor, displayStateInteractor, dumpManager);
    }
}
