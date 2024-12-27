package com.android.systemui.assist.data.repository;

import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AssistRepository {
    public final SharedFlowImpl _latestInvocationType;
    public final ReadonlySharedFlow latestInvocationType;

    public AssistRepository() {
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);
        this._latestInvocationType = MutableSharedFlow$default;
        FlowKt.asSharedFlow(MutableSharedFlow$default);
    }
}
