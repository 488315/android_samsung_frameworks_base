package com.android.server.display.feature.flags;

/* loaded from: classes6.dex */
public interface FeatureFlags {
    boolean alwaysRotateDisplayDevice();

    boolean autoBrightnessModeBedtimeWear();

    boolean autoBrightnessModes();

    boolean backUpSmoothDisplayAndForcePeakRefreshRate();

    boolean baseDensityForExternalDisplays();

    boolean blockAutobrightnessChangesOnStylusUsage();

    boolean brightnessIntRangeUserPerception();

    boolean brightnessWearBedtimeModeClamper();

    boolean committedStateSeparateEvent();

    boolean delayImplicitRrRegistrationUntilRrAccessed();

    boolean displayCategoryBuiltIn();

    boolean displayListenerPerformanceImprovements();

    boolean displayTopology();

    boolean dozeBrightnessFloat();

    boolean enableAdaptiveToneImprovements1();

    boolean enableAdaptiveToneImprovements2();

    boolean enableApplyDisplayChangedDuringDisplayAdded();

    boolean enableBatteryStatsForAllDisplays();

    boolean enableConnectedDisplayErrorHandling();

    boolean enableDisplayContentModeManagement();

    boolean enableDisplayOffload();

    boolean enableDisplayResolutionRangeVoting();

    boolean enableDisplaysRefreshRatesSynchronization();

    boolean enableGetSuggestedFrameRate();

    boolean enableGetSupportedRefreshRates();

    boolean enableHasArrSupport();

    boolean enableHdrOverridePluginType();

    boolean enableModeLimitForExternalDisplay();

    boolean enablePeakRefreshRatePhysicalLimit();

    boolean enablePixelAnisotropyCorrection();

    boolean enablePluginManager();

    boolean enablePortInDisplayLayout();

    boolean enablePowerThrottlingClamper();

    boolean enableRestrictDisplayModes();

    boolean enableSynthetic60hzModes();

    boolean enableUserPreferredModeVote();

    boolean enableUserRefreshRateForExternalDisplay();

    boolean enableVsyncLowLightVote();

    boolean enableVsyncLowPowerVote();

    boolean enableWaitingConfirmationBeforeMirroring();

    boolean evenDimmer();

    boolean fastHdrTransitions();

    boolean framerateOverrideTriggersRrCallbacks();

    boolean highestHdrSdrRatioApi();

    boolean idleScreenConfigInSubscribingLightSensor();

    boolean idleScreenRefreshRateTimeout();

    boolean ignoreAppPreferredRefreshRateRequest();

    boolean isAlwaysOnAvailableApi();

    boolean newHdrBrightnessModifier();

    boolean normalBrightnessForDozeParameter();

    boolean offloadDozeOverrideHoldsWakelock();

    boolean offloadSessionCancelBlockScreenOn();

    boolean refactorDisplayPowerController();

    boolean refreshRateEventForForegroundApps();

    boolean resolutionBackupRestore();

    boolean sensorBasedBrightnessThrottling();

    boolean separateTimeouts();

    boolean subscribeGranularDisplayEvents();

    boolean useFusionProxSensor();

    boolean virtualDisplayLimit();
}
