package com.android.systemui.globalactions.data.repository;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class GlobalActionsRepository {
    public final StateFlowImpl _isVisible;
    public final ReadonlyStateFlow isVisible;

    public GlobalActionsRepository() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isVisible = MutableStateFlow;
        FlowKt.asStateFlow(MutableStateFlow);
    }
}
