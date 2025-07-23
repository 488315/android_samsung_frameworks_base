package com.android.systemui.aod;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.FactoryTest;
import android.os.Process;
import android.os.UserHandle;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AODAmbientWallpaperHelper {
    public boolean isMainWonderLandWallpaper;
    public boolean isSubWonderLandWallpaper;
    public final Lazy keyguardFoldControllerLazy;
    public final Lazy keyguardViewMediatorLazy;
    public final Lazy lightRevelScrimLazy;
    public boolean needWallpaperForUnlockAnimation;
    private final SettingsHelper settingsHelper;
    public final Lazy statusBarWindowControllerStoreLazy;
    public final UserTracker userTracker;
    public final WallpaperManager wallpaperManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public AODAmbientWallpaperHelper(SettingsHelper settingsHelper, Lazy lazy, WallpaperManager wallpaperManager, UserTracker userTracker, Lazy lazy2, Lazy lazy3, BroadcastDispatcher broadcastDispatcher, WakefulnessLifecycle wakefulnessLifecycle, Lazy lazy4) {
        this.settingsHelper = settingsHelper;
        this.keyguardViewMediatorLazy = lazy;
        this.wallpaperManager = wallpaperManager;
        this.userTracker = userTracker;
        this.statusBarWindowControllerStoreLazy = lazy2;
        this.keyguardFoldControllerLazy = lazy3;
        this.lightRevelScrimLazy = lazy4;
        boolean areEqual = Intrinsics.areEqual(Process.myUserHandle(), UserHandle.SYSTEM);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.aod.AODAmbientWallpaperHelper$wallpaperChangeReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                if ("android.intent.action.WALLPAPER_CHANGED".equals(intent.getAction())) {
                    Log.d("AODAmbientWallpaperHelper", "onReceive: action ACTION_WALLPAPER_CHANGED");
                    AODAmbientWallpaperHelper.this.updateWonderLandWallpaperState();
                    ((LightRevealScrim) AODAmbientWallpaperHelper.this.lightRevelScrimLazy.get()).setAlpha(AODAmbientWallpaperHelper.this.getAlpha());
                }
            }
        };
        if (LsRune.AOD_SAFEMODE) {
            Log.w("AODAmbientWallpaperHelper", "Do not init in SafeMode");
        } else if (!areEqual) {
            Log.w("AODAmbientWallpaperHelper", "Do not init no system user");
        } else {
            wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1
                @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
                public final void onStartedGoingToSleep() {
                    AODAmbientWallpaperHelper aODAmbientWallpaperHelper = AODAmbientWallpaperHelper.this;
                    boolean z = aODAmbientWallpaperHelper.isAODFullScreenMode() || aODAmbientWallpaperHelper.isMainWonderLandWallpaper || aODAmbientWallpaperHelper.isSubWonderLandWallpaper;
                    AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("onStartedGoingToSleep isAODAmbientWallpaperMode=", "AODAmbientWallpaperHelper", z);
                    if (z) {
                        ((StatusBarWindowControllerImpl) ((StatusBarWindowController) ((StatusBarWindowControllerStore) aODAmbientWallpaperHelper.statusBarWindowControllerStoreLazy.get()).getDefaultDisplay())).setAODAmbientWallpaperState(false);
                    }
                    ((LightRevealScrim) aODAmbientWallpaperHelper.lightRevelScrimLazy.get()).setAlpha(aODAmbientWallpaperHelper.getAlpha());
                }

                @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
                public final void onStartedWakingUp() {
                    Log.i("AODAmbientWallpaperHelper", "onStartedWakingUp");
                    AODAmbientWallpaperHelper aODAmbientWallpaperHelper = AODAmbientWallpaperHelper.this;
                    ((StatusBarWindowControllerImpl) ((StatusBarWindowController) ((StatusBarWindowControllerStore) aODAmbientWallpaperHelper.statusBarWindowControllerStoreLazy.get()).getDefaultDisplay())).setAODAmbientWallpaperState(true);
                    ((LightRevealScrim) aODAmbientWallpaperHelper.lightRevelScrimLazy.get()).setAlpha(aODAmbientWallpaperHelper.getAlpha());
                }
            });
            BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, broadcastReceiver, new IntentFilter("android.intent.action.WALLPAPER_CHANGED"), null, UserHandle.ALL, 0, null, 52);
        }
    }

    public final float getAlpha() {
        String aODLightRevealAlpha;
        float f = FactoryTest.isFactoryBinary() ? 0.1f : this.isMainWonderLandWallpaper | this.isSubWonderLandWallpaper ? 0.0f : !isAODFullScreenMode() ? 1.0f : 0.6f;
        String str = Build.TYPE;
        if (("eng".equals(str) || "userdebug".equals(str)) && (aODLightRevealAlpha = this.settingsHelper.getAODLightRevealAlpha()) != null) {
            try {
                f = Float.parseFloat(aODLightRevealAlpha);
                Log.d("AODAmbientWallpaperHelper", "alpha:" + f);
                return f;
            } catch (NumberFormatException e) {
                Log.e("AODAmbientWallpaperHelper", "cannot convert alpha to float: " + e);
            }
        }
        return f;
    }

    public final boolean isAODFullScreenAndShowing() {
        return isAODFullScreenMode() && this.settingsHelper.isAODShown();
    }

    public final boolean isAODFullScreenMode() {
        if (!LsRune.AOD_FULLSCREEN || !this.settingsHelper.isAODEnabled() || !this.settingsHelper.isAODShowLockWallpaper()) {
            return false;
        }
        boolean z = LsRune.AOD_SUB_FULLSCREEN;
        Lazy lazy = this.keyguardFoldControllerLazy;
        if (z && ((KeyguardFoldControllerImpl) ((KeyguardFoldController) lazy.get())).isFoldOpened()) {
            return false;
        }
        return !LsRune.AOD_MAIN_FULLSCREEN || ((KeyguardFoldControllerImpl) ((KeyguardFoldController) lazy.get())).isFoldOpened();
    }

    public final void updateWonderLandWallpaperState() {
        String valueOf;
        String str;
        boolean z = LsRune.AOD_SUB_DISPLAY_LOCK;
        Lazy lazy = this.keyguardFoldControllerLazy;
        UserTracker userTracker = this.userTracker;
        String str2 = "";
        if (z) {
            if (!((KeyguardFoldControllerImpl) ((KeyguardFoldController) lazy.get())).isFoldOpened()) {
                ComponentName semGetWallpaperComponent = this.wallpaperManager.semGetWallpaperComponent(18, ((UserTrackerImpl) userTracker).getUserId());
                str = String.valueOf(semGetWallpaperComponent != null ? semGetWallpaperComponent.getClassName() : null);
                this.isSubWonderLandWallpaper = "com.samsung.android.wonderland.wallpaper.service.WonderLandWallpaperReloadedSubService".equals(str);
                boolean z2 = !((KeyguardFoldControllerImpl) ((KeyguardFoldController) lazy.get())).isFoldOpened();
                boolean z3 = this.isMainWonderLandWallpaper;
                boolean z4 = this.isSubWonderLandWallpaper;
                StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("updateWonderLandWallpaperState: isFolded=", ", isMainWonderLandWallpaper=", ", isSubWonderLandWallpaper=", z2, z3);
                m.append(z4);
                m.append(" mainWallpaperClassName=");
                m.append(str2);
                m.append(", subWallpaperClassName=");
                ExifInterface$$ExternalSyntheticOutline0.m(m, str, "AODAmbientWallpaperHelper");
            }
            ComponentName semGetWallpaperComponent2 = this.wallpaperManager.semGetWallpaperComponent(6, ((UserTrackerImpl) userTracker).getUserId());
            valueOf = String.valueOf(semGetWallpaperComponent2 != null ? semGetWallpaperComponent2.getClassName() : null);
            this.isMainWonderLandWallpaper = "com.samsung.android.wonderland.wallpaper.service.WonderLandWallpaperReloadedService".equals(valueOf);
        } else if (LsRune.AOD_SUB_DISPLAY_COVER) {
            ComponentName semGetWallpaperComponent3 = this.wallpaperManager.semGetWallpaperComponent(6, ((UserTrackerImpl) userTracker).getUserId());
            valueOf = String.valueOf(semGetWallpaperComponent3 != null ? semGetWallpaperComponent3.getClassName() : null);
            this.isMainWonderLandWallpaper = "com.samsung.android.wonderland.wallpaper.service.WonderLandWallpaperReloadedService".equals(valueOf);
        } else {
            ComponentName semGetWallpaperComponent4 = this.wallpaperManager.semGetWallpaperComponent(6, ((UserTrackerImpl) userTracker).getUserId());
            valueOf = String.valueOf(semGetWallpaperComponent4 != null ? semGetWallpaperComponent4.getClassName() : null);
            this.isMainWonderLandWallpaper = "com.samsung.android.wonderland.wallpaper.service.WonderLandWallpaperReloadedService".equals(valueOf);
        }
        str2 = valueOf;
        str = "";
        boolean z22 = !((KeyguardFoldControllerImpl) ((KeyguardFoldController) lazy.get())).isFoldOpened();
        boolean z32 = this.isMainWonderLandWallpaper;
        boolean z42 = this.isSubWonderLandWallpaper;
        StringBuilder m2 = EmergencyButtonController$$ExternalSyntheticOutline0.m("updateWonderLandWallpaperState: isFolded=", ", isMainWonderLandWallpaper=", ", isSubWonderLandWallpaper=", z22, z32);
        m2.append(z42);
        m2.append(" mainWallpaperClassName=");
        m2.append(str2);
        m2.append(", subWallpaperClassName=");
        ExifInterface$$ExternalSyntheticOutline0.m(m2, str, "AODAmbientWallpaperHelper");
    }
}
