package com.android.internal.hidden_from_bootclasspath.android.content.pm;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean allowSdkSandboxQueryIntentActivities();

    boolean alwaysLoadPastCertsV4();

    boolean appCompatOption16kb();

    boolean archiving();

    boolean aslInApkAppMetadataSource();

    boolean audioPlaybackCaptureAllowance();

    boolean cacheSdkSystemFeatures();

    boolean changeLauncherBadging();

    boolean cloudCompilationPm();

    boolean cloudCompilationVerification();

    boolean componentStateChangedMetrics();

    boolean deletePackagesSilentlyBackport();

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

    boolean includeFeatureFlagsInPackageCacher();

    boolean introduceMediaProcessingType();

    boolean lightweightInvisibleLabelDetection();

    boolean minTargetSdk24();

    boolean nullableDataDir();

    boolean optimizeParsingInRegisteredServicesCache();

    boolean packageRestartQueryDisabledByDefault();

    boolean parallelPackageParsingAcrossSystemDirs();

    boolean provideInfoOfApkInApex();

    boolean quarantinedEnabled();

    boolean readInstallInfo();

    boolean recoverabilityDetection();

    boolean reduceBroadcastsForComponentStateChanges();

    boolean relativeReferenceIntentFilters();

    boolean removeCrossUserPermissionHack();

    boolean removeHiddenModuleUsage();

    boolean restrictNonpreloadsSystemShareduids();

    boolean rollbackLifetime();

    boolean sdkDependencyInstaller();

    boolean sdkLibIndependence();

    boolean setPreVerifiedDomains();

    boolean stayStopped();

    boolean uidBasedProviderLookup();

    boolean useArtServiceV2();

    boolean usePiaV2();

    boolean waitApplicationKilled();
}
