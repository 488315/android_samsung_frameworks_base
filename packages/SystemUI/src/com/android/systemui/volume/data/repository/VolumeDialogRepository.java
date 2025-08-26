package com.android.systemui.volume.data.repository;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class VolumeDialogRepository {
    public final StateFlowImpl _isDialogVisible;
    public final ReadonlyStateFlow isDialogVisible;

    public VolumeDialogRepository() {
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isDialogVisible = stateFlowImplMutableStateFlow;
        this.isDialogVisible = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
    }
}
