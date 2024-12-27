package android.app.admin.flags;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.app.admin.flags.FeatureFlags
    public boolean allowQueryingProfileType() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean allowScreenBrightnessControlOnCope() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean alwaysPersistDo() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean assistContentUserRestrictionEnabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean backupConnectedAppsSettings() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean backupServiceSecurityLogEventEnabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean coexistenceMigrationForNonEmmManagementEnabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean copyAccountWithRetryEnabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean crossUserSuspensionEnabledRo() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean dedicatedDeviceControlApiEnabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean dedicatedDeviceControlEnabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean defaultSmsPersonalAppSuspensionFixEnabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean deletePrivateSpaceUnderRestriction() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean devicePolicySizeTrackingEnabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean devicePolicySizeTrackingInternalBugFixEnabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean devicePolicySizeTrackingInternalEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean deviceTheftApiEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean deviceTheftImplEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean disallowUserControlBgUsageFix() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean disallowUserControlStoppedStateFix() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean dmrhSetAppRestrictions() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean dumpsysPolicyEngineMigrationEnabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean esimManagementEnabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean esimManagementUxEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean headlessDeviceOwnerDelegateSecurityLoggingBugFix() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean headlessDeviceOwnerProvisioningFixEnabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean headlessDeviceOwnerSingleUserEnabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean headlessSingleMinTargetSdk() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean headlessSingleUserBadDeviceAdminStateFix() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean headlessSingleUserCompatibilityFix() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean headlessSingleUserFixes() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean hsumUnlockNotificationFix() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean isMtePolicyEnforced() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean isRecursiveRequiredAppMergingEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean onboardingBugreportStorageBugFix() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean onboardingBugreportV2Enabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean onboardingConsentlessBugreports() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean permissionMigrationForZeroTrustApiEnabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean permissionMigrationForZeroTrustImplEnabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean policyEngineMigrationV2Enabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean powerExemptionBgUsageFix() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean quietModeCredentialBugFix() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean securityLogV2Enabled() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean unmanagedModeMigration() {
        return false;
    }
}
