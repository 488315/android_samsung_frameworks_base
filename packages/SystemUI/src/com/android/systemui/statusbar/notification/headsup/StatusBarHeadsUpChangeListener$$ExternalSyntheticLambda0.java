package com.android.systemui.statusbar.notification.headsup;

import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StatusBarHeadsUpChangeListener$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StatusBarHeadsUpChangeListener f$0;

    public /* synthetic */ StatusBarHeadsUpChangeListener$$ExternalSyntheticLambda0(StatusBarHeadsUpChangeListener statusBarHeadsUpChangeListener, int i) {
        this.$r8$classId = i;
        this.f$0 = statusBarHeadsUpChangeListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        StatusBarHeadsUpChangeListener statusBarHeadsUpChangeListener = this.f$0;
        switch (i) {
            case 0:
                if (!((HeadsUpManagerImpl) statusBarHeadsUpChangeListener.mHeadsUpManager).mHasPinnedNotification) {
                    NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) statusBarHeadsUpChangeListener.mNotificationShadeWindowController;
                    NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                    notificationShadeWindowState.headsUpNotificationShowing = false;
                    notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                    int i2 = SceneContainerFlag.$r8$clinit;
                    ((HeadsUpManagerImpl) statusBarHeadsUpChangeListener.mHeadsUpManager).setHeadsUpAnimatingAway(false);
                }
                RemoteInputCoordinator remoteInputCoordinator = statusBarHeadsUpChangeListener.mNotificationRemoteInputManager.mRemoteInputListener;
                if (remoteInputCoordinator != null) {
                    remoteInputCoordinator.onPanelCollapsed();
                    break;
                }
                break;
            default:
                if (!((HeadsUpManagerImpl) statusBarHeadsUpChangeListener.mHeadsUpManager).mHasPinnedNotification) {
                    NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl2 = (NotificationShadeWindowControllerImpl) statusBarHeadsUpChangeListener.mNotificationShadeWindowController;
                    NotificationShadeWindowState notificationShadeWindowState2 = notificationShadeWindowControllerImpl2.mCurrentState;
                    notificationShadeWindowState2.headsUpNotificationShowing = false;
                    notificationShadeWindowControllerImpl2.apply(notificationShadeWindowState2);
                    int i3 = SceneContainerFlag.$r8$clinit;
                    ((HeadsUpManagerImpl) statusBarHeadsUpChangeListener.mHeadsUpManager).setHeadsUpAnimatingAway(false);
                }
                RemoteInputCoordinator remoteInputCoordinator2 = statusBarHeadsUpChangeListener.mNotificationRemoteInputManager.mRemoteInputListener;
                if (remoteInputCoordinator2 != null) {
                    remoteInputCoordinator2.onPanelCollapsed();
                    break;
                }
                break;
        }
    }
}
