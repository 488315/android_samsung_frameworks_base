package com.android.systemui.statusbar.notification.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class NotificationLaunchAnimationRepository {
    public final StateFlowImpl isLaunchAnimationRunning = StateFlowKt.MutableStateFlow(Boolean.FALSE);
}
