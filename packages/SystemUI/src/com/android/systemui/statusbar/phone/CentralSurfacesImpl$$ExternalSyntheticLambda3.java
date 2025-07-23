package com.android.systemui.statusbar.phone;

import android.os.RemoteException;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.statusbar.LightRevealScrim;
import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CentralSurfacesImpl f$0;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda3(CentralSurfacesImpl centralSurfacesImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = centralSurfacesImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        final CentralSurfacesImpl centralSurfacesImpl = this.f$0;
        switch (i) {
            case 0:
                centralSurfacesImpl.checkBarModes$1();
                break;
            case 1:
                final float wallpaperDimAmount = centralSurfacesImpl.mWallpaperManager.lockScreenWallpaperExists() ? centralSurfacesImpl.mWallpaperManager.getWallpaperDimAmount() : 0.0f;
                centralSurfacesImpl.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        CentralSurfacesImpl centralSurfacesImpl2 = CentralSurfacesImpl.this;
                        float f = wallpaperDimAmount;
                        ScrimController scrimController = centralSurfacesImpl2.mScrimController;
                        scrimController.getClass();
                        float compositeAlpha = ColorUtils.compositeAlpha((int) (f * 255.0f), 51) / 255.0f;
                        scrimController.mScrimBehindAlphaKeyguard = compositeAlpha;
                        for (ScrimState scrimState : ScrimState.values()) {
                            scrimState.mScrimBehindAlphaKeyguard = compositeAlpha;
                        }
                        scrimController.scheduleUpdate$1();
                    }
                });
                break;
            case 2:
                UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl.getClass();
                try {
                    centralSurfacesImpl.mDreamManager.awaken();
                    break;
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return;
                }
            case 3:
                centralSurfacesImpl.updateScrimController();
                break;
            default:
                LightRevealScrim lightRevealScrim = centralSurfacesImpl.mLightRevealScrim;
                boolean z = lightRevealScrim.isScrimOpaque;
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) centralSurfacesImpl.mNotificationShadeWindowController;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                if (notificationShadeWindowState.lightRevealScrimOpaque != z) {
                    notificationShadeWindowState.lightRevealScrimOpaque = z;
                    notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                }
                boolean z2 = lightRevealScrim.isScrimOpaque;
                Iterator it = centralSurfacesImpl.mScreenOffAnimationController.animations.iterator();
                while (it.hasNext()) {
                    ((ScreenOffAnimation) it.next()).onScrimOpaqueChanged(z2);
                }
                break;
        }
    }
}
