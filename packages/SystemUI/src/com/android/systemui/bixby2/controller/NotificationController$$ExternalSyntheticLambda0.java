package com.android.systemui.bixby2.controller;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
