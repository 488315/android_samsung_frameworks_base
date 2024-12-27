package android.net.wifi.flags;

/* loaded from: classes3.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_GET_DEVICE_CROSS_AKM_ROAMING_SUPPORT = "android.net.wifi.flags.get_device_cross_akm_roaming_support";
    public static final String FLAG_LEGACY_KEYSTORE_TO_WIFI_BLOBSTORE_MIGRATION = "android.net.wifi.flags.legacy_keystore_to_wifi_blobstore_migration";
    public static final String FLAG_NETWORK_PROVIDER_BATTERY_CHARGING_STATUS = "android.net.wifi.flags.network_provider_battery_charging_status";

    public static boolean getDeviceCrossAkmRoamingSupport() {
        return FEATURE_FLAGS.getDeviceCrossAkmRoamingSupport();
    }

    public static boolean legacyKeystoreToWifiBlobstoreMigration() {
        return FEATURE_FLAGS.legacyKeystoreToWifiBlobstoreMigration();
    }

    public static boolean networkProviderBatteryChargingStatus() {
        return FEATURE_FLAGS.networkProviderBatteryChargingStatus();
    }
}
