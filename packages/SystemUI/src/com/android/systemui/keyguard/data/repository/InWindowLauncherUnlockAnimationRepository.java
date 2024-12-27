package com.android.systemui.keyguard.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

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
