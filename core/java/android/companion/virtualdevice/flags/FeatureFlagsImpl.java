package android.companion.virtualdevice.flags;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean cameraDeviceAwareness() {
        return true;
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean deviceAwareDrm() {
        return true;
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean deviceAwareRecordAudioPermission() {
        return true;
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean intentInterceptionActionMatchingFix() {
        return true;
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean metricsCollection() {
        return true;
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean virtualCameraServiceDiscovery() {
        return true;
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean virtualDisplayMultiWindowModeSupport() {
        return true;
    }
}
