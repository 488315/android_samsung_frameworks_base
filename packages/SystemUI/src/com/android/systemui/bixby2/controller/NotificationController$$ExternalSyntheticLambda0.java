package com.android.systemui.bixby2.controller;

public final /* synthetic */ class NotificationController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationController f$0;

    public /* synthetic */ NotificationController$$ExternalSyntheticLambda0(NotificationController notificationController, int i) {
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
