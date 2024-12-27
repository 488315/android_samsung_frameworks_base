package com.android.systemui.statusbar.notification.logging;

import android.service.notification.StatusBarNotification;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.logging.nano.Notifications$Notification;
import com.android.systemui.statusbar.notification.logging.nano.Notifications$NotificationList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface NotificationPanelLogger {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public enum NotificationPanelEvent implements UiEventLogger.UiEventEnum {
        NOTIFICATION_PANEL_OPEN_STATUS_BAR(200),
        NOTIFICATION_PANEL_OPEN_LOCKSCREEN(201),
        NOTIFICATION_DRAG(1226);

        private final int mId;

        NotificationPanelEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    static Notifications$NotificationList toNotificationProto(List list) {
        Notifications$NotificationList notifications$NotificationList = new Notifications$NotificationList();
        if (list == null) {
            return notifications$NotificationList;
        }
        Notifications$Notification[] notifications$NotificationArr = new Notifications$Notification[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            NotificationEntry notificationEntry = (NotificationEntry) it.next();
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            if (statusBarNotification != null) {
                Notifications$Notification notifications$Notification = new Notifications$Notification();
                notifications$Notification.uid = statusBarNotification.getUid();
                notifications$Notification.packageName = statusBarNotification.getPackageName();
                if (statusBarNotification.getInstanceId() != null) {
                    notifications$Notification.instanceId = statusBarNotification.getInstanceId().getId();
                }
                if (statusBarNotification.getNotification() != null) {
                    notifications$Notification.isGroupSummary = statusBarNotification.getNotification().isGroupSummary();
                }
                int i2 = notificationEntry.mBucket;
                int i3 = 1;
                if (i2 == 1) {
                    i3 = 2;
                } else if (i2 != 7) {
                    if (i2 != 8) {
                        switch (i2) {
                            case 10:
                            case 11:
                                i3 = 3;
                                break;
                            case 12:
                                i3 = 4;
                                break;
                            case 13:
                                i3 = 5;
                                break;
                            default:
                                i3 = 0;
                                break;
                        }
                    } else {
                        i3 = 6;
                    }
                }
                notifications$Notification.section = i3;
                notifications$NotificationArr[i] = notifications$Notification;
            }
            i++;
        }
        notifications$NotificationList.notifications = notifications$NotificationArr;
        return notifications$NotificationList;
    }
}
