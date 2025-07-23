package com.android.internal.hidden_from_bootclasspath.android.permission.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean allowHostPermissionDialogsOnVirtualDevices();

    boolean appOpsServiceHandlerFix();

    boolean appopAccessTrackingLoggingEnabled();

    boolean appopModeCachingEnabled();

    boolean checkOpOverloadApiEnabled();

    boolean checkOpValidatePackage();

    boolean delayUidStateChangesFromCapabilityUpdates();

    boolean deviceAwareAppOpNewSchemaEnabled();

    boolean deviceAwarePermissionApisEnabled();

    boolean deviceAwarePermissionsEnabled();

    boolean deviceIdInOpProxyInfoEnabled();

    boolean devicePolicyManagementRoleSplitCreateManagedProfileEnabled();

    boolean dontRemoveExistingUidStates();

    boolean enableAiaiProxiedTextClassifiers();

    boolean enableAllSqliteAppopsAccesses();

    boolean enableOtpInTextClassifiers();

    boolean enableSqliteAppopsAccesses();

    boolean enableSystemSupervisionRoleBehavior();

    boolean enforceDefaultDeviceIdInMyAttributionSource();

    boolean enhancedConfirmationInCallApisEnabled();

    boolean enhancedConfirmationModeApisEnabled();

    boolean factoryResetPrepPermissionApis();

    boolean finePowerMonitorPermission();

    boolean finishRunningOpsForKilledPackages();

    boolean getEmergencyRoleHolderApiEnabled();

    boolean grantReadBlockedNumbersToSystemUiIntelligence();

    boolean healthConnectBackupRestorePermissionEnabled();

    boolean ignoreProcessText();

    boolean locationBypassPrivacyDashboardEnabled();

    boolean noteOpBatchingEnabled();

    boolean opEnableMobileDataByUser();

    boolean permissionRequestShortCircuitEnabled();

    boolean permissionTreeApisDeprecated();

    boolean rangingPermissionEnabled();

    boolean rateLimitBatchedNoteOpAsyncCallbacksEnabled();

    boolean recordAllRuntimeAppopsSqlite();

    boolean replaceBodySensorPermissionEnabled();

    boolean retailDemoRoleEnabled();

    boolean runtimePermissionAppopsMappingEnabled();

    boolean sensitiveContentImprovements();

    boolean sensitiveContentMetricsBugfix();

    boolean sensitiveContentRecentsScreenshotBugfix();

    boolean sensitiveNotificationAppProtection();

    boolean serverSideAttributionRegistration();

    boolean setNextAttributionSource();

    boolean shouldRegisterAttributionSource();

    boolean signaturePermissionAllowlistEnabled();

    boolean sqliteDiscreteOpEventLoggingEnabled();

    boolean supervisionRolePermissionUpdateEnabled();

    boolean syncOnOpNotedApi();

    boolean systemSelectionToolbarEnabled();

    boolean systemServerRoleControllerEnabled();

    boolean systemVendorIntelligenceRoleEnabled();

    boolean textClassifierChoiceApiEnabled();

    boolean unknownCallPackageInstallBlockingEnabled();

    boolean unknownCallSettingBlockedLoggingEnabled();

    boolean updatableTextClassifierForOtpDetectionEnabled();

    boolean useFrozenAwareRemoteCallbackList();

    boolean useProfileLabelsForDefaultAppSectionTitles();

    boolean useSystemSelectionToolbarInSysui();

    boolean voiceActivationPermissionApis();

    boolean walletRoleCrossUserEnabled();

    boolean walletRoleEnabled();

    boolean walletRoleIconPropertyEnabled();
}
