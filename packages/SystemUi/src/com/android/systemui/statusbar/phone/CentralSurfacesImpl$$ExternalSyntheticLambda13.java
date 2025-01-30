package com.android.systemui.statusbar.phone;

import android.app.Notification;
import android.app.PendingIntent;
import android.os.SystemClock;
import android.service.notification.StatusBarNotification;
import android.util.EventLog;
import com.android.p038wm.shell.bubbles.BubbleController;
import com.android.p038wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda15;
import com.android.p038wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda19;
import com.android.p038wm.shell.bubbles.Bubbles;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.startingsurface.StartingWindowController;
import com.android.p038wm.shell.startingsurface.StartingWindowController$$ExternalSyntheticLambda1;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda13 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CentralSurfacesImpl f$0;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda13(CentralSurfacesImpl centralSurfacesImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = centralSurfacesImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        boolean z;
        int i = 1;
        int i2 = 6;
        switch (this.$r8$classId) {
            case 0:
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                Bubbles bubbles = (Bubbles) obj;
                int i3 = centralSurfacesImpl.mStatusBarMode;
                if (i3 == 3 || i3 == 6 || centralSurfacesImpl.mStatusBarWindowState == 2) {
                    NavBarHelper navBarHelper = (NavBarHelper) centralSurfacesImpl.mNavBarHelperLazy.get();
                    navBarHelper.getClass();
                    if (new NavBarHelper.CurrentSysuiState(navBarHelper).mWindowState == 2) {
                        z = false;
                        BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubbles;
                        ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda19(i, bubblesImpl, z));
                        break;
                    }
                }
                z = true;
                BubbleController.BubblesImpl bubblesImpl2 = (BubbleController.BubblesImpl) bubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda19(i, bubblesImpl2, z));
            case 1:
                CentralSurfacesImpl centralSurfacesImpl2 = this.f$0;
                NotificationEntry notificationEntry = (NotificationEntry) obj;
                centralSurfacesImpl2.getClass();
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                Notification notification2 = statusBarNotification.getNotification();
                if (notification2.fullScreenIntent != null) {
                    try {
                        EventLog.writeEvent(36003, statusBarNotification.getKey());
                        centralSurfacesImpl2.wakeUpForFullScreenIntent(statusBarNotification.getPackageName());
                        notification2.fullScreenIntent.send();
                        notificationEntry.interruption = true;
                        notificationEntry.lastFullScreenIntentLaunchTime = SystemClock.elapsedRealtime();
                        break;
                    } catch (PendingIntent.CanceledException unused) {
                        return;
                    }
                }
                break;
            case 2:
                CentralSurfacesImpl centralSurfacesImpl3 = this.f$0;
                centralSurfacesImpl3.getClass();
                BubbleController.BubblesImpl bubblesImpl3 = (BubbleController.BubblesImpl) ((Bubbles) obj);
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda15(i2, bubblesImpl3, new CentralSurfacesImpl$$ExternalSyntheticLambda0(centralSurfacesImpl3)));
                break;
            case 3:
                CentralSurfacesImpl centralSurfacesImpl4 = this.f$0;
                StartingWindowController.StartingSurfaceImpl startingSurfaceImpl = (StartingWindowController.StartingSurfaceImpl) obj;
                centralSurfacesImpl4.getClass();
                ((HandlerExecutor) StartingWindowController.this.mSplashScreenExecutor).execute(new StartingWindowController$$ExternalSyntheticLambda1(i, startingSurfaceImpl, new CentralSurfacesImpl$$ExternalSyntheticLambda0(centralSurfacesImpl4)));
                break;
            case 4:
                CentralSurfacesImpl centralSurfacesImpl5 = this.f$0;
                centralSurfacesImpl5.getClass();
                int intValue = ((Integer) obj).intValue();
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) centralSurfacesImpl5.mNotificationShadeWindowController;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                if (intValue != notificationShadeWindowState.scrimsVisibility) {
                    boolean isExpanded = notificationShadeWindowControllerImpl.isExpanded(notificationShadeWindowState);
                    notificationShadeWindowState.scrimsVisibility = intValue;
                    if (isExpanded != notificationShadeWindowControllerImpl.isExpanded(notificationShadeWindowState)) {
                        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                    }
                    notificationShadeWindowControllerImpl.mScrimsVisibilityListener.accept(Integer.valueOf(intValue));
                    break;
                }
                break;
            case 5:
                CentralSurfacesImpl centralSurfacesImpl6 = this.f$0;
                centralSurfacesImpl6.getClass();
                CentralSurfacesImpl$$ExternalSyntheticLambda5 centralSurfacesImpl$$ExternalSyntheticLambda5 = new CentralSurfacesImpl$$ExternalSyntheticLambda5(centralSurfacesImpl6, i2);
                if (!((Boolean) obj).booleanValue()) {
                    centralSurfacesImpl$$ExternalSyntheticLambda5.run();
                    break;
                } else {
                    centralSurfacesImpl6.mLightRevealScrim.post(centralSurfacesImpl$$ExternalSyntheticLambda5);
                    break;
                }
            default:
                this.f$0.getClass();
                break;
        }
    }
}
