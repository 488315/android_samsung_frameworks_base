package com.android.systemui.qs.panels.data.repository;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class TileSquishinessRepository {
    public final StateFlowImpl _squishiness;
    public final ReadonlyStateFlow squishiness;

    public TileSquishinessRepository() {
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Float.valueOf(1.0f));
        this._squishiness = stateFlowImplMutableStateFlow;
        this.squishiness = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
    }
}
