package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.SubscreenQuickReplyCoordinator;
import com.android.systemui.statusbar.notification.collection.notifcollection.InternalNotifUpdater;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SubscreenQuickReplyCoordinator$registerSubscreenStateChangeListener$1 {
    final /* synthetic */ SubscreenQuickReplyCoordinator this$0;

    public SubscreenQuickReplyCoordinator$registerSubscreenStateChangeListener$1(SubscreenQuickReplyCoordinator subscreenQuickReplyCoordinator) {
        this.this$0 = subscreenQuickReplyCoordinator;
    }

    public void onHideDetail(String str) {
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("   end extension - ", str, "SubscreenQuickReplyCoordinator");
        SubscreenQuickReplyCoordinator.SubscreenQuickReplyExtender mQuickReplyExtender = this.this$0.getMQuickReplyExtender();
        if (str == null) {
            str = "";
        }
        mQuickReplyExtender.endLifetimeExtension(str);
    }

    public void onReply(NotificationEntry notificationEntry) {
        InternalNotifUpdater internalNotifUpdater;
        StatusBarNotification statusBarNotification;
        Notification notification2;
        StatusBarNotification statusBarNotification2;
        Notification notification3;
        StatusBarNotification statusBarNotification3;
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("   subscreen quick reply - ", notificationEntry != null ? notificationEntry.mKey : null, "SubscreenQuickReplyCoordinator");
        Notification notification4 = (notificationEntry == null || (statusBarNotification3 = notificationEntry.mSbn) == null) ? null : statusBarNotification3.getNotification();
        if (notification4 != null) {
            notification4.flags = ((notificationEntry == null || (statusBarNotification2 = notificationEntry.mSbn) == null || (notification3 = statusBarNotification2.getNotification()) == null) ? (notificationEntry == null || (statusBarNotification = notificationEntry.mSbn) == null || (notification2 = statusBarNotification.getNotification()) == null) ? null : Integer.valueOf(notification2.flags) : Integer.valueOf(notification3.flags | 8)).intValue();
        }
        internalNotifUpdater = this.this$0.mNotifUpdater;
        if (internalNotifUpdater == null) {
            internalNotifUpdater = null;
        }
        ((NotifCollection$$ExternalSyntheticLambda0) internalNotifUpdater).onInternalNotificationUpdate("Extending lifetime of notification with subscreen quick reply", notificationEntry != null ? notificationEntry.mSbn : null);
    }
}
