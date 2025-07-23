package android.media.soundtrigger;

/* loaded from: classes3.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_DETECTION_SERVICE_PAUSED_RESUMED_API = "android.media.soundtrigger.detection_service_paused_resumed_api";
    public static final String FLAG_GENERIC_MODEL_API = "android.media.soundtrigger.generic_model_api";
    public static final String FLAG_MANAGER_API = "android.media.soundtrigger.manager_api";

    public static boolean detectionServicePausedResumedApi() {
        return FEATURE_FLAGS.detectionServicePausedResumedApi();
    }

    public static boolean genericModelApi() {
        return FEATURE_FLAGS.genericModelApi();
    }

    public static boolean managerApi() {
        return FEATURE_FLAGS.managerApi();
    }
}
