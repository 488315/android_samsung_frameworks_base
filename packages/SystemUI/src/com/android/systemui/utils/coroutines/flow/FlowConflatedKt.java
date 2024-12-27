package com.android.systemui.utils.coroutines.flow;

import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public abstract class FlowConflatedKt {
    public static final Flow conflatedCallbackFlow(Function2 function2) {
        return FlowKt.buffer$default(FlowKt.callbackFlow(function2), -1);
    }
}
