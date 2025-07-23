package com.android.systemui.bixby2.controller;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int i = this.$r8$classId;
        NotificationController notificationController = this.f$0;
        switch (i) {
            case 0:
                notificationController.lambda$deleteAllNotifications$1();
                break;
            default:
                notificationController.lambda$deleteAllNotificationsDismissable$2();
                break;
        }
    }
}
