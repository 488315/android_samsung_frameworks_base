package android.location.flags;

public interface FeatureFlags {
    boolean enableLocationBypass();

    boolean fixServiceWatcher();

    boolean geoidHeightsViaAltitudeHal();

    boolean gnssApiMeasurementRequestWorkSource();

    boolean gnssApiNavicL1();

    boolean gnssConfigurationFromResource();

    boolean locationBypass();

    boolean locationValidation();

    boolean newGeocoder();

    boolean releaseSuplConnectionOnTimeout();

    boolean replaceFutureElapsedRealtimeJni();

    boolean subscriptionsChangedListenerThread();
}
