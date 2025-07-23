package android.net.wifi.flags;

/* loaded from: classes3.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_GET_DEVICE_CROSS_AKM_ROAMING_SUPPORT = "android.net.wifi.flags.get_device_cross_akm_roaming_support";
    public static final String FLAG_HOTSPOT_NETWORK_CONNECTING_STATE_FOR_DETAILS_PAGE = "android.net.wifi.flags.hotspot_network_connecting_state_for_details_page";
    public static final String FLAG_HOTSPOT_NETWORK_UNKNOWN_STATUS_RESETS_CONNECTING_STATE = "android.net.wifi.flags.hotspot_network_unknown_status_resets_connecting_state";
    public static final String FLAG_LEGACY_KEYSTORE_TO_WIFI_BLOBSTORE_MIGRATION_READ_ONLY = "android.net.wifi.flags.legacy_keystore_to_wifi_blobstore_migration_read_only";
    public static final String FLAG_NETWORK_PROVIDER_BATTERY_CHARGING_STATUS = "android.net.wifi.flags.network_provider_battery_charging_status";
    public static final String FLAG_USD = "android.net.wifi.flags.usd";

    public static boolean getDeviceCrossAkmRoamingSupport() {
        return FEATURE_FLAGS.getDeviceCrossAkmRoamingSupport();
    }

    public static boolean hotspotNetworkConnectingStateForDetailsPage() {
        return FEATURE_FLAGS.hotspotNetworkConnectingStateForDetailsPage();
    }

    public static boolean hotspotNetworkUnknownStatusResetsConnectingState() {
        return FEATURE_FLAGS.hotspotNetworkUnknownStatusResetsConnectingState();
    }

    public static boolean legacyKeystoreToWifiBlobstoreMigrationReadOnly() {
        return FEATURE_FLAGS.legacyKeystoreToWifiBlobstoreMigrationReadOnly();
    }

    public static boolean networkProviderBatteryChargingStatus() {
        return FEATURE_FLAGS.networkProviderBatteryChargingStatus();
    }

    public static boolean usd() {
        return FEATURE_FLAGS.usd();
    }
}
