package com.android.systemui.common.coroutine;

import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ConflatedCallbackFlow {
    public static final ConflatedCallbackFlow INSTANCE = new ConflatedCallbackFlow();

    private ConflatedCallbackFlow() {
    }

    public static Flow conflatedCallbackFlow(Function2 function2) {
        return FlowKt.buffer$default(FlowKt.callbackFlow(function2), -1);
    }
}
