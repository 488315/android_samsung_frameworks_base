package com.android.systemui.statusbar.notification.init;

import android.service.notification.StatusBarNotification;
import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CentralSurfaces;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    public final void initialize(CentralSurfaces centralSurfaces, NotificationPresenter notificationPresenter, NotificationListContainer notificationListContainer, NotificationStackScrollLayoutController.NotifStackControllerImpl notifStackControllerImpl, NotificationActivityStarter notificationActivityStarter, NotificationRowBinderImpl.BindRowCallback bindRowCallback) {
        this.notificationListener.registerAsSystemService();
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public final void resetUserExpandedStates() {
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public final void setNotificationSnoozed(StatusBarNotification statusBarNotification, NotificationSwipeActionHelper.SnoozeOption snoozeOption) {
    }
}
