package android.app.admin.flags;

/* loaded from: classes.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ALLOW_QUERYING_PROFILE_TYPE =
            "android.app.admin.flags.allow_querying_profile_type";
    public static final String FLAG_ALLOW_SCREEN_BRIGHTNESS_CONTROL_ON_COPE =
            "android.app.admin.flags.allow_screen_brightness_control_on_cope";
    public static final String FLAG_ALWAYS_PERSIST_DO = "android.app.admin.flags.always_persist_do";
    public static final String FLAG_ASSIST_CONTENT_USER_RESTRICTION_ENABLED =
            "android.app.admin.flags.assist_content_user_restriction_enabled";
    public static final String FLAG_BACKUP_CONNECTED_APPS_SETTINGS =
            "android.app.admin.flags.backup_connected_apps_settings";
    public static final String FLAG_BACKUP_SERVICE_SECURITY_LOG_EVENT_ENABLED =
            "android.app.admin.flags.backup_service_security_log_event_enabled";
    public static final String FLAG_COEXISTENCE_MIGRATION_FOR_NON_EMM_MANAGEMENT_ENABLED =
            "android.app.admin.flags.coexistence_migration_for_non_emm_management_enabled";
    public static final String FLAG_COPY_ACCOUNT_WITH_RETRY_ENABLED =
            "android.app.admin.flags.copy_account_with_retry_enabled";
    public static final String FLAG_CROSS_USER_SUSPENSION_ENABLED_RO =
            "android.app.admin.flags.cross_user_suspension_enabled_ro";
    public static final String FLAG_DEDICATED_DEVICE_CONTROL_API_ENABLED =
            "android.app.admin.flags.dedicated_device_control_api_enabled";
    public static final String FLAG_DEDICATED_DEVICE_CONTROL_ENABLED =
            "android.app.admin.flags.dedicated_device_control_enabled";
    public static final String FLAG_DEFAULT_SMS_PERSONAL_APP_SUSPENSION_FIX_ENABLED =
            "android.app.admin.flags.default_sms_personal_app_suspension_fix_enabled";
    public static final String FLAG_DELETE_PRIVATE_SPACE_UNDER_RESTRICTION =
            "android.app.admin.flags.delete_private_space_under_restriction";
    public static final String FLAG_DEVICE_POLICY_SIZE_TRACKING_ENABLED =
            "android.app.admin.flags.device_policy_size_tracking_enabled";
    public static final String FLAG_DEVICE_POLICY_SIZE_TRACKING_INTERNAL_BUG_FIX_ENABLED =
            "android.app.admin.flags.device_policy_size_tracking_internal_bug_fix_enabled";
    public static final String FLAG_DEVICE_POLICY_SIZE_TRACKING_INTERNAL_ENABLED =
            "android.app.admin.flags.device_policy_size_tracking_internal_enabled";
    public static final String FLAG_DEVICE_THEFT_API_ENABLED =
            "android.app.admin.flags.device_theft_api_enabled";
    public static final String FLAG_DEVICE_THEFT_IMPL_ENABLED =
            "android.app.admin.flags.device_theft_impl_enabled";
    public static final String FLAG_DISALLOW_USER_CONTROL_BG_USAGE_FIX =
            "android.app.admin.flags.disallow_user_control_bg_usage_fix";
    public static final String FLAG_DISALLOW_USER_CONTROL_STOPPED_STATE_FIX =
            "android.app.admin.flags.disallow_user_control_stopped_state_fix";
    public static final String FLAG_DMRH_SET_APP_RESTRICTIONS =
            "android.app.admin.flags.dmrh_set_app_restrictions";
    public static final String FLAG_DUMPSYS_POLICY_ENGINE_MIGRATION_ENABLED =
            "android.app.admin.flags.dumpsys_policy_engine_migration_enabled";
    public static final String FLAG_ESIM_MANAGEMENT_ENABLED =
            "android.app.admin.flags.esim_management_enabled";
    public static final String FLAG_ESIM_MANAGEMENT_UX_ENABLED =
            "android.app.admin.flags.esim_management_ux_enabled";
    public static final String FLAG_HEADLESS_DEVICE_OWNER_DELEGATE_SECURITY_LOGGING_BUG_FIX =
            "android.app.admin.flags.headless_device_owner_delegate_security_logging_bug_fix";
    public static final String FLAG_HEADLESS_DEVICE_OWNER_PROVISIONING_FIX_ENABLED =
            "android.app.admin.flags.headless_device_owner_provisioning_fix_enabled";
    public static final String FLAG_HEADLESS_DEVICE_OWNER_SINGLE_USER_ENABLED =
            "android.app.admin.flags.headless_device_owner_single_user_enabled";
    public static final String FLAG_HEADLESS_SINGLE_MIN_TARGET_SDK =
            "android.app.admin.flags.headless_single_min_target_sdk";
    public static final String FLAG_HEADLESS_SINGLE_USER_BAD_DEVICE_ADMIN_STATE_FIX =
            "android.app.admin.flags.headless_single_user_bad_device_admin_state_fix";
    public static final String FLAG_HEADLESS_SINGLE_USER_COMPATIBILITY_FIX =
            "android.app.admin.flags.headless_single_user_compatibility_fix";
    public static final String FLAG_HEADLESS_SINGLE_USER_FIXES =
            "android.app.admin.flags.headless_single_user_fixes";
    public static final String FLAG_HSUM_UNLOCK_NOTIFICATION_FIX =
            "android.app.admin.flags.hsum_unlock_notification_fix";
    public static final String FLAG_IS_MTE_POLICY_ENFORCED =
            "android.app.admin.flags.is_mte_policy_enforced";
    public static final String FLAG_IS_RECURSIVE_REQUIRED_APP_MERGING_ENABLED =
            "android.app.admin.flags.is_recursive_required_app_merging_enabled";
    public static final String FLAG_ONBOARDING_BUGREPORT_STORAGE_BUG_FIX =
            "android.app.admin.flags.onboarding_bugreport_storage_bug_fix";
    public static final String FLAG_ONBOARDING_BUGREPORT_V2_ENABLED =
            "android.app.admin.flags.onboarding_bugreport_v2_enabled";
    public static final String FLAG_ONBOARDING_CONSENTLESS_BUGREPORTS =
            "android.app.admin.flags.onboarding_consentless_bugreports";
    public static final String FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_API_ENABLED =
            "android.app.admin.flags.permission_migration_for_zero_trust_api_enabled";
    public static final String FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_IMPL_ENABLED =
            "android.app.admin.flags.permission_migration_for_zero_trust_impl_enabled";
    public static final String FLAG_POLICY_ENGINE_MIGRATION_V2_ENABLED =
            "android.app.admin.flags.policy_engine_migration_v2_enabled";
    public static final String FLAG_POWER_EXEMPTION_BG_USAGE_FIX =
            "android.app.admin.flags.power_exemption_bg_usage_fix";
    public static final String FLAG_QUIET_MODE_CREDENTIAL_BUG_FIX =
            "android.app.admin.flags.quiet_mode_credential_bug_fix";
    public static final String FLAG_SECURITY_LOG_V2_ENABLED =
            "android.app.admin.flags.security_log_v2_enabled";
    public static final String FLAG_UNMANAGED_MODE_MIGRATION =
            "android.app.admin.flags.unmanaged_mode_migration";

    public static boolean allowQueryingProfileType() {
        return FEATURE_FLAGS.allowQueryingProfileType();
    }

    public static boolean allowScreenBrightnessControlOnCope() {
        return FEATURE_FLAGS.allowScreenBrightnessControlOnCope();
    }

    public static boolean alwaysPersistDo() {
        return FEATURE_FLAGS.alwaysPersistDo();
    }

    public static boolean assistContentUserRestrictionEnabled() {
        return FEATURE_FLAGS.assistContentUserRestrictionEnabled();
    }

    public static boolean backupConnectedAppsSettings() {
        return FEATURE_FLAGS.backupConnectedAppsSettings();
    }

    public static boolean backupServiceSecurityLogEventEnabled() {
        return FEATURE_FLAGS.backupServiceSecurityLogEventEnabled();
    }

    public static boolean coexistenceMigrationForNonEmmManagementEnabled() {
        return FEATURE_FLAGS.coexistenceMigrationForNonEmmManagementEnabled();
    }

    public static boolean copyAccountWithRetryEnabled() {
        return FEATURE_FLAGS.copyAccountWithRetryEnabled();
    }

    public static boolean crossUserSuspensionEnabledRo() {
        return FEATURE_FLAGS.crossUserSuspensionEnabledRo();
    }

    public static boolean dedicatedDeviceControlApiEnabled() {
        return FEATURE_FLAGS.dedicatedDeviceControlApiEnabled();
    }

    public static boolean dedicatedDeviceControlEnabled() {
        return FEATURE_FLAGS.dedicatedDeviceControlEnabled();
    }

    public static boolean defaultSmsPersonalAppSuspensionFixEnabled() {
        return FEATURE_FLAGS.defaultSmsPersonalAppSuspensionFixEnabled();
    }

    public static boolean deletePrivateSpaceUnderRestriction() {
        return FEATURE_FLAGS.deletePrivateSpaceUnderRestriction();
    }

    public static boolean devicePolicySizeTrackingEnabled() {
        return FEATURE_FLAGS.devicePolicySizeTrackingEnabled();
    }

    public static boolean devicePolicySizeTrackingInternalBugFixEnabled() {
        return FEATURE_FLAGS.devicePolicySizeTrackingInternalBugFixEnabled();
    }

    public static boolean devicePolicySizeTrackingInternalEnabled() {
        return FEATURE_FLAGS.devicePolicySizeTrackingInternalEnabled();
    }

    public static boolean deviceTheftApiEnabled() {
        return FEATURE_FLAGS.deviceTheftApiEnabled();
    }

    public static boolean deviceTheftImplEnabled() {
        return FEATURE_FLAGS.deviceTheftImplEnabled();
    }

    public static boolean disallowUserControlBgUsageFix() {
        return FEATURE_FLAGS.disallowUserControlBgUsageFix();
    }

    public static boolean disallowUserControlStoppedStateFix() {
        return FEATURE_FLAGS.disallowUserControlStoppedStateFix();
    }

    public static boolean dmrhSetAppRestrictions() {
        return FEATURE_FLAGS.dmrhSetAppRestrictions();
    }

    public static boolean dumpsysPolicyEngineMigrationEnabled() {
        return FEATURE_FLAGS.dumpsysPolicyEngineMigrationEnabled();
    }

    public static boolean esimManagementEnabled() {
        return FEATURE_FLAGS.esimManagementEnabled();
    }

    public static boolean esimManagementUxEnabled() {
        return FEATURE_FLAGS.esimManagementUxEnabled();
    }

    public static boolean headlessDeviceOwnerDelegateSecurityLoggingBugFix() {
        return FEATURE_FLAGS.headlessDeviceOwnerDelegateSecurityLoggingBugFix();
    }

    public static boolean headlessDeviceOwnerProvisioningFixEnabled() {
        return FEATURE_FLAGS.headlessDeviceOwnerProvisioningFixEnabled();
    }

    public static boolean headlessDeviceOwnerSingleUserEnabled() {
        return FEATURE_FLAGS.headlessDeviceOwnerSingleUserEnabled();
    }

    public static boolean headlessSingleMinTargetSdk() {
        return FEATURE_FLAGS.headlessSingleMinTargetSdk();
    }

    public static boolean headlessSingleUserBadDeviceAdminStateFix() {
        return FEATURE_FLAGS.headlessSingleUserBadDeviceAdminStateFix();
    }

    public static boolean headlessSingleUserCompatibilityFix() {
        return FEATURE_FLAGS.headlessSingleUserCompatibilityFix();
    }

    public static boolean headlessSingleUserFixes() {
        return FEATURE_FLAGS.headlessSingleUserFixes();
    }

    public static boolean hsumUnlockNotificationFix() {
        return FEATURE_FLAGS.hsumUnlockNotificationFix();
    }

    public static boolean isMtePolicyEnforced() {
        return FEATURE_FLAGS.isMtePolicyEnforced();
    }

    public static boolean isRecursiveRequiredAppMergingEnabled() {
        return FEATURE_FLAGS.isRecursiveRequiredAppMergingEnabled();
    }

    public static boolean onboardingBugreportStorageBugFix() {
        return FEATURE_FLAGS.onboardingBugreportStorageBugFix();
    }

    public static boolean onboardingBugreportV2Enabled() {
        return FEATURE_FLAGS.onboardingBugreportV2Enabled();
    }

    public static boolean onboardingConsentlessBugreports() {
        return FEATURE_FLAGS.onboardingConsentlessBugreports();
    }

    public static boolean permissionMigrationForZeroTrustApiEnabled() {
        return FEATURE_FLAGS.permissionMigrationForZeroTrustApiEnabled();
    }

    public static boolean permissionMigrationForZeroTrustImplEnabled() {
        return FEATURE_FLAGS.permissionMigrationForZeroTrustImplEnabled();
    }

    public static boolean policyEngineMigrationV2Enabled() {
        return FEATURE_FLAGS.policyEngineMigrationV2Enabled();
    }

    public static boolean powerExemptionBgUsageFix() {
        return FEATURE_FLAGS.powerExemptionBgUsageFix();
    }

    public static boolean quietModeCredentialBugFix() {
        return FEATURE_FLAGS.quietModeCredentialBugFix();
    }

    public static boolean securityLogV2Enabled() {
        return FEATURE_FLAGS.securityLogV2Enabled();
    }

    public static boolean unmanagedModeMigration() {
        return FEATURE_FLAGS.unmanagedModeMigration();
    }
}
