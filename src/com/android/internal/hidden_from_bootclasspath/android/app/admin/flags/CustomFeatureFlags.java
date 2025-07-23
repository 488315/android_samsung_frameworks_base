package com.android.internal.hidden_from_bootclasspath.android.app.admin.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ACTIVE_ADMIN_CLEANUP, Flags.FLAG_ALLOW_QUERYING_PROFILE_TYPE, Flags.FLAG_ASSIST_CONTENT_USER_RESTRICTION_ENABLED, Flags.FLAG_BACKUP_CONNECTED_APPS_SETTINGS, Flags.FLAG_BACKUP_SERVICE_SECURITY_LOG_EVENT_ENABLED, Flags.FLAG_COEXISTENCE_MIGRATION_FOR_SUPERVISION_ENABLED, Flags.FLAG_CROSS_USER_SUSPENSION_ENABLED_RO, Flags.FLAG_DEDICATED_DEVICE_CONTROL_API_ENABLED, Flags.FLAG_DEFAULT_SMS_PERSONAL_APP_SUSPENSION_FIX_ENABLED, Flags.FLAG_DEVICE_POLICY_SIZE_TRACKING_ENABLED, Flags.FLAG_DEVICE_THEFT_API_ENABLED, Flags.FLAG_DEVICE_THEFT_IMPL_ENABLED, Flags.FLAG_DISALLOW_USER_CONTROL_STOPPED_STATE_FIX, Flags.FLAG_ENABLE_SUPERVISION_SERVICE_SYNC, Flags.FLAG_ESIM_MANAGEMENT_ENABLED, Flags.FLAG_ESIM_MANAGEMENT_UX_ENABLED, Flags.FLAG_HEADLESS_DEVICE_OWNER_SINGLE_USER_ENABLED, Flags.FLAG_HEADLESS_SINGLE_MIN_TARGET_SDK, Flags.FLAG_INTERNAL_LOG_EVENT_LISTENER, Flags.FLAG_IS_MTE_POLICY_ENFORCED, Flags.FLAG_IS_RECURSIVE_REQUIRED_APP_MERGING_ENABLED, Flags.FLAG_LOCK_NOW_COEXISTENCE, Flags.FLAG_MANAGEMENT_MODE_POLICY_METRICS, Flags.FLAG_ONBOARDING_BUGREPORT_STORAGE_BUG_FIX, Flags.FLAG_ONBOARDING_BUGREPORT_V2_ENABLED, Flags.FLAG_ONBOARDING_CONSENTLESS_BUGREPORTS, Flags.FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_API_ENABLED, Flags.FLAG_POLICY_ENGINE_MIGRATION_V2_ENABLED, Flags.FLAG_PROVISIONING_CONTEXT_PARAMETER, Flags.FLAG_QUIET_MODE_CREDENTIAL_BUG_FIX, Flags.FLAG_REMOVE_MANAGED_ESIM_ON_WORK_PROFILE_DELETION, Flags.FLAG_REMOVE_MANAGED_PROFILE_ENABLED, Flags.FLAG_RESET_PASSWORD_WITH_TOKEN_COEXISTENCE, Flags.FLAG_SECONDARY_LOCKSCREEN_API_ENABLED, Flags.FLAG_SECURITY_LOG_V2_ENABLED, Flags.FLAG_SET_APPLICATION_RESTRICTIONS_COEXISTENCE, Flags.FLAG_SET_AUTO_TIME_ENABLED_COEXISTENCE, Flags.FLAG_SET_AUTO_TIME_ZONE_ENABLED_COEXISTENCE, Flags.FLAG_SET_BACKUP_SERVICE_ENABLED_COEXISTENCE, Flags.FLAG_SET_KEYGUARD_DISABLED_FEATURES_COEXISTENCE, Flags.FLAG_SET_MTE_POLICY_COEXISTENCE, Flags.FLAG_SET_PERMISSION_GRANT_STATE_COEXISTENCE, Flags.FLAG_SPLIT_CREATE_MANAGED_PROFILE_ENABLED, Flags.FLAG_SUSPEND_PACKAGES_COEXISTENCE, Flags.FLAG_UNMANAGED_MODE_MIGRATION, Flags.FLAG_UNSUSPEND_NOT_SUSPENDED, Flags.FLAG_USE_POLICY_INTERSECTION_FOR_PERMITTED_INPUT_METHODS, Flags.FLAG_USER_PROVISIONING_SAME_STATE, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean activeAdminCleanup() {
        return getValue(Flags.FLAG_ACTIVE_ADMIN_CLEANUP, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).activeAdminCleanup();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean allowQueryingProfileType() {
        return getValue(Flags.FLAG_ALLOW_QUERYING_PROFILE_TYPE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowQueryingProfileType();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean assistContentUserRestrictionEnabled() {
        return getValue(Flags.FLAG_ASSIST_CONTENT_USER_RESTRICTION_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).assistContentUserRestrictionEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean backupConnectedAppsSettings() {
        return getValue(Flags.FLAG_BACKUP_CONNECTED_APPS_SETTINGS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda41
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).backupConnectedAppsSettings();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean backupServiceSecurityLogEventEnabled() {
        return getValue(Flags.FLAG_BACKUP_SERVICE_SECURITY_LOG_EVENT_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).backupServiceSecurityLogEventEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean coexistenceMigrationForSupervisionEnabled() {
        return getValue(Flags.FLAG_COEXISTENCE_MIGRATION_FOR_SUPERVISION_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda44
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).coexistenceMigrationForSupervisionEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean crossUserSuspensionEnabledRo() {
        return getValue(Flags.FLAG_CROSS_USER_SUSPENSION_ENABLED_RO, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).crossUserSuspensionEnabledRo();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean dedicatedDeviceControlApiEnabled() {
        return getValue(Flags.FLAG_DEDICATED_DEVICE_CONTROL_API_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dedicatedDeviceControlApiEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean defaultSmsPersonalAppSuspensionFixEnabled() {
        return getValue(Flags.FLAG_DEFAULT_SMS_PERSONAL_APP_SUSPENSION_FIX_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).defaultSmsPersonalAppSuspensionFixEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean devicePolicySizeTrackingEnabled() {
        return getValue(Flags.FLAG_DEVICE_POLICY_SIZE_TRACKING_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).devicePolicySizeTrackingEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean deviceTheftApiEnabled() {
        return getValue(Flags.FLAG_DEVICE_THEFT_API_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceTheftApiEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean deviceTheftImplEnabled() {
        return getValue(Flags.FLAG_DEVICE_THEFT_IMPL_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceTheftImplEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean disallowUserControlStoppedStateFix() {
        return getValue(Flags.FLAG_DISALLOW_USER_CONTROL_STOPPED_STATE_FIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disallowUserControlStoppedStateFix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean enableSupervisionServiceSync() {
        return getValue(Flags.FLAG_ENABLE_SUPERVISION_SERVICE_SYNC, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSupervisionServiceSync();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean esimManagementEnabled() {
        return getValue(Flags.FLAG_ESIM_MANAGEMENT_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda43
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).esimManagementEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean esimManagementUxEnabled() {
        return getValue(Flags.FLAG_ESIM_MANAGEMENT_UX_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).esimManagementUxEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean headlessDeviceOwnerSingleUserEnabled() {
        return getValue(Flags.FLAG_HEADLESS_DEVICE_OWNER_SINGLE_USER_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).headlessDeviceOwnerSingleUserEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean headlessSingleMinTargetSdk() {
        return getValue(Flags.FLAG_HEADLESS_SINGLE_MIN_TARGET_SDK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).headlessSingleMinTargetSdk();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean internalLogEventListener() {
        return getValue(Flags.FLAG_INTERNAL_LOG_EVENT_LISTENER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda47
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).internalLogEventListener();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean isMtePolicyEnforced() {
        return getValue(Flags.FLAG_IS_MTE_POLICY_ENFORCED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).isMtePolicyEnforced();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean isRecursiveRequiredAppMergingEnabled() {
        return getValue(Flags.FLAG_IS_RECURSIVE_REQUIRED_APP_MERGING_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).isRecursiveRequiredAppMergingEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean lockNowCoexistence() {
        return getValue(Flags.FLAG_LOCK_NOW_COEXISTENCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda34
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).lockNowCoexistence();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean managementModePolicyMetrics() {
        return getValue(Flags.FLAG_MANAGEMENT_MODE_POLICY_METRICS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).managementModePolicyMetrics();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean onboardingBugreportStorageBugFix() {
        return getValue(Flags.FLAG_ONBOARDING_BUGREPORT_STORAGE_BUG_FIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).onboardingBugreportStorageBugFix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean onboardingBugreportV2Enabled() {
        return getValue(Flags.FLAG_ONBOARDING_BUGREPORT_V2_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).onboardingBugreportV2Enabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean onboardingConsentlessBugreports() {
        return getValue(Flags.FLAG_ONBOARDING_CONSENTLESS_BUGREPORTS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda37
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).onboardingConsentlessBugreports();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean permissionMigrationForZeroTrustApiEnabled() {
        return getValue(Flags.FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_API_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).permissionMigrationForZeroTrustApiEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean policyEngineMigrationV2Enabled() {
        return getValue(Flags.FLAG_POLICY_ENGINE_MIGRATION_V2_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda39
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).policyEngineMigrationV2Enabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean provisioningContextParameter() {
        return getValue(Flags.FLAG_PROVISIONING_CONTEXT_PARAMETER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda36
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).provisioningContextParameter();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean quietModeCredentialBugFix() {
        return getValue(Flags.FLAG_QUIET_MODE_CREDENTIAL_BUG_FIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).quietModeCredentialBugFix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean removeManagedEsimOnWorkProfileDeletion() {
        return getValue(Flags.FLAG_REMOVE_MANAGED_ESIM_ON_WORK_PROFILE_DELETION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeManagedEsimOnWorkProfileDeletion();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean removeManagedProfileEnabled() {
        return getValue(Flags.FLAG_REMOVE_MANAGED_PROFILE_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeManagedProfileEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean resetPasswordWithTokenCoexistence() {
        return getValue(Flags.FLAG_RESET_PASSWORD_WITH_TOKEN_COEXISTENCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).resetPasswordWithTokenCoexistence();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean secondaryLockscreenApiEnabled() {
        return getValue(Flags.FLAG_SECONDARY_LOCKSCREEN_API_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).secondaryLockscreenApiEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean securityLogV2Enabled() {
        return getValue(Flags.FLAG_SECURITY_LOG_V2_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda42
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).securityLogV2Enabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean setApplicationRestrictionsCoexistence() {
        return getValue(Flags.FLAG_SET_APPLICATION_RESTRICTIONS_COEXISTENCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setApplicationRestrictionsCoexistence();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean setAutoTimeEnabledCoexistence() {
        return getValue(Flags.FLAG_SET_AUTO_TIME_ENABLED_COEXISTENCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setAutoTimeEnabledCoexistence();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean setAutoTimeZoneEnabledCoexistence() {
        return getValue(Flags.FLAG_SET_AUTO_TIME_ZONE_ENABLED_COEXISTENCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda40
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setAutoTimeZoneEnabledCoexistence();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean setBackupServiceEnabledCoexistence() {
        return getValue(Flags.FLAG_SET_BACKUP_SERVICE_ENABLED_COEXISTENCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda46
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setBackupServiceEnabledCoexistence();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean setKeyguardDisabledFeaturesCoexistence() {
        return getValue(Flags.FLAG_SET_KEYGUARD_DISABLED_FEATURES_COEXISTENCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setKeyguardDisabledFeaturesCoexistence();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean setMtePolicyCoexistence() {
        return getValue(Flags.FLAG_SET_MTE_POLICY_COEXISTENCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda35
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setMtePolicyCoexistence();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean setPermissionGrantStateCoexistence() {
        return getValue(Flags.FLAG_SET_PERMISSION_GRANT_STATE_COEXISTENCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setPermissionGrantStateCoexistence();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean splitCreateManagedProfileEnabled() {
        return getValue(Flags.FLAG_SPLIT_CREATE_MANAGED_PROFILE_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).splitCreateManagedProfileEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean suspendPackagesCoexistence() {
        return getValue(Flags.FLAG_SUSPEND_PACKAGES_COEXISTENCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).suspendPackagesCoexistence();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean unmanagedModeMigration() {
        return getValue(Flags.FLAG_UNMANAGED_MODE_MIGRATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda45
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).unmanagedModeMigration();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean unsuspendNotSuspended() {
        return getValue(Flags.FLAG_UNSUSPEND_NOT_SUSPENDED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).unsuspendNotSuspended();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean usePolicyIntersectionForPermittedInputMethods() {
        return getValue(Flags.FLAG_USE_POLICY_INTERSECTION_FOR_PERMITTED_INPUT_METHODS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).usePolicyIntersectionForPermittedInputMethods();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.FeatureFlags
    public boolean userProvisioningSameState() {
        return getValue(Flags.FLAG_USER_PROVISIONING_SAME_STATE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.CustomFeatureFlags$$ExternalSyntheticLambda38
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).userProvisioningSameState();
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
        return Arrays.asList(Flags.FLAG_ACTIVE_ADMIN_CLEANUP, Flags.FLAG_ALLOW_QUERYING_PROFILE_TYPE, Flags.FLAG_ASSIST_CONTENT_USER_RESTRICTION_ENABLED, Flags.FLAG_BACKUP_CONNECTED_APPS_SETTINGS, Flags.FLAG_BACKUP_SERVICE_SECURITY_LOG_EVENT_ENABLED, Flags.FLAG_COEXISTENCE_MIGRATION_FOR_SUPERVISION_ENABLED, Flags.FLAG_CROSS_USER_SUSPENSION_ENABLED_RO, Flags.FLAG_DEDICATED_DEVICE_CONTROL_API_ENABLED, Flags.FLAG_DEFAULT_SMS_PERSONAL_APP_SUSPENSION_FIX_ENABLED, Flags.FLAG_DEVICE_POLICY_SIZE_TRACKING_ENABLED, Flags.FLAG_DEVICE_THEFT_API_ENABLED, Flags.FLAG_DEVICE_THEFT_IMPL_ENABLED, Flags.FLAG_DISALLOW_USER_CONTROL_STOPPED_STATE_FIX, Flags.FLAG_ENABLE_SUPERVISION_SERVICE_SYNC, Flags.FLAG_ESIM_MANAGEMENT_ENABLED, Flags.FLAG_ESIM_MANAGEMENT_UX_ENABLED, Flags.FLAG_HEADLESS_DEVICE_OWNER_SINGLE_USER_ENABLED, Flags.FLAG_HEADLESS_SINGLE_MIN_TARGET_SDK, Flags.FLAG_INTERNAL_LOG_EVENT_LISTENER, Flags.FLAG_IS_MTE_POLICY_ENFORCED, Flags.FLAG_IS_RECURSIVE_REQUIRED_APP_MERGING_ENABLED, Flags.FLAG_LOCK_NOW_COEXISTENCE, Flags.FLAG_MANAGEMENT_MODE_POLICY_METRICS, Flags.FLAG_ONBOARDING_BUGREPORT_STORAGE_BUG_FIX, Flags.FLAG_ONBOARDING_BUGREPORT_V2_ENABLED, Flags.FLAG_ONBOARDING_CONSENTLESS_BUGREPORTS, Flags.FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_API_ENABLED, Flags.FLAG_POLICY_ENGINE_MIGRATION_V2_ENABLED, Flags.FLAG_PROVISIONING_CONTEXT_PARAMETER, Flags.FLAG_QUIET_MODE_CREDENTIAL_BUG_FIX, Flags.FLAG_REMOVE_MANAGED_ESIM_ON_WORK_PROFILE_DELETION, Flags.FLAG_REMOVE_MANAGED_PROFILE_ENABLED, Flags.FLAG_RESET_PASSWORD_WITH_TOKEN_COEXISTENCE, Flags.FLAG_SECONDARY_LOCKSCREEN_API_ENABLED, Flags.FLAG_SECURITY_LOG_V2_ENABLED, Flags.FLAG_SET_APPLICATION_RESTRICTIONS_COEXISTENCE, Flags.FLAG_SET_AUTO_TIME_ENABLED_COEXISTENCE, Flags.FLAG_SET_AUTO_TIME_ZONE_ENABLED_COEXISTENCE, Flags.FLAG_SET_BACKUP_SERVICE_ENABLED_COEXISTENCE, Flags.FLAG_SET_KEYGUARD_DISABLED_FEATURES_COEXISTENCE, Flags.FLAG_SET_MTE_POLICY_COEXISTENCE, Flags.FLAG_SET_PERMISSION_GRANT_STATE_COEXISTENCE, Flags.FLAG_SPLIT_CREATE_MANAGED_PROFILE_ENABLED, Flags.FLAG_SUSPEND_PACKAGES_COEXISTENCE, Flags.FLAG_UNMANAGED_MODE_MIGRATION, Flags.FLAG_UNSUSPEND_NOT_SUSPENDED, Flags.FLAG_USE_POLICY_INTERSECTION_FOR_PERMITTED_INPUT_METHODS, Flags.FLAG_USER_PROVISIONING_SAME_STATE);
    }
}
