package com.android.systemui.plugins;

import android.app.NotificationChannel;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(action = NotificationListenerController.ACTION, version = 1)
@DependsOn(target = NotificationProvider.class)
/* loaded from: classes2.dex */
public interface NotificationListenerController extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_NOTIFICATION_ASSISTANT";
    public static final int VERSION = 1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @ProvidesInterface(version = 1)
    public interface NotificationProvider {
        public static final int VERSION = 1;

        void addNotification(StatusBarNotification statusBarNotification);

        StatusBarNotification[] getActiveNotifications();

        NotificationListenerService.RankingMap getRankingMap();

        void removeNotification(StatusBarNotification statusBarNotification);

        void updateRanking();
    }

    void onListenerConnected(NotificationProvider notificationProvider);

    default boolean onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        return false;
    }

    default boolean onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        return false;
    }

    default boolean onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        return false;
    }

    default StatusBarNotification[] getActiveNotifications(StatusBarNotification[] statusBarNotificationArr) {
        return statusBarNotificationArr;
    }

    default NotificationListenerService.RankingMap getCurrentRanking(NotificationListenerService.RankingMap rankingMap) {
        return rankingMap;
    }
}
