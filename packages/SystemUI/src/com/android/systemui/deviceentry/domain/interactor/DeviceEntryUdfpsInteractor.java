package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl;
import com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepository;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DeviceEntryUdfpsInteractor {
    public final ChannelFlowTransformLatest isListeningForUdfps;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isUdfpsEnrolledAndEnabled;
    public final StateFlow isUdfpsSupported;
    public final ChannelFlowTransformLatest udfpsLocation;

    public DeviceEntryUdfpsInteractor(FingerprintPropertyInteractor fingerprintPropertyInteractor, DeviceEntryFingerprintAuthRepository deviceEntryFingerprintAuthRepository, BiometricSettingsRepository biometricSettingsRepository) {
        ReadonlyStateFlow readonlyStateFlow = fingerprintPropertyInteractor.isUdfps;
        this.isUdfpsSupported = readonlyStateFlow;
        this.isUdfpsEnrolledAndEnabled = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, ((BiometricSettingsRepositoryImpl) biometricSettingsRepository).isFingerprintEnrolledAndEnabled, new DeviceEntryUdfpsInteractor$isUdfpsEnrolledAndEnabled$1(null));
        this.isListeningForUdfps = FlowKt.transformLatest(readonlyStateFlow, new DeviceEntryUdfpsInteractor$special$$inlined$flatMapLatest$1(null, deviceEntryFingerprintAuthRepository));
        this.udfpsLocation = FlowKt.transformLatest(readonlyStateFlow, new DeviceEntryUdfpsInteractor$special$$inlined$flatMapLatest$2(null, fingerprintPropertyInteractor));
    }
}
