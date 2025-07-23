package com.android.systemui.statusbar.notification.headsup;

import android.util.Log;
import com.android.systemui.CoreStartable;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class StatusBarHeadsUpChangeListener implements OnHeadsUpChangedListener, CoreStartable {
    public final HeadsUpManager mHeadsUpManager;
    public final KeyguardBypassController mKeyguardBypassController;
    public final NotificationRemoteInputManager mNotificationRemoteInputManager;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final NotificationStackScrollLayoutController mNsslController;
    public final PanelExpansionInteractor mPanelExpansionInteractor;
    public final ShadeViewController mShadeViewController;
    public final StatusBarStateController mStatusBarStateController;
    public final StatusBarWindowControllerStore mStatusBarWindowControllerStore;

    public StatusBarHeadsUpChangeListener(NotificationShadeWindowController notificationShadeWindowController, StatusBarWindowControllerStore statusBarWindowControllerStore, ShadeViewController shadeViewController, PanelExpansionInteractor panelExpansionInteractor, NotificationStackScrollLayoutController notificationStackScrollLayoutController, KeyguardBypassController keyguardBypassController, HeadsUpManager headsUpManager, StatusBarStateController statusBarStateController, NotificationRemoteInputManager notificationRemoteInputManager) {
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mStatusBarWindowControllerStore = statusBarWindowControllerStore;
        this.mShadeViewController = shadeViewController;
        this.mPanelExpansionInteractor = panelExpansionInteractor;
        this.mNsslController = notificationStackScrollLayoutController;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mHeadsUpManager = headsUpManager;
        this.mStatusBarStateController = statusBarStateController;
        this.mNotificationRemoteInputManager = notificationRemoteInputManager;
    }

    @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
    public final void onHeadsUpPinnedModeChanged(boolean z) {
        NotificationShadeWindowController notificationShadeWindowController = this.mNotificationShadeWindowController;
        PanelExpansionInteractor panelExpansionInteractor = this.mPanelExpansionInteractor;
        StatusBarWindowControllerStore statusBarWindowControllerStore = this.mStatusBarWindowControllerStore;
        if (z) {
            NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) notificationShadeWindowController;
            NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
            notificationShadeWindowState.headsUpNotificationShowing = true;
            notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
            ((StatusBarWindowControllerImpl) ((StatusBarWindowController) statusBarWindowControllerStore.getDefaultDisplay())).setForceStatusBarVisible(true);
            if (panelExpansionInteractor.isFullyCollapsed()) {
                this.mShadeViewController.updateTouchableRegion();
                return;
            }
            return;
        }
        boolean z2 = this.mKeyguardBypassController.getBypassEnabled() && this.mStatusBarStateController.getState() == 1;
        if (panelExpansionInteractor.isFullyCollapsed() && !panelExpansionInteractor.isTracking() && !z2) {
            int i = SceneContainerFlag.$r8$clinit;
            ((HeadsUpManagerImpl) this.mHeadsUpManager).setHeadsUpAnimatingAway(true);
            ((StatusBarWindowControllerImpl) ((StatusBarWindowController) statusBarWindowControllerStore.getDefaultDisplay())).setForceStatusBarVisible(false);
            this.mNsslController.mView.mAnimationFinishedRunnables.add(new StatusBarHeadsUpChangeListener$$ExternalSyntheticLambda0(this, 1));
            return;
        }
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl2 = (NotificationShadeWindowControllerImpl) notificationShadeWindowController;
        NotificationShadeWindowState notificationShadeWindowState2 = notificationShadeWindowControllerImpl2.mCurrentState;
        notificationShadeWindowState2.headsUpNotificationShowing = false;
        notificationShadeWindowControllerImpl2.apply(notificationShadeWindowState2);
        if (z2) {
            ((StatusBarWindowControllerImpl) ((StatusBarWindowController) statusBarWindowControllerStore.getDefaultDisplay())).setForceStatusBarVisible(false);
        }
    }

    @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
    public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
        if (!notificationEntry.isRowDismissed() || z) {
            return;
        }
        Log.d("StatusBarHeadsUpChangeListener", "onHeadsUpStateChanged ->  runAfterAnimationFinished " + notificationEntry.mKey);
        StatusBarHeadsUpChangeListener$$ExternalSyntheticLambda0 statusBarHeadsUpChangeListener$$ExternalSyntheticLambda0 = new StatusBarHeadsUpChangeListener$$ExternalSyntheticLambda0(this, 0);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNsslController;
        notificationStackScrollLayoutController.mView.mAnimationFinishedRunnables.add(statusBarHeadsUpChangeListener$$ExternalSyntheticLambda0);
        notificationStackScrollLayoutController.mView.runAnimationFinishedRunnables();
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        ((HeadsUpManagerImpl) this.mHeadsUpManager).addListener(this);
    }
}
