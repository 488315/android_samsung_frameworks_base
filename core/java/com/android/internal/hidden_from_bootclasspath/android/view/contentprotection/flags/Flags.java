package com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_BLOCKLIST_UPDATE_ENABLED =
            "android.view.contentprotection.flags.blocklist_update_enabled";
    public static final String FLAG_CREATE_ACCESSIBILITY_OVERLAY_APP_OP_ENABLED =
            "android.view.contentprotection.flags.create_accessibility_overlay_app_op_enabled";
    public static final String FLAG_MANAGE_DEVICE_POLICY_ENABLED =
            "android.view.contentprotection.flags.manage_device_policy_enabled";
    public static final String FLAG_PARSE_GROUPS_CONFIG_ENABLED =
            "android.view.contentprotection.flags.parse_groups_config_enabled";
    public static final String FLAG_RAPID_CLEAR_NOTIFICATIONS_BY_LISTENER_APP_OP_ENABLED =
            "android.view.contentprotection.flags.rapid_clear_notifications_by_listener_app_op_enabled";
    public static final String FLAG_SETTING_UI_ENABLED =
            "android.view.contentprotection.flags.setting_ui_enabled";

    public static boolean blocklistUpdateEnabled() {
        return FEATURE_FLAGS.blocklistUpdateEnabled();
    }

    public static boolean createAccessibilityOverlayAppOpEnabled() {
        return FEATURE_FLAGS.createAccessibilityOverlayAppOpEnabled();
    }

    public static boolean manageDevicePolicyEnabled() {
        return FEATURE_FLAGS.manageDevicePolicyEnabled();
    }

    public static boolean parseGroupsConfigEnabled() {
        return FEATURE_FLAGS.parseGroupsConfigEnabled();
    }

    public static boolean rapidClearNotificationsByListenerAppOpEnabled() {
        return FEATURE_FLAGS.rapidClearNotificationsByListenerAppOpEnabled();
    }

    public static boolean settingUiEnabled() {
        return FEATURE_FLAGS.settingUiEnabled();
    }
}
