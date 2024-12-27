package com.android.systemui.deviceentry.domain.interactor;

import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NoopDeviceEntryFaceAuthInteractor implements DeviceEntryFaceAuthInteractor {
    public final EmptyFlow authenticationStatus = EmptyFlow.INSTANCE;
    public final StateFlowImpl isAuthenticated;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 isBypassEnabled;
    public final StateFlowImpl isLockedOut;

    public NoopDeviceEntryFaceAuthInteractor() {
        Boolean bool = Boolean.FALSE;
        this.isLockedOut = StateFlowKt.MutableStateFlow(bool);
        this.isAuthenticated = StateFlowKt.MutableStateFlow(bool);
        this.isBypassEnabled = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final boolean canFaceAuthRun() {
        return false;
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
        return false;
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final boolean isFaceAuthStrong() {
        return false;
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final StateFlow isLockedOut() {
        return this.isLockedOut;
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final boolean isRunning() {
        return false;
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final void onAccessibilityAction() {
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final void onDeviceLifted() {
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final void onNotificationPanelClicked() {
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final void onPrimaryBouncerUserInput() {
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final void onUdfpsSensorTouched() {
    }

    @Override // com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor
    public final void onWalletLaunched() {
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
