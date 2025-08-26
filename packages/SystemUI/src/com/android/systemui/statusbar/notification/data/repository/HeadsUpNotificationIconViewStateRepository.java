package com.android.systemui.statusbar.notification.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class HeadsUpNotificationIconViewStateRepository {
    public final StateFlowImpl isolatedNotification = StateFlowKt.MutableStateFlow(null);
    public final StateFlowImpl isolatedIconLocation = StateFlowKt.MutableStateFlow(null);
}
