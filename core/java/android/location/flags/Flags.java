package android.location.flags;

/* loaded from: classes2.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_LOCATION_BYPASS = "android.location.flags.enable_location_bypass";
    public static final String FLAG_FIX_SERVICE_WATCHER = "android.location.flags.fix_service_watcher";
    public static final String FLAG_GEOID_HEIGHTS_VIA_ALTITUDE_HAL = "android.location.flags.geoid_heights_via_altitude_hal";
    public static final String FLAG_GNSS_API_MEASUREMENT_REQUEST_WORK_SOURCE = "android.location.flags.gnss_api_measurement_request_work_source";
    public static final String FLAG_GNSS_API_NAVIC_L1 = "android.location.flags.gnss_api_navic_l1";
    public static final String FLAG_GNSS_CONFIGURATION_FROM_RESOURCE = "android.location.flags.gnss_configuration_from_resource";
    public static final String FLAG_LOCATION_BYPASS = "android.location.flags.location_bypass";
    public static final String FLAG_LOCATION_VALIDATION = "android.location.flags.location_validation";
    public static final String FLAG_NEW_GEOCODER = "android.location.flags.new_geocoder";
    public static final String FLAG_RELEASE_SUPL_CONNECTION_ON_TIMEOUT = "android.location.flags.release_supl_connection_on_timeout";
    public static final String FLAG_REPLACE_FUTURE_ELAPSED_REALTIME_JNI = "android.location.flags.replace_future_elapsed_realtime_jni";
    public static final String FLAG_SUBSCRIPTIONS_CHANGED_LISTENER_THREAD = "android.location.flags.subscriptions_changed_listener_thread";

    public static boolean enableLocationBypass() {
        return FEATURE_FLAGS.enableLocationBypass();
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

    public static boolean gnssConfigurationFromResource() {
        return FEATURE_FLAGS.gnssConfigurationFromResource();
    }

    public static boolean locationBypass() {
        return FEATURE_FLAGS.locationBypass();
    }

    public static boolean locationValidation() {
        return FEATURE_FLAGS.locationValidation();
    }

    public static boolean newGeocoder() {
        return FEATURE_FLAGS.newGeocoder();
    }

    public static boolean releaseSuplConnectionOnTimeout() {
        return FEATURE_FLAGS.releaseSuplConnectionOnTimeout();
    }

    public static boolean replaceFutureElapsedRealtimeJni() {
        return FEATURE_FLAGS.replaceFutureElapsedRealtimeJni();
    }

    public static boolean subscriptionsChangedListenerThread() {
        return FEATURE_FLAGS.subscriptionsChangedListenerThread();
    }
}
