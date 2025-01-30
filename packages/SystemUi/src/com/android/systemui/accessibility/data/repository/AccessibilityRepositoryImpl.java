package com.android.systemui.accessibility.data.repository;

import android.view.accessibility.AccessibilityManager;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AccessibilityRepositoryImpl implements AccessibilityRepository {
    public final Flow isTouchExplorationEnabled;

    public AccessibilityRepositoryImpl(AccessibilityManager accessibilityManager) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        AccessibilityRepositoryImpl$isTouchExplorationEnabled$1 accessibilityRepositoryImpl$isTouchExplorationEnabled$1 = new AccessibilityRepositoryImpl$isTouchExplorationEnabled$1(accessibilityManager, null);
        conflatedCallbackFlow.getClass();
        this.isTouchExplorationEnabled = FlowKt.distinctUntilChanged(ConflatedCallbackFlow.conflatedCallbackFlow(accessibilityRepositoryImpl$isTouchExplorationEnabled$1));
    }
}
