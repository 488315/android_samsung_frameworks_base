package android.location.flags;

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
                            Flags.FLAG_ENABLE_LOCATION_BYPASS,
                            Flags.FLAG_FIX_SERVICE_WATCHER,
                            Flags.FLAG_GEOID_HEIGHTS_VIA_ALTITUDE_HAL,
                            Flags.FLAG_GNSS_API_MEASUREMENT_REQUEST_WORK_SOURCE,
                            Flags.FLAG_GNSS_API_NAVIC_L1,
                            Flags.FLAG_GNSS_CONFIGURATION_FROM_RESOURCE,
                            Flags.FLAG_LOCATION_BYPASS,
                            Flags.FLAG_LOCATION_VALIDATION,
                            Flags.FLAG_NEW_GEOCODER,
                            Flags.FLAG_RELEASE_SUPL_CONNECTION_ON_TIMEOUT,
                            Flags.FLAG_REPLACE_FUTURE_ELAPSED_REALTIME_JNI,
                            Flags.FLAG_SUBSCRIPTIONS_CHANGED_LISTENER_THREAD,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean enableLocationBypass() {
        return getValue(
                Flags.FLAG_ENABLE_LOCATION_BYPASS,
                new Predicate() { // from class:
                    // android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableLocationBypass();
                    }
                });
    }

    @Override // android.location.flags.FeatureFlags
    public boolean fixServiceWatcher() {
        return getValue(
                Flags.FLAG_FIX_SERVICE_WATCHER,
                new Predicate() { // from class:
                    // android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).fixServiceWatcher();
                    }
                });
    }

    @Override // android.location.flags.FeatureFlags
    public boolean geoidHeightsViaAltitudeHal() {
        return getValue(
                Flags.FLAG_GEOID_HEIGHTS_VIA_ALTITUDE_HAL,
                new Predicate() { // from class:
                    // android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).geoidHeightsViaAltitudeHal();
                    }
                });
    }

    @Override // android.location.flags.FeatureFlags
    public boolean gnssApiMeasurementRequestWorkSource() {
        return getValue(
                Flags.FLAG_GNSS_API_MEASUREMENT_REQUEST_WORK_SOURCE,
                new Predicate() { // from class:
                    // android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).gnssApiMeasurementRequestWorkSource();
                    }
                });
    }

    @Override // android.location.flags.FeatureFlags
    public boolean gnssApiNavicL1() {
        return getValue(
                Flags.FLAG_GNSS_API_NAVIC_L1,
                new Predicate() { // from class:
                    // android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).gnssApiNavicL1();
                    }
                });
    }

    @Override // android.location.flags.FeatureFlags
    public boolean gnssConfigurationFromResource() {
        return getValue(
                Flags.FLAG_GNSS_CONFIGURATION_FROM_RESOURCE,
                new Predicate() { // from class:
                    // android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).gnssConfigurationFromResource();
                    }
                });
    }

    @Override // android.location.flags.FeatureFlags
    public boolean locationBypass() {
        return getValue(
                Flags.FLAG_LOCATION_BYPASS,
                new Predicate() { // from class:
                    // android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).locationBypass();
                    }
                });
    }

    @Override // android.location.flags.FeatureFlags
    public boolean locationValidation() {
        return getValue(
                Flags.FLAG_LOCATION_VALIDATION,
                new Predicate() { // from class:
                    // android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).locationValidation();
                    }
                });
    }

    @Override // android.location.flags.FeatureFlags
    public boolean newGeocoder() {
        return getValue(
                Flags.FLAG_NEW_GEOCODER,
                new Predicate() { // from class:
                    // android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).newGeocoder();
                    }
                });
    }

    @Override // android.location.flags.FeatureFlags
    public boolean releaseSuplConnectionOnTimeout() {
        return getValue(
                Flags.FLAG_RELEASE_SUPL_CONNECTION_ON_TIMEOUT,
                new Predicate() { // from class:
                    // android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).releaseSuplConnectionOnTimeout();
                    }
                });
    }

    @Override // android.location.flags.FeatureFlags
    public boolean replaceFutureElapsedRealtimeJni() {
        return getValue(
                Flags.FLAG_REPLACE_FUTURE_ELAPSED_REALTIME_JNI,
                new Predicate() { // from class:
                    // android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).replaceFutureElapsedRealtimeJni();
                    }
                });
    }

    @Override // android.location.flags.FeatureFlags
    public boolean subscriptionsChangedListenerThread() {
        return getValue(
                Flags.FLAG_SUBSCRIPTIONS_CHANGED_LISTENER_THREAD,
                new Predicate() { // from class:
                    // android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).subscriptionsChangedListenerThread();
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
                Flags.FLAG_ENABLE_LOCATION_BYPASS,
                Flags.FLAG_FIX_SERVICE_WATCHER,
                Flags.FLAG_GEOID_HEIGHTS_VIA_ALTITUDE_HAL,
                Flags.FLAG_GNSS_API_MEASUREMENT_REQUEST_WORK_SOURCE,
                Flags.FLAG_GNSS_API_NAVIC_L1,
                Flags.FLAG_GNSS_CONFIGURATION_FROM_RESOURCE,
                Flags.FLAG_LOCATION_BYPASS,
                Flags.FLAG_LOCATION_VALIDATION,
                Flags.FLAG_NEW_GEOCODER,
                Flags.FLAG_RELEASE_SUPL_CONNECTION_ON_TIMEOUT,
                Flags.FLAG_REPLACE_FUTURE_ELAPSED_REALTIME_JNI,
                Flags.FLAG_SUBSCRIPTIONS_CHANGED_LISTENER_THREAD);
    }
}
