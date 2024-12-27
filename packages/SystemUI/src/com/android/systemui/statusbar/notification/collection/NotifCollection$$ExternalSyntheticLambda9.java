package com.android.systemui.statusbar.notification.collection;

import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import com.android.systemui.statusbar.notification.collection.notifcollection.BindEntryEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.EntryUpdatedEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger;
import com.android.systemui.util.Assert;
import java.util.ArrayDeque;

public final /* synthetic */ class NotifCollection$$ExternalSyntheticLambda9 implements Runnable {
    public final /* synthetic */ NotifCollection f$0;
    public final /* synthetic */ StatusBarNotification f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ String f$3;

    public /* synthetic */ NotifCollection$$ExternalSyntheticLambda9(NotifCollection notifCollection, StatusBarNotification statusBarNotification, String str, String str2) {
        this.f$0 = notifCollection;
        this.f$1 = statusBarNotification;
        this.f$2 = str;
        this.f$3 = str2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NotifCollection notifCollection = this.f$0;
        StatusBarNotification statusBarNotification = this.f$1;
        String str = this.f$2;
        String str2 = this.f$3;
        notifCollection.getClass();
        Assert.isMainThread();
        notifCollection.checkForReentrantCall();
        NotificationEntry notificationEntry = (NotificationEntry) ((ArrayMap) notifCollection.mNotificationSet).get(statusBarNotification.getKey());
        NotifCollectionLogger notifCollectionLogger = notifCollection.mLogger;
        if (notificationEntry == null) {
            notifCollectionLogger.logNotifInternalUpdateFailed(statusBarNotification, str, str2);
            return;
        }
        notifCollectionLogger.logNotifInternalUpdate(notificationEntry, str, str2);
        notificationEntry.setSbn(statusBarNotification);
        ((ArrayDeque) notifCollection.mEventQueue).add(new BindEntryEvent(notificationEntry, statusBarNotification));
        notifCollectionLogger.logNotifUpdated(notificationEntry);
        ((ArrayDeque) notifCollection.mEventQueue).add(new EntryUpdatedEvent(notificationEntry, false));
        notifCollection.dispatchEventsAndRebuildList("updateNotificationInternally");
    }
}
