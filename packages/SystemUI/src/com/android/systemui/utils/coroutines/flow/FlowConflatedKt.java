package com.android.systemui.utils.coroutines.flow;

import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

public abstract class FlowConflatedKt {
    public static final Flow conflatedCallbackFlow(Function2 function2) {
        return FlowKt.buffer$default(FlowKt.callbackFlow(function2), -1);
    }
}
