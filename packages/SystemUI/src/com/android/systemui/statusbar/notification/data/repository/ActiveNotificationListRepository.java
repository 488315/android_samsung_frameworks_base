package com.android.systemui.statusbar.notification.data.repository;

import com.android.systemui.statusbar.notification.data.model.NotifStats;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ActiveNotificationListRepository {
    public final StateFlowImpl activeNotifications = StateFlowKt.MutableStateFlow(new ActiveNotificationsStore(null, null, null, null, 15, null));
    public final StateFlowImpl hasFilteredOutSeenNotifications = StateFlowKt.MutableStateFlow(Boolean.FALSE);
    public final StateFlowImpl notifStats;
    public final StateFlowImpl topOngoingNotificationKey;
    public final StateFlowImpl topUnseenNotificationKey;

    public ActiveNotificationListRepository() {
        NotifStats.Companion.getClass();
        this.notifStats = StateFlowKt.MutableStateFlow(NotifStats.empty);
        this.topOngoingNotificationKey = StateFlowKt.MutableStateFlow(null);
        this.topUnseenNotificationKey = StateFlowKt.MutableStateFlow(null);
    }
}
