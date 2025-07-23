package com.android.systemui.statusbar;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class StatusBarStateControllerExtKt {
    public static final Flow getExpansionChanges(StatusBarStateController statusBarStateController) {
        return FlowConflatedKt.conflatedCallbackFlow(new StatusBarStateControllerExtKt$expansionChanges$1(statusBarStateController, null));
    }
}
