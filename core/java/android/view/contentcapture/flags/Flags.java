package android.view.contentcapture.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_RUN_ON_BACKGROUND_THREAD_ENABLED =
            "android.view.contentcapture.flags.run_on_background_thread_enabled";

    public static boolean runOnBackgroundThreadEnabled() {
        return FEATURE_FLAGS.runOnBackgroundThreadEnabled();
    }
}
