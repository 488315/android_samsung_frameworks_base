package com.android.internal.camera.flags;

public interface FeatureFlags {
    boolean analytics24q3();

    boolean cachePermissionServices();

    boolean calculatePerfOverrideDuringSessionSupport();

    boolean cameraAeModeLowLightBoost();

    boolean cameraDeviceSetup();

    boolean cameraExtensionsCharacteristicsGet();

    boolean cameraHsumPermission();

    boolean cameraManualFlashStrengthControl();

    boolean cameraPrivacyAllowlist();

    boolean checkSessionSupportBeforeSessionChar();

    boolean concertMode();

    boolean concertModeApi();

    boolean delayLazyHalInstantiation();

    boolean extension10Bit();

    boolean featureCombinationQuery();

    boolean injectSessionParams();

    boolean lazyAidlWaitForService();

    boolean logUltrawideUsage();

    boolean logZoomOverrideUsage();

    boolean multiResRawReprocessing();

    boolean multiresolutionImagereaderUsageConfig();

    boolean realtimePriorityBump();

    boolean returnBuffersOutsideLocks();

    boolean sessionHalBufManager();

    boolean singleThreadExecutor();

    boolean surfaceIpc();

    boolean surfaceLeakFix();

    boolean useRoBoardApiLevelForVndkVersion();

    boolean useSystemApiForVndkVersion();

    boolean watchForegroundChanges();
}
