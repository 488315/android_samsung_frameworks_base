package com.android.server.feature.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_READ_DROPBOX_PERMISSION =
            "com.android.server.feature.flags.enable_read_dropbox_permission";

    public static boolean enableReadDropboxPermission() {
        return FEATURE_FLAGS.enableReadDropboxPermission();
    }
}
