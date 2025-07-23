package com.android.internal.hidden_from_bootclasspath.android.app.admin.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ACTIVE_ADMIN_CLEANUP = "android.app.admin.flags.active_admin_cleanup";
    public static final String FLAG_ALLOW_QUERYING_PROFILE_TYPE = "android.app.admin.flags.allow_querying_profile_type";
    public static final String FLAG_ASSIST_CONTENT_USER_RESTRICTION_ENABLED = "android.app.admin.flags.assist_content_user_restriction_enabled";
    public static final String FLAG_BACKUP_CONNECTED_APPS_SETTINGS = "android.app.admin.flags.backup_connected_apps_settings";
    public static final String FLAG_BACKUP_SERVICE_SECURITY_LOG_EVENT_ENABLED = "android.app.admin.flags.backup_service_security_log_event_enabled";
    public static final String FLAG_COEXISTENCE_MIGRATION_FOR_SUPERVISION_ENABLED = "android.app.admin.flags.coexistence_migration_for_supervision_enabled";
    public static final String FLAG_CROSS_USER_SUSPENSION_ENABLED_RO = "android.app.admin.flags.cross_user_suspension_enabled_ro";
    public static final String FLAG_DEDICATED_DEVICE_CONTROL_API_ENABLED = "android.app.admin.flags.dedicated_device_control_api_enabled";
    public static final String FLAG_DEFAULT_SMS_PERSONAL_APP_SUSPENSION_FIX_ENABLED = "android.app.admin.flags.default_sms_personal_app_suspension_fix_enabled";
    public static final String FLAG_DEVICE_POLICY_SIZE_TRACKING_ENABLED = "android.app.admin.flags.device_policy_size_tracking_enabled";
    public static final String FLAG_DEVICE_THEFT_API_ENABLED = "android.app.admin.flags.device_theft_api_enabled";
    public static final String FLAG_DEVICE_THEFT_IMPL_ENABLED = "android.app.admin.flags.device_theft_impl_enabled";
    public static final String FLAG_DISALLOW_USER_CONTROL_STOPPED_STATE_FIX = "android.app.admin.flags.disallow_user_control_stopped_state_fix";
    public static final String FLAG_ENABLE_SUPERVISION_SERVICE_SYNC = "android.app.admin.flags.enable_supervision_service_sync";
    public static final String FLAG_ESIM_MANAGEMENT_ENABLED = "android.app.admin.flags.esim_management_enabled";
    public static final String FLAG_ESIM_MANAGEMENT_UX_ENABLED = "android.app.admin.flags.esim_management_ux_enabled";
    public static final String FLAG_HEADLESS_DEVICE_OWNER_SINGLE_USER_ENABLED = "android.app.admin.flags.headless_device_owner_single_user_enabled";
    public static final String FLAG_HEADLESS_SINGLE_MIN_TARGET_SDK = "android.app.admin.flags.headless_single_min_target_sdk";
    public static final String FLAG_INTERNAL_LOG_EVENT_LISTENER = "android.app.admin.flags.internal_log_event_listener";
    public static final String FLAG_IS_MTE_POLICY_ENFORCED = "android.app.admin.flags.is_mte_policy_enforced";
    public static final String FLAG_IS_RECURSIVE_REQUIRED_APP_MERGING_ENABLED = "android.app.admin.flags.is_recursive_required_app_merging_enabled";
    public static final String FLAG_LOCK_NOW_COEXISTENCE = "android.app.admin.flags.lock_now_coexistence";
    public static final String FLAG_MANAGEMENT_MODE_POLICY_METRICS = "android.app.admin.flags.management_mode_policy_metrics";
    public static final String FLAG_ONBOARDING_BUGREPORT_STORAGE_BUG_FIX = "android.app.admin.flags.onboarding_bugreport_storage_bug_fix";
    public static final String FLAG_ONBOARDING_BUGREPORT_V2_ENABLED = "android.app.admin.flags.onboarding_bugreport_v2_enabled";
    public static final String FLAG_ONBOARDING_CONSENTLESS_BUGREPORTS = "android.app.admin.flags.onboarding_consentless_bugreports";
    public static final String FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_API_ENABLED = "android.app.admin.flags.permission_migration_for_zero_trust_api_enabled";
    public static final String FLAG_POLICY_ENGINE_MIGRATION_V2_ENABLED = "android.app.admin.flags.policy_engine_migration_v2_enabled";
    public static final String FLAG_PROVISIONING_CONTEXT_PARAMETER = "android.app.admin.flags.provisioning_context_parameter";
    public static final String FLAG_QUIET_MODE_CREDENTIAL_BUG_FIX = "android.app.admin.flags.quiet_mode_credential_bug_fix";
    public static final String FLAG_REMOVE_MANAGED_ESIM_ON_WORK_PROFILE_DELETION = "android.app.admin.flags.remove_managed_esim_on_work_profile_deletion";
    public static final String FLAG_REMOVE_MANAGED_PROFILE_ENABLED = "android.app.admin.flags.remove_managed_profile_enabled";
    public static final String FLAG_RESET_PASSWORD_WITH_TOKEN_COEXISTENCE = "android.app.admin.flags.reset_password_with_token_coexistence";
    public static final String FLAG_SECONDARY_LOCKSCREEN_API_ENABLED = "android.app.admin.flags.secondary_lockscreen_api_enabled";
    public static final String FLAG_SECURITY_LOG_V2_ENABLED = "android.app.admin.flags.security_log_v2_enabled";
    public static final String FLAG_SET_APPLICATION_RESTRICTIONS_COEXISTENCE = "android.app.admin.flags.set_application_restrictions_coexistence";
    public static final String FLAG_SET_AUTO_TIME_ENABLED_COEXISTENCE = "android.app.admin.flags.set_auto_time_enabled_coexistence";
    public static final String FLAG_SET_AUTO_TIME_ZONE_ENABLED_COEXISTENCE = "android.app.admin.flags.set_auto_time_zone_enabled_coexistence";
    public static final String FLAG_SET_BACKUP_SERVICE_ENABLED_COEXISTENCE = "android.app.admin.flags.set_backup_service_enabled_coexistence";
    public static final String FLAG_SET_KEYGUARD_DISABLED_FEATURES_COEXISTENCE = "android.app.admin.flags.set_keyguard_disabled_features_coexistence";
    public static final String FLAG_SET_MTE_POLICY_COEXISTENCE = "android.app.admin.flags.set_mte_policy_coexistence";
    public static final String FLAG_SET_PERMISSION_GRANT_STATE_COEXISTENCE = "android.app.admin.flags.set_permission_grant_state_coexistence";
    public static final String FLAG_SPLIT_CREATE_MANAGED_PROFILE_ENABLED = "android.app.admin.flags.split_create_managed_profile_enabled";
    public static final String FLAG_SUSPEND_PACKAGES_COEXISTENCE = "android.app.admin.flags.suspend_packages_coexistence";
    public static final String FLAG_UNMANAGED_MODE_MIGRATION = "android.app.admin.flags.unmanaged_mode_migration";
    public static final String FLAG_UNSUSPEND_NOT_SUSPENDED = "android.app.admin.flags.unsuspend_not_suspended";
    public static final String FLAG_USER_PROVISIONING_SAME_STATE = "android.app.admin.flags.user_provisioning_same_state";
    public static final String FLAG_USE_POLICY_INTERSECTION_FOR_PERMITTED_INPUT_METHODS = "android.app.admin.flags.use_policy_intersection_for_permitted_input_methods";

    public static boolean activeAdminCleanup() {
        return FEATURE_FLAGS.activeAdminCleanup();
    }

    public static boolean allowQueryingProfileType() {
        return FEATURE_FLAGS.allowQueryingProfileType();
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

    public static boolean coexistenceMigrationForSupervisionEnabled() {
        return FEATURE_FLAGS.coexistenceMigrationForSupervisionEnabled();
    }

    public static boolean crossUserSuspensionEnabledRo() {
        return FEATURE_FLAGS.crossUserSuspensionEnabledRo();
    }

    public static boolean dedicatedDeviceControlApiEnabled() {
        return FEATURE_FLAGS.dedicatedDeviceControlApiEnabled();
    }

    public static boolean defaultSmsPersonalAppSuspensionFixEnabled() {
        return FEATURE_FLAGS.defaultSmsPersonalAppSuspensionFixEnabled();
    }

    public static boolean devicePolicySizeTrackingEnabled() {
        return FEATURE_FLAGS.devicePolicySizeTrackingEnabled();
    }

    public static boolean deviceTheftApiEnabled() {
        return FEATURE_FLAGS.deviceTheftApiEnabled();
    }

    public static boolean deviceTheftImplEnabled() {
        return FEATURE_FLAGS.deviceTheftImplEnabled();
    }

    public static boolean disallowUserControlStoppedStateFix() {
        return FEATURE_FLAGS.disallowUserControlStoppedStateFix();
    }

    public static boolean enableSupervisionServiceSync() {
        return FEATURE_FLAGS.enableSupervisionServiceSync();
    }

    public static boolean esimManagementEnabled() {
        return FEATURE_FLAGS.esimManagementEnabled();
    }

    public static boolean esimManagementUxEnabled() {
        return FEATURE_FLAGS.esimManagementUxEnabled();
    }

    public static boolean headlessDeviceOwnerSingleUserEnabled() {
        return FEATURE_FLAGS.headlessDeviceOwnerSingleUserEnabled();
    }

    public static boolean headlessSingleMinTargetSdk() {
        return FEATURE_FLAGS.headlessSingleMinTargetSdk();
    }

    public static boolean internalLogEventListener() {
        return FEATURE_FLAGS.internalLogEventListener();
    }

    public static boolean isMtePolicyEnforced() {
        return FEATURE_FLAGS.isMtePolicyEnforced();
    }

    public static boolean isRecursiveRequiredAppMergingEnabled() {
        return FEATURE_FLAGS.isRecursiveRequiredAppMergingEnabled();
    }

    public static boolean lockNowCoexistence() {
        return FEATURE_FLAGS.lockNowCoexistence();
    }

    public static boolean managementModePolicyMetrics() {
        return FEATURE_FLAGS.managementModePolicyMetrics();
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

    public static boolean policyEngineMigrationV2Enabled() {
        return FEATURE_FLAGS.policyEngineMigrationV2Enabled();
    }

    public static boolean provisioningContextParameter() {
        return FEATURE_FLAGS.provisioningContextParameter();
    }

    public static boolean quietModeCredentialBugFix() {
        return FEATURE_FLAGS.quietModeCredentialBugFix();
    }

    public static boolean removeManagedEsimOnWorkProfileDeletion() {
        return FEATURE_FLAGS.removeManagedEsimOnWorkProfileDeletion();
    }

    public static boolean removeManagedProfileEnabled() {
        return FEATURE_FLAGS.removeManagedProfileEnabled();
    }

    public static boolean resetPasswordWithTokenCoexistence() {
        return FEATURE_FLAGS.resetPasswordWithTokenCoexistence();
    }

    public static boolean secondaryLockscreenApiEnabled() {
        return FEATURE_FLAGS.secondaryLockscreenApiEnabled();
    }

    public static boolean securityLogV2Enabled() {
        return FEATURE_FLAGS.securityLogV2Enabled();
    }

    public static boolean setApplicationRestrictionsCoexistence() {
        return FEATURE_FLAGS.setApplicationRestrictionsCoexistence();
    }

    public static boolean setAutoTimeEnabledCoexistence() {
        return FEATURE_FLAGS.setAutoTimeEnabledCoexistence();
    }

    public static boolean setAutoTimeZoneEnabledCoexistence() {
        return FEATURE_FLAGS.setAutoTimeZoneEnabledCoexistence();
    }

    public static boolean setBackupServiceEnabledCoexistence() {
        return FEATURE_FLAGS.setBackupServiceEnabledCoexistence();
    }

    public static boolean setKeyguardDisabledFeaturesCoexistence() {
        return FEATURE_FLAGS.setKeyguardDisabledFeaturesCoexistence();
    }

    public static boolean setMtePolicyCoexistence() {
        return FEATURE_FLAGS.setMtePolicyCoexistence();
    }

    public static boolean setPermissionGrantStateCoexistence() {
        return FEATURE_FLAGS.setPermissionGrantStateCoexistence();
    }

    public static boolean splitCreateManagedProfileEnabled() {
        return FEATURE_FLAGS.splitCreateManagedProfileEnabled();
    }

    public static boolean suspendPackagesCoexistence() {
        return FEATURE_FLAGS.suspendPackagesCoexistence();
    }

    public static boolean unmanagedModeMigration() {
        return FEATURE_FLAGS.unmanagedModeMigration();
    }

    public static boolean unsuspendNotSuspended() {
        return FEATURE_FLAGS.unsuspendNotSuspended();
    }

    public static boolean usePolicyIntersectionForPermittedInputMethods() {
        return FEATURE_FLAGS.usePolicyIntersectionForPermittedInputMethods();
    }

    public static boolean userProvisioningSameState() {
        return FEATURE_FLAGS.userProvisioningSameState();
    }
}
