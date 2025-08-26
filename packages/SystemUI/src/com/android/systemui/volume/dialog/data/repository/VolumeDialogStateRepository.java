package com.android.systemui.volume.dialog.data.repository;

import com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class VolumeDialogStateRepository {
    public final StateFlowImpl mutableState;
    public final ReadonlyStateFlow state;

    public VolumeDialogStateRepository() {
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(new VolumeDialogStateModel(false, null, null, false, null, 0, 0, 0, null, null, 0, false, false, false, false, 32767, null));
        this.mutableState = stateFlowImplMutableStateFlow;
        this.state = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
    }
}
