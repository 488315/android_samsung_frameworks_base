package com.android.systemui.statusbar.phone.ongoingcall.data.repository;

import com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class OngoingCallRepository {
    public final StateFlowImpl _ongoingCallState;
    public final ReadonlyStateFlow ongoingCallState;

    public OngoingCallRepository() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(OngoingCallModel.NoCall.INSTANCE);
        this._ongoingCallState = MutableStateFlow;
        this.ongoingCallState = FlowKt.asStateFlow(MutableStateFlow);
    }
}
