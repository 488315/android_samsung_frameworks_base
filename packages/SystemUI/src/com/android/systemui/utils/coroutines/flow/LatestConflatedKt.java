package com.android.systemui.utils.coroutines.flow;

import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

public abstract class LatestConflatedKt {
    public static final Flow flatMapLatestConflated(Flow flow, Function2 function2) {
        return FlowKt.buffer$default(FlowKt.transformLatest(flow, new LatestConflatedKt$flatMapLatestConflated$$inlined$flatMapLatest$1(function2, null)), -1);
    }
}
