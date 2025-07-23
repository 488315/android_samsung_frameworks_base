package com.android.server.compat;

/* loaded from: classes6.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_SYSTEM_UID_TARGET_SYSTEM_SDK = "com.android.server.compat.system_uid_target_system_sdk";

    public static boolean systemUidTargetSystemSdk() {
        return FEATURE_FLAGS.systemUidTargetSystemSdk();
    }
}
