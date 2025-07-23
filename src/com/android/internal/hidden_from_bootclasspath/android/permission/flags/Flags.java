package com.android.internal.hidden_from_bootclasspath.android.permission.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ALLOW_HOST_PERMISSION_DIALOGS_ON_VIRTUAL_DEVICES = "android.permission.flags.allow_host_permission_dialogs_on_virtual_devices";
    public static final String FLAG_APPOP_ACCESS_TRACKING_LOGGING_ENABLED = "android.permission.flags.appop_access_tracking_logging_enabled";
    public static final String FLAG_APPOP_MODE_CACHING_ENABLED = "android.permission.flags.appop_mode_caching_enabled";
    public static final String FLAG_APP_OPS_SERVICE_HANDLER_FIX = "android.permission.flags.app_ops_service_handler_fix";
    public static final String FLAG_CHECK_OP_OVERLOAD_API_ENABLED = "android.permission.flags.check_op_overload_api_enabled";
    public static final String FLAG_CHECK_OP_VALIDATE_PACKAGE = "android.permission.flags.check_op_validate_package";
    public static final String FLAG_DELAY_UID_STATE_CHANGES_FROM_CAPABILITY_UPDATES = "android.permission.flags.delay_uid_state_changes_from_capability_updates";
    public static final String FLAG_DEVICE_AWARE_APP_OP_NEW_SCHEMA_ENABLED = "android.permission.flags.device_aware_app_op_new_schema_enabled";
    public static final String FLAG_DEVICE_AWARE_PERMISSIONS_ENABLED = "android.permission.flags.device_aware_permissions_enabled";
    public static final String FLAG_DEVICE_AWARE_PERMISSION_APIS_ENABLED = "android.permission.flags.device_aware_permission_apis_enabled";
    public static final String FLAG_DEVICE_ID_IN_OP_PROXY_INFO_ENABLED = "android.permission.flags.device_id_in_op_proxy_info_enabled";
    public static final String FLAG_DEVICE_POLICY_MANAGEMENT_ROLE_SPLIT_CREATE_MANAGED_PROFILE_ENABLED = "android.permission.flags.device_policy_management_role_split_create_managed_profile_enabled";
    public static final String FLAG_DONT_REMOVE_EXISTING_UID_STATES = "android.permission.flags.dont_remove_existing_uid_states";
    public static final String FLAG_ENABLE_AIAI_PROXIED_TEXT_CLASSIFIERS = "android.permission.flags.enable_aiai_proxied_text_classifiers";
    public static final String FLAG_ENABLE_ALL_SQLITE_APPOPS_ACCESSES = "android.permission.flags.enable_all_sqlite_appops_accesses";
    public static final String FLAG_ENABLE_OTP_IN_TEXT_CLASSIFIERS = "android.permission.flags.enable_otp_in_text_classifiers";
    public static final String FLAG_ENABLE_SQLITE_APPOPS_ACCESSES = "android.permission.flags.enable_sqlite_appops_accesses";
    public static final String FLAG_ENABLE_SYSTEM_SUPERVISION_ROLE_BEHAVIOR = "android.permission.flags.enable_system_supervision_role_behavior";
    public static final String FLAG_ENFORCE_DEFAULT_DEVICE_ID_IN_MY_ATTRIBUTION_SOURCE = "android.permission.flags.enforce_default_device_id_in_my_attribution_source";
    public static final String FLAG_ENHANCED_CONFIRMATION_IN_CALL_APIS_ENABLED = "android.permission.flags.enhanced_confirmation_in_call_apis_enabled";
    public static final String FLAG_ENHANCED_CONFIRMATION_MODE_APIS_ENABLED = "android.permission.flags.enhanced_confirmation_mode_apis_enabled";
    public static final String FLAG_FACTORY_RESET_PREP_PERMISSION_APIS = "android.permission.flags.factory_reset_prep_permission_apis";
    public static final String FLAG_FINE_POWER_MONITOR_PERMISSION = "android.permission.flags.fine_power_monitor_permission";
    public static final String FLAG_FINISH_RUNNING_OPS_FOR_KILLED_PACKAGES = "android.permission.flags.finish_running_ops_for_killed_packages";
    public static final String FLAG_GET_EMERGENCY_ROLE_HOLDER_API_ENABLED = "android.permission.flags.get_emergency_role_holder_api_enabled";
    public static final String FLAG_GRANT_READ_BLOCKED_NUMBERS_TO_SYSTEM_UI_INTELLIGENCE = "android.permission.flags.grant_read_blocked_numbers_to_system_ui_intelligence";
    public static final String FLAG_HEALTH_CONNECT_BACKUP_RESTORE_PERMISSION_ENABLED = "android.permission.flags.health_connect_backup_restore_permission_enabled";
    public static final String FLAG_IGNORE_PROCESS_TEXT = "android.permission.flags.ignore_process_text";
    public static final String FLAG_LOCATION_BYPASS_PRIVACY_DASHBOARD_ENABLED = "android.permission.flags.location_bypass_privacy_dashboard_enabled";
    public static final String FLAG_NOTE_OP_BATCHING_ENABLED = "android.permission.flags.note_op_batching_enabled";
    public static final String FLAG_OP_ENABLE_MOBILE_DATA_BY_USER = "android.permission.flags.op_enable_mobile_data_by_user";
    public static final String FLAG_PERMISSION_REQUEST_SHORT_CIRCUIT_ENABLED = "android.permission.flags.permission_request_short_circuit_enabled";
    public static final String FLAG_PERMISSION_TREE_APIS_DEPRECATED = "android.permission.flags.permission_tree_apis_deprecated";
    public static final String FLAG_RANGING_PERMISSION_ENABLED = "android.permission.flags.ranging_permission_enabled";
    public static final String FLAG_RATE_LIMIT_BATCHED_NOTE_OP_ASYNC_CALLBACKS_ENABLED = "android.permission.flags.rate_limit_batched_note_op_async_callbacks_enabled";
    public static final String FLAG_RECORD_ALL_RUNTIME_APPOPS_SQLITE = "android.permission.flags.record_all_runtime_appops_sqlite";
    public static final String FLAG_REPLACE_BODY_SENSOR_PERMISSION_ENABLED = "android.permission.flags.replace_body_sensor_permission_enabled";
    public static final String FLAG_RETAIL_DEMO_ROLE_ENABLED = "android.permission.flags.retail_demo_role_enabled";
    public static final String FLAG_RUNTIME_PERMISSION_APPOPS_MAPPING_ENABLED = "android.permission.flags.runtime_permission_appops_mapping_enabled";
    public static final String FLAG_SENSITIVE_CONTENT_IMPROVEMENTS = "android.permission.flags.sensitive_content_improvements";
    public static final String FLAG_SENSITIVE_CONTENT_METRICS_BUGFIX = "android.permission.flags.sensitive_content_metrics_bugfix";
    public static final String FLAG_SENSITIVE_CONTENT_RECENTS_SCREENSHOT_BUGFIX = "android.permission.flags.sensitive_content_recents_screenshot_bugfix";
    public static final String FLAG_SENSITIVE_NOTIFICATION_APP_PROTECTION = "android.permission.flags.sensitive_notification_app_protection";
    public static final String FLAG_SERVER_SIDE_ATTRIBUTION_REGISTRATION = "android.permission.flags.server_side_attribution_registration";
    public static final String FLAG_SET_NEXT_ATTRIBUTION_SOURCE = "android.permission.flags.set_next_attribution_source";
    public static final String FLAG_SHOULD_REGISTER_ATTRIBUTION_SOURCE = "android.permission.flags.should_register_attribution_source";
    public static final String FLAG_SIGNATURE_PERMISSION_ALLOWLIST_ENABLED = "android.permission.flags.signature_permission_allowlist_enabled";
    public static final String FLAG_SQLITE_DISCRETE_OP_EVENT_LOGGING_ENABLED = "android.permission.flags.sqlite_discrete_op_event_logging_enabled";
    public static final String FLAG_SUPERVISION_ROLE_PERMISSION_UPDATE_ENABLED = "android.permission.flags.supervision_role_permission_update_enabled";
    public static final String FLAG_SYNC_ON_OP_NOTED_API = "android.permission.flags.sync_on_op_noted_api";
    public static final String FLAG_SYSTEM_SELECTION_TOOLBAR_ENABLED = "android.permission.flags.system_selection_toolbar_enabled";
    public static final String FLAG_SYSTEM_SERVER_ROLE_CONTROLLER_ENABLED = "android.permission.flags.system_server_role_controller_enabled";
    public static final String FLAG_SYSTEM_VENDOR_INTELLIGENCE_ROLE_ENABLED = "android.permission.flags.system_vendor_intelligence_role_enabled";
    public static final String FLAG_TEXT_CLASSIFIER_CHOICE_API_ENABLED = "android.permission.flags.text_classifier_choice_api_enabled";
    public static final String FLAG_UNKNOWN_CALL_PACKAGE_INSTALL_BLOCKING_ENABLED = "android.permission.flags.unknown_call_package_install_blocking_enabled";
    public static final String FLAG_UNKNOWN_CALL_SETTING_BLOCKED_LOGGING_ENABLED = "android.permission.flags.unknown_call_setting_blocked_logging_enabled";
    public static final String FLAG_UPDATABLE_TEXT_CLASSIFIER_FOR_OTP_DETECTION_ENABLED = "android.permission.flags.updatable_text_classifier_for_otp_detection_enabled";
    public static final String FLAG_USE_FROZEN_AWARE_REMOTE_CALLBACK_LIST = "android.permission.flags.use_frozen_aware_remote_callback_list";
    public static final String FLAG_USE_PROFILE_LABELS_FOR_DEFAULT_APP_SECTION_TITLES = "android.permission.flags.use_profile_labels_for_default_app_section_titles";
    public static final String FLAG_USE_SYSTEM_SELECTION_TOOLBAR_IN_SYSUI = "android.permission.flags.use_system_selection_toolbar_in_sysui";
    public static final String FLAG_VOICE_ACTIVATION_PERMISSION_APIS = "android.permission.flags.voice_activation_permission_apis";
    public static final String FLAG_WALLET_ROLE_CROSS_USER_ENABLED = "android.permission.flags.wallet_role_cross_user_enabled";
    public static final String FLAG_WALLET_ROLE_ENABLED = "android.permission.flags.wallet_role_enabled";
    public static final String FLAG_WALLET_ROLE_ICON_PROPERTY_ENABLED = "android.permission.flags.wallet_role_icon_property_enabled";

    public static boolean allowHostPermissionDialogsOnVirtualDevices() {
        return FEATURE_FLAGS.allowHostPermissionDialogsOnVirtualDevices();
    }

    public static boolean appOpsServiceHandlerFix() {
        return FEATURE_FLAGS.appOpsServiceHandlerFix();
    }

    public static boolean appopAccessTrackingLoggingEnabled() {
        return FEATURE_FLAGS.appopAccessTrackingLoggingEnabled();
    }

    public static boolean appopModeCachingEnabled() {
        return FEATURE_FLAGS.appopModeCachingEnabled();
    }

    public static boolean checkOpOverloadApiEnabled() {
        return FEATURE_FLAGS.checkOpOverloadApiEnabled();
    }

    public static boolean checkOpValidatePackage() {
        return FEATURE_FLAGS.checkOpValidatePackage();
    }

    public static boolean delayUidStateChangesFromCapabilityUpdates() {
        return FEATURE_FLAGS.delayUidStateChangesFromCapabilityUpdates();
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

    public static boolean devicePolicyManagementRoleSplitCreateManagedProfileEnabled() {
        return FEATURE_FLAGS.devicePolicyManagementRoleSplitCreateManagedProfileEnabled();
    }

    public static boolean dontRemoveExistingUidStates() {
        return FEATURE_FLAGS.dontRemoveExistingUidStates();
    }

    public static boolean enableAiaiProxiedTextClassifiers() {
        return FEATURE_FLAGS.enableAiaiProxiedTextClassifiers();
    }

    public static boolean enableAllSqliteAppopsAccesses() {
        return FEATURE_FLAGS.enableAllSqliteAppopsAccesses();
    }

    public static boolean enableOtpInTextClassifiers() {
        return FEATURE_FLAGS.enableOtpInTextClassifiers();
    }

    public static boolean enableSqliteAppopsAccesses() {
        return FEATURE_FLAGS.enableSqliteAppopsAccesses();
    }

    public static boolean enableSystemSupervisionRoleBehavior() {
        return FEATURE_FLAGS.enableSystemSupervisionRoleBehavior();
    }

    public static boolean enforceDefaultDeviceIdInMyAttributionSource() {
        return FEATURE_FLAGS.enforceDefaultDeviceIdInMyAttributionSource();
    }

    public static boolean enhancedConfirmationInCallApisEnabled() {
        return FEATURE_FLAGS.enhancedConfirmationInCallApisEnabled();
    }

    public static boolean enhancedConfirmationModeApisEnabled() {
        return FEATURE_FLAGS.enhancedConfirmationModeApisEnabled();
    }

    public static boolean factoryResetPrepPermissionApis() {
        return FEATURE_FLAGS.factoryResetPrepPermissionApis();
    }

    public static boolean finePowerMonitorPermission() {
        return FEATURE_FLAGS.finePowerMonitorPermission();
    }

    public static boolean finishRunningOpsForKilledPackages() {
        return FEATURE_FLAGS.finishRunningOpsForKilledPackages();
    }

    public static boolean getEmergencyRoleHolderApiEnabled() {
        return FEATURE_FLAGS.getEmergencyRoleHolderApiEnabled();
    }

    public static boolean grantReadBlockedNumbersToSystemUiIntelligence() {
        return FEATURE_FLAGS.grantReadBlockedNumbersToSystemUiIntelligence();
    }

    public static boolean healthConnectBackupRestorePermissionEnabled() {
        return FEATURE_FLAGS.healthConnectBackupRestorePermissionEnabled();
    }

    public static boolean ignoreProcessText() {
        return FEATURE_FLAGS.ignoreProcessText();
    }

    public static boolean locationBypassPrivacyDashboardEnabled() {
        return FEATURE_FLAGS.locationBypassPrivacyDashboardEnabled();
    }

    public static boolean noteOpBatchingEnabled() {
        return FEATURE_FLAGS.noteOpBatchingEnabled();
    }

    public static boolean opEnableMobileDataByUser() {
        return FEATURE_FLAGS.opEnableMobileDataByUser();
    }

    public static boolean permissionRequestShortCircuitEnabled() {
        return FEATURE_FLAGS.permissionRequestShortCircuitEnabled();
    }

    public static boolean permissionTreeApisDeprecated() {
        return FEATURE_FLAGS.permissionTreeApisDeprecated();
    }

    public static boolean rangingPermissionEnabled() {
        return FEATURE_FLAGS.rangingPermissionEnabled();
    }

    public static boolean rateLimitBatchedNoteOpAsyncCallbacksEnabled() {
        return FEATURE_FLAGS.rateLimitBatchedNoteOpAsyncCallbacksEnabled();
    }

    public static boolean recordAllRuntimeAppopsSqlite() {
        return FEATURE_FLAGS.recordAllRuntimeAppopsSqlite();
    }

    public static boolean replaceBodySensorPermissionEnabled() {
        return FEATURE_FLAGS.replaceBodySensorPermissionEnabled();
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

    public static boolean sqliteDiscreteOpEventLoggingEnabled() {
        return FEATURE_FLAGS.sqliteDiscreteOpEventLoggingEnabled();
    }

    public static boolean supervisionRolePermissionUpdateEnabled() {
        return FEATURE_FLAGS.supervisionRolePermissionUpdateEnabled();
    }

    public static boolean syncOnOpNotedApi() {
        return FEATURE_FLAGS.syncOnOpNotedApi();
    }

    public static boolean systemSelectionToolbarEnabled() {
        return FEATURE_FLAGS.systemSelectionToolbarEnabled();
    }

    public static boolean systemServerRoleControllerEnabled() {
        return FEATURE_FLAGS.systemServerRoleControllerEnabled();
    }

    public static boolean systemVendorIntelligenceRoleEnabled() {
        return FEATURE_FLAGS.systemVendorIntelligenceRoleEnabled();
    }

    public static boolean textClassifierChoiceApiEnabled() {
        return FEATURE_FLAGS.textClassifierChoiceApiEnabled();
    }

    public static boolean unknownCallPackageInstallBlockingEnabled() {
        return FEATURE_FLAGS.unknownCallPackageInstallBlockingEnabled();
    }

    public static boolean unknownCallSettingBlockedLoggingEnabled() {
        return FEATURE_FLAGS.unknownCallSettingBlockedLoggingEnabled();
    }

    public static boolean updatableTextClassifierForOtpDetectionEnabled() {
        return FEATURE_FLAGS.updatableTextClassifierForOtpDetectionEnabled();
    }

    public static boolean useFrozenAwareRemoteCallbackList() {
        return FEATURE_FLAGS.useFrozenAwareRemoteCallbackList();
    }

    public static boolean useProfileLabelsForDefaultAppSectionTitles() {
        return FEATURE_FLAGS.useProfileLabelsForDefaultAppSectionTitles();
    }

    public static boolean useSystemSelectionToolbarInSysui() {
        return FEATURE_FLAGS.useSystemSelectionToolbarInSysui();
    }

    public static boolean voiceActivationPermissionApis() {
        return FEATURE_FLAGS.voiceActivationPermissionApis();
    }

    public static boolean walletRoleCrossUserEnabled() {
        return FEATURE_FLAGS.walletRoleCrossUserEnabled();
    }

    public static boolean walletRoleEnabled() {
        return FEATURE_FLAGS.walletRoleEnabled();
    }

    public static boolean walletRoleIconPropertyEnabled() {
        return FEATURE_FLAGS.walletRoleIconPropertyEnabled();
    }
}
