package com.android.systemui.statusbar.notification.init;

import android.service.notification.StatusBarNotification;
import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationsControllerStub implements NotificationsController {
    public final NotificationListener notificationListener;

    public NotificationsControllerStub(NotificationListener notificationListener) {
        this.notificationListener = notificationListener;
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public final int getActiveNotificationsCount() {
        return 0;
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public final void initialize(NotificationPresenter notificationPresenter, NotificationListContainer notificationListContainer, NotificationStackScrollLayoutController.NotifStackControllerImpl notifStackControllerImpl, NotificationActivityStarter notificationActivityStarter) {
        this.notificationListener.registerAsSystemService();
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public final void resetUserExpandedStates() {
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public final void setNotificationSnoozed(StatusBarNotification statusBarNotification, NotificationSwipeActionHelper.SnoozeOption snoozeOption) {
    }
}
