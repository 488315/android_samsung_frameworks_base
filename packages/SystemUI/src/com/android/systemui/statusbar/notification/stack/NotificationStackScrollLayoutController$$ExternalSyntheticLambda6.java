package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationStackScrollLayoutController$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NotificationStackScrollLayoutController$$ExternalSyntheticLambda6(Object obj, int i) {
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
                NotificationStackScrollLayoutController.AnonymousClass4 anonymousClass4 = NotificationStackScrollLayoutController.HIDE_ALPHA_PROPERTY;
                notificationStackScrollLayoutController.mView.postDelayed(new NotificationStackScrollLayoutController$$ExternalSyntheticLambda6(notificationStackScrollLayoutController, 2), 0L);
                break;
            case 1:
                ((NotificationStackScrollLayoutController) obj).mShadeController.animateCollapseShade(0);
                break;
            case 2:
                ((NotificationStackScrollLayoutController) obj).mShadeController.animateCollapseShade(0);
                break;
            case 3:
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = (NotificationStackScrollLayoutController) obj;
                NotificationStackScrollLayoutController.AnonymousClass4 anonymousClass42 = NotificationStackScrollLayoutController.HIDE_ALPHA_PROPERTY;
                notificationStackScrollLayoutController2.mView.postDelayed(new NotificationStackScrollLayoutController$$ExternalSyntheticLambda6(notificationStackScrollLayoutController2, 1), 200L);
                break;
            default:
                ((NotificationsController) obj).resetUserExpandedStates();
                break;
        }
    }
}
