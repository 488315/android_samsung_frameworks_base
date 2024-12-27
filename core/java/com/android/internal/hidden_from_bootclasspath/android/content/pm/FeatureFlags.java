package com.android.internal.hidden_from_bootclasspath.android.content.pm;

public interface FeatureFlags {
    boolean allowSdkSandboxQueryIntentActivities();

    boolean archiving();

    boolean aslInApkAppMetadataSource();

    boolean componentStateChangedMetrics();

    boolean disallowSdkLibsToBeApps();

    boolean emergencyInstallPermission();

    boolean encodeAppIntent();

    boolean fixDuplicatedFlags();

    boolean fixSystemAppsFirstInstallTime();

    boolean forceMultiArchNativeLibsMatch();

    boolean getPackageInfo();

    boolean getPackageInfoWithFd();

    boolean getPackageStorageStats();

    boolean getResolvedApkPath();

    boolean improveHomeAppBehavior();

    boolean improveInstallDontKill();

    boolean improveInstallFreeze();

    boolean introduceMediaProcessingType();

    boolean lightweightInvisibleLabelDetection();

    boolean minTargetSdk24();

    boolean nullableDataDir();

    boolean packageRestartQueryDisabledByDefault();

    boolean provideInfoOfApkInApex();

    boolean quarantinedEnabled();

    boolean readInstallInfo();

    boolean recoverabilityDetection();

    boolean relativeReferenceIntentFilters();

    boolean restrictNonpreloadsSystemShareduids();

    boolean rollbackLifetime();

    boolean sdkLibIndependence();

    boolean setPreVerifiedDomains();

    boolean stayStopped();

    boolean useArtServiceV2();

    boolean usePiaV2();

    boolean waitApplicationKilled();
}
