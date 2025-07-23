package com.android.systemui.statusbar.notification.headsup;

import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class HeadsUpManagerExtKt {
    public static final Flow getHeadsUpEvents(HeadsUpManager headsUpManager) {
        return FlowConflatedKt.conflatedCallbackFlow(new HeadsUpManagerExtKt$headsUpEvents$1(headsUpManager, null));
    }
}
