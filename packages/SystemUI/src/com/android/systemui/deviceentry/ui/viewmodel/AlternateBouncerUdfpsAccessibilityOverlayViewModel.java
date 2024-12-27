package com.android.systemui.deviceentry.ui.viewmodel;

import com.android.systemui.accessibility.domain.interactor.AccessibilityInteractor;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AlternateBouncerUdfpsAccessibilityOverlayViewModel extends UdfpsAccessibilityOverlayViewModel {
    public AlternateBouncerUdfpsAccessibilityOverlayViewModel(UdfpsOverlayInteractor udfpsOverlayInteractor, AccessibilityInteractor accessibilityInteractor) {
        super(udfpsOverlayInteractor, accessibilityInteractor);
    }

    @Override // com.android.systemui.deviceentry.ui.viewmodel.UdfpsAccessibilityOverlayViewModel
    public final Flow isVisibleWhenTouchExplorationEnabled() {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
    }
}
