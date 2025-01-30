package com.android.systemui.statusbar.notification.collection;

import android.service.notification.StatusBarNotification;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotifCollection$$ExternalSyntheticLambda4 {
    public final /* synthetic */ NotifCollection f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ NotifCollection$$ExternalSyntheticLambda4(NotifCollection notifCollection, String str) {
        this.f$0 = notifCollection;
        this.f$1 = str;
    }

    public final void onInternalNotificationUpdate(String str, StatusBarNotification statusBarNotification) {
        NotifCollection notifCollection = this.f$0;
        notifCollection.getClass();
        notifCollection.mMainHandler.post(new NotifCollection$$ExternalSyntheticLambda6(statusBarNotification, notifCollection, this.f$1, str));
    }
}
