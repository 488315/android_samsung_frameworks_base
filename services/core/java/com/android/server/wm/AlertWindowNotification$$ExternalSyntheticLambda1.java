package com.android.server.wm;

import android.app.NotificationManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AlertWindowNotification$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ AlertWindowNotification f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ AlertWindowNotification$$ExternalSyntheticLambda1(
            AlertWindowNotification alertWindowNotification, boolean z) {
        this.f$0 = alertWindowNotification;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AlertWindowNotification alertWindowNotification = this.f$0;
        boolean z = this.f$1;
        if (alertWindowNotification.mPosted) {
            alertWindowNotification.mPosted = false;
            NotificationManager notificationManager = alertWindowNotification.mNotificationManager;
            String str = alertWindowNotification.mNotificationTag;
            notificationManager.cancel(str, 0);
            if (z) {
                alertWindowNotification.mNotificationManager.deleteNotificationChannel(str);
            }
        }
    }
}
