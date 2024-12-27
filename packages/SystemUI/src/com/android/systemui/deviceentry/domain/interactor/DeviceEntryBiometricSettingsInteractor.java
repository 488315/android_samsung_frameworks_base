package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DeviceEntryBiometricSettingsInteractor {
    public final Flow authenticationFlags;
    public final Flow faceAuthCurrentlyAllowed;
    public final StateFlow fingerprintAuthCurrentlyAllowed;
    public final StateFlow isFaceAuthEnrolledAndEnabled;
    public final StateFlow isFingerprintAuthEnrolledAndEnabled;

    public DeviceEntryBiometricSettingsInteractor(BiometricSettingsRepository biometricSettingsRepository) {
        BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl = (BiometricSettingsRepositoryImpl) biometricSettingsRepository;
        this.authenticationFlags = biometricSettingsRepositoryImpl.authenticationFlags;
        ReadonlyStateFlow readonlyStateFlow = biometricSettingsRepositoryImpl.isFingerprintEnrolledAndEnabled;
        this.isFingerprintAuthEnrolledAndEnabled = readonlyStateFlow;
        this.fingerprintAuthCurrentlyAllowed = biometricSettingsRepositoryImpl.isFingerprintAuthCurrentlyAllowed;
        ReadonlyStateFlow readonlyStateFlow2 = biometricSettingsRepositoryImpl.isFaceAuthEnrolledAndEnabled;
        this.isFaceAuthEnrolledAndEnabled = readonlyStateFlow2;
        this.faceAuthCurrentlyAllowed = biometricSettingsRepositoryImpl.isFaceAuthCurrentlyAllowed;
        new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, readonlyStateFlow2, new DeviceEntryBiometricSettingsInteractor$fingerprintAndFaceEnrolledAndEnabled$1(null));
    }
}
