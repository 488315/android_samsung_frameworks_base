package com.android.systemui.dock;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class DockManagerExtensionsKt {
    public static final Flow retrieveIsDocked(DockManager dockManager) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DockManagerExtensionsKt$retrieveIsDocked$1 dockManagerExtensionsKt$retrieveIsDocked$1 = new DockManagerExtensionsKt$retrieveIsDocked$1(dockManager, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(dockManagerExtensionsKt$retrieveIsDocked$1);
    }
}
