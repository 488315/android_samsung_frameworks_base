package com.android.server.display.feature.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ALWAYS_ROTATE_DISPLAY_DEVICE = "com.android.server.display.feature.flags.always_rotate_display_device";
    public static final String FLAG_AUTO_BRIGHTNESS_MODES = "com.android.server.display.feature.flags.auto_brightness_modes";
    public static final String FLAG_BACK_UP_SMOOTH_DISPLAY_AND_FORCE_PEAK_REFRESH_RATE = "com.android.server.display.feature.flags.back_up_smooth_display_and_force_peak_refresh_rate";
    public static final String FLAG_BRIGHTNESS_INT_RANGE_USER_PERCEPTION = "com.android.server.display.feature.flags.brightness_int_range_user_perception";
    public static final String FLAG_BRIGHTNESS_WEAR_BEDTIME_MODE_CLAMPER = "com.android.server.display.feature.flags.brightness_wear_bedtime_mode_clamper";
    public static final String FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_1 = "com.android.server.display.feature.flags.enable_adaptive_tone_improvements_1";
    public static final String FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_2 = "com.android.server.display.feature.flags.enable_adaptive_tone_improvements_2";
    public static final String FLAG_ENABLE_CONNECTED_DISPLAY_ERROR_HANDLING = "com.android.server.display.feature.flags.enable_connected_display_error_handling";
    public static final String FLAG_ENABLE_CONNECTED_DISPLAY_MANAGEMENT = "com.android.server.display.feature.flags.enable_connected_display_management";
    public static final String FLAG_ENABLE_DISPLAYS_REFRESH_RATES_SYNCHRONIZATION = "com.android.server.display.feature.flags.enable_displays_refresh_rates_synchronization";
    public static final String FLAG_ENABLE_DISPLAY_OFFLOAD = "com.android.server.display.feature.flags.enable_display_offload";
    public static final String FLAG_ENABLE_DISPLAY_RESOLUTION_RANGE_VOTING = "com.android.server.display.feature.flags.enable_display_resolution_range_voting";
    public static final String FLAG_ENABLE_HDR_CLAMPER = "com.android.server.display.feature.flags.enable_hdr_clamper";
    public static final String FLAG_ENABLE_MODE_LIMIT_FOR_EXTERNAL_DISPLAY = "com.android.server.display.feature.flags.enable_mode_limit_for_external_display";
    public static final String FLAG_ENABLE_NBM_CONTROLLER = "com.android.server.display.feature.flags.enable_nbm_controller";
    public static final String FLAG_ENABLE_PEAK_REFRESH_RATE_PHYSICAL_LIMIT = "com.android.server.display.feature.flags.enable_peak_refresh_rate_physical_limit";
    public static final String FLAG_ENABLE_PIXEL_ANISOTROPY_CORRECTION = "com.android.server.display.feature.flags.enable_pixel_anisotropy_correction";
    public static final String FLAG_ENABLE_PORT_IN_DISPLAY_LAYOUT = "com.android.server.display.feature.flags.enable_port_in_display_layout";
    public static final String FLAG_ENABLE_POWER_THROTTLING_CLAMPER = "com.android.server.display.feature.flags.enable_power_throttling_clamper";
    public static final String FLAG_ENABLE_RESTRICT_DISPLAY_MODES = "com.android.server.display.feature.flags.enable_restrict_display_modes";
    public static final String FLAG_ENABLE_SYNTHETIC_60HZ_MODES = "com.android.server.display.feature.flags.enable_synthetic_60hz_modes";
    public static final String FLAG_ENABLE_USER_PREFERRED_MODE_VOTE = "com.android.server.display.feature.flags.enable_user_preferred_mode_vote";
    public static final String FLAG_ENABLE_VSYNC_LOW_LIGHT_VOTE = "com.android.server.display.feature.flags.enable_vsync_low_light_vote";
    public static final String FLAG_ENABLE_VSYNC_LOW_POWER_VOTE = "com.android.server.display.feature.flags.enable_vsync_low_power_vote";
    public static final String FLAG_EVEN_DIMMER = "com.android.server.display.feature.flags.even_dimmer";
    public static final String FLAG_FAST_HDR_TRANSITIONS = "com.android.server.display.feature.flags.fast_hdr_transitions";
    public static final String FLAG_IDLE_SCREEN_REFRESH_RATE_TIMEOUT = "com.android.server.display.feature.flags.idle_screen_refresh_rate_timeout";
    public static final String FLAG_IGNORE_APP_PREFERRED_REFRESH_RATE_REQUEST = "com.android.server.display.feature.flags.ignore_app_preferred_refresh_rate_request";
    public static final String FLAG_OFFLOAD_CONTROLS_DOZE_AUTO_BRIGHTNESS = "com.android.server.display.feature.flags.offload_controls_doze_auto_brightness";
    public static final String FLAG_OFFLOAD_DOZE_OVERRIDE_HOLDS_WAKELOCK = "com.android.server.display.feature.flags.offload_doze_override_holds_wakelock";
    public static final String FLAG_REFACTOR_DISPLAY_POWER_CONTROLLER = "com.android.server.display.feature.flags.refactor_display_power_controller";
    public static final String FLAG_REFRESH_RATE_VOTING_TELEMETRY = "com.android.server.display.feature.flags.refresh_rate_voting_telemetry";
    public static final String FLAG_RESOLUTION_BACKUP_RESTORE = "com.android.server.display.feature.flags.resolution_backup_restore";
    public static final String FLAG_SENSOR_BASED_BRIGHTNESS_THROTTLING = "com.android.server.display.feature.flags.sensor_based_brightness_throttling";
    public static final String FLAG_USE_FUSION_PROX_SENSOR = "com.android.server.display.feature.flags.use_fusion_prox_sensor";

    public static boolean alwaysRotateDisplayDevice() {
        return FEATURE_FLAGS.alwaysRotateDisplayDevice();
    }

    public static boolean autoBrightnessModes() {
        return FEATURE_FLAGS.autoBrightnessModes();
    }

    public static boolean backUpSmoothDisplayAndForcePeakRefreshRate() {
        return FEATURE_FLAGS.backUpSmoothDisplayAndForcePeakRefreshRate();
    }

    public static boolean brightnessIntRangeUserPerception() {
        return FEATURE_FLAGS.brightnessIntRangeUserPerception();
    }

    public static boolean brightnessWearBedtimeModeClamper() {
        return FEATURE_FLAGS.brightnessWearBedtimeModeClamper();
    }

    public static boolean enableAdaptiveToneImprovements1() {
        return FEATURE_FLAGS.enableAdaptiveToneImprovements1();
    }

    public static boolean enableAdaptiveToneImprovements2() {
        return FEATURE_FLAGS.enableAdaptiveToneImprovements2();
    }

    public static boolean enableConnectedDisplayErrorHandling() {
        return FEATURE_FLAGS.enableConnectedDisplayErrorHandling();
    }

    public static boolean enableConnectedDisplayManagement() {
        return FEATURE_FLAGS.enableConnectedDisplayManagement();
    }

    public static boolean enableDisplayOffload() {
        return FEATURE_FLAGS.enableDisplayOffload();
    }

    public static boolean enableDisplayResolutionRangeVoting() {
        return FEATURE_FLAGS.enableDisplayResolutionRangeVoting();
    }

    public static boolean enableDisplaysRefreshRatesSynchronization() {
        return FEATURE_FLAGS.enableDisplaysRefreshRatesSynchronization();
    }

    public static boolean enableHdrClamper() {
        return FEATURE_FLAGS.enableHdrClamper();
    }

    public static boolean enableModeLimitForExternalDisplay() {
        return FEATURE_FLAGS.enableModeLimitForExternalDisplay();
    }

    public static boolean enableNbmController() {
        return FEATURE_FLAGS.enableNbmController();
    }

    public static boolean enablePeakRefreshRatePhysicalLimit() {
        return FEATURE_FLAGS.enablePeakRefreshRatePhysicalLimit();
    }

    public static boolean enablePixelAnisotropyCorrection() {
        return FEATURE_FLAGS.enablePixelAnisotropyCorrection();
    }

    public static boolean enablePortInDisplayLayout() {
        return FEATURE_FLAGS.enablePortInDisplayLayout();
    }

    public static boolean enablePowerThrottlingClamper() {
        return FEATURE_FLAGS.enablePowerThrottlingClamper();
    }

    public static boolean enableRestrictDisplayModes() {
        return FEATURE_FLAGS.enableRestrictDisplayModes();
    }

    public static boolean enableSynthetic60hzModes() {
        return FEATURE_FLAGS.enableSynthetic60hzModes();
    }

    public static boolean enableUserPreferredModeVote() {
        return FEATURE_FLAGS.enableUserPreferredModeVote();
    }

    public static boolean enableVsyncLowLightVote() {
        return FEATURE_FLAGS.enableVsyncLowLightVote();
    }

    public static boolean enableVsyncLowPowerVote() {
        return FEATURE_FLAGS.enableVsyncLowPowerVote();
    }

    public static boolean evenDimmer() {
        return FEATURE_FLAGS.evenDimmer();
    }

    public static boolean fastHdrTransitions() {
        return FEATURE_FLAGS.fastHdrTransitions();
    }

    public static boolean idleScreenRefreshRateTimeout() {
        return FEATURE_FLAGS.idleScreenRefreshRateTimeout();
    }

    public static boolean ignoreAppPreferredRefreshRateRequest() {
        return FEATURE_FLAGS.ignoreAppPreferredRefreshRateRequest();
    }

    public static boolean offloadControlsDozeAutoBrightness() {
        return FEATURE_FLAGS.offloadControlsDozeAutoBrightness();
    }

    public static boolean offloadDozeOverrideHoldsWakelock() {
        return FEATURE_FLAGS.offloadDozeOverrideHoldsWakelock();
    }

    public static boolean refactorDisplayPowerController() {
        return FEATURE_FLAGS.refactorDisplayPowerController();
    }

    public static boolean refreshRateVotingTelemetry() {
        return FEATURE_FLAGS.refreshRateVotingTelemetry();
    }

    public static boolean resolutionBackupRestore() {
        return FEATURE_FLAGS.resolutionBackupRestore();
    }

    public static boolean sensorBasedBrightnessThrottling() {
        return FEATURE_FLAGS.sensorBasedBrightnessThrottling();
    }

    public static boolean useFusionProxSensor() {
        return FEATURE_FLAGS.useFusionProxSensor();
    }
}
