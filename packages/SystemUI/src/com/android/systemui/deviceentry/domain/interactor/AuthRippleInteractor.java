package com.android.systemui.deviceentry.domain.interactor;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

public final class AuthRippleInteractor {
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 showUnlockRippleFromBiometricUnlock;
    public final ChannelFlowTransformLatest showUnlockRippleFromDeviceEntryIcon;

    public AuthRippleInteractor(DeviceEntrySourceInteractor deviceEntrySourceInteractor, DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor) {
        FlowKt.merge(FlowKt.transformLatest(deviceEntryUdfpsInteractor.isUdfpsSupported, new AuthRippleInteractor$special$$inlined$flatMapLatest$1(null, deviceEntrySourceInteractor)), deviceEntrySourceInteractor.deviceEntryFromBiometricSource);
    }
}
