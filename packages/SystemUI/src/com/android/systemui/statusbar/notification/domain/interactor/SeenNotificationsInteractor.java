package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationListRepository;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SeenNotificationsInteractor {
    public final StateFlowImpl hasFilteredOutSeenNotifications;
    public final ActiveNotificationListRepository notificationListRepository;

    public SeenNotificationsInteractor(ActiveNotificationListRepository activeNotificationListRepository) {
        this.notificationListRepository = activeNotificationListRepository;
        this.hasFilteredOutSeenNotifications = activeNotificationListRepository.hasFilteredOutSeenNotifications;
    }
}
