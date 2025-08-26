package com.android.systemui.statusbar.phone;

import android.app.ActivityOptions;
import android.app.Notification;
import android.app.PendingIntent;
import android.os.SystemClock;
import android.service.notification.StatusBarNotification;
import android.util.EventLog;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda14;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.startingsurface.StartingWindowController;
import com.android.wm.shell.startingsurface.StartingWindowController$$ExternalSyntheticLambda4;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CentralSurfacesImpl f$0;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda2(CentralSurfacesImpl centralSurfacesImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = centralSurfacesImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = 1;
        int i2 = this.$r8$classId;
        CentralSurfacesImpl centralSurfacesImpl = this.f$0;
        switch (i2) {
            case 0:
                Boolean bool = (Boolean) obj;
                UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl.getClass();
                if (bool.booleanValue() != centralSurfacesImpl.mIsIdleOnCommunal) {
                    centralSurfacesImpl.mIsIdleOnCommunal = bool.booleanValue();
                    centralSurfacesImpl.updateScrimController();
                    break;
                }
                break;
            case 1:
                NotificationEntry notificationEntry = (NotificationEntry) obj;
                UiEventLogger uiEventLogger2 = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl.getClass();
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                Notification notification2 = statusBarNotification.getNotification();
                if (notification2.fullScreenIntent != null) {
                    try {
                        EventLog.writeEvent(36003, statusBarNotification.getKey());
                        centralSurfacesImpl.mPowerInteractor.wakeUpForFullScreenIntent();
                        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                        activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(1);
                        notification2.fullScreenIntent.send(activityOptionsMakeBasic.toBundle());
                        notificationEntry.interruption = true;
                        notificationEntry.lastFullScreenIntentLaunchTime = SystemClock.elapsedRealtime();
                        break;
                    } catch (PendingIntent.CanceledException unused) {
                        return;
                    }
                }
                break;
            case 2:
                UiEventLogger uiEventLogger3 = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl.getClass();
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) ((Bubbles) obj);
                BubbleController.this.mMainExecutor.execute(new BubbleController$$ExternalSyntheticLambda14(3, bubblesImpl, new CentralSurfacesImpl$$ExternalSyntheticLambda24(centralSurfacesImpl)));
                break;
            case 3:
                StartingWindowController.StartingSurfaceImpl startingSurfaceImpl = (StartingWindowController.StartingSurfaceImpl) obj;
                UiEventLogger uiEventLogger4 = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl.getClass();
                StartingWindowController.this.mSplashScreenExecutor.execute(new StartingWindowController$$ExternalSyntheticLambda4(i, startingSurfaceImpl, new CentralSurfacesImpl$$ExternalSyntheticLambda24(centralSurfacesImpl)));
                break;
            case 4:
                UiEventLogger uiEventLogger5 = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl.checkBarModes$1();
                ((AutoHideControllerImpl) centralSurfacesImpl.mAutoHideController).touchAutoHide();
                centralSurfacesImpl.updateBubblesVisibility();
                break;
            case 5:
                Integer num = (Integer) obj;
                UiEventLogger uiEventLogger6 = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl.getClass();
                int iIntValue = num.intValue();
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) centralSurfacesImpl.mNotificationShadeWindowController;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                if (iIntValue != notificationShadeWindowState.scrimsVisibility) {
                    boolean zIsExpanded = notificationShadeWindowControllerImpl.isExpanded(notificationShadeWindowState);
                    notificationShadeWindowState.scrimsVisibility = iIntValue;
                    if (zIsExpanded != notificationShadeWindowControllerImpl.isExpanded(notificationShadeWindowState)) {
                        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                    }
                    notificationShadeWindowControllerImpl.mScrimsVisibilityListener.accept(num);
                    break;
                }
                break;
            case 6:
                UiEventLogger uiEventLogger7 = CentralSurfacesImpl.sUiEventLogger;
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
                UiEventLogger uiEventLogger8 = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl.getClass();
                break;
        }
    }
}
