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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CentralSurfacesImpl f$0;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda1(CentralSurfacesImpl centralSurfacesImpl, int i) {
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
                UiEventLogger uiEventLogger3 = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl.getClass();
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) ((Bubbles) obj);
                BubbleController.this.mMainExecutor.execute(new BubbleController$$ExternalSyntheticLambda14(3, bubblesImpl, new CentralSurfacesImpl$$ExternalSyntheticLambda23(centralSurfacesImpl)));
                break;
            case 3:
                StartingWindowController.StartingSurfaceImpl startingSurfaceImpl = (StartingWindowController.StartingSurfaceImpl) obj;
                UiEventLogger uiEventLogger4 = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl.getClass();
                StartingWindowController.this.mSplashScreenExecutor.execute(new StartingWindowController$$ExternalSyntheticLambda4(i, startingSurfaceImpl, new CentralSurfacesImpl$$ExternalSyntheticLambda23(centralSurfacesImpl)));
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
                UiEventLogger uiEventLogger7 = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl.getClass();
                CentralSurfacesImpl$$ExternalSyntheticLambda3 centralSurfacesImpl$$ExternalSyntheticLambda3 = new CentralSurfacesImpl$$ExternalSyntheticLambda3(centralSurfacesImpl, 4);
                if (!((Boolean) obj).booleanValue()) {
                    centralSurfacesImpl$$ExternalSyntheticLambda3.run();
                    break;
                } else {
                    centralSurfacesImpl.mLightRevealScrim.post(centralSurfacesImpl$$ExternalSyntheticLambda3);
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
