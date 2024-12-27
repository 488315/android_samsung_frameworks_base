package com.android.systemui.bouncer.data.repository;

import com.android.systemui.bouncer.shared.model.BouncerMessageModel;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class BouncerMessageRepositoryImpl implements BouncerMessageRepository {
    public final StateFlowImpl _bouncerMessage;
    public final StateFlowImpl bouncerMessage;

    public BouncerMessageRepositoryImpl() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new BouncerMessageModel(null, null, 3, null));
        this._bouncerMessage = MutableStateFlow;
        this.bouncerMessage = MutableStateFlow;
    }

    public final void setMessage(BouncerMessageModel bouncerMessageModel) {
        this._bouncerMessage.updateState(null, bouncerMessageModel);
    }
}
