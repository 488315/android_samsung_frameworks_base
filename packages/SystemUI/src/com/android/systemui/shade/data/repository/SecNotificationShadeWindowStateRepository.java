package com.android.systemui.shade.data.repository;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class SecNotificationShadeWindowStateRepository {
    public final StateFlowImpl _shadeOrQsExpanded;
    public final StateFlowImpl _state;
    public final StateFlowImpl _statusBarState;
    public final StateFlowImpl _visibility;
    public final ReadonlyStateFlow shadeOrQsExpanded;
    public final ReadonlyStateFlow state;
    public final ReadonlyStateFlow statusBarState;
    public final ReadonlyStateFlow visibility;

    public SecNotificationShadeWindowStateRepository() {
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._state = stateFlowImplMutableStateFlow;
        this.state = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._shadeOrQsExpanded = stateFlowImplMutableStateFlow2;
        this.shadeOrQsExpanded = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(1);
        this._statusBarState = stateFlowImplMutableStateFlow3;
        this.statusBarState = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(0);
        this._visibility = stateFlowImplMutableStateFlow4;
        this.visibility = FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
    }
}
