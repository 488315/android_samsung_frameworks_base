package com.android.systemui.flags;

import com.android.systemui.flags.ConditionalRestarter;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import dagger.Lazy;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class PluggedInCondition implements ConditionalRestarter.Condition {
    public final Lazy batteryControllerLazy;
    public final Flow canRestartNow = FlowConflatedKt.conflatedCallbackFlow(new PluggedInCondition$canRestartNow$1(this, null));

    public PluggedInCondition(Lazy lazy) {
        this.batteryControllerLazy = lazy;
    }

    @Override // com.android.systemui.flags.ConditionalRestarter.Condition
    public final Flow getCanRestartNow() {
        return this.canRestartNow;
    }
}
