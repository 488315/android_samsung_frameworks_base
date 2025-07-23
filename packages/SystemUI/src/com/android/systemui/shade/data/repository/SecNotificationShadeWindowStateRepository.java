package com.android.systemui.shade.data.repository;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._state = MutableStateFlow;
        this.state = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._shadeOrQsExpanded = MutableStateFlow2;
        this.shadeOrQsExpanded = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(1);
        this._statusBarState = MutableStateFlow3;
        this.statusBarState = FlowKt.asStateFlow(MutableStateFlow3);
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(0);
        this._visibility = MutableStateFlow4;
        this.visibility = FlowKt.asStateFlow(MutableStateFlow4);
    }
}
