package com.android.systemui.statusbar.policy;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class HeadsUpManagerExtKt {
    public static final Flow getHeadsUpEvents(HeadsUpManager headsUpManager) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        HeadsUpManagerExtKt$headsUpEvents$1 headsUpManagerExtKt$headsUpEvents$1 = new HeadsUpManagerExtKt$headsUpEvents$1(headsUpManager, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(headsUpManagerExtKt$headsUpEvents$1);
    }
}
