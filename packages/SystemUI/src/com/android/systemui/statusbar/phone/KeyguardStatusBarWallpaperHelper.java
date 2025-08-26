package com.android.systemui.statusbar.phone;

import android.app.SemWallpaperColors;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.widget.SystemUIWidgetCallback;

/* loaded from: classes3.dex */
public final class KeyguardStatusBarWallpaperHelper implements WakefulnessLifecycle.Observer, SystemUIWidgetCallback {
    public int fontColorFromWallPaper;
    public int fontColorType;
    public float intensity;
    public final KeyguardWallpaper keyguardWallpaper;
    public KeyguardStatusBarWallpaperListener listener;
    private final SettingsHelper settingsHelper;
    public final WakefulnessLifecycle wakefulnessLifecycle;
    public final WallpaperEventNotifier wallpaperEventNotifier;

    public KeyguardStatusBarWallpaperHelper(WakefulnessLifecycle wakefulnessLifecycle, WallpaperEventNotifier wallpaperEventNotifier, SettingsHelper settingsHelper, KeyguardWallpaper keyguardWallpaper) {
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.wallpaperEventNotifier = wallpaperEventNotifier;
        this.settingsHelper = settingsHelper;
        this.keyguardWallpaper = keyguardWallpaper;
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onFinishedWakingUp() {
        updateIconsAndTextColors();
    }

    public final void updateIconsAndTextColors() {
        this.fontColorType = 0;
        SemWallpaperColors.Item hint = ((KeyguardWallpaperController) this.keyguardWallpaper).getHint(16L, false);
        int fontColor = hint.getFontColor();
        this.fontColorType = fontColor;
        this.fontColorFromWallPaper = fontColor != 1 ? fontColor != 2 ? -301989889 : hint.getFontColorRgb() : DarkIconDispatcher.DEFAULT_DARK_ICON_TINT;
        this.intensity = this.fontColorType == 1 ? 1.0f : 0.0f;
        KeyguardStatusBarWallpaperListener keyguardStatusBarWallpaperListener = this.listener;
        if (keyguardStatusBarWallpaperListener != null) {
            keyguardStatusBarWallpaperListener.onWallpaperUpdated();
        }
    }

    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        if ((j & 16) == 0 && !this.settingsHelper.isOpenThemeLockWallpaper()) {
            return;
        }
        updateIconsAndTextColors();
    }
}
