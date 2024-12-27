package com.android.systemui.plugins;

import android.app.NotificationChannel;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@ProvidesInterface(action = NotificationListenerController.ACTION, version = 1)
@DependsOn(target = NotificationProvider.class)
public interface NotificationListenerController extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_NOTIFICATION_ASSISTANT";
    public static final int VERSION = 1;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
