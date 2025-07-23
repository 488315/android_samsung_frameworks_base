package com.android.internal.hidden_from_bootclasspath.android.permission.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ALLOW_HOST_PERMISSION_DIALOGS_ON_VIRTUAL_DEVICES, Flags.FLAG_APP_OPS_SERVICE_HANDLER_FIX, Flags.FLAG_APPOP_ACCESS_TRACKING_LOGGING_ENABLED, Flags.FLAG_APPOP_MODE_CACHING_ENABLED, Flags.FLAG_CHECK_OP_OVERLOAD_API_ENABLED, Flags.FLAG_CHECK_OP_VALIDATE_PACKAGE, Flags.FLAG_DELAY_UID_STATE_CHANGES_FROM_CAPABILITY_UPDATES, Flags.FLAG_DEVICE_AWARE_APP_OP_NEW_SCHEMA_ENABLED, Flags.FLAG_DEVICE_AWARE_PERMISSION_APIS_ENABLED, Flags.FLAG_DEVICE_AWARE_PERMISSIONS_ENABLED, Flags.FLAG_DEVICE_ID_IN_OP_PROXY_INFO_ENABLED, Flags.FLAG_DEVICE_POLICY_MANAGEMENT_ROLE_SPLIT_CREATE_MANAGED_PROFILE_ENABLED, Flags.FLAG_DONT_REMOVE_EXISTING_UID_STATES, Flags.FLAG_ENABLE_AIAI_PROXIED_TEXT_CLASSIFIERS, Flags.FLAG_ENABLE_ALL_SQLITE_APPOPS_ACCESSES, Flags.FLAG_ENABLE_OTP_IN_TEXT_CLASSIFIERS, Flags.FLAG_ENABLE_SQLITE_APPOPS_ACCESSES, Flags.FLAG_ENABLE_SYSTEM_SUPERVISION_ROLE_BEHAVIOR, Flags.FLAG_ENFORCE_DEFAULT_DEVICE_ID_IN_MY_ATTRIBUTION_SOURCE, Flags.FLAG_ENHANCED_CONFIRMATION_IN_CALL_APIS_ENABLED, Flags.FLAG_ENHANCED_CONFIRMATION_MODE_APIS_ENABLED, Flags.FLAG_FACTORY_RESET_PREP_PERMISSION_APIS, Flags.FLAG_FINE_POWER_MONITOR_PERMISSION, Flags.FLAG_FINISH_RUNNING_OPS_FOR_KILLED_PACKAGES, Flags.FLAG_GET_EMERGENCY_ROLE_HOLDER_API_ENABLED, Flags.FLAG_GRANT_READ_BLOCKED_NUMBERS_TO_SYSTEM_UI_INTELLIGENCE, Flags.FLAG_HEALTH_CONNECT_BACKUP_RESTORE_PERMISSION_ENABLED, Flags.FLAG_IGNORE_PROCESS_TEXT, Flags.FLAG_LOCATION_BYPASS_PRIVACY_DASHBOARD_ENABLED, Flags.FLAG_NOTE_OP_BATCHING_ENABLED, Flags.FLAG_OP_ENABLE_MOBILE_DATA_BY_USER, Flags.FLAG_PERMISSION_REQUEST_SHORT_CIRCUIT_ENABLED, Flags.FLAG_PERMISSION_TREE_APIS_DEPRECATED, Flags.FLAG_RANGING_PERMISSION_ENABLED, Flags.FLAG_RATE_LIMIT_BATCHED_NOTE_OP_ASYNC_CALLBACKS_ENABLED, Flags.FLAG_RECORD_ALL_RUNTIME_APPOPS_SQLITE, Flags.FLAG_REPLACE_BODY_SENSOR_PERMISSION_ENABLED, Flags.FLAG_RETAIL_DEMO_ROLE_ENABLED, Flags.FLAG_RUNTIME_PERMISSION_APPOPS_MAPPING_ENABLED, Flags.FLAG_SENSITIVE_CONTENT_IMPROVEMENTS, Flags.FLAG_SENSITIVE_CONTENT_METRICS_BUGFIX, Flags.FLAG_SENSITIVE_CONTENT_RECENTS_SCREENSHOT_BUGFIX, Flags.FLAG_SENSITIVE_NOTIFICATION_APP_PROTECTION, Flags.FLAG_SERVER_SIDE_ATTRIBUTION_REGISTRATION, Flags.FLAG_SET_NEXT_ATTRIBUTION_SOURCE, Flags.FLAG_SHOULD_REGISTER_ATTRIBUTION_SOURCE, Flags.FLAG_SIGNATURE_PERMISSION_ALLOWLIST_ENABLED, Flags.FLAG_SQLITE_DISCRETE_OP_EVENT_LOGGING_ENABLED, Flags.FLAG_SUPERVISION_ROLE_PERMISSION_UPDATE_ENABLED, Flags.FLAG_SYNC_ON_OP_NOTED_API, Flags.FLAG_SYSTEM_SELECTION_TOOLBAR_ENABLED, Flags.FLAG_SYSTEM_SERVER_ROLE_CONTROLLER_ENABLED, Flags.FLAG_SYSTEM_VENDOR_INTELLIGENCE_ROLE_ENABLED, Flags.FLAG_TEXT_CLASSIFIER_CHOICE_API_ENABLED, Flags.FLAG_UNKNOWN_CALL_PACKAGE_INSTALL_BLOCKING_ENABLED, Flags.FLAG_UNKNOWN_CALL_SETTING_BLOCKED_LOGGING_ENABLED, Flags.FLAG_UPDATABLE_TEXT_CLASSIFIER_FOR_OTP_DETECTION_ENABLED, Flags.FLAG_USE_FROZEN_AWARE_REMOTE_CALLBACK_LIST, Flags.FLAG_USE_PROFILE_LABELS_FOR_DEFAULT_APP_SECTION_TITLES, Flags.FLAG_USE_SYSTEM_SELECTION_TOOLBAR_IN_SYSUI, Flags.FLAG_VOICE_ACTIVATION_PERMISSION_APIS, Flags.FLAG_WALLET_ROLE_CROSS_USER_ENABLED, Flags.FLAG_WALLET_ROLE_ENABLED, Flags.FLAG_WALLET_ROLE_ICON_PROPERTY_ENABLED, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean allowHostPermissionDialogsOnVirtualDevices() {
        return getValue(Flags.FLAG_ALLOW_HOST_PERMISSION_DIALOGS_ON_VIRTUAL_DEVICES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowHostPermissionDialogsOnVirtualDevices();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean appOpsServiceHandlerFix() {
        return getValue(Flags.FLAG_APP_OPS_SERVICE_HANDLER_FIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appOpsServiceHandlerFix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean appopAccessTrackingLoggingEnabled() {
        return getValue(Flags.FLAG_APPOP_ACCESS_TRACKING_LOGGING_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appopAccessTrackingLoggingEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean appopModeCachingEnabled() {
        return getValue(Flags.FLAG_APPOP_MODE_CACHING_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda42
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appopModeCachingEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean checkOpOverloadApiEnabled() {
        return getValue(Flags.FLAG_CHECK_OP_OVERLOAD_API_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda34
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).checkOpOverloadApiEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean checkOpValidatePackage() {
        return getValue(Flags.FLAG_CHECK_OP_VALIDATE_PACKAGE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda57
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).checkOpValidatePackage();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean delayUidStateChangesFromCapabilityUpdates() {
        return getValue(Flags.FLAG_DELAY_UID_STATE_CHANGES_FROM_CAPABILITY_UPDATES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda56
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).delayUidStateChangesFromCapabilityUpdates();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean deviceAwareAppOpNewSchemaEnabled() {
        return getValue(Flags.FLAG_DEVICE_AWARE_APP_OP_NEW_SCHEMA_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceAwareAppOpNewSchemaEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean deviceAwarePermissionApisEnabled() {
        return getValue(Flags.FLAG_DEVICE_AWARE_PERMISSION_APIS_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda54
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceAwarePermissionApisEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean deviceAwarePermissionsEnabled() {
        return getValue(Flags.FLAG_DEVICE_AWARE_PERMISSIONS_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda53
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceAwarePermissionsEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean deviceIdInOpProxyInfoEnabled() {
        return getValue(Flags.FLAG_DEVICE_ID_IN_OP_PROXY_INFO_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceIdInOpProxyInfoEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean devicePolicyManagementRoleSplitCreateManagedProfileEnabled() {
        return getValue(Flags.FLAG_DEVICE_POLICY_MANAGEMENT_ROLE_SPLIT_CREATE_MANAGED_PROFILE_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda63
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).devicePolicyManagementRoleSplitCreateManagedProfileEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean dontRemoveExistingUidStates() {
        return getValue(Flags.FLAG_DONT_REMOVE_EXISTING_UID_STATES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dontRemoveExistingUidStates();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean enableAiaiProxiedTextClassifiers() {
        return getValue(Flags.FLAG_ENABLE_AIAI_PROXIED_TEXT_CLASSIFIERS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableAiaiProxiedTextClassifiers();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean enableAllSqliteAppopsAccesses() {
        return getValue(Flags.FLAG_ENABLE_ALL_SQLITE_APPOPS_ACCESSES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda48
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableAllSqliteAppopsAccesses();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean enableOtpInTextClassifiers() {
        return getValue(Flags.FLAG_ENABLE_OTP_IN_TEXT_CLASSIFIERS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableOtpInTextClassifiers();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean enableSqliteAppopsAccesses() {
        return getValue(Flags.FLAG_ENABLE_SQLITE_APPOPS_ACCESSES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda46
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSqliteAppopsAccesses();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean enableSystemSupervisionRoleBehavior() {
        return getValue(Flags.FLAG_ENABLE_SYSTEM_SUPERVISION_ROLE_BEHAVIOR, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda41
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSystemSupervisionRoleBehavior();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean enforceDefaultDeviceIdInMyAttributionSource() {
        return getValue(Flags.FLAG_ENFORCE_DEFAULT_DEVICE_ID_IN_MY_ATTRIBUTION_SOURCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enforceDefaultDeviceIdInMyAttributionSource();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean enhancedConfirmationInCallApisEnabled() {
        return getValue(Flags.FLAG_ENHANCED_CONFIRMATION_IN_CALL_APIS_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enhancedConfirmationInCallApisEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean enhancedConfirmationModeApisEnabled() {
        return getValue(Flags.FLAG_ENHANCED_CONFIRMATION_MODE_APIS_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda37
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enhancedConfirmationModeApisEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean factoryResetPrepPermissionApis() {
        return getValue(Flags.FLAG_FACTORY_RESET_PREP_PERMISSION_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda47
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).factoryResetPrepPermissionApis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean finePowerMonitorPermission() {
        return getValue(Flags.FLAG_FINE_POWER_MONITOR_PERMISSION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).finePowerMonitorPermission();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean finishRunningOpsForKilledPackages() {
        return getValue(Flags.FLAG_FINISH_RUNNING_OPS_FOR_KILLED_PACKAGES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).finishRunningOpsForKilledPackages();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean getEmergencyRoleHolderApiEnabled() {
        return getValue(Flags.FLAG_GET_EMERGENCY_ROLE_HOLDER_API_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).getEmergencyRoleHolderApiEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean grantReadBlockedNumbersToSystemUiIntelligence() {
        return getValue(Flags.FLAG_GRANT_READ_BLOCKED_NUMBERS_TO_SYSTEM_UI_INTELLIGENCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).grantReadBlockedNumbersToSystemUiIntelligence();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean healthConnectBackupRestorePermissionEnabled() {
        return getValue(Flags.FLAG_HEALTH_CONNECT_BACKUP_RESTORE_PERMISSION_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).healthConnectBackupRestorePermissionEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean ignoreProcessText() {
        return getValue(Flags.FLAG_IGNORE_PROCESS_TEXT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda44
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ignoreProcessText();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean locationBypassPrivacyDashboardEnabled() {
        return getValue(Flags.FLAG_LOCATION_BYPASS_PRIVACY_DASHBOARD_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).locationBypassPrivacyDashboardEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean noteOpBatchingEnabled() {
        return getValue(Flags.FLAG_NOTE_OP_BATCHING_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda58
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).noteOpBatchingEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean opEnableMobileDataByUser() {
        return getValue(Flags.FLAG_OP_ENABLE_MOBILE_DATA_BY_USER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda60
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).opEnableMobileDataByUser();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean permissionRequestShortCircuitEnabled() {
        return getValue(Flags.FLAG_PERMISSION_REQUEST_SHORT_CIRCUIT_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).permissionRequestShortCircuitEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean permissionTreeApisDeprecated() {
        return getValue(Flags.FLAG_PERMISSION_TREE_APIS_DEPRECATED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).permissionTreeApisDeprecated();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean rangingPermissionEnabled() {
        return getValue(Flags.FLAG_RANGING_PERMISSION_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rangingPermissionEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean rateLimitBatchedNoteOpAsyncCallbacksEnabled() {
        return getValue(Flags.FLAG_RATE_LIMIT_BATCHED_NOTE_OP_ASYNC_CALLBACKS_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rateLimitBatchedNoteOpAsyncCallbacksEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean recordAllRuntimeAppopsSqlite() {
        return getValue(Flags.FLAG_RECORD_ALL_RUNTIME_APPOPS_SQLITE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).recordAllRuntimeAppopsSqlite();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean replaceBodySensorPermissionEnabled() {
        return getValue(Flags.FLAG_REPLACE_BODY_SENSOR_PERMISSION_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).replaceBodySensorPermissionEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean retailDemoRoleEnabled() {
        return getValue(Flags.FLAG_RETAIL_DEMO_ROLE_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).retailDemoRoleEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean runtimePermissionAppopsMappingEnabled() {
        return getValue(Flags.FLAG_RUNTIME_PERMISSION_APPOPS_MAPPING_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda45
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).runtimePermissionAppopsMappingEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean sensitiveContentImprovements() {
        return getValue(Flags.FLAG_SENSITIVE_CONTENT_IMPROVEMENTS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda52
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sensitiveContentImprovements();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean sensitiveContentMetricsBugfix() {
        return getValue(Flags.FLAG_SENSITIVE_CONTENT_METRICS_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda39
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sensitiveContentMetricsBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean sensitiveContentRecentsScreenshotBugfix() {
        return getValue(Flags.FLAG_SENSITIVE_CONTENT_RECENTS_SCREENSHOT_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda36
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sensitiveContentRecentsScreenshotBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean sensitiveNotificationAppProtection() {
        return getValue(Flags.FLAG_SENSITIVE_NOTIFICATION_APP_PROTECTION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda61
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sensitiveNotificationAppProtection();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean serverSideAttributionRegistration() {
        return getValue(Flags.FLAG_SERVER_SIDE_ATTRIBUTION_REGISTRATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda38
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).serverSideAttributionRegistration();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean setNextAttributionSource() {
        return getValue(Flags.FLAG_SET_NEXT_ATTRIBUTION_SOURCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda43
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setNextAttributionSource();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean shouldRegisterAttributionSource() {
        return getValue(Flags.FLAG_SHOULD_REGISTER_ATTRIBUTION_SOURCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).shouldRegisterAttributionSource();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean signaturePermissionAllowlistEnabled() {
        return getValue(Flags.FLAG_SIGNATURE_PERMISSION_ALLOWLIST_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).signaturePermissionAllowlistEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean sqliteDiscreteOpEventLoggingEnabled() {
        return getValue(Flags.FLAG_SQLITE_DISCRETE_OP_EVENT_LOGGING_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sqliteDiscreteOpEventLoggingEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean supervisionRolePermissionUpdateEnabled() {
        return getValue(Flags.FLAG_SUPERVISION_ROLE_PERMISSION_UPDATE_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda49
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supervisionRolePermissionUpdateEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean syncOnOpNotedApi() {
        return getValue(Flags.FLAG_SYNC_ON_OP_NOTED_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).syncOnOpNotedApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean systemSelectionToolbarEnabled() {
        return getValue(Flags.FLAG_SYSTEM_SELECTION_TOOLBAR_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).systemSelectionToolbarEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean systemServerRoleControllerEnabled() {
        return getValue(Flags.FLAG_SYSTEM_SERVER_ROLE_CONTROLLER_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda59
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).systemServerRoleControllerEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean systemVendorIntelligenceRoleEnabled() {
        return getValue(Flags.FLAG_SYSTEM_VENDOR_INTELLIGENCE_ROLE_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda50
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).systemVendorIntelligenceRoleEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean textClassifierChoiceApiEnabled() {
        return getValue(Flags.FLAG_TEXT_CLASSIFIER_CHOICE_API_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).textClassifierChoiceApiEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean unknownCallPackageInstallBlockingEnabled() {
        return getValue(Flags.FLAG_UNKNOWN_CALL_PACKAGE_INSTALL_BLOCKING_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda40
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).unknownCallPackageInstallBlockingEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean unknownCallSettingBlockedLoggingEnabled() {
        return getValue(Flags.FLAG_UNKNOWN_CALL_SETTING_BLOCKED_LOGGING_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda55
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).unknownCallSettingBlockedLoggingEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean updatableTextClassifierForOtpDetectionEnabled() {
        return getValue(Flags.FLAG_UPDATABLE_TEXT_CLASSIFIER_FOR_OTP_DETECTION_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).updatableTextClassifierForOtpDetectionEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean useFrozenAwareRemoteCallbackList() {
        return getValue(Flags.FLAG_USE_FROZEN_AWARE_REMOTE_CALLBACK_LIST, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda62
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useFrozenAwareRemoteCallbackList();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean useProfileLabelsForDefaultAppSectionTitles() {
        return getValue(Flags.FLAG_USE_PROFILE_LABELS_FOR_DEFAULT_APP_SECTION_TITLES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useProfileLabelsForDefaultAppSectionTitles();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean useSystemSelectionToolbarInSysui() {
        return getValue(Flags.FLAG_USE_SYSTEM_SELECTION_TOOLBAR_IN_SYSUI, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useSystemSelectionToolbarInSysui();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean voiceActivationPermissionApis() {
        return getValue(Flags.FLAG_VOICE_ACTIVATION_PERMISSION_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).voiceActivationPermissionApis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean walletRoleCrossUserEnabled() {
        return getValue(Flags.FLAG_WALLET_ROLE_CROSS_USER_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).walletRoleCrossUserEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean walletRoleEnabled() {
        return getValue(Flags.FLAG_WALLET_ROLE_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda35
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).walletRoleEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean walletRoleIconPropertyEnabled() {
        return getValue(Flags.FLAG_WALLET_ROLE_ICON_PROPERTY_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda51
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).walletRoleIconPropertyEnabled();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ALLOW_HOST_PERMISSION_DIALOGS_ON_VIRTUAL_DEVICES, Flags.FLAG_APP_OPS_SERVICE_HANDLER_FIX, Flags.FLAG_APPOP_ACCESS_TRACKING_LOGGING_ENABLED, Flags.FLAG_APPOP_MODE_CACHING_ENABLED, Flags.FLAG_CHECK_OP_OVERLOAD_API_ENABLED, Flags.FLAG_CHECK_OP_VALIDATE_PACKAGE, Flags.FLAG_DELAY_UID_STATE_CHANGES_FROM_CAPABILITY_UPDATES, Flags.FLAG_DEVICE_AWARE_APP_OP_NEW_SCHEMA_ENABLED, Flags.FLAG_DEVICE_AWARE_PERMISSION_APIS_ENABLED, Flags.FLAG_DEVICE_AWARE_PERMISSIONS_ENABLED, Flags.FLAG_DEVICE_ID_IN_OP_PROXY_INFO_ENABLED, Flags.FLAG_DEVICE_POLICY_MANAGEMENT_ROLE_SPLIT_CREATE_MANAGED_PROFILE_ENABLED, Flags.FLAG_DONT_REMOVE_EXISTING_UID_STATES, Flags.FLAG_ENABLE_AIAI_PROXIED_TEXT_CLASSIFIERS, Flags.FLAG_ENABLE_ALL_SQLITE_APPOPS_ACCESSES, Flags.FLAG_ENABLE_OTP_IN_TEXT_CLASSIFIERS, Flags.FLAG_ENABLE_SQLITE_APPOPS_ACCESSES, Flags.FLAG_ENABLE_SYSTEM_SUPERVISION_ROLE_BEHAVIOR, Flags.FLAG_ENFORCE_DEFAULT_DEVICE_ID_IN_MY_ATTRIBUTION_SOURCE, Flags.FLAG_ENHANCED_CONFIRMATION_IN_CALL_APIS_ENABLED, Flags.FLAG_ENHANCED_CONFIRMATION_MODE_APIS_ENABLED, Flags.FLAG_FACTORY_RESET_PREP_PERMISSION_APIS, Flags.FLAG_FINE_POWER_MONITOR_PERMISSION, Flags.FLAG_FINISH_RUNNING_OPS_FOR_KILLED_PACKAGES, Flags.FLAG_GET_EMERGENCY_ROLE_HOLDER_API_ENABLED, Flags.FLAG_GRANT_READ_BLOCKED_NUMBERS_TO_SYSTEM_UI_INTELLIGENCE, Flags.FLAG_HEALTH_CONNECT_BACKUP_RESTORE_PERMISSION_ENABLED, Flags.FLAG_IGNORE_PROCESS_TEXT, Flags.FLAG_LOCATION_BYPASS_PRIVACY_DASHBOARD_ENABLED, Flags.FLAG_NOTE_OP_BATCHING_ENABLED, Flags.FLAG_OP_ENABLE_MOBILE_DATA_BY_USER, Flags.FLAG_PERMISSION_REQUEST_SHORT_CIRCUIT_ENABLED, Flags.FLAG_PERMISSION_TREE_APIS_DEPRECATED, Flags.FLAG_RANGING_PERMISSION_ENABLED, Flags.FLAG_RATE_LIMIT_BATCHED_NOTE_OP_ASYNC_CALLBACKS_ENABLED, Flags.FLAG_RECORD_ALL_RUNTIME_APPOPS_SQLITE, Flags.FLAG_REPLACE_BODY_SENSOR_PERMISSION_ENABLED, Flags.FLAG_RETAIL_DEMO_ROLE_ENABLED, Flags.FLAG_RUNTIME_PERMISSION_APPOPS_MAPPING_ENABLED, Flags.FLAG_SENSITIVE_CONTENT_IMPROVEMENTS, Flags.FLAG_SENSITIVE_CONTENT_METRICS_BUGFIX, Flags.FLAG_SENSITIVE_CONTENT_RECENTS_SCREENSHOT_BUGFIX, Flags.FLAG_SENSITIVE_NOTIFICATION_APP_PROTECTION, Flags.FLAG_SERVER_SIDE_ATTRIBUTION_REGISTRATION, Flags.FLAG_SET_NEXT_ATTRIBUTION_SOURCE, Flags.FLAG_SHOULD_REGISTER_ATTRIBUTION_SOURCE, Flags.FLAG_SIGNATURE_PERMISSION_ALLOWLIST_ENABLED, Flags.FLAG_SQLITE_DISCRETE_OP_EVENT_LOGGING_ENABLED, Flags.FLAG_SUPERVISION_ROLE_PERMISSION_UPDATE_ENABLED, Flags.FLAG_SYNC_ON_OP_NOTED_API, Flags.FLAG_SYSTEM_SELECTION_TOOLBAR_ENABLED, Flags.FLAG_SYSTEM_SERVER_ROLE_CONTROLLER_ENABLED, Flags.FLAG_SYSTEM_VENDOR_INTELLIGENCE_ROLE_ENABLED, Flags.FLAG_TEXT_CLASSIFIER_CHOICE_API_ENABLED, Flags.FLAG_UNKNOWN_CALL_PACKAGE_INSTALL_BLOCKING_ENABLED, Flags.FLAG_UNKNOWN_CALL_SETTING_BLOCKED_LOGGING_ENABLED, Flags.FLAG_UPDATABLE_TEXT_CLASSIFIER_FOR_OTP_DETECTION_ENABLED, Flags.FLAG_USE_FROZEN_AWARE_REMOTE_CALLBACK_LIST, Flags.FLAG_USE_PROFILE_LABELS_FOR_DEFAULT_APP_SECTION_TITLES, Flags.FLAG_USE_SYSTEM_SELECTION_TOOLBAR_IN_SYSUI, Flags.FLAG_VOICE_ACTIVATION_PERMISSION_APIS, Flags.FLAG_WALLET_ROLE_CROSS_USER_ENABLED, Flags.FLAG_WALLET_ROLE_ENABLED, Flags.FLAG_WALLET_ROLE_ICON_PROPERTY_ENABLED);
    }
}
