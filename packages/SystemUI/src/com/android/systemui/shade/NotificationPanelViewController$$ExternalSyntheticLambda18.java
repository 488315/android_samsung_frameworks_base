package com.android.systemui.shade;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.android.systemui.Dependency;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.Log;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda18 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda18(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((NotificationPanelViewController) obj).notifyExpandingFinished();
                break;
            case 1:
                ((NotificationPanelViewController) obj).closeQsIfPossible();
                break;
            case 2:
                ((NotificationPanelViewController) obj).instantCollapse();
                break;
            case 3:
                ((NotificationPanelViewController) obj).updateResources$1();
                break;
            case 4:
                NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) obj;
                ValueAnimator valueAnimator = notificationPanelViewController.mQsController.mExpansionAnimator;
                if (valueAnimator != null) {
                    valueAnimator.end();
                }
                notificationPanelViewController.collapse(1.0f, false);
                break;
            case 5:
                NotificationPanelViewController notificationPanelViewController2 = (NotificationPanelViewController) obj;
                notificationPanelViewController2.fling(0.0f, notificationPanelViewController2.mNextCollapseSpeedUpFactor, false);
                break;
            case 6:
                ((NotificationPanelViewController) obj).mKeyguardSecBottomArea.setVisibility(8);
                break;
            case 7:
                NotificationPanelViewController notificationPanelViewController3 = (NotificationPanelViewController) obj;
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                notificationPanelViewController3.mHeadsUpAnimatingAway = false;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController3.mNotificationStackScrollLayoutController;
                notificationStackScrollLayoutController.getClass();
                int i2 = SceneContainerFlag.$r8$clinit;
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                notificationStackScrollLayoutController.mView.setHeadsUpAnimatingAway(false);
                notificationPanelViewController3.updateVisibility();
                notificationPanelViewController3.updateExpansionAndVisibility();
                break;
            case 8:
                NotificationPanelViewController notificationPanelViewController4 = (NotificationPanelViewController) obj;
                if (notificationPanelViewController4.mExpandedFraction != 0.0f) {
                    Log.d("KeyguardVisible", "makeExpandedInvisible is not called. fraction=" + notificationPanelViewController4.mExpandedFraction);
                    ((KeyguardFastBioUnlockController) Dependency.sDependency.getDependencyInner(KeyguardFastBioUnlockController.class)).reset();
                    break;
                } else {
                    notificationPanelViewController4.mView.post(notificationPanelViewController4.mHideExpandedRunnable);
                    break;
                }
            case 9:
                Rect rect2 = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                ((NotificationPanelViewController) obj).setListening$1(false);
                break;
            case 10:
                NotificationPanelView notificationPanelView = ((NotificationPanelViewController) obj).mView;
                notificationPanelView.getParent().invalidateChild(notificationPanelView, NotificationPanelViewController.M_DUMMY_DIRTY_RECT);
                break;
            case 11:
                ((NotificationPanelViewController) obj).mIsBrightnessMirrorShowing.updateState(null, Boolean.FALSE);
                break;
            case 12:
                ((NotificationPanelViewController) obj).mLatencyTracker.onActionEnd(0);
                break;
            case 13:
                Rect rect3 = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                ((NotificationPanelViewController) obj).updateVisibility();
                break;
            case 14:
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
