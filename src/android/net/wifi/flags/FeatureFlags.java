package android.net.wifi.flags;

/* loaded from: classes3.dex */
public interface FeatureFlags {
    boolean getDeviceCrossAkmRoamingSupport();

    boolean hotspotNetworkConnectingStateForDetailsPage();

    boolean hotspotNetworkUnknownStatusResetsConnectingState();

    boolean legacyKeystoreToWifiBlobstoreMigrationReadOnly();

    boolean networkProviderBatteryChargingStatus();

    boolean usd();
}
