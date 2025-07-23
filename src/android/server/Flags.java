package android.server;

/* loaded from: classes3.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ALLOW_NETWORK_TIME_UPDATE_SERVICE = "android.server.allow_network_time_update_service";
    public static final String FLAG_ALLOW_REMOVING_VPN_SERVICE = "android.server.allow_removing_vpn_service";
    public static final String FLAG_EARLY_SYSTEM_CONFIG_INIT = "android.server.early_system_config_init";
    public static final String FLAG_ENABLE_THEME_SERVICE = "android.server.enable_theme_service";
    public static final String FLAG_MIGRATE_WRIST_ORIENTATION = "android.server.migrate_wrist_orientation";
    public static final String FLAG_REMOVE_APP_INTEGRITY_MANAGER_SERVICE = "android.server.remove_app_integrity_manager_service";
    public static final String FLAG_REMOVE_GAME_MANAGER_SERVICE_FROM_WEAR = "android.server.remove_game_manager_service_from_wear";
    public static final String FLAG_REMOVE_TEXT_SERVICE = "android.server.remove_text_service";
    public static final String FLAG_REMOVE_WEARABLE_SENSING_SERVICE_FROM_WEAR = "android.server.remove_wearable_sensing_service_from_wear";
    public static final String FLAG_TELEMETRY_APIS_SERVICE = "android.server.telemetry_apis_service";
    public static final String FLAG_WEAR_GESTURE_API = "android.server.wear_gesture_api";

    public static boolean allowNetworkTimeUpdateService() {
        return FEATURE_FLAGS.allowNetworkTimeUpdateService();
    }

    public static boolean allowRemovingVpnService() {
        return FEATURE_FLAGS.allowRemovingVpnService();
    }

    public static boolean earlySystemConfigInit() {
        return FEATURE_FLAGS.earlySystemConfigInit();
    }

    public static boolean enableThemeService() {
        return FEATURE_FLAGS.enableThemeService();
    }

    public static boolean migrateWristOrientation() {
        return FEATURE_FLAGS.migrateWristOrientation();
    }

    public static boolean removeAppIntegrityManagerService() {
        return FEATURE_FLAGS.removeAppIntegrityManagerService();
    }

    public static boolean removeGameManagerServiceFromWear() {
        return FEATURE_FLAGS.removeGameManagerServiceFromWear();
    }

    public static boolean removeTextService() {
        return FEATURE_FLAGS.removeTextService();
    }

    public static boolean removeWearableSensingServiceFromWear() {
        return FEATURE_FLAGS.removeWearableSensingServiceFromWear();
    }

    public static boolean telemetryApisService() {
        return FEATURE_FLAGS.telemetryApisService();
    }

    public static boolean wearGestureApi() {
        return FEATURE_FLAGS.wearGestureApi();
    }
}
