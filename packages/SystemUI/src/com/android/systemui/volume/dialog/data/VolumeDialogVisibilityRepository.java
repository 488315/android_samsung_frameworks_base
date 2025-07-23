package com.android.systemui.volume.dialog.data;

import com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogVisibilityRepository {
    public final ReadonlyStateFlow dialogVisibility;
    public final StateFlowImpl mutableDialogVisibility;

    public VolumeDialogVisibilityRepository() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(VolumeDialogVisibilityModel.Invisible.Companion);
        this.mutableDialogVisibility = MutableStateFlow;
        this.dialogVisibility = FlowKt.asStateFlow(MutableStateFlow);
    }
}
