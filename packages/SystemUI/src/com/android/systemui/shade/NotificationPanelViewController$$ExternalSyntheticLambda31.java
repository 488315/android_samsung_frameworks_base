package com.android.systemui.shade;

import android.animation.ValueAnimator;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.Log;
import com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda31 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda31(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) obj;
                ValueAnimator valueAnimator = notificationPanelViewController.mQsController.mExpansionAnimator;
                if (valueAnimator != null) {
                    valueAnimator.end();
                }
                notificationPanelViewController.collapse(1.0f, false);
                break;
            case 1:
                NotificationPanelViewController notificationPanelViewController2 = (NotificationPanelViewController) obj;
                notificationPanelViewController2.fling(0.0f, notificationPanelViewController2.mNextCollapseSpeedUpFactor, false);
                break;
            case 2:
                ((NotificationPanelViewController) obj).mKeyguardBottomArea.setVisibility(8);
                break;
            case 3:
                NotificationPanelViewController notificationPanelViewController3 = (NotificationPanelViewController) obj;
                notificationPanelViewController3.getClass();
                NotificationsHeadsUpRefactor.assertInLegacyMode();
                notificationPanelViewController3.mHeadsUpAnimatingAway = false;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController3.mNotificationStackScrollLayoutController;
                notificationStackScrollLayoutController.getClass();
                NotificationsHeadsUpRefactor.assertInLegacyMode();
                notificationStackScrollLayoutController.mView.setHeadsUpAnimatingAway(false);
                notificationPanelViewController3.updateVisibility$6();
                notificationPanelViewController3.updateExpansionAndVisibility();
                break;
            case 4:
                NotificationPanelViewController notificationPanelViewController4 = (NotificationPanelViewController) obj;
                if (notificationPanelViewController4.mExpandedFraction != 0.0f) {
                    Log.d("KeyguardVisible", "makeExpandedInvisible is not called. fraction=" + notificationPanelViewController4.mExpandedFraction);
                    ((KeyguardFastBioUnlockController) Dependency.sDependency.getDependencyInner(KeyguardFastBioUnlockController.class)).reset();
                    break;
                } else {
                    notificationPanelViewController4.mView.post(notificationPanelViewController4.mHideExpandedRunnable);
                    break;
                }
            case 5:
                ((NotificationPanelViewController) obj).setListening$3(false);
                break;
            case 6:
                NotificationPanelView notificationPanelView = ((NotificationPanelViewController) obj).mView;
                notificationPanelView.getParent().invalidateChild(notificationPanelView, NotificationPanelViewController.M_DUMMY_DIRTY_RECT);
                break;
            case 7:
                ((NotificationPanelViewController) obj).updateVisibility$6();
                break;
            case 8:
                ((NotificationPanelViewController) obj).mLatencyTracker.onActionEnd(0);
                break;
            case 9:
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) ((NotificationPanelViewController) obj).mNotificationShadeWindowController;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState.forceWindowCollapsed = false;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                break;
            default:
                ((StatusBarKeyguardViewManager) obj).readyForKeyguardDone();
                break;
        }
    }
}
