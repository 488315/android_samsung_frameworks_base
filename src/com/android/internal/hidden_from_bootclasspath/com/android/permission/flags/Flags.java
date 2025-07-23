package com.android.internal.hidden_from_bootclasspath.com.android.permission.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADD_BANNERS_TO_PRIVACY_SENSITIVE_APPS_FOR_AAOS = "com.android.permission.flags.add_banners_to_privacy_sensitive_apps_for_aaos";
    public static final String FLAG_APP_PERMISSION_FRAGMENT_USES_PREFERENCES = "com.android.permission.flags.app_permission_fragment_uses_preferences";
    public static final String FLAG_ARCHIVING_READ_ONLY = "com.android.permission.flags.archiving_read_only";
    public static final String FLAG_CROSS_USER_ROLE_ENABLED = "com.android.permission.flags.cross_user_role_enabled";
    public static final String FLAG_CROSS_USER_ROLE_UX_BUGFIX_ENABLED = "com.android.permission.flags.cross_user_role_ux_bugfix_enabled";
    public static final String FLAG_DECLUTTERED_PERMISSION_MANAGER_ENABLED = "com.android.permission.flags.decluttered_permission_manager_enabled";
    public static final String FLAG_DEFAULT_APPS_RECOMMENDATION_ENABLED = "com.android.permission.flags.default_apps_recommendation_enabled";
    public static final String FLAG_ENABLE_COARSE_FINE_LOCATION_PROMPT_FOR_AAOS = "com.android.permission.flags.enable_coarse_fine_location_prompt_for_aaos";
    public static final String FLAG_ENHANCED_CONFIRMATION_BACKPORT_ENABLED = "com.android.permission.flags.enhanced_confirmation_backport_enabled";
    public static final String FLAG_EXPRESSIVE_DESIGN_ENABLED = "com.android.permission.flags.expressive_design_enabled";
    public static final String FLAG_FIX_SAFETY_CENTER_TOUCH_TARGET = "com.android.permission.flags.fix_safety_center_touch_target";
    public static final String FLAG_ODAD_NOTIFICATIONS_SUPPORTED = "com.android.permission.flags.odad_notifications_supported";
    public static final String FLAG_PERMISSION_TIMELINE_ATTRIBUTION_LABEL_FIX = "com.android.permission.flags.permission_timeline_attribution_label_fix";
    public static final String FLAG_PRIVATE_PROFILE_SUPPORTED = "com.android.permission.flags.private_profile_supported";
    public static final String FLAG_PRIVATE_PROFILE_TITLE_API = "com.android.permission.flags.private_profile_title_api";
    public static final String FLAG_SAFETY_CENTER_ENABLED_NO_DEVICE_CONFIG = "com.android.permission.flags.safety_center_enabled_no_device_config";
    public static final String FLAG_SAFETY_CENTER_ISSUE_ONLY_AFFECTS_GROUP_STATUS = "com.android.permission.flags.safety_center_issue_only_affects_group_status";
    public static final String FLAG_WEAR_COMPOSE_MATERIAL3 = "com.android.permission.flags.wear_compose_material3";
    public static final String FLAG_WEAR_PRIVACY_DASHBOARD_ENABLED_READ_ONLY = "com.android.permission.flags.wear_privacy_dashboard_enabled_read_only";

    public static boolean privateProfileSupported() {
        return true;
    }

    public static boolean privateProfileTitleApi() {
        return true;
    }

    public static boolean wearPrivacyDashboardEnabledReadOnly() {
        return true;
    }

    public static boolean addBannersToPrivacySensitiveAppsForAaos() {
        return FEATURE_FLAGS.addBannersToPrivacySensitiveAppsForAaos();
    }

    public static boolean appPermissionFragmentUsesPreferences() {
        return FEATURE_FLAGS.appPermissionFragmentUsesPreferences();
    }

    public static boolean archivingReadOnly() {
        return FEATURE_FLAGS.archivingReadOnly();
    }

    public static boolean crossUserRoleEnabled() {
        return FEATURE_FLAGS.crossUserRoleEnabled();
    }

    public static boolean crossUserRoleUxBugfixEnabled() {
        return FEATURE_FLAGS.crossUserRoleUxBugfixEnabled();
    }

    public static boolean declutteredPermissionManagerEnabled() {
        return FEATURE_FLAGS.declutteredPermissionManagerEnabled();
    }

    public static boolean defaultAppsRecommendationEnabled() {
        return FEATURE_FLAGS.defaultAppsRecommendationEnabled();
    }

    public static boolean enableCoarseFineLocationPromptForAaos() {
        return FEATURE_FLAGS.enableCoarseFineLocationPromptForAaos();
    }

    public static boolean enhancedConfirmationBackportEnabled() {
        return FEATURE_FLAGS.enhancedConfirmationBackportEnabled();
    }

    public static boolean expressiveDesignEnabled() {
        return FEATURE_FLAGS.expressiveDesignEnabled();
    }

    public static boolean fixSafetyCenterTouchTarget() {
        return FEATURE_FLAGS.fixSafetyCenterTouchTarget();
    }

    public static boolean odadNotificationsSupported() {
        return FEATURE_FLAGS.odadNotificationsSupported();
    }

    public static boolean permissionTimelineAttributionLabelFix() {
        return FEATURE_FLAGS.permissionTimelineAttributionLabelFix();
    }

    public static boolean safetyCenterEnabledNoDeviceConfig() {
        return FEATURE_FLAGS.safetyCenterEnabledNoDeviceConfig();
    }

    public static boolean safetyCenterIssueOnlyAffectsGroupStatus() {
        return FEATURE_FLAGS.safetyCenterIssueOnlyAffectsGroupStatus();
    }

    public static boolean wearComposeMaterial3() {
        return FEATURE_FLAGS.wearComposeMaterial3();
    }
}
