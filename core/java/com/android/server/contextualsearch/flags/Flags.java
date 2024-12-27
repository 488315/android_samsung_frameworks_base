package com.android.server.contextualsearch.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_EXCLUDE_PERSISTENT_UI =
            "com.android.server.contextualsearch.flags.enable_exclude_persistent_ui";

    public static boolean enableExcludePersistentUi() {
        return FEATURE_FLAGS.enableExcludePersistentUi();
    }
}
