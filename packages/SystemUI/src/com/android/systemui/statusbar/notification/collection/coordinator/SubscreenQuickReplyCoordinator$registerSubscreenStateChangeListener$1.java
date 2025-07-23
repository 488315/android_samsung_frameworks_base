package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.SubscreenQuickReplyCoordinator;
import com.android.systemui.statusbar.notification.collection.notifcollection.InternalNotifUpdater;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
        int intValue;
        Notification notification3;
        Notification notification4;
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("   subscreen quick reply - ", notificationEntry != null ? notificationEntry.mKey : null, "SubscreenQuickReplyCoordinator");
        if (notificationEntry != null && (statusBarNotification = notificationEntry.mSbn) != null && (notification2 = statusBarNotification.getNotification()) != null) {
            StatusBarNotification statusBarNotification2 = notificationEntry.mSbn;
            if (statusBarNotification2 == null || (notification4 = statusBarNotification2.getNotification()) == null) {
                StatusBarNotification statusBarNotification3 = notificationEntry.mSbn;
                Integer valueOf = (statusBarNotification3 == null || (notification3 = statusBarNotification3.getNotification()) == null) ? null : Integer.valueOf(notification3.flags);
                valueOf.getClass();
                intValue = valueOf.intValue();
            } else {
                intValue = notification4.flags | 8;
            }
            notification2.flags = intValue;
        }
        internalNotifUpdater = this.this$0.mNotifUpdater;
        if (internalNotifUpdater == null) {
            internalNotifUpdater = null;
        }
        ((NotifCollection$$ExternalSyntheticLambda0) internalNotifUpdater).onInternalNotificationUpdate("Extending lifetime of notification with subscreen quick reply", notificationEntry != null ? notificationEntry.mSbn : null);
    }
}
