package com.android.server.notification;

import android.app.Notification;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationManagerService$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ NotificationManagerService f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ Notification f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ String f$4;
    public final /* synthetic */ int f$5;

    public /* synthetic */ NotificationManagerService$$ExternalSyntheticLambda8(NotificationManagerService notificationManagerService, boolean z, Notification notification, int i, String str, int i2) {
        this.f$0 = notificationManagerService;
        this.f$1 = z;
        this.f$2 = notification;
        this.f$3 = i;
        this.f$4 = str;
        this.f$5 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NotificationManagerService notificationManagerService = this.f$0;
        notificationManagerService.mAmi.onForegroundServiceNotificationUpdate(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5);
    }
}
