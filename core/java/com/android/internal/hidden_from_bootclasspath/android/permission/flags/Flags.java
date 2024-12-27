package com.android.internal.hidden_from_bootclasspath.android.permission.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_APEX_SIGNATURE_PERMISSION_ALLOWLIST_ENABLED =
            "android.permission.flags.apex_signature_permission_allowlist_enabled";
    public static final String FLAG_DEVICE_AWARE_APP_OP_NEW_SCHEMA_ENABLED =
            "android.permission.flags.device_aware_app_op_new_schema_enabled";
    public static final String FLAG_DEVICE_AWARE_PERMISSIONS_ENABLED =
            "android.permission.flags.device_aware_permissions_enabled";
    public static final String FLAG_DEVICE_AWARE_PERMISSION_APIS_ENABLED =
            "android.permission.flags.device_aware_permission_apis_enabled";
    public static final String FLAG_DEVICE_ID_IN_OP_PROXY_INFO_ENABLED =
            "android.permission.flags.device_id_in_op_proxy_info_enabled";
    public static final String FLAG_ENHANCED_CONFIRMATION_MODE_APIS_ENABLED =
            "android.permission.flags.enhanced_confirmation_mode_apis_enabled";
    public static final String FLAG_FACTORY_RESET_PREP_PERMISSION_APIS =
            "android.permission.flags.factory_reset_prep_permission_apis";
    public static final String FLAG_FINISH_RUNNING_OPS_FOR_KILLED_PACKAGES =
            "android.permission.flags.finish_running_ops_for_killed_packages";
    public static final String FLAG_GET_EMERGENCY_ROLE_HOLDER_API_ENABLED =
            "android.permission.flags.get_emergency_role_holder_api_enabled";
    public static final String FLAG_IGNORE_PROCESS_TEXT =
            "android.permission.flags.ignore_process_text";
    public static final String FLAG_OP_ENABLE_MOBILE_DATA_BY_USER =
            "android.permission.flags.op_enable_mobile_data_by_user";
    public static final String FLAG_RETAIL_DEMO_ROLE_ENABLED =
            "android.permission.flags.retail_demo_role_enabled";
    public static final String FLAG_RUNTIME_PERMISSION_APPOPS_MAPPING_ENABLED =
            "android.permission.flags.runtime_permission_appops_mapping_enabled";
    public static final String FLAG_SENSITIVE_CONTENT_IMPROVEMENTS =
            "android.permission.flags.sensitive_content_improvements";
    public static final String FLAG_SENSITIVE_CONTENT_METRICS_BUGFIX =
            "android.permission.flags.sensitive_content_metrics_bugfix";
    public static final String FLAG_SENSITIVE_CONTENT_RECENTS_SCREENSHOT_BUGFIX =
            "android.permission.flags.sensitive_content_recents_screenshot_bugfix";
    public static final String FLAG_SENSITIVE_NOTIFICATION_APP_PROTECTION =
            "android.permission.flags.sensitive_notification_app_protection";
    public static final String FLAG_SERVER_SIDE_ATTRIBUTION_REGISTRATION =
            "android.permission.flags.server_side_attribution_registration";
    public static final String FLAG_SET_NEXT_ATTRIBUTION_SOURCE =
            "android.permission.flags.set_next_attribution_source";
    public static final String FLAG_SHOULD_REGISTER_ATTRIBUTION_SOURCE =
            "android.permission.flags.should_register_attribution_source";
    public static final String FLAG_SIGNATURE_PERMISSION_ALLOWLIST_ENABLED =
            "android.permission.flags.signature_permission_allowlist_enabled";
    public static final String FLAG_SYSTEM_SERVER_ROLE_CONTROLLER_ENABLED =
            "android.permission.flags.system_server_role_controller_enabled";
    public static final String FLAG_VOICE_ACTIVATION_PERMISSION_APIS =
            "android.permission.flags.voice_activation_permission_apis";
    public static final String FLAG_WALLET_ROLE_ENABLED =
            "android.permission.flags.wallet_role_enabled";

    public static boolean apexSignaturePermissionAllowlistEnabled() {
        return FEATURE_FLAGS.apexSignaturePermissionAllowlistEnabled();
    }

    public static boolean deviceAwareAppOpNewSchemaEnabled() {
        return FEATURE_FLAGS.deviceAwareAppOpNewSchemaEnabled();
    }

    public static boolean deviceAwarePermissionApisEnabled() {
        return FEATURE_FLAGS.deviceAwarePermissionApisEnabled();
    }

    public static boolean deviceAwarePermissionsEnabled() {
        return FEATURE_FLAGS.deviceAwarePermissionsEnabled();
    }

    public static boolean deviceIdInOpProxyInfoEnabled() {
        return FEATURE_FLAGS.deviceIdInOpProxyInfoEnabled();
    }

    public static boolean enhancedConfirmationModeApisEnabled() {
        return FEATURE_FLAGS.enhancedConfirmationModeApisEnabled();
    }

    public static boolean factoryResetPrepPermissionApis() {
        return FEATURE_FLAGS.factoryResetPrepPermissionApis();
    }

    public static boolean finishRunningOpsForKilledPackages() {
        return FEATURE_FLAGS.finishRunningOpsForKilledPackages();
    }

    public static boolean getEmergencyRoleHolderApiEnabled() {
        return FEATURE_FLAGS.getEmergencyRoleHolderApiEnabled();
    }

    public static boolean ignoreProcessText() {
        return FEATURE_FLAGS.ignoreProcessText();
    }

    public static boolean opEnableMobileDataByUser() {
        return FEATURE_FLAGS.opEnableMobileDataByUser();
    }

    public static boolean retailDemoRoleEnabled() {
        return FEATURE_FLAGS.retailDemoRoleEnabled();
    }

    public static boolean runtimePermissionAppopsMappingEnabled() {
        return FEATURE_FLAGS.runtimePermissionAppopsMappingEnabled();
    }

    public static boolean sensitiveContentImprovements() {
        return FEATURE_FLAGS.sensitiveContentImprovements();
    }

    public static boolean sensitiveContentMetricsBugfix() {
        return FEATURE_FLAGS.sensitiveContentMetricsBugfix();
    }

    public static boolean sensitiveContentRecentsScreenshotBugfix() {
        return FEATURE_FLAGS.sensitiveContentRecentsScreenshotBugfix();
    }

    public static boolean sensitiveNotificationAppProtection() {
        return FEATURE_FLAGS.sensitiveNotificationAppProtection();
    }

    public static boolean serverSideAttributionRegistration() {
        return FEATURE_FLAGS.serverSideAttributionRegistration();
    }

    public static boolean setNextAttributionSource() {
        return FEATURE_FLAGS.setNextAttributionSource();
    }

    public static boolean shouldRegisterAttributionSource() {
        return FEATURE_FLAGS.shouldRegisterAttributionSource();
    }

    public static boolean signaturePermissionAllowlistEnabled() {
        return FEATURE_FLAGS.signaturePermissionAllowlistEnabled();
    }

    public static boolean systemServerRoleControllerEnabled() {
        return FEATURE_FLAGS.systemServerRoleControllerEnabled();
    }

    public static boolean voiceActivationPermissionApis() {
        return FEATURE_FLAGS.voiceActivationPermissionApis();
    }

    public static boolean walletRoleEnabled() {
        return FEATURE_FLAGS.walletRoleEnabled();
    }
}
