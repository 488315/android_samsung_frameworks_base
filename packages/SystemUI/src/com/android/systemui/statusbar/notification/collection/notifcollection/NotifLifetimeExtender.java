package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

public interface NotifLifetimeExtender {

    public interface OnEndLifetimeExtensionCallback {
    }

    void cancelLifetimeExtension(NotificationEntry notificationEntry);

    String getName();

    boolean maybeExtendLifetime(NotificationEntry notificationEntry, int i);

    void setCallback(OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback);
}
