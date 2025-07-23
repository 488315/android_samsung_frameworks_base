package com.android.systemui.statusbar.notification.stack.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
