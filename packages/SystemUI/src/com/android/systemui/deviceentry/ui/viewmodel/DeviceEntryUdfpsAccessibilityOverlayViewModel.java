package com.android.systemui.deviceentry.ui.viewmodel;

import com.android.systemui.accessibility.domain.interactor.AccessibilityInteractor;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DeviceEntryUdfpsAccessibilityOverlayViewModel extends UdfpsAccessibilityOverlayViewModel {
    public final DeviceEntryForegroundViewModel deviceEntryFgIconViewModel;
    public final DeviceEntryIconViewModel deviceEntryIconViewModel;

    public DeviceEntryUdfpsAccessibilityOverlayViewModel(UdfpsOverlayInteractor udfpsOverlayInteractor, AccessibilityInteractor accessibilityInteractor, DeviceEntryIconViewModel deviceEntryIconViewModel, DeviceEntryForegroundViewModel deviceEntryForegroundViewModel) {
        super(udfpsOverlayInteractor, accessibilityInteractor);
        this.deviceEntryIconViewModel = deviceEntryIconViewModel;
        this.deviceEntryFgIconViewModel = deviceEntryForegroundViewModel;
    }

    @Override // com.android.systemui.deviceentry.ui.viewmodel.UdfpsAccessibilityOverlayViewModel
    public final Flow isVisibleWhenTouchExplorationEnabled() {
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.deviceEntryFgIconViewModel.viewModel, this.deviceEntryIconViewModel.deviceEntryViewAlpha, new DeviceEntryUdfpsAccessibilityOverlayViewModel$isVisibleWhenTouchExplorationEnabled$1(null));
    }
}
