package com.android.internal.hidden_from_bootclasspath.android.app.admin.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean activeAdminCleanup();

    boolean allowQueryingProfileType();

    boolean assistContentUserRestrictionEnabled();

    boolean backupConnectedAppsSettings();

    boolean backupServiceSecurityLogEventEnabled();

    boolean coexistenceMigrationForSupervisionEnabled();

    boolean crossUserSuspensionEnabledRo();

    boolean dedicatedDeviceControlApiEnabled();

    boolean defaultSmsPersonalAppSuspensionFixEnabled();

    boolean devicePolicySizeTrackingEnabled();

    boolean deviceTheftApiEnabled();

    boolean deviceTheftImplEnabled();

    boolean disallowUserControlStoppedStateFix();

    boolean enableSupervisionServiceSync();

    boolean esimManagementEnabled();

    boolean esimManagementUxEnabled();

    boolean headlessDeviceOwnerSingleUserEnabled();

    boolean headlessSingleMinTargetSdk();

    boolean internalLogEventListener();

    boolean isMtePolicyEnforced();

    boolean isRecursiveRequiredAppMergingEnabled();

    boolean lockNowCoexistence();

    boolean managementModePolicyMetrics();

    boolean onboardingBugreportStorageBugFix();

    boolean onboardingBugreportV2Enabled();

    boolean onboardingConsentlessBugreports();

    boolean permissionMigrationForZeroTrustApiEnabled();

    boolean policyEngineMigrationV2Enabled();

    boolean provisioningContextParameter();

    boolean quietModeCredentialBugFix();

    boolean removeManagedEsimOnWorkProfileDeletion();

    boolean removeManagedProfileEnabled();

    boolean resetPasswordWithTokenCoexistence();

    boolean secondaryLockscreenApiEnabled();

    boolean securityLogV2Enabled();

    boolean setApplicationRestrictionsCoexistence();

    boolean setAutoTimeEnabledCoexistence();

    boolean setAutoTimeZoneEnabledCoexistence();

    boolean setBackupServiceEnabledCoexistence();

    boolean setKeyguardDisabledFeaturesCoexistence();

    boolean setMtePolicyCoexistence();

    boolean setPermissionGrantStateCoexistence();

    boolean splitCreateManagedProfileEnabled();

    boolean suspendPackagesCoexistence();

    boolean unmanagedModeMigration();

    boolean unsuspendNotSuspended();

    boolean usePolicyIntersectionForPermittedInputMethods();

    boolean userProvisioningSameState();
}
