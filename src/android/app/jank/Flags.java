package android.app.jank;

/* loaded from: classes.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_DETAILED_APP_JANK_METRICS_API = "android.app.jank.detailed_app_jank_metrics_api";
    public static final String FLAG_DETAILED_APP_JANK_METRICS_LOGGING_ENABLED = "android.app.jank.detailed_app_jank_metrics_logging_enabled";
    public static final String FLAG_VIEWROOT_CHOREOGRAPHER = "android.app.jank.viewroot_choreographer";

    public static boolean detailedAppJankMetricsApi() {
        return FEATURE_FLAGS.detailedAppJankMetricsApi();
    }

    public static boolean detailedAppJankMetricsLoggingEnabled() {
        return FEATURE_FLAGS.detailedAppJankMetricsLoggingEnabled();
    }

    public static boolean viewrootChoreographer() {
        return FEATURE_FLAGS.viewrootChoreographer();
    }
}
