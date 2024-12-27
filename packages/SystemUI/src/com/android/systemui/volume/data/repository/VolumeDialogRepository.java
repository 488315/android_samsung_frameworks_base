package com.android.systemui.volume.data.repository;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class VolumeDialogRepository {
    public final StateFlowImpl _isDialogVisible;
    public final ReadonlyStateFlow isDialogVisible;

    public VolumeDialogRepository() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isDialogVisible = MutableStateFlow;
        FlowKt.asStateFlow(MutableStateFlow);
    }
}
