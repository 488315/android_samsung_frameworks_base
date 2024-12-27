package com.android.systemui.qs.panels.data.repository;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class InfiniteGridSizeRepository {
    public final StateFlowImpl _columns;
    public final ReadonlyStateFlow columns = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(4));
}
