package android.app.admin.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_ALLOW_QUERYING_PROFILE_TYPE,
                            Flags.FLAG_ALLOW_SCREEN_BRIGHTNESS_CONTROL_ON_COPE,
                            Flags.FLAG_ALWAYS_PERSIST_DO,
                            Flags.FLAG_ASSIST_CONTENT_USER_RESTRICTION_ENABLED,
                            Flags.FLAG_BACKUP_CONNECTED_APPS_SETTINGS,
                            Flags.FLAG_BACKUP_SERVICE_SECURITY_LOG_EVENT_ENABLED,
                            Flags.FLAG_COEXISTENCE_MIGRATION_FOR_NON_EMM_MANAGEMENT_ENABLED,
                            Flags.FLAG_COPY_ACCOUNT_WITH_RETRY_ENABLED,
                            Flags.FLAG_CROSS_USER_SUSPENSION_ENABLED_RO,
                            Flags.FLAG_DEDICATED_DEVICE_CONTROL_API_ENABLED,
                            Flags.FLAG_DEDICATED_DEVICE_CONTROL_ENABLED,
                            Flags.FLAG_DEFAULT_SMS_PERSONAL_APP_SUSPENSION_FIX_ENABLED,
                            Flags.FLAG_DELETE_PRIVATE_SPACE_UNDER_RESTRICTION,
                            Flags.FLAG_DEVICE_POLICY_SIZE_TRACKING_ENABLED,
                            Flags.FLAG_DEVICE_POLICY_SIZE_TRACKING_INTERNAL_BUG_FIX_ENABLED,
                            Flags.FLAG_DEVICE_POLICY_SIZE_TRACKING_INTERNAL_ENABLED,
                            Flags.FLAG_DEVICE_THEFT_API_ENABLED,
                            Flags.FLAG_DEVICE_THEFT_IMPL_ENABLED,
                            Flags.FLAG_DISALLOW_USER_CONTROL_BG_USAGE_FIX,
                            Flags.FLAG_DISALLOW_USER_CONTROL_STOPPED_STATE_FIX,
                            Flags.FLAG_DMRH_SET_APP_RESTRICTIONS,
                            Flags.FLAG_DUMPSYS_POLICY_ENGINE_MIGRATION_ENABLED,
                            Flags.FLAG_ESIM_MANAGEMENT_ENABLED,
                            Flags.FLAG_ESIM_MANAGEMENT_UX_ENABLED,
                            Flags.FLAG_HEADLESS_DEVICE_OWNER_DELEGATE_SECURITY_LOGGING_BUG_FIX,
                            Flags.FLAG_HEADLESS_DEVICE_OWNER_PROVISIONING_FIX_ENABLED,
                            Flags.FLAG_HEADLESS_DEVICE_OWNER_SINGLE_USER_ENABLED,
                            Flags.FLAG_HEADLESS_SINGLE_MIN_TARGET_SDK,
                            Flags.FLAG_HEADLESS_SINGLE_USER_BAD_DEVICE_ADMIN_STATE_FIX,
                            Flags.FLAG_HEADLESS_SINGLE_USER_COMPATIBILITY_FIX,
                            Flags.FLAG_HEADLESS_SINGLE_USER_FIXES,
                            Flags.FLAG_HSUM_UNLOCK_NOTIFICATION_FIX,
                            Flags.FLAG_IS_MTE_POLICY_ENFORCED,
                            Flags.FLAG_IS_RECURSIVE_REQUIRED_APP_MERGING_ENABLED,
                            Flags.FLAG_ONBOARDING_BUGREPORT_STORAGE_BUG_FIX,
                            Flags.FLAG_ONBOARDING_BUGREPORT_V2_ENABLED,
                            Flags.FLAG_ONBOARDING_CONSENTLESS_BUGREPORTS,
                            Flags.FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_API_ENABLED,
                            Flags.FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_IMPL_ENABLED,
                            Flags.FLAG_POLICY_ENGINE_MIGRATION_V2_ENABLED,
                            Flags.FLAG_POWER_EXEMPTION_BG_USAGE_FIX,
                            Flags.FLAG_QUIET_MODE_CREDENTIAL_BUG_FIX,
                            Flags.FLAG_SECURITY_LOG_V2_ENABLED,
                            Flags.FLAG_UNMANAGED_MODE_MIGRATION,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean allowQueryingProfileType() {
        return getValue(
                Flags.FLAG_ALLOW_QUERYING_PROFILE_TYPE,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).allowQueryingProfileType();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean allowScreenBrightnessControlOnCope() {
        return getValue(
                Flags.FLAG_ALLOW_SCREEN_BRIGHTNESS_CONTROL_ON_COPE,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda30
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).allowScreenBrightnessControlOnCope();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean alwaysPersistDo() {
        return getValue(
                Flags.FLAG_ALWAYS_PERSIST_DO,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).alwaysPersistDo();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean assistContentUserRestrictionEnabled() {
        return getValue(
                Flags.FLAG_ASSIST_CONTENT_USER_RESTRICTION_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda43
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).assistContentUserRestrictionEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean backupConnectedAppsSettings() {
        return getValue(
                Flags.FLAG_BACKUP_CONNECTED_APPS_SETTINGS,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).backupConnectedAppsSettings();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean backupServiceSecurityLogEventEnabled() {
        return getValue(
                Flags.FLAG_BACKUP_SERVICE_SECURITY_LOG_EVENT_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).backupServiceSecurityLogEventEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean coexistenceMigrationForNonEmmManagementEnabled() {
        return getValue(
                Flags.FLAG_COEXISTENCE_MIGRATION_FOR_NON_EMM_MANAGEMENT_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj)
                                .coexistenceMigrationForNonEmmManagementEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean copyAccountWithRetryEnabled() {
        return getValue(
                Flags.FLAG_COPY_ACCOUNT_WITH_RETRY_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).copyAccountWithRetryEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean crossUserSuspensionEnabledRo() {
        return getValue(
                Flags.FLAG_CROSS_USER_SUSPENSION_ENABLED_RO,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).crossUserSuspensionEnabledRo();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean dedicatedDeviceControlApiEnabled() {
        return getValue(
                Flags.FLAG_DEDICATED_DEVICE_CONTROL_API_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).dedicatedDeviceControlApiEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean dedicatedDeviceControlEnabled() {
        return getValue(
                Flags.FLAG_DEDICATED_DEVICE_CONTROL_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).dedicatedDeviceControlEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean defaultSmsPersonalAppSuspensionFixEnabled() {
        return getValue(
                Flags.FLAG_DEFAULT_SMS_PERSONAL_APP_SUSPENSION_FIX_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda32
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).defaultSmsPersonalAppSuspensionFixEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean deletePrivateSpaceUnderRestriction() {
        return getValue(
                Flags.FLAG_DELETE_PRIVATE_SPACE_UNDER_RESTRICTION,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).deletePrivateSpaceUnderRestriction();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean devicePolicySizeTrackingEnabled() {
        return getValue(
                Flags.FLAG_DEVICE_POLICY_SIZE_TRACKING_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).devicePolicySizeTrackingEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean devicePolicySizeTrackingInternalBugFixEnabled() {
        return getValue(
                Flags.FLAG_DEVICE_POLICY_SIZE_TRACKING_INTERNAL_BUG_FIX_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda34
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).devicePolicySizeTrackingInternalBugFixEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean devicePolicySizeTrackingInternalEnabled() {
        return getValue(
                Flags.FLAG_DEVICE_POLICY_SIZE_TRACKING_INTERNAL_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda40
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).devicePolicySizeTrackingInternalEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean deviceTheftApiEnabled() {
        return getValue(
                Flags.FLAG_DEVICE_THEFT_API_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda37
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).deviceTheftApiEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean deviceTheftImplEnabled() {
        return getValue(
                Flags.FLAG_DEVICE_THEFT_IMPL_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).deviceTheftImplEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean disallowUserControlBgUsageFix() {
        return getValue(
                Flags.FLAG_DISALLOW_USER_CONTROL_BG_USAGE_FIX,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).disallowUserControlBgUsageFix();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean disallowUserControlStoppedStateFix() {
        return getValue(
                Flags.FLAG_DISALLOW_USER_CONTROL_STOPPED_STATE_FIX,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda31
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).disallowUserControlStoppedStateFix();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean dmrhSetAppRestrictions() {
        return getValue(
                Flags.FLAG_DMRH_SET_APP_RESTRICTIONS,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda39
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).dmrhSetAppRestrictions();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean dumpsysPolicyEngineMigrationEnabled() {
        return getValue(
                Flags.FLAG_DUMPSYS_POLICY_ENGINE_MIGRATION_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).dumpsysPolicyEngineMigrationEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean esimManagementEnabled() {
        return getValue(
                Flags.FLAG_ESIM_MANAGEMENT_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda41
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).esimManagementEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean esimManagementUxEnabled() {
        return getValue(
                Flags.FLAG_ESIM_MANAGEMENT_UX_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).esimManagementUxEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean headlessDeviceOwnerDelegateSecurityLoggingBugFix() {
        return getValue(
                Flags.FLAG_HEADLESS_DEVICE_OWNER_DELEGATE_SECURITY_LOGGING_BUG_FIX,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj)
                                .headlessDeviceOwnerDelegateSecurityLoggingBugFix();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean headlessDeviceOwnerProvisioningFixEnabled() {
        return getValue(
                Flags.FLAG_HEADLESS_DEVICE_OWNER_PROVISIONING_FIX_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).headlessDeviceOwnerProvisioningFixEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean headlessDeviceOwnerSingleUserEnabled() {
        return getValue(
                Flags.FLAG_HEADLESS_DEVICE_OWNER_SINGLE_USER_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).headlessDeviceOwnerSingleUserEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean headlessSingleMinTargetSdk() {
        return getValue(
                Flags.FLAG_HEADLESS_SINGLE_MIN_TARGET_SDK,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda36
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).headlessSingleMinTargetSdk();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean headlessSingleUserBadDeviceAdminStateFix() {
        return getValue(
                Flags.FLAG_HEADLESS_SINGLE_USER_BAD_DEVICE_ADMIN_STATE_FIX,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda33
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).headlessSingleUserBadDeviceAdminStateFix();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean headlessSingleUserCompatibilityFix() {
        return getValue(
                Flags.FLAG_HEADLESS_SINGLE_USER_COMPATIBILITY_FIX,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).headlessSingleUserCompatibilityFix();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean headlessSingleUserFixes() {
        return getValue(
                Flags.FLAG_HEADLESS_SINGLE_USER_FIXES,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda35
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).headlessSingleUserFixes();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean hsumUnlockNotificationFix() {
        return getValue(
                Flags.FLAG_HSUM_UNLOCK_NOTIFICATION_FIX,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda28
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).hsumUnlockNotificationFix();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean isMtePolicyEnforced() {
        return getValue(
                Flags.FLAG_IS_MTE_POLICY_ENFORCED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).isMtePolicyEnforced();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean isRecursiveRequiredAppMergingEnabled() {
        return getValue(
                Flags.FLAG_IS_RECURSIVE_REQUIRED_APP_MERGING_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda42
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).isRecursiveRequiredAppMergingEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean onboardingBugreportStorageBugFix() {
        return getValue(
                Flags.FLAG_ONBOARDING_BUGREPORT_STORAGE_BUG_FIX,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).onboardingBugreportStorageBugFix();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean onboardingBugreportV2Enabled() {
        return getValue(
                Flags.FLAG_ONBOARDING_BUGREPORT_V2_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda38
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).onboardingBugreportV2Enabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean onboardingConsentlessBugreports() {
        return getValue(
                Flags.FLAG_ONBOARDING_CONSENTLESS_BUGREPORTS,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).onboardingConsentlessBugreports();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean permissionMigrationForZeroTrustApiEnabled() {
        return getValue(
                Flags.FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_API_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).permissionMigrationForZeroTrustApiEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean permissionMigrationForZeroTrustImplEnabled() {
        return getValue(
                Flags.FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_IMPL_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).permissionMigrationForZeroTrustImplEnabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean policyEngineMigrationV2Enabled() {
        return getValue(
                Flags.FLAG_POLICY_ENGINE_MIGRATION_V2_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).policyEngineMigrationV2Enabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean powerExemptionBgUsageFix() {
        return getValue(
                Flags.FLAG_POWER_EXEMPTION_BG_USAGE_FIX,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).powerExemptionBgUsageFix();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean quietModeCredentialBugFix() {
        return getValue(
                Flags.FLAG_QUIET_MODE_CREDENTIAL_BUG_FIX,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).quietModeCredentialBugFix();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean securityLogV2Enabled() {
        return getValue(
                Flags.FLAG_SECURITY_LOG_V2_ENABLED,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).securityLogV2Enabled();
                    }
                });
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean unmanagedModeMigration() {
        return getValue(
                Flags.FLAG_UNMANAGED_MODE_MIGRATION,
                new Predicate() { // from class:
                    // android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda29
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).unmanagedModeMigration();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_ALLOW_QUERYING_PROFILE_TYPE,
                Flags.FLAG_ALLOW_SCREEN_BRIGHTNESS_CONTROL_ON_COPE,
                Flags.FLAG_ALWAYS_PERSIST_DO,
                Flags.FLAG_ASSIST_CONTENT_USER_RESTRICTION_ENABLED,
                Flags.FLAG_BACKUP_CONNECTED_APPS_SETTINGS,
                Flags.FLAG_BACKUP_SERVICE_SECURITY_LOG_EVENT_ENABLED,
                Flags.FLAG_COEXISTENCE_MIGRATION_FOR_NON_EMM_MANAGEMENT_ENABLED,
                Flags.FLAG_COPY_ACCOUNT_WITH_RETRY_ENABLED,
                Flags.FLAG_CROSS_USER_SUSPENSION_ENABLED_RO,
                Flags.FLAG_DEDICATED_DEVICE_CONTROL_API_ENABLED,
                Flags.FLAG_DEDICATED_DEVICE_CONTROL_ENABLED,
                Flags.FLAG_DEFAULT_SMS_PERSONAL_APP_SUSPENSION_FIX_ENABLED,
                Flags.FLAG_DELETE_PRIVATE_SPACE_UNDER_RESTRICTION,
                Flags.FLAG_DEVICE_POLICY_SIZE_TRACKING_ENABLED,
                Flags.FLAG_DEVICE_POLICY_SIZE_TRACKING_INTERNAL_BUG_FIX_ENABLED,
                Flags.FLAG_DEVICE_POLICY_SIZE_TRACKING_INTERNAL_ENABLED,
                Flags.FLAG_DEVICE_THEFT_API_ENABLED,
                Flags.FLAG_DEVICE_THEFT_IMPL_ENABLED,
                Flags.FLAG_DISALLOW_USER_CONTROL_BG_USAGE_FIX,
                Flags.FLAG_DISALLOW_USER_CONTROL_STOPPED_STATE_FIX,
                Flags.FLAG_DMRH_SET_APP_RESTRICTIONS,
                Flags.FLAG_DUMPSYS_POLICY_ENGINE_MIGRATION_ENABLED,
                Flags.FLAG_ESIM_MANAGEMENT_ENABLED,
                Flags.FLAG_ESIM_MANAGEMENT_UX_ENABLED,
                Flags.FLAG_HEADLESS_DEVICE_OWNER_DELEGATE_SECURITY_LOGGING_BUG_FIX,
                Flags.FLAG_HEADLESS_DEVICE_OWNER_PROVISIONING_FIX_ENABLED,
                Flags.FLAG_HEADLESS_DEVICE_OWNER_SINGLE_USER_ENABLED,
                Flags.FLAG_HEADLESS_SINGLE_MIN_TARGET_SDK,
                Flags.FLAG_HEADLESS_SINGLE_USER_BAD_DEVICE_ADMIN_STATE_FIX,
                Flags.FLAG_HEADLESS_SINGLE_USER_COMPATIBILITY_FIX,
                Flags.FLAG_HEADLESS_SINGLE_USER_FIXES,
                Flags.FLAG_HSUM_UNLOCK_NOTIFICATION_FIX,
                Flags.FLAG_IS_MTE_POLICY_ENFORCED,
                Flags.FLAG_IS_RECURSIVE_REQUIRED_APP_MERGING_ENABLED,
                Flags.FLAG_ONBOARDING_BUGREPORT_STORAGE_BUG_FIX,
                Flags.FLAG_ONBOARDING_BUGREPORT_V2_ENABLED,
                Flags.FLAG_ONBOARDING_CONSENTLESS_BUGREPORTS,
                Flags.FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_API_ENABLED,
                Flags.FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_IMPL_ENABLED,
                Flags.FLAG_POLICY_ENGINE_MIGRATION_V2_ENABLED,
                Flags.FLAG_POWER_EXEMPTION_BG_USAGE_FIX,
                Flags.FLAG_QUIET_MODE_CREDENTIAL_BUG_FIX,
                Flags.FLAG_SECURITY_LOG_V2_ENABLED,
                Flags.FLAG_UNMANAGED_MODE_MIGRATION);
    }
}
