package com.android.server.display.feature.flags;

/* loaded from: classes6.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ALWAYS_ROTATE_DISPLAY_DEVICE = "com.android.server.display.feature.flags.always_rotate_display_device";
    public static final String FLAG_AUTO_BRIGHTNESS_MODES = "com.android.server.display.feature.flags.auto_brightness_modes";
    public static final String FLAG_AUTO_BRIGHTNESS_MODE_BEDTIME_WEAR = "com.android.server.display.feature.flags.auto_brightness_mode_bedtime_wear";
    public static final String FLAG_BACK_UP_SMOOTH_DISPLAY_AND_FORCE_PEAK_REFRESH_RATE = "com.android.server.display.feature.flags.back_up_smooth_display_and_force_peak_refresh_rate";
    public static final String FLAG_BASE_DENSITY_FOR_EXTERNAL_DISPLAYS = "com.android.server.display.feature.flags.base_density_for_external_displays";
    public static final String FLAG_BLOCK_AUTOBRIGHTNESS_CHANGES_ON_STYLUS_USAGE = "com.android.server.display.feature.flags.block_autobrightness_changes_on_stylus_usage";
    public static final String FLAG_BRIGHTNESS_INT_RANGE_USER_PERCEPTION = "com.android.server.display.feature.flags.brightness_int_range_user_perception";
    public static final String FLAG_BRIGHTNESS_WEAR_BEDTIME_MODE_CLAMPER = "com.android.server.display.feature.flags.brightness_wear_bedtime_mode_clamper";
    public static final String FLAG_COMMITTED_STATE_SEPARATE_EVENT = "com.android.server.display.feature.flags.committed_state_separate_event";
    public static final String FLAG_DELAY_IMPLICIT_RR_REGISTRATION_UNTIL_RR_ACCESSED = "com.android.server.display.feature.flags.delay_implicit_rr_registration_until_rr_accessed";
    public static final String FLAG_DISPLAY_CATEGORY_BUILT_IN = "com.android.server.display.feature.flags.display_category_built_in";
    public static final String FLAG_DISPLAY_LISTENER_PERFORMANCE_IMPROVEMENTS = "com.android.server.display.feature.flags.display_listener_performance_improvements";
    public static final String FLAG_DISPLAY_TOPOLOGY = "com.android.server.display.feature.flags.display_topology";
    public static final String FLAG_DOZE_BRIGHTNESS_FLOAT = "com.android.server.display.feature.flags.doze_brightness_float";
    public static final String FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_1 = "com.android.server.display.feature.flags.enable_adaptive_tone_improvements_1";
    public static final String FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_2 = "com.android.server.display.feature.flags.enable_adaptive_tone_improvements_2";
    public static final String FLAG_ENABLE_APPLY_DISPLAY_CHANGED_DURING_DISPLAY_ADDED = "com.android.server.display.feature.flags.enable_apply_display_changed_during_display_added";
    public static final String FLAG_ENABLE_BATTERY_STATS_FOR_ALL_DISPLAYS = "com.android.server.display.feature.flags.enable_battery_stats_for_all_displays";
    public static final String FLAG_ENABLE_CONNECTED_DISPLAY_ERROR_HANDLING = "com.android.server.display.feature.flags.enable_connected_display_error_handling";
    public static final String FLAG_ENABLE_DISPLAYS_REFRESH_RATES_SYNCHRONIZATION = "com.android.server.display.feature.flags.enable_displays_refresh_rates_synchronization";
    public static final String FLAG_ENABLE_DISPLAY_CONTENT_MODE_MANAGEMENT = "com.android.server.display.feature.flags.enable_display_content_mode_management";
    public static final String FLAG_ENABLE_DISPLAY_OFFLOAD = "com.android.server.display.feature.flags.enable_display_offload";
    public static final String FLAG_ENABLE_DISPLAY_RESOLUTION_RANGE_VOTING = "com.android.server.display.feature.flags.enable_display_resolution_range_voting";
    public static final String FLAG_ENABLE_GET_SUGGESTED_FRAME_RATE = "com.android.server.display.feature.flags.enable_get_suggested_frame_rate";
    public static final String FLAG_ENABLE_GET_SUPPORTED_REFRESH_RATES = "com.android.server.display.feature.flags.enable_get_supported_refresh_rates";
    public static final String FLAG_ENABLE_HAS_ARR_SUPPORT = "com.android.server.display.feature.flags.enable_has_arr_support";
    public static final String FLAG_ENABLE_HDR_OVERRIDE_PLUGIN_TYPE = "com.android.server.display.feature.flags.enable_hdr_override_plugin_type";
    public static final String FLAG_ENABLE_MODE_LIMIT_FOR_EXTERNAL_DISPLAY = "com.android.server.display.feature.flags.enable_mode_limit_for_external_display";
    public static final String FLAG_ENABLE_PEAK_REFRESH_RATE_PHYSICAL_LIMIT = "com.android.server.display.feature.flags.enable_peak_refresh_rate_physical_limit";
    public static final String FLAG_ENABLE_PIXEL_ANISOTROPY_CORRECTION = "com.android.server.display.feature.flags.enable_pixel_anisotropy_correction";
    public static final String FLAG_ENABLE_PLUGIN_MANAGER = "com.android.server.display.feature.flags.enable_plugin_manager";
    public static final String FLAG_ENABLE_PORT_IN_DISPLAY_LAYOUT = "com.android.server.display.feature.flags.enable_port_in_display_layout";
    public static final String FLAG_ENABLE_POWER_THROTTLING_CLAMPER = "com.android.server.display.feature.flags.enable_power_throttling_clamper";
    public static final String FLAG_ENABLE_RESTRICT_DISPLAY_MODES = "com.android.server.display.feature.flags.enable_restrict_display_modes";
    public static final String FLAG_ENABLE_SYNTHETIC_60HZ_MODES = "com.android.server.display.feature.flags.enable_synthetic_60hz_modes";
    public static final String FLAG_ENABLE_USER_PREFERRED_MODE_VOTE = "com.android.server.display.feature.flags.enable_user_preferred_mode_vote";
    public static final String FLAG_ENABLE_USER_REFRESH_RATE_FOR_EXTERNAL_DISPLAY = "com.android.server.display.feature.flags.enable_user_refresh_rate_for_external_display";
    public static final String FLAG_ENABLE_VSYNC_LOW_LIGHT_VOTE = "com.android.server.display.feature.flags.enable_vsync_low_light_vote";
    public static final String FLAG_ENABLE_VSYNC_LOW_POWER_VOTE = "com.android.server.display.feature.flags.enable_vsync_low_power_vote";
    public static final String FLAG_ENABLE_WAITING_CONFIRMATION_BEFORE_MIRRORING = "com.android.server.display.feature.flags.enable_waiting_confirmation_before_mirroring";
    public static final String FLAG_EVEN_DIMMER = "com.android.server.display.feature.flags.even_dimmer";
    public static final String FLAG_FAST_HDR_TRANSITIONS = "com.android.server.display.feature.flags.fast_hdr_transitions";
    public static final String FLAG_FRAMERATE_OVERRIDE_TRIGGERS_RR_CALLBACKS = "com.android.server.display.feature.flags.framerate_override_triggers_rr_callbacks";
    public static final String FLAG_HIGHEST_HDR_SDR_RATIO_API = "com.android.server.display.feature.flags.highest_hdr_sdr_ratio_api";
    public static final String FLAG_IDLE_SCREEN_CONFIG_IN_SUBSCRIBING_LIGHT_SENSOR = "com.android.server.display.feature.flags.idle_screen_config_in_subscribing_light_sensor";
    public static final String FLAG_IDLE_SCREEN_REFRESH_RATE_TIMEOUT = "com.android.server.display.feature.flags.idle_screen_refresh_rate_timeout";
    public static final String FLAG_IGNORE_APP_PREFERRED_REFRESH_RATE_REQUEST = "com.android.server.display.feature.flags.ignore_app_preferred_refresh_rate_request";
    public static final String FLAG_IS_ALWAYS_ON_AVAILABLE_API = "com.android.server.display.feature.flags.is_always_on_available_api";
    public static final String FLAG_NEW_HDR_BRIGHTNESS_MODIFIER = "com.android.server.display.feature.flags.new_hdr_brightness_modifier";
    public static final String FLAG_NORMAL_BRIGHTNESS_FOR_DOZE_PARAMETER = "com.android.server.display.feature.flags.normal_brightness_for_doze_parameter";
    public static final String FLAG_OFFLOAD_DOZE_OVERRIDE_HOLDS_WAKELOCK = "com.android.server.display.feature.flags.offload_doze_override_holds_wakelock";
    public static final String FLAG_OFFLOAD_SESSION_CANCEL_BLOCK_SCREEN_ON = "com.android.server.display.feature.flags.offload_session_cancel_block_screen_on";
    public static final String FLAG_REFACTOR_DISPLAY_POWER_CONTROLLER = "com.android.server.display.feature.flags.refactor_display_power_controller";
    public static final String FLAG_REFRESH_RATE_EVENT_FOR_FOREGROUND_APPS = "com.android.server.display.feature.flags.refresh_rate_event_for_foreground_apps";
    public static final String FLAG_RESOLUTION_BACKUP_RESTORE = "com.android.server.display.feature.flags.resolution_backup_restore";
    public static final String FLAG_SENSOR_BASED_BRIGHTNESS_THROTTLING = "com.android.server.display.feature.flags.sensor_based_brightness_throttling";
    public static final String FLAG_SEPARATE_TIMEOUTS = "com.android.server.display.feature.flags.separate_timeouts";
    public static final String FLAG_SUBSCRIBE_GRANULAR_DISPLAY_EVENTS = "com.android.server.display.feature.flags.subscribe_granular_display_events";
    public static final String FLAG_USE_FUSION_PROX_SENSOR = "com.android.server.display.feature.flags.use_fusion_prox_sensor";
    public static final String FLAG_VIRTUAL_DISPLAY_LIMIT = "com.android.server.display.feature.flags.virtual_display_limit";

    public static boolean alwaysRotateDisplayDevice() {
        return FEATURE_FLAGS.alwaysRotateDisplayDevice();
    }

    public static boolean autoBrightnessModeBedtimeWear() {
        return FEATURE_FLAGS.autoBrightnessModeBedtimeWear();
    }

    public static boolean autoBrightnessModes() {
        return FEATURE_FLAGS.autoBrightnessModes();
    }

    public static boolean backUpSmoothDisplayAndForcePeakRefreshRate() {
        return FEATURE_FLAGS.backUpSmoothDisplayAndForcePeakRefreshRate();
    }

    public static boolean baseDensityForExternalDisplays() {
        return FEATURE_FLAGS.baseDensityForExternalDisplays();
    }

    public static boolean blockAutobrightnessChangesOnStylusUsage() {
        return FEATURE_FLAGS.blockAutobrightnessChangesOnStylusUsage();
    }

    public static boolean brightnessIntRangeUserPerception() {
        return FEATURE_FLAGS.brightnessIntRangeUserPerception();
    }

    public static boolean brightnessWearBedtimeModeClamper() {
        return FEATURE_FLAGS.brightnessWearBedtimeModeClamper();
    }

    public static boolean committedStateSeparateEvent() {
        return FEATURE_FLAGS.committedStateSeparateEvent();
    }

    public static boolean delayImplicitRrRegistrationUntilRrAccessed() {
        return FEATURE_FLAGS.delayImplicitRrRegistrationUntilRrAccessed();
    }

    public static boolean displayCategoryBuiltIn() {
        return FEATURE_FLAGS.displayCategoryBuiltIn();
    }

    public static boolean displayListenerPerformanceImprovements() {
        return FEATURE_FLAGS.displayListenerPerformanceImprovements();
    }

    public static boolean displayTopology() {
        return FEATURE_FLAGS.displayTopology();
    }

    public static boolean dozeBrightnessFloat() {
        return FEATURE_FLAGS.dozeBrightnessFloat();
    }

    public static boolean enableAdaptiveToneImprovements1() {
        return FEATURE_FLAGS.enableAdaptiveToneImprovements1();
    }

    public static boolean enableAdaptiveToneImprovements2() {
        return FEATURE_FLAGS.enableAdaptiveToneImprovements2();
    }

    public static boolean enableApplyDisplayChangedDuringDisplayAdded() {
        return FEATURE_FLAGS.enableApplyDisplayChangedDuringDisplayAdded();
    }

    public static boolean enableBatteryStatsForAllDisplays() {
        return FEATURE_FLAGS.enableBatteryStatsForAllDisplays();
    }

    public static boolean enableConnectedDisplayErrorHandling() {
        return FEATURE_FLAGS.enableConnectedDisplayErrorHandling();
    }

    public static boolean enableDisplayContentModeManagement() {
        return FEATURE_FLAGS.enableDisplayContentModeManagement();
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

    public static boolean enableGetSuggestedFrameRate() {
        return FEATURE_FLAGS.enableGetSuggestedFrameRate();
    }

    public static boolean enableGetSupportedRefreshRates() {
        return FEATURE_FLAGS.enableGetSupportedRefreshRates();
    }

    public static boolean enableHasArrSupport() {
        return FEATURE_FLAGS.enableHasArrSupport();
    }

    public static boolean enableHdrOverridePluginType() {
        return FEATURE_FLAGS.enableHdrOverridePluginType();
    }

    public static boolean enableModeLimitForExternalDisplay() {
        return FEATURE_FLAGS.enableModeLimitForExternalDisplay();
    }

    public static boolean enablePeakRefreshRatePhysicalLimit() {
        return FEATURE_FLAGS.enablePeakRefreshRatePhysicalLimit();
    }

    public static boolean enablePixelAnisotropyCorrection() {
        return FEATURE_FLAGS.enablePixelAnisotropyCorrection();
    }

    public static boolean enablePluginManager() {
        return FEATURE_FLAGS.enablePluginManager();
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

    public static boolean enableUserRefreshRateForExternalDisplay() {
        return FEATURE_FLAGS.enableUserRefreshRateForExternalDisplay();
    }

    public static boolean enableVsyncLowLightVote() {
        return FEATURE_FLAGS.enableVsyncLowLightVote();
    }

    public static boolean enableVsyncLowPowerVote() {
        return FEATURE_FLAGS.enableVsyncLowPowerVote();
    }

    public static boolean enableWaitingConfirmationBeforeMirroring() {
        return FEATURE_FLAGS.enableWaitingConfirmationBeforeMirroring();
    }

    public static boolean evenDimmer() {
        return FEATURE_FLAGS.evenDimmer();
    }

    public static boolean fastHdrTransitions() {
        return FEATURE_FLAGS.fastHdrTransitions();
    }

    public static boolean framerateOverrideTriggersRrCallbacks() {
        return FEATURE_FLAGS.framerateOverrideTriggersRrCallbacks();
    }

    public static boolean highestHdrSdrRatioApi() {
        return FEATURE_FLAGS.highestHdrSdrRatioApi();
    }

    public static boolean idleScreenConfigInSubscribingLightSensor() {
        return FEATURE_FLAGS.idleScreenConfigInSubscribingLightSensor();
    }

    public static boolean idleScreenRefreshRateTimeout() {
        return FEATURE_FLAGS.idleScreenRefreshRateTimeout();
    }

    public static boolean ignoreAppPreferredRefreshRateRequest() {
        return FEATURE_FLAGS.ignoreAppPreferredRefreshRateRequest();
    }

    public static boolean isAlwaysOnAvailableApi() {
        return FEATURE_FLAGS.isAlwaysOnAvailableApi();
    }

    public static boolean newHdrBrightnessModifier() {
        return FEATURE_FLAGS.newHdrBrightnessModifier();
    }

    public static boolean normalBrightnessForDozeParameter() {
        return FEATURE_FLAGS.normalBrightnessForDozeParameter();
    }

    public static boolean offloadDozeOverrideHoldsWakelock() {
        return FEATURE_FLAGS.offloadDozeOverrideHoldsWakelock();
    }

    public static boolean offloadSessionCancelBlockScreenOn() {
        return FEATURE_FLAGS.offloadSessionCancelBlockScreenOn();
    }

    public static boolean refactorDisplayPowerController() {
        return FEATURE_FLAGS.refactorDisplayPowerController();
    }

    public static boolean refreshRateEventForForegroundApps() {
        return FEATURE_FLAGS.refreshRateEventForForegroundApps();
    }

    public static boolean resolutionBackupRestore() {
        return FEATURE_FLAGS.resolutionBackupRestore();
    }

    public static boolean sensorBasedBrightnessThrottling() {
        return FEATURE_FLAGS.sensorBasedBrightnessThrottling();
    }

    public static boolean separateTimeouts() {
        return FEATURE_FLAGS.separateTimeouts();
    }

    public static boolean subscribeGranularDisplayEvents() {
        return FEATURE_FLAGS.subscribeGranularDisplayEvents();
    }

    public static boolean useFusionProxSensor() {
        return FEATURE_FLAGS.useFusionProxSensor();
    }

    public static boolean virtualDisplayLimit() {
        return FEATURE_FLAGS.virtualDisplayLimit();
    }
}
