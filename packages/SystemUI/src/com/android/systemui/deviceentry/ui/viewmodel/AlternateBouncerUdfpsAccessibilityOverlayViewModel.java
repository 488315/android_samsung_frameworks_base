package com.android.systemui.deviceentry.ui.viewmodel;

import com.android.systemui.accessibility.domain.interactor.AccessibilityInteractor;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AlternateBouncerUdfpsAccessibilityOverlayViewModel extends UdfpsAccessibilityOverlayViewModel {
    public AlternateBouncerUdfpsAccessibilityOverlayViewModel(UdfpsOverlayInteractor udfpsOverlayInteractor, AccessibilityInteractor accessibilityInteractor) {
        super(udfpsOverlayInteractor, accessibilityInteractor);
    }

    @Override // com.android.systemui.deviceentry.ui.viewmodel.UdfpsAccessibilityOverlayViewModel
    public final Flow isVisibleWhenTouchExplorationEnabled() {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
    }
}
