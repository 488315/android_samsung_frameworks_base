package com.android.systemui.statusbar.phone;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda0;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.window.StatusBarWindowController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarHeadsUpChangeListener implements OnHeadsUpChangedListener {
    public final HeadsUpManagerPhone mHeadsUpManager;
    public final KeyguardBypassController mKeyguardBypassController;
    public final NotificationRemoteInputManager mNotificationRemoteInputManager;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final ShadeViewController mShadeViewController;
    public final StatusBarStateController mStatusBarStateController;
    public final StatusBarWindowController mStatusBarWindowController;

    public StatusBarHeadsUpChangeListener(NotificationShadeWindowController notificationShadeWindowController, StatusBarWindowController statusBarWindowController, ShadeViewController shadeViewController, KeyguardBypassController keyguardBypassController, HeadsUpManagerPhone headsUpManagerPhone, StatusBarStateController statusBarStateController, NotificationRemoteInputManager notificationRemoteInputManager) {
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mStatusBarWindowController = statusBarWindowController;
        this.mShadeViewController = shadeViewController;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mHeadsUpManager = headsUpManagerPhone;
        this.mStatusBarStateController = statusBarStateController;
        this.mNotificationRemoteInputManager = notificationRemoteInputManager;
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public final void onHeadsUpPinnedModeChanged(boolean z) {
        NotificationShadeWindowController notificationShadeWindowController = this.mNotificationShadeWindowController;
        StatusBarWindowController statusBarWindowController = this.mStatusBarWindowController;
        ShadeViewController shadeViewController = this.mShadeViewController;
        if (z) {
            NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) notificationShadeWindowController;
            NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
            notificationShadeWindowState.headsUpNotificationShowing = true;
            notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
            statusBarWindowController.setForceStatusBarVisible(true);
            NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) shadeViewController;
            if (notificationPanelViewController.isFullyCollapsed()) {
                notificationPanelViewController.mView.requestLayout();
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl2 = (NotificationShadeWindowControllerImpl) notificationPanelViewController.mNotificationShadeWindowController;
                NotificationShadeWindowState notificationShadeWindowState2 = notificationShadeWindowControllerImpl2.mCurrentState;
                notificationShadeWindowState2.forceWindowCollapsed = true;
                notificationShadeWindowControllerImpl2.apply(notificationShadeWindowState2);
                notificationPanelViewController.mView.post(new NotificationPanelViewController$$ExternalSyntheticLambda0(notificationPanelViewController, 11));
                return;
            }
            return;
        }
        boolean z2 = this.mKeyguardBypassController.getBypassEnabled() && this.mStatusBarStateController.getState() == 1;
        NotificationPanelViewController notificationPanelViewController2 = (NotificationPanelViewController) shadeViewController;
        if (notificationPanelViewController2.isFullyCollapsed() && !notificationPanelViewController2.mTracking && !z2) {
            this.mHeadsUpManager.setHeadsUpGoingAway(true);
            statusBarWindowController.setForceStatusBarVisible(false);
            notificationPanelViewController2.mNotificationStackScrollLayoutController.mView.mAnimationFinishedRunnables.add(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarHeadsUpChangeListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    StatusBarHeadsUpChangeListener statusBarHeadsUpChangeListener = StatusBarHeadsUpChangeListener.this;
                    HeadsUpManagerPhone headsUpManagerPhone = statusBarHeadsUpChangeListener.mHeadsUpManager;
                    if (!headsUpManagerPhone.mHasPinnedNotification) {
                        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl3 = (NotificationShadeWindowControllerImpl) statusBarHeadsUpChangeListener.mNotificationShadeWindowController;
                        NotificationShadeWindowState notificationShadeWindowState3 = notificationShadeWindowControllerImpl3.mCurrentState;
                        notificationShadeWindowState3.headsUpNotificationShowing = false;
                        notificationShadeWindowControllerImpl3.apply(notificationShadeWindowState3);
                        headsUpManagerPhone.setHeadsUpGoingAway(false);
                    }
                    RemoteInputCoordinator remoteInputCoordinator = statusBarHeadsUpChangeListener.mNotificationRemoteInputManager.mRemoteInputListener;
                    if (remoteInputCoordinator != null) {
                        remoteInputCoordinator.mRemoteInputActiveExtender.endAllLifetimeExtensions();
                    }
                }
            });
            return;
        }
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl3 = (NotificationShadeWindowControllerImpl) notificationShadeWindowController;
        NotificationShadeWindowState notificationShadeWindowState3 = notificationShadeWindowControllerImpl3.mCurrentState;
        notificationShadeWindowState3.headsUpNotificationShowing = false;
        notificationShadeWindowControllerImpl3.apply(notificationShadeWindowState3);
        if (z2) {
            statusBarWindowController.setForceStatusBarVisible(false);
        }
    }
}
