package com.android.systemui.statusbar;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class StatusBarStateControllerExtKt {
    public static final Flow getExpansionChanges(StatusBarStateController statusBarStateController) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        StatusBarStateControllerExtKt$expansionChanges$1 statusBarStateControllerExtKt$expansionChanges$1 = new StatusBarStateControllerExtKt$expansionChanges$1(statusBarStateController, null);
        conflatedCallbackFlow.getClass();
        return ConflatedCallbackFlow.conflatedCallbackFlow(statusBarStateControllerExtKt$expansionChanges$1);
    }
}
