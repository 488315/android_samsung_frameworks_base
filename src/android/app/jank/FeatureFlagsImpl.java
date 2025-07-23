package android.app.jank;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.app.jank.FeatureFlags
    public boolean detailedAppJankMetricsApi() {
        return true;
    }

    @Override // android.app.jank.FeatureFlags
    public boolean detailedAppJankMetricsLoggingEnabled() {
        return false;
    }

    @Override // android.app.jank.FeatureFlags
    public boolean viewrootChoreographer() {
        return true;
    }
}
