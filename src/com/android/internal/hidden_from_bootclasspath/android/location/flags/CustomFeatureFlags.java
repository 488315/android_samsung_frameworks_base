package com.android.internal.hidden_from_bootclasspath.android.location.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_DENSITY_BASED_COARSE_LOCATIONS, Flags.FLAG_DEPRECATE_PROVIDER_REQUEST_APIS, Flags.FLAG_DISABLE_STATIONARY_THROTTLING, Flags.FLAG_ENABLE_LOCATION_BYPASS, Flags.FLAG_ENABLE_NI_SUPL_MESSAGE_INJECTION_BY_CARRIER_CONFIG, Flags.FLAG_ENABLE_NI_SUPL_MESSAGE_INJECTION_BY_CARRIER_CONFIG_BUGFIX, Flags.FLAG_FIX_IS_IN_EMERGENCY_ANR, Flags.FLAG_FIX_SERVICE_WATCHER, Flags.FLAG_GEOID_HEIGHTS_VIA_ALTITUDE_HAL, Flags.FLAG_GNSS_API_MEASUREMENT_REQUEST_WORK_SOURCE, Flags.FLAG_GNSS_API_NAVIC_L1, Flags.FLAG_GNSS_ASSISTANCE_INTERFACE, Flags.FLAG_GNSS_ASSISTANCE_INTERFACE_JNI, Flags.FLAG_GNSS_CONFIGURATION_FROM_RESOURCE, Flags.FLAG_GNSS_LOCATION_PROVIDER_OVERLAY_2025_DEVICES, Flags.FLAG_KEEP_GNSS_STATIONARY_THROTTLING, Flags.FLAG_LIMIT_FUSED_GPS, Flags.FLAG_LOCATION_BYPASS, Flags.FLAG_LOCATION_VALIDATION, Flags.FLAG_MISSING_ATTRIBUTION_TAGS_IN_OVERLAY, Flags.FLAG_NEW_GEOCODER, Flags.FLAG_POPULATION_DENSITY_PROVIDER, Flags.FLAG_RELEASE_SUPL_CONNECTION_ON_TIMEOUT, Flags.FLAG_REPLACE_FUTURE_ELAPSED_REALTIME_JNI, Flags.FLAG_SERVICE_WATCHER_UNSTABLE_FALLBACK, Flags.FLAG_SUBSCRIPTIONS_CHANGED_LISTENER_THREAD, Flags.FLAG_UPDATE_MIN_LOCATION_REQUEST_INTERVAL, Flags.FLAG_USE_LEGACY_NTP_TIME, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean densityBasedCoarseLocations() {
        return getValue(Flags.FLAG_DENSITY_BASED_COARSE_LOCATIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).densityBasedCoarseLocations();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean deprecateProviderRequestApis() {
        return getValue(Flags.FLAG_DEPRECATE_PROVIDER_REQUEST_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deprecateProviderRequestApis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean disableStationaryThrottling() {
        return getValue(Flags.FLAG_DISABLE_STATIONARY_THROTTLING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableStationaryThrottling();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean enableLocationBypass() {
        return getValue(Flags.FLAG_ENABLE_LOCATION_BYPASS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableLocationBypass();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean enableNiSuplMessageInjectionByCarrierConfig() {
        return getValue(Flags.FLAG_ENABLE_NI_SUPL_MESSAGE_INJECTION_BY_CARRIER_CONFIG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableNiSuplMessageInjectionByCarrierConfig();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean enableNiSuplMessageInjectionByCarrierConfigBugfix() {
        return getValue(Flags.FLAG_ENABLE_NI_SUPL_MESSAGE_INJECTION_BY_CARRIER_CONFIG_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableNiSuplMessageInjectionByCarrierConfigBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean fixIsInEmergencyAnr() {
        return getValue(Flags.FLAG_FIX_IS_IN_EMERGENCY_ANR, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixIsInEmergencyAnr();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean fixServiceWatcher() {
        return getValue(Flags.FLAG_FIX_SERVICE_WATCHER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixServiceWatcher();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean geoidHeightsViaAltitudeHal() {
        return getValue(Flags.FLAG_GEOID_HEIGHTS_VIA_ALTITUDE_HAL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).geoidHeightsViaAltitudeHal();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean gnssApiMeasurementRequestWorkSource() {
        return getValue(Flags.FLAG_GNSS_API_MEASUREMENT_REQUEST_WORK_SOURCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).gnssApiMeasurementRequestWorkSource();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean gnssApiNavicL1() {
        return getValue(Flags.FLAG_GNSS_API_NAVIC_L1, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).gnssApiNavicL1();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean gnssAssistanceInterface() {
        return getValue(Flags.FLAG_GNSS_ASSISTANCE_INTERFACE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).gnssAssistanceInterface();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean gnssAssistanceInterfaceJni() {
        return getValue(Flags.FLAG_GNSS_ASSISTANCE_INTERFACE_JNI, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).gnssAssistanceInterfaceJni();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean gnssConfigurationFromResource() {
        return getValue(Flags.FLAG_GNSS_CONFIGURATION_FROM_RESOURCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).gnssConfigurationFromResource();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean gnssLocationProviderOverlay2025Devices() {
        return getValue(Flags.FLAG_GNSS_LOCATION_PROVIDER_OVERLAY_2025_DEVICES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).gnssLocationProviderOverlay2025Devices();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean keepGnssStationaryThrottling() {
        return getValue(Flags.FLAG_KEEP_GNSS_STATIONARY_THROTTLING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keepGnssStationaryThrottling();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean limitFusedGps() {
        return getValue(Flags.FLAG_LIMIT_FUSED_GPS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).limitFusedGps();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean locationBypass() {
        return getValue(Flags.FLAG_LOCATION_BYPASS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).locationBypass();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean locationValidation() {
        return getValue(Flags.FLAG_LOCATION_VALIDATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).locationValidation();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean missingAttributionTagsInOverlay() {
        return getValue(Flags.FLAG_MISSING_ATTRIBUTION_TAGS_IN_OVERLAY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).missingAttributionTagsInOverlay();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean newGeocoder() {
        return getValue(Flags.FLAG_NEW_GEOCODER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).newGeocoder();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean populationDensityProvider() {
        return getValue(Flags.FLAG_POPULATION_DENSITY_PROVIDER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).populationDensityProvider();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean releaseSuplConnectionOnTimeout() {
        return getValue(Flags.FLAG_RELEASE_SUPL_CONNECTION_ON_TIMEOUT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).releaseSuplConnectionOnTimeout();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean replaceFutureElapsedRealtimeJni() {
        return getValue(Flags.FLAG_REPLACE_FUTURE_ELAPSED_REALTIME_JNI, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).replaceFutureElapsedRealtimeJni();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean serviceWatcherUnstableFallback() {
        return getValue(Flags.FLAG_SERVICE_WATCHER_UNSTABLE_FALLBACK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).serviceWatcherUnstableFallback();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean subscriptionsChangedListenerThread() {
        return getValue(Flags.FLAG_SUBSCRIPTIONS_CHANGED_LISTENER_THREAD, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).subscriptionsChangedListenerThread();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean updateMinLocationRequestInterval() {
        return getValue(Flags.FLAG_UPDATE_MIN_LOCATION_REQUEST_INTERVAL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).updateMinLocationRequestInterval();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean useLegacyNtpTime() {
        return getValue(Flags.FLAG_USE_LEGACY_NTP_TIME, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.location.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useLegacyNtpTime();
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
        return Arrays.asList(Flags.FLAG_DENSITY_BASED_COARSE_LOCATIONS, Flags.FLAG_DEPRECATE_PROVIDER_REQUEST_APIS, Flags.FLAG_DISABLE_STATIONARY_THROTTLING, Flags.FLAG_ENABLE_LOCATION_BYPASS, Flags.FLAG_ENABLE_NI_SUPL_MESSAGE_INJECTION_BY_CARRIER_CONFIG, Flags.FLAG_ENABLE_NI_SUPL_MESSAGE_INJECTION_BY_CARRIER_CONFIG_BUGFIX, Flags.FLAG_FIX_IS_IN_EMERGENCY_ANR, Flags.FLAG_FIX_SERVICE_WATCHER, Flags.FLAG_GEOID_HEIGHTS_VIA_ALTITUDE_HAL, Flags.FLAG_GNSS_API_MEASUREMENT_REQUEST_WORK_SOURCE, Flags.FLAG_GNSS_API_NAVIC_L1, Flags.FLAG_GNSS_ASSISTANCE_INTERFACE, Flags.FLAG_GNSS_ASSISTANCE_INTERFACE_JNI, Flags.FLAG_GNSS_CONFIGURATION_FROM_RESOURCE, Flags.FLAG_GNSS_LOCATION_PROVIDER_OVERLAY_2025_DEVICES, Flags.FLAG_KEEP_GNSS_STATIONARY_THROTTLING, Flags.FLAG_LIMIT_FUSED_GPS, Flags.FLAG_LOCATION_BYPASS, Flags.FLAG_LOCATION_VALIDATION, Flags.FLAG_MISSING_ATTRIBUTION_TAGS_IN_OVERLAY, Flags.FLAG_NEW_GEOCODER, Flags.FLAG_POPULATION_DENSITY_PROVIDER, Flags.FLAG_RELEASE_SUPL_CONNECTION_ON_TIMEOUT, Flags.FLAG_REPLACE_FUTURE_ELAPSED_REALTIME_JNI, Flags.FLAG_SERVICE_WATCHER_UNSTABLE_FALLBACK, Flags.FLAG_SUBSCRIPTIONS_CHANGED_LISTENER_THREAD, Flags.FLAG_UPDATE_MIN_LOCATION_REQUEST_INTERVAL, Flags.FLAG_USE_LEGACY_NTP_TIME);
    }
}
