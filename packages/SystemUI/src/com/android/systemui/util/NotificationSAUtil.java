package com.android.systemui.util;

import android.content.Context;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class NotificationSAUtil {
    public static void sendCancelLog(String str, NotificationEntry notificationEntry) {
        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, str, "type", (notificationEntry.isOngoingAcitivty() && notificationEntry.isPromotedState()) ? "live notification" : notificationEntry.mRanking.isConversation() ? SystemUIAnalytics.QPNE_VID_CONVERSATION : SystemUIAnalytics.QPNE_VID_NORMAL, SystemUIAnalytics.QPNE_VID_PRIORITY, (notificationEntry.mRanking.getChannel() == null || !notificationEntry.mRanking.getChannel().isImportantConversation()) ? notificationEntry.mRanking.getImportance() >= 3 ? SystemUIAnalytics.QPNE_VID_ALERT : notificationEntry.mRanking.getImportance() < 3 ? SystemUIAnalytics.QPNE_VID_SILENT : "" : SystemUIAnalytics.QPNE_VID_PRIORITY, "information", notificationEntry.mSbn.getPackageName() + " ; " + notificationEntry.mSbn.getId() + " ; " + notificationEntry.mRanking.getChannel().getId() + " ; " + (notificationEntry.mSbn.getNotification().category == null ? "null" : notificationEntry.mSbn.getNotification().category) + " ; " + notificationEntry.mRanking.getImportance());
    }

    public static void sendOALog(Context context, String str, NotificationEntry notificationEntry) {
        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), str, "information", notificationEntry.mSbn.getPackageName() + " ; " + notificationEntry.mSbn.getNotification().loadHeaderAppName(context));
    }

    public static void sendTypeLog(String str, NotificationEntry notificationEntry) {
        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, str, "type", (notificationEntry.isOngoingAcitivty() && notificationEntry.isPromotedState()) ? "live notification" : notificationEntry.mRanking.isConversation() ? SystemUIAnalytics.QPNE_VID_CONVERSATION : SystemUIAnalytics.QPNE_VID_NORMAL, SystemUIAnalytics.QPNE_KEY_APP, notificationEntry.mSbn.getPackageName());
    }
}
