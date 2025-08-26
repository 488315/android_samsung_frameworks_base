package com.android.systemui.deviceentry.ui.viewmodel;

import com.android.systemui.accessibility.domain.interactor.AccessibilityInteractor;
import com.android.systemui.biometrics.UdfpsUtils;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public abstract class UdfpsAccessibilityOverlayViewModel {
    public final ReadonlyStateFlow udfpsOverlayParams;
    public final UdfpsUtils udfpsUtils = new UdfpsUtils();
    public final ChannelFlowTransformLatest visible;

    public UdfpsAccessibilityOverlayViewModel(UdfpsOverlayInteractor udfpsOverlayInteractor, AccessibilityInteractor accessibilityInteractor) {
        this.udfpsOverlayParams = udfpsOverlayInteractor.udfpsOverlayParams;
        this.visible = FlowKt.transformLatest(accessibilityInteractor.isTouchExplorationEnabled, new UdfpsAccessibilityOverlayViewModel$special$$inlined$flatMapLatest$1(null, this));
    }

    public abstract Flow isVisibleWhenTouchExplorationEnabled();
}
