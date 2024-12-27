package com.android.systemui.qs.panels.data.repository;

import com.android.systemui.qs.panels.shared.model.PartitionedGridLayoutType;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class GridLayoutTypeRepositoryImpl implements GridLayoutTypeRepository {
    public final StateFlowImpl _layout;
    public final ReadonlyStateFlow layout = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(PartitionedGridLayoutType.INSTANCE));
}
