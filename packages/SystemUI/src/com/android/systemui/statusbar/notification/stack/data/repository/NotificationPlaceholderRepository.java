package com.android.systemui.statusbar.notification.stack.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class NotificationPlaceholderRepository {
    public final StateFlowImpl alphaForBrightnessMirror = StateFlowKt.MutableStateFlow(Float.valueOf(1.0f));
    public final StateFlowImpl shadeScrimBounds = StateFlowKt.MutableStateFlow(null);
    public final StateFlowImpl constrainedAvailableSpace = StateFlowKt.MutableStateFlow(0);
    public final StateFlowImpl scrolledToTop = StateFlowKt.MutableStateFlow(Boolean.TRUE);
}
