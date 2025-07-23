package android.server;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.server.FeatureFlags
    public boolean allowNetworkTimeUpdateService() {
        return true;
    }

    @Override // android.server.FeatureFlags
    public boolean allowRemovingVpnService() {
        return true;
    }

    @Override // android.server.FeatureFlags
    public boolean earlySystemConfigInit() {
        return true;
    }

    @Override // android.server.FeatureFlags
    public boolean enableThemeService() {
        return false;
    }

    @Override // android.server.FeatureFlags
    public boolean migrateWristOrientation() {
        return true;
    }

    @Override // android.server.FeatureFlags
    public boolean removeAppIntegrityManagerService() {
        return false;
    }

    @Override // android.server.FeatureFlags
    public boolean removeGameManagerServiceFromWear() {
        return true;
    }

    @Override // android.server.FeatureFlags
    public boolean removeTextService() {
        return true;
    }

    @Override // android.server.FeatureFlags
    public boolean removeWearableSensingServiceFromWear() {
        return true;
    }

    @Override // android.server.FeatureFlags
    public boolean telemetryApisService() {
        return true;
    }

    @Override // android.server.FeatureFlags
    public boolean wearGestureApi() {
        return false;
    }
}
