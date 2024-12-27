package android.webkit;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_UPDATE_SERVICE_IPC_WRAPPER =
            "android.webkit.update_service_ipc_wrapper";
    public static final String FLAG_UPDATE_SERVICE_V2 = "android.webkit.update_service_v2";

    public static boolean updateServiceIpcWrapper() {
        return FEATURE_FLAGS.updateServiceIpcWrapper();
    }

    public static boolean updateServiceV2() {
        return FEATURE_FLAGS.updateServiceV2();
    }
}
