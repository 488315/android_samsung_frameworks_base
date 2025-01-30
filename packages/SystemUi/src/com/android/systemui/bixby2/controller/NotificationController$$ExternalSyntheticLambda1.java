package com.android.systemui.bixby2.controller;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class NotificationController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationController f$0;

    public /* synthetic */ NotificationController$$ExternalSyntheticLambda1(NotificationController notificationController, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$deleteAllNotifications$1();
                break;
            default:
                this.f$0.lambda$deleteAllNotificationsDismissable$2();
                break;
        }
    }
}
