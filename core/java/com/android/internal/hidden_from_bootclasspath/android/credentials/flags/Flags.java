package com.android.internal.hidden_from_bootclasspath.android.credentials.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_CLEAR_CREDENTIALS_FIX_ENABLED = "android.credentials.flags.clear_credentials_fix_enabled";
    public static final String FLAG_CLEAR_SESSION_ENABLED = "android.credentials.flags.clear_session_enabled";
    public static final String FLAG_CONFIGURABLE_SELECTOR_UI_ENABLED = "android.credentials.flags.configurable_selector_ui_enabled";
    public static final String FLAG_CREDMAN_BIOMETRIC_API_ENABLED = "android.credentials.flags.credman_biometric_api_enabled";
    public static final String FLAG_HYBRID_FILTER_OPT_FIX_ENABLED = "android.credentials.flags.hybrid_filter_opt_fix_enabled";
    public static final String FLAG_INSTANT_APPS_ENABLED = "android.credentials.flags.instant_apps_enabled";
    public static final String FLAG_NEW_FRAMEWORK_METRICS = "android.credentials.flags.new_framework_metrics";
    public static final String FLAG_NEW_SETTINGS_INTENTS = "android.credentials.flags.new_settings_intents";
    public static final String FLAG_NEW_SETTINGS_UI = "android.credentials.flags.new_settings_ui";
    public static final String FLAG_SELECTOR_UI_IMPROVEMENTS_ENABLED = "android.credentials.flags.selector_ui_improvements_enabled";
    public static final String FLAG_SETTINGS_ACTIVITY_ENABLED = "android.credentials.flags.settings_activity_enabled";
    public static final String FLAG_WEAR_CREDENTIAL_MANAGER_ENABLED = "android.credentials.flags.wear_credential_manager_enabled";

    public static boolean clearCredentialsFixEnabled() {
        return FEATURE_FLAGS.clearCredentialsFixEnabled();
    }

    public static boolean clearSessionEnabled() {
        return FEATURE_FLAGS.clearSessionEnabled();
    }

    public static boolean configurableSelectorUiEnabled() {
        return FEATURE_FLAGS.configurableSelectorUiEnabled();
    }

    public static boolean credmanBiometricApiEnabled() {
        return FEATURE_FLAGS.credmanBiometricApiEnabled();
    }

    public static boolean hybridFilterOptFixEnabled() {
        return FEATURE_FLAGS.hybridFilterOptFixEnabled();
    }

    public static boolean instantAppsEnabled() {
        return FEATURE_FLAGS.instantAppsEnabled();
    }

    public static boolean newFrameworkMetrics() {
        return FEATURE_FLAGS.newFrameworkMetrics();
    }

    public static boolean newSettingsIntents() {
        return FEATURE_FLAGS.newSettingsIntents();
    }

    public static boolean newSettingsUi() {
        return FEATURE_FLAGS.newSettingsUi();
    }

    public static boolean selectorUiImprovementsEnabled() {
        return FEATURE_FLAGS.selectorUiImprovementsEnabled();
    }

    public static boolean settingsActivityEnabled() {
        return FEATURE_FLAGS.settingsActivityEnabled();
    }

    public static boolean wearCredentialManagerEnabled() {
        return FEATURE_FLAGS.wearCredentialManagerEnabled();
    }
}
