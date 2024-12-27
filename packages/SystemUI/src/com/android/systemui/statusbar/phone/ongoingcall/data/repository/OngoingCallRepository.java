package com.android.systemui.statusbar.phone.ongoingcall.data.repository;

import com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class OngoingCallRepository {
    public final StateFlowImpl _ongoingCallState;
    public final ReadonlyStateFlow ongoingCallState;

    public OngoingCallRepository() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(OngoingCallModel.NoCall.INSTANCE);
        this._ongoingCallState = MutableStateFlow;
        this.ongoingCallState = FlowKt.asStateFlow(MutableStateFlow);
    }
}
