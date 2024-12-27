package com.android.systemui.statusbar.notification.stack.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class NotificationViewHeightRepository {
    public final StateFlowImpl syntheticScroll = StateFlowKt.MutableStateFlow(Float.valueOf(0.0f));
    public final StateFlowImpl isCurrentGestureOverscroll = StateFlowKt.MutableStateFlow(Boolean.FALSE);
}
