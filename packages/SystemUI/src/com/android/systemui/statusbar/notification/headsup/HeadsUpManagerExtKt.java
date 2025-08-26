package com.android.systemui.statusbar.notification.headsup;

import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public abstract class HeadsUpManagerExtKt {
    public static final Flow getHeadsUpEvents(HeadsUpManager headsUpManager) {
        return FlowConflatedKt.conflatedCallbackFlow(new HeadsUpManagerExtKt$headsUpEvents$1(headsUpManager, null));
    }
}
