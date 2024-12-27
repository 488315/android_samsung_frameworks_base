package com.android.systemui.statusbar.notification.collection;

import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.notifcollection.InternalNotifUpdater;

public final /* synthetic */ class NotifCollection$$ExternalSyntheticLambda0 implements InternalNotifUpdater {
    public final /* synthetic */ NotifCollection f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ NotifCollection$$ExternalSyntheticLambda0(NotifCollection notifCollection, String str) {
        this.f$0 = notifCollection;
        this.f$1 = str;
    }

    public final void onInternalNotificationUpdate(String str, StatusBarNotification statusBarNotification) {
        NotifCollection notifCollection = this.f$0;
        notifCollection.getClass();
        notifCollection.mMainHandler.post(new NotifCollection$$ExternalSyntheticLambda9(notifCollection, statusBarNotification, this.f$1, str));
    }
}
