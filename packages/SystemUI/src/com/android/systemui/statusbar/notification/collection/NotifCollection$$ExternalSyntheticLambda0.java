package com.android.systemui.statusbar.notification.collection;

import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.notifcollection.InternalNotifUpdater;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
