package com.android.systemui.volume.data.repository;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogRepository {
    public final StateFlowImpl _isDialogVisible;
    public final ReadonlyStateFlow isDialogVisible;

    public VolumeDialogRepository() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isDialogVisible = MutableStateFlow;
        this.isDialogVisible = FlowKt.asStateFlow(MutableStateFlow);
    }
}
