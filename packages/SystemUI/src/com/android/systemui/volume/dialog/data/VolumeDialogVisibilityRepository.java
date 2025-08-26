package com.android.systemui.volume.dialog.data;

import com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class VolumeDialogVisibilityRepository {
    public final ReadonlyStateFlow dialogVisibility;
    public final StateFlowImpl mutableDialogVisibility;

    public VolumeDialogVisibilityRepository() {
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(VolumeDialogVisibilityModel.Invisible.Companion);
        this.mutableDialogVisibility = stateFlowImplMutableStateFlow;
        this.dialogVisibility = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
    }
}
