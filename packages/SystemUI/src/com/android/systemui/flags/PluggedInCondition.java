package com.android.systemui.flags;

import com.android.systemui.flags.ConditionalRestarter;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import dagger.Lazy;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
