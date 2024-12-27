package com.android.systemui.flags;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.flags.ConditionalRestarter;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import dagger.Lazy;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
