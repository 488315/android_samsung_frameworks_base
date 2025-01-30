package com.android.systemui.dock;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class DockManagerExtensionsKt {
    public static final Flow retrieveIsDocked(DockManager dockManager) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DockManagerExtensionsKt$retrieveIsDocked$1 dockManagerExtensionsKt$retrieveIsDocked$1 = new DockManagerExtensionsKt$retrieveIsDocked$1(dockManager, null);
        conflatedCallbackFlow.getClass();
        return ConflatedCallbackFlow.conflatedCallbackFlow(dockManagerExtensionsKt$retrieveIsDocked$1);
    }
}
