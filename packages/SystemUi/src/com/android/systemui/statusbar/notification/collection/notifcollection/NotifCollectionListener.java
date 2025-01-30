package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.app.NotificationChannel;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface NotifCollectionListener {
    default void onEntryUpdated(NotificationEntry notificationEntry) {
    }

    default void onEntryUpdated(NotificationEntry notificationEntry, boolean z) {
        onEntryUpdated(notificationEntry);
    }

    default void onEntryAdded(NotificationEntry notificationEntry) {
    }

    default void onEntryCleanUp(NotificationEntry notificationEntry) {
    }

    default void onEntryInit(NotificationEntry notificationEntry) {
    }

    default void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
    }

    default void onRankingApplied() {
    }

    default void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
    }

    default void onEntryRemoved(NotificationEntry notificationEntry, int i) {
    }

    default void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
    }
}
