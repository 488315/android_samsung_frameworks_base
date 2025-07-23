package com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_DEPRECATE_DPM_SUPERVISION_APIS = "android.app.supervision.flags.deprecate_dpm_supervision_apis";
    public static final String FLAG_ENABLE_APP_APPROVAL = "android.app.supervision.flags.enable_app_approval";
    public static final String FLAG_ENABLE_SUPERVISION_APP_SERVICE = "android.app.supervision.flags.enable_supervision_app_service";
    public static final String FLAG_ENABLE_SUPERVISION_PIN_RECOVERY_SCREEN = "android.app.supervision.flags.enable_supervision_pin_recovery_screen";
    public static final String FLAG_ENABLE_SUPERVISION_SETTINGS_SCREEN = "android.app.supervision.flags.enable_supervision_settings_screen";
    public static final String FLAG_ENABLE_SYNC_WITH_DPM = "android.app.supervision.flags.enable_sync_with_dpm";
    public static final String FLAG_ENABLE_WEB_CONTENT_FILTERS_SCREEN = "android.app.supervision.flags.enable_web_content_filters_screen";
    public static final String FLAG_SUPERVISION_API = "android.app.supervision.flags.supervision_api";
    public static final String FLAG_SUPERVISION_API_ON_WEAR = "android.app.supervision.flags.supervision_api_on_wear";
    public static final String FLAG_SUPERVISION_MANAGER_APIS = "android.app.supervision.flags.supervision_manager_apis";

    public static boolean deprecateDpmSupervisionApis() {
        return FEATURE_FLAGS.deprecateDpmSupervisionApis();
    }

    public static boolean enableAppApproval() {
        return FEATURE_FLAGS.enableAppApproval();
    }

    public static boolean enableSupervisionAppService() {
        return FEATURE_FLAGS.enableSupervisionAppService();
    }

    public static boolean enableSupervisionPinRecoveryScreen() {
        return FEATURE_FLAGS.enableSupervisionPinRecoveryScreen();
    }

    public static boolean enableSupervisionSettingsScreen() {
        return FEATURE_FLAGS.enableSupervisionSettingsScreen();
    }

    public static boolean enableSyncWithDpm() {
        return FEATURE_FLAGS.enableSyncWithDpm();
    }

    public static boolean enableWebContentFiltersScreen() {
        return FEATURE_FLAGS.enableWebContentFiltersScreen();
    }

    public static boolean supervisionApi() {
        return FEATURE_FLAGS.supervisionApi();
    }

    public static boolean supervisionApiOnWear() {
        return FEATURE_FLAGS.supervisionApiOnWear();
    }

    public static boolean supervisionManagerApis() {
        return FEATURE_FLAGS.supervisionManagerApis();
    }
}
