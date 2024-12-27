package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationListRepository;
import kotlinx.coroutines.flow.StateFlowImpl;

public final class SeenNotificationsInteractor {
    public final StateFlowImpl hasFilteredOutSeenNotifications;
    public final ActiveNotificationListRepository notificationListRepository;

    public SeenNotificationsInteractor(ActiveNotificationListRepository activeNotificationListRepository) {
        this.notificationListRepository = activeNotificationListRepository;
        this.hasFilteredOutSeenNotifications = activeNotificationListRepository.hasFilteredOutSeenNotifications;
    }
}
