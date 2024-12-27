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
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_APEX_SIGNATURE_PERMISSION_ALLOWLIST_ENABLED, Flags.FLAG_DEVICE_AWARE_APP_OP_NEW_SCHEMA_ENABLED, Flags.FLAG_DEVICE_AWARE_PERMISSION_APIS_ENABLED, Flags.FLAG_DEVICE_AWARE_PERMISSIONS_ENABLED, Flags.FLAG_DEVICE_ID_IN_OP_PROXY_INFO_ENABLED, Flags.FLAG_ENHANCED_CONFIRMATION_MODE_APIS_ENABLED, Flags.FLAG_FACTORY_RESET_PREP_PERMISSION_APIS, Flags.FLAG_FINISH_RUNNING_OPS_FOR_KILLED_PACKAGES, Flags.FLAG_GET_EMERGENCY_ROLE_HOLDER_API_ENABLED, Flags.FLAG_IGNORE_PROCESS_TEXT, Flags.FLAG_OP_ENABLE_MOBILE_DATA_BY_USER, Flags.FLAG_RETAIL_DEMO_ROLE_ENABLED, Flags.FLAG_RUNTIME_PERMISSION_APPOPS_MAPPING_ENABLED, Flags.FLAG_SENSITIVE_CONTENT_IMPROVEMENTS, Flags.FLAG_SENSITIVE_CONTENT_METRICS_BUGFIX, Flags.FLAG_SENSITIVE_CONTENT_RECENTS_SCREENSHOT_BUGFIX, Flags.FLAG_SENSITIVE_NOTIFICATION_APP_PROTECTION, Flags.FLAG_SERVER_SIDE_ATTRIBUTION_REGISTRATION, Flags.FLAG_SET_NEXT_ATTRIBUTION_SOURCE, Flags.FLAG_SHOULD_REGISTER_ATTRIBUTION_SOURCE, Flags.FLAG_SIGNATURE_PERMISSION_ALLOWLIST_ENABLED, Flags.FLAG_SYSTEM_SERVER_ROLE_CONTROLLER_ENABLED, Flags.FLAG_VOICE_ACTIVATION_PERMISSION_APIS, Flags.FLAG_WALLET_ROLE_ENABLED, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean apexSignaturePermissionAllowlistEnabled() {
        return getValue(Flags.FLAG_APEX_SIGNATURE_PERMISSION_ALLOWLIST_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).apexSignaturePermissionAllowlistEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean deviceAwareAppOpNewSchemaEnabled() {
        return getValue(Flags.FLAG_DEVICE_AWARE_APP_OP_NEW_SCHEMA_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceAwareAppOpNewSchemaEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean deviceAwarePermissionApisEnabled() {
        return getValue(Flags.FLAG_DEVICE_AWARE_PERMISSION_APIS_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceAwarePermissionApisEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean deviceAwarePermissionsEnabled() {
        return getValue(Flags.FLAG_DEVICE_AWARE_PERMISSIONS_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceAwarePermissionsEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean deviceIdInOpProxyInfoEnabled() {
        return getValue(Flags.FLAG_DEVICE_ID_IN_OP_PROXY_INFO_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceIdInOpProxyInfoEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean enhancedConfirmationModeApisEnabled() {
        return getValue(Flags.FLAG_ENHANCED_CONFIRMATION_MODE_APIS_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enhancedConfirmationModeApisEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean factoryResetPrepPermissionApis() {
        return getValue(Flags.FLAG_FACTORY_RESET_PREP_PERMISSION_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).factoryResetPrepPermissionApis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean finishRunningOpsForKilledPackages() {
        return getValue(Flags.FLAG_FINISH_RUNNING_OPS_FOR_KILLED_PACKAGES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).finishRunningOpsForKilledPackages();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean getEmergencyRoleHolderApiEnabled() {
        return getValue(Flags.FLAG_GET_EMERGENCY_ROLE_HOLDER_API_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).getEmergencyRoleHolderApiEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean ignoreProcessText() {
        return getValue(Flags.FLAG_IGNORE_PROCESS_TEXT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ignoreProcessText();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean opEnableMobileDataByUser() {
        return getValue(Flags.FLAG_OP_ENABLE_MOBILE_DATA_BY_USER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).opEnableMobileDataByUser();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean retailDemoRoleEnabled() {
        return getValue(Flags.FLAG_RETAIL_DEMO_ROLE_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).retailDemoRoleEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean runtimePermissionAppopsMappingEnabled() {
        return getValue(Flags.FLAG_RUNTIME_PERMISSION_APPOPS_MAPPING_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).runtimePermissionAppopsMappingEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean sensitiveContentImprovements() {
        return getValue(Flags.FLAG_SENSITIVE_CONTENT_IMPROVEMENTS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sensitiveContentImprovements();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean sensitiveContentMetricsBugfix() {
        return getValue(Flags.FLAG_SENSITIVE_CONTENT_METRICS_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sensitiveContentMetricsBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean sensitiveContentRecentsScreenshotBugfix() {
        return getValue(Flags.FLAG_SENSITIVE_CONTENT_RECENTS_SCREENSHOT_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sensitiveContentRecentsScreenshotBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean sensitiveNotificationAppProtection() {
        return getValue(Flags.FLAG_SENSITIVE_NOTIFICATION_APP_PROTECTION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sensitiveNotificationAppProtection();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean serverSideAttributionRegistration() {
        return getValue(Flags.FLAG_SERVER_SIDE_ATTRIBUTION_REGISTRATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).serverSideAttributionRegistration();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean setNextAttributionSource() {
        return getValue(Flags.FLAG_SET_NEXT_ATTRIBUTION_SOURCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setNextAttributionSource();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean shouldRegisterAttributionSource() {
        return getValue(Flags.FLAG_SHOULD_REGISTER_ATTRIBUTION_SOURCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).shouldRegisterAttributionSource();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean signaturePermissionAllowlistEnabled() {
        return getValue(Flags.FLAG_SIGNATURE_PERMISSION_ALLOWLIST_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).signaturePermissionAllowlistEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean systemServerRoleControllerEnabled() {
        return getValue(Flags.FLAG_SYSTEM_SERVER_ROLE_CONTROLLER_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).systemServerRoleControllerEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean voiceActivationPermissionApis() {
        return getValue(Flags.FLAG_VOICE_ACTIVATION_PERMISSION_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).voiceActivationPermissionApis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.permission.flags.FeatureFlags
    public boolean walletRoleEnabled() {
        return getValue(Flags.FLAG_WALLET_ROLE_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).walletRoleEnabled();
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
        return Arrays.asList(Flags.FLAG_APEX_SIGNATURE_PERMISSION_ALLOWLIST_ENABLED, Flags.FLAG_DEVICE_AWARE_APP_OP_NEW_SCHEMA_ENABLED, Flags.FLAG_DEVICE_AWARE_PERMISSION_APIS_ENABLED, Flags.FLAG_DEVICE_AWARE_PERMISSIONS_ENABLED, Flags.FLAG_DEVICE_ID_IN_OP_PROXY_INFO_ENABLED, Flags.FLAG_ENHANCED_CONFIRMATION_MODE_APIS_ENABLED, Flags.FLAG_FACTORY_RESET_PREP_PERMISSION_APIS, Flags.FLAG_FINISH_RUNNING_OPS_FOR_KILLED_PACKAGES, Flags.FLAG_GET_EMERGENCY_ROLE_HOLDER_API_ENABLED, Flags.FLAG_IGNORE_PROCESS_TEXT, Flags.FLAG_OP_ENABLE_MOBILE_DATA_BY_USER, Flags.FLAG_RETAIL_DEMO_ROLE_ENABLED, Flags.FLAG_RUNTIME_PERMISSION_APPOPS_MAPPING_ENABLED, Flags.FLAG_SENSITIVE_CONTENT_IMPROVEMENTS, Flags.FLAG_SENSITIVE_CONTENT_METRICS_BUGFIX, Flags.FLAG_SENSITIVE_CONTENT_RECENTS_SCREENSHOT_BUGFIX, Flags.FLAG_SENSITIVE_NOTIFICATION_APP_PROTECTION, Flags.FLAG_SERVER_SIDE_ATTRIBUTION_REGISTRATION, Flags.FLAG_SET_NEXT_ATTRIBUTION_SOURCE, Flags.FLAG_SHOULD_REGISTER_ATTRIBUTION_SOURCE, Flags.FLAG_SIGNATURE_PERMISSION_ALLOWLIST_ENABLED, Flags.FLAG_SYSTEM_SERVER_ROLE_CONTROLLER_ENABLED, Flags.FLAG_VOICE_ACTIVATION_PERMISSION_APIS, Flags.FLAG_WALLET_ROLE_ENABLED);
    }
}
