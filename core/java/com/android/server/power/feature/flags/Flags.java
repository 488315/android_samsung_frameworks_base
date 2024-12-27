package com.android.server.power.feature.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_EARLY_SCREEN_TIMEOUT_DETECTOR =
            "com.android.server.power.feature.flags.enable_early_screen_timeout_detector";
    public static final String FLAG_IMPROVE_WAKELOCK_LATENCY =
            "com.android.server.power.feature.flags.improve_wakelock_latency";

    public static boolean enableEarlyScreenTimeoutDetector() {
        return FEATURE_FLAGS.enableEarlyScreenTimeoutDetector();
    }

    public static boolean improveWakelockLatency() {
        return FEATURE_FLAGS.improveWakelockLatency();
    }
}
