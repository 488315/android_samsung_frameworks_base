package com.android.systemui.shade;

import android.animation.ValueAnimator;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.Log;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda0(NotificationPanelViewController notificationPanelViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationPanelViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.setListening(false);
                break;
            case 1:
                NotificationPanelView notificationPanelView = this.f$0.mView;
                notificationPanelView.getParent().invalidateChild(notificationPanelView, NotificationPanelViewController.M_DUMMY_DIRTY_RECT);
                break;
            case 2:
                this.f$0.closeQsIfPossible();
                break;
            case 3:
                this.f$0.instantCollapse();
                break;
            case 4:
                this.f$0.updateResources();
                break;
            case 5:
                NotificationPanelViewController notificationPanelViewController = this.f$0;
                ValueAnimator valueAnimator = notificationPanelViewController.mQsController.mExpansionAnimator;
                if (valueAnimator != null) {
                    valueAnimator.end();
                }
                notificationPanelViewController.collapse(1.0f, false);
                break;
            case 6:
                NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                notificationPanelViewController2.fling(0.0f, false, notificationPanelViewController2.mNextCollapseSpeedUpFactor);
                break;
            case 7:
                this.f$0.mKeyguardBottomArea.setVisibility(8);
                break;
            case 8:
                NotificationPanelViewController notificationPanelViewController3 = this.f$0;
                notificationPanelViewController3.mHeadsUpAnimatingAway = false;
                NotificationStackScrollLayout notificationStackScrollLayout = notificationPanelViewController3.mNotificationStackScrollLayoutController.mView;
                notificationStackScrollLayout.mHeadsUpAnimatingAway = false;
                notificationStackScrollLayout.updateClipping();
                notificationPanelViewController3.updateVisibility();
                notificationPanelViewController3.updateExpansionAndVisibility();
                break;
            case 9:
                NotificationPanelViewController notificationPanelViewController4 = this.f$0;
                if (notificationPanelViewController4.mExpandedFraction != 0.0f) {
                    Log.m138d("KeyguardVisible", "makeExpandedInvisible is not called. fraction=" + notificationPanelViewController4.mExpandedFraction);
                    ((KeyguardFastBioUnlockController) Dependency.get(KeyguardFastBioUnlockController.class)).reset();
                    break;
                } else {
                    notificationPanelViewController4.mView.post(notificationPanelViewController4.mHideExpandedRunnable);
                    break;
                }
            case 10:
                NotificationPanelViewController notificationPanelViewController5 = this.f$0;
                notificationPanelViewController5.notifyExpandingFinished();
                notificationPanelViewController5.onUnlockHintFinished();
                notificationPanelViewController5.mHintAnimationRunning = false;
                break;
            case 11:
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.f$0.mNotificationShadeWindowController;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState.forceWindowCollapsed = false;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                break;
            default:
                this.f$0.mLatencyTracker.onActionEnd(0);
                break;
        }
    }
}
