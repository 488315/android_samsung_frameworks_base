package android.server;

/* loaded from: classes3.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_REMOVE_TEXT_SERVICE = "android.server.remove_text_service";
    public static final String FLAG_TELEMETRY_APIS_SERVICE =
            "android.server.telemetry_apis_service";

    public static boolean removeTextService() {
        return FEATURE_FLAGS.removeTextService();
    }

    public static boolean telemetryApisService() {
        return FEATURE_FLAGS.telemetryApisService();
    }
}
