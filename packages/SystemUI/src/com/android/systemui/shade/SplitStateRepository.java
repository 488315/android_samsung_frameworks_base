package com.android.systemui.shade;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class SplitStateRepository {
    public final StateFlowImpl _isQsState;
    public final StateFlowImpl _transitioning;
    public final ReadonlyStateFlow isQsState;
    public final ReadonlyStateFlow transitioning;

    public SplitStateRepository() {
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(-1);
        this._isQsState = stateFlowImplMutableStateFlow;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._transitioning = stateFlowImplMutableStateFlow2;
        this.isQsState = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.transitioning = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
    }
}
