package com.android.systemui.settings.brightness.data.repository;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class BrightnessMirrorShowingRepository {
    public final StateFlowImpl _isShowing;
    public final ReadonlyStateFlow isShowing;

    public BrightnessMirrorShowingRepository() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isShowing = MutableStateFlow;
        this.isShowing = FlowKt.asStateFlow(MutableStateFlow);
    }
}
