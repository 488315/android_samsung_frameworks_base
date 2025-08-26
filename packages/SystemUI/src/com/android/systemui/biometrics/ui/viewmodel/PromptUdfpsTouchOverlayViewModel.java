package com.android.systemui.biometrics.ui.viewmodel;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public final class PromptUdfpsTouchOverlayViewModel implements UdfpsTouchOverlayViewModel {
    public final StateFlowImpl shouldHandleTouches = StateFlowKt.MutableStateFlow(Boolean.TRUE);

    @Override // com.android.systemui.biometrics.ui.viewmodel.UdfpsTouchOverlayViewModel
    public final Flow getShouldHandleTouches() {
        return this.shouldHandleTouches;
    }
}
