package com.android.internal.hidden_from_bootclasspath.android.location.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_DENSITY_BASED_COARSE_LOCATIONS = "android.location.flags.density_based_coarse_locations";
    public static final String FLAG_DEPRECATE_PROVIDER_REQUEST_APIS = "android.location.flags.deprecate_provider_request_apis";
    public static final String FLAG_DISABLE_STATIONARY_THROTTLING = "android.location.flags.disable_stationary_throttling";
    public static final String FLAG_ENABLE_LOCATION_BYPASS = "android.location.flags.enable_location_bypass";
    public static final String FLAG_ENABLE_NI_SUPL_MESSAGE_INJECTION_BY_CARRIER_CONFIG = "android.location.flags.enable_ni_supl_message_injection_by_carrier_config";
    public static final String FLAG_ENABLE_NI_SUPL_MESSAGE_INJECTION_BY_CARRIER_CONFIG_BUGFIX = "android.location.flags.enable_ni_supl_message_injection_by_carrier_config_bugfix";
    public static final String FLAG_FIX_IS_IN_EMERGENCY_ANR = "android.location.flags.fix_is_in_emergency_anr";
    public static final String FLAG_FIX_SERVICE_WATCHER = "android.location.flags.fix_service_watcher";
    public static final String FLAG_GEOID_HEIGHTS_VIA_ALTITUDE_HAL = "android.location.flags.geoid_heights_via_altitude_hal";
    public static final String FLAG_GNSS_API_MEASUREMENT_REQUEST_WORK_SOURCE = "android.location.flags.gnss_api_measurement_request_work_source";
    public static final String FLAG_GNSS_API_NAVIC_L1 = "android.location.flags.gnss_api_navic_l1";
    public static final String FLAG_GNSS_ASSISTANCE_INTERFACE = "android.location.flags.gnss_assistance_interface";
    public static final String FLAG_GNSS_ASSISTANCE_INTERFACE_JNI = "android.location.flags.gnss_assistance_interface_jni";
    public static final String FLAG_GNSS_CONFIGURATION_FROM_RESOURCE = "android.location.flags.gnss_configuration_from_resource";
    public static final String FLAG_GNSS_LOCATION_PROVIDER_OVERLAY_2025_DEVICES = "android.location.flags.gnss_location_provider_overlay_2025_devices";
    public static final String FLAG_KEEP_GNSS_STATIONARY_THROTTLING = "android.location.flags.keep_gnss_stationary_throttling";
    public static final String FLAG_LIMIT_FUSED_GPS = "android.location.flags.limit_fused_gps";
    public static final String FLAG_LOCATION_BYPASS = "android.location.flags.location_bypass";
    public static final String FLAG_LOCATION_VALIDATION = "android.location.flags.location_validation";
    public static final String FLAG_MISSING_ATTRIBUTION_TAGS_IN_OVERLAY = "android.location.flags.missing_attribution_tags_in_overlay";
    public static final String FLAG_NEW_GEOCODER = "android.location.flags.new_geocoder";
    public static final String FLAG_POPULATION_DENSITY_PROVIDER = "android.location.flags.population_density_provider";
    public static final String FLAG_RELEASE_SUPL_CONNECTION_ON_TIMEOUT = "android.location.flags.release_supl_connection_on_timeout";
    public static final String FLAG_REPLACE_FUTURE_ELAPSED_REALTIME_JNI = "android.location.flags.replace_future_elapsed_realtime_jni";
    public static final String FLAG_SERVICE_WATCHER_UNSTABLE_FALLBACK = "android.location.flags.service_watcher_unstable_fallback";
    public static final String FLAG_SUBSCRIPTIONS_CHANGED_LISTENER_THREAD = "android.location.flags.subscriptions_changed_listener_thread";
    public static final String FLAG_UPDATE_IS_IN_EMERGENCY_BEFORE_ON_REGISTER = "android.location.flags.update_is_in_emergency_before_on_register";
    public static final String FLAG_UPDATE_MIN_LOCATION_REQUEST_INTERVAL = "android.location.flags.update_min_location_request_interval";
    public static final String FLAG_USE_LEGACY_NTP_TIME = "android.location.flags.use_legacy_ntp_time";

    public static boolean densityBasedCoarseLocations() {
        return FEATURE_FLAGS.densityBasedCoarseLocations();
    }

    public static boolean deprecateProviderRequestApis() {
        return FEATURE_FLAGS.deprecateProviderRequestApis();
    }

    public static boolean disableStationaryThrottling() {
        return FEATURE_FLAGS.disableStationaryThrottling();
    }

    public static boolean enableLocationBypass() {
        return FEATURE_FLAGS.enableLocationBypass();
    }

    public static boolean enableNiSuplMessageInjectionByCarrierConfig() {
        return FEATURE_FLAGS.enableNiSuplMessageInjectionByCarrierConfig();
    }

    public static boolean enableNiSuplMessageInjectionByCarrierConfigBugfix() {
        return FEATURE_FLAGS.enableNiSuplMessageInjectionByCarrierConfigBugfix();
    }

    public static boolean fixIsInEmergencyAnr() {
        return FEATURE_FLAGS.fixIsInEmergencyAnr();
    }

    public static boolean fixServiceWatcher() {
        return FEATURE_FLAGS.fixServiceWatcher();
    }

    public static boolean geoidHeightsViaAltitudeHal() {
        return FEATURE_FLAGS.geoidHeightsViaAltitudeHal();
    }

    public static boolean gnssApiMeasurementRequestWorkSource() {
        return FEATURE_FLAGS.gnssApiMeasurementRequestWorkSource();
    }

    public static boolean gnssApiNavicL1() {
        return FEATURE_FLAGS.gnssApiNavicL1();
    }

    public static boolean gnssAssistanceInterface() {
        return FEATURE_FLAGS.gnssAssistanceInterface();
    }

    public static boolean gnssAssistanceInterfaceJni() {
        return FEATURE_FLAGS.gnssAssistanceInterfaceJni();
    }

    public static boolean gnssConfigurationFromResource() {
        return FEATURE_FLAGS.gnssConfigurationFromResource();
    }

    public static boolean gnssLocationProviderOverlay2025Devices() {
        return FEATURE_FLAGS.gnssLocationProviderOverlay2025Devices();
    }

    public static boolean keepGnssStationaryThrottling() {
        return FEATURE_FLAGS.keepGnssStationaryThrottling();
    }

    public static boolean limitFusedGps() {
        return FEATURE_FLAGS.limitFusedGps();
    }

    public static boolean locationBypass() {
        return FEATURE_FLAGS.locationBypass();
    }

    public static boolean locationValidation() {
        return FEATURE_FLAGS.locationValidation();
    }

    public static boolean missingAttributionTagsInOverlay() {
        return FEATURE_FLAGS.missingAttributionTagsInOverlay();
    }

    public static boolean newGeocoder() {
        return FEATURE_FLAGS.newGeocoder();
    }

    public static boolean populationDensityProvider() {
        return FEATURE_FLAGS.populationDensityProvider();
    }

    public static boolean releaseSuplConnectionOnTimeout() {
        return FEATURE_FLAGS.releaseSuplConnectionOnTimeout();
    }

    public static boolean replaceFutureElapsedRealtimeJni() {
        return FEATURE_FLAGS.replaceFutureElapsedRealtimeJni();
    }

    public static boolean serviceWatcherUnstableFallback() {
        return FEATURE_FLAGS.serviceWatcherUnstableFallback();
    }

    public static boolean subscriptionsChangedListenerThread() {
        return FEATURE_FLAGS.subscriptionsChangedListenerThread();
    }

    public static boolean updateIsInEmergencyBeforeOnRegister() {
        return FEATURE_FLAGS.updateIsInEmergencyBeforeOnRegister();
    }

    public static boolean updateMinLocationRequestInterval() {
        return FEATURE_FLAGS.updateMinLocationRequestInterval();
    }

    public static boolean useLegacyNtpTime() {
        return FEATURE_FLAGS.useLegacyNtpTime();
    }
}
