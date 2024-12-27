package com.android.systemui.keyguard.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class InWindowLauncherUnlockAnimationRepository {
    public final StateFlowImpl launcherActivityClass;
    public final StateFlowImpl launcherSmartspaceState;
    public final StateFlowImpl startedUnlockAnimation = StateFlowKt.MutableStateFlow(Boolean.FALSE);

    public InWindowLauncherUnlockAnimationRepository() {
        StateFlowKt.MutableStateFlow(null);
        this.launcherActivityClass = StateFlowKt.MutableStateFlow(null);
        this.launcherSmartspaceState = StateFlowKt.MutableStateFlow(null);
    }
}
