package com.android.internal.hidden_from_bootclasspath.android.credentials.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_CLEAR_CREDENTIALS_FIX_ENABLED = "android.credentials.flags.clear_credentials_fix_enabled";
    public static final String FLAG_CLEAR_SESSION_ENABLED = "android.credentials.flags.clear_session_enabled";
    public static final String FLAG_CONFIGURABLE_SELECTOR_UI_ENABLED = "android.credentials.flags.configurable_selector_ui_enabled";
    public static final String FLAG_CREDMAN_BIOMETRIC_API_ENABLED = "android.credentials.flags.credman_biometric_api_enabled";
    public static final String FLAG_FIX_METRIC_DUPLICATION_EMITS = "android.credentials.flags.fix_metric_duplication_emits";
    public static final String FLAG_FRAMEWORK_SESSION_ID_METRIC_BUNDLE = "android.credentials.flags.framework_session_id_metric_bundle";
    public static final String FLAG_HYBRID_FILTER_OPT_FIX_ENABLED = "android.credentials.flags.hybrid_filter_opt_fix_enabled";
    public static final String FLAG_INSTANT_APPS_ENABLED = "android.credentials.flags.instant_apps_enabled";
    public static final String FLAG_NEW_FRAMEWORK_METRICS = "android.credentials.flags.new_framework_metrics";
    public static final String FLAG_NEW_SETTINGS_INTENTS = "android.credentials.flags.new_settings_intents";
    public static final String FLAG_NEW_SETTINGS_UI = "android.credentials.flags.new_settings_ui";
    public static final String FLAG_PACKAGE_UPDATE_FIX_ENABLED = "android.credentials.flags.package_update_fix_enabled";
    public static final String FLAG_PROPAGATE_USER_CONTEXT_FOR_INTENT_CREATION = "android.credentials.flags.propagate_user_context_for_intent_creation";
    public static final String FLAG_SAFEGUARD_CANDIDATE_CREDENTIALS_API_CALLER = "android.credentials.flags.safeguard_candidate_credentials_api_caller";
    public static final String FLAG_SELECTOR_UI_IMPROVEMENTS_ENABLED = "android.credentials.flags.selector_ui_improvements_enabled";
    public static final String FLAG_SETTINGS_ACTIVITY_ENABLED = "android.credentials.flags.settings_activity_enabled";
    public static final String FLAG_SETTINGS_W_FIXES = "android.credentials.flags.settings_w_fixes";
    public static final String FLAG_TTL_FIX_ENABLED = "android.credentials.flags.ttl_fix_enabled";
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

    public static boolean fixMetricDuplicationEmits() {
        return FEATURE_FLAGS.fixMetricDuplicationEmits();
    }

    public static boolean frameworkSessionIdMetricBundle() {
        return FEATURE_FLAGS.frameworkSessionIdMetricBundle();
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

    public static boolean packageUpdateFixEnabled() {
        return FEATURE_FLAGS.packageUpdateFixEnabled();
    }

    public static boolean propagateUserContextForIntentCreation() {
        return FEATURE_FLAGS.propagateUserContextForIntentCreation();
    }

    public static boolean safeguardCandidateCredentialsApiCaller() {
        return FEATURE_FLAGS.safeguardCandidateCredentialsApiCaller();
    }

    public static boolean selectorUiImprovementsEnabled() {
        return FEATURE_FLAGS.selectorUiImprovementsEnabled();
    }

    public static boolean settingsActivityEnabled() {
        return FEATURE_FLAGS.settingsActivityEnabled();
    }

    public static boolean settingsWFixes() {
        return FEATURE_FLAGS.settingsWFixes();
    }

    public static boolean ttlFixEnabled() {
        return FEATURE_FLAGS.ttlFixEnabled();
    }

    public static boolean wearCredentialManagerEnabled() {
        return FEATURE_FLAGS.wearCredentialManagerEnabled();
    }
}
