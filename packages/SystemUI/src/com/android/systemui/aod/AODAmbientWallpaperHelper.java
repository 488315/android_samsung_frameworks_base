package com.android.systemui.aod;

import android.app.WallpaperManager;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.Log;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.view.SemWindowManager;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class AODAmbientWallpaperHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy keyguardFoldControllerLazy;
    public final Lazy keyguardViewMediatorLazy;
    private final SettingsHelper settingsHelper;
    public final Lazy statusBarWindowControllerLazy;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public AODAmbientWallpaperHelper(SettingsHelper settingsHelper, Lazy lazy, WallpaperManager wallpaperManager, UserTracker userTracker, WakefulnessLifecycle wakefulnessLifecycle, Lazy lazy2, Lazy lazy3) {
        this.settingsHelper = settingsHelper;
        this.keyguardViewMediatorLazy = lazy;
        this.statusBarWindowControllerLazy = lazy2;
        this.keyguardFoldControllerLazy = lazy3;
        SemWindowManager.getInstance().isFolded();
        wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.aod.AODAmbientWallpaperHelper.1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                AODAmbientWallpaperHelper aODAmbientWallpaperHelper = AODAmbientWallpaperHelper.this;
                boolean isAODFullScreenMode = aODAmbientWallpaperHelper.isAODFullScreenMode();
                Log.d("AODAmbientWallpaperHelper", "onStartedGoingToSleep isAODAmbientWallpaperMode=" + isAODFullScreenMode);
                if (isAODFullScreenMode) {
                    ((StatusBarWindowController) aODAmbientWallpaperHelper.statusBarWindowControllerLazy.get()).setAODAmbientWallpaperState(false);
                }
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                int i = AODAmbientWallpaperHelper.$r8$clinit;
                AODAmbientWallpaperHelper aODAmbientWallpaperHelper = AODAmbientWallpaperHelper.this;
                aODAmbientWallpaperHelper.getClass();
                Log.d("AODAmbientWallpaperHelper", "onStartedWakingUp");
                ((StatusBarWindowController) aODAmbientWallpaperHelper.statusBarWindowControllerLazy.get()).setAODAmbientWallpaperState(true);
            }
        });
    }

    public final boolean isAODFullScreenAndShowing() {
        return isAODFullScreenMode() && this.settingsHelper.isAODShown();
    }

    public final boolean isAODFullScreenMode() {
        return LsRune.AOD_FULLSCREEN && this.settingsHelper.isAODEnabled() && this.settingsHelper.isAODShowLockWallpaper() && !(LsRune.AOD_SUB_FULLSCREEN && ((KeyguardFoldControllerImpl) ((KeyguardFoldController) this.keyguardFoldControllerLazy.get())).isFoldOpened());
    }
}
