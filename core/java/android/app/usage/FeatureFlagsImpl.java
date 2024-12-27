package android.app.usage;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.app.usage.FeatureFlags
    public boolean disableIdleCheck() {
        return false;
    }

    @Override // android.app.usage.FeatureFlags
    public boolean filterBasedEventQueryApi() {
        return true;
    }

    @Override // android.app.usage.FeatureFlags
    public boolean getAppBytesByDataTypeApi() {
        return true;
    }

    @Override // android.app.usage.FeatureFlags
    public boolean reportUsageStatsPermission() {
        return true;
    }

    @Override // android.app.usage.FeatureFlags
    public boolean useDedicatedHandlerThread() {
        return true;
    }

    @Override // android.app.usage.FeatureFlags
    public boolean useParceledList() {
        return true;
    }

    @Override // android.app.usage.FeatureFlags
    public boolean userInteractionTypeApi() {
        return true;
    }
}
