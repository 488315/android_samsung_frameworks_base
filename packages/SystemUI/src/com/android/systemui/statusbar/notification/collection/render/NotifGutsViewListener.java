package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotificationGuts;

public interface NotifGutsViewListener {
    void onGutsClose(NotificationEntry notificationEntry);

    void onGutsOpen(NotificationEntry notificationEntry, NotificationGuts notificationGuts);
}
