package com.android.systemui.deviceentry.domain.interactor;

import android.app.trust.TrustManager;
import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.biometrics.data.repository.FacePropertyRepository;
import com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl;
import com.android.systemui.biometrics.data.repository.FaceSensorInfo;
import com.android.systemui.biometrics.shared.model.SensorStrength;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepository;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl;
import com.android.systemui.deviceentry.data.repository.FaceWakeUpTriggersConfig;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.log.FaceAuthenticationLogger;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.user.data.repository.UserRepository;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.List;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* loaded from: classes2.dex */
public final class SystemUIDeviceEntryFaceAuthInteractor implements DeviceEntryFaceAuthInteractor {
    public final ChannelLimitedFlowMerge authenticationStatus;
    public final BiometricSettingsRepository biometricSettingsRepository;
    public final StateFlowImpl faceAuthenticationStatusOverride;
    public final FacePropertyRepository facePropertyRepository;
    public final StateFlowImpl isAuthenticated;
    public final Flow isBypassEnabled;
    public final StateFlowImpl isLockedOut;
    public final List listeners = new ArrayList();
    public final Lazy primaryBouncerInteractor;
    public final DeviceEntryFaceAuthRepository repository;

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

    public SystemUIDeviceEntryFaceAuthInteractor(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, DeviceEntryFaceAuthRepository deviceEntryFaceAuthRepository, Lazy lazy, AlternateBouncerInteractor alternateBouncerInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, FaceAuthenticationLogger faceAuthenticationLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, UserRepository userRepository, FacePropertyRepository facePropertyRepository, FaceWakeUpTriggersConfig faceWakeUpTriggersConfig, PowerInteractor powerInteractor, BiometricSettingsRepository biometricSettingsRepository, TrustManager trustManager, Lazy lazy2, DeviceEntryFaceAuthStatusInteractor deviceEntryFaceAuthStatusInteractor) {
        this.repository = deviceEntryFaceAuthRepository;
        this.primaryBouncerInteractor = lazy;
        this.facePropertyRepository = facePropertyRepository;
        this.biometricSettingsRepository = biometricSettingsRepository;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.deviceentry.domain.interactor.SystemUIDeviceEntryFaceAuthInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ((PrimaryBouncerInteractor) this.f$0.primaryBouncerInteractor.get()).isShowing;
            }
        });
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.faceAuthenticationStatusOverride = stateFlowImplMutableStateFlow;
        this.authenticationStatus = FlowKt.merge(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(stateFlowImplMutableStateFlow), new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(deviceEntryFaceAuthStatusInteractor.authenticationStatus));
        DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl = (DeviceEntryFaceAuthRepositoryImpl) deviceEntryFaceAuthRepository;
        new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(deviceEntryFaceAuthRepositoryImpl._detectionStatus);
        this.isLockedOut = deviceEntryFaceAuthRepositoryImpl.isLockedOut;
        this.isAuthenticated = deviceEntryFaceAuthRepositoryImpl.isAuthenticated;
        this.isBypassEnabled = deviceEntryFaceAuthRepositoryImpl.isBypassEnabled;
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final boolean canFaceAuthRun() {
        return ((Boolean) ((DeviceEntryFaceAuthRepositoryImpl) this.repository).canRunFaceAuth.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final Flow getAuthenticationStatus() {
        return this.authenticationStatus;
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final StateFlow isAuthenticated() {
        return this.isAuthenticated;
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final Flow isBypassEnabled() {
        return this.isBypassEnabled;
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final boolean isFaceAuthEnabledAndEnrolled() {
        return ((Boolean) ((BiometricSettingsRepositoryImpl) this.biometricSettingsRepository).isFaceAuthEnrolledAndEnabled.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final boolean isFaceAuthStrong() {
        FaceSensorInfo faceSensorInfo = (FaceSensorInfo) ((FacePropertyRepositoryImpl) this.facePropertyRepository).sensorInfo.$$delegate_0.getValue();
        return (faceSensorInfo != null ? faceSensorInfo.strength : null) == SensorStrength.STRONG;
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final StateFlow isLockedOut() {
        return this.isLockedOut;
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final boolean isRunning() {
        return ((Boolean) ((DeviceEntryFaceAuthRepositoryImpl) this.repository)._isAuthRunning.getValue()).booleanValue();
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final void onAccessibilityAction() {
        FaceAuthUiEvent faceAuthUiEvent = FaceAuthUiEvent.FACE_AUTH_TRIGGERED_OCCLUDING_APP_REQUESTED;
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final void onDeviceLifted() {
        FaceAuthUiEvent faceAuthUiEvent = FaceAuthUiEvent.FACE_AUTH_TRIGGERED_OCCLUDING_APP_REQUESTED;
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final void onNotificationPanelClicked() {
        FaceAuthUiEvent faceAuthUiEvent = FaceAuthUiEvent.FACE_AUTH_TRIGGERED_OCCLUDING_APP_REQUESTED;
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final void onPrimaryBouncerUserInput() {
        ((DeviceEntryFaceAuthRepositoryImpl) this.repository).cancel();
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final void onUdfpsSensorTouched() {
        FaceAuthUiEvent faceAuthUiEvent = FaceAuthUiEvent.FACE_AUTH_TRIGGERED_OCCLUDING_APP_REQUESTED;
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final void onWalletLaunched() {
        FaceSensorInfo faceSensorInfo = (FaceSensorInfo) ((FacePropertyRepositoryImpl) this.facePropertyRepository).sensorInfo.$$delegate_0.getValue();
        if ((faceSensorInfo != null ? faceSensorInfo.strength : null) == SensorStrength.STRONG) {
            FaceAuthUiEvent faceAuthUiEvent = FaceAuthUiEvent.FACE_AUTH_TRIGGERED_OCCLUDING_APP_REQUESTED;
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
