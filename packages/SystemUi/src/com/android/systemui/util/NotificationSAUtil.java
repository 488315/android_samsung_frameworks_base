package com.android.systemui.util;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationSAUtil {
    public static void sendCancelLog(NotificationEntry notificationEntry, String str) {
        String str2;
        String str3 = notificationEntry.mSbn.getPackageName() + " ; " + notificationEntry.mSbn.getId() + " ; " + notificationEntry.getChannel().getId() + " ; " + (notificationEntry.mSbn.getNotification().category == null ? "null" : notificationEntry.mSbn.getNotification().category) + " ; " + notificationEntry.getImportance();
        if (notificationEntry.mRanking.isConversation()) {
            str2 = "conversation";
        } else {
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            str2 = expandableNotificationRow == null ? false : expandableNotificationRow.mEntry.mSbn.getNotification().isMediaNotification() ? "media" : "normal";
        }
        SystemUIAnalytics.sendEventCDLog("QPN001", str, "type", str2, "priority", (notificationEntry.getChannel() == null || !notificationEntry.getChannel().isImportantConversation()) ? notificationEntry.getImportance() >= 3 ? "alert" : notificationEntry.getImportance() < 3 ? "silent" : "" : "priority", "information", str3);
    }
}
