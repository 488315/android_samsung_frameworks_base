package com.android.internal.hidden_from_bootclasspath.android.hardware.devicestate.feature.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_DEVICE_STATE_PROPERTY_API =
            "android.hardware.devicestate.feature.flags.device_state_property_api";

    public static boolean deviceStatePropertyApi() {
        return FEATURE_FLAGS.deviceStatePropertyApi();
    }
}
