package com.android.internal.compat.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_SKIP_OLD_AND_DISABLED_COMPAT_LOGGING =
            "com.android.internal.compat.flags.skip_old_and_disabled_compat_logging";

    public static boolean skipOldAndDisabledCompatLogging() {
        return FEATURE_FLAGS.skipOldAndDisabledCompatLogging();
    }
}
