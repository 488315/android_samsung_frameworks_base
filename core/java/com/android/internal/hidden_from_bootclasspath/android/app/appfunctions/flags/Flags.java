package com.android.internal.hidden_from_bootclasspath.android.app.appfunctions.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_APP_FUNCTION_MANAGER =
            "android.app.appfunctions.flags.enable_app_function_manager";

    public static boolean enableAppFunctionManager() {
        return FEATURE_FLAGS.enableAppFunctionManager();
    }
}
