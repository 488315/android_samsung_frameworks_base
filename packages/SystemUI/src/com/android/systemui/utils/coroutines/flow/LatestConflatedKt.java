package com.android.systemui.utils.coroutines.flow;

import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class LatestConflatedKt {
    public static final Flow flatMapLatestConflated(Flow flow, Function2 function2) {
        return FlowKt.buffer$default(FlowKt.transformLatest(flow, new LatestConflatedKt$flatMapLatestConflated$$inlined$flatMapLatest$1(function2, null)), -1, 2);
    }
}
