package com.android.internal.hidden_from_bootclasspath.android.location.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean densityBasedCoarseLocations() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean deprecateProviderRequestApis() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean disableStationaryThrottling() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean enableLocationBypass() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean enableNiSuplMessageInjectionByCarrierConfig() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean enableNiSuplMessageInjectionByCarrierConfigBugfix() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean fixIsInEmergencyAnr() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean fixServiceWatcher() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean geoidHeightsViaAltitudeHal() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean gnssApiMeasurementRequestWorkSource() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean gnssApiNavicL1() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean gnssAssistanceInterface() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean gnssAssistanceInterfaceJni() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean gnssConfigurationFromResource() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean gnssLocationProviderOverlay2025Devices() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean keepGnssStationaryThrottling() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean limitFusedGps() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean locationBypass() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean locationValidation() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean missingAttributionTagsInOverlay() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean newGeocoder() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean populationDensityProvider() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean releaseSuplConnectionOnTimeout() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean replaceFutureElapsedRealtimeJni() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean serviceWatcherUnstableFallback() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean subscriptionsChangedListenerThread() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean updateIsInEmergencyBeforeOnRegister() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean updateMinLocationRequestInterval() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.location.flags.FeatureFlags
    public boolean useLegacyNtpTime() {
        return true;
    }
}
