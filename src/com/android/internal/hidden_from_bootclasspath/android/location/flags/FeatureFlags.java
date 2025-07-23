package com.android.internal.hidden_from_bootclasspath.android.location.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean densityBasedCoarseLocations();

    boolean deprecateProviderRequestApis();

    boolean disableStationaryThrottling();

    boolean enableLocationBypass();

    boolean enableNiSuplMessageInjectionByCarrierConfig();

    boolean enableNiSuplMessageInjectionByCarrierConfigBugfix();

    boolean fixIsInEmergencyAnr();

    boolean fixServiceWatcher();

    boolean geoidHeightsViaAltitudeHal();

    boolean gnssApiMeasurementRequestWorkSource();

    boolean gnssApiNavicL1();

    boolean gnssAssistanceInterface();

    boolean gnssAssistanceInterfaceJni();

    boolean gnssConfigurationFromResource();

    boolean gnssLocationProviderOverlay2025Devices();

    boolean keepGnssStationaryThrottling();

    boolean limitFusedGps();

    boolean locationBypass();

    boolean locationValidation();

    boolean missingAttributionTagsInOverlay();

    boolean newGeocoder();

    boolean populationDensityProvider();

    boolean releaseSuplConnectionOnTimeout();

    boolean replaceFutureElapsedRealtimeJni();

    boolean serviceWatcherUnstableFallback();

    boolean subscriptionsChangedListenerThread();

    boolean updateMinLocationRequestInterval();

    boolean useLegacyNtpTime();
}
