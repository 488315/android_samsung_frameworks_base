package com.android.internal.hidden_from_bootclasspath.android.provider;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_A11Y_STANDALONE_GESTURE_ENABLED =
            "android.provider.a11y_standalone_gesture_enabled";
    public static final String FLAG_BACKUP_TASKS_SETTINGS_SCREEN =
            "android.provider.backup_tasks_settings_screen";
    public static final String FLAG_SYSTEM_SETTINGS_DEFAULT =
            "android.provider.system_settings_default";
    public static final String FLAG_USER_KEYS = "android.provider.user_keys";

    public static boolean a11yStandaloneGestureEnabled() {
        return FEATURE_FLAGS.a11yStandaloneGestureEnabled();
    }

    public static boolean backupTasksSettingsScreen() {
        return FEATURE_FLAGS.backupTasksSettingsScreen();
    }

    public static boolean systemSettingsDefault() {
        return FEATURE_FLAGS.systemSettingsDefault();
    }

    public static boolean userKeys() {
        return FEATURE_FLAGS.userKeys();
    }
}
