package com.android.server.display.feature.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean alwaysRotateDisplayDevice() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean autoBrightnessModes() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean backUpSmoothDisplayAndForcePeakRefreshRate() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean brightnessIntRangeUserPerception() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean brightnessWearBedtimeModeClamper() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableAdaptiveToneImprovements1() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableAdaptiveToneImprovements2() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableConnectedDisplayErrorHandling() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableConnectedDisplayManagement() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableDisplayOffload() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableDisplayResolutionRangeVoting() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableDisplaysRefreshRatesSynchronization() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableHdrClamper() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableModeLimitForExternalDisplay() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableNbmController() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enablePeakRefreshRatePhysicalLimit() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enablePixelAnisotropyCorrection() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enablePortInDisplayLayout() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enablePowerThrottlingClamper() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableRestrictDisplayModes() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableSynthetic60hzModes() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableUserPreferredModeVote() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableVsyncLowLightVote() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableVsyncLowPowerVote() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean evenDimmer() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean fastHdrTransitions() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean idleScreenRefreshRateTimeout() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean ignoreAppPreferredRefreshRateRequest() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean offloadControlsDozeAutoBrightness() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean offloadDozeOverrideHoldsWakelock() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean refactorDisplayPowerController() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean refreshRateVotingTelemetry() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean resolutionBackupRestore() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean sensorBasedBrightnessThrottling() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean useFusionProxSensor() {
        return false;
    }
}
