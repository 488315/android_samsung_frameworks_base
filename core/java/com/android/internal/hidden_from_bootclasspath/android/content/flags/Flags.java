package com.android.internal.hidden_from_bootclasspath.android.content.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_BIND_PACKAGE_ISOLATED_PROCESS =
            "android.content.flags.enable_bind_package_isolated_process";

    public static boolean enableBindPackageIsolatedProcess() {
        return FEATURE_FLAGS.enableBindPackageIsolatedProcess();
    }
}
