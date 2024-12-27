package com.android.systemui.statusbar.notification.data.repository;

import com.android.systemui.statusbar.notification.collection.render.NotifStats;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class ActiveNotificationListRepository {
    public final StateFlowImpl activeNotifications = StateFlowKt.MutableStateFlow(new ActiveNotificationsStore(null, null, null, null, 15, null));
    public final StateFlowImpl hasFilteredOutSeenNotifications = StateFlowKt.MutableStateFlow(Boolean.FALSE);
    public final StateFlowImpl notifStats;
    public final StateFlowImpl topOngoingNotificationKey;
    public final StateFlowImpl topUnseenNotificationKey;

    public ActiveNotificationListRepository() {
        NotifStats.Companion.getClass();
        this.notifStats = StateFlowKt.MutableStateFlow(NotifStats.empty);
        StateFlowKt.MutableStateFlow(null);
        StateFlowKt.MutableStateFlow(null);
    }
}
