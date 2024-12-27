package com.android.systemui.keyguard.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class KeyguardOcclusionRepository {
    public final StateFlowImpl showWhenLockedActivityInfo = StateFlowKt.MutableStateFlow(new ShowWhenLockedActivityInfo(false, null, 2, null));
}
