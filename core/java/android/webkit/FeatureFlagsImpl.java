package android.webkit;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.webkit.FeatureFlags
    public boolean updateServiceIpcWrapper() {
        return false;
    }

    @Override // android.webkit.FeatureFlags
    public boolean updateServiceV2() {
        return true;
    }
}
