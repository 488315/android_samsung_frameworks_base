package com.android.systemui.shade;

import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda8(NotificationPanelViewController notificationPanelViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationPanelViewController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        switch (i) {
            case 0:
                notificationPanelViewController.getClass();
                notificationPanelViewController.mIsOcclusionTransitionRunning = ((TransitionStep) obj).transitionState == TransitionState.RUNNING;
                break;
            case 1:
                notificationPanelViewController.getClass();
                notificationPanelViewController.mIsOcclusionTransitionRunning = ((TransitionStep) obj).transitionState == TransitionState.RUNNING;
                break;
            case 2:
                notificationPanelViewController.getClass();
                boolean z = ((TransitionStep) obj).transitionState == TransitionState.RUNNING;
                notificationPanelViewController.mIsOcclusionTransitionRunning = z;
                notificationPanelViewController.mIsGoneToDreamingLockscreenHostedTransitionRunning = z;
                break;
            case 3:
                notificationPanelViewController.getClass();
                notificationPanelViewController.mIsOcclusionTransitionRunning = ((TransitionStep) obj).transitionState == TransitionState.RUNNING;
                break;
            case 4:
                notificationPanelViewController.getClass();
                notificationPanelViewController.mIsOcclusionTransitionRunning = ((TransitionStep) obj).transitionState == TransitionState.RUNNING;
                break;
            case 5:
                notificationPanelViewController.getClass();
                notificationPanelViewController.mIsOcclusionTransitionRunning = ((TransitionStep) obj).transitionState == TransitionState.RUNNING;
                break;
            case 6:
                notificationPanelViewController.setExpandedHeightInternal(((Float) obj).floatValue());
                break;
            case 7:
                notificationPanelViewController.onTrackingStopped(((Boolean) obj).booleanValue());
                break;
            case 8:
                Runnable runnable = notificationPanelViewController.mPanelAlphaEndAction;
                if (runnable != null) {
                    runnable.run();
                    break;
                }
                break;
            case 9:
                notificationPanelViewController.getClass();
                notificationPanelViewController.mIsOcclusionTransitionRunning = ((TransitionStep) obj).transitionState == TransitionState.RUNNING;
                break;
            case 10:
                notificationPanelViewController.getClass();
                float floatValue = ((Float) obj).floatValue();
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
                notificationStackScrollLayoutController.mMaxAlphaForKeyguard = floatValue;
                notificationStackScrollLayoutController.mMaxAlphaForKeyguardSource = "mPrimaryBouncerToGoneTransitionViewModel.getNotificationAlpha()";
                notificationStackScrollLayoutController.updateAlpha$1$1();
                break;
            case 11:
                notificationPanelViewController.getClass();
                if (((Boolean) obj).booleanValue()) {
                    notificationPanelViewController.updateSystemUiStateFlags();
                    break;
                }
                break;
            default:
                notificationPanelViewController.getClass();
                notificationPanelViewController.mIsOcclusionTransitionRunning = ((TransitionStep) obj).transitionState == TransitionState.RUNNING;
                break;
        }
    }
}
