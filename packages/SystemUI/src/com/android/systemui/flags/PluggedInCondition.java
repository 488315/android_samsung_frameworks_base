package com.android.systemui.flags;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.flags.ConditionalRestarter;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import dagger.Lazy;
import kotlinx.coroutines.flow.Flow;

public final class PluggedInCondition implements ConditionalRestarter.Condition {
    public final Lazy batteryControllerLazy;
    public final Flow canRestartNow;

    public PluggedInCondition(Lazy lazy) {
        this.batteryControllerLazy = lazy;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        PluggedInCondition$canRestartNow$1 pluggedInCondition$canRestartNow$1 = new PluggedInCondition$canRestartNow$1(this, null);
        conflatedCallbackFlow.getClass();
        this.canRestartNow = FlowConflatedKt.conflatedCallbackFlow(pluggedInCondition$canRestartNow$1);
    }

    @Override // com.android.systemui.flags.ConditionalRestarter.Condition
    public final Flow getCanRestartNow() {
        return this.canRestartNow;
    }
}
