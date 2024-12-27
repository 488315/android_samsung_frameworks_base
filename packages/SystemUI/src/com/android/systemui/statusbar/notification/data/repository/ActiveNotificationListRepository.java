package com.android.systemui.statusbar.notification.data.repository;

import com.android.systemui.statusbar.notification.collection.render.NotifStats;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
