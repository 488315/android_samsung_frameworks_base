package com.android.systemui.statusbar.policy;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

public abstract class HeadsUpManagerExtKt {
    public static final Flow getHeadsUpEvents(HeadsUpManager headsUpManager) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        HeadsUpManagerExtKt$headsUpEvents$1 headsUpManagerExtKt$headsUpEvents$1 = new HeadsUpManagerExtKt$headsUpEvents$1(headsUpManager, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(headsUpManagerExtKt$headsUpEvents$1);
    }
}
