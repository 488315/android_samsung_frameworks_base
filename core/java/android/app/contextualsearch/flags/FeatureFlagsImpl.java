package android.app.contextualsearch.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean enableService() {
        return true;
    }

    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean enableTokenRefresh() {
        return false;
    }
}
