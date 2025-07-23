package com.android.systemui.statusbar.notification.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HeadsUpNotificationIconViewStateRepository {
    public final StateFlowImpl isolatedNotification = StateFlowKt.MutableStateFlow(null);
    public final StateFlowImpl isolatedIconLocation = StateFlowKt.MutableStateFlow(null);
}
