package com.android.systemui.assist.data.repository;

import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* loaded from: classes.dex */
public final class AssistRepository {
    public final SharedFlowImpl _latestInvocationType;
    public final ReadonlySharedFlow latestInvocationType;

    public AssistRepository() {
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);
        this._latestInvocationType = sharedFlowImplMutableSharedFlow$default;
        this.latestInvocationType = FlowKt.asSharedFlow(sharedFlowImplMutableSharedFlow$default);
    }
}
