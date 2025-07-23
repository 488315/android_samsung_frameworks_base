package com.android.internal.hidden_from_bootclasspath.android.provider;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_A11Y_STANDALONE_GESTURE_ENABLED = "android.provider.a11y_standalone_gesture_enabled";
    public static final String FLAG_ALLOW_CONFIG_MAXIMUM_CALL_LOG_ENTRIES_PER_SIM = "android.provider.allow_config_maximum_call_log_entries_per_sim";
    public static final String FLAG_NEW_DEFAULT_ACCOUNT_API_ENABLED = "android.provider.new_default_account_api_enabled";
    public static final String FLAG_REDUCE_BINDER_TRANSACTION_SIZE_FOR_GET_ALL_PROPERTIES = "android.provider.reduce_binder_transaction_size_for_get_all_properties";
    public static final String FLAG_SYSTEM_REGIONAL_PREFERENCES_API_ENABLED = "android.provider.system_regional_preferences_api_enabled";
    public static final String FLAG_SYSTEM_SETTINGS_DEFAULT = "android.provider.system_settings_default";
    public static final String FLAG_USER_KEYS = "android.provider.user_keys";

    public static boolean a11yStandaloneGestureEnabled() {
        return FEATURE_FLAGS.a11yStandaloneGestureEnabled();
    }

    public static boolean allowConfigMaximumCallLogEntriesPerSim() {
        return FEATURE_FLAGS.allowConfigMaximumCallLogEntriesPerSim();
    }

    public static boolean newDefaultAccountApiEnabled() {
        return FEATURE_FLAGS.newDefaultAccountApiEnabled();
    }

    public static boolean reduceBinderTransactionSizeForGetAllProperties() {
        return FEATURE_FLAGS.reduceBinderTransactionSizeForGetAllProperties();
    }

    public static boolean systemRegionalPreferencesApiEnabled() {
        return FEATURE_FLAGS.systemRegionalPreferencesApiEnabled();
    }

    public static boolean systemSettingsDefault() {
        return FEATURE_FLAGS.systemSettingsDefault();
    }

    public static boolean userKeys() {
        return FEATURE_FLAGS.userKeys();
    }
}
