package com.android.systemui.communal.posturing.data.repository;

import com.android.systemui.communal.posturing.data.model.PositionState;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class NoOpPosturingRepository implements PosturingRepository {
    public final ReadonlyStateFlow positionState = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(new PositionState(null, null, 3, null)));
}
