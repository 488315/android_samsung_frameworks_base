package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DozeServiceHost$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ DozeServiceHost f$0;
    public final /* synthetic */ NotificationEntry f$1;

    public /* synthetic */ DozeServiceHost$$ExternalSyntheticLambda3(DozeServiceHost dozeServiceHost, NotificationEntry notificationEntry) {
        this.f$0 = dozeServiceHost;
        this.f$1 = notificationEntry;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DozeServiceHost dozeServiceHost = this.f$0;
        NotificationEntry notificationEntry = this.f$1;
        dozeServiceHost.getClass();
        int i = NotificationIconContainerRefactor.$r8$clinit;
        notificationEntry.getClass();
        dozeServiceHost.mNotificationIconAreaController.updateAodNotificationIcons();
    }
}
