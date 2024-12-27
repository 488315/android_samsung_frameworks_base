package com.android.internal.hidden_from_bootclasspath.com.android.media.editing.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADD_MEDIA_METRICS_EDITING =
            "com.android.media.editing.flags.add_media_metrics_editing";

    public static boolean addMediaMetricsEditing() {
        return FEATURE_FLAGS.addMediaMetricsEditing();
    }
}
