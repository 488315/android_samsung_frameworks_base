package android.server.app;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.server.app.FeatureFlags
    public boolean disableGameModeWhenAppTop() {
        return true;
    }

    @Override // android.server.app.FeatureFlags
    public boolean gameDefaultFrameRate() {
        return true;
    }
}
