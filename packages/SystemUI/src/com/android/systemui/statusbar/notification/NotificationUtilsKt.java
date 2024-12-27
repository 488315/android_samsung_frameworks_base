package com.android.systemui.statusbar.notification;

import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.ListEntry;

public abstract class NotificationUtilsKt {
    public static final String getLogKey(ListEntry listEntry) {
        if (listEntry != null) {
            return NotificationUtils.logKey(listEntry);
        }
        return null;
    }

    public static final String getLogKey(StatusBarNotification statusBarNotification) {
        String key;
        if (statusBarNotification == null || (key = statusBarNotification.getKey()) == null) {
            return null;
        }
        return key.replace("\n", "");
    }
}
