package android.companion.virtualdevice.flags;

/* loaded from: classes.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_CAMERA_DEVICE_AWARENESS = "android.companion.virtualdevice.flags.camera_device_awareness";
    public static final String FLAG_DEVICE_AWARE_DRM = "android.companion.virtualdevice.flags.device_aware_drm";
    public static final String FLAG_DEVICE_AWARE_RECORD_AUDIO_PERMISSION = "android.companion.virtualdevice.flags.device_aware_record_audio_permission";
    public static final String FLAG_INTENT_INTERCEPTION_ACTION_MATCHING_FIX = "android.companion.virtualdevice.flags.intent_interception_action_matching_fix";
    public static final String FLAG_METRICS_COLLECTION = "android.companion.virtualdevice.flags.metrics_collection";
    public static final String FLAG_VIRTUAL_CAMERA_SERVICE_DISCOVERY = "android.companion.virtualdevice.flags.virtual_camera_service_discovery";
    public static final String FLAG_VIRTUAL_DISPLAY_MULTI_WINDOW_MODE_SUPPORT = "android.companion.virtualdevice.flags.virtual_display_multi_window_mode_support";

    public static boolean cameraDeviceAwareness() {
        return FEATURE_FLAGS.cameraDeviceAwareness();
    }

    public static boolean deviceAwareDrm() {
        return FEATURE_FLAGS.deviceAwareDrm();
    }

    public static boolean deviceAwareRecordAudioPermission() {
        return FEATURE_FLAGS.deviceAwareRecordAudioPermission();
    }

    public static boolean intentInterceptionActionMatchingFix() {
        return FEATURE_FLAGS.intentInterceptionActionMatchingFix();
    }

    public static boolean metricsCollection() {
        return FEATURE_FLAGS.metricsCollection();
    }

    public static boolean virtualCameraServiceDiscovery() {
        return FEATURE_FLAGS.virtualCameraServiceDiscovery();
    }

    public static boolean virtualDisplayMultiWindowModeSupport() {
        return FEATURE_FLAGS.virtualDisplayMultiWindowModeSupport();
    }
}
