package com.android.systemui.globalactions.data.repository;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class GlobalActionsRepository {
    public final StateFlowImpl _isVisible;
    public final ReadonlyStateFlow isVisible;

    public GlobalActionsRepository() {
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isVisible = stateFlowImplMutableStateFlow;
        this.isVisible = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
    }
}
