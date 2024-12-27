package com.android.systemui.statusbar.notification;

import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.ListEntry;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
