package com.android.systemui.statusbar.phone;

import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.window.StatusBarWindowController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StatusBarHeadsUpChangeListener implements OnHeadsUpChangedListener, CoreStartable {
    public final HeadsUpManager mHeadsUpManager;
    public final KeyguardBypassController mKeyguardBypassController;
    public final NotificationRemoteInputManager mNotificationRemoteInputManager;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final NotificationStackScrollLayoutController mNsslController;
    public final PanelExpansionInteractor mPanelExpansionInteractor;
    public final ShadeViewController mShadeViewController;
    public final StatusBarStateController mStatusBarStateController;
    public final StatusBarWindowController mStatusBarWindowController;

    public StatusBarHeadsUpChangeListener(NotificationShadeWindowController notificationShadeWindowController, StatusBarWindowController statusBarWindowController, ShadeViewController shadeViewController, PanelExpansionInteractor panelExpansionInteractor, NotificationStackScrollLayoutController notificationStackScrollLayoutController, KeyguardBypassController keyguardBypassController, HeadsUpManager headsUpManager, StatusBarStateController statusBarStateController, NotificationRemoteInputManager notificationRemoteInputManager) {
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mStatusBarWindowController = statusBarWindowController;
        this.mShadeViewController = shadeViewController;
        this.mPanelExpansionInteractor = panelExpansionInteractor;
        this.mNsslController = notificationStackScrollLayoutController;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mHeadsUpManager = headsUpManager;
        this.mStatusBarStateController = statusBarStateController;
        this.mNotificationRemoteInputManager = notificationRemoteInputManager;
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public final void onHeadsUpPinnedModeChanged(boolean z) {
        NotificationShadeWindowController notificationShadeWindowController = this.mNotificationShadeWindowController;
        PanelExpansionInteractor panelExpansionInteractor = this.mPanelExpansionInteractor;
        StatusBarWindowController statusBarWindowController = this.mStatusBarWindowController;
        if (z) {
            NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) notificationShadeWindowController;
            NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
            notificationShadeWindowState.headsUpNotificationShowing = true;
            notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
            statusBarWindowController.setForceStatusBarVisible(true);
            if (panelExpansionInteractor.isFullyCollapsed()) {
                this.mShadeViewController.updateTouchableRegion();
                return;
            }
            return;
        }
        boolean z2 = this.mKeyguardBypassController.getBypassEnabled() && this.mStatusBarStateController.getState() == 1;
        if (!panelExpansionInteractor.isFullyCollapsed() || panelExpansionInteractor.isTracking() || z2) {
            NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl2 = (NotificationShadeWindowControllerImpl) notificationShadeWindowController;
            NotificationShadeWindowState notificationShadeWindowState2 = notificationShadeWindowControllerImpl2.mCurrentState;
            notificationShadeWindowState2.headsUpNotificationShowing = false;
            notificationShadeWindowControllerImpl2.apply(notificationShadeWindowState2);
            if (z2) {
                statusBarWindowController.setForceStatusBarVisible(false);
                return;
            }
            return;
        }
        NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
        Flags.notificationsHeadsUpRefactor();
        ((HeadsUpManagerPhone) this.mHeadsUpManager).setHeadsUpAnimatingAway(true);
        statusBarWindowController.setForceStatusBarVisible(false);
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarHeadsUpChangeListener$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                StatusBarHeadsUpChangeListener statusBarHeadsUpChangeListener = StatusBarHeadsUpChangeListener.this;
                if (!((BaseHeadsUpManager) statusBarHeadsUpChangeListener.mHeadsUpManager).mHasPinnedNotification) {
                    NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl3 = (NotificationShadeWindowControllerImpl) statusBarHeadsUpChangeListener.mNotificationShadeWindowController;
                    NotificationShadeWindowState notificationShadeWindowState3 = notificationShadeWindowControllerImpl3.mCurrentState;
                    notificationShadeWindowState3.headsUpNotificationShowing = false;
                    notificationShadeWindowControllerImpl3.apply(notificationShadeWindowState3);
                    NotificationsHeadsUpRefactor notificationsHeadsUpRefactor2 = NotificationsHeadsUpRefactor.INSTANCE;
                    Flags.notificationsHeadsUpRefactor();
                    ((HeadsUpManagerPhone) statusBarHeadsUpChangeListener.mHeadsUpManager).setHeadsUpAnimatingAway(false);
                }
                RemoteInputCoordinator remoteInputCoordinator = statusBarHeadsUpChangeListener.mNotificationRemoteInputManager.mRemoteInputListener;
                if (remoteInputCoordinator != null) {
                    remoteInputCoordinator.onPanelCollapsed();
                }
            }
        };
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNsslController;
        notificationStackScrollLayoutController.mView.mAnimationFinishedRunnables.add(runnable);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        if (notificationStackScrollLayout.mAnimationRunning) {
            return;
        }
        notificationStackScrollLayout.runAnimationFinishedRunnables();
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        ((BaseHeadsUpManager) this.mHeadsUpManager).addListener(this);
    }
}
