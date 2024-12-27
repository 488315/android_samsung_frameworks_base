package com.android.server.wm;

import android.app.ActivityManager;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.Color;
import android.util.Slog;
import android.view.WindowManager;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppCompatLetterboxOverrides {
    public final ActivityRecord mActivityRecord;
    public final AppCompatConfiguration mAppCompatConfiguration;
    public boolean mShowWallpaperForLetterboxBackground;

    public AppCompatLetterboxOverrides(
            ActivityRecord activityRecord, AppCompatConfiguration appCompatConfiguration) {
        this.mActivityRecord = activityRecord;
        this.mAppCompatConfiguration = appCompatConfiguration;
    }

    public final Color getLetterboxBackgroundColor() {
        ActivityRecord activityRecord = this.mActivityRecord;
        WindowState findMainWindow = activityRecord.findMainWindow(true);
        AppCompatConfiguration appCompatConfiguration = this.mAppCompatConfiguration;
        if (findMainWindow == null || findMainWindow.isLetterboxedForDisplayCutout()) {
            return (findMainWindow == null || findMainWindow.getStageType() == 0)
                    ? Color.valueOf(-16777216)
                    : Color.valueOf(
                            MultiWindowUtils.getRoundedCornerColor(
                                    appCompatConfiguration.mContext));
        }
        if (CoreRune.MT_APP_COMPAT_CONFIGURATION) {
            MultiTaskingAppCompatConfiguration config =
                    MultiTaskingAppCompatConfiguration.getConfig(activityRecord);
            if (config.shouldUseLetterboxBackgroundColor()) {
                return config.getLetterboxBackgroundColor();
            }
        }
        int letterboxBackgroundType = appCompatConfiguration.getLetterboxBackgroundType();
        ActivityManager.TaskDescription taskDescription = activityRecord.taskDescription;
        if (letterboxBackgroundType == 0) {
            return appCompatConfiguration.getLetterboxBackgroundColor();
        }
        if (letterboxBackgroundType != 1) {
            if (letterboxBackgroundType != 2) {
                if (letterboxBackgroundType != 3) {
                    throw new AssertionError(
                            VibrationParam$1$$ExternalSyntheticOutline0.m(
                                    letterboxBackgroundType,
                                    "Unexpected letterbox background type: "));
                }
                if (hasWallpaperBackgroundForLetterbox()) {
                    return appCompatConfiguration.getLetterboxBackgroundColor();
                }
                Slog.w(
                        "ActivityTaskManager",
                        "Wallpaper option is selected for letterbox background but blur is not"
                            + " supported by a device or not supported in the current window"
                            + " configuration or both alpha scrim and blur radius aren't provided"
                            + " so using solid color background");
            } else if (taskDescription != null
                    && taskDescription.getBackgroundColorFloating() != 0) {
                return Color.valueOf(taskDescription.getBackgroundColorFloating());
            }
        } else if (taskDescription != null && taskDescription.getBackgroundColor() != 0) {
            return Color.valueOf(taskDescription.getBackgroundColor());
        }
        return appCompatConfiguration.getLetterboxBackgroundColor();
    }

    public final int getLetterboxBackgroundType() {
        return CoreRune.MT_APP_COMPAT_CONFIGURATION
                ? MultiTaskingAppCompatConfiguration.getConfig(this.mActivityRecord)
                        .getLetterboxBackgroundType()
                : this.mAppCompatConfiguration.getLetterboxBackgroundType();
    }

    public final int getLetterboxWallpaperBlurRadiusPx() {
        return CoreRune.MT_APP_COMPAT_CONFIGURATION
                ? Math.max(
                        MultiTaskingAppCompatConfiguration.getConfig(this.mActivityRecord)
                                .getLetterboxBackgroundWallpaperBlurRadiusPx(),
                        0)
                : Math.max(
                        this.mAppCompatConfiguration.mLetterboxBackgroundWallpaperBlurRadiusPx, 0);
    }

    public final float getLetterboxWallpaperDarkScrimAlpha() {
        if (CoreRune.MT_APP_COMPAT_CONFIGURATION) {
            float letterboxBackgroundWallpaperDarkScrimAlpha =
                    MultiTaskingAppCompatConfiguration.getConfig(this.mActivityRecord)
                            .getLetterboxBackgroundWallpaperDarkScrimAlpha();
            return (letterboxBackgroundWallpaperDarkScrimAlpha
                                    < FullScreenMagnificationGestureHandler.MAX_SCALE
                            || letterboxBackgroundWallpaperDarkScrimAlpha >= 1.0f)
                    ? FullScreenMagnificationGestureHandler.MAX_SCALE
                    : letterboxBackgroundWallpaperDarkScrimAlpha;
        }
        float f = this.mAppCompatConfiguration.mLetterboxBackgroundWallpaperDarkScrimAlpha;
        return (f < FullScreenMagnificationGestureHandler.MAX_SCALE || f >= 1.0f)
                ? FullScreenMagnificationGestureHandler.MAX_SCALE
                : f;
    }

    public final boolean hasWallpaperBackgroundForLetterbox() {
        if (CoreRune.MT_APP_COMPAT_CONFIGURATION) {
            ActivityRecord activityRecord = this.mActivityRecord;
            if (MultiTaskingAppCompatConfiguration.isPresetBlurWallpaperLetterboxed(
                    activityRecord)) {
                return activityRecord.mOccludesParent || activityRecord.isRootOfTask();
            }
        }
        return this.mShowWallpaperForLetterboxBackground;
    }

    public final boolean isLetterboxWallpaperBlurSupported() {
        if (CoreRune.MT_APP_COMPAT_CONFIGURATION
                && MultiTaskingAppCompatConfiguration.getConfig(this.mActivityRecord)
                        .isLetterboxWallpaperBlurSupported()) {
            return true;
        }
        return ((WindowManager)
                        this.mAppCompatConfiguration.mContext.getSystemService(WindowManager.class))
                .isCrossWindowBlurEnabled();
    }

    public final boolean shouldHideLetterboxSurface(WindowState windowState) {
        if (windowState != null) {
            ActivityRecord activityRecord = this.mActivityRecord;
            if (!activityRecord.mOccludesParent
                    && ((!activityRecord.isRootOfTask() || windowState.mAttrs.format == -2)
                            && ((CoreRune.MT_APP_COMPAT_CONFIGURATION
                                            && MultiTaskingAppCompatConfiguration
                                                    .isPresetBlurWallpaperLetterboxed(
                                                            activityRecord))
                                    || windowState.isLetterboxedForDisplayCutout()))) {
                return true;
            }
        }
        return false;
    }
}
