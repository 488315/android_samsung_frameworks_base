package com.android.systemui.volume.dialog.data.repository;

import com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogStateRepository {
    public final StateFlowImpl mutableState;
    public final ReadonlyStateFlow state;

    public VolumeDialogStateRepository() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new VolumeDialogStateModel(false, null, null, false, null, 0, 0, 0, null, null, 0, false, false, false, false, 32767, null));
        this.mutableState = MutableStateFlow;
        this.state = FlowKt.asStateFlow(MutableStateFlow);
    }
}
