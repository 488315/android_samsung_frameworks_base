package com.android.systemui.shade.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class ShadeAnimationRepository {
    public final StateFlowImpl isLaunchingActivity = StateFlowKt.MutableStateFlow(Boolean.FALSE);
}
