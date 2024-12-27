package com.android.systemui.statusbar;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class StatusBarStateControllerExtKt {
    public static final Flow getExpansionChanges(StatusBarStateController statusBarStateController) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        StatusBarStateControllerExtKt$expansionChanges$1 statusBarStateControllerExtKt$expansionChanges$1 = new StatusBarStateControllerExtKt$expansionChanges$1(statusBarStateController, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(statusBarStateControllerExtKt$expansionChanges$1);
    }
}
