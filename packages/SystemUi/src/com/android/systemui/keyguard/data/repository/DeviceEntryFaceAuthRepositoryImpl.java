package com.android.systemui.keyguard.data.repository;

import android.content.Context;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.os.CancellationSignal;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.FaceAuthUiEvent;
import com.android.keyguard.FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.biometrics.SideFpsController$$ExternalSyntheticOutline0;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.AcquiredAuthenticationStatus;
import com.android.systemui.keyguard.shared.model.DetectionStatus;
import com.android.systemui.keyguard.shared.model.ErrorAuthenticationStatus;
import com.android.systemui.keyguard.shared.model.FailedAuthenticationStatus;
import com.android.systemui.keyguard.shared.model.HelpAuthenticationStatus;
import com.android.systemui.keyguard.shared.model.SuccessAuthenticationStatus;
import com.android.systemui.log.FaceAuthenticationLogger;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DeviceEntryFaceAuthRepositoryImpl implements DeviceEntryFaceAuthRepository, Dumpable {
    public final StateFlowImpl _authenticationStatus;
    public final StateFlowImpl _canRunFaceAuth;
    public final StateFlowImpl _detectionStatus;
    public final StateFlowImpl _isAuthRunning;
    public final StateFlowImpl _isAuthenticated;
    public final StateFlowImpl _isLockedOut;
    public final AlternateBouncerInteractor alternateBouncerInteractor;
    public final CoroutineScope applicationScope;
    public CancellationSignal authCancellationSignal;
    public final CoroutineDispatcher backgroundDispatcher;
    public final BiometricSettingsRepository biometricSettingsRepository;
    public final StateFlowImpl canRunDetection;
    public StandaloneCoroutine cancelNotReceivedHandlerJob;
    public boolean cancellationInProgress;
    public CancellationSignal detectCancellationSignal;
    public final DeviceEntryFaceAuthRepositoryImpl$detectionCallback$1 detectionCallback;
    public final Set faceAcquiredInfoIgnoreList;
    public final DeviceEntryFaceAuthRepositoryImpl$faceAuthCallback$1 faceAuthCallback;
    public final FaceAuthenticationLogger faceAuthLogger;
    public FaceAuthUiEvent faceAuthRequestedWhileCancellation;
    public final FaceManager faceManager;
    public StandaloneCoroutine halErrorRetryJob;
    public final Flow isBypassEnabled;
    public final boolean isDetectionSupported;
    public final StateFlowImpl isLockedOut;
    public final KeyguardBypassController keyguardBypassController;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardRepository keyguardRepository;
    public final CoroutineDispatcher mainDispatcher;
    public int retryCount;
    public final SessionTracker sessionTracker;
    public final UiEventLogger uiEventsLogger;
    public final UserRepository userRepository;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0088, code lost:
    
        if (r2 == null) goto L14;
     */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.keyguard.data.repository.DeviceEntryFaceAuthRepositoryImpl$detectionCallback$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.keyguard.data.repository.DeviceEntryFaceAuthRepositoryImpl$faceAuthCallback$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public DeviceEntryFaceAuthRepositoryImpl(Context context, FaceManager faceManager, UserRepository userRepository, KeyguardBypassController keyguardBypassController, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, SessionTracker sessionTracker, UiEventLogger uiEventLogger, FaceAuthenticationLogger faceAuthenticationLogger, BiometricSettingsRepository biometricSettingsRepository, DeviceEntryFingerprintAuthRepository deviceEntryFingerprintAuthRepository, TrustRepository trustRepository, KeyguardRepository keyguardRepository, KeyguardInteractor keyguardInteractor, AlternateBouncerInteractor alternateBouncerInteractor, TableLogBuffer tableLogBuffer, TableLogBuffer tableLogBuffer2, KeyguardTransitionInteractor keyguardTransitionInteractor, FeatureFlags featureFlags, DumpManager dumpManager) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        List sensorPropertiesInternal;
        FaceSensorPropertiesInternal faceSensorPropertiesInternal;
        this.faceManager = faceManager;
        this.userRepository = userRepository;
        this.keyguardBypassController = keyguardBypassController;
        this.applicationScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundDispatcher = coroutineDispatcher2;
        this.sessionTracker = sessionTracker;
        this.uiEventsLogger = uiEventLogger;
        this.faceAuthLogger = faceAuthenticationLogger;
        this.biometricSettingsRepository = biometricSettingsRepository;
        this.keyguardRepository = keyguardRepository;
        this.keyguardInteractor = keyguardInteractor;
        this.alternateBouncerInteractor = alternateBouncerInteractor;
        this._authenticationStatus = StateFlowKt.MutableStateFlow(null);
        this._detectionStatus = StateFlowKt.MutableStateFlow(null);
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isLockedOut = MutableStateFlow;
        this.isLockedOut = MutableStateFlow;
        this.isDetectionSupported = (faceManager == null || (sensorPropertiesInternal = faceManager.getSensorPropertiesInternal()) == null || (faceSensorPropertiesInternal = (FaceSensorPropertiesInternal) CollectionsKt___CollectionsKt.firstOrNull(sensorPropertiesInternal)) == null) ? false : faceSensorPropertiesInternal.supportsFaceDetection;
        this._isAuthRunning = StateFlowKt.MutableStateFlow(bool);
        this._canRunFaceAuth = StateFlowKt.MutableStateFlow(Boolean.TRUE);
        this.canRunDetection = StateFlowKt.MutableStateFlow(bool);
        this._isAuthenticated = StateFlowKt.MutableStateFlow(bool);
        if (keyguardBypassController != null) {
            ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
            DeviceEntryFaceAuthRepositoryImpl$isBypassEnabled$1$1 deviceEntryFaceAuthRepositoryImpl$isBypassEnabled$1$1 = new DeviceEntryFaceAuthRepositoryImpl$isBypassEnabled$1$1(keyguardBypassController, null);
            conflatedCallbackFlow.getClass();
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = ConflatedCallbackFlow.conflatedCallbackFlow(deviceEntryFaceAuthRepositoryImpl$isBypassEnabled$1$1);
        }
        flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        this.isBypassEnabled = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        FaceManager.LockoutResetCallback lockoutResetCallback = new FaceManager.LockoutResetCallback() { // from class: com.android.systemui.keyguard.data.repository.DeviceEntryFaceAuthRepositoryImpl$faceLockoutResetCallback$1
            public final void onLockoutReset(int i) {
                DeviceEntryFaceAuthRepositoryImpl.this._isLockedOut.setValue(Boolean.FALSE);
            }
        };
        if (faceManager != null) {
            faceManager.addLockoutResetCallback(lockoutResetCallback);
        }
        this.faceAcquiredInfoIgnoreList = (Set) Arrays.stream(context.getResources().getIntArray(R.array.config_face_acquire_device_entry_ignorelist)).boxed().collect(Collectors.toSet());
        dumpManager.registerCriticalDumpable("DeviceEntryFaceAuthRepositoryImpl", this);
        Flags flags = Flags.INSTANCE;
        this.faceAuthCallback = new FaceManager.AuthenticationCallback() { // from class: com.android.systemui.keyguard.data.repository.DeviceEntryFaceAuthRepositoryImpl$faceAuthCallback$1
            public final void onAuthenticationAcquired(int i) {
                DeviceEntryFaceAuthRepositoryImpl.this._authenticationStatus.setValue(new AcquiredAuthenticationStatus(i));
            }

            public final void onAuthenticationError(int i, CharSequence charSequence) {
                ErrorAuthenticationStatus errorAuthenticationStatus = new ErrorAuthenticationStatus(i, String.valueOf(charSequence));
                int i2 = errorAuthenticationStatus.msgId;
                if (i2 == 9) {
                    DeviceEntryFaceAuthRepositoryImpl.this._isLockedOut.setValue(Boolean.TRUE);
                }
                DeviceEntryFaceAuthRepositoryImpl.this._authenticationStatus.setValue(errorAuthenticationStatus);
                DeviceEntryFaceAuthRepositoryImpl.this._isAuthenticated.setValue(Boolean.FALSE);
                if (i2 == 5) {
                    DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl = DeviceEntryFaceAuthRepositoryImpl.this;
                    StandaloneCoroutine standaloneCoroutine = deviceEntryFaceAuthRepositoryImpl.cancelNotReceivedHandlerJob;
                    if (standaloneCoroutine != null) {
                        standaloneCoroutine.cancel(null);
                    }
                    BuildersKt.launch$default(deviceEntryFaceAuthRepositoryImpl.applicationScope, null, null, new DeviceEntryFaceAuthRepositoryImpl$handleFaceCancellationError$1(deviceEntryFaceAuthRepositoryImpl, null), 3);
                }
                if (i2 == 1 || i2 == 2) {
                    DeviceEntryFaceAuthRepositoryImpl.this.faceAuthLogger.hardwareError(errorAuthenticationStatus);
                    DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl2 = DeviceEntryFaceAuthRepositoryImpl.this;
                    int i3 = deviceEntryFaceAuthRepositoryImpl2.retryCount;
                    if (i3 < 20) {
                        deviceEntryFaceAuthRepositoryImpl2.retryCount = i3 + 1;
                        StandaloneCoroutine standaloneCoroutine2 = deviceEntryFaceAuthRepositoryImpl2.halErrorRetryJob;
                        if (standaloneCoroutine2 != null) {
                            standaloneCoroutine2.cancel(null);
                        }
                        deviceEntryFaceAuthRepositoryImpl2.halErrorRetryJob = BuildersKt.launch$default(deviceEntryFaceAuthRepositoryImpl2.applicationScope, null, null, new DeviceEntryFaceAuthRepositoryImpl$handleFaceHardwareError$1(deviceEntryFaceAuthRepositoryImpl2, null), 3);
                    }
                }
                DeviceEntryFaceAuthRepositoryImpl.this.faceAuthLogger.authenticationError(i, charSequence, i2 == 9, i2 == 5);
                DeviceEntryFaceAuthRepositoryImpl.access$onFaceAuthRequestCompleted(DeviceEntryFaceAuthRepositoryImpl.this);
            }

            public final void onAuthenticationFailed() {
                DeviceEntryFaceAuthRepositoryImpl.this._authenticationStatus.setValue(FailedAuthenticationStatus.INSTANCE);
                DeviceEntryFaceAuthRepositoryImpl.this._isAuthenticated.setValue(Boolean.FALSE);
                LogBuffer.log$default(DeviceEntryFaceAuthRepositoryImpl.this.faceAuthLogger.logBuffer, "DeviceEntryFaceAuthRepositoryLog", LogLevel.DEBUG, "Face authentication failed", null, 8, null);
                DeviceEntryFaceAuthRepositoryImpl.access$onFaceAuthRequestCompleted(DeviceEntryFaceAuthRepositoryImpl.this);
            }

            public final void onAuthenticationHelp(int i, CharSequence charSequence) {
                if (DeviceEntryFaceAuthRepositoryImpl.this.faceAcquiredInfoIgnoreList.contains(Integer.valueOf(i))) {
                    return;
                }
                DeviceEntryFaceAuthRepositoryImpl.this._authenticationStatus.setValue(new HelpAuthenticationStatus(i, String.valueOf(charSequence)));
            }

            public final void onAuthenticationSucceeded(FaceManager.AuthenticationResult authenticationResult) {
                DeviceEntryFaceAuthRepositoryImpl.this._authenticationStatus.setValue(new SuccessAuthenticationStatus(authenticationResult));
                DeviceEntryFaceAuthRepositoryImpl.this._isAuthenticated.setValue(Boolean.TRUE);
                DeviceEntryFaceAuthRepositoryImpl.this.faceAuthLogger.faceAuthSuccess(authenticationResult);
                DeviceEntryFaceAuthRepositoryImpl.access$onFaceAuthRequestCompleted(DeviceEntryFaceAuthRepositoryImpl.this);
            }
        };
        this.detectionCallback = new FaceManager.FaceDetectionCallback() { // from class: com.android.systemui.keyguard.data.repository.DeviceEntryFaceAuthRepositoryImpl$detectionCallback$1
            public final void onFaceDetected(int i, int i2, boolean z) {
                LogBuffer.log$default(DeviceEntryFaceAuthRepositoryImpl.this.faceAuthLogger.logBuffer, "DeviceEntryFaceAuthRepositoryLog", LogLevel.DEBUG, "Face detected", null, 8, null);
                DeviceEntryFaceAuthRepositoryImpl.this._detectionStatus.setValue(new DetectionStatus(i, i2, z));
            }
        };
    }

    public static final void access$onFaceAuthRequestCompleted(DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl) {
        deviceEntryFaceAuthRepositoryImpl.cancellationInProgress = false;
        deviceEntryFaceAuthRepositoryImpl._isAuthRunning.setValue(Boolean.FALSE);
        deviceEntryFaceAuthRepositoryImpl.authCancellationSignal = null;
    }

    public final Object authenticate(FaceAuthUiEvent faceAuthUiEvent, boolean z, Continuation continuation) {
        Object obj;
        StateFlowImpl stateFlowImpl = this._isAuthRunning;
        boolean booleanValue = ((Boolean) stateFlowImpl.getValue()).booleanValue();
        FaceAuthenticationLogger faceAuthenticationLogger = this.faceAuthLogger;
        if (booleanValue) {
            faceAuthenticationLogger.ignoredFaceAuthTrigger(faceAuthUiEvent, "face auth is currently running");
            return Unit.INSTANCE;
        }
        if (this.cancellationInProgress) {
            faceAuthenticationLogger.queuingRequestWhileCancelling(this.faceAuthRequestedWhileCancellation, faceAuthUiEvent);
            this.faceAuthRequestedWhileCancellation = faceAuthUiEvent;
            return Unit.INSTANCE;
        }
        this.faceAuthRequestedWhileCancellation = null;
        boolean booleanValue2 = ((Boolean) this._canRunFaceAuth.getValue()).booleanValue();
        CoroutineDispatcher coroutineDispatcher = this.mainDispatcher;
        if (booleanValue2) {
            return BuildersKt.withContext(coroutineDispatcher, new DeviceEntryFaceAuthRepositoryImpl$authenticate$2(this, faceAuthUiEvent, null), continuation);
        }
        if (!z || !((Boolean) this.canRunDetection.getValue()).booleanValue()) {
            faceAuthenticationLogger.ignoredFaceAuthTrigger(faceAuthUiEvent, "face auth & detect gating check is false");
            return Unit.INSTANCE;
        }
        faceAuthenticationLogger.ignoredFaceAuthTrigger(faceAuthUiEvent, "face auth gating check is false, falling back to detection.");
        if (!this.isDetectionSupported) {
            FaceManager faceManager = this.faceManager;
            faceAuthenticationLogger.detectionNotSupported(faceManager, faceManager != null ? faceManager.getSensorPropertiesInternal() : null);
            obj = Unit.INSTANCE;
        } else if (((Boolean) stateFlowImpl.getValue()).booleanValue() || this.detectCancellationSignal != null) {
            faceAuthenticationLogger.skippingDetection(((Boolean) stateFlowImpl.getValue()).booleanValue(), this.detectCancellationSignal != null);
            obj = Unit.INSTANCE;
        } else {
            this.detectCancellationSignal = new CancellationSignal();
            obj = BuildersKt.withContext(coroutineDispatcher, new DeviceEntryFaceAuthRepositoryImpl$detect$2(this, null), continuation);
        }
        return obj == CoroutineSingletons.COROUTINE_SUSPENDED ? obj : Unit.INSTANCE;
    }

    public final void cancel() {
        CancellationSignal cancellationSignal = this.authCancellationSignal;
        if (cancellationSignal == null) {
            return;
        }
        cancellationSignal.cancel();
        this.cancelNotReceivedHandlerJob = BuildersKt.launch$default(this.applicationScope, null, null, new DeviceEntryFaceAuthRepositoryImpl$cancel$1(this, null), 3);
        this.cancellationInProgress = true;
        this._isAuthRunning.setValue(Boolean.FALSE);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        List sensorPropertiesInternal;
        FaceSensorPropertiesInternal faceSensorPropertiesInternal;
        printWriter.println("DeviceEntryFaceAuthRepositoryImpl state:");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  cancellationInProgress: ", this.cancellationInProgress, printWriter);
        printWriter.println("  _isLockedOut.value: " + this._isLockedOut.getValue());
        printWriter.println("  _isAuthRunning.value: " + this._isAuthRunning.getValue());
        printWriter.println("  isDetectionSupported: " + this.isDetectionSupported);
        printWriter.println("  FaceManager state:");
        StringBuilder sb = new StringBuilder("    faceManager: ");
        FaceManager faceManager = this.faceManager;
        sb.append(faceManager);
        printWriter.println(sb.toString());
        printWriter.println("    sensorPropertiesInternal: " + (faceManager != null ? faceManager.getSensorPropertiesInternal() : null));
        printWriter.println("    supportsFaceDetection: " + ((faceManager == null || (sensorPropertiesInternal = faceManager.getSensorPropertiesInternal()) == null || (faceSensorPropertiesInternal = (FaceSensorPropertiesInternal) CollectionsKt___CollectionsKt.firstOrNull(sensorPropertiesInternal)) == null) ? null : Boolean.valueOf(faceSensorPropertiesInternal.supportsFaceDetection)));
        FaceAuthUiEvent faceAuthUiEvent = this.faceAuthRequestedWhileCancellation;
        FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("  faceAuthRequestedWhileCancellation: ", faceAuthUiEvent != null ? faceAuthUiEvent.getReason() : null, printWriter);
        printWriter.println("  authCancellationSignal: " + this.authCancellationSignal);
        printWriter.println("  detectCancellationSignal: " + this.detectCancellationSignal);
        printWriter.println("  faceAcquiredInfoIgnoreList: " + this.faceAcquiredInfoIgnoreList);
        printWriter.println("  _authenticationStatus: " + this._authenticationStatus.getValue());
        printWriter.println("  _detectionStatus: " + this._detectionStatus.getValue());
        SideFpsController$$ExternalSyntheticOutline0.m105m("  currentUserId: ", ((UserRepositoryImpl) this.userRepository).getSelectedUserInfo().id, printWriter);
        printWriter.println("  keyguardSessionId: " + this.sessionTracker.getSessionId(1));
        KeyguardBypassController keyguardBypassController = this.keyguardBypassController;
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  lockscreenBypassEnabled: ", keyguardBypassController != null ? keyguardBypassController.getBypassEnabled() : false, printWriter);
    }

    public /* synthetic */ DeviceEntryFaceAuthRepositoryImpl(Context context, FaceManager faceManager, UserRepository userRepository, KeyguardBypassController keyguardBypassController, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, SessionTracker sessionTracker, UiEventLogger uiEventLogger, FaceAuthenticationLogger faceAuthenticationLogger, BiometricSettingsRepository biometricSettingsRepository, DeviceEntryFingerprintAuthRepository deviceEntryFingerprintAuthRepository, TrustRepository trustRepository, KeyguardRepository keyguardRepository, KeyguardInteractor keyguardInteractor, AlternateBouncerInteractor alternateBouncerInteractor, TableLogBuffer tableLogBuffer, TableLogBuffer tableLogBuffer2, KeyguardTransitionInteractor keyguardTransitionInteractor, FeatureFlags featureFlags, DumpManager dumpManager, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : faceManager, userRepository, (i & 8) != 0 ? null : keyguardBypassController, coroutineScope, coroutineDispatcher, coroutineDispatcher2, sessionTracker, uiEventLogger, faceAuthenticationLogger, biometricSettingsRepository, deviceEntryFingerprintAuthRepository, trustRepository, keyguardRepository, keyguardInteractor, alternateBouncerInteractor, tableLogBuffer, tableLogBuffer2, keyguardTransitionInteractor, featureFlags, dumpManager);
    }
}
