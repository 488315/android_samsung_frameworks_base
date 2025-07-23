package com.android.systemui.kairos;

import kotlinx.coroutines.flow.ChannelFlowBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ToColdFlowKt {
    public static final Flow toColdConflatedFlow(Events events, KairosNetwork kairosNetwork) {
        return FlowKt.buffer$default(new ChannelFlowBuilder(new ToColdFlowKt$toColdConflatedFlow$1(kairosNetwork, events, null), null, 0, null, 14, null), -1, 2);
    }

    public static final Flow toColdConflatedFlow(State state, KairosNetwork kairosNetwork) {
        return FlowKt.buffer$default(new ChannelFlowBuilder(new ToColdFlowKt$toColdConflatedFlow$2(kairosNetwork, state, null), null, 0, null, 14, null), -1, 2);
    }
}
