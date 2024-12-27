package com.android.systemui.deviceentry.domain.interactor;

import android.app.trust.TrustManager;
import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.biometrics.data.repository.FacePropertyRepository;
import com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl;
import com.android.systemui.biometrics.data.repository.FaceSensorInfo;
import com.android.systemui.biometrics.shared.model.SensorStrength;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SystemUIDeviceEntryFaceAuthInteractor implements DeviceEntryFaceAuthInteractor {
    public final ChannelLimitedFlowMerge authenticationStatus;
    public final BiometricSettingsRepository biometricSettingsRepository;
    public final StateFlowImpl faceAuthenticationStatusOverride;
    public final FacePropertyRepository facePropertyRepository;
    public final StateFlow isAuthenticated;
    public final Flow isBypassEnabled;
    public final StateFlow isLockedOut;
    public final DeviceEntryFaceAuthRepository repository;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public SystemUIDeviceEntryFaceAuthInteractor(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, DeviceEntryFaceAuthRepository deviceEntryFaceAuthRepository, Lazy lazy, AlternateBouncerInteractor alternateBouncerInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, FaceAuthenticationLogger faceAuthenticationLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, UserRepository userRepository, FacePropertyRepository facePropertyRepository, FaceWakeUpTriggersConfig faceWakeUpTriggersConfig, PowerInteractor powerInteractor, BiometricSettingsRepository biometricSettingsRepository, TrustManager trustManager) {
        this.repository = deviceEntryFaceAuthRepository;
        this.facePropertyRepository = facePropertyRepository;
        this.biometricSettingsRepository = biometricSettingsRepository;
        new ArrayList();
        DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl = (DeviceEntryFaceAuthRepositoryImpl) deviceEntryFaceAuthRepository;
        this.authenticationStatus = FlowKt.merge(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(StateFlowKt.MutableStateFlow(null)), new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(deviceEntryFaceAuthRepositoryImpl._authenticationStatus));
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
