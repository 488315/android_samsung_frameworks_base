package android.webkit;

/* loaded from: classes4.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.webkit.FeatureFlags
    public boolean deprecateStartSafeBrowsing() {
        return false;
    }

    @Override // android.webkit.FeatureFlags
    public boolean fileSystemAccess() {
        return false;
    }

    @Override // android.webkit.FeatureFlags
    public boolean mainlineApis() {
        return false;
    }

    @Override // android.webkit.FeatureFlags
    public boolean updateServiceIpcWrapper() {
        return false;
    }

    @Override // android.webkit.FeatureFlags
    public boolean updateServiceV2() {
        return true;
    }

    @Override // android.webkit.FeatureFlags
    public boolean useBEntryPoint() {
        return false;
    }

    @Override // android.webkit.FeatureFlags
    public boolean userAgentReduction() {
        return false;
    }
}
