package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;

public final /* synthetic */ class DozeServiceHost$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ DozeServiceHost f$0;
    public final /* synthetic */ NotificationEntry f$1;

    public /* synthetic */ DozeServiceHost$$ExternalSyntheticLambda2(DozeServiceHost dozeServiceHost, NotificationEntry notificationEntry) {
        this.f$0 = dozeServiceHost;
        this.f$1 = notificationEntry;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DozeServiceHost dozeServiceHost = this.f$0;
        NotificationEntry notificationEntry = this.f$1;
        dozeServiceHost.getClass();
        int i = NotificationIconContainerRefactor.$r8$clinit;
        notificationEntry.mPulseSupressed = true;
        dozeServiceHost.mNotificationIconAreaController.updateAodNotificationIcons();
    }
}
