package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.stack.NotificationListContainer;

public interface ShadeViewManagerFactory {
    ShadeViewManager create(NotificationListContainer notificationListContainer, NotifStackController notifStackController);
}
