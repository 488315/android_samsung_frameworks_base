package com.android.systemui.settings.brightness.data.repository;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BrightnessMirrorShowingRepository {
    public final StateFlowImpl _isShowing;
    public final ReadonlyStateFlow isShowing;

    public BrightnessMirrorShowingRepository() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isShowing = MutableStateFlow;
        this.isShowing = FlowKt.asStateFlow(MutableStateFlow);
    }
}
