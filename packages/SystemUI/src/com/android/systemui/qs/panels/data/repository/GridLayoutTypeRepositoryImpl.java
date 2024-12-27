package com.android.systemui.qs.panels.data.repository;

import com.android.systemui.qs.panels.shared.model.PartitionedGridLayoutType;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class GridLayoutTypeRepositoryImpl implements GridLayoutTypeRepository {
    public final StateFlowImpl _layout;
    public final ReadonlyStateFlow layout = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(PartitionedGridLayoutType.INSTANCE));
}
