package com.android.systemui.assist.data.repository;

import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AssistRepository {
    public final SharedFlowImpl _latestInvocationType;
    public final ReadonlySharedFlow latestInvocationType;

    public AssistRepository() {
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);
        this._latestInvocationType = MutableSharedFlow$default;
        this.latestInvocationType = FlowKt.asSharedFlow(MutableSharedFlow$default);
    }
}
