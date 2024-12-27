package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

public interface VisibilityLocationProvider {
    boolean isInVisibleLocation(NotificationEntry notificationEntry);
}
