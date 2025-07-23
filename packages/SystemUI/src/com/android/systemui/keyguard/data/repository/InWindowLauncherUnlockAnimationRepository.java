package com.android.systemui.keyguard.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
