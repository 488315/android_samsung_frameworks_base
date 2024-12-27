package android.location.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.location.flags.FeatureFlags
    public boolean enableLocationBypass() {
        return false;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean fixServiceWatcher() {
        return true;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean geoidHeightsViaAltitudeHal() {
        return true;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean gnssApiMeasurementRequestWorkSource() {
        return true;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean gnssApiNavicL1() {
        return true;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean gnssConfigurationFromResource() {
        return true;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean locationBypass() {
        return true;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean locationValidation() {
        return true;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean newGeocoder() {
        return true;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean releaseSuplConnectionOnTimeout() {
        return true;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean replaceFutureElapsedRealtimeJni() {
        return true;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean subscriptionsChangedListenerThread() {
        return false;
    }
}
