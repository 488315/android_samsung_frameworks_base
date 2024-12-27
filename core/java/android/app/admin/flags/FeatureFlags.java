package android.app.admin.flags;

/* loaded from: classes.dex */
public interface FeatureFlags {
    boolean allowQueryingProfileType();

    boolean allowScreenBrightnessControlOnCope();

    boolean alwaysPersistDo();

    boolean assistContentUserRestrictionEnabled();

    boolean backupConnectedAppsSettings();

    boolean backupServiceSecurityLogEventEnabled();

    boolean coexistenceMigrationForNonEmmManagementEnabled();

    boolean copyAccountWithRetryEnabled();

    boolean crossUserSuspensionEnabledRo();

    boolean dedicatedDeviceControlApiEnabled();

    boolean dedicatedDeviceControlEnabled();

    boolean defaultSmsPersonalAppSuspensionFixEnabled();

    boolean deletePrivateSpaceUnderRestriction();

    boolean devicePolicySizeTrackingEnabled();

    boolean devicePolicySizeTrackingInternalBugFixEnabled();

    boolean devicePolicySizeTrackingInternalEnabled();

    boolean deviceTheftApiEnabled();

    boolean deviceTheftImplEnabled();

    boolean disallowUserControlBgUsageFix();

    boolean disallowUserControlStoppedStateFix();

    boolean dmrhSetAppRestrictions();

    boolean dumpsysPolicyEngineMigrationEnabled();

    boolean esimManagementEnabled();

    boolean esimManagementUxEnabled();

    boolean headlessDeviceOwnerDelegateSecurityLoggingBugFix();

    boolean headlessDeviceOwnerProvisioningFixEnabled();

    boolean headlessDeviceOwnerSingleUserEnabled();

    boolean headlessSingleMinTargetSdk();

    boolean headlessSingleUserBadDeviceAdminStateFix();

    boolean headlessSingleUserCompatibilityFix();

    boolean headlessSingleUserFixes();

    boolean hsumUnlockNotificationFix();

    boolean isMtePolicyEnforced();

    boolean isRecursiveRequiredAppMergingEnabled();

    boolean onboardingBugreportStorageBugFix();

    boolean onboardingBugreportV2Enabled();

    boolean onboardingConsentlessBugreports();

    boolean permissionMigrationForZeroTrustApiEnabled();

    boolean permissionMigrationForZeroTrustImplEnabled();

    boolean policyEngineMigrationV2Enabled();

    boolean powerExemptionBgUsageFix();

    boolean quietModeCredentialBugFix();

    boolean securityLogV2Enabled();

    boolean unmanagedModeMigration();
}
