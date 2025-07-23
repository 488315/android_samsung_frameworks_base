package com.android.server.display.feature.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes6.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ALWAYS_ROTATE_DISPLAY_DEVICE, Flags.FLAG_AUTO_BRIGHTNESS_MODE_BEDTIME_WEAR, Flags.FLAG_AUTO_BRIGHTNESS_MODES, Flags.FLAG_BACK_UP_SMOOTH_DISPLAY_AND_FORCE_PEAK_REFRESH_RATE, Flags.FLAG_BASE_DENSITY_FOR_EXTERNAL_DISPLAYS, Flags.FLAG_BLOCK_AUTOBRIGHTNESS_CHANGES_ON_STYLUS_USAGE, Flags.FLAG_BRIGHTNESS_INT_RANGE_USER_PERCEPTION, Flags.FLAG_BRIGHTNESS_WEAR_BEDTIME_MODE_CLAMPER, Flags.FLAG_COMMITTED_STATE_SEPARATE_EVENT, Flags.FLAG_DELAY_IMPLICIT_RR_REGISTRATION_UNTIL_RR_ACCESSED, Flags.FLAG_DISPLAY_CATEGORY_BUILT_IN, Flags.FLAG_DISPLAY_LISTENER_PERFORMANCE_IMPROVEMENTS, Flags.FLAG_DISPLAY_TOPOLOGY, Flags.FLAG_DOZE_BRIGHTNESS_FLOAT, Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_1, Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_2, Flags.FLAG_ENABLE_APPLY_DISPLAY_CHANGED_DURING_DISPLAY_ADDED, Flags.FLAG_ENABLE_BATTERY_STATS_FOR_ALL_DISPLAYS, Flags.FLAG_ENABLE_CONNECTED_DISPLAY_ERROR_HANDLING, Flags.FLAG_ENABLE_DISPLAY_CONTENT_MODE_MANAGEMENT, Flags.FLAG_ENABLE_DISPLAY_OFFLOAD, Flags.FLAG_ENABLE_DISPLAY_RESOLUTION_RANGE_VOTING, Flags.FLAG_ENABLE_DISPLAYS_REFRESH_RATES_SYNCHRONIZATION, Flags.FLAG_ENABLE_GET_SUGGESTED_FRAME_RATE, Flags.FLAG_ENABLE_GET_SUPPORTED_REFRESH_RATES, Flags.FLAG_ENABLE_HAS_ARR_SUPPORT, Flags.FLAG_ENABLE_HDR_OVERRIDE_PLUGIN_TYPE, Flags.FLAG_ENABLE_MODE_LIMIT_FOR_EXTERNAL_DISPLAY, Flags.FLAG_ENABLE_PEAK_REFRESH_RATE_PHYSICAL_LIMIT, Flags.FLAG_ENABLE_PIXEL_ANISOTROPY_CORRECTION, Flags.FLAG_ENABLE_PLUGIN_MANAGER, Flags.FLAG_ENABLE_PORT_IN_DISPLAY_LAYOUT, Flags.FLAG_ENABLE_POWER_THROTTLING_CLAMPER, Flags.FLAG_ENABLE_RESTRICT_DISPLAY_MODES, Flags.FLAG_ENABLE_SYNTHETIC_60HZ_MODES, Flags.FLAG_ENABLE_USER_PREFERRED_MODE_VOTE, Flags.FLAG_ENABLE_USER_REFRESH_RATE_FOR_EXTERNAL_DISPLAY, Flags.FLAG_ENABLE_VSYNC_LOW_LIGHT_VOTE, Flags.FLAG_ENABLE_VSYNC_LOW_POWER_VOTE, Flags.FLAG_ENABLE_WAITING_CONFIRMATION_BEFORE_MIRRORING, Flags.FLAG_EVEN_DIMMER, Flags.FLAG_FAST_HDR_TRANSITIONS, Flags.FLAG_FRAMERATE_OVERRIDE_TRIGGERS_RR_CALLBACKS, Flags.FLAG_HIGHEST_HDR_SDR_RATIO_API, Flags.FLAG_IDLE_SCREEN_CONFIG_IN_SUBSCRIBING_LIGHT_SENSOR, Flags.FLAG_IDLE_SCREEN_REFRESH_RATE_TIMEOUT, Flags.FLAG_IGNORE_APP_PREFERRED_REFRESH_RATE_REQUEST, Flags.FLAG_IS_ALWAYS_ON_AVAILABLE_API, Flags.FLAG_NEW_HDR_BRIGHTNESS_MODIFIER, Flags.FLAG_NORMAL_BRIGHTNESS_FOR_DOZE_PARAMETER, Flags.FLAG_OFFLOAD_DOZE_OVERRIDE_HOLDS_WAKELOCK, Flags.FLAG_OFFLOAD_SESSION_CANCEL_BLOCK_SCREEN_ON, Flags.FLAG_REFACTOR_DISPLAY_POWER_CONTROLLER, Flags.FLAG_REFRESH_RATE_EVENT_FOR_FOREGROUND_APPS, Flags.FLAG_RESOLUTION_BACKUP_RESTORE, Flags.FLAG_SENSOR_BASED_BRIGHTNESS_THROTTLING, Flags.FLAG_SEPARATE_TIMEOUTS, Flags.FLAG_SUBSCRIBE_GRANULAR_DISPLAY_EVENTS, Flags.FLAG_USE_FUSION_PROX_SENSOR, Flags.FLAG_VIRTUAL_DISPLAY_LIMIT, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean alwaysRotateDisplayDevice() {
        return getValue(Flags.FLAG_ALWAYS_ROTATE_DISPLAY_DEVICE, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).alwaysRotateDisplayDevice();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean autoBrightnessModeBedtimeWear() {
        return getValue(Flags.FLAG_AUTO_BRIGHTNESS_MODE_BEDTIME_WEAR, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).autoBrightnessModeBedtimeWear();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean autoBrightnessModes() {
        return getValue(Flags.FLAG_AUTO_BRIGHTNESS_MODES, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).autoBrightnessModes();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean backUpSmoothDisplayAndForcePeakRefreshRate() {
        return getValue(Flags.FLAG_BACK_UP_SMOOTH_DISPLAY_AND_FORCE_PEAK_REFRESH_RATE, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).backUpSmoothDisplayAndForcePeakRefreshRate();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean baseDensityForExternalDisplays() {
        return getValue(Flags.FLAG_BASE_DENSITY_FOR_EXTERNAL_DISPLAYS, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda50
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).baseDensityForExternalDisplays();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean blockAutobrightnessChangesOnStylusUsage() {
        return getValue(Flags.FLAG_BLOCK_AUTOBRIGHTNESS_CHANGES_ON_STYLUS_USAGE, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda59
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).blockAutobrightnessChangesOnStylusUsage();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean brightnessIntRangeUserPerception() {
        return getValue(Flags.FLAG_BRIGHTNESS_INT_RANGE_USER_PERCEPTION, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).brightnessIntRangeUserPerception();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean brightnessWearBedtimeModeClamper() {
        return getValue(Flags.FLAG_BRIGHTNESS_WEAR_BEDTIME_MODE_CLAMPER, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).brightnessWearBedtimeModeClamper();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean committedStateSeparateEvent() {
        return getValue(Flags.FLAG_COMMITTED_STATE_SEPARATE_EVENT, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).committedStateSeparateEvent();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean delayImplicitRrRegistrationUntilRrAccessed() {
        return getValue(Flags.FLAG_DELAY_IMPLICIT_RR_REGISTRATION_UNTIL_RR_ACCESSED, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).delayImplicitRrRegistrationUntilRrAccessed();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean displayCategoryBuiltIn() {
        return getValue(Flags.FLAG_DISPLAY_CATEGORY_BUILT_IN, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda49
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).displayCategoryBuiltIn();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean displayListenerPerformanceImprovements() {
        return getValue(Flags.FLAG_DISPLAY_LISTENER_PERFORMANCE_IMPROVEMENTS, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda43
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).displayListenerPerformanceImprovements();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean displayTopology() {
        return getValue(Flags.FLAG_DISPLAY_TOPOLOGY, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).displayTopology();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean dozeBrightnessFloat() {
        return getValue(Flags.FLAG_DOZE_BRIGHTNESS_FLOAT, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dozeBrightnessFloat();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableAdaptiveToneImprovements1() {
        return getValue(Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_1, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda35
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableAdaptiveToneImprovements1();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableAdaptiveToneImprovements2() {
        return getValue(Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_2, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableAdaptiveToneImprovements2();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableApplyDisplayChangedDuringDisplayAdded() {
        return getValue(Flags.FLAG_ENABLE_APPLY_DISPLAY_CHANGED_DURING_DISPLAY_ADDED, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableApplyDisplayChangedDuringDisplayAdded();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableBatteryStatsForAllDisplays() {
        return getValue(Flags.FLAG_ENABLE_BATTERY_STATS_FOR_ALL_DISPLAYS, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableBatteryStatsForAllDisplays();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableConnectedDisplayErrorHandling() {
        return getValue(Flags.FLAG_ENABLE_CONNECTED_DISPLAY_ERROR_HANDLING, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableConnectedDisplayErrorHandling();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableDisplayContentModeManagement() {
        return getValue(Flags.FLAG_ENABLE_DISPLAY_CONTENT_MODE_MANAGEMENT, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda37
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDisplayContentModeManagement();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableDisplayOffload() {
        return getValue(Flags.FLAG_ENABLE_DISPLAY_OFFLOAD, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda57
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDisplayOffload();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableDisplayResolutionRangeVoting() {
        return getValue(Flags.FLAG_ENABLE_DISPLAY_RESOLUTION_RANGE_VOTING, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda34
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDisplayResolutionRangeVoting();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableDisplaysRefreshRatesSynchronization() {
        return getValue(Flags.FLAG_ENABLE_DISPLAYS_REFRESH_RATES_SYNCHRONIZATION, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda56
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDisplaysRefreshRatesSynchronization();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableGetSuggestedFrameRate() {
        return getValue(Flags.FLAG_ENABLE_GET_SUGGESTED_FRAME_RATE, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableGetSuggestedFrameRate();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableGetSupportedRefreshRates() {
        return getValue(Flags.FLAG_ENABLE_GET_SUPPORTED_REFRESH_RATES, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableGetSupportedRefreshRates();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableHasArrSupport() {
        return getValue(Flags.FLAG_ENABLE_HAS_ARR_SUPPORT, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableHasArrSupport();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableHdrOverridePluginType() {
        return getValue(Flags.FLAG_ENABLE_HDR_OVERRIDE_PLUGIN_TYPE, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda39
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableHdrOverridePluginType();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableModeLimitForExternalDisplay() {
        return getValue(Flags.FLAG_ENABLE_MODE_LIMIT_FOR_EXTERNAL_DISPLAY, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableModeLimitForExternalDisplay();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enablePeakRefreshRatePhysicalLimit() {
        return getValue(Flags.FLAG_ENABLE_PEAK_REFRESH_RATE_PHYSICAL_LIMIT, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePeakRefreshRatePhysicalLimit();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enablePixelAnisotropyCorrection() {
        return getValue(Flags.FLAG_ENABLE_PIXEL_ANISOTROPY_CORRECTION, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda40
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePixelAnisotropyCorrection();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enablePluginManager() {
        return getValue(Flags.FLAG_ENABLE_PLUGIN_MANAGER, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda41
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePluginManager();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enablePortInDisplayLayout() {
        return getValue(Flags.FLAG_ENABLE_PORT_IN_DISPLAY_LAYOUT, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePortInDisplayLayout();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enablePowerThrottlingClamper() {
        return getValue(Flags.FLAG_ENABLE_POWER_THROTTLING_CLAMPER, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda46
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePowerThrottlingClamper();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableRestrictDisplayModes() {
        return getValue(Flags.FLAG_ENABLE_RESTRICT_DISPLAY_MODES, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda52
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableRestrictDisplayModes();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableSynthetic60hzModes() {
        return getValue(Flags.FLAG_ENABLE_SYNTHETIC_60HZ_MODES, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda48
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSynthetic60hzModes();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableUserPreferredModeVote() {
        return getValue(Flags.FLAG_ENABLE_USER_PREFERRED_MODE_VOTE, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda58
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableUserPreferredModeVote();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableUserRefreshRateForExternalDisplay() {
        return getValue(Flags.FLAG_ENABLE_USER_REFRESH_RATE_FOR_EXTERNAL_DISPLAY, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda44
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableUserRefreshRateForExternalDisplay();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableVsyncLowLightVote() {
        return getValue(Flags.FLAG_ENABLE_VSYNC_LOW_LIGHT_VOTE, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda36
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableVsyncLowLightVote();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableVsyncLowPowerVote() {
        return getValue(Flags.FLAG_ENABLE_VSYNC_LOW_POWER_VOTE, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableVsyncLowPowerVote();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableWaitingConfirmationBeforeMirroring() {
        return getValue(Flags.FLAG_ENABLE_WAITING_CONFIRMATION_BEFORE_MIRRORING, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda38
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableWaitingConfirmationBeforeMirroring();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean evenDimmer() {
        return getValue(Flags.FLAG_EVEN_DIMMER, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda42
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).evenDimmer();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean fastHdrTransitions() {
        return getValue(Flags.FLAG_FAST_HDR_TRANSITIONS, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fastHdrTransitions();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean framerateOverrideTriggersRrCallbacks() {
        return getValue(Flags.FLAG_FRAMERATE_OVERRIDE_TRIGGERS_RR_CALLBACKS, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).framerateOverrideTriggersRrCallbacks();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean highestHdrSdrRatioApi() {
        return getValue(Flags.FLAG_HIGHEST_HDR_SDR_RATIO_API, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).highestHdrSdrRatioApi();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean idleScreenConfigInSubscribingLightSensor() {
        return getValue(Flags.FLAG_IDLE_SCREEN_CONFIG_IN_SUBSCRIBING_LIGHT_SENSOR, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda53
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).idleScreenConfigInSubscribingLightSensor();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean idleScreenRefreshRateTimeout() {
        return getValue(Flags.FLAG_IDLE_SCREEN_REFRESH_RATE_TIMEOUT, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda54
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).idleScreenRefreshRateTimeout();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean ignoreAppPreferredRefreshRateRequest() {
        return getValue(Flags.FLAG_IGNORE_APP_PREFERRED_REFRESH_RATE_REQUEST, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ignoreAppPreferredRefreshRateRequest();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean isAlwaysOnAvailableApi() {
        return getValue(Flags.FLAG_IS_ALWAYS_ON_AVAILABLE_API, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda55
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).isAlwaysOnAvailableApi();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean newHdrBrightnessModifier() {
        return getValue(Flags.FLAG_NEW_HDR_BRIGHTNESS_MODIFIER, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).newHdrBrightnessModifier();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean normalBrightnessForDozeParameter() {
        return getValue(Flags.FLAG_NORMAL_BRIGHTNESS_FOR_DOZE_PARAMETER, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).normalBrightnessForDozeParameter();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean offloadDozeOverrideHoldsWakelock() {
        return getValue(Flags.FLAG_OFFLOAD_DOZE_OVERRIDE_HOLDS_WAKELOCK, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda45
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).offloadDozeOverrideHoldsWakelock();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean offloadSessionCancelBlockScreenOn() {
        return getValue(Flags.FLAG_OFFLOAD_SESSION_CANCEL_BLOCK_SCREEN_ON, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).offloadSessionCancelBlockScreenOn();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean refactorDisplayPowerController() {
        return getValue(Flags.FLAG_REFACTOR_DISPLAY_POWER_CONTROLLER, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda47
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).refactorDisplayPowerController();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean refreshRateEventForForegroundApps() {
        return getValue(Flags.FLAG_REFRESH_RATE_EVENT_FOR_FOREGROUND_APPS, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda51
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).refreshRateEventForForegroundApps();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean resolutionBackupRestore() {
        return getValue(Flags.FLAG_RESOLUTION_BACKUP_RESTORE, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).resolutionBackupRestore();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean sensorBasedBrightnessThrottling() {
        return getValue(Flags.FLAG_SENSOR_BASED_BRIGHTNESS_THROTTLING, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sensorBasedBrightnessThrottling();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean separateTimeouts() {
        return getValue(Flags.FLAG_SEPARATE_TIMEOUTS, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).separateTimeouts();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean subscribeGranularDisplayEvents() {
        return getValue(Flags.FLAG_SUBSCRIBE_GRANULAR_DISPLAY_EVENTS, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).subscribeGranularDisplayEvents();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean useFusionProxSensor() {
        return getValue(Flags.FLAG_USE_FUSION_PROX_SENSOR, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useFusionProxSensor();
            }
        });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean virtualDisplayLimit() {
        return getValue(Flags.FLAG_VIRTUAL_DISPLAY_LIMIT, new Predicate() { // from class: com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).virtualDisplayLimit();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ALWAYS_ROTATE_DISPLAY_DEVICE, Flags.FLAG_AUTO_BRIGHTNESS_MODE_BEDTIME_WEAR, Flags.FLAG_AUTO_BRIGHTNESS_MODES, Flags.FLAG_BACK_UP_SMOOTH_DISPLAY_AND_FORCE_PEAK_REFRESH_RATE, Flags.FLAG_BASE_DENSITY_FOR_EXTERNAL_DISPLAYS, Flags.FLAG_BLOCK_AUTOBRIGHTNESS_CHANGES_ON_STYLUS_USAGE, Flags.FLAG_BRIGHTNESS_INT_RANGE_USER_PERCEPTION, Flags.FLAG_BRIGHTNESS_WEAR_BEDTIME_MODE_CLAMPER, Flags.FLAG_COMMITTED_STATE_SEPARATE_EVENT, Flags.FLAG_DELAY_IMPLICIT_RR_REGISTRATION_UNTIL_RR_ACCESSED, Flags.FLAG_DISPLAY_CATEGORY_BUILT_IN, Flags.FLAG_DISPLAY_LISTENER_PERFORMANCE_IMPROVEMENTS, Flags.FLAG_DISPLAY_TOPOLOGY, Flags.FLAG_DOZE_BRIGHTNESS_FLOAT, Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_1, Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_2, Flags.FLAG_ENABLE_APPLY_DISPLAY_CHANGED_DURING_DISPLAY_ADDED, Flags.FLAG_ENABLE_BATTERY_STATS_FOR_ALL_DISPLAYS, Flags.FLAG_ENABLE_CONNECTED_DISPLAY_ERROR_HANDLING, Flags.FLAG_ENABLE_DISPLAY_CONTENT_MODE_MANAGEMENT, Flags.FLAG_ENABLE_DISPLAY_OFFLOAD, Flags.FLAG_ENABLE_DISPLAY_RESOLUTION_RANGE_VOTING, Flags.FLAG_ENABLE_DISPLAYS_REFRESH_RATES_SYNCHRONIZATION, Flags.FLAG_ENABLE_GET_SUGGESTED_FRAME_RATE, Flags.FLAG_ENABLE_GET_SUPPORTED_REFRESH_RATES, Flags.FLAG_ENABLE_HAS_ARR_SUPPORT, Flags.FLAG_ENABLE_HDR_OVERRIDE_PLUGIN_TYPE, Flags.FLAG_ENABLE_MODE_LIMIT_FOR_EXTERNAL_DISPLAY, Flags.FLAG_ENABLE_PEAK_REFRESH_RATE_PHYSICAL_LIMIT, Flags.FLAG_ENABLE_PIXEL_ANISOTROPY_CORRECTION, Flags.FLAG_ENABLE_PLUGIN_MANAGER, Flags.FLAG_ENABLE_PORT_IN_DISPLAY_LAYOUT, Flags.FLAG_ENABLE_POWER_THROTTLING_CLAMPER, Flags.FLAG_ENABLE_RESTRICT_DISPLAY_MODES, Flags.FLAG_ENABLE_SYNTHETIC_60HZ_MODES, Flags.FLAG_ENABLE_USER_PREFERRED_MODE_VOTE, Flags.FLAG_ENABLE_USER_REFRESH_RATE_FOR_EXTERNAL_DISPLAY, Flags.FLAG_ENABLE_VSYNC_LOW_LIGHT_VOTE, Flags.FLAG_ENABLE_VSYNC_LOW_POWER_VOTE, Flags.FLAG_ENABLE_WAITING_CONFIRMATION_BEFORE_MIRRORING, Flags.FLAG_EVEN_DIMMER, Flags.FLAG_FAST_HDR_TRANSITIONS, Flags.FLAG_FRAMERATE_OVERRIDE_TRIGGERS_RR_CALLBACKS, Flags.FLAG_HIGHEST_HDR_SDR_RATIO_API, Flags.FLAG_IDLE_SCREEN_CONFIG_IN_SUBSCRIBING_LIGHT_SENSOR, Flags.FLAG_IDLE_SCREEN_REFRESH_RATE_TIMEOUT, Flags.FLAG_IGNORE_APP_PREFERRED_REFRESH_RATE_REQUEST, Flags.FLAG_IS_ALWAYS_ON_AVAILABLE_API, Flags.FLAG_NEW_HDR_BRIGHTNESS_MODIFIER, Flags.FLAG_NORMAL_BRIGHTNESS_FOR_DOZE_PARAMETER, Flags.FLAG_OFFLOAD_DOZE_OVERRIDE_HOLDS_WAKELOCK, Flags.FLAG_OFFLOAD_SESSION_CANCEL_BLOCK_SCREEN_ON, Flags.FLAG_REFACTOR_DISPLAY_POWER_CONTROLLER, Flags.FLAG_REFRESH_RATE_EVENT_FOR_FOREGROUND_APPS, Flags.FLAG_RESOLUTION_BACKUP_RESTORE, Flags.FLAG_SENSOR_BASED_BRIGHTNESS_THROTTLING, Flags.FLAG_SEPARATE_TIMEOUTS, Flags.FLAG_SUBSCRIBE_GRANULAR_DISPLAY_EVENTS, Flags.FLAG_USE_FUSION_PROX_SENSOR, Flags.FLAG_VIRTUAL_DISPLAY_LIMIT);
    }
}
