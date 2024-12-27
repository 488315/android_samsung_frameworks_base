package android.webkit;

/* loaded from: classes4.dex */
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
