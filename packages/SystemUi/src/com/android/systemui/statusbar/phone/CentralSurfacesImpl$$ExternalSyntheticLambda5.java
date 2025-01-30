package com.android.systemui.statusbar.phone;

import android.R;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.os.RemoteException;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.graphics.ColorUtils;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CentralSurfacesImpl f$0;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda5(CentralSurfacesImpl centralSurfacesImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = centralSurfacesImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String valueOf;
        ComponentName component;
        String str;
        switch (this.$r8$classId) {
            case 0:
                this.f$0.checkBarModes();
                break;
            case 1:
                final CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                WallpaperInfo wallpaperInfoForUser = centralSurfacesImpl.mWallpaperManager.getWallpaperInfoForUser(((UserTrackerImpl) centralSurfacesImpl.mUserTracker).getUserId());
                centralSurfacesImpl.mWallpaperController.wallpaperInfo = wallpaperInfoForUser;
                AODAmbientWallpaperHelper aODAmbientWallpaperHelper = centralSurfacesImpl.mAODAmbientWallpaperHelper;
                aODAmbientWallpaperHelper.getClass();
                boolean z = LsRune.AOD_SUB_DISPLAY_LOCK;
                final boolean z2 = false;
                UserTracker userTracker = aODAmbientWallpaperHelper.userTracker;
                WallpaperManager wallpaperManager = aODAmbientWallpaperHelper.wallpaperManager;
                SettingsHelper settingsHelper = aODAmbientWallpaperHelper.settingsHelper;
                String str2 = "";
                if (z) {
                    if (aODAmbientWallpaperHelper.isFolded) {
                        ComponentName semGetWallpaperComponent = wallpaperManager.semGetWallpaperComponent(17, ((UserTrackerImpl) userTracker).getUserId());
                        str = String.valueOf(semGetWallpaperComponent != null ? semGetWallpaperComponent.getClassName() : null);
                        aODAmbientWallpaperHelper.isSubWonderLandWallpaper = Intrinsics.areEqual("com.samsung.android.wonderland.wallpaper.service.WonderLandWallpaperReloadedSubService", str) && settingsHelper.isLiveWallpaperEnabled();
                        boolean z3 = aODAmbientWallpaperHelper.isFolded;
                        boolean z4 = aODAmbientWallpaperHelper.isMainWonderLandWallpaper;
                        boolean z5 = aODAmbientWallpaperHelper.isSubWonderLandWallpaper;
                        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("updateWonderLandWallpaperState: isFolded=", z3, ", isMainWonderLandWallpaper=", z4, ", isSubWonderLandWallpaper=");
                        m69m.append(z5);
                        m69m.append(" mainWallpaperClassName=");
                        m69m.append(str2);
                        m69m.append(", subWallpaperClassName=");
                        ExifInterface$$ExternalSyntheticOutline0.m35m(m69m, str, "AODAmbientWallpaperHelper");
                        if (centralSurfacesImpl.mContext.getResources().getBoolean(R.bool.config_device_respects_hold_carrier_config) && wallpaperInfoForUser != null && wallpaperInfoForUser.supportsAmbientMode() && centralSurfacesImpl.mAODAmbientWallpaperHelper.isWonderLandAmbientWallpaper()) {
                            z2 = true;
                        }
                        centralSurfacesImpl.mMainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda14
                            @Override // java.lang.Runnable
                            public final void run() {
                                CentralSurfacesImpl centralSurfacesImpl2 = CentralSurfacesImpl.this;
                                boolean z6 = z2;
                                centralSurfacesImpl2.getClass();
                                if (LsRune.AOD_LIGHT_REVEAL) {
                                    int i = z6 ? 4 : 0;
                                    LightRevealScrim lightRevealScrim = centralSurfacesImpl2.mLightRevealScrim;
                                    lightRevealScrim.setVisibility(i);
                                    lightRevealScrim.setAlpha(z6 ? 0.0f : 1.0f);
                                }
                                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) centralSurfacesImpl2.mNotificationShadeWindowController;
                                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                                notificationShadeWindowState.wallpaperSupportsAmbientMode = z6;
                                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                                ScrimController scrimController = centralSurfacesImpl2.mScrimController;
                                scrimController.getClass();
                                Log.i("ScrimController", "setWallpaperSupportsAmbientMode: wallpaperSupportsAmbientMode=" + z6);
                                scrimController.mWallpaperSupportsAmbientMode = z6;
                                for (ScrimState scrimState : ScrimState.values()) {
                                    scrimState.mWallpaperSupportsAmbientMode = z6;
                                }
                                centralSurfacesImpl2.mKeyguardViewMediator.setWallpaperSupportsAmbientMode(z6);
                            }
                        });
                        break;
                    } else {
                        ComponentName semGetWallpaperComponent2 = wallpaperManager.semGetWallpaperComponent(5, ((UserTrackerImpl) userTracker).getUserId());
                        valueOf = String.valueOf(semGetWallpaperComponent2 != null ? semGetWallpaperComponent2.getClassName() : null);
                        aODAmbientWallpaperHelper.isMainWonderLandWallpaper = Intrinsics.areEqual("com.samsung.android.wonderland.wallpaper.service.WonderLandWallpaperReloadedService", valueOf) && settingsHelper.isLiveWallpaperEnabled();
                    }
                } else if (LsRune.AOD_SUB_DISPLAY_COVER) {
                    ComponentName semGetWallpaperComponent3 = wallpaperManager.semGetWallpaperComponent(5, ((UserTrackerImpl) userTracker).getUserId());
                    valueOf = String.valueOf(semGetWallpaperComponent3 != null ? semGetWallpaperComponent3.getClassName() : null);
                    aODAmbientWallpaperHelper.isMainWonderLandWallpaper = Intrinsics.areEqual("com.samsung.android.wonderland.wallpaper.service.WonderLandWallpaperReloadedService", valueOf) && settingsHelper.isLiveWallpaperEnabled(false);
                } else {
                    if (wallpaperInfoForUser != null && (component = wallpaperInfoForUser.getComponent()) != null) {
                        r10 = component.getClassName();
                    }
                    valueOf = String.valueOf(r10);
                    aODAmbientWallpaperHelper.isMainWonderLandWallpaper = Intrinsics.areEqual(valueOf, "com.samsung.android.wonderland.wallpaper.service.WonderLandWallpaperReloadedService") && settingsHelper.isLiveWallpaperEnabled();
                }
                str2 = valueOf;
                str = "";
                boolean z32 = aODAmbientWallpaperHelper.isFolded;
                boolean z42 = aODAmbientWallpaperHelper.isMainWonderLandWallpaper;
                boolean z52 = aODAmbientWallpaperHelper.isSubWonderLandWallpaper;
                StringBuilder m69m2 = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("updateWonderLandWallpaperState: isFolded=", z32, ", isMainWonderLandWallpaper=", z42, ", isSubWonderLandWallpaper=");
                m69m2.append(z52);
                m69m2.append(" mainWallpaperClassName=");
                m69m2.append(str2);
                m69m2.append(", subWallpaperClassName=");
                ExifInterface$$ExternalSyntheticOutline0.m35m(m69m2, str, "AODAmbientWallpaperHelper");
                if (centralSurfacesImpl.mContext.getResources().getBoolean(R.bool.config_device_respects_hold_carrier_config)) {
                    z2 = true;
                }
                centralSurfacesImpl.mMainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        CentralSurfacesImpl centralSurfacesImpl2 = CentralSurfacesImpl.this;
                        boolean z6 = z2;
                        centralSurfacesImpl2.getClass();
                        if (LsRune.AOD_LIGHT_REVEAL) {
                            int i = z6 ? 4 : 0;
                            LightRevealScrim lightRevealScrim = centralSurfacesImpl2.mLightRevealScrim;
                            lightRevealScrim.setVisibility(i);
                            lightRevealScrim.setAlpha(z6 ? 0.0f : 1.0f);
                        }
                        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) centralSurfacesImpl2.mNotificationShadeWindowController;
                        NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                        notificationShadeWindowState.wallpaperSupportsAmbientMode = z6;
                        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                        ScrimController scrimController = centralSurfacesImpl2.mScrimController;
                        scrimController.getClass();
                        Log.i("ScrimController", "setWallpaperSupportsAmbientMode: wallpaperSupportsAmbientMode=" + z6);
                        scrimController.mWallpaperSupportsAmbientMode = z6;
                        for (ScrimState scrimState : ScrimState.values()) {
                            scrimState.mWallpaperSupportsAmbientMode = z6;
                        }
                        centralSurfacesImpl2.mKeyguardViewMediator.setWallpaperSupportsAmbientMode(z6);
                    }
                });
                break;
            case 2:
                CentralSurfacesImpl centralSurfacesImpl2 = this.f$0;
                centralSurfacesImpl2.getClass();
                try {
                    centralSurfacesImpl2.mBarService.onPanelHidden();
                    break;
                } catch (RemoteException unused) {
                    return;
                }
            case 3:
                CentralSurfacesImpl centralSurfacesImpl3 = this.f$0;
                centralSurfacesImpl3.getClass();
                try {
                    centralSurfacesImpl3.mDreamManager.awaken();
                    break;
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return;
                }
            case 4:
                final CentralSurfacesImpl centralSurfacesImpl4 = this.f$0;
                centralSurfacesImpl4.getClass();
                ((ExecutorImpl) centralSurfacesImpl4.mMainExecutor).execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        ScrimController scrimController = CentralSurfacesImpl.this.mScrimController;
                        scrimController.getClass();
                        float compositeAlpha = ColorUtils.compositeAlpha((int) 0.0f, 51) / 255.0f;
                        scrimController.mScrimBehindAlphaKeyguard = compositeAlpha;
                        for (ScrimState scrimState : ScrimState.values()) {
                            scrimState.mScrimBehindAlphaKeyguard = compositeAlpha;
                        }
                        scrimController.scheduleUpdate();
                    }
                });
                break;
            case 5:
                this.f$0.updateScrimController();
                break;
            default:
                CentralSurfacesImpl centralSurfacesImpl5 = this.f$0;
                LightRevealScrim lightRevealScrim = centralSurfacesImpl5.mLightRevealScrim;
                boolean z6 = lightRevealScrim.isScrimOpaque;
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) centralSurfacesImpl5.mNotificationShadeWindowController;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                if (notificationShadeWindowState.lightRevealScrimOpaque != z6) {
                    notificationShadeWindowState.lightRevealScrimOpaque = z6;
                    notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                }
                boolean z7 = lightRevealScrim.isScrimOpaque;
                Iterator it = ((ArrayList) centralSurfacesImpl5.mScreenOffAnimationController.animations).iterator();
                while (it.hasNext()) {
                    ((ScreenOffAnimation) it.next()).onScrimOpaqueChanged(z7);
                }
                break;
        }
    }
}
