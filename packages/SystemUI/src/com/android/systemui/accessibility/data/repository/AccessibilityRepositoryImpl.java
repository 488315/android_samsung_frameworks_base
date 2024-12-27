package com.android.systemui.accessibility.data.repository;

import android.view.accessibility.AccessibilityManager;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

public final class AccessibilityRepositoryImpl implements AccessibilityRepository {
    public final Flow isEnabled;
    public final Flow isTouchExplorationEnabled;

    public AccessibilityRepositoryImpl(AccessibilityManager accessibilityManager) {
        this.isTouchExplorationEnabled = FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(new AccessibilityRepositoryImpl$isTouchExplorationEnabled$1(accessibilityManager, null)));
        this.isEnabled = FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(new AccessibilityRepositoryImpl$isEnabled$1(accessibilityManager, null)));
    }
}
