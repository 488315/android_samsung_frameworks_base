package com.android.server.wm;

import android.R;
import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.Color;
import android.provider.DeviceConfig;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.wm.utils.DimenPxIntSupplier;

import com.samsung.android.rune.CoreRune;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppCompatConfiguration {
    public final AppCompatConfigurationPersister mAppCompatConfigurationPersister;
    public final Context mContext;
    public float mDefaultMinAspectRatioForUnresizableApps;
    public int mDefaultPositionForHorizontalReachability;
    public int mDefaultPositionForVerticalReachability;
    public final SynchedDeviceConfig mDeviceConfig;
    public float mFixedOrientationLetterboxAspectRatio;
    public boolean mIsAutomaticReachabilityInBookModeEnabled;
    public final boolean mIsCameraCompatSplitScreenAspectRatioEnabled;
    public boolean mIsDisplayAspectRatioEnabledForFixedOrientationLetterbox;
    public boolean mIsEducationEnabled;
    public boolean mIsHorizontalReachabilityEnabled;
    public final boolean mIsPolicyForIgnoringRequestedOrientationEnabled;
    public boolean mIsSplitScreenAspectRatioForUnresizableAppsEnabled;
    public boolean mIsVerticalReachabilityEnabled;
    public int mLetterboxActivityCornersRadius;
    public Color mLetterboxBackgroundColorOverride;
    public Integer mLetterboxBackgroundColorResourceIdOverride;
    public final int mLetterboxBackgroundType;
    public int mLetterboxBackgroundWallpaperBlurRadiusPx;
    public float mLetterboxBackgroundWallpaperDarkScrimAlpha;
    public float mLetterboxBookModePositionMultiplier;
    public float mLetterboxHorizontalPositionMultiplier;
    public float mLetterboxTabletopModePositionMultiplier;
    public float mLetterboxVerticalPositionMultiplier;
    public final MultiTaskingAppCompatConfiguration.PresetManager mPresetManager;
    public final DimenPxIntSupplier mThinLetterboxHeightPxSupplier;
    public final DimenPxIntSupplier mThinLetterboxWidthPxSupplier;
    public boolean mTranslucentLetterboxingOverrideEnabled;
    public boolean mUserAppAspectRatioFullscreenOverrideEnabled;
    public boolean mUserAppAspectRatioSettingsOverrideEnabled;
    public int mLetterboxBackgroundTypeOverride = -1;
    public boolean mIsCameraCompatTreatmentRefreshEnabled = true;
    public boolean mIsCameraCompatRefreshCycleThroughStopEnabled = true;

    public AppCompatConfiguration(
            Context context, AppCompatConfigurationPersister appCompatConfigurationPersister) {
        this.mPresetManager =
                CoreRune.MT_APP_COMPAT_CONFIGURATION
                        ? new MultiTaskingAppCompatConfiguration.PresetManager(this)
                        : null;
        this.mContext = context;
        this.mFixedOrientationLetterboxAspectRatio =
                context.getResources().getFloat(R.dimen.config_pictureInPictureMinAspectRatio);
        int integer = context.getResources().getInteger(R.integer.config_minimumScreenOffTimeout);
        if (integer != 0 && integer != 1 && integer != 2 && integer != 3) {
            integer = 0;
        }
        this.mLetterboxBackgroundType = integer;
        this.mLetterboxActivityCornersRadius =
                context.getResources().getInteger(R.integer.config_minNumVisibleRecentTasks_lowRam);
        this.mLetterboxBackgroundWallpaperBlurRadiusPx =
                context.getResources()
                        .getDimensionPixelSize(R.dimen.config_rotaryEncoderAxisScrollTickInterval);
        this.mLetterboxBackgroundWallpaperDarkScrimAlpha =
                context.getResources().getFloat(R.dimen.config_resActivitySnapshotScale);
        float f =
                context.getResources()
                        .getFloat(R.dimen.config_screenBrightnessMinimumDimAmountFloat);
        assertValidMultiplier(f, "mLetterboxHorizontalPositionMultiplier");
        this.mLetterboxHorizontalPositionMultiplier = f;
        float f2 =
                context.getResources()
                        .getFloat(R.dimen.config_screen_magnification_scaling_threshold);
        assertValidMultiplier(f2, "mLetterboxVerticalPositionMultiplier");
        this.mLetterboxVerticalPositionMultiplier = f2;
        setLetterboxBookModePositionMultiplier(
                context.getResources().getFloat(R.dimen.config_screenBrightnessDimFloat));
        setLetterboxTabletopModePositionMultiplier(
                context.getResources()
                        .getFloat(R.dimen.config_screenBrightnessSettingDefaultFloat));
        this.mIsHorizontalReachabilityEnabled =
                context.getResources().getBoolean(R.bool.config_magnification_always_on_enabled);
        this.mIsVerticalReachabilityEnabled =
                context.getResources().getBoolean(R.bool.config_maskMainBuiltInDisplayCutout);
        this.mIsAutomaticReachabilityInBookModeEnabled =
                context.getResources().getBoolean(R.bool.config_lockUiMode);
        this.mDefaultPositionForHorizontalReachability =
                readLetterboxHorizontalReachabilityPositionFromConfig(context, false);
        this.mDefaultPositionForVerticalReachability =
                readLetterboxVerticalReachabilityPositionFromConfig(context, false);
        this.mIsEducationEnabled =
                context.getResources().getBoolean(R.bool.config_lowPowerStandbyEnabledByDefault);
        this.mDefaultMinAspectRatioForUnresizableApps =
                context.getResources().getFloat(R.dimen.config_screenBrightnessDozeFloat);
        this.mIsSplitScreenAspectRatioForUnresizableAppsEnabled =
                context.getResources().getBoolean(R.bool.config_mainBuiltInDisplayIsRound);
        this.mIsDisplayAspectRatioEnabledForFixedOrientationLetterbox =
                context.getResources().getBoolean(R.bool.config_lockscreenWeatherEnabledByDefault);
        this.mIsCameraCompatSplitScreenAspectRatioEnabled =
                context.getResources().getBoolean(R.bool.config_letterboxIsEducationEnabled);
        this.mIsPolicyForIgnoringRequestedOrientationEnabled =
                context.getResources().getBoolean(R.bool.config_magnification_area);
        this.mThinLetterboxWidthPxSupplier =
                new DimenPxIntSupplier(context, R.dimen.config_screenBrightnessSettingMinimumFloat);
        this.mThinLetterboxHeightPxSupplier =
                new DimenPxIntSupplier(context, R.dimen.config_screenBrightnessSettingMaximumFloat);
        this.mAppCompatConfigurationPersister = appCompatConfigurationPersister;
        PersisterQueue persisterQueue = appCompatConfigurationPersister.mPersisterQueue;
        synchronized (persisterQueue) {
            if (!persisterQueue.mLazyTaskWriterThread.isAlive()) {
                persisterQueue.mLazyTaskWriterThread.start();
            }
        }
        SynchedDeviceConfig.SynchedDeviceConfigBuilder synchedDeviceConfigBuilder =
                new SynchedDeviceConfig.SynchedDeviceConfigBuilder(context.getMainExecutor());
        synchedDeviceConfigBuilder.addDeviceConfigEntry(
                "enable_compat_camera_treatment",
                true,
                context.getResources()
                        .getBoolean(R.bool.config_letterboxIsEnabledForTranslucentActivities));
        synchedDeviceConfigBuilder.addDeviceConfigEntry(
                "enable_display_rotation_immersive_app_compat_policy",
                true,
                context.getResources()
                        .getBoolean(R.bool.config_longPressOnPowerForAssistantSettingAvailable));
        synchedDeviceConfigBuilder.addDeviceConfigEntry(
                "allow_ignore_orientation_request", true, true);
        synchedDeviceConfigBuilder.addDeviceConfigEntry(
                "enable_compat_fake_focus",
                true,
                context.getResources()
                        .getBoolean(R.bool.config_launchCameraOnCameraLensCoverToggle));
        synchedDeviceConfigBuilder.addDeviceConfigEntry(
                "enable_letterbox_translucent_activity",
                true,
                context.getResources().getBoolean(R.bool.config_lowPowerStandbySupported));
        synchedDeviceConfigBuilder.addDeviceConfigEntry(
                "enable_app_compat_aspect_ratio_user_settings",
                true,
                context.getResources().getBoolean(R.bool.config_attachNavBarToAppDuringTransition));
        synchedDeviceConfigBuilder.addDeviceConfigEntry(
                "enable_letterbox_background_wallpaper", false, true);
        synchedDeviceConfigBuilder.addDeviceConfigEntry(
                "enable_app_compat_user_aspect_ratio_fullscreen",
                true,
                context.getResources().getBoolean(R.bool.config_assistTouchGestureEnabledDefault));
        Executor executor = synchedDeviceConfigBuilder.mExecutor;
        Map map = synchedDeviceConfigBuilder.mDeviceConfigEntries;
        String str = synchedDeviceConfigBuilder.mNamespace;
        final SynchedDeviceConfig synchedDeviceConfig = new SynchedDeviceConfig(str, executor, map);
        ((ConcurrentHashMap) map)
                .forEach(
                        new BiConsumer() { // from class:
                                           // com.android.server.wm.SynchedDeviceConfig$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                SynchedDeviceConfig synchedDeviceConfig2 = SynchedDeviceConfig.this;
                                SynchedDeviceConfig.SynchedDeviceConfigEntry
                                        synchedDeviceConfigEntry =
                                                (SynchedDeviceConfig.SynchedDeviceConfigEntry) obj2;
                                synchedDeviceConfig2.getClass();
                                boolean z = synchedDeviceConfigEntry.mDefaultValue;
                                synchedDeviceConfigEntry.mOverrideValue =
                                        DeviceConfig.getBoolean(
                                                synchedDeviceConfig2.mNamespace, (String) obj, z);
                            }
                        });
        DeviceConfig.addOnPropertiesChangedListener(str, executor, synchedDeviceConfig);
        this.mDeviceConfig = synchedDeviceConfig;
    }

    public static void assertValidMultiplier(float f, String str) {
        if (f < FullScreenMagnificationGestureHandler.MAX_SCALE || f > 1.0f) {
            throw new IllegalArgumentException("Trying to set " + str + " out of bounds: " + f);
        }
    }

    public static String letterboxBackgroundTypeToString(int i) {
        return i != 0
                ? i != 1
                        ? i != 2
                                ? i != 3
                                        ? VibrationParam$1$$ExternalSyntheticOutline0.m(
                                                i, "unknown=")
                                        : "LETTERBOX_BACKGROUND_WALLPAPER"
                                : "LETTERBOX_BACKGROUND_APP_COLOR_BACKGROUND_FLOATING"
                        : "LETTERBOX_BACKGROUND_APP_COLOR_BACKGROUND"
                : "LETTERBOX_BACKGROUND_SOLID_COLOR";
    }

    public static String letterboxHorizontalReachabilityPositionToString(int i) {
        if (i == 0) {
            return "LETTERBOX_HORIZONTAL_REACHABILITY_POSITION_LEFT";
        }
        if (i == 1) {
            return "LETTERBOX_HORIZONTAL_REACHABILITY_POSITION_CENTER";
        }
        if (i == 2) {
            return "LETTERBOX_HORIZONTAL_REACHABILITY_POSITION_RIGHT";
        }
        throw new AssertionError(
                VibrationParam$1$$ExternalSyntheticOutline0.m(
                        i, "Unexpected letterbox position type: "));
    }

    public static String letterboxVerticalReachabilityPositionToString(int i) {
        if (i == 0) {
            return "LETTERBOX_VERTICAL_REACHABILITY_POSITION_TOP";
        }
        if (i == 1) {
            return "LETTERBOX_VERTICAL_REACHABILITY_POSITION_CENTER";
        }
        if (i == 2) {
            return "LETTERBOX_VERTICAL_REACHABILITY_POSITION_BOTTOM";
        }
        throw new AssertionError(
                VibrationParam$1$$ExternalSyntheticOutline0.m(
                        i, "Unexpected letterbox position type: "));
    }

    public static int readLetterboxHorizontalReachabilityPositionFromConfig(
            Context context, boolean z) {
        int integer =
                context.getResources()
                        .getInteger(
                                z
                                        ? R.integer.config_mobile_hotspot_provision_check_period
                                        : R.integer.config_mobile_mtu);
        if (integer == 0 || integer == 1 || integer == 2) {
            return integer;
        }
        return 1;
    }

    public static int readLetterboxVerticalReachabilityPositionFromConfig(
            Context context, boolean z) {
        int integer =
                context.getResources()
                        .getInteger(
                                z
                                        ? R.integer.config_motionPredictionOffsetNanos
                                        : R.integer.config_multiuserMaxRunningUsers);
        if (integer == 0 || integer == 1 || integer == 2) {
            return integer;
        }
        return 1;
    }

    public final float getHorizontalMultiplierForReachability(boolean z) {
        int letterboxPositionForHorizontalReachability =
                this.mAppCompatConfigurationPersister.getLetterboxPositionForHorizontalReachability(
                        z);
        if (letterboxPositionForHorizontalReachability == 0) {
            return FullScreenMagnificationGestureHandler.MAX_SCALE;
        }
        if (letterboxPositionForHorizontalReachability == 1) {
            return 0.5f;
        }
        if (letterboxPositionForHorizontalReachability == 2) {
            return 1.0f;
        }
        throw new AssertionError(
                VibrationParam$1$$ExternalSyntheticOutline0.m(
                        letterboxPositionForHorizontalReachability,
                        "Unexpected letterbox position type: "));
    }

    public final Color getLetterboxBackgroundColor() {
        Color color = this.mLetterboxBackgroundColorOverride;
        if (color != null) {
            return color;
        }
        Integer num = this.mLetterboxBackgroundColorResourceIdOverride;
        return Color.valueOf(
                this.mContext
                        .getResources()
                        .getColor(
                                num != null
                                        ? num.intValue()
                                        : R.color.dim_foreground_disabled_holo_light));
    }

    public final int getLetterboxBackgroundType() {
        int i = this.mLetterboxBackgroundTypeOverride;
        if (i != -1) {
            return i;
        }
        return this.mDeviceConfig.getFlagValue("enable_letterbox_background_wallpaper")
                ? 3
                : this.mLetterboxBackgroundType;
    }

    public final float getVerticalMultiplierForReachability(boolean z) {
        int letterboxPositionForVerticalReachability =
                this.mAppCompatConfigurationPersister.getLetterboxPositionForVerticalReachability(
                        z);
        if (letterboxPositionForVerticalReachability == 0) {
            return FullScreenMagnificationGestureHandler.MAX_SCALE;
        }
        if (letterboxPositionForVerticalReachability == 1) {
            return 0.5f;
        }
        if (letterboxPositionForVerticalReachability == 2) {
            return 1.0f;
        }
        throw new AssertionError(
                VibrationParam$1$$ExternalSyntheticOutline0.m(
                        letterboxPositionForVerticalReachability,
                        "Unexpected letterbox position type: "));
    }

    public final boolean isTranslucentLetterboxingEnabled() {
        return this.mTranslucentLetterboxingOverrideEnabled
                || this.mDeviceConfig.getFlagValue("enable_letterbox_translucent_activity");
    }

    public final boolean isUserAppAspectRatioFullscreenEnabled() {
        return isUserAppAspectRatioSettingsEnabled()
                && (this.mUserAppAspectRatioFullscreenOverrideEnabled
                        || this.mDeviceConfig.getFlagValue(
                                "enable_app_compat_user_aspect_ratio_fullscreen"));
    }

    public final boolean isUserAppAspectRatioSettingsEnabled() {
        return this.mUserAppAspectRatioSettingsOverrideEnabled
                || this.mDeviceConfig.getFlagValue("enable_app_compat_aspect_ratio_user_settings");
    }

    public void setLetterboxBookModePositionMultiplier(float f) {
        assertValidMultiplier(f, "mLetterboxBookModePositionMultiplier");
        this.mLetterboxBookModePositionMultiplier = f;
    }

    public void setLetterboxTabletopModePositionMultiplier(float f) {
        assertValidMultiplier(f, "mLetterboxTabletopModePositionMultiplier");
        this.mLetterboxTabletopModePositionMultiplier = f;
    }
}
