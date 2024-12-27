package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.init.NotificationsController;

public final /* synthetic */ class NotificationStackScrollLayoutController$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NotificationStackScrollLayoutController$$ExternalSyntheticLambda8(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = (NotificationStackScrollLayoutController) obj;
                notificationStackScrollLayoutController.getClass();
                notificationStackScrollLayoutController.mView.postDelayed(new NotificationStackScrollLayoutController$$ExternalSyntheticLambda8(notificationStackScrollLayoutController, 2), 0L);
                break;
            case 1:
                ((NotificationStackScrollLayoutController) obj).mShadeController.animateCollapseShade(0);
                break;
            case 2:
                ((NotificationStackScrollLayoutController) obj).mShadeController.animateCollapseShade(0);
                break;
            case 3:
                ((NotificationStackScrollLayoutController) obj).updateFooter();
                break;
            case 4:
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = (NotificationStackScrollLayoutController) obj;
                notificationStackScrollLayoutController2.getClass();
                notificationStackScrollLayoutController2.mView.postDelayed(new NotificationStackScrollLayoutController$$ExternalSyntheticLambda8(notificationStackScrollLayoutController2, 1), 200L);
                break;
            default:
                ((NotificationsController) obj).resetUserExpandedStates();
                break;
        }
    }
}
