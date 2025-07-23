package com.android.systemui.util;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationSAUtil {
    public static void sendCancelLog(String str, NotificationEntry notificationEntry) {
        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, str, "type", (notificationEntry.isOngoingActivity() && notificationEntry.isPromotedState()) ? "live notification" : notificationEntry.mRanking.isConversation() ? SystemUIAnalytics.QPNE_VID_CONVERSATION : SystemUIAnalytics.QPNE_VID_NORMAL, SystemUIAnalytics.QPNE_VID_PRIORITY, (notificationEntry.mRanking.getChannel() == null || !notificationEntry.mRanking.getChannel().isImportantConversation()) ? notificationEntry.mRanking.getImportance() >= 3 ? SystemUIAnalytics.QPNE_VID_ALERT : notificationEntry.mRanking.getImportance() < 3 ? SystemUIAnalytics.QPNE_VID_SILENT : "" : SystemUIAnalytics.QPNE_VID_PRIORITY, "information", notificationEntry.mSbn.getPackageName() + " ; " + notificationEntry.mSbn.getId() + " ; " + notificationEntry.mRanking.getChannel().getId() + " ; " + (notificationEntry.mSbn.getNotification().category == null ? "null" : notificationEntry.mSbn.getNotification().category) + " ; " + notificationEntry.mRanking.getImportance());
    }

    public static void sendOALog(String str, NotificationEntry notificationEntry) {
        String packageName = notificationEntry.mSbn.getPackageName();
        if (notificationEntry.mSbn.getNotification().isGroupSummary()) {
            return;
        }
        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), str, SystemUIAnalytics.QPNE_KEY_APP, packageName);
    }

    public static void sendTypeLog(String str, NotificationEntry notificationEntry) {
        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, str, "type", (notificationEntry.isOngoingActivity() && notificationEntry.isPromotedState()) ? "live notification" : notificationEntry.mRanking.isConversation() ? SystemUIAnalytics.QPNE_VID_CONVERSATION : SystemUIAnalytics.QPNE_VID_NORMAL, SystemUIAnalytics.QPNE_KEY_APP, notificationEntry.mSbn.getPackageName());
    }
}
