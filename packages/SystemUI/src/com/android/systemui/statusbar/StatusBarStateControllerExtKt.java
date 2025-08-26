package com.android.systemui.statusbar;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public abstract class StatusBarStateControllerExtKt {
    public static final Flow getExpansionChanges(StatusBarStateController statusBarStateController) {
        return FlowConflatedKt.conflatedCallbackFlow(new StatusBarStateControllerExtKt$expansionChanges$1(statusBarStateController, null));
    }
}
