package com.android.systemui.statusbar.notification.stack.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationPlaceholderRepository {
    public final StateFlowImpl alphaForBrightnessMirror = StateFlowKt.MutableStateFlow(Float.valueOf(1.0f));
    public final StateFlowImpl shadeScrimBounds = StateFlowKt.MutableStateFlow(null);
    public final StateFlowImpl constrainedAvailableSpace = StateFlowKt.MutableStateFlow(0);
    public final StateFlowImpl scrolledToTop = StateFlowKt.MutableStateFlow(Boolean.TRUE);
}
