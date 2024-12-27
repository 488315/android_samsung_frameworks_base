package com.android.systemui.statusbar.phone;

import android.app.ActivityOptions;
import android.app.Notification;
import android.app.PendingIntent;
import android.os.SystemClock;
import android.service.notification.StatusBarNotification;
import android.util.EventLog;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda16;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.startingsurface.StartingWindowController;
import com.android.wm.shell.startingsurface.StartingWindowController$$ExternalSyntheticLambda4;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CentralSurfacesImpl f$0;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda2(CentralSurfacesImpl centralSurfacesImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = centralSurfacesImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        CentralSurfacesImpl centralSurfacesImpl = this.f$0;
        switch (i) {
            case 0:
                Boolean bool = (Boolean) obj;
                centralSurfacesImpl.getClass();
                if (bool.booleanValue() != centralSurfacesImpl.mIsIdleOnCommunal) {
                    centralSurfacesImpl.mIsIdleOnCommunal = bool.booleanValue();
                    centralSurfacesImpl.updateScrimController();
                    break;
                }
                break;
            case 1:
                NotificationEntry notificationEntry = (NotificationEntry) obj;
                centralSurfacesImpl.getClass();
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                Notification notification2 = statusBarNotification.getNotification();
                if (notification2.fullScreenIntent != null) {
                    try {
                        EventLog.writeEvent(36003, statusBarNotification.getKey());
                        centralSurfacesImpl.mPowerInteractor.wakeUpForFullScreenIntent(statusBarNotification.getPackageName());
                        ActivityOptions makeBasic = ActivityOptions.makeBasic();
                        makeBasic.setPendingIntentBackgroundActivityStartMode(1);
                        notification2.fullScreenIntent.send(makeBasic.toBundle());
                        notificationEntry.interruption = true;
                        notificationEntry.lastFullScreenIntentLaunchTime = SystemClock.elapsedRealtime();
                        break;
                    } catch (PendingIntent.CanceledException unused) {
                        return;
                    }
                }
                break;
            case 2:
                centralSurfacesImpl.getClass();
                CentralSurfacesImpl$$ExternalSyntheticLambda0 centralSurfacesImpl$$ExternalSyntheticLambda0 = new CentralSurfacesImpl$$ExternalSyntheticLambda0(centralSurfacesImpl);
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) ((Bubbles) obj);
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda16(5, bubblesImpl, centralSurfacesImpl$$ExternalSyntheticLambda0));
                break;
            case 3:
                StartingWindowController.StartingSurfaceImpl startingSurfaceImpl = (StartingWindowController.StartingSurfaceImpl) obj;
                centralSurfacesImpl.getClass();
                CentralSurfacesImpl$$ExternalSyntheticLambda0 centralSurfacesImpl$$ExternalSyntheticLambda02 = new CentralSurfacesImpl$$ExternalSyntheticLambda0(centralSurfacesImpl);
                ((HandlerExecutor) StartingWindowController.this.mSplashScreenExecutor).execute(new StartingWindowController$$ExternalSyntheticLambda4(1, startingSurfaceImpl, centralSurfacesImpl$$ExternalSyntheticLambda02));
                break;
            case 4:
                centralSurfacesImpl.checkBarModes$1();
                centralSurfacesImpl.mAutoHideController.touchAutoHide();
                centralSurfacesImpl.updateBubblesVisibility();
                break;
            case 5:
                Integer num = (Integer) obj;
                centralSurfacesImpl.getClass();
                int intValue = num.intValue();
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) centralSurfacesImpl.mNotificationShadeWindowController;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                if (intValue != notificationShadeWindowState.scrimsVisibility) {
                    boolean isExpanded = notificationShadeWindowControllerImpl.isExpanded(notificationShadeWindowState);
                    notificationShadeWindowState.scrimsVisibility = intValue;
                    if (isExpanded != notificationShadeWindowControllerImpl.isExpanded(notificationShadeWindowState)) {
                        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                    }
                    notificationShadeWindowControllerImpl.mScrimsVisibilityListener.accept(num);
                    break;
                }
                break;
            case 6:
                centralSurfacesImpl.getClass();
                CentralSurfacesImpl$$ExternalSyntheticLambda4 centralSurfacesImpl$$ExternalSyntheticLambda4 = new CentralSurfacesImpl$$ExternalSyntheticLambda4(centralSurfacesImpl, 4);
                if (!((Boolean) obj).booleanValue()) {
                    centralSurfacesImpl$$ExternalSyntheticLambda4.run();
                    break;
                } else {
                    centralSurfacesImpl.mLightRevealScrim.post(centralSurfacesImpl$$ExternalSyntheticLambda4);
                    break;
                }
            default:
                ((Boolean) obj).booleanValue();
                centralSurfacesImpl.getClass();
                break;
        }
    }
}
