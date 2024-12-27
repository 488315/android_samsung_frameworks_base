package com.android.internal.hidden_from_bootclasspath.android.media.tv.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_BROADCAST_VISIBILITY_TYPES =
            "android.media.tv.flags.broadcast_visibility_types";
    public static final String FLAG_ENABLE_AD_SERVICE_FW =
            "android.media.tv.flags.enable_ad_service_fw";
    public static final String FLAG_TIAF_V_APIS = "android.media.tv.flags.tiaf_v_apis";

    public static boolean broadcastVisibilityTypes() {
        return FEATURE_FLAGS.broadcastVisibilityTypes();
    }

    public static boolean enableAdServiceFw() {
        return FEATURE_FLAGS.enableAdServiceFw();
    }

    public static boolean tiafVApis() {
        return FEATURE_FLAGS.tiafVApis();
    }
}
