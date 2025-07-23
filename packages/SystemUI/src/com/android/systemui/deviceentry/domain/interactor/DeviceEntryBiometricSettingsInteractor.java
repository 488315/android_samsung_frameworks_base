package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DeviceEntryBiometricSettingsInteractor {
    public final ChannelFlowTransformLatest authenticationFlags;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 faceAuthCurrentlyAllowed;
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
