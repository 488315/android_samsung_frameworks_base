package com.android.systemui.statusbar.notification.stack.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationViewHeightRepository {
    public final StateFlowImpl syntheticScroll = StateFlowKt.MutableStateFlow(Float.valueOf(0.0f));
    public final StateFlowImpl isCurrentGestureOverscroll = StateFlowKt.MutableStateFlow(Boolean.FALSE);
}
