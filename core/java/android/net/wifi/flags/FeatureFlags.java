package android.net.wifi.flags;

public interface FeatureFlags {
    boolean getDeviceCrossAkmRoamingSupport();

    boolean legacyKeystoreToWifiBlobstoreMigration();

    boolean networkProviderBatteryChargingStatus();
}
