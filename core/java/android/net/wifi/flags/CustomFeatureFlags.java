package android.net.wifi.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_GET_DEVICE_CROSS_AKM_ROAMING_SUPPORT,
                            Flags.FLAG_LEGACY_KEYSTORE_TO_WIFI_BLOBSTORE_MIGRATION,
                            Flags.FLAG_NETWORK_PROVIDER_BATTERY_CHARGING_STATUS,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.net.wifi.flags.FeatureFlags
    public boolean getDeviceCrossAkmRoamingSupport() {
        return getValue(
                Flags.FLAG_GET_DEVICE_CROSS_AKM_ROAMING_SUPPORT,
                new Predicate() { // from class:
                                  // android.net.wifi.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).getDeviceCrossAkmRoamingSupport();
                    }
                });
    }

    @Override // android.net.wifi.flags.FeatureFlags
    public boolean legacyKeystoreToWifiBlobstoreMigration() {
        return getValue(
                Flags.FLAG_LEGACY_KEYSTORE_TO_WIFI_BLOBSTORE_MIGRATION,
                new Predicate() { // from class:
                                  // android.net.wifi.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).legacyKeystoreToWifiBlobstoreMigration();
                    }
                });
    }

    @Override // android.net.wifi.flags.FeatureFlags
    public boolean networkProviderBatteryChargingStatus() {
        return getValue(
                Flags.FLAG_NETWORK_PROVIDER_BATTERY_CHARGING_STATUS,
                new Predicate() { // from class:
                                  // android.net.wifi.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).networkProviderBatteryChargingStatus();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_GET_DEVICE_CROSS_AKM_ROAMING_SUPPORT,
                Flags.FLAG_LEGACY_KEYSTORE_TO_WIFI_BLOBSTORE_MIGRATION,
                Flags.FLAG_NETWORK_PROVIDER_BATTERY_CHARGING_STATUS);
    }
}
