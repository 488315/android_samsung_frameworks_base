package com.android.server.display.feature.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean alwaysRotateDisplayDevice();

    boolean autoBrightnessModes();

    boolean backUpSmoothDisplayAndForcePeakRefreshRate();

    boolean brightnessIntRangeUserPerception();

    boolean brightnessWearBedtimeModeClamper();

    boolean enableAdaptiveToneImprovements1();

    boolean enableAdaptiveToneImprovements2();

    boolean enableConnectedDisplayErrorHandling();

    boolean enableConnectedDisplayManagement();

    boolean enableDisplayOffload();

    boolean enableDisplayResolutionRangeVoting();

    boolean enableDisplaysRefreshRatesSynchronization();

    boolean enableHdrClamper();

    boolean enableModeLimitForExternalDisplay();

    boolean enableNbmController();

    boolean enablePeakRefreshRatePhysicalLimit();

    boolean enablePixelAnisotropyCorrection();

    boolean enablePortInDisplayLayout();

    boolean enablePowerThrottlingClamper();

    boolean enableRestrictDisplayModes();

    boolean enableSynthetic60hzModes();

    boolean enableUserPreferredModeVote();

    boolean enableVsyncLowLightVote();

    boolean enableVsyncLowPowerVote();

    boolean evenDimmer();

    boolean fastHdrTransitions();

    boolean idleScreenRefreshRateTimeout();

    boolean ignoreAppPreferredRefreshRateRequest();

    boolean offloadControlsDozeAutoBrightness();

    boolean offloadDozeOverrideHoldsWakelock();

    boolean refactorDisplayPowerController();

    boolean refreshRateVotingTelemetry();

    boolean resolutionBackupRestore();

    boolean sensorBasedBrightnessThrottling();

    boolean useFusionProxSensor();
}
