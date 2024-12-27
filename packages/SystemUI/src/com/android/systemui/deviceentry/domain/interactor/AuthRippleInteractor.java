package com.android.systemui.deviceentry.domain.interactor;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AuthRippleInteractor {
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 showUnlockRippleFromBiometricUnlock;
    public final ChannelFlowTransformLatest showUnlockRippleFromDeviceEntryIcon;

    public AuthRippleInteractor(DeviceEntrySourceInteractor deviceEntrySourceInteractor, DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor) {
        FlowKt.merge(FlowKt.transformLatest(deviceEntryUdfpsInteractor.isUdfpsSupported, new AuthRippleInteractor$special$$inlined$flatMapLatest$1(null, deviceEntrySourceInteractor)), deviceEntrySourceInteractor.deviceEntryFromBiometricSource);
    }
}
