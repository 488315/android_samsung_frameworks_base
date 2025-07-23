package com.android.systemui.dock;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class DockManagerExtensionsKt {
    public static final Flow retrieveIsDocked(DockManager dockManager) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DockManagerExtensionsKt$retrieveIsDocked$1 dockManagerExtensionsKt$retrieveIsDocked$1 = new DockManagerExtensionsKt$retrieveIsDocked$1(dockManager, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(dockManagerExtensionsKt$retrieveIsDocked$1);
    }
}
