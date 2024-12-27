package com.android.server.display.feature.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_ALWAYS_ROTATE_DISPLAY_DEVICE,
                            Flags.FLAG_AUTO_BRIGHTNESS_MODES,
                            Flags.FLAG_BACK_UP_SMOOTH_DISPLAY_AND_FORCE_PEAK_REFRESH_RATE,
                            Flags.FLAG_BRIGHTNESS_INT_RANGE_USER_PERCEPTION,
                            Flags.FLAG_BRIGHTNESS_WEAR_BEDTIME_MODE_CLAMPER,
                            Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_1,
                            Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_2,
                            Flags.FLAG_ENABLE_CONNECTED_DISPLAY_ERROR_HANDLING,
                            Flags.FLAG_ENABLE_CONNECTED_DISPLAY_MANAGEMENT,
                            Flags.FLAG_ENABLE_DISPLAY_OFFLOAD,
                            Flags.FLAG_ENABLE_DISPLAY_RESOLUTION_RANGE_VOTING,
                            Flags.FLAG_ENABLE_DISPLAYS_REFRESH_RATES_SYNCHRONIZATION,
                            Flags.FLAG_ENABLE_HDR_CLAMPER,
                            Flags.FLAG_ENABLE_MODE_LIMIT_FOR_EXTERNAL_DISPLAY,
                            Flags.FLAG_ENABLE_NBM_CONTROLLER,
                            Flags.FLAG_ENABLE_PEAK_REFRESH_RATE_PHYSICAL_LIMIT,
                            Flags.FLAG_ENABLE_PIXEL_ANISOTROPY_CORRECTION,
                            Flags.FLAG_ENABLE_PORT_IN_DISPLAY_LAYOUT,
                            Flags.FLAG_ENABLE_POWER_THROTTLING_CLAMPER,
                            Flags.FLAG_ENABLE_RESTRICT_DISPLAY_MODES,
                            Flags.FLAG_ENABLE_SYNTHETIC_60HZ_MODES,
                            Flags.FLAG_ENABLE_USER_PREFERRED_MODE_VOTE,
                            Flags.FLAG_ENABLE_VSYNC_LOW_LIGHT_VOTE,
                            Flags.FLAG_ENABLE_VSYNC_LOW_POWER_VOTE,
                            Flags.FLAG_EVEN_DIMMER,
                            Flags.FLAG_FAST_HDR_TRANSITIONS,
                            Flags.FLAG_IDLE_SCREEN_REFRESH_RATE_TIMEOUT,
                            Flags.FLAG_IGNORE_APP_PREFERRED_REFRESH_RATE_REQUEST,
                            Flags.FLAG_OFFLOAD_CONTROLS_DOZE_AUTO_BRIGHTNESS,
                            Flags.FLAG_OFFLOAD_DOZE_OVERRIDE_HOLDS_WAKELOCK,
                            Flags.FLAG_REFACTOR_DISPLAY_POWER_CONTROLLER,
                            Flags.FLAG_REFRESH_RATE_VOTING_TELEMETRY,
                            Flags.FLAG_RESOLUTION_BACKUP_RESTORE,
                            Flags.FLAG_SENSOR_BASED_BRIGHTNESS_THROTTLING,
                            Flags.FLAG_USE_FUSION_PROX_SENSOR,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean alwaysRotateDisplayDevice() {
        return getValue(
                Flags.FLAG_ALWAYS_ROTATE_DISPLAY_DEVICE,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).alwaysRotateDisplayDevice();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean autoBrightnessModes() {
        return getValue(
                Flags.FLAG_AUTO_BRIGHTNESS_MODES,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).autoBrightnessModes();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean backUpSmoothDisplayAndForcePeakRefreshRate() {
        return getValue(
                Flags.FLAG_BACK_UP_SMOOTH_DISPLAY_AND_FORCE_PEAK_REFRESH_RATE,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).backUpSmoothDisplayAndForcePeakRefreshRate();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean brightnessIntRangeUserPerception() {
        return getValue(
                Flags.FLAG_BRIGHTNESS_INT_RANGE_USER_PERCEPTION,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).brightnessIntRangeUserPerception();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean brightnessWearBedtimeModeClamper() {
        return getValue(
                Flags.FLAG_BRIGHTNESS_WEAR_BEDTIME_MODE_CLAMPER,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).brightnessWearBedtimeModeClamper();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableAdaptiveToneImprovements1() {
        return getValue(
                Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_1,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableAdaptiveToneImprovements1();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableAdaptiveToneImprovements2() {
        return getValue(
                Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_2,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableAdaptiveToneImprovements2();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableConnectedDisplayErrorHandling() {
        return getValue(
                Flags.FLAG_ENABLE_CONNECTED_DISPLAY_ERROR_HANDLING,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableConnectedDisplayErrorHandling();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableConnectedDisplayManagement() {
        return getValue(
                Flags.FLAG_ENABLE_CONNECTED_DISPLAY_MANAGEMENT,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableConnectedDisplayManagement();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableDisplayOffload() {
        return getValue(
                Flags.FLAG_ENABLE_DISPLAY_OFFLOAD,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda33
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableDisplayOffload();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableDisplayResolutionRangeVoting() {
        return getValue(
                Flags.FLAG_ENABLE_DISPLAY_RESOLUTION_RANGE_VOTING,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableDisplayResolutionRangeVoting();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableDisplaysRefreshRatesSynchronization() {
        return getValue(
                Flags.FLAG_ENABLE_DISPLAYS_REFRESH_RATES_SYNCHRONIZATION,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda32
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableDisplaysRefreshRatesSynchronization();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableHdrClamper() {
        return getValue(
                Flags.FLAG_ENABLE_HDR_CLAMPER,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableHdrClamper();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableModeLimitForExternalDisplay() {
        return getValue(
                Flags.FLAG_ENABLE_MODE_LIMIT_FOR_EXTERNAL_DISPLAY,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableModeLimitForExternalDisplay();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableNbmController() {
        return getValue(
                Flags.FLAG_ENABLE_NBM_CONTROLLER,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableNbmController();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enablePeakRefreshRatePhysicalLimit() {
        return getValue(
                Flags.FLAG_ENABLE_PEAK_REFRESH_RATE_PHYSICAL_LIMIT,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enablePeakRefreshRatePhysicalLimit();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enablePixelAnisotropyCorrection() {
        return getValue(
                Flags.FLAG_ENABLE_PIXEL_ANISOTROPY_CORRECTION,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enablePixelAnisotropyCorrection();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enablePortInDisplayLayout() {
        return getValue(
                Flags.FLAG_ENABLE_PORT_IN_DISPLAY_LAYOUT,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enablePortInDisplayLayout();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enablePowerThrottlingClamper() {
        return getValue(
                Flags.FLAG_ENABLE_POWER_THROTTLING_CLAMPER,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enablePowerThrottlingClamper();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableRestrictDisplayModes() {
        return getValue(
                Flags.FLAG_ENABLE_RESTRICT_DISPLAY_MODES,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda30
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableRestrictDisplayModes();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableSynthetic60hzModes() {
        return getValue(
                Flags.FLAG_ENABLE_SYNTHETIC_60HZ_MODES,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda29
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableSynthetic60hzModes();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableUserPreferredModeVote() {
        return getValue(
                Flags.FLAG_ENABLE_USER_PREFERRED_MODE_VOTE,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda34
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableUserPreferredModeVote();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableVsyncLowLightVote() {
        return getValue(
                Flags.FLAG_ENABLE_VSYNC_LOW_LIGHT_VOTE,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableVsyncLowLightVote();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableVsyncLowPowerVote() {
        return getValue(
                Flags.FLAG_ENABLE_VSYNC_LOW_POWER_VOTE,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableVsyncLowPowerVote();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean evenDimmer() {
        return getValue(
                Flags.FLAG_EVEN_DIMMER,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).evenDimmer();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean fastHdrTransitions() {
        return getValue(
                Flags.FLAG_FAST_HDR_TRANSITIONS,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).fastHdrTransitions();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean idleScreenRefreshRateTimeout() {
        return getValue(
                Flags.FLAG_IDLE_SCREEN_REFRESH_RATE_TIMEOUT,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda31
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).idleScreenRefreshRateTimeout();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean ignoreAppPreferredRefreshRateRequest() {
        return getValue(
                Flags.FLAG_IGNORE_APP_PREFERRED_REFRESH_RATE_REQUEST,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).ignoreAppPreferredRefreshRateRequest();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean offloadControlsDozeAutoBrightness() {
        return getValue(
                Flags.FLAG_OFFLOAD_CONTROLS_DOZE_AUTO_BRIGHTNESS,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).offloadControlsDozeAutoBrightness();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean offloadDozeOverrideHoldsWakelock() {
        return getValue(
                Flags.FLAG_OFFLOAD_DOZE_OVERRIDE_HOLDS_WAKELOCK,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).offloadDozeOverrideHoldsWakelock();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean refactorDisplayPowerController() {
        return getValue(
                Flags.FLAG_REFACTOR_DISPLAY_POWER_CONTROLLER,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda28
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).refactorDisplayPowerController();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean refreshRateVotingTelemetry() {
        return getValue(
                Flags.FLAG_REFRESH_RATE_VOTING_TELEMETRY,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).refreshRateVotingTelemetry();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean resolutionBackupRestore() {
        return getValue(
                Flags.FLAG_RESOLUTION_BACKUP_RESTORE,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).resolutionBackupRestore();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean sensorBasedBrightnessThrottling() {
        return getValue(
                Flags.FLAG_SENSOR_BASED_BRIGHTNESS_THROTTLING,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).sensorBasedBrightnessThrottling();
                    }
                });
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean useFusionProxSensor() {
        return getValue(
                Flags.FLAG_USE_FUSION_PROX_SENSOR,
                new Predicate() { // from class:
                                  // com.android.server.display.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).useFusionProxSensor();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_ALWAYS_ROTATE_DISPLAY_DEVICE,
                Flags.FLAG_AUTO_BRIGHTNESS_MODES,
                Flags.FLAG_BACK_UP_SMOOTH_DISPLAY_AND_FORCE_PEAK_REFRESH_RATE,
                Flags.FLAG_BRIGHTNESS_INT_RANGE_USER_PERCEPTION,
                Flags.FLAG_BRIGHTNESS_WEAR_BEDTIME_MODE_CLAMPER,
                Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_1,
                Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_2,
                Flags.FLAG_ENABLE_CONNECTED_DISPLAY_ERROR_HANDLING,
                Flags.FLAG_ENABLE_CONNECTED_DISPLAY_MANAGEMENT,
                Flags.FLAG_ENABLE_DISPLAY_OFFLOAD,
                Flags.FLAG_ENABLE_DISPLAY_RESOLUTION_RANGE_VOTING,
                Flags.FLAG_ENABLE_DISPLAYS_REFRESH_RATES_SYNCHRONIZATION,
                Flags.FLAG_ENABLE_HDR_CLAMPER,
                Flags.FLAG_ENABLE_MODE_LIMIT_FOR_EXTERNAL_DISPLAY,
                Flags.FLAG_ENABLE_NBM_CONTROLLER,
                Flags.FLAG_ENABLE_PEAK_REFRESH_RATE_PHYSICAL_LIMIT,
                Flags.FLAG_ENABLE_PIXEL_ANISOTROPY_CORRECTION,
                Flags.FLAG_ENABLE_PORT_IN_DISPLAY_LAYOUT,
                Flags.FLAG_ENABLE_POWER_THROTTLING_CLAMPER,
                Flags.FLAG_ENABLE_RESTRICT_DISPLAY_MODES,
                Flags.FLAG_ENABLE_SYNTHETIC_60HZ_MODES,
                Flags.FLAG_ENABLE_USER_PREFERRED_MODE_VOTE,
                Flags.FLAG_ENABLE_VSYNC_LOW_LIGHT_VOTE,
                Flags.FLAG_ENABLE_VSYNC_LOW_POWER_VOTE,
                Flags.FLAG_EVEN_DIMMER,
                Flags.FLAG_FAST_HDR_TRANSITIONS,
                Flags.FLAG_IDLE_SCREEN_REFRESH_RATE_TIMEOUT,
                Flags.FLAG_IGNORE_APP_PREFERRED_REFRESH_RATE_REQUEST,
                Flags.FLAG_OFFLOAD_CONTROLS_DOZE_AUTO_BRIGHTNESS,
                Flags.FLAG_OFFLOAD_DOZE_OVERRIDE_HOLDS_WAKELOCK,
                Flags.FLAG_REFACTOR_DISPLAY_POWER_CONTROLLER,
                Flags.FLAG_REFRESH_RATE_VOTING_TELEMETRY,
                Flags.FLAG_RESOLUTION_BACKUP_RESTORE,
                Flags.FLAG_SENSOR_BASED_BRIGHTNESS_THROTTLING,
                Flags.FLAG_USE_FUSION_PROX_SENSOR);
    }
}
