package com.android.systemui.statusbar;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

public abstract class StatusBarStateControllerExtKt {
    public static final Flow getExpansionChanges(StatusBarStateController statusBarStateController) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        StatusBarStateControllerExtKt$expansionChanges$1 statusBarStateControllerExtKt$expansionChanges$1 = new StatusBarStateControllerExtKt$expansionChanges$1(statusBarStateController, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(statusBarStateControllerExtKt$expansionChanges$1);
    }
}
