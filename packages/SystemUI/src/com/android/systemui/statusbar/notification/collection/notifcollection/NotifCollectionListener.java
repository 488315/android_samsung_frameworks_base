package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.app.NotificationChannel;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface NotifCollectionListener {
    default void onEntryUpdated(NotificationEntry notificationEntry) {
    }

    default void onEntryUpdated(NotificationEntry notificationEntry, boolean z) {
        onEntryUpdated(notificationEntry);
    }

    default void onRankingApplied() {
    }

    default void onEntryAdded(NotificationEntry notificationEntry) {
    }

    default void onEntryCleanUp(NotificationEntry notificationEntry) {
    }

    default void onEntryInit(NotificationEntry notificationEntry) {
    }

    default void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
    }

    default void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
    }

    default void onEntryRemoved(NotificationEntry notificationEntry, int i) {
    }

    default void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
    }
}
