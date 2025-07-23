package com.android.libcore.readonly;

/* loaded from: classes6.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_NATIVE_METRICS = "com.android.libcore.readonly.native_metrics";
    public static final String FLAG_POST_CLEANUP_APIS = "com.android.libcore.readonly.post_cleanup_apis";

    public static boolean nativeMetrics() {
        return FEATURE_FLAGS.nativeMetrics();
    }

    public static boolean postCleanupApis() {
        return FEATURE_FLAGS.postCleanupApis();
    }
}
