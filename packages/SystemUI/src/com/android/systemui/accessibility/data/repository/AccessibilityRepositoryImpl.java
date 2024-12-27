package com.android.systemui.accessibility.data.repository;

import android.view.accessibility.AccessibilityManager;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class AccessibilityRepositoryImpl implements AccessibilityRepository {
    public final Flow isEnabled;
    public final Flow isTouchExplorationEnabled;

    public AccessibilityRepositoryImpl(AccessibilityManager accessibilityManager) {
        this.isTouchExplorationEnabled = FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(new AccessibilityRepositoryImpl$isTouchExplorationEnabled$1(accessibilityManager, null)));
        this.isEnabled = FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(new AccessibilityRepositoryImpl$isEnabled$1(accessibilityManager, null)));
    }
}
