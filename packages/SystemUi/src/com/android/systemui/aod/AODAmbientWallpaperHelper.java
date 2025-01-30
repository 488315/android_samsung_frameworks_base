package com.android.systemui.aod;

import android.app.WallpaperManager;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.Log;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.view.SemWindowManager;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AODAmbientWallpaperHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean isFolded = SemWindowManager.getInstance().isFolded();
    public boolean isMainWonderLandWallpaper;
    public boolean isSubWonderLandWallpaper;
    public final Lazy keyguardViewMediatorLazy;
    public final SettingsHelper settingsHelper;
    public final Lazy statusBarWindowControllerLazy;
    public final UserTracker userTracker;
    public final WallpaperManager wallpaperManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public AODAmbientWallpaperHelper(SettingsHelper settingsHelper, Lazy lazy, WallpaperManager wallpaperManager, UserTracker userTracker, WakefulnessLifecycle wakefulnessLifecycle, Lazy lazy2) {
        this.settingsHelper = settingsHelper;
        this.keyguardViewMediatorLazy = lazy;
        this.wallpaperManager = wallpaperManager;
        this.userTracker = userTracker;
        this.statusBarWindowControllerLazy = lazy2;
        wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.aod.AODAmbientWallpaperHelper.1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                AODAmbientWallpaperHelper aODAmbientWallpaperHelper = AODAmbientWallpaperHelper.this;
                boolean isAODAmbientWallpaperMode = aODAmbientWallpaperHelper.isAODAmbientWallpaperMode();
                Log.m138d("AODAmbientWallpaperHelper", "onStartedGoingToSleep isAODAmbientWallpaperMode=" + isAODAmbientWallpaperMode);
                if (isAODAmbientWallpaperMode) {
                    aODAmbientWallpaperHelper.setAODAmbientWallpaperState(false);
                }
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                int i = AODAmbientWallpaperHelper.$r8$clinit;
                AODAmbientWallpaperHelper aODAmbientWallpaperHelper = AODAmbientWallpaperHelper.this;
                aODAmbientWallpaperHelper.getClass();
                Log.m138d("AODAmbientWallpaperHelper", "onStartedWakingUp");
                aODAmbientWallpaperHelper.setAODAmbientWallpaperState(true);
            }
        });
    }

    public final boolean isAODAmbientWallpaperMode() {
        if (!isAODFullScreenMode()) {
            if (!(this.isSubWonderLandWallpaper | this.isMainWonderLandWallpaper)) {
                return false;
            }
        }
        return true;
    }

    public final boolean isAODFullScreenMode() {
        boolean z = LsRune.AOD_FULLSCREEN;
        if (!z) {
            return false;
        }
        SettingsHelper settingsHelper = this.settingsHelper;
        if (!settingsHelper.isAODEnabled()) {
            return false;
        }
        if (z && settingsHelper.mItemLists.get("aod_show_lockscreen_wallpaper").getIntValue() != 0) {
            return !LsRune.AOD_SUB_FULLSCREEN || this.isFolded;
        }
        return false;
    }

    public final boolean isWonderLandAmbientWallpaper() {
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("isWonderLandAmbientWallpaper: isFolded=", this.isFolded, ", isMainWonderLandWallpaper=", this.isMainWonderLandWallpaper, ", isSubWonderLandWallpaper="), this.isSubWonderLandWallpaper, "AODAmbientWallpaperHelper");
        return LsRune.AOD_SUB_DISPLAY_LOCK ? this.isFolded ? this.isSubWonderLandWallpaper : this.isMainWonderLandWallpaper : this.isMainWonderLandWallpaper;
    }

    public final void setAODAmbientWallpaperState(boolean z) {
        StatusBarWindowController statusBarWindowController = (StatusBarWindowController) this.statusBarWindowControllerLazy.get();
        StatusBarWindowController.State state = statusBarWindowController.mCurrentState;
        if (state.mIsAODAmbientWallpaperWakingUp != z) {
            state.mIsAODAmbientWallpaperWakingUp = z;
            android.util.Log.d("StatusBarWindowController", "setAODAmbientWallpaperState: wakingUp=" + z);
            statusBarWindowController.apply(state, false);
        }
    }
}
