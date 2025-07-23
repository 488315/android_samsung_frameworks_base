package android.net.wifi.flags;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.net.wifi.flags.FeatureFlags
    public boolean getDeviceCrossAkmRoamingSupport() {
        return true;
    }

    @Override // android.net.wifi.flags.FeatureFlags
    public boolean hotspotNetworkConnectingStateForDetailsPage() {
        return false;
    }

    @Override // android.net.wifi.flags.FeatureFlags
    public boolean hotspotNetworkUnknownStatusResetsConnectingState() {
        return false;
    }

    @Override // android.net.wifi.flags.FeatureFlags
    public boolean legacyKeystoreToWifiBlobstoreMigrationReadOnly() {
        return true;
    }

    @Override // android.net.wifi.flags.FeatureFlags
    public boolean networkProviderBatteryChargingStatus() {
        return true;
    }

    @Override // android.net.wifi.flags.FeatureFlags
    public boolean usd() {
        return true;
    }
}
