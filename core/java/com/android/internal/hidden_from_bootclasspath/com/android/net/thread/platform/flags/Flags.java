package com.android.internal.hidden_from_bootclasspath.com.android.net.thread.platform.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_THREAD_ENABLED_PLATFORM = "com.android.net.thread.platform.flags.thread_enabled_platform";
    public static final String FLAG_THREAD_USER_RESTRICTION_ENABLED = "com.android.net.thread.platform.flags.thread_user_restriction_enabled";

    public static boolean threadEnabledPlatform() {
        return FEATURE_FLAGS.threadEnabledPlatform();
    }

    public static boolean threadUserRestrictionEnabled() {
        return FEATURE_FLAGS.threadUserRestrictionEnabled();
    }
}
