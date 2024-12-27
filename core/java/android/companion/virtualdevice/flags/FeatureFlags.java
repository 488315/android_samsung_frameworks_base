package android.companion.virtualdevice.flags;

public interface FeatureFlags {
    boolean cameraDeviceAwareness();

    boolean deviceAwareDrm();

    boolean deviceAwareRecordAudioPermission();

    boolean intentInterceptionActionMatchingFix();

    boolean metricsCollection();

    boolean virtualCameraServiceDiscovery();

    boolean virtualDisplayMultiWindowModeSupport();
}
