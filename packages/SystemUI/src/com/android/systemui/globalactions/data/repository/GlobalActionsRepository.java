package com.android.systemui.globalactions.data.repository;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GlobalActionsRepository {
    public final StateFlowImpl _isVisible;
    public final ReadonlyStateFlow isVisible;

    public GlobalActionsRepository() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isVisible = MutableStateFlow;
        this.isVisible = FlowKt.asStateFlow(MutableStateFlow);
    }
}
