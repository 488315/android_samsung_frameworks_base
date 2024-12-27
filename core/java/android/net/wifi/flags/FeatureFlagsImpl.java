package android.net.wifi.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.net.wifi.flags.FeatureFlags
    public boolean getDeviceCrossAkmRoamingSupport() {
        return true;
    }

    @Override // android.net.wifi.flags.FeatureFlags
    public boolean legacyKeystoreToWifiBlobstoreMigration() {
        return false;
    }

    @Override // android.net.wifi.flags.FeatureFlags
    public boolean networkProviderBatteryChargingStatus() {
        return true;
    }
}
