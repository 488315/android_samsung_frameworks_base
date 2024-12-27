package com.android.systemui.bouncer.data.repository;

import com.android.systemui.bouncer.shared.model.BouncerMessageModel;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
