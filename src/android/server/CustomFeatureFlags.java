package android.server;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ALLOW_NETWORK_TIME_UPDATE_SERVICE, Flags.FLAG_ALLOW_REMOVING_VPN_SERVICE, Flags.FLAG_EARLY_SYSTEM_CONFIG_INIT, Flags.FLAG_ENABLE_THEME_SERVICE, Flags.FLAG_MIGRATE_WRIST_ORIENTATION, Flags.FLAG_REMOVE_APP_INTEGRITY_MANAGER_SERVICE, Flags.FLAG_REMOVE_GAME_MANAGER_SERVICE_FROM_WEAR, Flags.FLAG_REMOVE_TEXT_SERVICE, Flags.FLAG_REMOVE_WEARABLE_SENSING_SERVICE_FROM_WEAR, Flags.FLAG_TELEMETRY_APIS_SERVICE, Flags.FLAG_WEAR_GESTURE_API, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.server.FeatureFlags
    public boolean allowNetworkTimeUpdateService() {
        return getValue(Flags.FLAG_ALLOW_NETWORK_TIME_UPDATE_SERVICE, new Predicate() { // from class: android.server.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowNetworkTimeUpdateService();
            }
        });
    }

    @Override // android.server.FeatureFlags
    public boolean allowRemovingVpnService() {
        return getValue(Flags.FLAG_ALLOW_REMOVING_VPN_SERVICE, new Predicate() { // from class: android.server.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowRemovingVpnService();
            }
        });
    }

    @Override // android.server.FeatureFlags
    public boolean earlySystemConfigInit() {
        return getValue(Flags.FLAG_EARLY_SYSTEM_CONFIG_INIT, new Predicate() { // from class: android.server.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).earlySystemConfigInit();
            }
        });
    }

    @Override // android.server.FeatureFlags
    public boolean enableThemeService() {
        return getValue(Flags.FLAG_ENABLE_THEME_SERVICE, new Predicate() { // from class: android.server.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableThemeService();
            }
        });
    }

    @Override // android.server.FeatureFlags
    public boolean migrateWristOrientation() {
        return getValue(Flags.FLAG_MIGRATE_WRIST_ORIENTATION, new Predicate() { // from class: android.server.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).migrateWristOrientation();
            }
        });
    }

    @Override // android.server.FeatureFlags
    public boolean removeAppIntegrityManagerService() {
        return getValue(Flags.FLAG_REMOVE_APP_INTEGRITY_MANAGER_SERVICE, new Predicate() { // from class: android.server.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeAppIntegrityManagerService();
            }
        });
    }

    @Override // android.server.FeatureFlags
    public boolean removeGameManagerServiceFromWear() {
        return getValue(Flags.FLAG_REMOVE_GAME_MANAGER_SERVICE_FROM_WEAR, new Predicate() { // from class: android.server.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeGameManagerServiceFromWear();
            }
        });
    }

    @Override // android.server.FeatureFlags
    public boolean removeTextService() {
        return getValue(Flags.FLAG_REMOVE_TEXT_SERVICE, new Predicate() { // from class: android.server.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeTextService();
            }
        });
    }

    @Override // android.server.FeatureFlags
    public boolean removeWearableSensingServiceFromWear() {
        return getValue(Flags.FLAG_REMOVE_WEARABLE_SENSING_SERVICE_FROM_WEAR, new Predicate() { // from class: android.server.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeWearableSensingServiceFromWear();
            }
        });
    }

    @Override // android.server.FeatureFlags
    public boolean telemetryApisService() {
        return getValue(Flags.FLAG_TELEMETRY_APIS_SERVICE, new Predicate() { // from class: android.server.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).telemetryApisService();
            }
        });
    }

    @Override // android.server.FeatureFlags
    public boolean wearGestureApi() {
        return getValue(Flags.FLAG_WEAR_GESTURE_API, new Predicate() { // from class: android.server.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).wearGestureApi();
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
        return Arrays.asList(Flags.FLAG_ALLOW_NETWORK_TIME_UPDATE_SERVICE, Flags.FLAG_ALLOW_REMOVING_VPN_SERVICE, Flags.FLAG_EARLY_SYSTEM_CONFIG_INIT, Flags.FLAG_ENABLE_THEME_SERVICE, Flags.FLAG_MIGRATE_WRIST_ORIENTATION, Flags.FLAG_REMOVE_APP_INTEGRITY_MANAGER_SERVICE, Flags.FLAG_REMOVE_GAME_MANAGER_SERVICE_FROM_WEAR, Flags.FLAG_REMOVE_TEXT_SERVICE, Flags.FLAG_REMOVE_WEARABLE_SENSING_SERVICE_FROM_WEAR, Flags.FLAG_TELEMETRY_APIS_SERVICE, Flags.FLAG_WEAR_GESTURE_API);
    }
}
