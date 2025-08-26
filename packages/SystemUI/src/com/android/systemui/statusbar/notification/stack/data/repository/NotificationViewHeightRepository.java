package com.android.systemui.statusbar.notification.stack.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class NotificationViewHeightRepository {
    public final StateFlowImpl isCurrentGestureInGuts;
    public final StateFlowImpl isCurrentGestureOverscroll;
    public final StateFlowImpl syntheticScroll = StateFlowKt.MutableStateFlow(Float.valueOf(0.0f));

    public NotificationViewHeightRepository() {
        Boolean bool = Boolean.FALSE;
        this.isCurrentGestureOverscroll = StateFlowKt.MutableStateFlow(bool);
        this.isCurrentGestureInGuts = StateFlowKt.MutableStateFlow(bool);
    }
}
