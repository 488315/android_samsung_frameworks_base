package com.android.systemui.shade;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SplitStateRepository {
    public final StateFlowImpl _isQsState;
    public final StateFlowImpl _reverseState;
    public final StateFlowImpl _transitioning;
    public final ReadonlyStateFlow isQsState;
    public final ReadonlyStateFlow reverseState;
    public final ReadonlyStateFlow transitioning;

    public SplitStateRepository() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(0);
        this._isQsState = MutableStateFlow;
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._reverseState = MutableStateFlow2;
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this._transitioning = MutableStateFlow3;
        this.isQsState = FlowKt.asStateFlow(MutableStateFlow);
        this.reverseState = FlowKt.asStateFlow(MutableStateFlow2);
        this.transitioning = FlowKt.asStateFlow(MutableStateFlow3);
    }
}
