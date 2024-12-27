package com.android.server.wm;

import android.content.res.Configuration;
import android.graphics.Color;
import android.view.RoundedCorners;
import android.view.SemBlurInfo;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class MultiTaskingAppCompatConfiguration {
    public static final boolean BLUR_SUPPORTED = CoreRune.FW_WINDOW_BLUR_SUPPORTED;
    public final AppCompatConfiguration mAppCompatConfiguration;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class BlackLetterboxConfig extends MultiTaskingAppCompatConfiguration {
        public static final Color BLACK_COLOR = Color.valueOf(-16777216);
        public final DisplayContent mDisplay;
        public int mLetterboxActivityCornersRadius;

        public BlackLetterboxConfig(DisplayContent displayContent) {
            super(displayContent.mWmService.mAppCompatConfiguration);
            this.mDisplay = displayContent;
            onConfigurationChanged(displayContent.getConfiguration());
        }

        @Override // com.android.server.wm.MultiTaskingAppCompatConfiguration
        public final int getLetterboxActivityCornersRadius() {
            return this.mLetterboxActivityCornersRadius;
        }

        @Override // com.android.server.wm.MultiTaskingAppCompatConfiguration
        public final Color getLetterboxBackgroundColor() {
            return BLACK_COLOR;
        }

        @Override // com.android.server.wm.MultiTaskingAppCompatConfiguration
        public int getLetterboxBackgroundType() {
            return 0;
        }

        @Override // com.android.server.wm.MultiTaskingAppCompatConfiguration
        public int getLetterboxBackgroundWallpaperBlurRadiusPx() {
            return 0;
        }

        @Override // com.android.server.wm.MultiTaskingAppCompatConfiguration
        public final float getLetterboxBackgroundWallpaperDarkScrimAlpha() {
            return FullScreenMagnificationGestureHandler.MAX_SCALE;
        }

        @Override // com.android.server.wm.MultiTaskingAppCompatConfiguration
        public final boolean isPresetConfig() {
            return true;
        }

        public void onConfigurationChanged(Configuration configuration) {
            DisplayContent displayContent = this.mDisplay;
            this.mLetterboxActivityCornersRadius = RoundedCorners.getRoundedCornerRadius(displayContent.mDisplayPolicy.mUiContext.getResources(), displayContent.mCurrentUniqueDisplayId);
        }

        @Override // com.android.server.wm.MultiTaskingAppCompatConfiguration
        public final boolean shouldUseLetterboxBackgroundColor() {
            int letterboxBackgroundType = getLetterboxBackgroundType();
            return letterboxBackgroundType == 0 || letterboxBackgroundType == 3;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BlurWallpaperLetterboxConfig extends BlackLetterboxConfig {
        public SemBlurInfo.ColorCurve mBlurColorCurve;
        public int mBlurRadiusPx;
        public CapturedLetterbox mCapturedLetterbox;

        @Override // com.android.server.wm.MultiTaskingAppCompatConfiguration
        public final void destroy() {
            CapturedLetterbox capturedLetterbox = this.mCapturedLetterbox;
            if (capturedLetterbox != null) {
                capturedLetterbox.mShouldUseCapturedLetterbox = false;
                capturedLetterbox.removeCapturedLetterboxSurface();
            }
        }

        @Override // com.android.server.wm.MultiTaskingAppCompatConfiguration.BlackLetterboxConfig, com.android.server.wm.MultiTaskingAppCompatConfiguration
        public final int getLetterboxBackgroundType() {
            return isLetterboxWallpaperBlurSupported() ? 3 : 0;
        }

        @Override // com.android.server.wm.MultiTaskingAppCompatConfiguration
        public final SemBlurInfo.ColorCurve getLetterboxBackgroundWallpaperBlurColorCurve() {
            if (isLetterboxWallpaperBlurSupported()) {
                return this.mBlurColorCurve;
            }
            return null;
        }

        @Override // com.android.server.wm.MultiTaskingAppCompatConfiguration.BlackLetterboxConfig, com.android.server.wm.MultiTaskingAppCompatConfiguration
        public final int getLetterboxBackgroundWallpaperBlurRadiusPx() {
            return this.mBlurRadiusPx;
        }

        @Override // com.android.server.wm.MultiTaskingAppCompatConfiguration
        public final boolean hasCapturedLetterboxSurface() {
            CapturedLetterbox capturedLetterbox = this.mCapturedLetterbox;
            return (capturedLetterbox == null || capturedLetterbox.mCapturedLetterboxSurface == null) ? false : true;
        }

        @Override // com.android.server.wm.MultiTaskingAppCompatConfiguration
        public final boolean isLetterboxWallpaperBlurSupported() {
            return MultiTaskingAppCompatConfiguration.BLUR_SUPPORTED && !this.mDisplay.mAtmService.mMultiTaskingController.mIsMinimalBatteryUse;
        }

        @Override // com.android.server.wm.MultiTaskingAppCompatConfiguration
        public final void onAdjustWallpaperWindows(boolean z) {
            if (!isLetterboxWallpaperBlurSupported()) {
                z = false;
            }
            if (this.mCapturedLetterbox == null) {
                if (!z) {
                    return;
                } else {
                    this.mCapturedLetterbox = new CapturedLetterbox(this.mDisplay);
                }
            }
            CapturedLetterbox capturedLetterbox = this.mCapturedLetterbox;
            if (z && capturedLetterbox.mShouldUseCapturedLetterbox) {
                return;
            }
            capturedLetterbox.mShouldUseCapturedLetterbox = z;
            capturedLetterbox.removeCapturedLetterboxSurface();
            if (z) {
                DisplayContent displayContent = capturedLetterbox.mDisplay;
                WindowManagerService.H h = displayContent.mWmService.mH;
                CapturedLetterbox$$ExternalSyntheticLambda0 capturedLetterbox$$ExternalSyntheticLambda0 = capturedLetterbox.mShowCapturedLetterboxRunnable;
                h.removeCallbacks(capturedLetterbox$$ExternalSyntheticLambda0);
                displayContent.mWmService.mH.postDelayed(capturedLetterbox$$ExternalSyntheticLambda0, 5000L);
            }
        }

        @Override // com.android.server.wm.MultiTaskingAppCompatConfiguration.BlackLetterboxConfig
        public final void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            float[] blurPresetAttrs = SemBlurInfo.Builder.getBlurPresetAttrs(configuration.isNightModeActive() ? 16 : 13);
            this.mBlurRadiusPx = (int) blurPresetAttrs[0];
            this.mBlurColorCurve = new SemBlurInfo.ColorCurve(blurPresetAttrs[1], blurPresetAttrs[2], blurPresetAttrs[3], blurPresetAttrs[4], blurPresetAttrs[5], blurPresetAttrs[6]);
            if (this.mCapturedLetterbox != null) {
                if (!isLetterboxWallpaperBlurSupported()) {
                    CapturedLetterbox capturedLetterbox = this.mCapturedLetterbox;
                    capturedLetterbox.mShouldUseCapturedLetterbox = false;
                    capturedLetterbox.removeCapturedLetterboxSurface();
                    return;
                }
                CapturedLetterbox capturedLetterbox2 = this.mCapturedLetterbox;
                boolean z = capturedLetterbox2.mShouldUseCapturedLetterbox;
                capturedLetterbox2.mShouldUseCapturedLetterbox = z;
                capturedLetterbox2.removeCapturedLetterboxSurface();
                if (z) {
                    DisplayContent displayContent = capturedLetterbox2.mDisplay;
                    WindowManagerService.H h = displayContent.mWmService.mH;
                    CapturedLetterbox$$ExternalSyntheticLambda0 capturedLetterbox$$ExternalSyntheticLambda0 = capturedLetterbox2.mShowCapturedLetterboxRunnable;
                    h.removeCallbacks(capturedLetterbox$$ExternalSyntheticLambda0);
                    displayContent.mWmService.mH.postDelayed(capturedLetterbox$$ExternalSyntheticLambda0, 5000L);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public @interface Preset {
        public static final int DEFAULT;

        static {
            DEFAULT = CoreRune.MT_APP_COMPAT_LARGE_SCREEN ? MultiTaskingAppCompatConfiguration.BLUR_SUPPORTED ? 1 : 2 : 0;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PresetManager {
        public final MultiTaskingAppCompatConfiguration mDeviceConfig;
        public int mPreset = Preset.DEFAULT;

        public PresetManager(AppCompatConfiguration appCompatConfiguration) {
            this.mDeviceConfig = new MultiTaskingAppCompatConfiguration(appCompatConfiguration);
        }

        public static String presetToString(int i) {
            return i != 0 ? i != 1 ? i != 2 ? BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "Unknown(", ")") : "BLACK_LETTERBOX" : "BLUR_WALLPAPER_LETTERBOX" : "DEVICE";
        }
    }

    public MultiTaskingAppCompatConfiguration(AppCompatConfiguration appCompatConfiguration) {
        this.mAppCompatConfiguration = appCompatConfiguration;
    }

    public static MultiTaskingAppCompatConfiguration getConfig(ActivityRecord activityRecord) {
        PresetManager presetManager = activityRecord.mWmService.mAppCompatConfiguration.mPresetManager;
        MultiTaskingAppCompatConfiguration multiTaskingAppCompatConfiguration = presetManager.mDeviceConfig;
        if (presetManager.mPreset == 0 || activityRecord.mDisplayContent == null || !MultiTaskingAppCompatController.inAllowedWindowingMode(activityRecord) || !activityRecord.areBoundsLetterboxed()) {
            return multiTaskingAppCompatConfiguration;
        }
        DisplayContent displayContent = activityRecord.mDisplayContent;
        if (displayContent.mMultiTaskingAppCompatConfiguration == null) {
            int i = presetManager.mPreset;
            if (i == 1) {
                displayContent.mMultiTaskingAppCompatConfiguration = new BlurWallpaperLetterboxConfig(displayContent);
            } else if (i == 2) {
                displayContent.mMultiTaskingAppCompatConfiguration = new BlackLetterboxConfig(displayContent);
            } else {
                UiModeManagerService$13$$ExternalSyntheticOutline0.m(new StringBuilder("AppCompatConfigurationPreset("), presetManager.mPreset, ") is unknown.", "MultiTaskingAppCompat");
            }
        }
        BlackLetterboxConfig blackLetterboxConfig = displayContent.mMultiTaskingAppCompatConfiguration;
        return blackLetterboxConfig != null ? blackLetterboxConfig : multiTaskingAppCompatConfiguration;
    }

    public static boolean isPresetBlurWallpaperLetterboxed(ActivityRecord activityRecord) {
        return isPresetWallpaperLetterboxed(activityRecord) && getConfig(activityRecord).isLetterboxWallpaperBlurSupported();
    }

    public static boolean isPresetLetterboxed(ActivityRecord activityRecord) {
        return activityRecord != null && getConfig(activityRecord).isPresetConfig();
    }

    public static boolean isPresetWallpaperLetterboxed(ActivityRecord activityRecord) {
        return isPresetLetterboxed(activityRecord) && getConfig(activityRecord).getLetterboxBackgroundType() == 3;
    }

    public void destroy() {
    }

    public int getLetterboxActivityCornersRadius() {
        return this.mAppCompatConfiguration.mLetterboxActivityCornersRadius;
    }

    public Color getLetterboxBackgroundColor() {
        return this.mAppCompatConfiguration.getLetterboxBackgroundColor();
    }

    public int getLetterboxBackgroundType() {
        return this.mAppCompatConfiguration.getLetterboxBackgroundType();
    }

    public SemBlurInfo.ColorCurve getLetterboxBackgroundWallpaperBlurColorCurve() {
        return null;
    }

    public int getLetterboxBackgroundWallpaperBlurRadiusPx() {
        return this.mAppCompatConfiguration.mLetterboxBackgroundWallpaperBlurRadiusPx;
    }

    public float getLetterboxBackgroundWallpaperDarkScrimAlpha() {
        return this.mAppCompatConfiguration.mLetterboxBackgroundWallpaperDarkScrimAlpha;
    }

    public boolean hasCapturedLetterboxSurface() {
        return false;
    }

    public boolean isLetterboxWallpaperBlurSupported() {
        return false;
    }

    public boolean isPresetConfig() {
        return false;
    }

    public void onAdjustWallpaperWindows(boolean z) {
    }

    public boolean shouldUseLetterboxBackgroundColor() {
        return false;
    }
}
