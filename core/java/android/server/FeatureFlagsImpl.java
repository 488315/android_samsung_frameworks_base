package android.server;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.server.FeatureFlags
    public boolean removeTextService() {
        return false;
    }

    @Override // android.server.FeatureFlags
    public boolean telemetryApisService() {
        return true;
    }
}
