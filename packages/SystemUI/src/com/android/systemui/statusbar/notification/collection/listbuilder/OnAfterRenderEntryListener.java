package com.android.systemui.statusbar.notification.collection.listbuilder;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.NotifRowController;

public interface OnAfterRenderEntryListener {
    void onAfterRenderEntry(NotificationEntry notificationEntry, NotifRowController notifRowController);
}
