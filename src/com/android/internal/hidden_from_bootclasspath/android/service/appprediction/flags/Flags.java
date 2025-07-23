package com.android.internal.hidden_from_bootclasspath.android.service.appprediction.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_SERVICE_FEATURES_API = "android.service.appprediction.flags.service_features_api";

    public static boolean serviceFeaturesApi() {
        return FEATURE_FLAGS.serviceFeaturesApi();
    }
}
