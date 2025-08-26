package com.android.internal.hidden_from_bootclasspath.com.android.permission.flags;

import android.os.flagging.AconfigPackage;
import android.util.Log;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    private static final String TAG = "FeatureFlagsImplExport";
    private static boolean addBannersToPrivacySensitiveAppsForAaos = false;
    private static boolean appPermissionFragmentUsesPreferences = false;
    private static boolean archivingReadOnly = false;
    private static boolean crossUserRoleEnabled = false;
    private static boolean crossUserRoleUxBugfixEnabled = false;
    private static boolean declutteredPermissionManagerEnabled = false;
    private static boolean defaultAppsRecommendationEnabled = false;
    private static boolean enableCoarseFineLocationPromptForAaos = false;
    private static boolean enhancedConfirmationBackportEnabled = false;
    private static boolean expressiveDesignEnabled = false;
    private static boolean fixSafetyCenterTouchTarget = false;
    private static volatile boolean isCached = false;
    private static boolean odadNotificationsSupported = false;
    private static boolean permissionTimelineAttributionLabelFix = false;
    private static boolean privateProfileSupported = false;
    private static boolean privateProfileTitleApi = false;
    private static boolean safetyCenterEnabledNoDeviceConfig = false;
    private static boolean safetyCenterIssueOnlyAffectsGroupStatus = false;
    private static boolean wearComposeMaterial3 = false;
    private static boolean wearPrivacyDashboardEnabledReadOnly = false;

    private void init() {
        try {
            AconfigPackage aconfigPackageLoad = AconfigPackage.load("com.android.permission.flags");
            addBannersToPrivacySensitiveAppsForAaos = aconfigPackageLoad.getBooleanFlagValue("add_banners_to_privacy_sensitive_apps_for_aaos", false);
            appPermissionFragmentUsesPreferences = aconfigPackageLoad.getBooleanFlagValue("app_permission_fragment_uses_preferences", false);
            archivingReadOnly = aconfigPackageLoad.getBooleanFlagValue("archiving_read_only", false);
            crossUserRoleEnabled = aconfigPackageLoad.getBooleanFlagValue("cross_user_role_enabled", false);
            crossUserRoleUxBugfixEnabled = aconfigPackageLoad.getBooleanFlagValue("cross_user_role_ux_bugfix_enabled", false);
            declutteredPermissionManagerEnabled = aconfigPackageLoad.getBooleanFlagValue("decluttered_permission_manager_enabled", false);
            defaultAppsRecommendationEnabled = aconfigPackageLoad.getBooleanFlagValue("default_apps_recommendation_enabled", false);
            enableCoarseFineLocationPromptForAaos = aconfigPackageLoad.getBooleanFlagValue("enable_coarse_fine_location_prompt_for_aaos", false);
            enhancedConfirmationBackportEnabled = aconfigPackageLoad.getBooleanFlagValue("enhanced_confirmation_backport_enabled", false);
            expressiveDesignEnabled = aconfigPackageLoad.getBooleanFlagValue("expressive_design_enabled", false);
            fixSafetyCenterTouchTarget = aconfigPackageLoad.getBooleanFlagValue("fix_safety_center_touch_target", false);
            odadNotificationsSupported = aconfigPackageLoad.getBooleanFlagValue("odad_notifications_supported", false);
            permissionTimelineAttributionLabelFix = aconfigPackageLoad.getBooleanFlagValue("permission_timeline_attribution_label_fix", false);
            privateProfileSupported = true;
            privateProfileTitleApi = true;
            safetyCenterEnabledNoDeviceConfig = aconfigPackageLoad.getBooleanFlagValue("safety_center_enabled_no_device_config", false);
            safetyCenterIssueOnlyAffectsGroupStatus = aconfigPackageLoad.getBooleanFlagValue("safety_center_issue_only_affects_group_status", false);
            wearComposeMaterial3 = aconfigPackageLoad.getBooleanFlagValue("wear_compose_material3", false);
            wearPrivacyDashboardEnabledReadOnly = true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } catch (LinkageError e2) {
            Log.w(TAG, e2.toString());
        }
        isCached = true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean addBannersToPrivacySensitiveAppsForAaos() {
        if (!isCached) {
            init();
        }
        return addBannersToPrivacySensitiveAppsForAaos;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean appPermissionFragmentUsesPreferences() {
        if (!isCached) {
            init();
        }
        return appPermissionFragmentUsesPreferences;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean archivingReadOnly() {
        if (!isCached) {
            init();
        }
        return archivingReadOnly;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean crossUserRoleEnabled() {
        if (!isCached) {
            init();
        }
        return crossUserRoleEnabled;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean crossUserRoleUxBugfixEnabled() {
        if (!isCached) {
            init();
        }
        return crossUserRoleUxBugfixEnabled;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean declutteredPermissionManagerEnabled() {
        if (!isCached) {
            init();
        }
        return declutteredPermissionManagerEnabled;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean defaultAppsRecommendationEnabled() {
        if (!isCached) {
            init();
        }
        return defaultAppsRecommendationEnabled;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean enableCoarseFineLocationPromptForAaos() {
        if (!isCached) {
            init();
        }
        return enableCoarseFineLocationPromptForAaos;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean enhancedConfirmationBackportEnabled() {
        if (!isCached) {
            init();
        }
        return enhancedConfirmationBackportEnabled;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean expressiveDesignEnabled() {
        if (!isCached) {
            init();
        }
        return expressiveDesignEnabled;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean fixSafetyCenterTouchTarget() {
        if (!isCached) {
            init();
        }
        return fixSafetyCenterTouchTarget;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean odadNotificationsSupported() {
        if (!isCached) {
            init();
        }
        return odadNotificationsSupported;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean permissionTimelineAttributionLabelFix() {
        if (!isCached) {
            init();
        }
        return permissionTimelineAttributionLabelFix;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean privateProfileSupported() {
        if (!isCached) {
            init();
        }
        return privateProfileSupported;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean privateProfileTitleApi() {
        if (!isCached) {
            init();
        }
        return privateProfileTitleApi;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean safetyCenterEnabledNoDeviceConfig() {
        if (!isCached) {
            init();
        }
        return safetyCenterEnabledNoDeviceConfig;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean safetyCenterIssueOnlyAffectsGroupStatus() {
        if (!isCached) {
            init();
        }
        return safetyCenterIssueOnlyAffectsGroupStatus;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean wearComposeMaterial3() {
        if (!isCached) {
            init();
        }
        return wearComposeMaterial3;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean wearPrivacyDashboardEnabledReadOnly() {
        if (!isCached) {
            init();
        }
        return wearPrivacyDashboardEnabledReadOnly;
    }
}
