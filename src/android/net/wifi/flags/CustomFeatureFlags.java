package android.net.wifi.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_GET_DEVICE_CROSS_AKM_ROAMING_SUPPORT, Flags.FLAG_HOTSPOT_NETWORK_CONNECTING_STATE_FOR_DETAILS_PAGE, Flags.FLAG_HOTSPOT_NETWORK_UNKNOWN_STATUS_RESETS_CONNECTING_STATE, Flags.FLAG_LEGACY_KEYSTORE_TO_WIFI_BLOBSTORE_MIGRATION_READ_ONLY, Flags.FLAG_NETWORK_PROVIDER_BATTERY_CHARGING_STATUS, Flags.FLAG_USD, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.net.wifi.flags.FeatureFlags
    public boolean getDeviceCrossAkmRoamingSupport() {
        return getValue(Flags.FLAG_GET_DEVICE_CROSS_AKM_ROAMING_SUPPORT, new Predicate() { // from class: android.net.wifi.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).getDeviceCrossAkmRoamingSupport();
            }
        });
    }

    @Override // android.net.wifi.flags.FeatureFlags
    public boolean hotspotNetworkConnectingStateForDetailsPage() {
        return getValue(Flags.FLAG_HOTSPOT_NETWORK_CONNECTING_STATE_FOR_DETAILS_PAGE, new Predicate() { // from class: android.net.wifi.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hotspotNetworkConnectingStateForDetailsPage();
            }
        });
    }

    @Override // android.net.wifi.flags.FeatureFlags
    public boolean hotspotNetworkUnknownStatusResetsConnectingState() {
        return getValue(Flags.FLAG_HOTSPOT_NETWORK_UNKNOWN_STATUS_RESETS_CONNECTING_STATE, new Predicate() { // from class: android.net.wifi.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hotspotNetworkUnknownStatusResetsConnectingState();
            }
        });
    }

    @Override // android.net.wifi.flags.FeatureFlags
    public boolean legacyKeystoreToWifiBlobstoreMigrationReadOnly() {
        return getValue(Flags.FLAG_LEGACY_KEYSTORE_TO_WIFI_BLOBSTORE_MIGRATION_READ_ONLY, new Predicate() { // from class: android.net.wifi.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).legacyKeystoreToWifiBlobstoreMigrationReadOnly();
            }
        });
    }

    @Override // android.net.wifi.flags.FeatureFlags
    public boolean networkProviderBatteryChargingStatus() {
        return getValue(Flags.FLAG_NETWORK_PROVIDER_BATTERY_CHARGING_STATUS, new Predicate() { // from class: android.net.wifi.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).networkProviderBatteryChargingStatus();
            }
        });
    }

    @Override // android.net.wifi.flags.FeatureFlags
    public boolean usd() {
        return getValue(Flags.FLAG_USD, new Predicate() { // from class: android.net.wifi.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).usd();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_GET_DEVICE_CROSS_AKM_ROAMING_SUPPORT, Flags.FLAG_HOTSPOT_NETWORK_CONNECTING_STATE_FOR_DETAILS_PAGE, Flags.FLAG_HOTSPOT_NETWORK_UNKNOWN_STATUS_RESETS_CONNECTING_STATE, Flags.FLAG_LEGACY_KEYSTORE_TO_WIFI_BLOBSTORE_MIGRATION_READ_ONLY, Flags.FLAG_NETWORK_PROVIDER_BATTERY_CHARGING_STATUS, Flags.FLAG_USD);
    }
}
